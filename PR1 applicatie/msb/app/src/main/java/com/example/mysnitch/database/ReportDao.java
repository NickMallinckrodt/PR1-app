package com.example.mysnitch.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mysnitch.Report;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReportDao {

    @Query("SELECT id, title, location, description, user, vehicle, date, address, media FROM Report")
    List<Report> getAllReports();

    @Query("SELECT * FROM Report WHERE id =:reportId")
    List<Report> getReportById(int reportId);

    @Insert
    void insertReport(Report report);

    @Delete
    void deleteReport(Report report);

    @Update
    void updateReport(Report report);
}
