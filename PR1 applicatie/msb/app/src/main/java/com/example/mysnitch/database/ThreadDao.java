package com.example.mysnitch.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mysnitch.DiscussionThread;

import java.util.List;

@Dao
public interface ThreadDao {

    @Query("SELECT id, user, title, content, media, likes FROM DiscussionThread")
    List<DiscussionThread> getAllThreads();

    @Insert
    void insertThread(DiscussionThread discussionThread);

    @Delete
    void deleteThread(DiscussionThread discussionThread);

    @Update
    void updateThread(DiscussionThread discussionThread);
}
