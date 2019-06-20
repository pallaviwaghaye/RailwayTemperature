package com.webakruti.railwaytemperature;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.railwaytemperature.model.ForgotPwd;
import com.webakruti.railwaytemperature.model.Login;
import com.webakruti.railwaytemperature.retrofit.ApiConstants;
import com.webakruti.railwaytemperature.retrofit.service.RestClient;
import com.webakruti.railwaytemperature.utils.NetworkUtil;
import com.webakruti.railwaytemperature.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView backToLogin;
    private Button buttonResetPassword;
    private EditText editTextEmail;
    private ProgressDialog progressDialogForAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
    }

    private void initViews() {
        backToLogin = (TextView)findViewById(R.id.backToLogin);
        buttonResetPassword = (Button)findViewById(R.id.buttonResetPassword);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);

        backToLogin.setOnClickListener(this);
        buttonResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.backToLogin :
                if (editTextEmail.getText().toString().length() > 0) {
                    if (isValidEmailAddress(editTextEmail.getText().toString().trim())) {

                            if (NetworkUtil.hasConnectivity(ForgotPasswordActivity.this)) {
                                //callForgotPwdAPI();
                                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                            }

                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email id must be valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email id cant be empty", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.buttonResetPassword:
                break;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

   /* private void callForgotPwdAPI() {

        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        //SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        //Log.e("Generated Token:", pref.getString("regId", ""));
        //String firebaseToken = pref.getString("regId","");

        Call<ForgotPwd> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).forgotPwd(editTextEmail.getText().toString());
        requestCallback.enqueue(new Callback<ForgotPwd>() {
            @Override
            public void onResponse(Call<ForgotPwd> call, Response<ForgotPwd> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    ForgotPwd result = response.body();

                    // Save UserResponse to SharedPref
                    SharedPreferenceManager.storeUserResponseObjectInSharedPreference(result);

                    if(result.getStatus() == true) {
                        // Toast.makeText(LoginActivity.this, result.getMsg().toString(), Toast.LENGTH_SHORT).show();
                       *//* new AlertDialog.Builder(LoginActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
*//*
                        Intent intent = new Intent(ForgotPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                                   *//* }
                                })
                                .show();*//*
                    }
                    else{
                        //Toast.makeText(LoginActivity.this, result.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(ForgotPasswordActivity.this,R.style.alertDialog)
                                .setMessage(result.getMsg().toString())
                                .setPositiveButton("Ok", null)
                                .show();
                    }


                } else {
                    // Response code is 401
                    //  Toast.makeText(UserLoginActivity.this, "Unauthorized User!! MobileNo or Password is incorrect.", Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setMessage("Unauthorized User!!")
                            .setPositiveButton("OK", null)
                            .show();
                    *//*Toast toast = Toast.makeText(UserLoginActivity.this, "Unauthorized User!! MobileNo or Password is incorrect.", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.YELLOW);
                    toast.show();*//*
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<ForgotPwd> call, Throwable t) {

                if (t != null) {

                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });
    }*/
}
