package com.sarkisian.gh.di;

import com.sarkisian.gh.GitHubApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ViewModelModule.class,
        AppModule.class,
        APIModule.class,
        DataModule.class,
        BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(GitHubApp application);

        AppComponent build();
    }

    void inject(GitHubApp application);

}
