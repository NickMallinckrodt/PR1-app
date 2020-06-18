package com.example.mysnitch;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mysnitch.database.Converters;

import java.io.Serializable;
import java.util.Date;


@Entity
@TypeConverters(Converters.class)
public class Report implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private User user;
    private String location;
    private Vehicle vehicle;
    private Date date;
    private String address;

    private Media media;



    // Licensplate constructor weggehaald, zorgde voor confusion in Android Room
    // In AppRepository wordt nu een Vehicle gemaakt van de licenseplate als die nog niet bestond

    public Report(String title, String description, Vehicle vehicle, String location, String address, Media media)
    {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setVehicle(vehicle);
        this.setLocation(location);
        this.setAddress(address);
        this.setMedia(media);

        user = User.getLoggedInUser();
        date = new Date();


        // TODO get the current location and put it in location
    }

    public void setMedia(Media media){
        this.media = media;
    }

    public Media getMedia(){
        return this.media;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
