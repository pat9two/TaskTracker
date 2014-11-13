package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.security.Timestamp;
import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_UserJobView extends Activity {
    TextView depValue;
    TextView locValue;
    TextView descValue;
    TextView dateValue;
    Long start;
    Long stop;
    Long elapsed;
    Long paused;
    Long resumed;
    Boolean startPressed = false;
    Boolean pausePressed = false;
    String workOrderId;
    ParseQuery<ParseObject> query;
    ParseObject po;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        Intent intent = getIntent();
        workOrderId = intent.getStringExtra("");

        depValue = (TextView)findViewById(R.id.user_view_department_value);
        locValue = (TextView)findViewById(R.id.user_view_location_value);
        descValue= (TextView)findViewById(R.id.user_view_workDesc_value);
        dateValue = (TextView)findViewById(R.id.user_view_schedule_value);

        query = ParseQuery.getQuery("WorkOrder");
        query.include("Department");
        query.include("Location");
        query.whereEqualTo("objectId", workOrderId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null){
                    po = parseObjects.get(0);
                    depValue.setText(parseObjects.get(0).getString("Department_id"));
                    locValue.setText(parseObjects.get(0).getString("Location_id"));
                    descValue.setText(parseObjects.get(0).getString("description"));
                    dateValue.setText(parseObjects.get(0).getString("scheduleDate"));
                }
            }
        });
    }

    public void Start(){
        if(!startPressed) {
            start = System.currentTimeMillis();
            startPressed = true;

            //put this time started into the relational table of employee -- workorder (many to many)
        }
    }
    public void Stop(){
        if(startPressed ) {

            stop = System.currentTimeMillis();
            elapsed = resumed - start;
            int hours = (int) (elapsed / 3600000);
            int minutes = (int) (elapsed - hours * 3600000) / 60000;
            int seconds = (int) (elapsed - hours * 3600000 - minutes * 60000) / 1000;

            //put this time elapsed and time stopped into the relational table of employee -- workorder (many to many)
        }//else do nothing.
    }
    public void Pause(){
        if(startPressed){
            paused = System.currentTimeMillis();
            elapsed = paused - start;
            pausePressed = true;
        }
    }
    public void Resume(){
        if(pausePressed){
            resumed = System.currentTimeMillis();
            elapsed += resumed - paused;
        }
    }
}
