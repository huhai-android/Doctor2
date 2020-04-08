package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.AgreementActivity;
import com.newdjk.doctor.ui.entity.AboutUsEntity;
import com.newdjk.doctor.ui.entity.AppEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ServiceItemPriceEntity;
import com.newdjk.doctor.ui.entity.SetValueEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.AppUpdateUtils;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.HomeUtils;
import com.newdjk.doctor.utils.MathUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.SystemUitl;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newdjk.doctor.MyApplication.getContext;


public class SetTuwenValueActivity extends BasicActivity {


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
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.btn_change_value)
    AppCompatButton btnChangeValue;
    public  String accountid;

    @Override
    protected int initViewResId() {
        return R.layout.activity_settuwenvalue;
    }

    @Override
    protected void initView() {
        initTitle("个性化定价").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void initListener() {

        btnChangeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toast("该功能暂时未开通");
                if (TextUtils.isEmpty(etPrice.getText().toString())){
                    toast("设置价格不能为空");
                    return;
                }
                if (Double.parseDouble(etPrice.getText().toString())<0){
                    toast("设置价格不能小于0");
                    return;
                }
                setprice();
            }
        });
    }

    @Override
    protected void initData() {
        accountid = getIntent().getStringExtra("AccountId");
        getprice(accountid);
    }



    @Override
    protected void otherViewClick(View view) {


    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private void setprice() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("AccountId", accountid);
        params.put("ServiceItemCode", "1101");
        params.put("Price",etPrice.getText().toString());
        mMyOkhttp.post().url(HttpUrl.SaveDrServiceItemPrice).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {

            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                loading(false);
                if (response.getCode()==0){
                    tvPrice.setText("¥" + MathUtils.getDealValue(etPrice.getText().toString() )+ "");
                    toast(response.getMessage()+"");
                    EventBus.getDefault().post(new SetValueEntity(true));
                    finish();

                }else {
                    toast("设置价格失败");
                }
            }


            @Override
            public void onFailures(int statusCode, String errorMsg) {
                toast(errorMsg);
                loading(false);
            }
        });

    }

    private void getprice(String accountid) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("AccountId", accountid);
        params.put("ServiceItemCode", "1101");
        mMyOkhttp.get().url(HttpUrl.GetDrServiceItemPriceForSet).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ServiceItemPriceEntity>>() {

            @Override
            public void onSuccess(int statusCode, ResponseEntity<ServiceItemPriceEntity> response) {


                //是否已设置单独价格（0：未设置，1：已设置，2：未开通服务）
                if (response.getData().getIsSet()==0){
                    tvPrice.setText("¥" +MathUtils.getDealValue( response.getData().getCommonPrice()) + "");

                }else {
                    tvPrice.setText("¥" +MathUtils.getDealValue( response.getData().getPrice()) + "");
                }

            }


            @Override
            public void onFailures(int statusCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }
}
