package com.newdjk.doctor.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.CertInfoEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.ItemView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.EnvType;

public class MyCertActivity extends BasicActivity {
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
    @BindView(R.id.my_cert)
    ItemView myCert;
    @BindView(R.id.find_cert)
    ItemView findCert;
    @BindView(R.id.reset_password)
    ItemView resetPassword;
    @BindView(R.id.cert_manager)
    ItemView certManager;
    @BindView(R.id.cert_update)
    ItemView certUpdate;
    @BindView(R.id.tv_desc_sign)
    TextView tvDescSign;
    @BindView(R.id.tv_help_sign)
    TextView tvHelpSign;
    private Gson mGson;

    @Override
    protected int initViewResId() {
        return R.layout.cert;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        initBackTitle("证书管理");
        tvDescSign.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvHelpSign.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initListener() {
        myCert.setOnClickListener(this);
        findCert.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        certManager.setOnClickListener(this);
        certUpdate.setOnClickListener(this);
        tvDescSign.setOnClickListener(this);
        tvHelpSign.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (BuildConfig.DEBUG) {
            BJCASDK.getInstance().setServerUrl(EnvType.INTEGRATE);
        } else {
            BJCASDK.getInstance().setServerUrl(EnvType.PUBLIC);
        }
        BJCASDK.getInstance().getUserInfo(mContext, Contants.clientId, new YWXListener() {
            @Override
            public void callback(String s) {
                Log.d("chat", "证书" + s);
                try {
                    Gson mg = new Gson();
                    CertInfoEntity certInfoEntity = mg.fromJson(s, CertInfoEntity.class);
                    //判断证书是否过期
//                    Long endtime = date2TimeStamp(certInfoEntity.getEndTime(), "yyyy-MM-dd HH:mm:ss");
//                    Long nowtime = System.currentTimeMillis();

                } catch (Exception e) {

                }
            }
        });

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.my_cert:
                BJCASDK.getInstance().showCertActivity(this, Contants.clientId, new YWXListener() {
                    @Override
                    public void callback(String s) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                        String message = yWXListenerEntity.getMessage();
                        String status = yWXListenerEntity.getStatus();
                        // Toast.makeText(MyCertActivity.this,message,Toast.LENGTH_SHORT).show();
                        if (status != null && status.equals("0")) {

                        } else {
                            Toast.makeText(MyCertActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                break;
            case R.id.find_cert:

                BJCASDK.getInstance().certDown(this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                    @Override
                    public void callback(String s) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                        String message = yWXListenerEntity.getMessage();
                        String status = yWXListenerEntity.getStatus();
                        // Toast.makeText(MyCertActivity.this,message,Toast.LENGTH_SHORT).show();
                        if (status != null && status.equals("0")) {
                            Toast.makeText(MyCertActivity.this, "你已成功找回证书", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(MyCertActivity.this,message,Toast.LENGTH_SHORT).show();
                            Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.reset_password:
                BJCASDK.getInstance().certResetPin(MyCertActivity.this, Contants.clientId, new YWXListener() {
                    @Override
                    public void callback(String msg) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                        String message = yWXListenerEntity.getMessage();
                        String status = yWXListenerEntity.getStatus();
                        // Toast.makeText(MyCertActivity.this,message,Toast.LENGTH_SHORT).show();
                        if (status != null && status.equals("0")) {
                            Toast.makeText(MyCertActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyCertActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.cert_manager:
                boolean isExists = BJCASDK.getInstance().existsCert(this);
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(this);
                if (!isExists) {
                    BJCASDK.getInstance().certDown(mActivity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                        @Override
                        public void callback(String s) {
                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                            String status = yWXListenerEntity.getStatus();
                            String message = yWXListenerEntity.getMessage();
                            if (status != null && status.equals("0")) {
                                boolean ExistStamp = BJCASDK.getInstance().existsStamp(MyCertActivity.this);
                                if (!ExistStamp) {
                                    BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                                        @Override
                                        public void callback(String msg) {
                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                            String status = yWXListenerEntity.getStatus();
                                            String message = yWXListenerEntity.getMessage();
                                            if (status != null && status.equals("0")) {
                                                Toast.makeText(mActivity, "设置签章成功", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //  Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                                ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                            }
                                        }
                                    });
                                } else {
                                    String path = BJCASDK.getInstance().getStampPic(mContext);
                                    alertDialog(path);
                                }
                            } else {
                                //   Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                Toast.makeText(mActivity, "下载证书失败，请重试!(" + status + ")", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                } else {
                    //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                    Log.i("zdp", "证书已下载");
                    if (!ExistStamp) {
                        BJCASDK.getInstance().drawStamp(mActivity, Contants.clientId, new YWXListener() {
                            @Override
                            public void callback(String msg) {
                                YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                String status = yWXListenerEntity.getStatus();
                                String message = yWXListenerEntity.getMessage();
                                if (status != null && status.equals("0")) {
                                    Toast.makeText(mActivity, "设置签章成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                                    ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                                }
                            }
                        });
                    } else {

                        String path = BJCASDK.getInstance().getStampPic(mContext);
                        alertDialog(path);
                    }

                }

                break;
            case R.id.cert_update:
                BJCASDK.getInstance().certUpdate(MyCertActivity.this, Contants.clientId, new YWXListener() {
                    @Override
                    public void callback(String msg) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                        String message = yWXListenerEntity.getMessage();
                        Toast.makeText(MyCertActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.tv_desc_sign:

                Intent intent = new Intent(MyCertActivity.this, PrescriptionActivity.class);
                intent.putExtra("type", 14);
                startActivity(intent);
                break;

            case R.id.tv_help_sign:
                Intent helpintent = new Intent(MyCertActivity.this, PrescriptionActivity.class);
                helpintent.putExtra("type", 15);
                startActivity(helpintent);

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void alertDialog(String path) {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.setting_cert, null);
        dialog.setContentView(view);
        Button sure = view.findViewById(R.id.btn_positive_custom_dialog);
        sure.setText("修改");
        Button cancel = view.findViewById(R.id.btn_negative_custom_dialog);
        ImageView image = view.findViewById(R.id.cert_image);
        Bitmap bitmap = ImageBase64.base64ToBitmap(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(MyApplication.getContext())
                .load(bytes)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BJCASDK.getInstance().drawStamp(MyCertActivity.this, Contants.clientId, new YWXListener() {
                    @Override
                    public void callback(String msg) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                        String message = yWXListenerEntity.getMessage();
                        String status = yWXListenerEntity.getStatus();
                        if (status != null && status.equals("0")) {
                            Toast.makeText(mActivity, "设置签章成功", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(mActivity,message,Toast.LENGTH_SHORT).show();
                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                        }

                    }
                });
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
