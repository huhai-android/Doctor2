package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.FlexWrap;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.DiseaseLabelAdapter;
import com.newdjk.doctor.ui.entity.DiseaseLabelEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.GlideRoundImage;
import com.newdjk.doctor.views.ItemView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author EDZ
 */ /*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui
 *  @文件名:   AdsDialog
 *  @创建者:   huhai
 *  @创建时间:  2019/3/12 10:43
 *  @描述：广告对话框
 */
public class FenjiCooperationActivity extends BasicActivity {


    private static final String TAG = "Fenji";
    private static List<DiseaseLabelEntity> list = new ArrayList<>();
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
    @BindView(R.id.civImg)
    ImageView civImg;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.mFunDepartment)
    TextView mFunDepartment;
    @BindView(R.id.mlevel)
    TextView mlevel;
    @BindView(R.id.tv_disease_label)
    ItemView tvDiseaseLabel;
    @BindView(R.id.rv_selected)
    RecyclerView rvSelected;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_sure)
    AppCompatButton btnSure;
    private DiseaseLabelAdapter mAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_fenji_cooperation;
    }

    @Override
    protected void initView() {
        initBackTitle("分级转诊");
        // 已选中单词
        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexWrap(FlexWrap.WRAP);
        layoutManager2.setAlignItems(AlignItems.STRETCH);
        rvSelected.setLayoutManager(layoutManager2);

        mAdapter = new DiseaseLabelAdapter(R.layout.disease_label_item, list);
        rvSelected.setAdapter(mAdapter);
        hospital.setText(MyApplication.mDoctorInfoByIdEntity.getHospitalName());
        tvName.setText(MyApplication.mDoctorInfoByIdEntity.getDrName());
        mFunDepartment.setText(MyApplication.mDoctorInfoByIdEntity.getDepartmentName());
        title.setText(MyApplication.mDoctorInfoByIdEntity.getPositionName());
        Glide.with(FenjiCooperationActivity.this)
                .load(MyApplication.mDoctorInfoByIdEntity.getPicturePath())
                .placeholder(R.drawable.doctor_default_img).error(R.drawable.doctor_default_img)
                .transform(new CenterCrop(FenjiCooperationActivity.this), new GlideRoundImage(FenjiCooperationActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(civImg);


    }

    @Override
    protected void initListener() {
        tvDiseaseLabel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.im_delete) {
                    list.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_disease_label:
                Intent diseaselabel = new Intent(mContext, DiseaseLabelActivity.class);
                startActivityForResult(diseaselabel, 0);
                break;
            case R.id.btn_sure:
                if (!cbAgreement.isChecked()){
                    toast("请勾选分级合作协议");
                   return;
                }
                openFenjiZhuanzhen();
                break;
        }

    }

    private void openFenjiZhuanzhen() {

        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        String url = HttpUrl.openDoctorDisGroup + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        mMyOkhttp.post().url(url).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {
                loading(false);
                if (entituy.getCode() == 0) {
                    Intent fenjizhuanzhen = new Intent(mContext, FenjiZhuanzhenActivity.class);
                    startActivity(fenjizhuanzhen);
                    finish();

                } else {
                    toast(entituy.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            String result = data.getStringExtra("result");
            Log.d(TAG, "获取疾病标签" + result);
            jsonToList(result);
            mAdapter.setNewData(list);
        }
    }

    public static List<DiseaseLabelEntity> jsonToList(String s) {
        Gson gs = new Gson();
        list.clear();
        List<DiseaseLabelEntity> dalist = gs.fromJson(s, new TypeToken<List<DiseaseLabelEntity>>() {
        }.getType());
        list.addAll(dalist); //把JSON格式的字符串转为List
        return list;
    }
}

