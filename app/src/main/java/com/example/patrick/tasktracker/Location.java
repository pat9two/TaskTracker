package com.example.patrick.tasktracker;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Location {
    private int Location_id;
    private String Location_name;
    private int Department_id;


    public Location(){}
    public Location(int Location_id, String Location_name){
        this.Location_id = Location_id;
        this.Location_name = Location_name;
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
