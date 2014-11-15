package com.example.patrick.tasktracker;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;
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

    public QueryAdapterDeptRemoveLoc(Context context, QueryFactory<ParseObject> factory) {
        super(context, factory);
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        if (v == null) {
            //this is the layout file that contains a Relative layout containing a TextView and Checkbox element.
            v = View.inflate(getContext(), R.layout.remove_loc_listview_item, null);
        }
        super.getItemView(parseobject, v, parent);

        TextView location = (TextView) v.findViewById(R.id.remove_loc_lv_item_title);
        location.setText(parseobject.getString("Location_id"));
        CheckBox checkbox = (CheckBox) v.findViewById(R.id.remove_loc_checkbox);

        return v;
    }

}
