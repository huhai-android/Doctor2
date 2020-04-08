package com.newdjk.doctor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.ServiceSettingActivity;
import com.newdjk.doctor.ui.activity.SetTuwenValueActivity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ServiceItemPriceEntity;
import com.newdjk.doctor.ui.entity.SetValueEntity;
import com.newdjk.doctor.ui.entity.UpdateImMessageEntity;
import com.newdjk.doctor.utils.MathUtils;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatSetupFragment extends BasicFragment {


    @BindView(R.id.lv_tosetvalue)
    LinearLayout lvTosetvalue;
    Unbinder unbinder;
    public String accountid;
    @BindView(R.id.tv_value)
    TextView tvValue;
    private ServiceItemPriceEntity serviceItemPriceEntity;
    private String tag="ChatSetupFragment";

    public static ChatSetupFragment newInstance() {
        Bundle args = new Bundle();
            ChatSetupFragment myFragment = new ChatSetupFragment();
        myFragment.setArguments(args);
        return myFragment;
    }
    @Override
    protected int initViewResId() {
        return R.layout.newchatfragment3;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        lvTosetvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////  是否已设置单独价格（0：未设置，1：已设置，2：未开通服务）
                if (serviceItemPriceEntity!=null){

                    if (serviceItemPriceEntity.getIsSet() == 0) {
                        Intent serviceSettingIntent = new Intent(getContext(), SetTuwenValueActivity.class);
                        serviceSettingIntent.putExtra("AccountId", accountid);
                        startActivity(serviceSettingIntent);


                    } else if (serviceItemPriceEntity.getIsSet() == 1) {
                        Intent serviceSettingIntent = new Intent(getContext(), SetTuwenValueActivity.class);
                        serviceSettingIntent.putExtra("AccountId", accountid);
                        startActivity(serviceSettingIntent);

                    } else if (serviceItemPriceEntity.getIsSet() == 2) {

                        Intent serviceSettingIntent = new Intent(getContext(), ServiceSettingActivity.class);
                        startActivity(serviceSettingIntent);
                    }

                }


            }
        });
    }

    @Override
    protected void initData() {

        accountid = getActivity().getIntent().getStringExtra("AccountId");
        getprice(accountid);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

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
                //  是否已设置单独价格（0：未设置，1：已设置，2：未开通服务）

                if (response.getCode() == 0) {
                    serviceItemPriceEntity = response.getData();
                    if (response.getData().getIsSet() == 0) {
                        tvValue.setText("未设置");
                    } else if (response.getData().getIsSet() == 1) {
                        tvValue.setText("¥" + MathUtils.getDealValue(  response.getData().getPrice() )+ "");
                    } else if (response.getData().getIsSet() == 2) {
                        tvValue.setText("未开通图文问诊，去开通");

                    }
                }

            }


            @Override
            public void onFailures(int statusCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(SetValueEntity setValueEntity) {
        if (setValueEntity.isTure){
            Log.d(tag,"设置价格成功");
            getprice(accountid);
        }

    }

}