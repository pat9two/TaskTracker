package com.example.patrick.tasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_LoginMain extends Activity {
    Date checkpoint;
    EditText username;
    EditText password;
    TextView alert;

    public final static String EXTRA_USERNAME = "com.example.patrick.tasktracker.USERNAME";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);
        //set xml layout.
        setContentView(R.layout.login_main);
    }


    public void LogIn(View view){
        final Intent adminIntent = new Intent(this, Activity_AdminMain.class);
        final Intent userIntent = new Intent(this, Activity_UserMain.class);

        username = (EditText)findViewById(R.id.login_main_username_field);
        password = (EditText)findViewById(R.id.login_main_password_field);
        alert = (TextView)findViewById(R.id.login_main_greeting);

        //query to find the employee account with the corresponding username and password.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
        query.whereEqualTo("User_name", username.getText().toString());
        query.whereEqualTo("Password", password.getText().toString());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                //checks to find at least one result from the query.
                if(e == null && parseObjects.size() > 0){
                    Log.d("Login", parseObjects.get(0).get("User_name").toString() + " " +  parseObjects.get(0).get("Admin").toString());
                    String admin = parseObjects.get(0).get("Admin").toString();
                    if(admin.equals("1")) {
                        //starts admin activity.
                        Log.d("Login", parseObjects.get(0).get("User_name").toString());

                        adminIntent.putExtra(EXTRA_USERNAME, parseObjects.get(0).get("User_name").toString());
                        startActivity(adminIntent);

                        alert.setText("Login Successful!");
                    }else{
                        //starts user activity
                        userIntent.putExtra(EXTRA_USERNAME, parseObjects.get(0).get("User_name").toString());
                        userIntent.putExtra("objectId", parseObjects.get(0).getObjectId());
                        startActivity(userIntent);
                    }
                }else{
                    Log.d("Login", username.getText().toString() + " " + password.getText().toString());
                    Log.d("Login", "Incorrect username or password");
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        alert.setText("Logged out Successfully!");
    }
}
