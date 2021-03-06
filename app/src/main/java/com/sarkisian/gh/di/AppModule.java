package com.sarkisian.gh.di;

import android.content.Context;

import com.sarkisian.gh.GitHubApp;
import com.sarkisian.gh.data.api.ApiFactory;
import com.sarkisian.gh.data.api.GitHubAPI;
import com.sarkisian.gh.data.db.DbFactory;
import com.sarkisian.gh.data.db.GitHubDatabase;
import com.sarkisian.gh.data.repository.GitHubRepository;
import com.sarkisian.gh.util.AppExecutors;
import com.sarkisian.gh.util.NetworkUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(GitHubApp application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    NetworkUtil provideNetworkUtil(Context context) {
        return new NetworkUtil(context);
    }

}
