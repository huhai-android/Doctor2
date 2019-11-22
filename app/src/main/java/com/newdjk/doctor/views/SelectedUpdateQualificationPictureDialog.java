package com.newdjk.doctor.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

import java.io.File;

public class SelectedUpdateQualificationPictureDialog implements View.OnClickListener {
    TextView photograph;
    TextView album;
    private Activity mContext;
    private String mPicturePath;
    private Dialog mDialog;
    private String mAction;


    public SelectedUpdateQualificationPictureDialog(Context context, String action) {
        mAction = action;
        mContext = (Activity) context;
    }


    public String getPicturePath() {
        return mPicturePath;
    }

    //显示dialog的方法
    public void show() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_select_picture, null);
            photograph = view.findViewById(R.id.photograph);
            album = view.findViewById(R.id.album);
            mDialog.setContentView(view);//dialog布局文件
            /*mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog*/
            mDialog.show();
            photograph.setOnClickListener(this);
            album.setOnClickListener(this);
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photograph:
                close();
                //拍照制定目录
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
                if (mAction.equals("first")) {
                    mContext.startActivityForResult(openCameraIntent, 1);
                } else if (mAction.equals("second")) {
                    mContext.startActivityForResult(openCameraIntent, 2);
                } else if (mAction.equals("third")) {
                    mContext.startActivityForResult(openCameraIntent, 3);
                } else if (mAction.equals("fourth")) {
                    mContext.startActivityForResult(openCameraIntent, 4);
                }
                break;
            case R.id.album:
                close();
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (mAction.equals("first")) {
                    mContext.startActivityForResult(intent, 5);
                } else if (mAction.equals("second")) {
                    mContext.startActivityForResult(intent, 6);
                } else if (mAction.equals("third")) {
                    mContext.startActivityForResult(intent, 7);
                } else if (mAction.equals("fourth")) {
                    mContext.startActivityForResult(intent, 8);
                }
                break;
        }
    }
}
