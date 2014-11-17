package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_AdminWoEmployee extends ActionBarActivity {
//display all employees attached to this work order.
    String workorderId;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee);
        Intent intent = getIntent();
        workorderId = intent.getStringExtra("extra");

        //ParseQueryAdapter<ParseObject>
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addEmployee(View view){
        Intent intent = new Intent(this, Activity_AdminWoEmployeeAdd.class);
        intent.putExtra("extra", workorderId);
        startActivity(intent);
    }
}
