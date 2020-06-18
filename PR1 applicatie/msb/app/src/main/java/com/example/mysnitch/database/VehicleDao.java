package com.example.mysnitch.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mysnitch.Vehicle;

import java.util.List;

@Dao
public interface VehicleDao {

    @Query("SELECT * FROM Vehicle")
    List<Vehicle> getAllVehicles();

    @Query("SELECT * FROM Vehicle WHERE licensePlate=:lp")
    Vehicle getVehicleByLp(String lp);

    @Query("SELECT COUNT(*) FROM Vehicle WHERE licensePlate=:lp")
    int doesVehicleExist(String lp);

    @Insert
    void insertVehicle(Vehicle vehicle);
    @Update
    void updateVehicle(Vehicle vehicle);
    @Delete
    void deleteVehicle(Vehicle vehicle);


}
