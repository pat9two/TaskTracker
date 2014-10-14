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


import java.util.List;


public class Homepage extends Activity {

    /* Testing purposes only */
    //////////////////////////

    TextView textview;
    EditText textbox;
    TextView rowlist;
    public void dropEntry(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase sdb = db.getWritableDatabase();
        rowlist = (TextView)findViewById(R.id.list);
        Employee em = db.getEmployee(1);
        Log.d("Dropping", em.First_name);
        rowlist.setText(String.valueOf(db.getEmployeesCount()));
        db.deleteEmployee(em);
    }
    public void addRow(View view){
        int i = 0;
        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        db.addEmployee(new Employee("pm01789", "2ndDan!912", textbox.getText().toString(), "Marino", "true"));
        SQLiteDatabase rdb = db.getReadableDatabase();

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    }


}
