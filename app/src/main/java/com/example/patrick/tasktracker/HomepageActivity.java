package com.example.patrick.tasktracker;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.*;


import com.parse.FindCallback;

import com.parse.Parse;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomepageActivity extends Activity {

    /* Testing purposes only */
    //////////////////////////

    TextView textview;
    EditText textbox;
    TextView rowlist;

    public void refreshList(View view){
        rowlist = (TextView)findViewById(R.id.list);
        SyncData();
        DatabaseHandler db = new DatabaseHandler(this);
        List<Employee> empList = db.getAllEmployees();
        if(empList.size() >0){
            rowlist.setText("");
            for(int i = 0; i < empList.size(); i++){

                Employee em = empList.get(i);
                rowlist.append(em.getFirst_name() + " time: " + em.getSync_timestamp()+ "\n");
            }
        }

        db.close();
    }

    public void dropEntry(View view){

        rowlist = (TextView)findViewById(R.id.list);
        DatabaseHandler db = new DatabaseHandler(this);
        List<Employee> empList = db.getAllEmployees();
        if(empList.size() >0){
            Employee em = db.getEmployee(empList.size());
            Log.d("Dropping", em.getFirst_name());
            db.deleteEmployee(em);
            rowlist.setText("");
            for(int i = 0; i < empList.size(); i++){
                rowlist.append(empList.get(i).getFirst_name() + "\n");
            }
        }
        db.close();
    }

    public void addRow(View view){

        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        Employee em = new Employee("pm01789", "password", textbox.getText().toString(), "Marino", "1", new Date());
        db.addEmployee(em, true);

        textview = (TextView)findViewById(R.id.textbox);
        textview.setText(String.valueOf(em.getEagle_id()));
    }
    //////////////////////////////////
    /* Testing purposes only. */

    public void onClick(View view){
        DatabaseHandler db = new DatabaseHandler(this);

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        SyncData();

    }

    public void SyncData(){
       final DatabaseHandler db = new DatabaseHandler(this);
        final List<Employee> empList = new ArrayList<Employee>();
        Log.d("Notice", "running SyncData()");


        if(db.getEmployeesCount() > 0) {
            List<Employee> tempEmpList = db.getAllEmployees();
            Employee em = db.getEmployee(tempEmpList.size());

            Date ts = em.getSync_timestamp();
            Log.d("Notice", "Local db has employee rows");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
            Log.d("Notice", "Name " + em.getFirst_name() + " Looking for date " + em.getSync_timestamp());

            query.whereLessThan("updatedAt", ts);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objectList, ParseException e) {
                    Log.d("Notice", "Query returned " + objectList.size() + " entries");
                    if (e == null) {
                        for(ParseObject Employee : objectList){
                            Employee emp = new Employee((String)Employee.get("User_name"),
                                    (String)Employee.get("Password"),
                                    (String)Employee.get("First_name"),
                                    (String)Employee.get("Last_name"),
                                    (String)Employee.get("Admin"),
                                    (Date)Employee.get("updatedAt"));

                            empList.add(emp);
                        }
                        for(int i = 0; i < empList.size(); i++){
                            Log.d("Notice","Adding employee to local db");
                            db.addEmployee(empList.get(i), false);
                        }
                    } else {
                        // something went wrong
                        Log.d("Sync error", "Cannot sync employees table");
                    }


                }
            });
        }
        if(db.getEmployeesCount() == 0){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
            query.whereLessThan("updatedAt",new Date());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objectList, ParseException e) {
                    Log.d("Notice", "Query returned " + objectList.size() + " entries");
                    if (e == null) {
                        for(ParseObject Employee : objectList){
                            Employee emp = new Employee((String)Employee.get("User_name"),
                                    (String)Employee.get("Password"),
                                    (String)Employee.get("First_name"),
                                    (String)Employee.get("Last_name"),
                                    (String)Employee.get("Admin"),
                                    (Date)Employee.get("updatedAt"));

                            empList.add(emp);
                        }
                        for(int i = 0; i < empList.size(); i++){
                            Log.d("Notice","Adding employee to local db");
                            db.addEmployee(empList.get(i), false);
                        }
                    } else {
                        // something went wrong
                        Log.d("Sync error", "Cannot sync employees table");
                    }


                }
            });

        }else{
            Log.d("Sync error", "No entries to sync");
        }


        db.close();
    }

}
