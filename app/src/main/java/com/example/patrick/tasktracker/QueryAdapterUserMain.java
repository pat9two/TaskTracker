package com.example.patrick.tasktracker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Patrick on 11/22/2014.
 */
public class QueryAdapterUserMain extends  ParseQueryAdapter<ParseObject>{
    public QueryAdapterUserMain(Context context, ParseQueryAdapter.QueryFactory<ParseObject> factory) {
        super(context, factory);
    }

    @Override
    public View getItemView(final ParseObject parseobject, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.user_main_listitem, null);
            holder = new ViewHolder();
            holder.woNumber = (TextView)v.findViewById(R.id.user_main_wo_number);
            holder.woNumber.setText("#" + parseobject.getParseObject("workorder").get("workorderId").toString() + " ");
            holder.woDesc = (TextView)v.findViewById(R.id.user_main_wo_desc);
            holder.woDesc.setText(parseobject.getParseObject("workorder").getString("description"));
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        return v;
    }


    private class ViewHolder{
        TextView woNumber;
        TextView woDesc;
    }

}
