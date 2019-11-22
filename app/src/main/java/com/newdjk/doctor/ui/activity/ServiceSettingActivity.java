package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.dialog.DialogUtil;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.Mdt.MDTSettingActivity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceSettingActivity extends BasicActivity {
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mConsultPic)
    ItemView mConsultPic;
    @BindView(R.id.mConsultVideo)
    ItemView mConsultVideo;
    @BindView(R.id.mOnline)
    ItemView mOnline;
    @BindView(R.id.mService)
    ItemView mService;
    @BindView(R.id.mSecondDiagnose)
    ItemView mSecondDiagnose;
    @BindView(R.id.online_renewal_layout)
    LinearLayout onlineRenewalLayout;
    @BindView(R.id.mSecondDiagnoseLayout)
    LinearLayout mSecondDiagnoseLayout;
    @BindView(R.id.consult_view)
    View consultView;
    @BindView(R.id.consult_view_2)
    View consultView2;
    @BindView(R.id.mfaceYuyue)
    ItemView mfaceYuyue;
    @BindView(R.id.mdtService)
    ItemView mdtService;
    @BindView(R.id.mdtline1)
    View mdtline1;
    @BindView(R.id.mdtline2)
    View mdtline2;
    @BindView(R.id.mdttitle)
    LinearLayout mdttitle;
    private int mStatus;
    private int mDoctype;
    private boolean isOpenMdt = false;

    @Override
    protected int initViewResId() {
        return R.layout.service_setting;
    }

    @Override
    protected void initView() {

        initBackTitle("开通服务");
        mStatus = SpUtils.getInt(Contants.Status, 0);

    }

    @Override
    protected void initListener() {
        mConsultPic.setOnClickListener(this);
        mConsultVideo.setOnClickListener(this);
        mOnline.setOnClickListener(this);
        mService.setOnClickListener(this);
        mSecondDiagnose.setOnClickListener(this);
        mfaceYuyue.setOnClickListener(this);
        mdtService.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        requestServiceData();
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        if (mDoctype == 2) {
            //护士展示
            mConsultPic.getTvItemLeftText().setText("护理咨询");
            mConsultVideo.setVisibility(View.VISIBLE);
            mConsultVideo.getTvItemLeftText().setText("远程护理");
            onlineRenewalLayout.setVisibility(View.GONE);
            consultView.setVisibility(View.VISIBLE);
            mSecondDiagnoseLayout.setVisibility(View.GONE);
            mfaceYuyue.setVisibility(View.GONE);
            consultView2.setVisibility(View.GONE);

            mdtline1.setVisibility(View.GONE);
            mdtline2.setVisibility(View.GONE);
            mdtService.setVisibility(View.GONE);
            mdttitle.setVisibility(View.GONE);

        } else if (mDoctype == 1) {

            consultView.setVisibility(View.VISIBLE);
            mConsultVideo.setVisibility(View.VISIBLE);

        }




    }

    private void requestServiceData() {
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.ServicePackItem).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ServiceEntity>() {
            @Override
            public void onSuccess(int statusCode, ServiceEntity entity) {
                if (entity.getCode() == 0) {
                    handleData(entity);
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void handleData(ServiceEntity entity) {
        mConsultPic.setRightText("未开通");
        mConsultVideo.setRightText("未开通");
        mOnline.setRightText("未开通");
        mSecondDiagnose.setRightText("未开通");
        mfaceYuyue.setRightText("去设置");
        ServiceEntity.DataBean data = entity.getData();
        if (data == null) return;
        List<ServiceEntity.DataBean.DrServiceItemsBean> drServiceItems = data.getDrServiceItems();
        if (drServiceItems == null) return;
        for (ServiceEntity.DataBean.DrServiceItemsBean drServiceItem : drServiceItems) {
            switch (drServiceItem.getSericeItemCode()) {
                case "1101":
                    //图文问诊
                    mConsultPic.setRightText("已开通");
                    break;
                case "1102":
                    //视频问诊
                    mConsultVideo.setRightText("已开通");
                    break;
                case "1103":
                    //名医续方
                    mOnline.setRightText("已开通");
                    break;
                case "1201":
                    //护理咨询
                    mConsultPic.setRightText("已开通");
                    break;
                case "1202":
                    //远程护理
                    mConsultVideo.setRightText("已开通");
                    break;

                case "1110":
                    //第二诊疗
                    mSecondDiagnose.setRightText("已开通");
                    break;

                case "1130":
                    isOpenMdt = true;
                    mdtService.setRightText("已开通");
                    break;
            }
        }

        int inPack = data.getInPack();
        int totalPack = data.getAuditedPack();
        mService.setRightText("已创建" + totalPack + "个,审核中" + inPack + "个");

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.mOnline:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, ServiceSettingActivity.this);
                } else {
                    Intent partySettingIntent = new Intent(ServiceSettingActivity.this, OnlinePartySettingActivity.class);
                    partySettingIntent.putExtra("title", mOnline.getTvItemLeftText().getText().toString());
                    startActivityForResult(partySettingIntent, 0);

                }
                break;
            case R.id.mConsultPic:

                Intent consultSettingIntent = new Intent(ServiceSettingActivity.this, ConsultSettingActivity.class);
                consultSettingIntent.putExtra("title", mConsultPic.getTvItemLeftText().getText().toString());
                startActivityForResult(consultSettingIntent, 1);
                break;
            case R.id.mConsultVideo:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, ServiceSettingActivity.this);
                } else {
                    Intent videoInterrationSettingIntent = new Intent(ServiceSettingActivity.this, VideoInterrogationSettingActivity.class);
                    videoInterrationSettingIntent.putExtra("title", mConsultVideo.getTvItemLeftText().getText().toString());
                    startActivityForResult(videoInterrationSettingIntent, 2);
                }
                break;
            case R.id.mService:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                //定制服务包
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, ServiceSettingActivity.this);
                } else {
                    //定制服务包
                    Intent medicalServiceIntent = new Intent(ServiceSettingActivity.this, MedicalServiceActivity.class);
                    medicalServiceIntent.putExtra("type", 2);
                    startActivity(medicalServiceIntent);
                }
                break;

            case R.id.mSecondDiagnose:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, ServiceSettingActivity.this);
                } else {
                    Intent intent = new Intent(ServiceSettingActivity.this, TreatSettingActivity.class);
                    startActivity(intent);

                }
                break;


            case R.id.mfaceYuyue:

                Intent mianzhen = new Intent(this, PrescriptionActivity.class);
                mianzhen.putExtra("type", 21);
                startActivity(mianzhen);
                break;

            case R.id.mdtService:
                if (isOpenMdt) {
                    Intent mdtservice = new Intent(this, MDTSettingActivity.class);
                    mdtservice.putExtra("type", 21);
                    startActivity(mdtservice);
                } else {
                    showDialog();
                }

                break;
        }
    }

    private void showDialog() {
        DialogUtil.getInstanse().show(this, "温馨提示", "请联系客服0755-82569787申请加入专科协作团队，开通协作服务，连接更多专家资源", R.layout.dialog_alert_signalbutton, new DialogUtil.DialogListener() {

            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                case 1:
                case 2:
                    requestServiceData();
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
