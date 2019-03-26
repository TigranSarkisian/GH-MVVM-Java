package com.sarkisian.gh.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sarkisian.gh.ui.repos.RepoListViewModel;
import com.sarkisian.gh.util.RepoListViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel.class)
    abstract ViewModel bindRepoListViewModel(RepoListViewModel repoListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(RepoListViewModelFactory factory);

}
