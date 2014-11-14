package com.example.patrick.tasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "WorkOrder");
        mainAdapter.setTextKey("WorkOrder_id");

        //not used
        //adapter = new AdminworkOrderListViewAdapter(this);

        workOrderListView = (ListView)findViewById(R.id.job_list_view);
        workOrderListView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

//Needs AdminWoInfo java class...
      /*  workOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject po = (ParseObject)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Activity_AdminWoInfo.class);
                intent.putExtra("extra", po.getObjectId());
                Log.d("AdminWorkOrders", " " + po.getObjectId());
                startActivity(intent);
            }
        }); */
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.admin_wo_create_item:
                Intent intent = new Intent(this, Activity_AdminWoNew.class);
                this.startActivity(intent);
                break;
            case R.id.admin_wo_search_item:
                //display search bar.
                break;
        }
        return true;
    }

    public void runSearch(){
        EditText SearchCriteria = (EditText)findViewById(R.id.search_job_field);
        String searchTerm = SearchCriteria.getText().toString().trim();
        //search all tables for the searchcritera. limited to departments locations, orders with employees assigned.

        //search departments

        //search locations

        //search workorder id

        //search employees

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wo_view_actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }
}