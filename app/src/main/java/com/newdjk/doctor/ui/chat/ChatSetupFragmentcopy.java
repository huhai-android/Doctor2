package com.newdjk.doctor.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ServiceItemPriceEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatSetupFragmentcopy extends BasicFragment {
    @Override
    protected int initViewResId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }


//    Unbinder unbinder;
//    @BindView(R.id.btn_change_value)
//    AppCompatButton btnChangeValue;
//    @BindView(R.id.tv_price)
//    TextView tvPrice;
//    @BindView(R.id.et_price)
//    EditText etPrice;
//public  String accountid;
//    public static ChatSetupFragmentcopy newInstance() {
//        Bundle args = new Bundle();
//        ChatSetupFragmentcopy myFragment = new ChatSetupFragmentcopy();
//        myFragment.setArguments(args);
//        return myFragment;
//    }
//
//    @Override
//    protected int initViewResId() {
//        return R.layout.newchatfragment3;
//    }
//
//
//    @Override
//    protected void initView() {
//
//    }
//
//    @Override
//    protected void initListener() {
//        btnChangeValue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //toast("该功能暂时未开通");
//                if (TextUtils.isEmpty(etPrice.getText().toString())){
//                    toast("设置价格不能为空");
//                    return;
//                }
//                if (Double.parseDouble(etPrice.getText().toString())<0){
//                    toast("设置价格不能小于0");
//                    return;
//                }
//                    setprice();
//            }
//        });
//    }
//
//    private void setprice() {
//        loading(true);
//        Map<String, String> headMap = new HashMap<>();
//        headMap.put("Authorization", SpUtils.getString(Contants.Token));
//        HashMap<String, String> params = new HashMap<>();
//        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
//        params.put("AccountId", accountid);
//        params.put("ServiceItemCode", "1101");
//        params.put("Price",etPrice.getText().toString());
//        mMyOkhttp.post().url(HttpUrl.SaveDrServiceItemPrice).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
//
//            @Override
//            public void onSuccess(int statusCode, ResponseEntity response) {
//                loading(false);
//               if (response.getCode()==0){
//                   tvPrice.setText("¥" + etPrice.getText().toString() + "");
//                   toast(response.getMessage()+"");
//               }else {
//                   toast("设置价格失败");
//               }
//            }
//
//
//            @Override
//            public void onFailures(int statusCode, String errorMsg) {
//                toast(errorMsg);
//                loading(false);
//            }
//        });
//
//    }
//
//
//    @Override
//    protected void initData() {
//        accountid = getActivity().getIntent().getStringExtra("AccountId");
//        getprice(accountid);
//
//    }
//
//    private void getprice(String accountid) {
//        Map<String, String> headMap = new HashMap<>();
//        headMap.put("Authorization", SpUtils.getString(Contants.Token));
//        HashMap<String, String> params = new HashMap<>();
//        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
//        params.put("AccountId", accountid);
//        params.put("ServiceItemCode", "1101");
//        mMyOkhttp.get().url(HttpUrl.GetDrServiceItemPriceForSet).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ServiceItemPriceEntity>>() {
//
//            @Override
//            public void onSuccess(int statusCode, ResponseEntity<ServiceItemPriceEntity> response) {
//
//                tvPrice.setText("¥" + response.getData().getPrice() + "");
//            }
//
//
//            @Override
//            public void onFailures(int statusCode, String errorMsg) {
//                toast(errorMsg);
//            }
//        });
//    }
//
//
//    @Override
//    protected void otherViewClick(View view) {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        //  mInquiryRecordListEntity = (InquiryRecordListEntity)getArguments().getSerializable("consultMessage");
//        super.onCreate(savedInstanceState);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

}