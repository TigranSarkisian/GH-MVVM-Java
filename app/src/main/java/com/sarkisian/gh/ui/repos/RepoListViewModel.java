package com.sarkisian.gh.ui.repos;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sarkisian.gh.data.entity.Repo;
import com.sarkisian.gh.data.entity.Resource;
import com.sarkisian.gh.data.repository.GitHubRepository;
import com.sarkisian.gh.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

public class RepoListViewModel extends ViewModel {

    private final GitHubRepository mGitHubRepository;

    @Inject
    public RepoListViewModel(GitHubRepository gitHubRepository) {
        mGitHubRepository = gitHubRepository;
    }

    public LiveData<Resource<List<Repo>>> getRepoList(String username) {
        if (username == null){
            return AbsentLiveData.create();
        } else {
            return mGitHubRepository.getRepos(username);
        }
    }

}
