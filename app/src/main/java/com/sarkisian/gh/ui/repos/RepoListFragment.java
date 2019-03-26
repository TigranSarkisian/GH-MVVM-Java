package com.sarkisian.gh.ui.repos;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sarkisian.gh.R;
import com.sarkisian.gh.data.entity.Repo;
import com.sarkisian.gh.databinding.FragmentRepoListBinding;
import com.sarkisian.gh.ui.adapter.RepoAdapter;
import com.sarkisian.gh.ui.base.BaseFragment;

import javax.inject.Inject;

import timber.log.Timber;

import static com.sarkisian.gh.data.entity.Status.ERROR;
import static com.sarkisian.gh.data.entity.Status.SUCCESS;

public class RepoListFragment extends BaseFragment implements RepoAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RepoAdapter mRepoAdapter;
    private FragmentRepoListBinding mBinding;
    private RepoListViewModel mReposViewModel;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    public static RepoListFragment newInstance() {
        return new RepoListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReposViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RepoListViewModel.class);
        loadRepos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list,
                container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.rvRepoList.setLayoutManager(linearLayoutManager);
        mBinding.rvRepoList.setHasFixedSize(true);
        mBinding.rvRepoList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mBinding.srlRepoList.setOnRefreshListener(this);

        mRepoAdapter = new RepoAdapter(this);
        mBinding.rvRepoList.setAdapter(mRepoAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        loadRepos();
    }

    @Override
    public void onItemClick(Repo repo) {
        Toast.makeText(getActivity(), repo.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(Repo repo) {
        Toast.makeText(getActivity(), repo.getLanguage(), Toast.LENGTH_SHORT).show();
    }

    private void loadRepos() {
        mReposViewModel.getRepoList("google").observe(this, resource -> {
            if (resource != null) {
                Timber.i(String.valueOf(resource.mStatus));
                Toast.makeText(getContext(), String.valueOf(resource.mStatus), Toast.LENGTH_SHORT).show();

                if (resource.mData != null) {
                    mBinding.srlRepoList.setRefreshing(false);
                    mRepoAdapter.updateDataSet(resource.mData);
                }
            }
        });
    }

}
