package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AppLicationEntity;
import com.newdjk.doctor.ui.entity.QueryOrgDoctorSearchByPageEntity;
import com.newdjk.doctor.utils.AppLicationUtils;
import com.newdjk.doctor.utils.GlideUtils;

import java.util.List;

public class ZixunDoctorAdapter extends BaseQuickAdapter<QueryOrgDoctorSearchByPageEntity.ReturnDataBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<QueryOrgDoctorSearchByPageEntity.ReturnDataBean> datalist;


    public ZixunDoctorAdapter(@Nullable List<QueryOrgDoctorSearchByPageEntity.ReturnDataBean> data) {
        super(R.layout.zixun_doctor_item, data);

        datalist = data;
        mGson = new Gson();


    }

    @Override
    protected void convert(BaseViewHolder helper, final QueryOrgDoctorSearchByPageEntity.ReturnDataBean item) {

        helper.setText(R.id.tv_name, item.getDrName() + "");
        helper.setText(R.id.tv_position, item.getPositionName() + "|" + item.getDepartmentName());
        helper.setText(R.id.tv_hospitical, item.getHospitalName()+"" );
        helper.setText(R.id.tv_goodat, "擅长："+(TextUtils.isEmpty(item.getDoctorSkill())?"":item.getDoctorSkill())+"" );
        ImageView imageView = helper.itemView.findViewById(R.id.im_icon);
        GlideUtils.loadDoctorImage(item.getPicturePath() + "", imageView);
        int status = item.getDrStatus();

        if (status == 0) {
            helper.setText(R.id.tv_renzheng, "未认证");

        } else if (status == 1) {
            helper.setText(R.id.tv_renzheng, "已认证");
        } else if (status == 2) {//失败
            //  content = "您的资料审核不通过，请重新认证";
            helper.setText(R.id.tv_renzheng, "认证失败");

        } else if (status == 3) {
            // content = "您的资料正在审核中，请耐心等待";
            helper.setText(R.id.tv_renzheng, "认证中");

        }




    }
}
