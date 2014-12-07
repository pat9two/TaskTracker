package com.example.patrick.tasktracker;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import android.view.View;
import android.widget.*;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;


public class Activity_AdminWoNew extends Activity {
    Button scheduleDateButton;
    Spinner departmentSpinner;
    Spinner locationSpinner;
    EditText descriptionBox;
    EditText materialsBox;
    ParseQueryAdapter<ParseObject> departmentSpinnerAdapter;
    ParseQueryAdapter<ParseObject> locationSpinnerAdapter;
    String selectedDepartment;
    String selectedLocation;
    int counter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_new);
        //set xml elements to local variables.
        departmentSpinner = (Spinner)findViewById(R.id.admin_new_departmentSpinner);
        locationSpinner = (Spinner)findViewById(R.id.admin_new_locationSpinner);
        descriptionBox = (EditText)findViewById(R.id.admin_new_workDesc_textBox);
        materialsBox = (EditText)findViewById(R.id.admin_new_workMats_textBox);
        scheduleDateButton = (Button)findViewById(R.id.admin_new_schedule_button);

        //set adapter to the spinner in order to populate it with deparments
        departmentSpinnerAdapter = new ParseQueryAdapter<ParseObject>(this, "Department");
        departmentSpinner.setAdapter(departmentSpinnerAdapter);
        departmentSpinnerAdapter.setTextKey("Department_id");
        //when a department is selected from the first spinner, the 2nd spinner (locations) will be populated with the related locations.
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ParseObject departmentObject = (ParseObject)parent.getItemAtPosition(position);
                selectedDepartment = departmentObject.getObjectId();

                ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    @Override
                    public ParseQuery<ParseObject> create() {
                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Location");
                        query.whereEqualTo("parent", departmentObject);
                        return query;
                    }
                };
                    locationSpinnerAdapter = new ParseQueryAdapter<ParseObject>(view.getContext(), factory);
                    locationSpinnerAdapter.setTextKey("Location_id");
                    locationSpinner.setAdapter(locationSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        ParseQuery<ParseObject> numOfWorkorders = new ParseQuery<ParseObject>("WorkOrder");
        numOfWorkorders.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if(e == null){
                    //counter in order to display the number a workorder has.
                    counter = i;
                }
            }
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);
                selectedLocation = po.getObjectId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //button on click method
    public void saveWorkOrder(View view) {
        //checks if there are values selected in both spinners. (drop downs)
        if (!selectedDepartment.isEmpty() || !selectedLocation.isEmpty()){
            //checks if the fields are filled in correctly via checkFields() method.
            if(checkFields()) {
                ParseObject po = new ParseObject("WorkOrder");
                //links to a department row
                po.put("department", ParseObject.createWithoutData("Department", selectedDepartment));
                //links to a location row
                po.put("location", ParseObject.createWithoutData("Location", selectedLocation));

                po.put("description", descriptionBox.getText().toString());
                po.put("materials", materialsBox.getText().toString());
                //
                po.put("workorderId", counter + 1);
                po.put("scheduleDate", scheduleDateButton.getText().toString().trim());
                po.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //finishes current activity and returns to previous activity.
                            finish();
                        } else {
                            //log exception.
                            Log.d("AdminWoNew", "" + e.toString());
                        }
                    }
                });
            }
        }else{
            //show error for spinner not selected
            Log.d("AdminWorkorderNew", "must have selection in department spinner/location spinner");
        }
    }

    //button on click method. returns to previous activity doing no actions.
    public void cancel(View view){
        finish();
    }

    //button on click method. shows the date picker popup.
    public void showDatePickerDialog(View view){
        //custom dialog fragment to show only month, day, and year.
        DialogFragment newFragment = new AdminWoNew_setDate();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    //method to validate field entries. fields cannot be empty.
    public Boolean checkFields(){
        if(descriptionBox.getText().toString().trim().isEmpty()){
            descriptionBox.setError("Cannot be empty.");
            return false;
        }
        if(locationSpinner.getSelectedItem() == null){
            // START TOAST
            Context toastContext = getApplicationContext();
            CharSequence text = "Workorder needs a location.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(toastContext, text, duration).show();
            // END TOAST
            return false;
        }
        if(departmentSpinner.getSelectedItem().toString().isEmpty()){
            // START TOAST
            Context toastContext = getApplicationContext();
            CharSequence text = "Workorder needs a department.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(toastContext, text, duration).show();
            // END TOAST
            return false;
        }
        //materials box is allowed to be empty
        return true;
    }
}