package com.fil.github_client.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitRepository implements Serializable, Parcelable {
    public static final GitRepository EMPTY_GIT_REPOSITORY = new GitRepository();

    @Expose
    Integer id;

    @Expose
    String name;

    @SerializedName("full_name")
    @Expose
    String fullName;

    @Expose
    User owner;

    @SerializedName("html_url")
    @Expose
    String htmlUrl;

    @Expose
    String description;

    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("updated_at")
    @Expose
    String updatedAt;

    @Expose
    String homepage;

    @SerializedName("stargazers_count")
    @Expose
    Integer stargazersCount;

    @Expose
    String language;

    @Expose
    Integer watchers;

    @Expose
    Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.fullName);
        dest.writeParcelable(this.owner, flags);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.description);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.homepage);
        dest.writeValue(this.stargazersCount);
        dest.writeString(this.language);
        dest.writeValue(this.watchers);
        dest.writeValue(this.score);
    }

    public GitRepository() {
    }

    protected GitRepository(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.fullName = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.htmlUrl = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.homepage = in.readString();
        this.stargazersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.language = in.readString();
        this.watchers = (Integer) in.readValue(Integer.class.getClassLoader());
        this.score = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<GitRepository> CREATOR = new Parcelable.Creator<GitRepository>() {
        @Override
        public GitRepository createFromParcel(Parcel source) {
            return new GitRepository(source);
        }

        @Override
        public GitRepository[] newArray(int size) {
            return new GitRepository[size];
        }
    };
}
