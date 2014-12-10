package com.example.patrick.tasktracker;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by Shwaat on 11/14/2014.
 */
public class QueryAdapterDeptRemoveLoc extends ParseQueryAdapter<ParseObject> {
    ArrayList<ParseObject> listToRemove;
    ArrayList<ParseObject> removeList = new ArrayList<ParseObject>();
    public QueryAdapterDeptRemoveLoc(Context context, QueryFactory<ParseObject> factory,ArrayList<ParseObject> listToRemove) {
        super(context, factory);
        this.listToRemove = listToRemove;
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.remove_loc_listview_item, null);
            holder = new ViewHolder();
            holder.location = (TextView)v.findViewById(R.id.remove_loc_lv_item_title);
            holder.location.setText(parseobject.get("Location_id").toString());
            holder.checkbox = (CheckBox)v.findViewById(R.id.remove_loc_checkbox);
            final CheckBox checkbox = holder.checkbox;
            v.setTag(holder);

            //listener to run when user presses on checkbox.
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = checkbox.isChecked();
                    if(isChecked) {
                        Log.d("DepRemLoc", "Added to list: " + parseobject.get("Location_id"));

                        listToRemove.add(parseobject);
                        checkbox.setChecked(true);
                    }else{
                        Log.d("DepRemLoc", "Removed from list: " + parseobject.get("Location_id"));
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
        TextView location;
        CheckBox checkbox;
    }

}
