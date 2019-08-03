package com.example.githubrepos;

public class Repos {
    private String name;
    private String commits;

    public Repos(String name, String commits) {
        this.name = name;
        this.commits = commits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }
}
