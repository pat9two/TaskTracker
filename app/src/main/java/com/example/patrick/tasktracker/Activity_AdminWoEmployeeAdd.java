package com.example.patrick.tasktracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_AdminWoEmployeeAdd extends ActionBarActivity {
    String workorderId;
    QueryAdapterWorkorderEmployeeAdd adapter;
    ArrayList<ParseObject> listToAdd = new ArrayList<ParseObject>();
    ListView listview;
    ParseObject wo;
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee_add);

        Intent intent = getIntent();
        workorderId = intent.getStringExtra("extra");

        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Employee");
                return query;
            }
        };
        adapter = new QueryAdapterWorkorderEmployeeAdd(this, factory, listToAdd);
        listview = (ListView)findViewById(R.id.admin_wo_add_emp_listView);
        listview.setAdapter(adapter);
        adapter.loadObjects();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox)view.findViewById(R.id.admin_wo_emp_add_checkbox);
                ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                if(!cb.isChecked()) {
                    cb.setChecked(true);
                    Log.d("WoEmpAdd", "Added to list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToAdd.add(po);
                }else{
                    cb.setChecked(false);
                    Log.d("WoEmpAdd", "Removed from list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToAdd.remove(po);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void addEmployee(final View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("WorkOrder");
        query.getInBackground(workorderId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject workOrder, ParseException e) {
                if(e == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Assign Employee: Are you sure?").setTitle("Warning");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {
                            //Iterate through the list and add to the workorder relation "assignedEmployees"
                            ParseRelation<ParseObject> relation = workOrder.getRelation("assignedEmployees");
                            for(ParseObject po : listToAdd){
                                relation.add(po);
                            }
                            workOrder.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        dialog.cancel();
                                    }
                                }
                            });

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
            }
        });


    }
    public void cancelAction(View view)
    {
        finish();
    }
}
