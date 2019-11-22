package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newdjk.doctor.R;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.adapter
 *  @文件名:   SimpleAdapter
 *  @创建者:   huhai
 *  @创建时间:  2018/11/29 16:36
 *  @描述：
 */

public class SimpleAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public SimpleAdapter(Context pContext, List<String> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.list_item_spinder, null);
        if (convertView != null) {
            TextView tvRefuseDesc = convertView.findViewById(R.id.tv_refuse_desc);
            tvRefuseDesc.setText(mList.get(position));
        }
        return convertView;
    }
}
