package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.reflect.TypeToken;
import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.SymptomSelectedAdapter;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.GetSecondDiagnosisSettingEntity;
import com.newdjk.doctor.ui.entity.ItemDivideRationEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.MathUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.ItemView;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.TitleBuilder;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreatSettingActivity extends BasicActivity {


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
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accepts_switch)
    SwitchButton acceptsSwitch;
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
    @BindView(R.id.mFunReport)
    ItemView mFunReport;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.symptoms)
    LinearLayout symptoms;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    @BindView(R.id.tv_switch_advice)
    TextView tvSwitchAdvice;
    @BindView(R.id.bu_switch_advice)
    SwitchButton buSwitchAdvice;
    @BindView(R.id.tv_service_title)
    TextView tvServiceTitle;
    @BindView(R.id.tv_fencheng_proportion)
    TextView tvFenchengProportion;
    private Gson mGson;
    private String discount;
    private String numberSource;
    private String price;
    private int mDoctype;
    private GetSecondDiagnosisSettingEntity mGetSecondDiagnosisSettingEntity;
    private SymptomSelectedAdapter mSymptomSelectedAdapter;
    private List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> mList;
    private ResponseEntity<ItemDivideRationEntity> mItemDivideRationEntity;

    @Override
    protected int initViewResId() {
        return R.layout.treat_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mList = new ArrayList<>();
        GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 3);
        recyleview.setLayoutManager(mManagerLayout);
        mSymptomSelectedAdapter = new SymptomSelectedAdapter(mList);
        recyleview.setAdapter(mSymptomSelectedAdapter);
        if (mDoctype == 1) {
            new TitleBuilder(mActivity)
                    .setTitleText(getString(R.string.treat_setting))
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
                                    if (discount.equals("")) {
                                        discount = "0";
                                    }
                                    if (numberSource.equals("")) {
                                        numberSource = "0";
                                    }
                                    if (price.equals("")) {
                                        price = "0";
                                    }
                                    if (mList.size() > 0) {
                                        boolean checked = acceptsSwitch.isChecked();
                                        boolean IsRecommend = buSwitchAdvice.isChecked();
                                        mGetSecondDiagnosisSettingEntity.setIsOn(checked ? 1 : 0);
                                        mGetSecondDiagnosisSettingEntity.setIsRecommend(IsRecommend ? 1 : 0);
                                        mGetSecondDiagnosisSettingEntity.setPrice(Double.parseDouble(discount));
                                        mGetSecondDiagnosisSettingEntity.setOriginalPrice(Double.parseDouble(price));
                                        mGetSecondDiagnosisSettingEntity.setNumberSource(Integer.parseInt(numberSource));
                                        SaveSecondDiagnosisSetting();
                                    } else {
                                        toast("请选择诊疗的适应病症！");
                                    }
                                }
                            });


                        }
                    });


        } else if (mDoctype == 2) {
            acceptsSwitchText.setText("护理咨询开通开关");
            acceptAccountText.setText("每日护理数量");
            consultPriceText.setText("护理咨询单价");
            consultDiscountText.setText("护理咨询优惠价");
            new TitleBuilder(mActivity)
                    .setTitleText("第二诊疗意见设置")
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
                                    if (discount.equals("")) {
                                        discount = "0";
                                    }
                                    if (numberSource.equals("")) {
                                        numberSource = "0";
                                    }
                                    if (price.equals("")) {
                                        price = "0";
                                    }
                                    if (mList.size() > 0) {
                                        boolean checked = acceptsSwitch.isChecked();
                                        boolean IsRecommend = buSwitchAdvice.isChecked();
                                        mGetSecondDiagnosisSettingEntity.setIsOn(checked ? 1 : 0);
                                        mGetSecondDiagnosisSettingEntity.setIsRecommend(IsRecommend ? 1 : 0);
                                        mGetSecondDiagnosisSettingEntity.setPrice(Double.parseDouble(discount));
                                        mGetSecondDiagnosisSettingEntity.setOriginalPrice(Double.parseDouble(price));
                                        mGetSecondDiagnosisSettingEntity.setNumberSource(Integer.parseInt(numberSource));
                                        SaveSecondDiagnosisSetting();
                                    } else {
                                        toast("请选择诊疗的适应病症！");
                                    }
                                }
                            });


                        }
                    });

        }


    }

    @Override
    protected void initListener() {
        mFunReport.setOnClickListener(this);
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
        // Intent intent = getIntent();
       /* if (intent != null) {
            String title = intent.getStringExtra("title");
            topTitle.setText(title);
        }*/
        //获取图文问诊的数据
        GetSecondDiagnosisSetting();
        //  queryDoctorDiseases();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                discount = consultDiscount.getText().toString();
                numberSource = acceptAccount.getText().toString();
                price = consultPrice.getText().toString();
                if (discount.equals("")) {
                    discount = "0";
                }
                if (numberSource.equals("")) {
                    numberSource = "0";
                }
                if (price.equals("")) {
                    price = "0";
                }
                if (mList.size() > 0) {
                    boolean checked = acceptsSwitch.isChecked();
                    boolean IsRecommend = buSwitchAdvice.isChecked();
                    mGetSecondDiagnosisSettingEntity.setIsOn(checked ? 1 : 0);
                    mGetSecondDiagnosisSettingEntity.setIsRecommend(IsRecommend ? 1 : 0);
                    mGetSecondDiagnosisSettingEntity.setPrice(Double.parseDouble(discount));
                    mGetSecondDiagnosisSettingEntity.setOriginalPrice(Double.parseDouble(price));
                    mGetSecondDiagnosisSettingEntity.setNumberSource(Integer.parseInt(numberSource));
                    SaveSecondDiagnosisSetting();
                } else {
                    toast("请选择诊疗的适应病症！");
                }
                break;
            case R.id.mFunReport:
                Intent intent = new Intent(this, DiseaseSelectedActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                String dieaseData = data.getStringExtra("DiseaseList");
                Type jsonType = new TypeToken<List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean>>() {
                }.getType();
                List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> list = mGson.fromJson(dieaseData, jsonType);
                mGetSecondDiagnosisSettingEntity.setDoctorDiseases(list);
                //  List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> diseaseList = new ArrayList<>();
                mList.clear();
                for (int i = 0; i < list.size(); i++) {
                    GetSecondDiagnosisSettingEntity.DoctorDiseasesBean doctorDiseasesBean = new GetSecondDiagnosisSettingEntity.DoctorDiseasesBean();
                    doctorDiseasesBean.setDiseaseName(list.get(i).getDiseaseName());
                    mList.add(doctorDiseasesBean);
                }
                mSymptomSelectedAdapter.setNewData(mList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //   queryDoctorDiseases();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void GetSecondDiagnosisSetting() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetSecondDiagnosisSetting + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GetSecondDiagnosisSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GetSecondDiagnosisSettingEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.clear();
                    Log.i("SymptomsSelectActivity", "entity=" + entity.getData().toString());
                    mGetSecondDiagnosisSettingEntity = entity.getData();
                    mList = mGetSecondDiagnosisSettingEntity.getDoctorDiseases();
                    mSymptomSelectedAdapter.setNewData(mList);
                    TreatSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mGetSecondDiagnosisSettingEntity.getIsOn();
                            int IsRecommend = mGetSecondDiagnosisSettingEntity.getIsRecommend();
                            acceptsSwitch.setChecked(isOn != 0);
                            buSwitchAdvice.setChecked(IsRecommend != 0);
                            acceptAccount.setText(String.valueOf(mGetSecondDiagnosisSettingEntity.getNumberSource()));
                            consultPrice.setText(MathUtils.getDealValue(mGetSecondDiagnosisSettingEntity.getOriginalPrice()));
                            consultDiscount.setText(MathUtils.getDealValue(mGetSecondDiagnosisSettingEntity.getPrice()));
                            if (TextUtils.isEmpty(mGetSecondDiagnosisSettingEntity.getPriceRemark())) {
//                                tvPriceDesc.setVisibility(View.GONE);
//                                tvServiceTitle.setVisibility(View.GONE);
                            } else {
                             //   tvPriceDesc.setText("" + mGetSecondDiagnosisSettingEntity.getPriceRemark());
                            }


                        }
                    });
                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();


                QueryServiceItemDivideRation("1110");
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    /*    mDataList = new ArrayList<>();
        for (int i = 0;i < 20;i++) {
            SymptomEntity SymptomEntity = new SymptomEntity();
            SymptomEntity.setSymptom("喉癌"+i);
            mDataList.add(SymptomEntity);
        }
        mSymptomSelectAdapter.setDatalist(mDataList);*/
    }

    private void SaveSecondDiagnosisSetting() {

        String json = mGson.toJson(mGetSecondDiagnosisSettingEntity);
        loading(true);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveSecondDiagnosisSetting).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("修改成功");
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        toast("保存失败");
                    }

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
    private void QueryServiceItemDivideRation(String SericeItemCode) {
        Map<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)) + "");
        params.put("SericeItemCode", SericeItemCode + "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);mGetSecondDiagnosisSettingEntity

        mMyOkhttp.get().url(HttpUrl.QueryServiceItemDivideRation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ItemDivideRationEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ItemDivideRationEntity> entity) {
                try {
                    if (entity.getCode() == 0) {
                        mItemDivideRationEntity=entity;
                        if (mGetSecondDiagnosisSettingEntity.getIsRecommend()==1){
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

    public void  refreshAdviceValue(ResponseEntity<ItemDivideRationEntity> entity){
        if (mGetSecondDiagnosisSettingEntity!=null){
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
