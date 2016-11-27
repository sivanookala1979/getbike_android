package com.vave.getbike.adapter;

/**
 * Created by sivanookala on 27/11/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.model.Ride;

import java.util.List;

public class RideAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;
    private Ride tempRide = null;
    private List<Ride> data;

    public RideAdapter(Activity activity, List<Ride> d, Resources resLocal) {
        data = d;
        res = resLocal;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        ViewHolder holder;

        if (convertView == null) {
            result = inflater.inflate(R.layout.ride_list_item, null);
            holder = new ViewHolder();
            holder.rideId = (TextView) result.findViewById(R.id.rideId);
            holder.requestorName = (TextView) result.findViewById(R.id.rideRequestedBy);
            result.setTag(holder);
        } else
            holder = (ViewHolder) result.getTag();

        tempRide = data.get(position);
        holder.rideId.setText(tempRide.getId() + "");
        holder.requestorName.setText(tempRide.getRequestorName());
        return result;
    }

    public static class ViewHolder {

        public TextView rideId;
        public TextView requestorName;

    }
}
