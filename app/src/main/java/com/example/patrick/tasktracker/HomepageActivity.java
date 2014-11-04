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



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class HomepageActivity extends Activity {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date checkpoint;
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
        checkpoint = new Date(System.currentTimeMillis());
    }

    public void dropEntry(View view){

        rowlist = (TextView)findViewById(R.id.list);
        SyncData();

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
        SyncData();
        Date date = new Date(System.currentTimeMillis());


        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        Employee em = new Employee("pm01789", "password", textbox.getText().toString(), "Marino", "1", date);
        db.addEmployee(em, true);

        textview = (TextView)findViewById(R.id.textbox);
        textview.setText(String.valueOf(em.getEagle_id()));

        db.close();
        checkpoint = new Date(System.currentTimeMillis());
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

        checkpoint = new Date(System.currentTimeMillis());

        SyncData();

    }

    public void SyncData(){
       final DatabaseHandler db = new DatabaseHandler(this);
        final List<Employee> empList = new ArrayList<Employee>();
        Log.d("Notice", "running SyncData()");


        if(db.getEmployeesCount() > 0) {

            List<Employee> tempEmpList = db.getAllEmployees();
            Employee em = db.getEmployee(tempEmpList.size());

            Log.d("Notice", "Local db has employee rows");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
            Log.d("Notice", "Name " + em.getFirst_name() + " Checkpoint " + checkpoint);

            query.whereGreaterThan("updatedAt", checkpoint);
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
                                    new Date(System.currentTimeMillis()));
                            emp.setSync_id(Employee.getObjectId());
                            empList.add(emp);
                        }
                        for(int i = 0; i < empList.size(); i++){
                            Log.d("Notice","Adding employee to local db");
                            db.addEmployee(empList.get(i), false);
                        }
                        checkpoint = new Date(System.currentTimeMillis());
                        db.close();
                    } else {
                        // something went wrong
                        Log.d("Sync error", "Cannot sync employees table");
                        db.close();
                    }
                }
            });
        }else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
            query.whereLessThan("updatedAt", checkpoint);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objectList, ParseException e) {
                    Log.d("Empty database", "Query returned " + objectList.size() + " entries");
                    if (e == null) {
                        for(ParseObject Employee : objectList){
                            Employee emp = new Employee(
                                    (String)Employee.get("User_name"),
                                    (String)Employee.get("Password"),
                                    (String)Employee.get("First_name"),
                                    (String)Employee.get("Last_name"),
                                    (String)Employee.get("Admin"),
                                    (Date)Employee.get("updatedAt"));
                            emp.setSync_id(Employee.getObjectId());
                            empList.add(emp);
                        }
                        for(int i = 0; i < empList.size(); i++){
                            Log.d("Notice","Adding employee to local db");
                            db.addEmployee(empList.get(i), false);
                        }
                        checkpoint = new Date(System.currentTimeMillis());
                        db.close();
                    } else {
                        // something went wrong
                        Log.d("Sync error", "Cannot sync employees table");
                        db.close();
                    }

                }
            });
        }


    }
}
