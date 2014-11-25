package com.example.patrick.tasktracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_AdminWoEmployeeAdd extends Activity {
    String workorderId;
    QueryAdapterWorkorderEmployeeAdd adapter;
    ArrayList<ParseObject> listToAdd = new ArrayList<ParseObject>();
    ListView listview;
    Context context = this;
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee_add);

        Intent intent = getIntent();
        workorderId = intent.getStringExtra("extra");
        ParseQuery<ParseObject> wo_empQuery = new ParseQuery<ParseObject>("WorkOrder_Employee");
        wo_empQuery.include("employee");
        wo_empQuery.include("workorder");
        wo_empQuery.whereEqualTo("workorder", ParseObject.createWithoutData("WorkOrder", workorderId));
        wo_empQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    final ArrayList<String> empList = new ArrayList<String>();
                    for(ParseObject po: parseObjects){
                        empList.add(po.getParseObject("employee").getObjectId());
                    }
                    ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {
                            //find all employees not currently assigned to this work order.
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Employee");
                            query.whereNotContainedIn("objectId", empList);
                            return query;
                        }
                    };
                    adapter = new QueryAdapterWorkorderEmployeeAdd(context, factory, listToAdd);
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
            }
        });

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
                            for(ParseObject po : listToAdd){
                                ParseObject workorder_employee = new ParseObject("WorkOrder_Employee");
                                workorder_employee.put("workorder", workOrder);

                                workorder_employee.put("employee",po);
                                Log.d("WoEmployeeAdd", po.getString("First_name"));
                                workorder_employee.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null) {
                                            dialog.cancel();
                                        }
                                    }
                                });
                            }
                            finish();


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
