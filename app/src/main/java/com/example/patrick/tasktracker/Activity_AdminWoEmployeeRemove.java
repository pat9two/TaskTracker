package com.example.patrick.tasktracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
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
public class Activity_AdminWoEmployeeRemove extends Activity {

    String workOrderId, empObjId;
    QueryAdapterWorkorderEmployeeRem adapter;
    ArrayList<ParseObject> listToRemove = new ArrayList<ParseObject>();
    ListView listview;
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_employee_remove);
        Intent intent = getIntent();
        workOrderId = intent.getStringExtra("extra");

        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> wo_emp = new ParseQuery<ParseObject>("WorkOrder_Employee");
                wo_emp.include("employee");
                wo_emp.whereEqualTo("workorder", ParseObject.createWithoutData("WorkOrder", workOrderId));
                return wo_emp;
            }
        };
        adapter = new QueryAdapterWorkorderEmployeeRem(this, factory, listToRemove);
        listview = (ListView)findViewById(R.id.admin_wo_rem_emp_listView);
        listview.setAdapter(adapter);
        adapter.loadObjects();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox)view.findViewById(R.id.admin_wo_emp_add_checkbox);
                ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                if(!cb.isChecked()) {
                    cb.setChecked(true);
                    Log.d("WoEmpRem", "Added to list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToRemove.add(po);
                }else{
                    cb.setChecked(false);
                    Log.d("WoEmpRem", "Removed from list: " + po.getString("First_name") + " " + po.getString("Last_name"));
                    listToRemove.remove(po);
                }
            }
        });
    }

    public void remEmployee(final View view){
        if(listToRemove.size() > 0 && listToRemove != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Remove Employee: Are you sure?").setTitle("Warning");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, int id) {
                    //Iterate through the list and add to the workorder relation "assignedEmployees"
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
                        CharSequence text = "Employees Removed.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(toastContext, text, duration).show();
                        // END REMOVAL TOAST
                    } else {
                        Context toastContext = getApplicationContext();
                        CharSequence text = "Something went wrong...";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(toastContext, text, duration).show();
                        Log.d("WoEmployeeRemove", e.toString());
                    }
                        }
                    });
                    dialog.cancel();
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
        else {
            Context toastContext = getApplicationContext();
            CharSequence text = "Please choose at least one item.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(toastContext, text, duration).show();
        }
    }

    public void cancelAction(View view)
    {
        finish();
    }
}
