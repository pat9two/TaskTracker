package com.example.patrick.tasktracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


import java.util.List;


public class Homepage extends Activity {
    TextView textview;
    Button button;
    EditText textbox;

    public void addRow(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        textbox = (EditText)findViewById(R.id.entry);
        db.addEmployee(new Employee("pm01789", "2ndDan!912", textbox.getText().toString(), "Marino", "true"));
        textview = (TextView)findViewById(R.id.textbox);
        textview.setText(String.valueOf(db.getEmployee(0).getEagle_id() + " " +  db.getEmployee(0).getFirst_name()));


    }
    public void onClick(View view){
        DatabaseHandler db = new DatabaseHandler(this);

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        DatabaseHandler db = new DatabaseHandler(this);

        Log.d("Insert: ", "Inserting...");
        db.addEmployee(new Employee("pm01789","2ndDan!912", "Patrick", "Marino", "true"));

        Log.d("Reading: ", "Reading all contacts..");
        List<Employee> employees = db.getAllEmployees();

        for(int i = 0; i < employees.size(); i++){
            String log = "Id: " + employees.get(i).getEagle_id()+" ,Name: " + employees.get(i).getFirst_name() + " " + employees.get(i).getLast_name();
            Log.d("Name: ",log);
        }
  /*
        for(Employee em : employees){
            String log = "Id: " + em.getEagle_id()+" ,Name: " + em.getFirst_name() + " " + em.getLast_name();
            Log.d("Name: ",log);
        }
  */
    }


}
