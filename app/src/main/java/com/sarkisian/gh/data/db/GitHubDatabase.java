package com.sarkisian.gh.data.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sarkisian.gh.data.entity.Repo;

import static com.sarkisian.gh.data.db.DbFactory.DB_VERSION;

@Database(entities = {Repo.class}, version = DB_VERSION)
public abstract class GitHubDatabase extends RoomDatabase {

    public abstract RepoDao repoDao();

}
