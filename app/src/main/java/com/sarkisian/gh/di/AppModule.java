package com.sarkisian.gh.di;

import android.content.Context;

import com.sarkisian.gh.GitHubApp;
import com.sarkisian.gh.data.api.ApiFactory;
import com.sarkisian.gh.data.api.GitHubService;
import com.sarkisian.gh.data.db.DbFactory;
import com.sarkisian.gh.data.db.GitHubDatabase;
import com.sarkisian.gh.data.repository.GitHubRepository;
import com.sarkisian.gh.util.AppExecutors;
import com.sarkisian.gh.util.NetworkUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = ViewModelModule.class)
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

    @Provides
    @Singleton
    GitHubDatabase provideGitHubDatabase(Context context) {
        return DbFactory.buildDb(context);
    }

    @Singleton
    @Provides
    GitHubService provideGitHubService() {
        return ApiFactory.getGitHubService();
    }

    @Singleton
    @Provides
    GitHubRepository provideGitHubRepository(GitHubService gitHubService, GitHubDatabase repoDatabase,
                                             AppExecutors appExecutors) {
        return new GitHubRepository(gitHubService, repoDatabase, appExecutors);
    }

}
