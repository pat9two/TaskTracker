package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_AdminWoEmployee extends ActionBarActivity {
//display all employees attached to this work order.
    String workOrderId;
    QueryAdapterWorkorderEmployee adapter;
    ListView listview;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee);
        Intent intent = getIntent();
        workOrderId = intent.getStringExtra("extra");

        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> wo_emp = new ParseQuery<ParseObject>("WorkOrder_Employee");
                wo_emp.include("employee");
                wo_emp.whereEqualTo("workorder", ParseObject.createWithoutData("WorkOrder", workOrderId));
                return wo_emp;
            }
        };
        adapter = new QueryAdapterWorkorderEmployee(this, factory);
        listview = (ListView)findViewById(R.id.admin_wo_emp_listView);
        listview.setAdapter(adapter);
        adapter.loadObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addEmployee(View view){
        Intent intent = new Intent(this, Activity_AdminWoEmployeeAdd.class);
        intent.putExtra("extra", workOrderId);
        startActivity(intent);
    }

    public void remEmployee(View view)
    {
        Intent intent = new Intent(this, Activity_AdminWoEmployeeRemove.class);
        intent.putExtra("extra", workOrderId);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.admin_employee_refresh_item:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
