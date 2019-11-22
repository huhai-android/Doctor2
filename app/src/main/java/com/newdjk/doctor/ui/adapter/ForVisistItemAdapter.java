package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.activity.VideoInterrogationAppointmentActivity;
import com.newdjk.doctor.ui.entity.ConsultDataEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.ForVisistEntity;
import com.newdjk.doctor.ui.entity.ForVisistMessageEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForVisistItemAdapter extends RecyclerView.Adapter<ForVisistItemAdapter.MyViewHolder> {



    private ForVisistEntity mForVisistEntity;
    private List<ForVisistMessageEntity> mDataList;

    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private Gson mGson;

    public ForVisistItemAdapter(Activity activity) {
        mActivity = activity;
        mGson = new Gson();
        //mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.for_visist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        mDataList = mForVisistEntity.getReturnData();
        int weekType = mDataList.get(position).getWeekDay();
        String weekDay = "";
        switch (weekType) {
            case 1:
                weekDay = "星期一";
                break;
            case 2:
                weekDay = "星期二";
                break;
            case 3:
                weekDay = "星期三";
                break;
            case 4:
                weekDay = "星期四";
                break;
            case 5:
                weekDay = "星期五";
                break;
            case 6:
                weekDay = "星期六";
                break;
            case 7:
                weekDay = "星期日";
                break;
        }
        holder.date.setText(mDataList.get(position).getDutyDate());
        holder.time.setText(weekDay+"  "+mDataList.get(position).getStartTime()+"-"+mDataList.get(position).getEndTime());
        holder.number.setText(""+mDataList.get(position).getReserveNums());
        holder.consultMessageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forVisistIntent = new Intent(mActivity, VideoInterrogationAppointmentActivity.class);
                forVisistIntent.putExtra("id",mDataList.get(position).getId());
                mActivity.startActivity(forVisistIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("kk", "mInquiryRecordListEntity=" + mForVisistEntity);
        return mForVisistEntity == null ? 0 : mForVisistEntity.getReturnData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.consult_message_item)
        RelativeLayout consultMessageItem;
        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setDatalist(ForVisistEntity forVisistEntity) {
        mForVisistEntity = forVisistEntity;
        notifyDataSetChanged();
    }
}
