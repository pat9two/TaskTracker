package com.example.patrick.tasktracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_EmployeeRemove extends Activity {
    QueryAdapterEmpRemove adapter;
    ListView listview;
    ArrayList<ParseObject> listToRemove = new ArrayList<ParseObject>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        //set xml layout
        setContentView(R.layout.admin_employee_remove);

        //query factory to be used by adapter. gets all employee objects.
        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Employee");
                return query;
            }
        };
        //custom adapter to show employee with a checkbox in each listitem.
        adapter = new QueryAdapterEmpRemove(this, factory, listToRemove);
        listview = (ListView)findViewById(R.id.emp_rem_list_view);
        listview.setAdapter(adapter);
        adapter.loadObjects();

        //set listener for when user presses on a listitem or the checkbox in order to add or remove an employee from being removed.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox)view.findViewById(R.id.admin_rem_emp_checkbox);
                ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                if(!cb.isChecked()) {
                    cb.setChecked(true);
                    Log.d("EmpRemove", "Added to list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToRemove.add(po);
                }else{
                    cb.setChecked(false);
                    Log.d("EmpRemove", "Removed from list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToRemove.remove(po);
                }
            }
        });
    }

    //button on click method. removes all employees contained in listtoremove.
    public void removeEmployee(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Employee: Are you sure?").setTitle("Warning");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Remove loc
                ParseObject.deleteAllInBackground(listToRemove, new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //refreshes current activity.
                            Intent intent = getIntent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(intent);

                            // START REMOVAL TOAST
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Employee(s) Removed.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            // END REMOVAL TOAST
                        } else {
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Something went wrong...";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            Log.d("EmployeeRemove", e.toString());
                        }
                    }
                });
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //button on click method. does nothing and returns to previous activity.
    public void cancelAction(View view)
    {
        finish();
    }
}
