package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.DocQualificationEntity;
import com.newdjk.doctor.ui.entity.DoctorImagSaveRequestEntity;
import com.newdjk.doctor.ui.entity.DoctorRegImgEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.DisplayUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MultiImageUploadView;
import com.newdjk.doctor.views.RoundImageUploadView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * Created by Struggle on 2018/10/12.
 */

public class UpdateQualifications extends BasicActivity {


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
    @BindView(R.id.login_out)
    AppCompatButton loginOut;
    @BindView(R.id.practice_license_zi_ge)
    EditText practiceLicenseZiGe;
    @BindView(R.id.practice_license)
    EditText practiceLicense;
    @BindView(R.id.tv_last_update_time)
    TextView tvLastUpdateTime;
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
    @BindView(R.id.practice_license_number)
    TextView practiceLicenseNumber;
    @BindView(R.id.practice_license_number_zi_ge)
    TextView practiceLicenseNumberZiGe;
    private DocQualificationEntity mDocQualificationEntity;
    private List<DocQualificationEntity.DataBean> mList, mOtherList;

    private List<DoctorRegImgEntity> mImageList;

    private Gson mGson = new Gson();
    private final static String TAG = "===";

    private String mIdNumber;
    private String mLastUpdateTime;
    private String mNumber1, mNumber2;
    private String mUpNumber1, mUpNumber2;


    private final static int REQ_PERMISSION_CODE = 0x100;
    private int mFlag = -1;
    private List<String> mSavePicList1 = new ArrayList<>(); //上传的图片凭证的数据源 执业证
    private List<String> mSavePicList2 = new ArrayList<>(); //上传的图片凭证的数据源 医师资格证
    private List<String> mSavePicList3 = new ArrayList<>(); //上传的图片凭证的数据源 职称证

    private List<String> mLocalList1 = new ArrayList<>(); //上传的图片凭证的数据源 执业证
    private List<String> mLocalList2 = new ArrayList<>(); //上传的图片凭证的数据源 医师资格证
    private List<String> mLocalList3 = new ArrayList<>(); //上传的图片凭证的数据源 职称证

    private static final int IMG_REQUEST_CODE = 0x010;
    private HashMap<String, List<String>> mHashMap = new HashMap<>();

    @Override
    protected int initViewResId() {
        return R.layout.activity_update_qualification;
    }

    @Override
    protected void initView() {
        checkPermission();

        topTitle.setText("更新资质");
        topLeft.setVisibility(View.VISIBLE);

        mImageList = new ArrayList<>();
        mOtherList = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                setUploadView(uploadView1, tvPicNumber1, mSavePicList1);
            } else if (i == 1) {
                setUploadView(uploadView2, tvPicNumber2, mSavePicList2);
            } else if (i == 2) {
                setUploadView(uploadView3, tvPicNumber3, mSavePicList3);
            }
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

