package com.example.patrick.tasktracker;

/**
 * Created by Patrick on 10/8/2014.
 */
public class Department {
    int Department_id;
    String Charged;

    public Department(){}

    public Department(int Department_id, String charged){
       this.Department_id = Department_id;
       this.Charged = Charged;
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
