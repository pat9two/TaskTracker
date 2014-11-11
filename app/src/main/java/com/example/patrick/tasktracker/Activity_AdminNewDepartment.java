package com.example.patrick.tasktracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

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
    public void cancelDep(View view){
        finish();
    }
    public void addDep(View view){
        depName = (EditText)findViewById(R.id.new_dept_field);
        depRadioGroup = (RadioGroup)findViewById(R.id.new_dept_radio_group);
        int selectedButton = depRadioGroup.getCheckedRadioButtonId();

        ParseObject parseObject = new ParseObject("Department");
        parseObject.put("Department_id", depName.getText().toString());

        if(selectedButton == 0){
            parseObject.put("Charged", "1");
        }else{
            parseObject.put("Charged", "0");
        }

        parseObject.saveInBackground(new SaveCallback() {
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
    }
}