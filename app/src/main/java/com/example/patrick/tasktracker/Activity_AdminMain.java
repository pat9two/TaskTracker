package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_AdminMain extends Activity {
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra(Activity_LoginMain.EXTRA_USERNAME);

        title = (TextView)findViewById(R.id.admin_main_title);
        title.setText("Welcome " + username);
    }

    public void viewWorkOrders(View view)
    {

    }


    public void viewEmployees(View view)
    {

    }


    public void viewDepartments(View view)
    {

    }
}
