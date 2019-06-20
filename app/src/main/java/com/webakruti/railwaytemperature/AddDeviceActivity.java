package com.webakruti.railwaytemperature;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.railwaytemperature.model.addDevice;
import com.webakruti.railwaytemperature.retrofit.ApiConstants;
import com.webakruti.railwaytemperature.retrofit.service.RestClient;
import com.webakruti.railwaytemperature.utils.NetworkUtil;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageViewBack;
    private EditText editTextDeviceid;
    private EditText editTextTrainName;
    private EditText editTextCoachNumber;
    private RelativeLayout relativeLayoutAdd;
    private ProgressDialog progressDialogForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        intitView();
    }

    public void intitView() {
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        editTextDeviceid = (EditText) findViewById(R.id.editTextDeviceid);
        editTextTrainName = (EditText) findViewById(R.id.editTextTrainName);
        editTextCoachNumber = (EditText) findViewById(R.id.editTextCoachNumber);
        relativeLayoutAdd = (RelativeLayout) findViewById(R.id.relativeLayoutAdd);

        imageViewBack.setOnClickListener(this);
        relativeLayoutAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.imageViewBack :
                Intent intent = new Intent(AddDeviceActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.relativeLayoutAdd :
                if (editTextDeviceid.getText().toString().length() > 0) {
                    if (NetworkUtil.hasConnectivity(AddDeviceActivity.this)) {
                        callAddDeviceAPI();
                        /*Intent intent1 = new Intent(AddDeviceActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                finish();*/

                    } else {
                        Toast.makeText(AddDeviceActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(AddDeviceActivity.this, "Device id can't be empty", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void callAddDeviceAPI() {
        progressDialogForAPI = new ProgressDialog(AddDeviceActivity.this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<addDevice> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).addDevice(editTextDeviceid.getText().toString(),editTextTrainName.getText().toString(),editTextCoachNumber.getText().toString());
        requestCallback.enqueue(new Callback<addDevice>() {
            @Override
            public void onResponse(Call<addDevice> call, Response<addDevice> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    addDevice result = response.body();

                    Log.e("result :==", String.valueOf(result));

                    if(result.getStatus() == true ) {
                        //Toast.makeText(AddDeviceActivity.this, result.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(AddDeviceActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(AddDeviceActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();
                    }else{
                        //Toast.makeText(AddDeviceActivity.this, result.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(AddDeviceActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", null)
                                .show();
                    }

                } else {
                    // Response code is 401
                    //  Toast.makeText(UserLoginActivity.this, "Unauthorized User!! MobileNo or Password is incorrect.", Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(AddDeviceActivity.this)
                            .setMessage("Unauthorized User!!")
                            .setPositiveButton("OK", null)
                            .show();
                    /*Toast toast = Toast.makeText(UserLoginActivity.this, "Unauthorized User!! MobileNo or Password is incorrect.", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.YELLOW);
                    toast.show();*/
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<addDevice> call, Throwable t) {

                if (t != null) {

                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });
    }

}
