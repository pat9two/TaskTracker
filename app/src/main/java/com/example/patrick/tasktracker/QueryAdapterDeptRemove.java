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
public class QueryAdapterDeptRemove extends ParseQueryAdapter<ParseObject> {
    ArrayList<ParseObject> listToRemove;
    public QueryAdapterDeptRemove(Context context, QueryFactory<ParseObject> factory,ArrayList<ParseObject> listToRemove) {
        super(context, factory);
        this.listToRemove = listToRemove;
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.admin_dept_remove_listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView)v.findViewById(R.id.admin_rem_dept_name);
            holder.name.setText(parseobject.get("Department_id").toString());
            holder.checkbox = (CheckBox)v.findViewById(R.id.admin_rem_dept_checkbox);
            final CheckBox checkbox = holder.checkbox;
            v.setTag(holder);

            //check box listener to run when the user pressed on the checkbox.
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = checkbox.isChecked();
                    if(isChecked) {
                        Log.d("DeptRemove", "Added to listToRemove: " + parseobject.getString("Department_id"));

                        listToRemove.add(parseobject);
                        checkbox.setChecked(true);
                    }else{
                        Log.d("DeptRemove", "Removed from listToRemove: " + parseobject.getString("Department_id"));
                        listToRemove.remove(parseobject);
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
