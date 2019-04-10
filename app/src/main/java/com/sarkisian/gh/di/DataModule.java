package com.sarkisian.gh.di;

import android.content.Context;

import com.sarkisian.gh.GitHubApp;
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
public class DataModule {

    @Provides
    @Singleton
    GitHubDatabase provideGitHubDatabase(Context context) {
        return DbFactory.buildDb(context);
    }

    @Singleton
    @Provides
    GitHubRepository provideGitHubRepository(GitHubAPI gitHubAPI,
                                             GitHubDatabase repoDatabase,
                                             AppExecutors appExecutors) {
        return new GitHubRepository(gitHubAPI, repoDatabase, appExecutors);
    }

}
