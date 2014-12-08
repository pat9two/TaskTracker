package com.example.patrick.tasktracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by Shwaat on 11/2/2014.
 */
public class Activity_AdminNewDepartment extends Activity {
    EditText depName;
    RadioGroup depRadioGroup;
    Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_department_new);
        intent = new Intent(this, Activity_AdminDepartments.class);

    }
    //button on click method
    public void cancelDep(View view){
        //go back to previous activity.
        finish();
    }

    //button on click method
    public void addDep(View view){
        depName = (EditText)findViewById(R.id.new_dept_field);
        depRadioGroup = (RadioGroup)findViewById(R.id.new_dept_radio_group);
        int selectedButton = depRadioGroup.getCheckedRadioButtonId();

        final ParseObject depObject = new ParseObject("Department");
        depObject.put("Department_id", depName.getText().toString());

        if(selectedButton == 0){
            depObject.put("Charged", "1");
        }else{
            depObject.put("Charged", "0");
        }
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Department");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    if(!parseObjects.contains(depObject)){
                        depObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e == null){
                                    finish();
                                }else{
                                    Log.d("AddDepartment", "Exception  "+ e.toString());
                                    finish();
                                }
                            }
                        });
                    }else{
                        //this department already exists, show alert.
                        // START TOAST
                        Context toastContext = getApplicationContext();
                        CharSequence text = "This department already exists.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(toastContext, text, duration).show();
                        // END TOAST

                    }
                }else{
                    //failure to find the object  --  not connected to internet.
                    Log.d("NewDepartment", e.toString());
                }
            }
        });
    }
}