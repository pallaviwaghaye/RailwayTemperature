package com.webakruti.railwaytemperature;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.railwaytemperature.model.Registration;
import com.webakruti.railwaytemperature.retrofit.ApiConstants;
import com.webakruti.railwaytemperature.retrofit.service.RestClient;
import com.webakruti.railwaytemperature.utils.NetworkUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements OnClickListener{
    private Button buttonRegister;
    private TextView gotoLogin;
    private EditText editTextFullname;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextRefId;
    private ProgressDialog progressDialogForAPI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
    }

    private void initViews() {
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        gotoLogin = (TextView)findViewById(R.id.gotoLogin);
        editTextFullname = (EditText)findViewById(R.id.editTextFullname);
        //editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextMobile = (EditText)findViewById(R.id.editTextMobile);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        editTextRefId = (EditText)findViewById(R.id.editTextRefId);


        buttonRegister.setOnClickListener(this);
        gotoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonRegister :

                if (editTextFullname.getText().toString().length() > 0) {
                    if(editTextMobile.getText().toString().length() > 0) {
                        if (editTextMobile.getText().toString().length() == 10) {
                        /*if (editTextEmail.getText().toString().length() > 0) {
                            if (isValidEmailAddress(editTextEmail.getText().toString().trim())) {*/
                                if (editTextPassword.getText().toString().length() >= 6) {
                                    if (editTextConfirmPassword.getText().toString().length() >= 6) {
                                        if(editTextConfirmPassword.getText().toString().equalsIgnoreCase(editTextPassword.getText().toString())){


                                            if (NetworkUtil.hasConnectivity(RegistrationActivity.this)) {
                                                callRegistrationAPI();

                                            } else {
                                                Toast.makeText(RegistrationActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                                            }
                                            /*Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();*/
                                        } else {
                                            Toast.makeText(RegistrationActivity.this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Confirm password must be greater than 6", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Password must be greater than 6", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Mobile number must be valid", Toast.LENGTH_SHORT).show();
                            }
                        /*} else {
                            Toast.makeText(RegistrationActivity.this, "Email id can't be empty", Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Mobile number can't be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Full name can't be empty", Toast.LENGTH_SHORT).show();
                }
                break;
                /*Intent intent1 = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;*/
            case R.id.gotoLogin :
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void callRegistrationAPI() {

        progressDialogForAPI = new ProgressDialog(RegistrationActivity.this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<Registration> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).register(editTextFullname.getText().toString(),editTextMobile.getText().toString(),editTextPassword.getText().toString(),editTextConfirmPassword.getText().toString(),editTextRefId.getText().toString());
        requestCallback.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    Registration result = response.body();

                    Log.e("result :==", String.valueOf(result));

                    if(result.getStatus() == true) {

                        new AlertDialog.Builder(RegistrationActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();

                    }
                    else{
                        //Toast.makeText(LoginActivity.this, result.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(RegistrationActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", null)
                                .show();
                    }


                } else {
                    // Response code is 401
                    //  Toast.makeText(UserLoginActivity.this, "Unauthorized User!! MobileNo or Password is incorrect.", Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(RegistrationActivity.this)
                            .setMessage("Unauthorized User!!")
                            .setPositiveButton("OK", null)
                            .show();

                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {

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
