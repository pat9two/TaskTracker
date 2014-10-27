package com.example.patrick.tasktracker;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Material {
    private int Material_id;
    private String Material_name;
    private String Sync_id;

    public Material() {
    }

    public Material(int Material_id, String Material_name) {
        this.Material_id = Material_id;
        this.Material_name = Material_name;
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