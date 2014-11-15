package com.example.patrick.tasktracker;

import java.util.ArrayList;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.DeleteCallback;

import java.util.List;

/**
 * Created by Shwaat on 11/9/2014.
 */
public class Activity_DepartmentRemLoc extends Activity {

    Department DepObject;
    ListView listView;
    Intent intent;
    TextView locName;
    QueryAdapterDeptRemoveLoc adapter;
    ArrayList<ParseObject> listToRemove = new ArrayList<ParseObject>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_department_remove_loc);

        Intent intent = getIntent();
        DepObject = intent.getParcelableExtra("extra");
        final ParseObject po = new ParseObject("Department");
        po.put("objectId", DepObject.getSync_id());
        po.put("Department_id", DepObject.getDepartment_name());
        po.put("Charged", DepObject.getChargedStatus());
        Log.d("DepLocInfo", DepObject.getSync_id() + " " + DepObject.getDepartment_name() + " " + DepObject.getChargedStatus());


        ParseQuery<ParseObject> depquery = new ParseQuery<ParseObject>("Department");
        depquery.whereEqualTo("objectId", DepObject.getSync_id());
        depquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery create() {
                        ParseQuery query = new ParseQuery("Location");
                        query.whereEqualTo("parent", parseObjects.get(0));
                        return query;
                    }
                };

                adapter = new QueryAdapterDeptRemoveLoc(context, factory);
                adapter.setTextKey("Location_id");
                listView = (ListView) findViewById(R.id.dept_rem_loc_view);
                listView.setAdapter(adapter);
                adapter.loadObjects();
            }
        });
    }

    public void addToList(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();

        if(view.getId() == R.id.remove_loc_checkbox)
        {
            if(checked)
            {
                // add to list to be removed
            }
        }
    }

    public void removeLoc(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Record: Are you sure?")
                .setTitle("Warning");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Remove loc
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
        /*
            for (int i=0; i<listToRemove.size(); i++)
            {
                ParseObject parseObject = listToRemove.get(i);
                parseObject.deleteInBackground(new DeleteCallback() {
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
                            CharSequence text = "Location Removed.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                            // END REMOVAL TOAST
                        }
                        else {
                            Context toastContext = getApplicationContext();
                            CharSequence text = "Something went wrong...";
                            int duration = Toast.LENGTH_SHORT;

                            Toast.makeText(toastContext, text, duration).show();
                        }

                    }
                });
            }*/
        }

    public void cancelAction(View view)
    {
        finish();
    }
}
