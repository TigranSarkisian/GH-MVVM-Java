package com.sarkisian.gh.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sarkisian.gh.data.api.ApiResponse;
import com.sarkisian.gh.data.entity.Resource;
import com.sarkisian.gh.util.AppExecutors;

import java.util.Objects;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors mAppExecutors;
    private final MediatorLiveData<Resource<ResultType>> mResult = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
        mResult.setValue(Resource.loading(null));

        LiveData<ResultType> localSource = loadFromDb();

        mResult.addSource(localSource, localRepoList -> {
            mResult.removeSource(localSource);

            if (shouldFetchFromApi(localRepoList)) {
                fetchFromApi(localSource);
            } else {
                mResult.addSource(localSource, repos -> setValue(Resource.success(repos)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(mResult.getValue(), newValue)) {
            mResult.setValue(newValue);
        }
    }

    private void fetchFromApi(final LiveData<ResultType> localSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = callApi();

        // re-attach dbSource as a new source, it will dispatch its latest value quickly
        mResult.addSource(localSource, source -> setValue(Resource.loading(source)));
        mResult.addSource(apiResponse, response -> {

            mResult.removeSource(localSource);
            mResult.removeSource(apiResponse);

            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                mAppExecutors.io().execute(() -> {

                    // save api result into DB
                    saveCallResult(processResponse(response));

                    mAppExecutors.mainThread().execute(() ->
                            // specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            mResult.addSource(loadFromDb(),
                                    newData -> setValue(Resource.success(newData)))
                    );
                });

            } else {
                onApiCallFailed(response.mErrorMessage);
                mResult.addSource(localSource,
                        newData -> setValue(Resource.error(response.mErrorMessage, newData)));
            }
        });
    }

    protected void onApiCallFailed(String message) {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return mResult;
    }

    private RequestType processResponse(ApiResponse<RequestType> response) {
        return response.mBody;
    }

    protected abstract void saveCallResult(@NonNull RequestType item);

    protected abstract boolean shouldFetchFromApi(@Nullable ResultType localRepoList);

    @NonNull
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    protected abstract LiveData<ApiResponse<RequestType>> callApi();

}
