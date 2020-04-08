package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportActivity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.fragment.MDTfenzhenReportFragment2;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;


public class MDTfenzhenReportAdapter extends BaseQuickAdapter<MDTDetailEntity, BaseViewHolder> {

    private final Fragment fragment;
    private Gson mGson;
    private TimerTask mTask2;
    private int count = 0;
    private boolean isScroll = false;
    private ScheduledExecutorService mScheduledExecutorService;
    private long curentTime;
    private final long VALID_TIME = 86400000;

    public MDTfenzhenReportAdapter(@Nullable List<MDTDetailEntity> data, Fragment context) {
        super(R.layout.mdt_fenzhen_item, data);
        mGson = new Gson();
        this.fragment = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final MDTDetailEntity item) {

        String validTime = item.getPayTime();
        helper.setText(R.id.tv_name, item.getPatientName());
        helper.setText(R.id.tv_age, item.getPatientAge());
        if (item.getPatientSex() == 1) {
            helper.setText(R.id.tv_sex, "男");

        } else if (item.getPatientSex() == 2) {
            helper.setText(R.id.tv_sex, "女");

        } else {
            helper.setText(R.id.tv_sex, "未知");

        }
        if (TextUtils.isEmpty(item.getDescription())){
            helper.setText(R.id.tv_patient_desc, "病情主诉：" + "");
        }else {
            helper.setText(R.id.tv_patient_desc, "病情主诉：" + item.getDescription() + "");
        }


        helper.setText(R.id.tv_comedown_time, validTime + "");

//        ButtonView buttonView = helper.getView(R.id.accept_text);


        //如果自己是基层医生，没有查看报告权限

        Log.d(TAG, "基层医生id" + item.getConsultDrId() + " 医生id" + MyApplication.mDoctorInfoByIdEntity.getDrId());
        Log.d(TAG, "会诊医生id" + item.getMajorDrId() + " 医生id" + MyApplication.mDoctorInfoByIdEntity.getDrId());

        //基层医生不显示填写数据按钮
        if (item.getSubjectType() == 1) {
            if (item.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                helper.setVisible(R.id.accept_text, false);
            } else {
                helper.setVisible(R.id.accept_text, true);
            }
        }

        CircleImageView imageView = helper.getView(R.id.civImg);

//        Glide.with(MyApplication.getContext())
//                .load(item.getPatPicturePath())
//                .placeholder(R.drawable.patient_default_img)
//                .into(imageView);
        GlideUtils.loadPatientImage(item.getPatPicturePath(),imageView);
        Log.d(TAG, "类型" + item.getSubjectType() + "  " + item.getPayTime());
        RecyclerView recyclerView = helper.itemView.findViewById(R.id.recylevie);


        TextView accepttext = helper.itemView.findViewById(R.id.accept_text);

        //是否是主治医生
        boolean ismain = false;
        //是否是主诊医生

       // Log.d("MDTfenzhenReportAdapter","数据"+item.toString());
        if (item.getMajorDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
            ismain = true;
        } else {
            ismain = false;
        }

        //已完成的报告  所有按钮都是查看详情
        if (fragment instanceof MDTfenzhenReportFragment2) {
            accepttext.setText("查看详情");
            accepttext.setClickable(true);
            helper.addOnClickListener(R.id.accept_text);

            //未完成的报告  按钮要区分开来
        } else {
            if (item.getIsNext() == 1) {//必须等等
//                accepttext.setText("等待会诊结论");
//                accepttext.setTextColor(mContext.getResources().getColor(R.color.white));
//                accepttext.setBackgroundResource(R.drawable.btn_input_advice_enable);
////                accepttext.setBackgroundResource(R.drawable.symptoms_style);
//                accepttext.setClickable(false);
                if (helper.getAdapterPosition()==7) {
                    Log.d("MDTfenzhenReportAdapter", "数据111111111");
                }
                helper.setText(R.id.accept_text, "填写专家意见");
                accepttext.setTextColor(mContext.getResources().getColor(R.color.white));
                accepttext.setBackgroundResource(R.drawable.btn_input_advice);
                accepttext.setClickable(true);
                helper.addOnClickListener(R.id.accept_text);
            } else {

                if (item.getSubjectType() == 2) {
                    helper.setText(R.id.accept_text, "填写专家意见");
                    accepttext.setTextColor(mContext.getResources().getColor(R.color.white));
                    accepttext.setBackgroundResource(R.drawable.btn_input_advice);
                    accepttext.setClickable(true);
                    helper.addOnClickListener(R.id.accept_text);
                } else {
                    //不是主治医生，不能进去填写意见
                    if (ismain){
                        helper.setText(R.id.accept_text, "填写结论性意见");
                        accepttext.setTextColor(mContext.getResources().getColor(R.color.white));
                        accepttext.setBackgroundResource(R.drawable.btn_input_advice);
//                accepttext.setBackgroundResource(R.drawable.symptoms_style);
                        accepttext.setClickable(true);
                        helper.addOnClickListener(R.id.accept_text);
                    }else {
                        helper.setText(R.id.accept_text, "填写结论性意见");
                        accepttext.setTextColor(mContext.getResources().getColor(R.color.white));
                        accepttext.setBackgroundResource(R.drawable.btn_input_advice_enable);
//                accepttext.setBackgroundResource(R.drawable.symptoms_style);
                        if (helper.getAdapterPosition()==7){
                            Log.d("MDTfenzhenReportAdapter","数据3333333333");
                        }

                        accepttext.setClickable(false);
                      //  helper.addOnClickListener(R.id.accept_text);
                    }


                }


            }


            Log.d("MDTfenzhenadapter","是否是主诊医生"+item.getDrId()+" 登录id"+MyApplication.mDoctorInfoByIdEntity.getDrId()+" 数据"+helper.getAdapterPosition());


        }
//        if (item.getSubjectType() == 3) {
        List<MDTDetailEntity.MDTReportDoctorsBean> list = new ArrayList<>();

        if (item.getSubjectType() == 3) {
            list.addAll(item.getMDTReportDoctors());
            helper.setVisible(R.id.line1, true);
            //如果自己不是主诊医生，就不能有填写结论性建议的按钮  已完成中的报告不用管

        } else {
            helper.setVisible(R.id.line1, false);
        }

        helper.addOnClickListener(R.id.to_group_chat);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        FenzhenReportDoctorListAdapter fenzhenReportDoctorListAdapter = new FenzhenReportDoctorListAdapter(list, ismain, fragment);
        recyclerView.setAdapter(fenzhenReportDoctorListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        fenzhenReportDoctorListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.accept_text:
                        final MDTDetailEntity.MDTReportDoctorsBean data = item.getMDTReportDoctors().get(position);
                        if (data.getDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {
                            Intent intent = new Intent(mContext, MDTInputReportActivity.class);
                            intent.putExtra("MDTDetailEntity", item);
                            mContext.startActivity(intent);
                        } else {
                            //只有自己是主诊医生，才能提醒
                            if (item.getMajorDrId() == MyApplication.mDoctorInfoByIdEntity.getDrId()) {

                                GroupButtonDialog groupButtonDialog = new GroupButtonDialog(mContext);

                                groupButtonDialog.show("提示", "是否提醒" + data.getDrName() + "填写诊疗建议？", new GroupButtonDialog.DialogListener() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void confirm() {
                                        //   ToastUtil.setToast("已经撤回");
                                        RemindDoctorAdvice(data);
                                    }
                                });

                            }

                        }
                        break;

                }

            }
        });


    }

    //提醒医生填写报告
    private void RemindDoctorAdvice(final MDTDetailEntity.MDTReportDoctorsBean item) {

        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", item.getSubjectBuyRecordId() + "");
        map.put("DrId", item.getDrId() + "");
        map.put("DrName", item.getDrName() + "");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().post().url(HttpUrl.RemindDoctorAdvice).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ToastUtil.setToast("提醒" + item.getDrName() + "医生成功");
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });


    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
