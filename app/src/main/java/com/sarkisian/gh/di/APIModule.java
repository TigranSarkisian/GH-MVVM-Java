package com.sarkisian.gh.di;

import com.sarkisian.gh.data.api.ApiFactory;
import com.sarkisian.gh.data.api.GitHubAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class APIModule {

    @Singleton
    @Provides
    GitHubAPI provideGitHubService() {
        return ApiFactory.getGitHubService();
    }

}
