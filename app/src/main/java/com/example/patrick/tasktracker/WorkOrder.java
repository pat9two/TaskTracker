package com.example.patrick.tasktracker;

import java.security.Timestamp;
import java.sql.Date;

/**
 * Created by Patrick on 10/14/2014.
 */
public class WorkOrder {
    private int WO_id;
    private String Work_description;
    private Date Schedule_date;
    private Timestamp Start_time;
    private Timestamp End_time;

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

    public Timestamp getStart_time() {
        return this.Start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.Start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return this.End_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.End_time = end_time;
    }
}
