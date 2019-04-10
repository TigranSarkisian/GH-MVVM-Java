package com.sarkisian.gh.data.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.sarkisian.gh.data.entity.Status.ERROR;
import static com.sarkisian.gh.data.entity.Status.LOADING;
import static com.sarkisian.gh.data.entity.Status.SUCCESS;

public class Resource<T> {

    @NonNull
    private final Status mStatus;
    @Nullable
    private final String mMessage;
    @Nullable
    public final T mData;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.mStatus = status;
        this.mData = data;
        this.mMessage = message;
    }

    public Status getStatus() {
        return mStatus;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (mStatus != resource.mStatus) {
            return false;
        }

        if (mMessage != null ? !mMessage.equals(resource.mMessage) : resource.mMessage != null) {
            return false;
        }

        return mData != null ? mData.equals(resource.mData) : resource.mData == null;
    }

    @Override
    public int hashCode() {
        int result = mStatus.hashCode();
        result = 31 * result + (mMessage != null ? mMessage.hashCode() : 0);
        result = 31 * result + (mData != null ? mData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + mStatus +
                ", message='" + mMessage + '\'' +
                ", data=" + mData +
                '}';
    }


}
