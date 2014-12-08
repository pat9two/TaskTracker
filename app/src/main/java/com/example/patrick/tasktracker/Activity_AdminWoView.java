package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_AdminWoView extends ActionBarActivity {
    ParseQueryAdapter<ParseObject> mainAdapter;
    ListView workOrderListView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_wo_view);
        //create adapter to populate listview
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "WorkOrder");
        //the value that will be shown is the TextKey.
        mainAdapter.setTextKey("WorkOrder_id");

        workOrderListView = (ListView)findViewById(R.id.job_list_view);
        mainAdapter.setTextKey("description");
        workOrderListView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        //when a user presses on the listitem, it will navigate to the work order info activity
        workOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Activity_AdminWoInfo.class);
                intent.putExtra("extra", po.getObjectId());
                Log.d("AdminWorkOrders", " " + po.getObjectId());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_view_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.admin_wo_create_item:
                woCreateOption();
                return true;
            case R.id.admin_wo_assign_emp_item:
                // do create wo employees stuff
                return true;
            case R.id.admin_wo_refresh_item:
                refreshActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //button on click method to create new work order
    public void woCreateOption(){
        Intent intent = new Intent(this, Activity_AdminWoNew.class);
        startActivity(intent);
    }

    public void refreshActivity()
    {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}