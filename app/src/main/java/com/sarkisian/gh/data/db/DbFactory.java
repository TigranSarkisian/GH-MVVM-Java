package com.sarkisian.gh.data.db;


import android.arch.persistence.room.Room;
import android.content.Context;

public class DbFactory {

    public static final String DB_NAME = "repo.db";
    public static final int DB_VERSION = 1;
    public static final String REPO_TABLE = "REPO_TABLE";

    public static GitHubDatabase buildDb(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                GitHubDatabase.class,
                DB_NAME
        ).build();
    }

}
