package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AllDoctorCheckEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.TimeUtil;

import java.util.List;


public class WaitForCheckDoctorDataAdapter extends BaseQuickAdapter<AllDoctorCheckEntity.ReturnDataBean, BaseViewHolder> {

    private Gson mGson;

    public WaitForCheckDoctorDataAdapter(@Nullable List<AllDoctorCheckEntity.ReturnDataBean> data) {
        super(R.layout.online_alldoctor_item, data);
        mGson = new Gson();
    }

    @Override
    protected void convert(BaseViewHolder helper, final AllDoctorCheckEntity.ReturnDataBean item) {

        Log.d("AllDoctorDataAdapter", "unReadNum=" + item.toString() + item.getPicturePath());
        try {
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.load(mContext, avatar, item.getPicturePath());
            try {
                helper.setText(R.id.create_time, TimeUtil.showTime(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                helper.setText(R.id.create_time, item.getCreateTime());
            }
            helper.setText(R.id.tv_desc, item.getWeeks());
            int sexType = item.getSex();
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
            String areaName = item.getPatientName();
            if (areaName != null) {
                helper.setText(R.id.patient_name, areaName);
            } else {
                helper.setText(R.id.patient_name, "");
            }
            helper.setText(R.id.address, "");
            helper.setText(R.id.age, item.getAge() + "");


        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }
}
