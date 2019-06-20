package com.webakruti.railwaytemperature.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webakruti.railwaytemperature.R;
import com.webakruti.railwaytemperature.model.DeviceDetails;

import java.io.Serializable;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by DELL on 5/15/2019.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Activity context;
    List<DeviceDetails.Datum> list;
    int size;



    public HomeAdapter(Activity context, List<DeviceDetails.Datum> list) {
        this.context = context;
        //this.list = list;
        this.list = list;


    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_adapter, viewGroup, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder viewHolder, final int position) {

        final DeviceDetails.Datum devicelist =  list.get(position);

        viewHolder.textViewTrainName.setText(devicelist.getTrainName());
        viewHolder.textViewCoachNoName.setText(devicelist.getCoachName());
        viewHolder.textViewDeviceId.setText(devicelist.getDeviceid());
        viewHolder.textViewInternalTemp.setText(devicelist.getTemperature());
        viewHolder.textViewExternalTemp.setText(devicelist.getExternalTemp());
        viewHolder.textViewDate.setText(devicelist.getDate());
        viewHolder.textViewTime.setText(devicelist.getTime());

        if(devicelist.getStatus().equalsIgnoreCase("1")) {
            viewHolder.textViewRailwayStatus.setText("Active");
            viewHolder.textViewRailwayStatus.setTextColor(context.getResources().getColor(R.color.green_fluro));
        }else{
            viewHolder.textViewRailwayStatus.setText("Inactive");
            viewHolder.textViewRailwayStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTrainName;
        private TextView textViewRailwayStatus;
        private TextView textViewCoachNoName;
        private TextView textViewDeviceId;
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewInternalTemp;
        private TextView textViewExternalTemp;
        private RelativeLayout relativeLayoutViewDetails;


        private CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardView);
            textViewTrainName = (TextView)itemView.findViewById(R.id.textViewTrainName);
            textViewRailwayStatus = (TextView)itemView.findViewById(R.id.textViewRailwayStatus);
            textViewCoachNoName = (TextView)itemView.findViewById(R.id.textViewCoachNoName);
            textViewDeviceId = (TextView)itemView.findViewById(R.id.textViewDeviceId);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
            textViewInternalTemp = (TextView)itemView.findViewById(R.id.textViewInternalTemp);
            textViewExternalTemp = (TextView)itemView.findViewById(R.id.textViewExternalTemp);


        }
    }
}
