package com.example.patrick.tasktracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Patrick on 11/11/2014.
 */
public class QueryAdapterUserJobView extends ParseQueryAdapter<ParseObject> {
    public QueryAdapterUserJobView(Context context,final String objectId) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("WorkOrder");
                query.include("Department");
                query.include("Location");
                query.whereEqualTo("objectId", objectId);
                return query;
            }
        });
    }
    /*
    @Override
    public View getItemView(ParseObject parseobject, View v, ViewGroup parent) {
        if (v==null){
            v = View.inflate(getContext(), R.layout.row_ppl, null);
        }

        super.getItemView(parseobject, v, parent);
        ParseObject city = parseobject.getParseObject("City");
        ParseObject country = City.getParseObject("Country");
        String continent = country.getString("Continent");

        TextView nome = (TextView) v.findViewById(R.id.textView1);
        nome.setText(parseobject.getString("Name"));
        TextView cont = (TextView) v.findViewById(R.id.textView2);
        cont.setText(continent);

        return v;
        */
    }
