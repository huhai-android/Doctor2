package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowBigImageActivity extends BasicActivity {


    @BindView(R.id.origin_picture)
    PhotoView originPicture;
    private String mPath;
    private String doctorType;
    private String doctorPositionName;
    private String TAG="ShowBigImageActivity";

    @Override
    protected int initViewResId() {
        return R.layout.show_picture_simple;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        originPicture.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        }
        );

        Intent intent = getIntent();
        mPath = intent.getStringExtra("path");
        doctorType = intent.getStringExtra("doctorType");
        doctorPositionName = intent.getStringExtra("doctorPositionName");

        Log.d(TAG, "职称" + doctorType+"  "+doctorPositionName);
        if (doctorType.equals("1")){

            if (doctorPositionName.contains("乡村医生")){
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.xiangcun)
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(originPicture);
            }else {
                Glide.with(MyApplication.getContext())
                        .load(R.drawable.simple)
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(originPicture);
            }

        }else{
            Glide.with(MyApplication.getContext())
                    .load(R.drawable.nurse)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(originPicture);


        }
        Log.i("zdp", "path=" + mPath);

    }

    @Override
    protected void otherViewClick(View view) {

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
}
