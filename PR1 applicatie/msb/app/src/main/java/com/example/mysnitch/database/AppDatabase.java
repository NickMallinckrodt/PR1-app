package com.example.mysnitch.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mysnitch.DiscussionThread;
import com.example.mysnitch.Report;
import com.example.mysnitch.User;
import com.example.mysnitch.Vehicle;

@Database(entities = {Report.class, User.class, DiscussionThread.class, Vehicle.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReportDao reportDao();
    public abstract UserDao userDao();
    public abstract ThreadDao threadDao();
    public abstract VehicleDao vehicleDao();
}
