package com.sarkisian.gh.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sarkisian.gh.data.entity.ApiResponse;
import com.sarkisian.gh.data.api.GitHubAPI;
import com.sarkisian.gh.data.db.GitHubDatabase;
import com.sarkisian.gh.data.entity.Repo;
import com.sarkisian.gh.data.entity.Resource;
import com.sarkisian.gh.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class GitHubRepository {

    private final GitHubAPI mGitHubAPI;
    private final GitHubDatabase mGitHubDatabase;
    private final AppExecutors mAppExecutors;

    @Inject
    public GitHubRepository(GitHubAPI gitHubAPI,
                            GitHubDatabase gitHubDatabase,
                            AppExecutors appExecutors) {
        mGitHubAPI = gitHubAPI;
        mGitHubDatabase = gitHubDatabase;
        mAppExecutors = appExecutors;
    }

    public LiveData<Resource<List<Repo>>> getRepos(String gitHubUser) {
        return new NetworkBoundResource<List<Repo>, List<Repo>>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Repo> repoList) {
                mGitHubDatabase.repoDao().insertOrUpdateRepos(repoList);
            }

            @Override
            protected boolean shouldFetchFromApi(@Nullable List<Repo> localRepoList) {
                return true; // shouldFetch = repoList == null || repoList.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Repo>> loadFromDb() {
                return mGitHubDatabase.repoDao().getRepos();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Repo>>> callApi() {
                return mGitHubAPI.getRepos(gitHubUser);
            }

            @Override
            protected void onApiCallFailed(String message) {
                Timber.e(message);
            }

        }.asLiveData();
    }

}
