package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;


public class Activity_AdminMain extends Activity {
    TextView title;
    String username;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Intent intent = getIntent();
        username = intent.getStringExtra(Activity_LoginMain.EXTRA_USERNAME);

        title = (TextView)findViewById(R.id.admin_main_title);
        title.setText("Welcome " + username);
    }

    //button on click method
    public void viewWorkOrders(View view){
        Intent intent = new Intent(this, Activity_AdminWoView.class);
        startActivity(intent);
    }

    //button on click method
    public void viewEmployees(View view){
        Intent intent = new Intent(this, Activity_EmployeeMain.class);
        startActivity(intent);
    }

    //button on click method
    public void viewDepartments(View view){
        Intent intent = new Intent(this, Activity_AdminDepartments.class);
        startActivity(intent);
    }

    //button on click method
    public void logoutAction(View view)
    {
        finish();
    }
}
