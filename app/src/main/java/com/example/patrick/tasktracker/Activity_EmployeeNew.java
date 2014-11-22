package com.example.patrick.tasktracker;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_EmployeeNew extends Activity {
    EditText First_name, Last_name, Eagle_id, Username, Password, Confirm_password;
    RadioGroup adminButtonGroup;
    RadioButton adminYes, adminNo;
    EditText[] info = new EditText[6];
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_employee_new);

        First_name = (EditText)findViewById(R.id.admin_addEmp_fName_field);
        Last_name = (EditText)findViewById(R.id.admin_addEmp_lName_field);
        Eagle_id = (EditText)findViewById(R.id.admin_addEmp_eID_field);
        Username = (EditText)findViewById(R.id.admin_addEmp_user_field);
        Password = (EditText)findViewById(R.id.admin_addEmp_pass_field);
        Confirm_password = (EditText)findViewById(R.id.admin_addEmp_cPass_field);
        adminButtonGroup = (RadioGroup)findViewById(R.id.admin_addEmp_new_status);
        adminYes = (RadioButton)findViewById(R.id.admin_addEmp_radio_yes);
        adminNo = (RadioButton)findViewById(R.id.admin_addEmp_radio_no);
    }

    public void AddEmp(View view){


        info[0] = First_name;
        info[1] = Last_name;
        info[2] = Eagle_id;
        info[3] = Username;
        info[4] = Password;
        info[5] = Confirm_password;

        if(CheckFields()){
            final ParseObject po = new ParseObject("Employee");

            po.put("First_name", info[0].getText().toString().trim());
            po.put("Last_name", info[1].getText().toString().trim());
            po.put("Eagle_id", info[2].getText().toString().trim());
            po.put("User_name", info[3].getText().toString().trim());
            po.put("Password", info[4].getText().toString().trim());

            String adminValue = "0";
            // Sets administrator based on the radio button that was selected.
            if(adminYes.isChecked())
                {
                    adminValue = "1";
                }
            else
                if(adminNo.isChecked())
                    {
                        adminValue = "0";
                    }
            po.put("Admin", adminValue);

            po.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        //finishes adding employee and returns to employee list.
                        Log.d("AddEmployee", po.getString("First_name") + " " + po.getString("Last_name"));
                        finish();
                    }else{
                        Log.d("AddEmployee", e.toString());
                        finish();
                    }
                }
            });
        }
    }

    public Boolean CheckFields(){
        for(int i = 0; i < info.length; i++){
            if(info[i].getText().toString().trim().isEmpty()){
                //Field cannot be left blank
                info[i].setError("Field cannot be left blank");
                return false;
            }

            //Check field for spaces.
            if(info[i].getText().toString().trim().contains(" ")){
                //field cannot contain spaces.
                info[i].setError("Field cannot contain spaces");
                return false;
            }
            if(i == 2){
                if(info[i].getText().toString().trim().length() != 9){
                    info[i].setError("Field must be a 9 digit Eagle id");
                    return false;
                }
                try{

                    Integer.parseInt(info[i].getText().toString().trim());

                }catch(Exception e){
                    info[i].setError("Field must be 9 digit Eagle id");
                    return false;
                }
            }
            //Check matching password and confirm password fields
            if (i == 4){
                if(!info[i].getText().toString().equals(info[i + 1].getText().toString())) {
                    //Passwords must mach
                    info[i].setError("Passwords must match");
                    return false;
                }
            }
        }
        //All statements passed.
        return true;
    }

    public void cancelAction(View view)
    {
        finish();
    }
}