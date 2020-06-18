package com.example.mysnitch;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String licensePlate;
    private int timesReported;
    private String vehicleDescription;


    public Vehicle(String licensePlate)
    {
        this.setLicensePlate(licensePlate);
        timesReported = 1;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getTimesReported() {
        return timesReported;
    }

    public void setTimesReported(int timesReported) {
        this.timesReported = timesReported;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }


    public void isReported(){
        this.timesReported++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
