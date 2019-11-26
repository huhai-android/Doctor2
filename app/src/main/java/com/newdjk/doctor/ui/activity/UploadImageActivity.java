package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
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

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpLoadImageSuccess;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.views.AlbumsTypeSelectedpopwin;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UploadImageActivity extends BasicActivity {


    private static final String TAG = "UploadImageActivity";
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
    @BindView(R.id.btn_upload)
    AppCompatButton btnUpload;
    @BindView(R.id.im_takepic)
    ImageView imTakepic;
    @BindView(R.id.im_uploadpic)
    ImageView imUploadpic;
    private AlbumsTypeSelectedpopwin AlbumsTypeSelectedpopwin;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private String mImgPath;
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    private String mPicturePath;

    @Override
    protected int initViewResId() {
        return R.layout.activity_upload_image;
    }

    @Override
    protected void initView() {
        initTitle("上传头像").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void initListener() {

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    AlbumsTypeSelectedpopwin = new AlbumsTypeSelectedpopwin(UploadImageActivity.this, UploadImageActivity.this);
                    AlbumsTypeSelectedpopwin.showAtLocation(btnUpload, Gravity.BOTTOM, 0, 0);
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 0.7f;
                    getWindow().setAttributes(lp);
                    AlbumsTypeSelectedpopwin.setOnDismissListener(new PopupWindow.OnDismissListener() {

                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1f;

                            getWindow().setAttributes(lp);
                        }
                    });
                }
            }
        });


        imTakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String f = System.currentTimeMillis() + ".jpg";
                    mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                    File file = new File(mPicturePath);
                    file.getParentFile().mkdirs();
                    Log.i("zdp", mPicturePath);
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }

                    //添加权限
                    openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(openCameraIntent, 1);
                }

            }
        });
        imUploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }

            }
        });
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
                ActivityCompat.requestPermissions(UploadImageActivity.this,
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
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d(TAG, "XXXXXXXXXXXXXX");

        } else {
            Log.d(TAG, "yyyyyyyyyyyyy");
        }
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case 1:  //拍照
                    //  String image64 = ImageBase64.encodeImage(mSelectedPictureDialog.getPicturePath());
                    String f1 = System.currentTimeMillis() + ".jpg";
                    String picturePath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f1;
                    File file1 = new File(picturePath1);
                    file1.getParentFile().mkdirs();
                    Log.i("zdp", picturePath1);
                    Uri uri1;
                    uri1 = Uri.fromFile(file1);
                    Uri selectedImage1 = Uri.fromFile(new File(mPicturePath));
                    startCropActivity(selectedImage1, uri1);

                    break;
                case 2: //相册
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

                case UCrop.REQUEST_CROP:
                    // 裁剪照片
                    final Uri croppedUri = UCrop.getOutput(data);
                    try {
                        if (croppedUri != null) {
                            String path = croppedUri.getPath();  //获取照片路径
                            uploadPicture(path);
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



    @SuppressLint("StaticFieldLeak")
    private void uploadPicture(String path) {

        new AsyncTask<String, Integer, String>() {
            protected WeakReference<String> mTarget;
            // 弱引用是允许被gc回收的;


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
                            mImgPath = entituy.getData().getDisplayPath();
                            EventBus.getDefault().post(new UpLoadImageSuccess(mImgPath));
                            Log.i("PersonalDataActivity", "mImgPath=" + mImgPath);
                            toast("上传图片成功");
                            finish();
                        } else {
                            toast(entituy.getMessage());
                        }

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
    protected void initData() {


    }


    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

        }
    }


}
