package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    String DepObjectId;
    ParseQueryAdapter<ParseObject> mainAdapter;
    EditText locName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_department_loc_info);

        Intent intent = getIntent();
        DepObjectId = intent.getStringExtra("extra");


        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery create() {
                        ParseQuery query = new ParseQuery("Location");
                        query.include("Department");
                        query.whereEqualTo("parent", DepObjectId);
                        return query;
                    }
                };
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, factory);
        mainAdapter.setTextKey("Location_id");

        mainAdapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<ParseObject>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(List<ParseObject> parseObjects, Exception e) {

            }
        });
        listView = (ListView)findViewById(R.id.dept_loc_view);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();
    }
    public void addLoc(View view){
        locName = (EditText)findViewById(R.id.new_dept_field);
        if(CheckFields()) {
            //ParseObject.createwithoutData("Department", DepObjectId);
            ParseObject po = ParseObject.createWithoutData("Department", DepObjectId);
            ParseObject parseObject = new ParseObject("Location");
            parseObject.put("Location_id", locName.getText().toString().trim());
            parseObject.put("parent", po);
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        //refreshes current activity.
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }
    }
    public Boolean CheckFields(){
       
            if(locName.getText().toString().trim().isEmpty()){
                //Field cannot be left blank
                locName.setError("Field cannot be left blank");
                return false;
            }

        //All statements passed.
        return true;
    }
}
