package com.example.patrick.tasktracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.parse.Parse;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_AdminWoEmployeeRemove extends Activity {

    String workOrderId;
    public void OnCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee_remove);
    }
}
