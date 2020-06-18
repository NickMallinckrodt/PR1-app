package com.example.mysnitch.database;

import android.location.Location;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.example.mysnitch.Media;
import com.example.mysnitch.User;
import com.example.mysnitch.Vehicle;

import java.util.Date;


public class Converters {


    @TypeConverter
    public static String userToString(User user){
        return new Gson().toJson(user);
    }

    @TypeConverter
    public static User stringToUser(String string){
        return new Gson().fromJson(string, User.class);
    }

    @TypeConverter
    public static String toTimestamp(Date date){
        return date.toString();
    }

    @TypeConverter
    public static Date fromTimeStamp(String string){
        return new Date(string);
    }

    @TypeConverter
    public static String vehicleToString(Vehicle vehicle){
        return new Gson().toJson(vehicle);
    }

    @TypeConverter
    public static Vehicle stringToVehicle(String string){
        return new Gson().fromJson(string, Vehicle.class);
    }

    @TypeConverter
    public static Media stringToMedia(byte[] imagebytes){
        return new Media(imagebytes);
    }

    @TypeConverter
    public static byte[] mediaToString(Media media){
        return media.getImageByteArray();
    }

    @TypeConverter
    public static Location stringToLocation(String string){
        return new Location(string);
    }

    @TypeConverter
    public static String locationToString(Location location){
        return location.getProvider();
    }


}
