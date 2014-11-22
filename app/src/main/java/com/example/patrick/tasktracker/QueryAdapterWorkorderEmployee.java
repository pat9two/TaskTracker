package com.example.patrick.tasktracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Shwaat on 11/22/2014.
 */
public class QueryAdapterWorkorderEmployee extends ParseQueryAdapter<ParseObject> {
    public QueryAdapterWorkorderEmployee(Context context, ParseQueryAdapter.QueryFactory<ParseObject> factory) {
        super(context, factory);
    }

    @Override
    public View getItemView(final ParseObject workorderObject, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.admin_wo_employee_listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView)v.findViewById(R.id.admin_wo_emp_name);
            holder.name.setText(workorderObject.getParseObject("employee").get("First_name").toString() + " " + workorderObject.getParseObject("employee").get("Last_name").toString());
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        return v;
    }




    private class ViewHolder{
        TextView name;
    }
}
