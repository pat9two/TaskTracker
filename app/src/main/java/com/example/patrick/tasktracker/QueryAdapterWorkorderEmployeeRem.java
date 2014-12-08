package com.example.patrick.tasktracker;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;


public class QueryAdapterWorkorderEmployeeRem extends ParseQueryAdapter<ParseObject> {
    ArrayList<ParseObject> listToRem;
    public QueryAdapterWorkorderEmployeeRem(Context context, ParseQueryAdapter.QueryFactory<ParseObject> factory,ArrayList<ParseObject> listToRemove) {
        super(context, factory);
        this.listToRem = listToRemove;
    }

    @Override
    public View getItemView(final ParseObject workorderObject, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.admin_wo_employee_add_listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView)v.findViewById(R.id.admin_wo_emp_add_name);
            holder.name.setText(workorderObject.getParseObject("employee").get("First_name").toString() + " " + workorderObject.getParseObject("employee").get("Last_name").toString());
            holder.checkbox = (CheckBox)v.findViewById(R.id.admin_wo_emp_add_checkbox);
            final CheckBox checkbox = holder.checkbox;
            v.setTag(holder);

            //listener to run when user presses on the checkbox.
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = checkbox.isChecked();
                    if(isChecked) {
                        Log.d("WoEmpAdd", "Added to list: " + workorderObject.getString("First_name") + " " + workorderObject.getString("Last_name"));

                        listToRem.add(workorderObject);
                        checkbox.setChecked(true);
                    }else{
                        Log.d("WoEmpAdd", "Removed from list: " + workorderObject.getString("First_name") + " " + workorderObject.getString("Last_name"));
                        listToRem.remove(workorderObject);
                        checkbox.setChecked(false);
                    }
                }
            });
        }else{
            holder = (ViewHolder)v.getTag();
        }
        return v;
    }




    private class ViewHolder{
        TextView name;
        CheckBox checkbox;
    }
}
