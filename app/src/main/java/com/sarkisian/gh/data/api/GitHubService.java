package com.sarkisian.gh.data.api;


import android.arch.lifecycle.LiveData;

import com.sarkisian.gh.data.entity.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("users/{username}/repos")
    LiveData<ApiResponse<List<Repo>>> getRepos(@Path("username") String username);

}
