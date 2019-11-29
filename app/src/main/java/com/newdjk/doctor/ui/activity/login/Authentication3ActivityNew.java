package com.newdjk.doctor.ui.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.MainActivity;
import com.newdjk.doctor.ui.activity.ShowBigImageActivity;
import com.newdjk.doctor.ui.entity.DocQualificationEntity;
import com.newdjk.doctor.ui.entity.Doctor;
import com.newdjk.doctor.ui.entity.DoctorImagSaveRequestEntity;
import com.newdjk.doctor.ui.entity.DoctorRegImgEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.uploadimagelib.MainConstant;
import com.newdjk.doctor.utils.GsonUtils;
import com.newdjk.doctor.utils.IdcardRecognise;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.DisplayUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MultiImageUploadView;
import com.newdjk.doctor.views.RoundImageUploadView;
import com.newdjk.doctor.views.SelectedPictureDialog;
import com.newdjk.doctor.views.SelectedPictureDialog2;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;

/**
 * Created by Struggle on 2018/10/13.
 */

public class Authentication3ActivityNew extends BasicActivity {


    private static final String TAG = "Authentication";
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

    @BindView(R.id.practice_license_number)
    TextView practiceLicenseNumber;
    @BindView(R.id.practice_license)
    EditText practiceLicense;
    @BindView(R.id.next_step)
    AppCompatButton nextStep;
    @BindView(R.id.practice_license_number_zi_ge)
    TextView practiceLicenseNumberZiGe;
    @BindView(R.id.practice_license_zi_ge)
    EditText practiceLicenseZiGe;
    @BindView(R.id.tv_dr_type)
    TextView tvDrType;
    @BindView(R.id.upload_view1)
    RoundImageUploadView uploadView1;
    @BindView(R.id.tv_pic_number1)
    TextView tvPicNumber1;
    @BindView(R.id.upload_view2)
    RoundImageUploadView uploadView2;
    @BindView(R.id.tv_pic_number2)
    TextView tvPicNumber2;
    @BindView(R.id.upload_view3)
    RoundImageUploadView uploadView3;
    @BindView(R.id.tv_pic_number3)
    TextView tvPicNumber3;


    private static final int IMG_REQUEST_CODE = 0x010;
    @BindView(R.id.ll_zi_ge)
    LinearLayout llZiGe;
    @BindView(R.id.ll_zhi_cheng)
    LinearLayout llZhiCheng;
    @BindView(R.id.rl_yi_shi_zi_ge)
    RelativeLayout rlYiShiZiGe;
    @BindView(R.id.tv_certificate_name)
    TextView tvCertificateName;
    @BindView(R.id.tv_checksimple)
    ImageView tvChecksimple;
    @BindView(R.id.tv_checksimple2)
    ImageView tvChecksimple2;
    @BindView(R.id.tv_checksimple3)
    ImageView tvChecksimple3;
    @BindView(R.id.im_simpleshow)
    ImageView imSimpleshow;

    private List<String> mLocalList1 = new ArrayList<>(); //上传的图片凭证的数据源 执业证
    private List<String> mLocalList2 = new ArrayList<>(); //上传的图片凭证的数据源 医师资格证
    private List<String> mLocalList3 = new ArrayList<>(); //上传的图片凭证的数据源 职称证
    private List<String> mSavePicList1 = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList2 = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList3 = new ArrayList<>(); //上传的图片凭证的数据源
    private List<DoctorRegImgEntity> mList;
    private Gson mGson;
    private int mFlag = -1;
    private String mFirstPicturePath;
    private String mSecondPicturePath;
    private String mName;
    private String mIdCard;
    private List<DocQualificationEntity.DataBean> mImageBeanList, mOtherList;
    private String mNumber1, mNumber2;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private String mDrType;
    private String mCategoryItemId;
    private String mJobname;
    private String mWords = "";
    private SelectedPictureDialog2 mSelectedPictureDialog;

    @Override
    protected int initViewResId() {
        return R.layout.activity_authentiction3_new;
    }

