package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_UserMain extends Activity {
    ListView userWorkOrderListView;
    ParseQueryAdapter<ParseObject> mainAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);


        Log.d("UserMain", "main");



        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "WorkOrder");
        mainAdapter.setTextKey("WorkOrder_id");

        //not used yet. adapter to add multiple elements to a listitem.
        //adapter = new AdminuserWorkOrderListViewAdapter(this);

        userWorkOrderListView = (ListView)findViewById(R.id.user_employee_list_view);
        userWorkOrderListView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        userWorkOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Activity_UserJobView.class);
                intent.putExtra("extra", po.getObjectId());
                Log.d("UserMainWorkorders", " " +po.getObjectId());
                startActivity(intent);
            }
        });
    }
}
