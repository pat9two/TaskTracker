package com.example.patrick.tasktracker;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    public void dropEntry(View view){

        rowlist = (TextView)findViewById(R.id.list);
        Employee em = db.getEmployee(1);
        Log.d("Dropping", em.First_name);
        rowlist.setText(String.valueOf(db.getEmployeesCount()));
       // db.deleteEmployee(em);
    }
    public void addRow(View view){
        int i = 0;
        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        db.addEmployee(new Employee("pm01789", "2ndDan!912", textbox.getText().toString(), "Marino", "true"));
        SQLiteDatabase rdb = db.getWritableDatabase();

        textview = (TextView)findViewById(R.id.textbox);
        Cursor cursor = rdb.rawQuery("Select First_name FROM Employee",null);

        cursor.moveToFirst();
        textview.setText(cursor.getString(i));
        i++;
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
