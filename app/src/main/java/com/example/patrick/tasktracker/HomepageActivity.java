package com.example.patrick.tasktracker;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;


import java.util.List;


public class HomepageActivity extends Activity {

    /* Testing purposes only */
    //////////////////////////

    TextView textview;
    EditText textbox;
    TextView rowlist;
    EditText selectId;


    public void dropEntry(View view){
        selectId = (EditText)findViewById(R.id.selectId);
        rowlist = (TextView)findViewById(R.id.list);
        DatabaseHandler db = new DatabaseHandler(this);

        if(db.getEmployeesCount() >0){
            Employee em = db.getEmployee(db.getEmployeesCount());
            Log.d("Dropping", em.getFirst_name());
            db.deleteEmployee(em);
            rowlist.setText("");
            for(int i = 1; i <= db.getEmployeesCount(); i++){
                rowlist.append(db.getEmployee(i).getFirst_name() + "\n");
            }
        }
    }
    public void addRow(View view){

        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        Employee em = new Employee("pm01789", "password", textbox.getText().toString(), "Marino", "1");
        db.addEmployee(em);

        textview = (TextView)findViewById(R.id.textbox);
        textview.setText(String.valueOf(em.getEagle_id()));
    }
    //////////////////////////////////
    /* Testing purposes only. */

    public void onClick(View view){
        DatabaseHandler db = new DatabaseHandler(this);

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
       // ParseObject testObject = new ParseObject("TestObject");
       // testObject.put("foo", "bar");
       // testObject.saveInBackground();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    }


}
