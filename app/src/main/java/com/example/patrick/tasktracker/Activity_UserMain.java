package com.example.patrick.tasktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;

import java.util.List;

/**
 * Created by Shwaat on 11/3/2014.
 */
public class Activity_UserMain extends ActionBarActivity {
    ListView userWorkOrderListView;
    ParseQueryAdapter<ParseObject> mainAdapter;
    String username;
    String objectId;
    final Context context = this;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Parse.initialize(this, "6yEsCcvYy5ym7rmRKWleVy5A9jc2wHFz6aEL3Czs", "t3h3S0090VVBwdw0zasj5J0b28dLe9xebL5nIfKw");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        Intent intent = getIntent();
        username = intent.getStringExtra(Activity_LoginMain.EXTRA_USERNAME);
        objectId = intent.getStringExtra("objectId");

        Log.d("UserMain", ""+objectId);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Employee");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
                if(e == null){
                    ParseQueryAdapter.QueryFactory<ParseObject> woQuery = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {
                            final ParseQuery<ParseObject> workorderQuery = ParseQuery.getQuery("WorkOrder");

                            ParseQuery<ParseObject> factoryquery = ParseQuery.getQuery("WorkOrder_Employee");
                            factoryquery.whereEqualTo("employee", parseObject.getObjectId());
                            factoryquery.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> parseObjects, ParseException e) {
                                    for(ParseObject po : parseObjects){
                                        workorderQuery.whereEqualTo("objectId", po.getObjectId());
                                    }
                                }
                            });
                            return workorderQuery;
                        }
                    };

                    mainAdapter = new ParseQueryAdapter<ParseObject>(context, woQuery);

                    //not used yet. adapter to add multiple elements to a listitem.
                    //adapter = new AdminuserWorkOrderListViewAdapter(this);

                    userWorkOrderListView = (ListView)findViewById(R.id.user_employee_list_view);
                    mainAdapter.setTextKey("description");
                    userWorkOrderListView.setAdapter(mainAdapter);

                    mainAdapter.loadObjects();

                    userWorkOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ParseObject po = (ParseObject) parent.getItemAtPosition(position);

                            Intent intent = new Intent(view.getContext(), Activity_UserJobView.class);
                            intent.putExtra("workOrderId", po.getObjectId());
                            intent.putExtra("userId", objectId);
                            Log.d("UserMainWorkorders", "WorkOrder:" + po.getObjectId() + " Employee: " + objectId);
                            startActivity(intent);
                        }
                    });
                }else{
                    Log.d("UserMain", e.toString());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.admin_wo_refresh_item:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
