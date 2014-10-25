package com.example.patrick.tasktracker;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Material {
    private int Material_id;
    private String Material_name;

    public Material() {
    }

    public Material(int Material_id, String Material_name) {
        this.Material_id = Material_id;
        this.Material_name = Material_name;
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