package com.example.patrick.tasktracker;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Created by Patrick on 11/16/2014.
 */
public class QueryAdapterWorkorderEmployeeAdd extends ParseQueryAdapter<ParseObject> {
    ArrayList<ParseObject> listToAdd;
    public QueryAdapterWorkorderEmployeeAdd(Context context, QueryFactory<ParseObject> factory,ArrayList<ParseObject> listToAdd) {
        super(context, factory);
        this.listToAdd = listToAdd;
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.admin_wo_employee_add_listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView)v.findViewById(R.id.admin_wo_emp_add_name);
            holder.name.setText(parseobject.get("First_name").toString() + " " + parseobject.get("Last_name").toString());
            holder.checkbox = (CheckBox)v.findViewById(R.id.admin_wo_emp_add_checkbox);
            final CheckBox checkbox = holder.checkbox;
            v.setTag(holder);

            //listner to run when user presses on the chekbox.
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = checkbox.isChecked();
                    if(isChecked) {
                        Log.d("WoEmpAdd", "Added to list: " + parseobject.getString("First_name") + " " + parseobject.getString("Last_name"));

                        listToAdd.add(parseobject);
                        checkbox.setChecked(true);
                    }else{
                        Log.d("WoEmpAdd", "Removed from list: " + parseobject.getString("First_name") + " " + parseobject.getString("Last_name"));
                        listToAdd.remove(parseobject);
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
