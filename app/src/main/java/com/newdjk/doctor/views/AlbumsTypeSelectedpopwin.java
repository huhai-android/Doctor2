package com.newdjk.doctor.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newdjk.doctor.R;

import java.io.File;

import butterknife.BindView;

public class AlbumsTypeSelectedpopwin extends PopupWindow implements View.OnClickListener {
    private TextView takePhoto;
    private TextView albums;
    private  TextView cancel;
    private LinearLayout popLayout;
    private Activity mContext;
    private View view;
    private String mPicturePath;

    public AlbumsTypeSelectedpopwin(Context context, Activity activity) {
        super(context);
        mContext = activity;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_picture_select, null);

        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        this.setContentView(this.view);
        takePhoto = view.findViewById(R.id.take_photo);
        albums = view.findViewById(R.id.albums);
        cancel = view.findViewById(R.id.cancel);
          this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.popupwindow_animation);
        takePhoto.setOnClickListener(this);
        albums.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                dismiss();
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.file.provider", file);
                }
                else {
                    uri = Uri.fromFile(file);
                }

                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                mContext.startActivityForResult(openCameraIntent, 1);
                break;
            case R.id.albums:
                dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    mContext.startActivityForResult(intent, 2);

                break;
            case R.id.cancel:
              dismiss();
                break;
        }
    }
    public String getPicturePath () {
        return mPicturePath;
    }
}
