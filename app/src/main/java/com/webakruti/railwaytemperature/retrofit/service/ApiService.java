package com.webakruti.railwaytemperature.retrofit.service;



import com.webakruti.railwaytemperature.model.DeviceDetails;
import com.webakruti.railwaytemperature.model.ForgotPwd;
import com.webakruti.railwaytemperature.model.Login;
import com.webakruti.railwaytemperature.model.Registration;
import com.webakruti.railwaytemperature.model.addDevice;
import com.webakruti.railwaytemperature.retrofit.ApiConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // --------------------using params-------------------------

    /*@GET(ApiConstants.cameras)
    Call<List<CameraModel>> getCameraInfo();*/


    @POST(ApiConstants.login)
    Call<Login> login(@Query("mobile") String mobile,
                      @Query("password") String password);

    @POST(ApiConstants.registration)
    Call<Registration> register(@Query("fullname") String username,
                                @Query("mobile") String mobile,
                                @Query("password") String password,
                                @Query("cpassword") String confirmpassword,
                                @Query("refer_by") String referenceId);

    /*@POST(ApiConstants.forgotPwd)
    Call<ForgotPwd> forgotPwd(@Query("email") String email,
                          @Query("password") String password);*/

    @POST(ApiConstants.addDevice)
    Call<addDevice> addDevice(@Query("deviceid") String deviceId,
                              @Query("train_name") String trainName,
                              @Query("coach_name") String coachName);

    @GET(ApiConstants.getDeviceList)
    Call<DeviceDetails> getAllDevices();

    /*@POST(ApiConstants.delete)
    Call<deleteDevice> delete(@Query("id") String id);

    @POST(ApiConstants.editDevice)
    Call<editDevice> editDevice(@Query("id") String id,
                                @Query("cam_id") String cameraid,
                                @Query("doi") String dateofinstallation,
                                @Query("latitude") String latitude,
                                @Query("longitude") String longitude,
                                @Query("location") String address);*/



}
