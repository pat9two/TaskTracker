package com.example.patrick.tasktracker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_AdminWoEmployeeRemove extends ActionBarActivity {

    public void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee_remove);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
