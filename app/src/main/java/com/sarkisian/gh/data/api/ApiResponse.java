package com.sarkisian.gh.data.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
import timber.log.Timber;

public class ApiResponse<T> {

    private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");

    public final int mCode;
    @Nullable
    public final T mBody;
    @Nullable
    public final String mErrorMessage;
    @NonNull
    public final Map<String, String> mLinks;

    public ApiResponse(Throwable error) {
        mCode = 500;
        mBody = null;
        mErrorMessage = error.getMessage();
        mLinks = Collections.emptyMap();
    }

    public ApiResponse(Response<T> response) {
        mCode = response.code();

        if (response.isSuccessful()) {
            mBody = response.body();
            mErrorMessage = null;

        } else {
            String message = null;

            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ex) {
                    Timber.e(ex, "Error while parsing response");
                }
            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }

            mErrorMessage = message;
            mBody = null;
        }

        String linkHeader = response.headers().get("link");

        if (linkHeader == null) {
            mLinks = Collections.emptyMap();

        } else {
            mLinks = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();

                if (count == 2) {
                    mLinks.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 300;
    }

}
