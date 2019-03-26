package com.sarkisian.gh.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sarkisian.gh.R;
import com.sarkisian.gh.data.entity.Repo;
import com.sarkisian.gh.databinding.RepoItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repo> mRepoList;
    private OnItemClickListener mOnItemClickListener;

    public RepoAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mRepoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RepoItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.repo_item,
                viewGroup,
                false);

        return new ViewHolder(binding, mRepoList, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    public void updateDataSet(List<Repo> repoList) {
        mRepoList.clear();
        mRepoList.addAll(repoList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RepoItemBinding repoItemBinding;
        OnItemClickListener onItemClickListener;
        List<Repo> repoList;

        ViewHolder(RepoItemBinding repoItemBinding, List<Repo> repoList, OnItemClickListener onItemClickListener) {
            super(repoItemBinding.getRoot());
            this.repoItemBinding = repoItemBinding;
            this.repoList = repoList;
            this.onItemClickListener = onItemClickListener;
        }

        void bindData() {
            Repo repo = repoList.get(getAdapterPosition());
            repoItemBinding.setRepo(repo);
            repoItemBinding.executePendingBindings();

            repoItemBinding.getRoot().setOnClickListener(v ->
                    onItemClickListener.onItemClick(repoList.get(getAdapterPosition())));
            repoItemBinding.getRoot().setOnLongClickListener(v -> {
                onItemClickListener.onItemLongClick(repoList.get(getAdapterPosition()));
                return true;
            });
        }
    }

    public interface OnItemClickListener {

        void onItemClick(Repo repo);

        void onItemLongClick(Repo repo);
    }

}
