package com.cieslak.jacek.freesms.apiservices;

import com.cieslak.jacek.freesms.model.SMS;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface APIService {
    @POST("/create")
    Call<SMS> createUser(@Body SMS mSMS);

}