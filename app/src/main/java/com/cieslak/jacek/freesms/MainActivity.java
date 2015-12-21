package com.cieslak.jacek.freesms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;

import com.cieslak.jacek.freesms.adapter.ListViewAdapter;

import java.util.HashMap;

public class MainActivity extends Activity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private HashMap<String,String> mRandomData = new HashMap<String,String>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getActionBar().setElevation(4);
        this.mRandomData.put("Jacek Cieślak","726556056");
        contacts();


        this.mListView = (ListView) findViewById(R.id.lv_sample_list);
        this.mAdapter = new ListViewAdapter(this,mRandomData);
        this.mListView.setAdapter(mAdapter);


    }
    private void contacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        this.mRandomData.put(name,phoneNo);
                        Log.d("CONTACT","Name: " + name + ", Phone No: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
    }


}