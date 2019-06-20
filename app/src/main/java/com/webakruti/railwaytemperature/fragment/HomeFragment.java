package com.webakruti.railwaytemperature.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.railwaytemperature.AddDeviceActivity;
import com.webakruti.railwaytemperature.R;
import com.webakruti.railwaytemperature.adapter.HomeAdapter;
import com.webakruti.railwaytemperature.model.DeviceDetails;
import com.webakruti.railwaytemperature.retrofit.ApiConstants;
import com.webakruti.railwaytemperature.retrofit.service.RestClient;
import com.webakruti.railwaytemperature.utils.CustomSwipeToRefresh;
import com.webakruti.railwaytemperature.utils.NetworkUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private TextView textViewNoData;
    private HomeAdapter homeAdapter;
    private ProgressDialog progressDialogForAPI;
    private FloatingActionButton fab_plus;
    private CustomSwipeToRefresh swipeContainer;
    boolean isCallFromPullDown = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        initSwipeLayout();

        progressDialogForAPI = new ProgressDialog(getActivity());
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        if (NetworkUtil.hasConnectivity(getActivity())) {
            getRetrofit();
        } else {
            Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void initViews() {
        swipeContainer = (CustomSwipeToRefresh) rootView.findViewById(R.id.swipeContainer);


        textViewNoData = (TextView)rootView.findViewById(R.id.textViewNoData);

        fab_plus = (FloatingActionButton)rootView.findViewById(R.id.fab_plus);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        /*LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(new HomeAdapter(getActivity(),6));
*/
    }

    private void initSwipeLayout() {

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("", "swipe to refresh");
                if (NetworkUtil.hasConnectivity(getActivity())) {
                    // call API
                    isCallFromPullDown = true;
                    getRetrofit();


                } else {
                    swipeContainer.setRefreshing(false);
                }
            }
        });
// Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.blue,
                R.color.red,
                R.color.blue,
                R.color.red);

    }

    private void getRetrofit() {


        Call<DeviceDetails> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getAllDevices();
        requestCallback.enqueue(new Callback<DeviceDetails>() {
            @Override
            public void onResponse(Call<DeviceDetails> call, Response<DeviceDetails> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    DeviceDetails details = response.body();

                    if (details != null && details.getStatus() == true) {

                        //handleStationPlatformData(details);
                        textViewNoData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        final List<DeviceDetails.Datum> devicelist = details.getData();
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager1);
                        recyclerView.setAdapter(new HomeAdapter(getActivity(), devicelist));
                    }
                    else{
                        textViewNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    // Response code is 401
                }

                if (isCallFromPullDown) {
                    swipeContainer.setRefreshing(false);
                    isCallFromPullDown = false;
                } else {
                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeviceDetails> call, Throwable t) {

                if (t != null) {

                    if (isCallFromPullDown) {
                        swipeContainer.setRefreshing(false);
                        isCallFromPullDown = false;
                    } else {
                        if (progressDialogForAPI != null) {
                            progressDialogForAPI.cancel();
                        }
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });


    }

}
