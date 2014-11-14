package com.example.patrick.tasktracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;

import android.util.MonthDisplayHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Activity_AdminWoNew extends Activity {
    Spinner departmentSpinner;
    Spinner locationSpinner;
    EditText descriptionBox;
    EditText materialsBox;
    ParseQueryAdapter<ParseObject> departmentSpinnerAdapter;
    ParseQueryAdapter<ParseObject> locationSpinnerAdapter;
    String selectedDepartment;
    String selectedLocation;
    DatePicker scheduleDate;
    Button schedulebtn;
    TextView scheduleDateDisplay;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_new);
        setCurrentDateOnView();
        addListenerOnButton();
        departmentSpinner = (Spinner)findViewById(R.id.admin_new_departmentSpinner);
        locationSpinner = (Spinner)findViewById(R.id.admin_new_locationSpinner);
        descriptionBox = (EditText)findViewById(R.id.admin_new_workDesc_textBox);
        materialsBox = (EditText)findViewById(R.id.admin_new_workMats_textBox);
        scheduleDate = (DatePicker)findViewById(R.id.admin_new_schedule_date_picker);
        //schedulebtn = (Button)findViewById(R.id.admin_wo_new_datebtn);

        departmentSpinnerAdapter = new ParseQueryAdapter<ParseObject>(this, "Department");
        departmentSpinnerAdapter.setTextKey("Department_id");
        departmentSpinner.setAdapter(departmentSpinnerAdapter);

        //scheduleDate = ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ParseObject departmentObject = (ParseObject)parent.getItemAtPosition(position);
                selectedDepartment = departmentObject.getObjectId();

                ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory() {
                    @Override
                    public ParseQuery create() {
                        ParseQuery<ParseObject> query = new ParseQuery("Location");
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

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);
                selectedLocation = po.getObjectId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }
    public void save(View view) {
        if (!selectedDepartment.isEmpty() || !selectedLocation.isEmpty()){
            ParseObject po = new ParseObject("WorkOrder");
            //links to a department row
            po.put("department", ParseObject.createWithoutData("Department", selectedDepartment));
            //links to a location row
            po.put("location", ParseObject.createWithoutData("Location", selectedLocation));

            po.put("description", descriptionBox.getText().toString());
            po.put("materials", materialsBox.getText().toString());
            po.put("scheduleDate", getDateFromDatePicker(scheduleDate));
                    po.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                finish();
                            } else {
                                Log.d("AdminWoNew", "" + e.toString());
                            }
                        }
                    });
        }else{
            //show error for spinner not selected
            Log.d("AdminWorkorderNew", "must have selection in department spinner/location spinner");
        }
    }
    public void addListenerOnButton() {

        schedulebtn = (Button) findViewById(R.id.admin_wo_new_datebtn);

        schedulebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            scheduleDateDisplay.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            scheduleDate.init(year, month, day, null);

        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }
    public static String getDateFromDatePicker(DatePicker datePicker){


        String selectedDate = DateFormat.getDateInstance().format(datePicker.getCalendarView().getDate());

        return selectedDate;
    }
    // display current date
    public void setCurrentDateOnView() {

        scheduleDateDisplay = (TextView) findViewById(R.id.admin_new_schedule_display);
        scheduleDate = (DatePicker) findViewById(R.id.admin_new_schedule_date_picker);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        scheduleDateDisplay.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        scheduleDate.init(year, month, day, null);

    }

    public void cancel(){
        finish();
    }
}