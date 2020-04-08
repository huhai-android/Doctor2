package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.ZhanZhenListEntity;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment1;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment2;
import com.newdjk.doctor.ui.fragment.ZhuanZhenFragment3;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.TimeUtil;

import java.util.List;


public class FenjizhuanzhenAdapter extends BaseQuickAdapter<ZhanZhenListEntity.ReturnDataBean, BaseViewHolder> {

    private final Fragment fragment;
    private Gson mGson;

    public FenjizhuanzhenAdapter(@Nullable List<ZhanZhenListEntity.ReturnDataBean> data, Fragment context) {
        super(R.layout.zhuanzhen_item, data);
        mGson = new Gson();
        this.fragment = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZhanZhenListEntity.ReturnDataBean item) {

        try {
            TextView tvRefuse = helper.getView(R.id.tv_refuse);
            TextView tvHaveCheck = helper.getView(R.id.have_check);
            ImageView avatar = helper.getView(R.id.im_doctor);
            View line = helper.getView(R.id.viewline);

            if (fragment instanceof ZhuanZhenFragment1) {
                helper.setText(R.id.create_time, TimeUtil.showTime(item.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));

            } else if (fragment instanceof ZhuanZhenFragment2) {
                helper.setText(R.id.create_time, TimeUtil.showTime(item.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
                helper.setText(R.id.tv_doctor_patient, item.getFromDrName() + "医生 转诊");
                helper.setText(R.id.have_check, "");

                if (item.getIsReturnBack() == 0) {
                    tvRefuse.setVisibility(View.GONE);

                } else {
                    tvRefuse.setVisibility(View.VISIBLE);
                }
                tvHaveCheck.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            } else if (fragment instanceof ZhuanZhenFragment3) {
                if (TextUtils.isEmpty(item.getReferralTime())) {
                    helper.setText(R.id.create_time, "");

                } else {
                    helper.setText(R.id.create_time, TimeUtil.showTime(item.getReferralTime(), "yyyy-MM-dd HH:mm:ss"));

                }
                helper.setText(R.id.tv_doctor_patient, "转诊给" + item.getToDrName() + " 医生");
                if (item.getReferralStatus() == 2) {
                    helper.setText(R.id.have_check, "已就诊");
                } else {
                    helper.setText(R.id.have_check, "未就诊");
                }
                tvHaveCheck.setVisibility(View.VISIBLE);
                tvRefuse.setVisibility(View.GONE);
            }


//            Glide.with(MyApplication.getContext())
//                    .load(item.getPicturePath())
//                    .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
//                    .error(R.drawable.patient_default_img)
//                    .placeholder(R.drawable.patient_default_img)
//                    .into(avatar);
            GlideUtils.loadPatientImage(item.getPicturePath(),avatar);
            int sexType = item.getPatientSex();
            String sex = "";
            switch (sexType) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                case 3:
                    sex = "未知";
                    break;
            }
            helper.setText(R.id.sex, sex);
            helper.setText(R.id.age, item.getAge() + "");
            String areaName = item.getPatientName();
            if (areaName != null) {
                helper.setText(R.id.prescriptionp_number, item.getPatientName());
            } else {
                helper.setText(R.id.prescriptionp_number, "");
            }
            helper.setText(R.id.tv_desc, "转诊说明:" + (TextUtils.isEmpty(item.getReferralRemark()) ? "" : item.getReferralRemark()));

            helper.addOnClickListener(R.id.tv_refuse);

            helper.setText(R.id.type_zhuanzhen, item.getReferralClass() == 0 ? "分级转诊" : "专科转诊");

        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }


}
