package com.cieslak.jacek.freesms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cieslak.jacek.freesms.apiservices.RestClient;
import com.cieslak.jacek.freesms.model.SMS;
import com.cieslak.jacek.freesms.view.RoundedLetterView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SMSActivity extends AppCompatActivity {

    private static final String TAG = "SMSActivity";
    private String contact;
    private String number;
    private TextView mNameHolder;
    private RoundedLetterView mRoundedLetterView;
    private EditText mNumberTo;
    private EditText mMessage;
    private Button sendMessage;
    private SMS sms;
    private JSONObject jsonObject;
    private RestClient restClient = RestClient.getInstance();

    private ProgressDialog prgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contact = extras.getString("contact");
            number = extras.getString("number");
        }
        mRoundedLetterView = (RoundedLetterView) findViewById(R.id.rlv_name_view);
        mRoundedLetterView.setTitleText(contact.substring(0, 1).toUpperCase());
        mNameHolder = (TextView) findViewById(R.id.tv_name_holder);
        mNameHolder.setText(contact);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);


        mNumberTo = (EditText) findViewById(R.id.et_number);
        mNumberTo.setText(number);
        mMessage = (EditText) findViewById(R.id.et_message);
        sendMessage = (Button) findViewById(R.id.b_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                TelephonyManager tm = (TelephonyManager)
                        getSystemService(Context.TELEPHONY_SERVICE);
                String telNumber = tm.getLine1Number();
                if (telNumber != null)
//                    Toast.makeText(getApplicationContext(), "Phone number: " + telNumber,Toast.LENGTH_LONG).show();
                    sms = new SMS(String.valueOf(mNumberTo.getText()), String.valueOf(mNumberTo.getText()),String.valueOf(mMessage.getText()),0);
                 restClient.createUser(getApplication(),sms);
//                jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("numberTo",sms.getNumberTo());
//                    jsonObject.put("numberFrom",sms.getNumberFrom());
//                    jsonObject.put("message",sms.getMessage());
//                    jsonObject.put("Date",sms.getDate());
//                    invokeWS(jsonObject);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void invokeWS(final JSONObject jsonObject) throws UnsupportedEncodingException {
        // Show Progress Dialog
        final JSONObject jO = jsonObject;
        ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        Log.d(TAG,entity.toString());
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getApplicationContext(), "http://sms.servehttp.com:8081/server/api/create/", entity, "application/json", new JsonHttpResponseHandler(){
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                try {
                    prgDialog.hide();
                    Log.i("SER", "HERE!");
                    String login = obj.getString("login");
                    int ID = obj.getInt("id");

                    //user.setUserId(obj.getInt("userid"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "404 - Nie odnaleziono serwera!", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "500 - Coś poszło nie tak po stronie serwera!", Toast.LENGTH_LONG).show();
                } else if (statusCode == 403) {
                    Toast.makeText(getApplicationContext(), "Podano niepoprawne dane!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), throwable.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
