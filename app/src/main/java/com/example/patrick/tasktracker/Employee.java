package com.example.patrick.tasktracker;


/**
 * Created by Patrick on 10/5/2014.
 */
public class Employee {
    //private variables
    int Eagle_id;
    String User_name;
    String Password;
    String First_name;
    String Last_name;
    String Admin;

    // empty constructor
    public Employee(){}

    public Employee(String User_name,
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

    public Employee(String User_name,
                    String Password,
                    String First_name,
                    String Last_name){
        this.User_name = User_name;
        this.Password = Password;
        this.First_name = First_name;
        this.Last_name = Last_name;
    }




    // getters and setters
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
