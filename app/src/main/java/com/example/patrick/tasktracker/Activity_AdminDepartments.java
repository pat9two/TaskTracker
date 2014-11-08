package com.example.patrick.tasktracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shwaat on 11/2/2014.
 */
public class Activity_AdminDepartments extends Activity {
    ListView departmentListView;
    ArrayList<String> departments;
    ArrayAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);
        Log.d("Department","main");
        setContentView(R.layout.admin_department_main);
        departmentListView = (ListView)findViewById(R.id.departments_list_view);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Department");
        query.whereExists("Department_id");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectList, ParseException e) {
                Log.d("Departments", String.valueOf(objectList.size()));
                if(e == null && objectList.size() != 0){
                    departments = new ArrayList<String>();
                    for(int i = 0; i < objectList.size(); i++){
                        departments.add(objectList.get(i).get("Department_id").toString());
                        Log.d("Departments", "Populate " + objectList.get(i).get("Department_id").toString());
                    }
                    listAdapter.addAll(departments);
                    departmentListView.setAdapter(listAdapter);
                }
            }
        });

        departmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    public void addDepartment(View view){
        Intent intent = new Intent(this, Activity_AdminNewDepartment.class);
        startActivity(intent);
    }

}
