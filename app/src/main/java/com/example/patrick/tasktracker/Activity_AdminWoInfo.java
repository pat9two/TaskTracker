package com.example.patrick.tasktracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;


public class Activity_AdminWoInfo extends ActionBarActivity {
    String objectId;
    TextView department;
    TextView location;
    TextView description;
    TextView materials;
    TextView schedule;
    ParseObject wo;

    public void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_info);
        Intent intent = getIntent();
        objectId  = intent.getStringExtra("extra");
        Log.d("WoInfo", "intent extra objectId: " + objectId);
        //set all xml elements to the local variables
        department = (TextView)findViewById(R.id.admin_wo_info_dept_label_value);
        location = (TextView)findViewById(R.id.admin_wo_info_loc_label_value);
        description = (TextView)findViewById(R.id.admin_wo_info_desc_label_value);
        materials = (TextView)findViewById(R.id.admin_wo_info_mats_label_value);
        schedule = (TextView)findViewById(R.id.admin_wo_info_schedule_label_value);

        //parse query to get all information referenced by the WorkOrder table.
        ParseQuery<ParseObject> woquery = ParseQuery.getQuery("WorkOrder");
        woquery.include("department");
        woquery.include("location");
        woquery.getInBackground(objectId, new GetCallback<ParseObject>() {
            //after all WorkOrder objects are retreived, run this.
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    wo = parseObject;
                    ParseObject dep = parseObject.getParseObject("department");
                    ParseObject loc = parseObject.getParseObject("location");

                    //checks if there are missing references from deletion of departments and locations.
                    if(dep != null && loc != null) {
                        //log statements.
                        Log.d("adminWoInfo", "Department objectId: " + dep.getObjectId());
                        Log.d("adminWoInfo", "Location objectId: " + loc.getObjectId());

                        //shows the department value in the app.
                        ParseQuery<ParseObject> depquery = ParseQuery.getQuery("Department");
                        depquery.getInBackground(dep.getObjectId(), new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    department.setText(object.getString("Department_id"));
                                } else {
                                    //log exception
                                    Log.d("adminwoinfo", e.toString());
                                }
                            }
                        });
                        //shows the location value in the app.
                        ParseQuery<ParseObject> locquery = ParseQuery.getQuery("Location");
                        locquery.whereEqualTo("objectId", loc.getObjectId());
                        locquery.getInBackground(loc.getObjectId(), new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    location.setText(object.getString("Location_id"));
                                } else {
                                    //log exception
                                    Log.d("adminwoinfo", e.toString());
                                }
                            }
                        });
                        //shows description, materials, and schedule in the app.
                        description.setText(wo.getString("description"));
                        materials.setText(wo.getString("materials"));
                        schedule.setText(wo.getString("scheduleDate"));
                    }else{
                        location.setText("Null");
                        department.setText("Null");
                        description.setText(wo.getString("description"));
                        materials.setText(wo.getString("materials"));
                        schedule.setText(wo.getString("scheduleDate"));
                    }
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

    //button on click method. navigate to next activity. pass the object id of the workorder.
    public void assignedEmployees(){
        Intent intent = new Intent(this, Activity_AdminWoEmployee.class);
        intent.putExtra("extra", objectId);
        startActivity(intent);
    }

    //button on click method. deletes the workorder that is currently being viewed.
    public void deleteWorkorder(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Workorder: Are you sure?").setTitle("Warning");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Remove loc
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("WorkOrder_Employee");
                query.include("workorder");
                query.whereEqualTo("workorder", ParseObject.createWithoutData("WorkOrder", wo.getObjectId()));
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if(e == null){
                            ParseObject.deleteAllInBackground(parseObjects);
                        }else{
                            Log.d("AdminWoInfoDelete", e.toString());
                        }
                    }
                });
                wo.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            finish();

                            // START REMOVAL TOAST
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Workorder Deleted.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            // END REMOVAL TOAST
                        } else {
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Something went wrong...";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            Log.d("AdminWoInfo", e.toString());
                        }
                    }
                });
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void cancelAction(View view){
        finish();
    }
}
