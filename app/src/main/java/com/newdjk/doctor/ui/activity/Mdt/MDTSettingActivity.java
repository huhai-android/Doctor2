package com.newdjk.doctor.ui.activity.Mdt;

import android.content.Intent;
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

public class MDTSettingActivity extends BasicActivity {


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
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.consult_price_text)
    TextView consultPriceText;
    @BindView(R.id.consult_price)
    EditText consultPrice;
    @BindView(R.id.consult_discount_text)
    TextView consultDiscountText;
    @BindView(R.id.consult_discount)
    EditText consultDiscount;
    @BindView(R.id.tv_switch_advice)
    TextView tvSwitchAdvice;
    @BindView(R.id.bu_switch_advice)
    SwitchButton buSwitchAdvice;
    @BindView(R.id.tv_fencheng_proportion)
    TextView tvFenchengProportion;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.tv_service_title)
    TextView tvServiceTitle;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    private Gson mGson;
    private ConsultSettingEntity mConsultSettingEntity;
    private ResponseEntity<ItemDivideRationEntity> mItemDivideRationEntity;
    private String discount;
    private String numberSource;
    private String price;
    private int mDoctype;
    private String mSericeItemCode="1130";

    @Override
    protected int initViewResId() {
        return R.layout.mdt_setting;
    }

    @Override
    protected void initView() {
        new TitleBuilder(mActivity)
                .setTitleText("多学科会诊咨询设置")
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
    }

    @Override
    protected void initListener() {

        save.setOnClickListener(this);

        consultDiscount.addTextChangedListener(new TextWatcher() {
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
        mGson = new Gson();
        getMDTSetting();

    }

    private void getMDTSetting() {
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.GetMDTConsultSetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {

                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    int isOn = mConsultSettingEntity.isOn();
                    int isRecomand = mConsultSettingEntity.getIsRecommend();
                    buSwitchAdvice.setChecked(isOn != 0);
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

                } else {
                    toast(entity.getMessage());
                }

                QueryServiceItemDivideRation(mSericeItemCode);

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
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
        boolean IsRecommend = buSwitchAdvice.isChecked();
        paramsMap.put("IsRecommend", IsRecommend ? "1" : "0");
        //paramsMap.put("IsOn",mConsultSettingEntity.getIsOn()+"");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveMDTConsultSetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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
            }else {
                tvFenchengProportion.setText("0" + "元");
            }

        }else {
            tvFenchengProportion.setText("0" + "元");
        }

    }
}
