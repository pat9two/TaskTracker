package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_EmployeeMain extends Activity {
    ListView employeeListView;
    ArrayList<String> employees;
    ArrayAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_employee_main);
        employeeListView = (ListView)findViewById(R.id.emp_list_view);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");

        query.findInBackground(new FindCallback<ParseObject>() {
            //This code is ran after the query is finished.
            @Override
            public void done(List<ParseObject> objectList, ParseException e) {
                Log.d("Employees", String.valueOf(objectList.size()));
                //If no exception and object list has at least one object.
                if(e == null && objectList.size() != 0){
                    employees = new ArrayList<String>();
                    //Add the first name and last name to the employees Arraylist.
                    //Need to create a custom ListView adapter that adds number of assigned jobs.
                    for(int i = 0; i < objectList.size(); i++){
                        employees.add(objectList.get(i).get("First_name").toString() + " " + objectList.get(i).get("Last_name").toString());
                        Log.d("Employees", "Populate " + objectList.get(i).get("First_name").toString() + objectList.get(i).get("Last_name").toString());
                    }
                    listAdapter.addAll(employees);
                    employeeListView.setAdapter(listAdapter);
                }
            }
        });
    }

    public void createNewEmployee(View view){
        Intent intent = new Intent(this, Activity_EmployeeNew.class);
        startActivity(intent);
    }
    public void removeEmployee(View view){
        Intent intent = new Intent(this, Activity_EmployeeRemove.class);
        startActivity(intent);
    }
}
