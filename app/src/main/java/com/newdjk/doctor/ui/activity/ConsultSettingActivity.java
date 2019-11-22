package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class ConsultSettingActivity extends BasicActivity {

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

    @BindView(R.id.consult_discount)
    EditText consultDiscount;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.consult_price)
    TextView consultPrice;
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.consult_price_text)
    TextView consultPriceText;
    @BindView(R.id.consult_discount_text)
    TextView consultDiscountText;
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
    private Gson mGson;
    private ConsultSettingEntity mConsultSettingEntity;
    private String discount;
    private String numberSource;
    private String price;
    private int mDoctype;
    private String mSericeItemCode;
    private ResponseEntity<ItemDivideRationEntity> mItemDivideRationEntity;

    @Override
    protected int initViewResId() {
        return R.layout.consult_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        if (mDoctype == 1) {
            initBackTitle(getString(R.string.consult_setting));

            new TitleBuilder(mActivity)
                    .setTitleText(getString(R.string.consult_setting))
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

                                    discount = consultDiscount.getText().toString();
                                    numberSource = acceptAccount.getText().toString();
                                    price = consultPrice.getText().toString();
                                    if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                                        if (mConsultSettingEntity != null) {
                                            mConsultSettingEntity.setPrice(discount);
                                            mConsultSettingEntity.setOriginalPrice(price);
                                            mConsultSettingEntity.setNumberSource(numberSource);
                                        }

                                        saveConsultSetting();
                                    } else {
                                        toast("请把信息填写完整");
                                    }
                                }
                            });


                        }
                    });

            mSericeItemCode = "1101";

        } else if (mDoctype == 2) {
            mSericeItemCode = "1201";
            acceptsSwitchText.setText("护理咨询开通开关");
            acceptAccountText.setText("每日护理数量");
            consultPriceText.setText("护理咨询单价");
            consultDiscountText.setText("护理咨询优惠价");


            new TitleBuilder(mActivity)
                    .setTitleText("护理咨询设置")
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

                                    discount = consultDiscount.getText().toString();
                                    numberSource = acceptAccount.getText().toString();
                                    price = consultPrice.getText().toString();
                                    if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                                        if (mConsultSettingEntity != null) {
                                            mConsultSettingEntity.setPrice(discount);
                                            mConsultSettingEntity.setOriginalPrice(price);
                                            mConsultSettingEntity.setNumberSource(numberSource);
                                        }

                                        saveConsultSetting();
                                    } else {
                                        toast("请把信息填写完整");
                                    }
                                }
                            });


                        }
                    });


            tvPriceDesc.setVisibility(View.GONE);
            tvServiceTitle.setVisibility(View.GONE);
        }

        save.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

        consultDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (mItemDivideRationEntity != null) {
                        refreshAdviceValue(mItemDivideRationEntity);
                    }

                } else {
                    tvFenchengProportion.setText("0" + "元");
                }

            }
        });

        buSwitchAdvice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mItemDivideRationEntity != null) {
                        refreshAdviceValue(mItemDivideRationEntity);
                    }
                } else {
                    tvFenchengProportion.setText("0" + "元");
                }
            }
        });
    }

    @Override
    protected void initData() {
        mGson = new Gson();
        // Intent intent = getIntent();
       /* if (intent != null) {
            String title = intent.getStringExtra("title");
            topTitle.setText(title);
        }*/
        //获取图文问诊的数据
        getConsultSetting();


    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                discount = consultDiscount.getText().toString();
                numberSource = acceptAccount.getText().toString();
                price = consultPrice.getText().toString();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    if (mConsultSettingEntity != null) {
                        mConsultSettingEntity.setPrice(discount);
                        mConsultSettingEntity.setOriginalPrice(price);
                        mConsultSettingEntity.setNumberSource(numberSource);
                    }

                    saveConsultSetting();
                } else {
                    toast("请把信息填写完整");
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 获取图文问诊的数据
     */
    private void getConsultSetting() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        map.put("DoctorType", String.valueOf(SpUtils.getInt(Contants.DocType, 1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultSetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {

                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    ConsultSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mConsultSettingEntity.isOn();
                            int isRecomand = mConsultSettingEntity.getIsRecommend();
                            acceptsSwitch.setChecked(isOn != 0);
                            buSwitchAdvice.setChecked(isRecomand != 0);
                            acceptAccount.setText(String.valueOf(mConsultSettingEntity.getNumberSource()));

                            consultPrice.setText(MathUtils.getDealValue(mConsultSettingEntity.getOriginalPrice()));
                            consultDiscount.setText(MathUtils.getDealValue(mConsultSettingEntity.getPrice()));
                            if (TextUtils.isEmpty(mConsultSettingEntity.getPriceRemark())) {
//                                tvPriceDesc.setVisibility(View.GONE);
//                                tvServiceTitle.setVisibility(View.GONE);
                            } else {
                                //  tvPriceDesc.setText("" + mConsultSettingEntity.getPriceRemark());
                            }

                            QueryServiceItemDivideRation(mSericeItemCode);
                        }
                    });
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

    private void saveConsultSetting() {
        Map<String, String> paramsMap = new HashMap<>();
        if (mConsultSettingEntity != null) {
            paramsMap.put("ServiceItemId", mConsultSettingEntity.getServiceItemId() + "");
            paramsMap.put("SericeItemCode", mConsultSettingEntity.getSericeItemCode() + "");
            paramsMap.put("OriginalPrice", mConsultSettingEntity.getOriginalPrice());
            paramsMap.put("NumberSource", mConsultSettingEntity.getNumberSource());
            paramsMap.put("Price", mConsultSettingEntity.getPrice());
        }
        paramsMap.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DrName", SpUtils.getString(Contants.Name));
        boolean checked = acceptsSwitch.isChecked();
        boolean IsRecommend = buSwitchAdvice.isChecked();
        paramsMap.put("IsOn", checked ? "1" : "0");
        paramsMap.put("IsRecommend", IsRecommend ? "1" : "0");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveConsultSetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void QueryServiceItemDivideRation(String SericeItemCode) {
        Map<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)) + "");
        params.put("SericeItemCode", SericeItemCode + "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.get().url(HttpUrl.QueryServiceItemDivideRation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ItemDivideRationEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ItemDivideRationEntity> entity) {
                try {
                    if (entity.getCode() == 0) {
                        mItemDivideRationEntity = entity;
                        if (mConsultSettingEntity.getIsRecommend() == 1) {
                            refreshAdviceValue(mItemDivideRationEntity);
                        } else {
                            tvFenchengProportion.setText("0" + "元");
                        }
                    } else {
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


    public void refreshAdviceValue(ResponseEntity<ItemDivideRationEntity> entity) {
        if (mConsultSettingEntity != null) {
            if (buSwitchAdvice.isChecked()) {
                double price = Double.parseDouble(consultDiscount.getText().toString());
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
            } else {
                tvFenchengProportion.setText("0" + "元");
            }

        } else {
            tvFenchengProportion.setText("0" + "元");
        }

    }
}
