package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.iInterface.SignSuccessClickLister;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.SignRuleAdapter;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.DoctorSignEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SignRuleEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.GroupButtonDialog;
import com.newdjk.doctor.views.SignSuccessDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   MyPointsActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/21 9:45
 *  @描述：
 */
public class MyPointsActivity extends BasicActivity {
    private static final String TAG = "MyPointsActivity";
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
    @BindView(R.id.im_appicon)
    CircleImageView imAppicon;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_doctor_zhicheng)
    TextView tvDoctorZhicheng;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.lv_sign_button)
    LinearLayout lvSignButton;


    private boolean mIssign;
    private SignRuleAdapter mSignRuleAdapter;
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    private List<SignRuleEntity.DataBean> list = new ArrayList<>();
    private GroupButtonDialog mDialog;
    private SignSuccessDialog mSingleButtonDialog;
    private int point=0;

    @Override
    protected int initViewResId() {
        return R.layout.activity_mypoint;
    }

    @Override
    protected void initView() {

        initBackTitle("我的积分").setRightText("积分记录");
        mDoctorInfoByIdEntity = (DoctorInfoByIdEntity) getIntent().getSerializableExtra("doctorInfoByIdEntity");
        tvDoctorName.setText(SpUtils.getString(Contants.Name));
        mSignRuleAdapter = new SignRuleAdapter(list);

        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mSignRuleAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }




    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
        tvSign.setOnClickListener(this);
        mSignRuleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (list.get(position).getIntegralTypeCode()) {
                    case "101"://推荐医生  跳转推荐医生二维码
                        Intent audio = new Intent(MyPointsActivity.this, MyPointsRewardActivity.class);
                        startActivity(audio);
                        break;
                    case "102"://患者关注 跳转我的二维码
                        if (mDoctorInfoByIdEntity == null) {
                            return;
                        } else {
                            Intent idCardIntent = new Intent(MyPointsActivity.this, DoctorHomeCardActivity.class);
                            idCardIntent.putExtra("title", "我的名片");
                            idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                            startActivity(idCardIntent);
                        }
                        break;
                    case "103"://患者报道  跳转我的二维码
                        if (mDoctorInfoByIdEntity == null) {
                            return;
                        } else {
                            Intent idCardIntent = new Intent(MyPointsActivity.this, DoctorHomeCardActivity.class);
                            idCardIntent.putExtra("title", "我的名片");
                            idCardIntent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                            startActivity(idCardIntent);
                        }
                        break;
                    case "104": //销售积分  跳转我的服务
                        //先弹窗
                        Intent serviceSettingIntent = new Intent(MyPointsActivity.this, ServiceSettingActivity.class);
                        startActivity(serviceSettingIntent);

                        break;
                    case "105"://开处方单  跳转患者列表
                        //先弹窗
                        showPointDialog("您可前往【患者】列表为患者开具处方单。");
                        break;
                    case "106"://首次注册  跳转认证
                        int mStatus = SpUtils.getInt(Contants.Status, 0);
                        if (mStatus != 1) {
                            AppUtils.checkAuthenStatus(mStatus, MyPointsActivity.this);
                        } else {

                        }
                        break;
                    case "107"://签到积分  跳转我的积分
                        Intent intent3 = new Intent(MyPointsActivity.this, MyPointsRecodeActivity.class);
                        startActivity(intent3);
                        break;
                    case "108"://药品销售 跳转患者列表
                        //先弹窗
                        showPointDialog("您可前往【患者】列表为患者开具处方单。");

                        break;
                }
            }
        });
    }

    private void showPointDialog(String s) {
        if (mDialog == null) {
            mDialog = new GroupButtonDialog(MyPointsActivity.this);
        }
        mDialog.show(null, s, new GroupButtonDialog.DialogListener() {
            @Override
            public void cancel() {
                mDialog = null;
            }

            @Override
            public void confirm() {
                MyApplication.tag = 1;
                Intent intent = new Intent(MyPointsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {
        checkDoctorSignStatus();
        getSignList();
    }


    private void getSignList() {
        list.clear();
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        mMyOkhttp.get().url(HttpUrl.getSignList).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<SignRuleEntity>() {
            @Override
            public void onSuccess(int statusCode, SignRuleEntity response) {
                if (response.getCode() == 0) {
                    list = response.getData();
                    mSignRuleAdapter.setNewData(list);
                    Log.d(TAG, "数据长度" + list.size());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }

        });
    }

    private void checkDoctorSignStatus() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        mMyOkhttp.get().url(HttpUrl.isDoctorSign).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorSignEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorSignEntity> response) {

                if (response.getCode() == 0) {
                    try {
                        mIssign = response.getData().isIsSignin();

                        if (mIssign) {
                            tvSign.setText("今日已签到");
                            tvSign.setBackgroundResource(R.drawable.shape_sign_point_sign);
                        }else {
                        point=    response.getData().getSigninEarnIntegral();
                            tvSign.setText("立即签到+"+point+"分");
                        }
                        if (response.getData().isOpenSignin()) {
                            lvSignButton.setVisibility(View.VISIBLE);
                        } else {
                            lvSignButton.setVisibility(View.GONE);
                        }
                        tvPoint.setText(response.getData().getCurrentIntegral() == 0 ? "0" : (response.getData().getCurrentIntegral() + ""));
                        tvDoctorZhicheng.setText(TextUtils.isEmpty(response.getData().getPosition()) ? "--" : response.getData().getPosition());
//                        Glide.with(MyApplication.getContext())
//                                .load(response.getData().getPicturePath())
//                                .dontAnimate()
//                                .error(R.drawable.doctor_default_img)
//                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(imAppicon);
                        GlideUtils.loadDoctorImage(response.getData().getPicturePath(),imAppicon);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent audio = new Intent(MyPointsActivity.this, MyPointsRecodeActivity.class);
                startActivity(audio);
                break;

            case R.id.tv_sign:
                if (mIssign) {
                    toast("今天已签到");
                    // showSignDialog();
                } else {
                    sign();
                }
                break;
        }
    }

    private void sign() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        params.put("DrName", SpUtils.getString(Contants.Name));
        mMyOkhttp.post().url(HttpUrl.sign).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity response) {

                if (response.getCode() == 0) {
                    tvSign.setBackgroundResource(R.drawable.shape_sign_point_sign);
                    tvSign.setText("今日已签到");
                    showSignDialog();
                } else {
                    toast(response.getMessage() + "");
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }


        });
    }

    private void showSignDialog() {
        SignSuccessDialog.Builder builder = new SignSuccessDialog.Builder(this);
        mSingleButtonDialog = builder.setMessage("wwwww")
                .setSingleButton(point+"", new SignSuccessClickLister() {
                    @Override
                    public void onPositiveButtonClick(View view) {
                        checkDoctorSignStatus();
                        mSingleButtonDialog.dismiss();
                        mSingleButtonDialog = null;
                    }

                })
                .createSingleButtonDialog();
        mSingleButtonDialog.show();

        Window dialogWindow = mSingleButtonDialog.getWindow();
        WindowManager m = MyPointsActivity.this.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);

    }


}
