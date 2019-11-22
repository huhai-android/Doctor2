package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.entity.DoctorReportSettingEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   CustodyReportActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 16:41
 *  @描述：
 */
public class CheckSettingActivity extends BasicActivity {

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
    @BindView(R.id.accepts_switch)
    SwitchButton acceptsSwitch;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.consult_price)
    EditText consultPrice;
    @BindView(R.id.consult_discount)
    EditText consultDiscount;
    @BindView(R.id.save)
    TextView save;

    @Override
    protected int initViewResId() {
        return R.layout.activity_setting_report;
    }

    @Override
    protected void initView() {
        initBackTitle(getString(R.string.check_setting_report));

    }

    @Override
    protected void initListener() {
        save.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        //获取图文问诊的数据
        getConsultSetting();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.save:
                String discount = consultDiscount.getText().toString();
                String numberSource = acceptAccount.getText().toString();
                String price = consultPrice.getText().toString();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    saveConsultSetting(numberSource,price,discount);
                } else {
                    toast("请把信息填写完整");
                }
                break;
        }
    }

    private void saveConsultSetting(String numberSource, String price, String discount) {

        Map<String,String> paramsMap = new HashMap<>();
       // paramsMap.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DoctorId", "1");
        paramsMap.put("IsOpen", acceptsSwitch.isChecked()?"1":"0");
        paramsMap.put("ReceptionNum", numberSource);
        paramsMap.put("UnitPrice", price);
        paramsMap.put("DiscountPrice", discount);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.submitAllDoctorSetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                if (entity.getCode() == 0) {
                    toast("保存成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
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

    private void getConsultSetting() {
        Map<String, String> map = new HashMap<>();
        //  map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        map.put("DoctorId", "1");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getAllDoctorSetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorReportSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorReportSettingEntity> entity) {

                if (entity.getCode() == 0) {
                    DoctorReportSettingEntity mConsultSettingEntity = entity.getData();
                    boolean isOn = mConsultSettingEntity.isIsOpen();
                    acceptAccount.setText(String.valueOf(mConsultSettingEntity.getReceptionNum()));
                    consultPrice.setText(String.valueOf(mConsultSettingEntity.getUnitPrice()));
                    consultDiscount.setText(String.valueOf(mConsultSettingEntity.getDiscountPrice()));
                    acceptsSwitch.setCheckedImmediatelyNoEvent(isOn);
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


}
