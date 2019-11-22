package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.TaskAllEntity;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.MianzhenDialog;

import java.util.List;


public class TaskfragmentAdapter extends BaseQuickAdapter<TaskAllEntity.ReturnDataBean, BaseViewHolder> {


    private final MyOkHttp mMyOkhttp;
    private Gson mGson;
    private MianzhenDialog mDialog;

    public TaskfragmentAdapter(@Nullable List<TaskAllEntity.ReturnDataBean> data) {
        super(R.layout.list_task_child_item, data);
        mGson = new Gson();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();

    }

    @Override
    protected void convert(final BaseViewHolder helper, final TaskAllEntity.ReturnDataBean item) {
        if (TextUtils.isEmpty(item.getPatientName())) {
            helper.setText(R.id.tv_name, "- -");
        } else {
            helper.setText(R.id.tv_name, item.getPatientName() + "");
        }
        helper.setText(R.id.tv_age, (TextUtils.isEmpty(item.getPatientAge())?"":item.getPatientAge()) + "");
        helper.setText(R.id.tv_time, (TextUtils.isEmpty(item.getPayTime())?"":item.getPayTime()) + "");
        helper.setText(R.id.tv_deal_number, (TextUtils.isEmpty(item.getPayOrderNo())?"":item.getPayOrderNo()) + "");
        helper.setText(R.id.tv_status, ((TextUtils.isEmpty(item.getRelationName()))?"":item.getRelationName())+ "");

        if (item.getServiceType() == 50 || item.getServiceType() == 51 || item.getServiceType() == 52) {
            if (TextUtils.isEmpty(item.getServiceContent())) {
                helper.setText(R.id.tv_desc, "病情主诉：");
            } else {
                helper.setText(R.id.tv_desc, "病情主诉：" + item.getServiceContent() + "");
            }
        } else {
            if (TextUtils.isEmpty(item.getServiceContent())) {
                helper.setText(R.id.tv_desc, "病情描述：");
            } else {
                helper.setText(R.id.tv_desc, "病情描述：" + item.getServiceContent() + "");
            }
        }

        switch (item.getPatientSex()) {
            case 1:
                helper.setText(R.id.tv_sex, "男");

                break;
            case 2:
                helper.setText(R.id.tv_sex, "女");

                break;
            default:
                helper.setText(R.id.tv_sex, "未知");

                break;
        }
        CircleImageView avatar = helper.getView(R.id.avatar);
        GlideUtils.loadPatientImage(item.getPicturePath(), avatar);

        //需要判读是否可以取消预约
        //业务类型(1-图文问诊，2-视频问诊，3-名医续方，4-处方费，5-护理咨询，6-远程护理，7-上门服务，
        // 8-耗材，9-服务包，11-第二诊疗，12-检验检查，13-预约金，14-预约诊金，15-远程门诊，
        // 16-专科转诊，17-中药处方，18-优选推荐，19-面诊预约，20-拼团购买，21-母胎解读（21-39为设备解读），
        // 50-MDT咨询，51-MDT分诊，52-MDT会诊，99-其他
        if (item.getServiceType() == 19) {
            helper.setVisible(R.id.tv_cancel, true);
        } else {
            helper.setVisible(R.id.tv_cancel, false);
        }

        if (item.getServiceType() == 2||item.getServiceType() == 19){
            helper.setVisible(R.id.lv_yuyue, true);
        }else {
            helper.setVisible(R.id.lv_yuyue, false);
        }


        try {
            if (item.getServiceType() == 2||item.getServiceType() == 19){
                if (!TextUtils.isEmpty(item.getServiceStartTime())&&!TextUtils.isEmpty(item.getServiceEndTime())){
                    helper.setText(R.id.tv_time_yuyue, item.getServiceStartTime().substring(0, 10)+item.getServiceStartTime().substring(10, 19) + " - " + item.getServiceEndTime().substring(10, 19));

                }

            }

        }catch (Exception e){

        }

    }


}
