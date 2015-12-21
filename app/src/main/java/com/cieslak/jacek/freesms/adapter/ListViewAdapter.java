package com.cieslak.jacek.freesms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cieslak.jacek.freesms.R;
import com.cieslak.jacek.freesms.SMSActivity;
import com.cieslak.jacek.freesms.view.RoundedLetterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jacek on 2015-11-09.
 */
public class ListViewAdapter  extends BaseAdapter {

    private HashMap<String,String> mEntries = new HashMap<String,String>();
    private List<String> keyList = new ArrayList<String>();
    private List<String> valueList  = new ArrayList<String>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String key;
    private String number;

    public ListViewAdapter(Context context, HashMap<String,String> entries){
        this.keyList = new ArrayList<String>(entries.keySet());
        this.valueList = new ArrayList<String>(entries.values());
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.keyList.size();
    }

    @Override
    public String getItem(int position) {
        return this.keyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_view_item,parent,false);
            mViewHolder.mRelativeLayout = (RelativeLayout)  convertView.findViewById(R.id.item_relative_layout);
            mViewHolder.mRoundedLetterView = (RoundedLetterView) convertView.findViewById(R.id.rlv_name_view);
            mViewHolder.mNameTextView = (TextView) convertView.findViewById(R.id.tv_name_holder);
            mViewHolder.mPhoneTextView = (TextView) convertView.findViewById(R.id.tv_number_holder);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        key  = keyList.get(position);
        number = valueList.get(position);
        String split[] = key.split(" ");
        if (key != null){
            mViewHolder.mNameTextView.setText(key);
            mViewHolder.mPhoneTextView.setText(number);
            if(key.length() == 0){
                mViewHolder.mRoundedLetterView.setTitleText("A");
            }else{
//                if(split.length > 1)
//                    mViewHolder.mRoundedLetterView.setTitleText(key.substring(0,1).toUpperCase() + split[1].substring(0,1));
//                else
                    mViewHolder.mRoundedLetterView.setTitleText(key.substring(0,1).toUpperCase());
            }
            if(position%2 == 0){
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }else{
                mViewHolder.mRoundedLetterView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }
        }

        mViewHolder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, SMSActivity.class);

                i.putExtra("contact", keyList.get(position));
                i.putExtra("number",  valueList.get(position) );
                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    private static class ViewHolder {

        private RelativeLayout mRelativeLayout;
        private RoundedLetterView mRoundedLetterView;
        private TextView mNameTextView;
        private TextView mPhoneTextView;
    }
}