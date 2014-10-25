package com.example.patrick.tasktracker;

/**
 * Created by Patrick on 10/8/2014.
 */

public class Department {
    private int Department_id;
    private String Department_name;
    private String Charged;

    public Department(){}

    public Department(int Department_id, String charged){
       this.Department_id = Department_id;
       this.Charged = Charged;
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
