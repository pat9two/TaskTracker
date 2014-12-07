package com.example.patrick.tasktracker;

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
import android.app.Activity;
import com.parse.DeleteCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Created by Shwaat on 12/7/2014.
 */
public class Activity_DepartmentRem extends Activity {
    QueryAdapterDeptRemove adapter;
    ListView listview;
    ArrayList<ParseObject> listToRemove = new ArrayList<ParseObject>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_department_remove);
        ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Department");
                return query;
            }
        };
        adapter = new QueryAdapterDeptRemove(this, factory, listToRemove);
        listview = (ListView)findViewById(R.id.admin_rem_dept_listView);
        listview.setAdapter(adapter);
        adapter.loadObjects();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox)view.findViewById(R.id.admin_rem_dept_checkbox);
                ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                if(!cb.isChecked()) {
                    cb.setChecked(true);
                    Log.d("DeptRemove", "Added to list: " + po.getString("Department_id"));
                    listToRemove.add(po);
                }else{
                    cb.setChecked(false);
                    Log.d("DeptRemove", "Removed from list: " + po.getString("Department_id"));
                    listToRemove.remove(po);
                }
            }
        });
    }

    public void removeDepartment(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Department: Are you sure?").setTitle("Warning");
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
                            CharSequence text = "Department(s) Removed.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            // END REMOVAL TOAST
                        } else {
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Something went wrong...";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            Log.d("DepartmentRemove", e.toString());
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
    public void cancelAction(View view)
    {
        finish();
    }
}
