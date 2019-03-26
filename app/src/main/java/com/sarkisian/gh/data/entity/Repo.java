package com.sarkisian.gh.data.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static com.sarkisian.gh.data.db.DbFactory.REPO_TABLE;

@Entity(tableName = REPO_TABLE)
public class Repo {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("language")
    private String language;

    @SerializedName("stargazersCount")
    private int stargazersCount;

    public Repo() {

    }

    public Repo(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Repo(long id, String name, String htmlUrl, String description,
                String language, int stargazersCount) {
        this.id = id;
        this.name = name;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.language = language;
        this.stargazersCount = stargazersCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

}