    @Override
    protected void initView() {


        initBackTitle("资质认证");


        mGson = new Gson();
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mIdCard = intent.getStringExtra("idCardNumber");
        mFirstPicturePath = intent.getStringExtra("firstPicturePath");
        mSecondPicturePath = intent.getStringExtra("secondPicturePath");

        mJobname = intent.getStringExtra(Contants.JobName);
        mDrType = String.valueOf(SpUtils.getInt(Contants.DocType, -1));
        mCategoryItemId = SpUtils.getString(Contants.categoryItemId);
        Log.d(TAG, "职称" + mJobname + "" + mDrType + "  " + mCategoryItemId);
       /* mDrType = SpUtils.getInt(Contants.DocType,-1);
        String s = "资格证编号";
        if (mDrType != -1){
            if (mDrType == 1){
                mTypeName = "医生";
            }else {
                mTypeName = "护士";
            }
            tvDrType.setText(new StringBuffer(mTypeName).append("资格证"));
            practiceLicenseNumberZiGe.setText(new StringBuffer(mTypeName).append(s));
            practiceLicenseZiGe.setHint(new StringBuffer("请输入").append(mTypeName).append(s));
        }*/
        if (mCategoryItemId != null) {
            if (mCategoryItemId.equals("30") || mCategoryItemId.equals("31")) {
                llZhiCheng.setVisibility(View.VISIBLE);
            }
        }
        if (mJobname.contains("主任医师") || mJobname.equals("主治医师")) {
            llZhiCheng.setVisibility(View.GONE);
        }

        mList = new ArrayList<>();

        if (mDrType.equals("1")) {

//            for (int i = 0; i < 3; i++) {
//
//                if (i == 0) {
//                    setUploadView(uploadView1, tvPicNumber1, mSavePicList1);
//                } else if (i == 1) {
//                    setUploadView(uploadView2, tvPicNumber2, mSavePicList2);
//                } else if (i == 2) {
//                    setUploadView(uploadView3, tvPicNumber3, mSavePicList3);
//                }
//            }
//        } else {
            llZhiCheng.setVisibility(View.GONE);
            llZiGe.setVisibility(View.GONE);
            rlYiShiZiGe.setVisibility(View.GONE);

        }
        setUploadView(uploadView1, tvPicNumber1, mSavePicList1);
        if (!TextUtils.isEmpty(mJobname)) {
            if (mJobname.contains("乡村")) {
                llZiGe.setVisibility(View.GONE);
                tvCertificateName.setText("请上传乡村医生执业证书或乡村全科执业助理医师资格证书");
            }


        }
        if (mDrType.equals("1")) {

            if (mJobname.contains("乡村医生")) {
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.xiangcundoctor)
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imSimpleshow);
            } else {
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.commondoctor)
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imSimpleshow);
            }

        } else {
            Glide.with(MyApplication.getContext())
                    .load(R.drawable.nursedoctor)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imSimpleshow);


        }
    }

    private void setUploadView(final RoundImageUploadView uploadView, final TextView tvNumber, final List<String> list) {
        uploadView.setAddHandlerImage(R.mipmap.image_add);
        uploadView.setMax(9);
        uploadView.setNumCol(4);
        uploadView.setImgMargin(DisplayUtil.dp2px(this, 10));
        uploadView.setCloseHandlerImage(R.drawable.ic_delete_photo);
        uploadView.setOnImageChangeListener(new MultiImageUploadView.OnImageChangeListener() {
            @Override
            public void onImageChage(List<File> imgFiles, int maxSize) {
                tvNumber.setText(String.format("(%d/%d)", imgFiles.size(), maxSize));
            }
        });
        uploadView.setPadding(0, 0, 0, 0);
        uploadView.setAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectImage(uploadView);
                mFlag = uploadView.getId();
            }
        });

        uploadView.setOnDelPicListener(new MultiImageUploadView.OnDelPicListener() {
            @Override
            public void onDelPicListener(int pos) {
                mFlag = uploadView.getId();
                if (mFlag == R.id.upload_view1) {
                    if (mSavePicList1.size() > 0) {
                        mSavePicList1.remove(pos);
                    }

                } else if (mFlag == R.id.upload_view2) {
                    if (mSavePicList2.size() > 0) {
                        mSavePicList2.remove(pos);
                    }

                } else if (mFlag == R.id.upload_view3) {
                    if (mSavePicList3.size() > 0) {
                        mSavePicList3.remove(pos);
                    }

                }
            }
        });

    }

    private void startSelectImage(RoundImageUploadView uploadView) {
//        BoxingConfig mulitImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
//                .needCamera(R.drawable.ic_camera)
//                .needGif()
//                .withMaxCount(uploadView.getMax() - uploadView.getFiles().size());
//        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withMediaPlaceHolderRes(R.drawable.ic_camera);
//
//        Boxing.of(singleImgConfig).
//                withIntent(this, BoxingActivity.class).
//                start(this, IMG_REQUEST_CODE);

        mSelectedPictureDialog = new SelectedPictureDialog2(Authentication3ActivityNew.this, "first");
        mSelectedPictureDialog.show();
    }

    @Override
    protected void initListener() {
        nextStep.setOnClickListener(this);
        tvChecksimple.setOnClickListener(this);
        tvChecksimple2.setOnClickListener(this);
        tvChecksimple3.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        checkPermission();
        obtainDoctorQualification();
    }

    /**
     * 证照类型1-身份证正面，2-身份证反面，3-手持身份证，4-执业证，5-资格证，6-职称证,7-结业证)
     * <p>
     * 职业证
     */
    private void obtainDoctorQualification() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        loading(true);
        mMyOkhttp.get().url(HttpUrl.QueryDoctorRegImagByDrId).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<DocQualificationEntity>() {
            @Override
            public void onSuccess(int statusCode, DocQualificationEntity response) {
                LoadDialog.clear();
                Log.e("==", "onSuccess: " + response.getCode() + "  " + response.getData());
                if (response.getCode() == 0) {
                    mImageBeanList = response.getData();
                    int len = mImageBeanList.size();
                    for (int i = 0; i < len; i++) {
                        DocQualificationEntity.DataBean bean = mImageBeanList.get(i);
                        Log.e("text", "onSuccess: " + bean.getImgType() + "  " + bean.getImgPath() + "  " + bean.getNumber());
                        if (bean.getImgType() == 4) {//职业证
                            mSavePicList1.add(bean.getImgPath());
                            mNumber1 = bean.getNumber();
                        } else if (bean.getImgType() == 5) {
                            mSavePicList2.add(bean.getImgPath());
                            mNumber2 = bean.getNumber();
                        } else if (bean.getImgType() == 6) {
                            mSavePicList3.add(bean.getImgPath());
                        }
                    }


                    for (String s1 : mSavePicList1) {
                        uploadView1.addFile(new File(s1));
                    }

                    for (String s2 : mSavePicList2) {
                        uploadView2.addFile(new File(s2));
                    }

                    for (String s3 : mSavePicList3) {
                        uploadView3.addFile(new File(s3));
                    }


                    if (mNumber1 != null) {
                        practiceLicense.setText(mNumber1);
                    }

                    if (mNumber2 != null) {
                        practiceLicenseZiGe.setText(mNumber2);
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
            case R.id.next_step:
                String zyz = practiceLicense.getText().toString().trim();
                String zige = practiceLicenseZiGe.getText().toString().trim();

                if (mDrType.equals("1")) {
                    if (mJobname.contains("乡村")) {
                        if (mSavePicList1.size() == 0) {
                            toast("请上传乡村医生执业证书或乡村全科执业助理医师资格证书！");
                            return;
                        }
//                        if (TextUtils.isEmpty(zyz)) {
//                            toast("请将执业证编号填写完整！");
//                            return;
//                        }

//                        if (mCategoryItemId != null) {
//                            if (mCategoryItemId.equals("44") || mCategoryItemId.equals("45")) {
//                                if (mSavePicList3.size() == 0) {
//                                    toast("请将职称证上传完整！");
//                                    return;
//                                }
//                            }
//                        }
                    } else {
                        if (mSavePicList1.size() == 0) {
                            toast("请将执业证上传完整！");
                            return;
                        }
//                        if (TextUtils.isEmpty(zyz)) {
//                            toast("请将执业证编号填写完整！");
//                            return;
//                        }

//                        if (mCategoryItemId != null) {
//                            if (mCategoryItemId.equals("44") || mCategoryItemId.equals("45")) {
//                                if (mSavePicList3.size() == 0) {
//                                    toast("请将职称证上传完整！");
//                                    return;
//                                }
//                            }
//                        }
                    }


                    loading(true);
                    for (String s1 : mSavePicList1) {
                        DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        doctorRegImgEntity.setImgPath(s1);
                        doctorRegImgEntity.setImgType(4);
                        doctorRegImgEntity.setNumber(zyz);
                        mList.add(doctorRegImgEntity);
                    }

//                    for (String s2 : mSavePicList2) {
//                        DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
//                        doctorRegImgEntity.setImgPath(s2);
//                        doctorRegImgEntity.setImgType(5);
//                        doctorRegImgEntity.setNumber(zige);
//                        mList.add(doctorRegImgEntity);
//                    }
//
//                    for (String s3 : mSavePicList3) {
//                        DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
//                        doctorRegImgEntity.setImgPath(s3);
//                        doctorRegImgEntity.setImgType(6);
//                        mList.add(doctorRegImgEntity);
//                    }
                } else {
                    if (mSavePicList1.size() == 0) {
                        toast("请将证书上传完整");
                        return;
                    }
//                    if (TextUtils.isEmpty(zyz)) {
//                        toast("请将执业证书编号填写完整");
//                        return;
//                    }

                    for (String s1 : mSavePicList1) {
                        DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        doctorRegImgEntity.setImgPath(s1);
                        doctorRegImgEntity.setImgType(4);
                        doctorRegImgEntity.setNumber(zyz);
                        mList.add(doctorRegImgEntity);
                    }
                }

                if (TextUtils.isEmpty(mIdCard) || !mIdCard.matches(MainConstant.ID_NUMBER)) {
                    toast("身份证号码错误，请返回重新填写！");
                }
                DoctorRegImgEntity doctorRegImgEntity1 = new DoctorRegImgEntity();
                doctorRegImgEntity1.setImgPath(mFirstPicturePath);
                doctorRegImgEntity1.setImgType(1);
                doctorRegImgEntity1.setNumber(mIdCard);
                mList.add(doctorRegImgEntity1);
                DoctorRegImgEntity doctorRegImgEntity2 = new DoctorRegImgEntity();
                doctorRegImgEntity2.setImgPath(mSecondPicturePath);
                doctorRegImgEntity2.setImgType(2);
                mList.add(doctorRegImgEntity2);

                DoctorImagSaveRequestEntity doctorImagSaveRequestEntity = new DoctorImagSaveRequestEntity();
                doctorImagSaveRequestEntity.setDrId(SpUtils.getInt(Contants.Id, 0));
                doctorImagSaveRequestEntity.setDrName(mName);
                doctorImagSaveRequestEntity.setCredNo(mIdCard);
                doctorImagSaveRequestEntity.setDoctorRegImgs(mList);
                String json = mGson.toJson(doctorImagSaveRequestEntity);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                mMyOkhttp.post().url(HttpUrl.DoctorImagSave).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entity) {
                        LoadDialog.clear();
                        if (entity.getCode() == 0) {
                            Intent auditingIntent = new Intent(Authentication3ActivityNew.this, MainActivity.class);
                            //auditingIntent.putExtra("flag", "3");
                            SpUtils.put(Contants.Status, 3);
                            EventBus.getDefault().post(new UpdatePushView(7));
                            startActivity(auditingIntent);
                            toast("提交成功");
                        } else {
                            toast(entity.getMessage());
                        }

                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        LoadDialog.clear();
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.tv_checksimple3:
                Intent intent = new Intent(mContext, ShowBigImageActivity.class);
                intent.putExtra("doctorType", mDrType);
                intent.putExtra("doctorPositionName", mJobname);
                mContext.startActivity(intent);
                break;
            case R.id.tv_checksimple2:
                Intent intent2 = new Intent(mContext, ShowBigImageActivity.class);
                intent2.putExtra("doctorType", mDrType);
                intent2.putExtra("doctorPositionName", mJobname);
                mContext.startActivity(intent2);
                break;
            case R.id.tv_checksimple:
                Intent intent3 = new Intent(mContext, ShowBigImageActivity.class);
                intent3.putExtra("doctorType", mDrType);
                intent3.putExtra("doctorPositionName", mJobname);
                mContext.startActivity(intent3);
                break;
        }


    }


    // 处理选择的照片的地址
    private void refreshAdapter(List<String> picList, int mFlag) {
        mLocalList1.clear();
        mLocalList2.clear();
        mLocalList3.clear();
        for (String localMedia : picList) {
            //被压缩后的图片路径
            String compressPath = localMedia.toString(); //压缩后的图片路径
            if (mFlag == R.id.upload_view1) {

                mLocalList1.add(compressPath);//把图片添加到将要上传的图片数组中
                //cropImage(Uri.fromFile(new File(compressPath)));
            } else if (mFlag == R.id.upload_view2) {

                mLocalList2.add(compressPath);//把图片添加到将要上传的图片数组中
            } else if (mFlag == R.id.upload_view3) {
                mLocalList3.add(compressPath);//把图片添加到将要上传的图片数组中
            }

        }
        if (mFlag == R.id.upload_view1) {
            upLoadPicturePath(mLocalList1, mFlag);
        } else if (mFlag == R.id.upload_view2) {
            upLoadPicturePath(mLocalList2, mFlag);
        } else if (mFlag == R.id.upload_view3) {
            upLoadPicturePath(mLocalList3, mFlag);
        }


    }

    private void cropImage(Uri selectedImage1) {

        //  String image64 = ImageBase64.encodeImage(mSelectedPictureDialog.getPicturePath());
        String f1 = System.currentTimeMillis() + ".jpg";
        String picturePath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f1;
        File file1 = new File(picturePath1);
        file1.getParentFile().mkdirs();
        Log.i("zdp", picturePath1);
        Uri uri1;

        uri1 = Uri.fromFile(file1);

        startCropActivity(selectedImage1, uri1);
    }


    private void upLoadPicturePath(List<String> pictureList, int flag) {
        for (String path : pictureList) {
            uploadPicture(path, flag);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    List<String> images =new ArrayList();
                    Log.i("zdp", "path=" + mSelectedPictureDialog.getPicturePath());
                    images.add(mSelectedPictureDialog.getPicturePath());
                    refreshAdapter(images, mFlag);

                    break;
                case 2:
                    Log.i("zdp", "fdsf");
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径

                        List<String> images2 =new ArrayList();
                        Log.i("zdp", "path=" + path);
                        images2.add(path);
                        refreshAdapter(images2, mFlag);
                        cursor.close();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                    break;
            }
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Log.d(TAG, "裁剪图片" + resultUri.getPath());
            identityCardRecognition(resultUri.getPath());
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }


    private void uploadPicture(final String path, final int flag) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToStringfromAuth(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                loading(true);
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                loading(true);
                mMyOkhttp.post().url(HttpUrl.DoctorImagUpload).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            if (flag == R.id.upload_view1) {
                                String url = entituy.getData().getSavePath();
                                mSavePicList1.add(url);
                                //添加图片
                                uploadView1.addFile(new File(path));
                            } else if (flag == R.id.upload_view2) {
                                // mSavePicList2.add(entituy.getData().getDisplayPath());
                                String url = entituy.getData().getSavePath();
                                mSavePicList2.add(url);
                                //添加图片
                                uploadView2.addFile(new File(path));
                            } else if (flag == R.id.upload_view3) {
                                // mSavePicList3.add(entituy.getData().getDisplayPath());
                                String url = entituy.getData().getSavePath();
                                mSavePicList3.add(url);
                                //添加图片
                                uploadView3.addFile(new File(path));

                            }
                        } else {
                            toast("上传图片失败，请检查网络！");
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        LoadDialog.clear();
                        Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                        toast("上传图片失败，请检查网络！");
                    }
                });
            }
        }.execute(path);

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
                ActivityCompat.requestPermissions(Authentication3ActivityNew.this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
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
        crop.withAspectRatio(5, 1);
        crop.setMaxScaleMultiplier(8);
        //下面参数分别是缩放,旋转,裁剪框的比例
        crop.setAllowedGestures(UCropActivity.ALL, UCropActivity.NONE, UCropActivity.ALL);
        crop.setFreeStyleCropEnabled(true);
        crop.setToolbarTitle("裁剪执业证编号");
        crop.setShowCropGrid(false);
        UCrop.of(uri, destinationUri)
                .withOptions(crop)
                .start(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    practiceLicense.setText(mWords + "");
                    practiceLicense.setSelection(mWords.length());
                    loading(false);
                    break;
                case 2:
                    loading(false);
                    break;
                default:
                    break;
            }
        }

    };

    //身份证识别接口
    private void identityCardRecognition(final String picturePath) {
        loading(true, "执业证编号识别中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    result = IdcardRecognise.getDoctorUnmber(picturePath);
                    Doctor doctorNumber = GsonUtils.fromJson(result, Doctor.class);
                    for (int i = 0; i < doctorNumber.getWords_result().size(); i++) {
                        mWords = mWords + doctorNumber.getWords_result().get(i).getWords();
                    }
                    Log.d(TAG, "识别结果" + result + "   " + mWords);
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(2);
                }

            }
        }).start();

    }



}
