package com.newdjk.doctor.ui.activity.min;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.DoctorHomeCardActivity;
import com.newdjk.doctor.ui.activity.UpdateInfoActivity;
import com.newdjk.doctor.ui.activity.UpdateQualifications;
import com.newdjk.doctor.ui.activity.UploadImageActivity;
import com.newdjk.doctor.ui.activity.login.ChooseTitleActivity;
import com.newdjk.doctor.ui.activity.login.RegisterSuccessActivity;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TitleEntity;
import com.newdjk.doctor.ui.entity.UpLoadImageSuccess;
import com.newdjk.doctor.ui.entity.UpdateImageView;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.GlideCacheUtil;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.AlbumsTypeSelectedpopwin;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.ItemView;
import com.newdjk.doctor.views.LoadDialog;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonalDataActivity extends BasicActivity {

    private static final String TAG ="PersonalDataActivity";
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
    @BindView(R.id.mArrow)
    ImageView mArrow;
    @BindView(R.id.mCircleImageView)
    CircleImageView mCircleImageView;
    @BindView(R.id.mFunName)
    ItemView mFunName;
    @BindView(R.id.mFunGender)
    ItemView mFunGender;
    @BindView(R.id.mFunPhone)
    ItemView mFunPhone;
    @BindView(R.id.fun_yu_e)
    ItemView funYuE;
    @BindView(R.id.mFunBirth)
    ItemView mFunBirth;
    @BindView(R.id.mFunHospitol)
    ItemView mFunHospitol;
    @BindView(R.id.mFunDepartment)
    ItemView mFunDepartment;
    @BindView(R.id.mFunTitle)
    ItemView mFunTitle;
    @BindView(R.id.mFunGoodAt)
    ItemView mFunGoodAt;
    @BindView(R.id.mFunIntroduction)
    ItemView mFunIntroduction;
    @BindView(R.id.personal_image)
    RelativeLayout personalImage;
    @BindView(R.id.title)
    ItemView title;
    private String mImgPath;
    private AlbumsTypeSelectedpopwin AlbumsTypeSelectedpopwin;
    private String mGoodAtMessage;
    private String mTitleMessage;
    private String mIntrodutionMessage;
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    private Gson mGson;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private int mStatus;
    private String mFlag;

    @Override
    protected int initViewResId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initView() {
        GlideCacheUtil.getInstance().clearImageDiskCache(this);
        GlideCacheUtil.getInstance().clearImageMemoryCache(this);
        mGson = new Gson();
        initBackTitle("个人中心").setRightText("保存");
    }

    @Override
    protected void initListener() {
        mFunBirth.setOnClickListener(this);
        funYuE.setOnClickListener(this);
        mFunGoodAt.setOnClickListener(this);
        personalImage.setOnClickListener(this);
        // title.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        mFunTitle.setOnClickListener(this);
        mFunIntroduction.setOnClickListener(this);
        mFunPhone.setOnClickListener(this);

    }








    @Override
    protected void initData() {

        queryDoctorInfoByDrId();
//        mDoctorInfoByIdEntity = (DoctorInfoByIdEntity) getIntent().getSerializableExtra("doctorInfoByIdEntity");
//        int sexType = SpUtils.getInt(Contants.Sex, 0);
//        mStatus = SpUtils.getInt(Contants.Status, 0);
//        switch (mStatus) {
//            case 0:
//                mFunPhone.setRightText("未认证");
//                mFlag = "0";
//                break;
//            case 1:
//                mFunPhone.setRightText("已认证");
//                break;
//            case 2:
//                mFunPhone.setRightText("认证失败");
//                mFlag = "2";
//                break;
//            case 3:
//                mFunPhone.setRightText("认证中");
//                mFlag = "3";
//                break;
//        }
//        String sex = "";
//        switch (sexType) {
//            case 1:
//                sex = "男";
//                break;
//            case 2:
//                sex = "女";
//                break;
//            case 3:
//                sex = "未知";
//                break;
//        }
//        mFunName.setRightText(mDoctorInfoByIdEntity.getDrName());
//        mFunGender.setRightText(sex);
//        //    mFunPhone.setRightText(mDoctorInfoByIdEntity.getMobile());
//        //   funYuE.setRightText();
//        mFunBirth.setRightImage(getResources().getDrawable(R.mipmap.doctor_home_card));
//        mFunHospitol.setRightText(mDoctorInfoByIdEntity.getHospitalName());
//        mFunDepartment.setRightText(mDoctorInfoByIdEntity.getDepartmentName());
//        mFunTitle.setRightText(mDoctorInfoByIdEntity.getTreatMent());
//        title.setRightText(mDoctorInfoByIdEntity.getPositionName());
//        mFunGoodAt.setRightText(mDoctorInfoByIdEntity.getDoctorSkill());
//        mFunIntroduction.setRightText(mDoctorInfoByIdEntity.getDescription());
//        Log.i("PersonalDataActivity", "path=" + mDoctorInfoByIdEntity.getPicturePath());
//        Glide.with(MyApplication.getContext())
//                .load(mDoctorInfoByIdEntity.getPicturePath())
//
//                //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mCircleImageView);
        //  queryDoctorInfoByDrId();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.fun_yu_e:
                Intent intentConfiger = null;
                if (mStatus == 1) {
                    intentConfiger = new Intent(PersonalDataActivity.this, UpdateQualifications.class);
                } else {
                    intentConfiger = new Intent(this, RegisterSuccessActivity.class);
                    intentConfiger.putExtra("flag", mFlag);
                }

                startActivityForResult(intentConfiger, 7);

//                Intent updataAuthenticationIntent = new Intent(PersonalDataActivity.this, Authentication3ActivityNew.class);
//                startActivityForResult(updataAuthenticationIntent, 7);
                break;
            case R.id.mFunGoodAt:
                Intent funGoodAtIntent = new Intent(PersonalDataActivity.this, UpdateInfoActivity.class);
                funGoodAtIntent.putExtra("action", "funGoodAt");
                funGoodAtIntent.putExtra("data", mFunGoodAt.getTvItemRightText().getText());
                startActivityForResult(funGoodAtIntent, 3);
                break;
            case R.id.mFunTitle:
                Intent funTitleIntent = new Intent(PersonalDataActivity.this, UpdateInfoActivity.class);
                funTitleIntent.putExtra("action", "funTitle");
                funTitleIntent.putExtra("data", mFunTitle.getTvItemRightText().getText());
                startActivityForResult(funTitleIntent, 4);
                break;
            case R.id.mFunIntroduction:
                Intent funIntroductionIntent = new Intent(PersonalDataActivity.this, UpdateInfoActivity.class);
                funIntroductionIntent.putExtra("data", mFunIntroduction.getTvItemRightText().getText());
                funIntroductionIntent.putExtra("action", "funIntroduction");
                startActivityForResult(funIntroductionIntent, 5);
                break;
            case R.id.mFunBirth:
                Intent intent = new Intent(this, DoctorHomeCardActivity.class);
                intent.putExtra("title", "我的名片");
                intent.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
                startActivity(intent);
                break;
            case R.id.personal_image:
//                if (checkPermission()) {
//                    AlbumsTypeSelectedpopwin = new AlbumsTypeSelectedpopwin(PersonalDataActivity.this, PersonalDataActivity.this);
//                    AlbumsTypeSelectedpopwin.showAtLocation(personalImage, Gravity.BOTTOM, 0, 0);
//                    WindowManager.LayoutParams lp = getWindow().getAttributes();
//                    lp.alpha = 0.7f;
//                    getWindow().setAttributes(lp);
//                    AlbumsTypeSelectedpopwin.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss() {
//                            WindowManager.LayoutParams lp = getWindow().getAttributes();
//                            lp.alpha = 1f;
//
//                            getWindow().setAttributes(lp);
//                        }
//                    });
//                }

                Intent intent2 = new Intent(this, UploadImageActivity.class);

                startActivity(intent2);
                break;
            case R.id.title:
                Log.i("PersonalDataActivity", "type=" + Contants.DocType);
                Intent mTitleIntent = new Intent(this, ChooseTitleActivity.class);
                mTitleIntent.putExtra("type", 9);
                startActivityForResult(mTitleIntent, 6);
               /* Intent intent = new Intent(PersonalDataActivity.this, HospitalMessageListActivity.class);
                intent.putExtra("action", 4);
                intent.putExtra("professionId", SpUtils.getInt(Contants.DocType,0));
                startActivityForResult(intent, 6);*/
                break;
            case R.id.tv_right:
                updateDoctorInfo();
                break;

            case R.id.mFunPhone:
                mStatus = SpUtils.getInt(Contants.Status, 0);
                if (mStatus != 1) {
                    AppUtils.checkAuthenStatus(mStatus, PersonalDataActivity.this);
                }
                break;
        }
    }

    private void queryDoctorInfoByDrId() {
        String url = HttpUrl.QueryDoctorInfoByDrId + "?DrId=" + SpUtils.getInt(Contants.Id, 0);

        mMyOkhttp.get().url(url).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorInfoByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, final ResponseEntity<DoctorInfoByIdEntity> entity) {
                if (entity.getCode() == 0) {
                    mDoctorInfoByIdEntity = entity.getData();
                    int sexType = SpUtils.getInt(Contants.Sex, 0);
                    mStatus = SpUtils.getInt(Contants.Status, 0);
                    switch (mStatus) {
                        case 0:
                            mFunPhone.setRightText("去认证");
                            mFlag = "0";
                            break;
                        case 1:
                            mFunPhone.setRightText("已认证");
                            break;
                        case 2:
                            mFunPhone.setRightText("认证失败");
                            mFlag = "2";
                            break;
                        case 3:
                            mFunPhone.setRightText("认证中");
                            mFlag = "3";
                            break;
                    }
                    String sex = "";
                    switch (sexType) {
                        case 1:
                            sex = "男";
                            break;
                        case 2:
                            sex = "女";
                            break;
                        case 3:
                            sex = "未知";
                            break;
                    }
                    mFunName.setRightText(mDoctorInfoByIdEntity.getDrName());
                    mFunGender.setRightText(sex);
                    //    mFunPhone.setRightText(mDoctorInfoByIdEntity.getMobile());
                    //   funYuE.setRightText();
                    mFunBirth.setRightImage(getResources().getDrawable(R.mipmap.doctor_home_card));
                    mFunHospitol.setRightText(mDoctorInfoByIdEntity.getHospitalName());
                    mFunDepartment.setRightText(mDoctorInfoByIdEntity.getDepartmentName());
                    mFunTitle.setRightText(mDoctorInfoByIdEntity.getTreatMent());
                    title.setRightText(mDoctorInfoByIdEntity.getPositionName());
                    mFunGoodAt.setRightText(mDoctorInfoByIdEntity.getDoctorSkill());
                    mFunIntroduction.setRightText(mDoctorInfoByIdEntity.getDescription());
                    Log.i("PersonalDataActivity", "path=" + mDoctorInfoByIdEntity.getPicturePath());
                    Glide.with(MyApplication.getContext())
                            .load(mDoctorInfoByIdEntity.getPicturePath())

                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mCircleImageView);

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpLoadImageSuccess upLoadImageSuccess) {
        Log.d(TAG,"上传图片成功");
        mDoctorInfoByIdEntity.setPicturePath(upLoadImageSuccess.getImagePath());
        Glide.with(MyApplication.getContext())
                .load(mDoctorInfoByIdEntity.getPicturePath())

                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCircleImageView);

    }



    private void updateDoctorInfo() {
        loading(true);
        String json = mGson.toJson(mDoctorInfoByIdEntity);
        Log.i("PersonalDataActivity", "json=" + json);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdateDoctorInfo).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    toast("更新成功");
                    EventBus.getDefault().post(new UpdateImageView(true));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //  String image64 = ImageBase64.encodeImage(mSelectedPictureDialog.getPicturePath());
                    String f1 = System.currentTimeMillis() + ".jpg";
                    String picturePath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f1;
                    File file1 = new File(picturePath1);
                    file1.getParentFile().mkdirs();
                    Log.i("zdp", picturePath1);
                    Uri uri1;

                    uri1 = Uri.fromFile(file1);
                    if (AlbumsTypeSelectedpopwin != null) {
                        Uri selectedImage1 = Uri.fromFile(new File(AlbumsTypeSelectedpopwin.getPicturePath()));
                        startCropActivity(selectedImage1, uri1);
                    }

                    break;
                case 2:
                    Log.i("zdp", "fdsf");

                    try {

                        String f = System.currentTimeMillis() + ".jpg";
                        String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                        File file = new File(picturePath);
                        file.getParentFile().mkdirs();
                        Log.i("zdp", picturePath);
                        Uri uri;
                       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
                        }
                        else {
                            uri = Uri.fromFile(file);
                        }*/
                        uri = Uri.fromFile(file);
                        Uri selectedImage = data.getData();
                        startCropActivity(selectedImage, uri);

                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        Log.e("PersonalDataActivity", "error=" + e.toString());
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    String goodAtMessage = data.getStringExtra("message");
                    if (goodAtMessage != null) {
                        mFunGoodAt.setRightText(goodAtMessage);
                        mDoctorInfoByIdEntity.setDoctorSkill(goodAtMessage);
                    }
                    break;
                case 4:
                    String titleMessage = data.getStringExtra("message");
                    mDoctorInfoByIdEntity.setTreatMent(titleMessage);
                    mFunTitle.setRightText(titleMessage);
                    break;
                case 5:
                    String introdutionMessage = data.getStringExtra("message");
                    mDoctorInfoByIdEntity.setDescription(introdutionMessage);
                    mFunIntroduction.setRightText(introdutionMessage);
                    break;
                case 6:
                    TitleEntity.DataBean titleBean = (TitleEntity.DataBean) data.getSerializableExtra("titleBean");
                    mDoctorInfoByIdEntity.setPosition(titleBean.getCategoryItemValue());
                    title.setRightText(titleBean.getCategoryItemName());
                    break;

                case UCrop.REQUEST_CROP:
                    // 裁剪照片
                    final Uri croppedUri = UCrop.getOutput(data);
                    try {
                        if (croppedUri != null) {

                            String path = croppedUri.getPath();  //获取照片路径
                            uploadPicture(path);
                            Glide.with(MyApplication.getContext())
                                    .load(croppedUri)
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mCircleImageView);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    toast(cropError.toString());
                    Log.i("RESULT_ERROR", "UCrop_RESULT_ERROR" + cropError.toString());
                    break;

            }
        }
    }

    public void startCropActivity(Uri uri, Uri destinationUri) {
        UCrop.Options crop = new UCrop.Options();
        // do not copy exif information to crop pictures
        // because png do not have exif and png is not Distinguishable
        crop.setCompressionFormat(Bitmap.CompressFormat.PNG);
        crop.setToolbarColor(Color.parseColor("#000000"));
        crop.setStatusBarColor(Color.parseColor("#000000"));
        crop.setHideBottomControls(true);
        crop.withMaxResultSize(512, 512);
        crop.withAspectRatio(1, 1);
        UCrop.of(uri, destinationUri)
                .withOptions(crop)
                .start(this);
    }

    private void uploadPicture(String path) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                Log.i("PersonalDataActivity", "size=" + image64.length());
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.DoctorImagUpload).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        if (entituy.getCode() == 0) {
                            mImgPath = entituy.getData().getSavePath();
                            mDoctorInfoByIdEntity.setPicturePath(mImgPath);
                            Log.i("PersonalDataActivity", "mImgPath=" + mImgPath);
                        } else {
                            toast(entituy.getMessage());
                        }

                       /* DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        if (imageType>3) {
                            if (!practiceLicense.getText().toString().equals("")) {
                                doctorRegImgEntity.setNumber(practiceLicense.getText().toString());
                            }
                            if (registerDate.getText().toString().equals("")) {
                                doctorRegImgEntity.setRegisterTime(registerDate.getText().toString());
                            }
                            if(registerValidity.getText().toString().equals("")) {
                                doctorRegImgEntity.setValidTime(registerValidity.getText().toString());
                            }
                        }
                        doctorRegImgEntity.setImgPath(entituy.getData().getSavePath());
                        doctorRegImgEntity.setImgType(imageType);
                        mList.add(doctorRegImgEntity);*/
                        Log.i("zdp", "statusCode=" + statusCode);
                        Log.i("zdp", "data=" + entituy.getData());
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(PersonalDataActivity.this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }


}
