package com.newdjk.doctor.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;

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
public class AdsDialogActivity extends BasicActivity {


    @BindView(R.id.im_cancel)
    ImageView imCancel;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.lv_root)
    RelativeLayout lvRoot;

    @Override
    protected int initViewResId() {
        return R.layout.ads_dialog;
    }

    @Override
    protected void initView() {
        lvRoot.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        imCancel.setOnClickListener(this);
        image.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //image

        Glide.with(AdsDialogActivity.this) // could be an issue!
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=889644352,3407849871&fm=26&gp=0.jpg")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        // do something with the bitmap
                        // for demonstration purposes, let's just set it to an ImageView
                        image.setImageBitmap(bitmap);
                        lvRoot.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.image:
                // ToastUtil.setToast("点击了广告");
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}

