package com.example.patrick.tasktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_EmployeeMain extends ActionBarActivity {
    ListView employeeListView;
    ParseObject empObj;
    QueryAdapterEmployee listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_employee_main);
        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Employee");
                query.orderByDescending("createdAt");
                return query;
            }
        };

        listAdapter = new QueryAdapterEmployee(context, factory);
        employeeListView = (ListView) findViewById(R.id.emp_list_view);

        employeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);
                empObj = po;
                String objId = po.getString("objectId");

                Intent intent = new Intent(view.getContext(), Activity_EmployeeInfo.class);
                intent.putExtra("employeeObjId", objId);
                Log.d("AdminEmployee", " " +po.getObjectId());
                startActivity(intent);
            }
        });
            employeeListView.setAdapter(listAdapter);
            listAdapter.loadObjects();
    }

    public void createNewEmployee(View view){
        Intent intent = new Intent(this, Activity_EmployeeNew.class);
        startActivity(intent);
    }
    public void removeEmployee(View view){
        Intent intent = new Intent(this, Activity_EmployeeRemove.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
