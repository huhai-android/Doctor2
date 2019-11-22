package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ConsultSettingEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ItemDivideRationEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.MathUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.TitleBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoInterrogationSettingActivity extends BasicActivity {

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
    @BindView(R.id.interratin_switch)
    SwitchButton interratinSwitch;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.interration_price)
    EditText interrationPrice;
    @BindView(R.id.interration_discount)
    EditText interrationDiscount;
    @BindView(R.id.interration_schedule)
    RelativeLayout interrationSchedule;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.interration_price_text)
    TextView interrationPriceText;
    @BindView(R.id.interration_discount_text)
    TextView interrationDiscountText;
    @BindView(R.id.interration_schedule_text)
    TextView interrationScheduleText;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    @BindView(R.id.tv_switch_advice)
    TextView tvSwitchAdvice;
    @BindView(R.id.bu_switch_advice)
    SwitchButton buSwitchAdvice;
    @BindView(R.id.tv_fencheng_proportion)
    TextView tvFenchengProportion;
    @BindView(R.id.tv_service_title)
    TextView tvServiceTitle;
    private ConsultSettingEntity mConsultSettingEntity;
    private Gson mGson;
    private int mDoctype;
    private ResponseEntity<ItemDivideRationEntity> mItemDivideRationEntity;

    @Override
    protected int initViewResId() {
        return R.layout.video_interrogation_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        if (mDoctype == 1) {
            new TitleBuilder(mActivity)
                    .setTitleText("视频问诊设置")
                    .setLeftImage(R.drawable.head_back_selectot)
                    .setLeftOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final GroupButtonDialog groupButtonDialog = new GroupButtonDialog(mContext);

                            groupButtonDialog.show("提示", "是否保存当前界面设置", new GroupButtonDialog.DialogListener() {
                                @Override
                                public void cancel() {
                                    finish();
                                }

                                @Override
                                public void confirm() {
                                    //   ToastUtil.setToast("已经撤回");

                                    saveSetting();
                                }
                            });


                        }
                    });


        } else if (mDoctype == 2) {
            initBackTitle("远程护理设置");
            acceptsSwitchText.setText("远程护理开通开关");
            acceptAccountText.setText("每日远程数量");
            interrationPriceText.setText("远程护理单价");
            interrationDiscountText.setText("远程护理优惠价");
            interrationScheduleText.setText("远程护理排班");
        }

        interrationSchedule.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void saveSetting() {
        String discount = interrationDiscount.getText().toString().trim();
        String numberSource = acceptAccount.getText().toString().trim();
        String price = interrationPrice.getText().toString().trim();
        if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
            mConsultSettingEntity.setPrice(discount);
            mConsultSettingEntity.setOriginalPrice(price);
            mConsultSettingEntity.setNumberSource(numberSource);
            saveInquirySetting();
        } else {
            toast("请把信息填写完整");
        }
    }

    @Override
    protected void initListener() {
        interrationDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    if (mItemDivideRationEntity!=null){
                        refreshAdviceValue(mItemDivideRationEntity);
                    }

                }else {
                    tvFenchengProportion.setText("0" + "元");
                }

            }
        });

        buSwitchAdvice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (mItemDivideRationEntity!=null){
                        refreshAdviceValue(mItemDivideRationEntity);
                    }
                }else {
                    tvFenchengProportion.setText("0" + "元");
                }
            }
        });
    }

    @Override
    protected void initData() {
        getInquirySetting();


    }

    private void QueryServiceItemDivideRation(String SericeItemCode) {
        Map<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)) + "");
        params.put("SericeItemCode", SericeItemCode + "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);mConsultSettingEntity

        mMyOkhttp.get().url(HttpUrl.QueryServiceItemDivideRation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ItemDivideRationEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ItemDivideRationEntity> entity) {
                try {
                    if (entity.getCode() == 0) {
                        mItemDivideRationEntity=entity;
                        if (mConsultSettingEntity.getIsRecommend()==1){
                            refreshAdviceValue(mItemDivideRationEntity);
                        }else {
                            tvFenchengProportion.setText("0" + "元");
                        }
                    }else {
                        // toast(entity.getMessage());
                        ToastUtil.setToast(entity.getMessage());
                    }

                } catch (Exception e) {
                    tvFenchengProportion.setText("0" + "元");
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                ToastUtil.setToast(errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                String discount = interrationDiscount.getText().toString().trim();
                String numberSource = acceptAccount.getText().toString().trim();
                String price = interrationPrice.getText().toString().trim();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    mConsultSettingEntity.setPrice(discount);
                    mConsultSettingEntity.setOriginalPrice(price);
                    mConsultSettingEntity.setNumberSource(numberSource);
                    saveInquirySetting();
                } else {
                    toast("请把信息填写完整");
                }
                break;
            case R.id.interration_schedule:
                Intent ScheduleIntent = new Intent(VideoInterrogationSettingActivity.this, ScheduleActivity.class);
                startActivity(ScheduleIntent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getInquirySetting() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        map.put("DoctorType", String.valueOf(SpUtils.getInt(Contants.DocType, 1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquirySetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {
                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    VideoInterrogationSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mConsultSettingEntity.isOn();
                            int IsRecommend = mConsultSettingEntity.getIsRecommend();
                            interratinSwitch.setChecked(isOn != 0);
                            buSwitchAdvice.setChecked(IsRecommend != 0);
                            acceptAccount.setText(String.valueOf(mConsultSettingEntity.getNumberSource()));
                            interrationPrice.setText(MathUtils.getDealValue(mConsultSettingEntity.getOriginalPrice()));
                            interrationDiscount.setText(MathUtils.getDealValue(mConsultSettingEntity.getPrice()));
                            if (!TextUtils.isEmpty(mConsultSettingEntity.getPriceRemark())) {
                                //  tvPriceDesc.setText("" + mConsultSettingEntity.getPriceRemark());

                            } else {
//                                tvPriceDesc.setVisibility(View.GONE);
//                                tvServiceTitle.setVisibility(View.GONE);
                            }
                            QueryServiceItemDivideRation("1102");

                        }
                    });
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "errorMsg=" + errorMsg + ",statusCode=" + statusCode);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mGson = new Gson();
    }

    private void saveInquirySetting() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServiceItemId", mConsultSettingEntity.getServiceItemId() + "");
        paramsMap.put("SericeItemCode", mConsultSettingEntity.getSericeItemCode() + "");
        paramsMap.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DrName", SpUtils.getString(Contants.Name));
        paramsMap.put("OriginalPrice", mConsultSettingEntity.getOriginalPrice());
        paramsMap.put("NumberSource", mConsultSettingEntity.getNumberSource());
        paramsMap.put("Price", mConsultSettingEntity.getPrice());
        boolean checked = interratinSwitch.isChecked();
        boolean IsRecommend = buSwitchAdvice.isChecked();
        paramsMap.put("IsOn", checked ? "1" : "0");
        paramsMap.put("IsRecommend", IsRecommend ? "1" : "0");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveInquirySetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                if (entity.getCode() == 0) {
                    toast("保存成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
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

    public void  refreshAdviceValue(ResponseEntity<ItemDivideRationEntity> entity){

        if (mConsultSettingEntity!=null){
            if (buSwitchAdvice.isChecked()){
                double price = Double.parseDouble(interrationDiscount.getText().toString());
                double scale = Double.parseDouble(entity.getData().getItemDivideRation());
                double value = scale * price;
                double yushu = value - (int) value;
                if (yushu > 0) {
                    BigDecimal bg3 = new BigDecimal(value);
                    double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    DecimalFormat df = new DecimalFormat("0.00");
                    String s = df.format(f3);
                    tvFenchengProportion.setText(f3 + "元");
                } else {

                    tvFenchengProportion.setText(((int) value) + "元");
                }
            }else {
                tvFenchengProportion.setText("0" + "元");
            }

        }else {
            tvFenchengProportion.setText("0" + "元");
        }

    }
}
