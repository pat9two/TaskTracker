package com.example.patrick.tasktracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/14/2014.
 */
public class QueryAdapterEmployee extends ParseQueryAdapter<ParseObject> {
    public QueryAdapterEmployee(Context context, QueryFactory<ParseObject> factory) {
        super(context, factory);
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.employee_list_item, null);
            holder = new ViewHolder();
            holder.title = (TextView)v.findViewById(R.id.emp_lv_item_title);
            holder.title.setText(parseobject.get("First_name").toString() + " " + parseobject.get("Last_name").toString());
            holder.subTitle = (TextView)v.findViewById(R.id.emp_lv_item_subTitle);
            holder.subTitle.setText(parseobject.get("User_name").toString());
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        return v;
    }


    private class ViewHolder{
        TextView title;
        TextView subTitle;
    }

}
