package com.example.patrick.tasktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.List;

import bolts.Task;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_DepartmentLocInfo extends Activity {
    ListView listView;
    Department DepObject;
    ParseQueryAdapter<ParseObject> mainAdapter;
    EditText locName;
    TextView isCharged, deptName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //line to allow activity to use parse database.
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");
        final Context context = this;
        super.onCreate(savedInstanceState);
        //xml file that is used for the activity.
        setContentView(R.layout.admin_department_loc_info);
        //gets the data that was sent from a previous activity.
        Intent intent = getIntent();
        DepObject = intent.getParcelableExtra("extra");
        final ParseObject po = new ParseObject("Department");
        //setting the xml elements to local
        deptName = (TextView) findViewById(R.id.dept_loc_main_title);
        isCharged = (TextView) findViewById(R.id.dept_loc_is_charged);

        //show the name of the department in the app.
        deptName.append("" + DepObject.getDepartment_name());


        po.put("objectId", DepObject.getSync_id());
        po.put("Department_id", DepObject.getDepartment_name());
        po.put("Charged", DepObject.getChargedStatus());
        Log.d("DepLocInfo", DepObject.getSync_id() + " " + DepObject.getDepartment_name() + " " + DepObject.getChargedStatus());

        //show if the department is charged or not.
        if(po.getString("Charged").contentEquals("1"))
        {
            isCharged.append("Yes");
        }
        else
        {
            isCharged.append("No");
        }

        //querys the parse database for the specific department row and then downloads to the app in a background thread.
        ParseQuery<ParseObject> depquery = new ParseQuery<ParseObject>("Department");
        depquery.whereEqualTo("objectId", DepObject.getSync_id());
        depquery.getInBackground(DepObject.getSync_id(), new GetCallback<ParseObject>() {
            //when the background thread is done, this code block will be run.
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
                if (e == null) {
                    //this creates a parsequery for use by an adapter.
                    ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        public ParseQuery create() {
                            //gets all Locations for the Department that is being viewed in the app.
                            ParseQuery query = new ParseQuery("Location");
                            query.whereEqualTo("parent", parseObject);
                            return query;
                        }
                    };

                    //adapter for the ListView to show all locations associated with this department.
                    //uses the factory method from before.
                    mainAdapter = new ParseQueryAdapter<ParseObject>(context, factory);
                    //this sets the text that will be displayed in every listitem in the listview.
                    mainAdapter.setTextKey("Location_id");
                    //sets which xml element to populate with the data from the adapter.
                    listView = (ListView) findViewById(R.id.dept_loc_view);
                    listView.setAdapter(mainAdapter);
                    mainAdapter.loadObjects();
                }
            }
        });
    }

    //this code is ran when the app navigates back to this activity.
    @Override
    public void onResume()
    {
        //reloads the app to show updated values.
        super.onResume();
        if(mainAdapter != null) {
            mainAdapter.notifyDataSetChanged();
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            startActivity(intent);
        }
    }

    //method for on click button event.
    public void addLoc(View view) {
        locName = (EditText) findViewById(R.id.new_dept_field);
        if (CheckFields()) {

            ParseObject po = ParseObject.createWithoutData("Department", DepObject.getSync_id());
            ParseObject parseObject = new ParseObject("Location");
            parseObject.put("Location_id", locName.getText().toString().trim());
            parseObject.put("parent", po);
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    //if the object was not saved in the background, this code will not refresh the app.
                    if (e == null) {
                        //refreshes current activity.
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(intent);
                    }else{
                        //logs the exception.
                        Log.d("DepartmentLocInfo", e.toString());
                    }
                }
            });
        }
    }

    //field validation.
    public Boolean CheckFields() {

        if (locName.getText().toString().trim().isEmpty()) {
            //Field cannot be left blank
            locName.setError("Field cannot be left blank");
            return false;
        }

        //All statements passed.
        return true;
    }

    //mehthod for onclick button event to remove locations.
    public void viewRemoveLoc(View view) {
        Intent intent = new Intent(view.getContext(), Activity_DepartmentRemLoc.class);
        if(DepObject != null) {
            intent.putExtra("extra", DepObject);
        }
        else {
            DepObject = intent.getParcelableExtra("extra");
            intent.putExtra("extra", DepObject);
        }
        startActivity(intent);
    }
}
