package com.example.patrick.tasktracker;

import java.util.Date;

/**
 * Created by Patrick on 10/14/2014.
 */
public class WorkOrder {
    private int WO_id;
    private String Work_description;
    private Date Schedule_date;

    private Date Start_time;
    private Date End_time;
    private String Sync_id;
    private Date Sync_timestamp;

    public WorkOrder(){}

    public WorkOrder(int WO_id,
                     String work_description,
                     Date schedule_date,
                     String sync_id) {
        this.WO_id = WO_id;
        this.Work_description = work_description;
        this.Schedule_date = schedule_date;
        this.Sync_id = sync_id;
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
    public int getWO_id() {
        return this.WO_id;
    }

    public void setWO_id(int WO_id) {
        this.WO_id = WO_id;
    }

    public String getWork_description() {
        return this.Work_description;
    }

    public void setWork_description(String work_description) {
        this.Work_description = work_description;
    }

    public Date getSchedule_date() {
        return this.Schedule_date;
    }

    public void setSchedule_date(Date schedule_date) {
        this.Schedule_date = schedule_date;
    }

    public Date getStart_time() {
        return this.Start_time;
    }

    public void setStart_time(Date start_time) {
        this.Start_time = start_time;
    }

    public Date getEnd_time() {
        return this.End_time;
    }

    public void setEnd_time(Date end_time) {
        this.End_time = end_time;
    }
}