                    mSavePicList1.remove(pos);
                } else if (mFlag == R.id.upload_view2) {

                    mSavePicList2.remove(pos);
                } else if (mFlag == R.id.upload_view3) {

                    mSavePicList3.remove(pos);
                }
            }
        });

    }

    private void startSelectImage(RoundImageUploadView uploadView) {
        BoxingConfig mulitImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG)
                .needCamera(R.drawable.ic_camera)
                .needGif()
                .withMaxCount(uploadView.getMax() - uploadView.getFiles().size());
        Boxing.of(mulitImgConfig).
                withIntent(this, BoxingActivity.class).
                start(this, IMG_REQUEST_CODE);
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        obtainDoctorQualification();
    }

    /**
     * 证照类型1-身份证正面，2-身份证反面，3-手持身份证，4-执业证，5-资格证，6-职称证,7-结业证)
     * <p>
     * 职业证
     */
    private void obtainDoctorQualification() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        loading(true);
        mMyOkhttp.get().url(HttpUrl.QueryDoctorRegImagByDrId).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<DocQualificationEntity>() {
            @Override
            public void onSuccess(int statusCode, DocQualificationEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    mList = response.getData();
                    int len = mList.size();
                    for (int i = 0; i < len; i++) {
                        DocQualificationEntity.DataBean bean = mList.get(i);
                        mLastUpdateTime = bean.getUpdateTime();
                        if (bean.getImgType() == 4) {//职业证
                            mSavePicList1.add(bean.getImgPath());
                            mNumber1 = bean.getNumber();
                        } else if (bean.getImgType() == 5) {
                            mSavePicList2.add(bean.getImgPath());
                            mNumber2 = bean.getNumber();
                        } else if (bean.getImgType() == 6) {
                            mSavePicList3.add(bean.getImgPath());
                        } else {
                            mOtherList.add(mList.get(i));
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


                    if (mLastUpdateTime != null && !mLastUpdateTime.equals("")) {
                        tvLastUpdateTime.setText(mLastUpdateTime.substring(0, 10).replace("-", "."));
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

    }


    @OnClick({R.id.top_left, R.id.login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.login_out:
                mUpNumber1 = practiceLicense.getText().toString().trim();
                mUpNumber2 = practiceLicenseZiGe.getText().toString().trim();
                if (TextUtils.isEmpty(mUpNumber1) || TextUtils.isEmpty(mUpNumber2)) {
                    toast("请将证书编号填写完整");
                    return;
                }
                doUpdateQualification();
                break;
            case R.id.top_left:
                this.finish();
                break;
        }
    }

    private void doUpdateQualification() {
        String zyz = practiceLicense.getText().toString().trim();
        String zige = practiceLicenseZiGe.getText().toString().trim();
        if (TextUtils.isEmpty(zyz) || TextUtils.isEmpty(zige)) {
            toast("请将证书编号填写完整");
            return;
        }

        loading(true);

        for (String s1 : mSavePicList1) {
            DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
            doctorRegImgEntity.setImgPath(s1);
            doctorRegImgEntity.setImgType(4);
            doctorRegImgEntity.setNumber(zyz);
            mImageList.add(doctorRegImgEntity);
        }


        for (String s2 : mSavePicList2) {
            DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
            doctorRegImgEntity.setImgPath(s2);
            doctorRegImgEntity.setImgType(5);
            doctorRegImgEntity.setNumber(zige);
            mImageList.add(doctorRegImgEntity);
        }

        for (String s3 : mSavePicList3) {
            DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
            doctorRegImgEntity.setImgPath(s3);
            doctorRegImgEntity.setImgType(6);
            mImageList.add(doctorRegImgEntity);
        }


        for (int i = 0; i < mOtherList.size(); i++) {
            DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
            doctorRegImgEntity.setImgPath(mOtherList.get(i).getImgPath());
            doctorRegImgEntity.setImgType(mOtherList.get(i).getImgType());
            doctorRegImgEntity.setNumber(mOtherList.get(i).getNumber());
            mImageList.add(doctorRegImgEntity);
        }

        DoctorImagSaveRequestEntity doctorImagSaveRequestEntity = new DoctorImagSaveRequestEntity();
        doctorImagSaveRequestEntity.setDrId(SpUtils.getInt(Contants.Id, 0));
        doctorImagSaveRequestEntity.setDrName(SpUtils.get(Contants.Name, "").toString());
        doctorImagSaveRequestEntity.setCredNo("");
        doctorImagSaveRequestEntity.setDrName("");
        doctorImagSaveRequestEntity.setDoctorRegImgs(mImageList);
        String json = mGson.toJson(doctorImagSaveRequestEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.DoctorImagSave).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    toast("更新成功");
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

    private Context getContext() {
        return this;
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: " + mFlag);
            switch (requestCode) {
                case IMG_REQUEST_CODE:
                    ArrayList<BaseMedia> images = Boxing.getResult(data);
//                    if (images != null) {
//                        for (BaseMedia image : images) {
//                            if (mFlag == R.id.upload_view1) {
//                                uploadView1.addFile(new File(image.getPath()));
//                            } else if (mFlag == R.id.upload_view2) {
//                                uploadView2.addFile(new File(image.getPath()));
//                            } else if (mFlag == R.id.upload_view3) {
//                                uploadView3.addFile(new File(image.getPath()));
//                            }

//                        }
//                    }
                    refreshAdapter(images, mFlag);

                    break;
            }
        }

    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<BaseMedia> picList, int mFlag) {
        mLocalList1.clear();
        mLocalList2.clear();
        mLocalList3.clear();
        for (BaseMedia localMedia : picList) {
            //被压缩后的图片路径
            String compressPath = localMedia.getPath(); //压缩后的图片路径
            if (mFlag == R.id.upload_view1) {

                mLocalList1.add(compressPath);//把图片添加到将要上传的图片数组中
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

    private void upLoadPicturePath(List<String> pictureList, int flag) {
        for (String path : pictureList) {
            uploadPicture(path, flag);
        }
    }


    private void uploadPicture(String path, final int flag) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                loading(true);
                Map<String, String> headMap = new HashMap<>();
                headMap.put("Authorization",SpUtils.getString(Contants.Token));
                mMyOkhttp.post().url(HttpUrl.FeedbackImgLoad).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            Log.e(TAG, "onSuccess: " + entituy.getData().getDisplayPath());
                            if (flag == R.id.upload_view1) {
                                String url = entituy.getData().getDisplayPath();
                                mSavePicList1.add(url);
                                uploadView1.addFile(new File(url));
                            } else if (flag == R.id.upload_view2) {
                                // mSavePicList2.add(entituy.getData().getDisplayPath());
                                String url = entituy.getData().getDisplayPath();
                                mSavePicList2.add(url);
                                uploadView2.addFile(new File(url));
                            } else if (flag == R.id.upload_view3) {
                                // mSavePicList3.add(entituy.getData().getDisplayPath());
                                String url = entituy.getData().getDisplayPath();
                                mSavePicList3.add(url);
                                uploadView3.addFile(new File(url));
                            }
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        LoadDialog.clear();
                        Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }


}
