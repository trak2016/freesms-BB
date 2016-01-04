package com.cieslak.jacek.freesms.apiservices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cieslak.jacek.freesms.model.SMS;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RestClient{

    private static RestClient instance = null;

//    private ResultReadyCallback callback;

    private static final String BASE_URL = "http://sms.servehttp.com:8081/server/api/";
    private APIService service;
    boolean success = false;


    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(APIService.class);
    }

//    public List<User> getUsers() {
//        Call<List<User>> userlist = service.users();
//        userlist.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Response<List<User>> response) {
//                if (response.isSuccess()) {
//                    users = response.body();
//                    callback.resultReady(users);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("REST", t.getMessage());
//            }
//        });
//        return users;
//    }

//    public void setCallback(ResultReadyCallback callback) {
//        this.callback = callback;
//    }

    public boolean createUser(final Context ctx, SMS sms) {
        Call<SMS> u = service.createUser(sms);
        u.enqueue(new Callback<SMS>() {


            @Override
            public void onResponse(Response<SMS> response, Retrofit retrofit) {
                success = response.isSuccess();
                if(success) {
                    Toast.makeText(ctx, "User Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("REST", t.getMessage());
                Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();

            }
        });
        return success;
    }

    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

//    public interface ResultReadyCallback {
//        public void resultReady(List<User> users);
//    }

}