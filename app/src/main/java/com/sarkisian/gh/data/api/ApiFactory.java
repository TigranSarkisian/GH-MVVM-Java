package com.sarkisian.gh.data.api;


import com.sarkisian.gh.BuildConfig;
import com.sarkisian.gh.util.LiveDataCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    public static class Url {
        static final String BASE_URL = "https://api.github.com/";
    }

    public static GitHubService getGitHubService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addInterceptor(logging);
        }

        OkHttpClient okHttpClient = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .client(okHttpClient)
                //.callbackExecutor(Executors.newFixedThreadPool(5))
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubService.class);
    }

}
