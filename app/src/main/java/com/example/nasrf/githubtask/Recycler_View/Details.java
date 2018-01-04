package com.example.nasrf.githubtask.Recycler_View;

/**
 * Created by nasrf on 3/1/2018.
 */

public class Details {
    private String userName;
    private String description;
    private String repoName;
    private String fork;
    private String url;

    public Details(String userName, String description, String repoName, String fork,String url) {
        this.userName = userName;
        this.description = description;
        this.repoName = repoName;
        this.fork = fork;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
}
