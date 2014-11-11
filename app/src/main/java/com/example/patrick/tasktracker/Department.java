package com.example.patrick.tasktracker;


import java.util.Date;

/**
 * Created by Patrick on 10/8/2014.
 */

public class Department {
    private int Department_id;
    private String Department_name;
    private String Charged;
    private String Sync_id;
    private Date Sync_timestamp;



    public Department(){}

    public Department(int Department_id,
                      String charged,
                      String Sync_id){
        this.Department_id = Department_id;
        this.Charged = charged;
        this.Sync_id = Sync_id;
    }

    public Department(String Department_name,
                      String Charged){
        this.Department_name = Department_name;
        this.Charged = Charged;
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

    public String getDepartment_name() {
        return this.Department_name;
    }

    public void setDepartment_name(String department_name) {
        this.Department_name = department_name;
    }

    public int getDepartment_id(){
        return this.Department_id;
    }

    public void setDepartment_id(int Eagle_id){
        this.Department_id = Department_id;
    }

    public String getChargedStatus(){
        return this.Charged;
    }

    public void setChargedStatus(String Charged){
        this.Charged = Charged;
    }

}
