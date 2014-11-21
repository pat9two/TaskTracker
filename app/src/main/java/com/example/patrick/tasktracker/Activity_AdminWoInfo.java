package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by Shwaat on 11/11/2014.
 */
public class Activity_AdminWoInfo extends ActionBarActivity {
    String objectId;
    TextView department;
    TextView location;
    TextView description;
    TextView materials;
    TextView schedule;

    public void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_info);
        Intent intent = getIntent();
        objectId  = intent.getStringExtra("extra");
        Log.d("WoInfo", "intent extra objectId: " + objectId);
        department = (TextView)findViewById(R.id.admin_wo_info_dept_label_value);
        location = (TextView)findViewById(R.id.admin_wo_info_loc_label_value);
        description = (TextView)findViewById(R.id.admin_wo_info_desc_label_value);
        materials = (TextView)findViewById(R.id.admin_wo_info_mats_label_value);
        schedule = (TextView)findViewById(R.id.admin_wo_info_schedule_label_value);
        //ParseQuery<ParseObject> depquery = ParseQuery.getQuery("Department");
       // ParseQuery<ParseObject> locquery = ParseQuery.getQuery("Location");
        ParseQuery<ParseObject> woquery = ParseQuery.getQuery("WorkOrder");
        woquery.include("department");
        woquery.include("location");
        woquery.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    ParseObject wo = parseObject;
                    ParseObject dep = parseObject.getParseObject("department");
                    ParseObject loc = parseObject.getParseObject("location");

                    Log.d("adminWoInfo", "Department objectId: " + dep.getObjectId());
                    Log.d("adminWoInfo", "Location objectId: " + loc.getObjectId());

                    ParseQuery<ParseObject> depquery = ParseQuery.getQuery("Department");
                    depquery.getInBackground(dep.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {

                                department.setText(object.getString("Department_id"));
                            } else {
                                Log.d("adminwoinfo", e.toString());
                            }
                        }
                    });
                    ParseQuery<ParseObject> locquery = ParseQuery.getQuery("Location");
                    locquery.whereEqualTo("objectId", loc.getObjectId());
                    locquery.getInBackground(loc.getObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                location.setText(object.getString("Location_id"));
                            } else {
                                Log.d("adminwoinfo", e.toString());
                            }
                        }
                    });
                    description.setText(wo.getString("description"));
                    materials.setText(wo.getString("materials"));
                    schedule.setText(wo.getString("scheduleDate"));

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_info_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.admin_wo_info_item:
                assignedEmployees();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void assignedEmployees(){
        Intent intent = new Intent(this, Activity_AdminWoEmployee.class);
        intent.putExtra("extra", objectId);
        startActivity(intent);
    }
}
