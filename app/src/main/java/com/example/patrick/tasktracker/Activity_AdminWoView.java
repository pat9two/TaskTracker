package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_AdminWoView extends ActionBarActivity {
    ParseQueryAdapter<ParseObject> mainAdapter;
    ListView workOrderListView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_view);

        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "WorkOrder");
        mainAdapter.setTextKey("WorkOrder_id");

        //not used
        //adapter = new AdminworkOrderListViewAdapter(this);

        workOrderListView = (ListView)findViewById(R.id.job_list_view);
        mainAdapter.setTextKey("description");
        workOrderListView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        workOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);


                Intent intent = new Intent(view.getContext(), Activity_AdminWoInfo.class);
                intent.putExtra("extra", po.getObjectId());
                Log.d("AdminWorkOrders", " " + po.getObjectId());
                startActivity(intent);
            }
        });
    }

    public void runSearch(){
       // EditText SearchCriteria = (EditText)findViewById(R.id.search_job_field);
        //String searchTerm = SearchCriteria.getText().toString().trim();
        //search all tables for the searchcritera. limited to departments locations, orders with employees assigned.

        //search departments

        //search locations

        //search workorder id

        //search employees


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_view_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.admin_wo_create_item:
                woCreateOption();
                return true;
            case R.id.admin_wo_assign_emp_item:
                // do create wo employees stuff
                return true;
            case R.id.admin_wo_search_item:
                // do search stuff
                runSearch();
                return true;
            case R.id.admin_wo_refresh_item:
                refreshActivity();
                return true;
            case R.id.admin_wo_settings_item:
                // do settings stuff
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void woCreateOption(){
        Intent intent = new Intent(this, Activity_AdminWoNew.class);
        startActivity(intent);
    }

    public void refreshActivity()
    {
        finish();
        startActivity(getIntent());
    }
}