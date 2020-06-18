package com.example.mysnitch;

import com.example.mysnitch.User;

import java.util.Date;

public class Comment {
    private String comment;
    private Date date;
    private User user;
    private DiscussionThread discussionThread;

    public Comment(String comment, User user, Date date, DiscussionThread discussionThread){
        this.setComment(comment);
        this.setUser(user);
        this.setDate(date);
        this.setDiscussionThread(discussionThread);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DiscussionThread getDiscussionThread() {
        return discussionThread;
    }

    public void setDiscussionThread(DiscussionThread discussionThread) {
        this.discussionThread = discussionThread;
    }
}
