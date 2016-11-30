package com.vave.getbike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.model.SearchResults;

import java.util.ArrayList;

/**
 * Created by RAM on 11/26/2016.
 */

public class MyCustomBaseAdapter extends BaseAdapter{
    private static ArrayList<SearchResults> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.rideId = (TextView) convertView.findViewById(R.id.ride_id);
            holder.requestorName = (TextView) convertView.findViewById(R.id.requester_name);
            holder.requestedTime = (TextView) convertView.findViewById(R.id.requested_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rideId.setText(searchArrayList.get(position).getRideId());
        holder.requestorName.setText(searchArrayList.get(position).getRequesterName());
        holder.requestedTime.setText(searchArrayList.get(position).getRequestedTime());

        return convertView;
    }

    static class ViewHolder {
        TextView rideId;
        TextView requestorName;
        TextView requestedTime;
    }
}
