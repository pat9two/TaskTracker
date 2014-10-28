package com.example.patrick.tasktracker;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Material {
    private int Material_id;
    private String Material_name;
    private String Sync_id;
    private Date Sync_timestamp;

    public Material(){}

    public Material(int Material_id,
                    String Material_name) {
        this.Material_name = Material_name;
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

    public int getMaterial_id() {
        return this.Material_id;
    }

    public void setMaterial_id(int material_id) {
        this.Material_id = material_id;
    }

    public String getMaterial_name() {
        return this.Material_name;
    }

    public void setMaterial_name(String material_name) {
        this.Material_name = material_name;
    }
}