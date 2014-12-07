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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.CountCallback;
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
    QueryAdapterEmployee listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        final Context context = this;
        super.onCreate(savedInstanceState);
        //set xml layout
        setContentView(R.layout.admin_employee_main);

        //query factory for use in list view adapter.
        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                //gets all employee objects by newest to oldest.
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Employee");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        //custom adapter to show the employee's first and last names along with their usernames.
        listAdapter = new QueryAdapterEmployee(context, factory);
        employeeListView = (ListView) findViewById(R.id.emp_list_view);

        //set listener for when a user presses on a listitem. navigates to the next activity and passes the employee objectid in the intent.
        employeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Activity_EmployeeInfo.class);
                intent.putExtra("employeeObjId", po.getObjectId());
                Log.d("AdminEmployee", " " +po.getObjectId());
                startActivity(intent);
            }
        });
            employeeListView.setAdapter(listAdapter);
            listAdapter.loadObjects();
    }

    //button on click method. navigates to next activity
    public void createNewEmployee(View view){
        Intent intent = new Intent(this, Activity_EmployeeNew.class);
        startActivity(intent);
    }
    //button on click method. navigates to next activity.
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
