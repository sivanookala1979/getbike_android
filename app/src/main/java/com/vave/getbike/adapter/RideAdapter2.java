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

public class RideAdapter2 extends BaseAdapter {

    private ArrayList<SearchResults> searchArrayList;
    Context context;

    private LayoutInflater mInflater;

    public RideAdapter2(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
        this.context = context;
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
        String indianRupee = context.getResources().getString(R.string.Rs);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ride_history_item, null);
            holder = new ViewHolder();
            holder.rideId = (TextView) convertView.findViewById(R.id.ride_id);
            holder.fromAddress = (TextView) convertView.findViewById(R.id.fromAddress);
            holder.toAddress = (TextView) convertView.findViewById(R.id.toAddress);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.rideDateTime = (TextView) convertView.findViewById(R.id.rideDateTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rideId.setText(searchArrayList.get(position).getRideId());
        //TODO NEED TO GET FROM OBJECT
        holder.price.setText(indianRupee + "0.0");
//        holder.fromAddress.setText(searchArrayList.get(position).getRequesterName());
//        holder.toAddress.setText(searchArrayList.get(position).getRequestedTime());
//        holder.rideDateTime.setText(searchArrayList.get(position).getRequestedTime());

        return convertView;
    }

    static class ViewHolder {

        TextView rideId;
        TextView fromAddress;
        TextView toAddress;
        TextView price;
        TextView rideDateTime;


    }
}
