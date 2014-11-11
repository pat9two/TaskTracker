package com.example.patrick.tasktracker;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;

import java.util.List;


/**
 * Created by Patrick on 11/10/2014.
 */
public class AdminDepartmentListViewAdapter extends ParseQueryAdapter<ParseObject> {


    public AdminDepartmentListViewAdapter(Context context){
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>(){
            public ParseQuery create(){
                ParseQuery query = new ParseQuery("Department");
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent){
        if(v == null){
            v = View.inflate(getContext(), R.layout.admin_department_listitem, null);
        }

        super.getItemView(object, v, parent);

        //get department name
        TextView Department_name = (TextView)v.findViewById(R.id.deparment_name);
        Department_name.setText(object.getString("Department_id"));

        //get number of locations in this department
        final TextView Num_of_locations = (TextView)v.findViewById(R.id.number_of_locations);

        ParseRelation<ParseObject> relation = object.getRelation("locations");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.whereEqualTo("Department_id", object.get("Department_id"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null && parseObjects.size() != 0){
                    Num_of_locations.setText(parseObjects.get(0).toString());
                }
            }
        });

        return v;
    }
}
