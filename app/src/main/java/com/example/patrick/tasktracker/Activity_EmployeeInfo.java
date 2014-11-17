package com.example.patrick.tasktracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/15/2014.
 */
public class Activity_EmployeeInfo extends Activity {
    Intent intent;
    String objectId;
    TextView firstName;
    TextView lastName;
    TextView eagleId;
    TextView userName;
    ParseQueryAdapter<ParseObject> mainAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);

        // getIntent of the activity
        intent = getIntent();
        // set the content view to the proper layout
        setContentView(R.layout.admin_employee_info);

        // get objId from employeeObjId
        objectId  = intent.getStringExtra("employeeObjId");
        firstName = (TextView)findViewById(R.id.admin_emp_info_fName);
        lastName = (TextView)findViewById(R.id.admin_emp_info_lName);
        eagleId = (TextView)findViewById(R.id.admin_emp_info_eID);
        userName = (TextView)findViewById(R.id.admin_emp_info_user);
        Log.d("Employee", "objectId");
        ParseQuery<ParseObject> empinfoquery = ParseQuery.getQuery("Employee");
        empinfoquery.getInBackground(objectId, new GetCallback<ParseObject>(){
            public void done(ParseObject object, ParseException e){
                if(e == null){

                    firstName.append(object.getString("First_name"));
                    lastName.append(object.getString("Last_name"));
                    eagleId.append(object.getString("Eagle_id"));
                    userName.append(object.getString("User_name"));
                }else{
                    Log.d("Employee", e.toString());
                }
            }
        });

    }

    public void cancelAction(View view)
    {
        finish();
    }
}
