package com.example.patrick.tasktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_UserMain extends ActionBarActivity {
    ListView userWorkOrderListView;
    ParseQueryAdapter<ParseObject> mainAdapter;
    String username;
    String objectId;
    final Context context = this;
    @Override
    public void onCreate(Bundle savedInstanceState){
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        Intent intent = getIntent();
        username = intent.getStringExtra(Activity_LoginMain.EXTRA_USERNAME);
        objectId = intent.getStringExtra("objectId");

        Log.d("UserMain", ""+objectId);
        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>(){
            public ParseQuery<ParseObject> create() {
                //gets all workorder_employee table objects,
                // then finds where employee column equal to the employee object.
                ParseQuery<ParseObject> wo_emp = new ParseQuery<ParseObject>("WorkOrder_Employee");
                wo_emp.include("workorder");
                wo_emp.whereEqualTo("employee", ParseObject.createWithoutData("Employee", objectId));

                return wo_emp;
            }
        };
        mainAdapter = new QueryAdapterUserMain(context, factory);



        userWorkOrderListView = (ListView) findViewById(R.id.user_employee_list_view);
        userWorkOrderListView.setAdapter(mainAdapter);


        userWorkOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Activity_UserJobView.class);
                intent.putExtra("workOrderId", po.getObjectId());
                intent.putExtra("userId", objectId);
                Log.d("UserMainWorkorders", "WorkOrder: " + po.getObjectId() + " Employee: " + objectId);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.admin_wo_refresh_item:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
