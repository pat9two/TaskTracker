package com.example.patrick.tasktracker;

import java.util.Date;

/**
 * Created by Patrick on 10/27/2014.
 */
public class Emp_Work_order {
    private int Emp_WO_id;
    private String Sync_id;
    private Date Sync_timestamp;

    public Emp_Work_order(){}

    public Emp_Work_order(int Emp_WO_id, String Sync_id){
        this.Emp_WO_id = Emp_WO_id;
        this.Sync_id = Sync_id;
    }

    public String getSync_id() {
        return this.Sync_id;
    }

    public void setSync_id(String sync_id) {
        this.Sync_id = sync_id;
    }

    public Date getSync_timestamp() {
        return this.Sync_timestamp;
    }

    public void setSync_timestamp(Date sync_timestamp) {
        this.Sync_timestamp = sync_timestamp;
    }

    public int getEmp_WO_id() {
        return this.Emp_WO_id;
    }

    public void setEmp_WO_id(int emp_WO_id) {
        this.Emp_WO_id = emp_WO_id;
    }
}
