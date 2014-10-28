package com.example.patrick.tasktracker;


import java.util.Date;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Location {
    private int Location_id;
    private String Location_name;
    private int Department_id;
    private String Sync_id;
    private Date Sync_timestamp;

    public Location(){}
    public Location(int Location_id,
                    String Location_name){
        this.Location_id = Location_id;
        this.Location_name = Location_name;
    }

    public Date getSync_timestamp() {
        return this.Sync_timestamp;
    }

    public void setSync_timestamp(Date sync_timestamp) {
        this.Sync_timestamp = sync_timestamp;
    }

    public String getSync_id() {
        return this.Sync_id;
    }

    public void setSync_id(String sync_id) {
        this.Sync_id = sync_id;
    }

    public int getDepartment_id() {
        return this.Department_id;
    }

    public void setDepartment_id(int department_id) {
        this.Department_id = department_id;
    }

    public int getLocation_id() {
        return this.Location_id;
    }

    public void setLocation_id(int location_id) {
        this.Location_id = location_id;
    }

    public String getLocation_name() {
        return this.Location_name;
    }

    public void setLocation_name(String location_name) {
        this.Location_name = location_name;
    }
}
