package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;
import com.newdjk.doctor.ui.fragment.MianzhenFragment1;
import com.newdjk.doctor.utils.GlideMediaLoader;

import java.util.List;


public class MianzhenChildAdapter extends BaseQuickAdapter<MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean, BaseViewHolder> {

    private final Fragment fragment;

    private Gson mGson;

    public MianzhenChildAdapter(@Nullable List<MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean> data, Fragment fragment) {
        super(R.layout.list_mianzhen_child_item, data);
        mGson = new Gson();
        this.fragment = fragment;

    }

    @Override
    protected void convert(BaseViewHolder helper, final MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean item) {
        if (TextUtils.isEmpty(item.getPatientName())) {
            helper.setText(R.id.tv_name, "- -");

        } else {
            helper.setText(R.id.tv_name, item.getPatientName() + "");

        }
        switch (item.getPatientSex()) {
            case 1:
                helper.setText(R.id.tv_sex, "（男）");

                break;
            case 2:
                helper.setText(R.id.tv_sex, "（女）");

                break;
            default:
                helper.setText(R.id.tv_sex, "（未知）");

                break;
        }
        helper.setText(R.id.tv_age, item.getPatientAge() + "");
        helper.setText(R.id.tv_time, item.getStartTime().substring(0,5) + " - "+item.getEndTime().substring(0,5));
        if (TextUtils.isEmpty(item.getDescription())) {
          //  helper.setText(R.id.tv_desc, "病情描述：");
        } else {
           // helper.setText(R.id.tv_desc, "病情描述：" + item.getDescription() + "");
        }
        ImageView avatar = helper.getView(R.id.avatar);

        GlideMediaLoader.load(mContext, avatar, item.getPatientPic());
        //面诊预约状态。0-待就诊；1-已完成；2-已取消
        if (fragment instanceof MianzhenFragment1) {

            if (item.isCanCancel()) {

                switch (item.getAppointmentStatus()) {

                    case 0:
                        helper.setVisible(R.id.tv_status, true);
                        helper.setText(R.id.tv_status, "取消预约");
                        helper.setBackgroundRes(R.id.tv_status, R.drawable.bg_cancel_mianzhen);
                        break;
                    case 1:
                        helper.setVisible(R.id.tv_status, false);
                        helper.setText(R.id.tv_status, "已完成");
                        helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.yiquxiaoyuyue));
                        helper.setBackgroundRes(R.id.tv_status, R.color.white);
                        break;
                    case 2:
                        helper.setVisible(R.id.tv_status, false);
                        helper.setText(R.id.tv_status, "已取消预约");
                        helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.yiquxiaoyuyue));
                        helper.setBackgroundRes(R.id.tv_status, R.color.white);
                        break;
                }
            } else {
                helper.setVisible(R.id.tv_status, false);
            }

        } else {


            switch (item.getAppointmentStatus()) {

                case 0:
                    helper.setVisible(R.id.tv_status, false);
                    helper.setText(R.id.tv_status, "取消预约");
                    helper.setBackgroundRes(R.id.tv_status, R.drawable.bg_cancel_mianzhen);
                    break;
                case 1:
                    helper.setVisible(R.id.tv_status, true);
                    helper.setText(R.id.tv_status, "已完成");
                    helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.yiquxiaoyuyue));
                    helper.setBackgroundRes(R.id.tv_status, R.color.white);
                    break;
                case 2:
                    helper.setVisible(R.id.tv_status, true);
                    helper.setText(R.id.tv_status, "已取消预约");
                    helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.yiquxiaoyuyue));
                    helper.setBackgroundRes(R.id.tv_status, R.color.white);
                    break;
            }
        }


        helper.addOnClickListener(R.id.tv_status);
    }
}
