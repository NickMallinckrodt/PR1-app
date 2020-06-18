package com.example.mysnitch;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class DiscussionThread implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private User user;
    private String title;
    private String content;
    private Media media;
    private int likes;

    @Ignore
    private ArrayList<Comment> comments;

    public DiscussionThread(User user, String title, String content, Media media){
        this.setUser(user);
        this.setTitle(title);
        this.setContent(content);
        this.setMedia(media);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(String comment, User user, Date date) {
        this.comments.add(new Comment(comment, user, date, this));
    }
}
