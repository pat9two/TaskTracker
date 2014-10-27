package com.example.patrick.tasktracker;


/**
 * Created by Patrick on 10/5/2014.
 */
public class Employee {
    //private variables
    private String Sync_id;



    private int Eagle_id;
    private  String User_name;
    private  String Password;
    private  String First_name;
    private  String Last_name;
    private  String Admin;

    // empty constructor
    public Employee(){}

    public Employee(int Eagle_id,
                    String User_name,
                    String Password,
                    String First_name,
                    String Last_name,
                    String Admin){
        this.Eagle_id = Eagle_id;
        this.User_name = User_name;
        this.Password = Password;
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Admin = Admin;
    }

    public Employee(
                    String User_name,
                    String Password,
                    String First_name,
                    String Last_name,
                    String Admin){
        ///  this.Eagle_id = Eagle_id;
        this.User_name = User_name;
        this.Password = Password;
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Admin = Admin;
    }
     // getters and setters

    public String getSync_id() {
        return this.Sync_id;
    }

    public void setSync_id(String sync_id) {
        this.Sync_id = sync_id;
    }

    public int getEagle_id(){
        return this.Eagle_id;
    }

    public void setEagle_id(int Eagle_id){
        this.Eagle_id = Eagle_id;
    }

    public String getUser_name(){
        return this.User_name;
    }

    public void setUser_name(String User_name){
        this.User_name = User_name;
    }

    public String getPassword(){
        return this.Password;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public String getFirst_name(){
        return this.First_name;
    }

    public void setFirst_name(String first_name){
        this.First_name = First_name;
    }

    public String getLast_name(){
        return this.Last_name;
    }

    public void setLast_name(String Last_name){
        this.Last_name = Last_name;
    }

    public String getAdmin(){
        return this.Admin;
    }

    public void setAdmin(String Admin){
        this.Admin = Admin;
    }

}
