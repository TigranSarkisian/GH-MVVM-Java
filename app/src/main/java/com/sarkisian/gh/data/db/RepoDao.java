package com.sarkisian.gh.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sarkisian.gh.data.entity.Repo;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static com.sarkisian.gh.data.db.DbFactory.REPO_TABLE;

@Dao
public interface RepoDao {

    @Insert(onConflict = REPLACE)
    void insertOrUpdateRepos(List<Repo> repos);

    @Query("SELECT * FROM " + REPO_TABLE)
    LiveData<List<Repo>> getRepos();

    @Delete
    void deleteRepo(Repo repo);

}
