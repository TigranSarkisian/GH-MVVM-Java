package com.sarkisian.gh.di;

import com.sarkisian.gh.ui.main.MainActivity;
import com.sarkisian.gh.ui.repos.RepoListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = RepoListFragmentModule.class)
    abstract RepoListFragment bindRepoListFragment();

}
