package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

public class PlusDialog {
    private Context mContext;
    private Dialog mDialog;

    public PlusDialog(Context mContext ){
        this.mContext = mContext;
    }
    public  void show(String title,String content,String stamp,String number) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.plus_sign_detail, null);
            mDialog.setContentView(view);
            TextView titleTx = view.findViewById(R.id.title);
            TextView numberTx = view.findViewById(R.id.number);
            ImageView stampIv = view.findViewById(R.id.stamp);
            TextView signContentTx = view.findViewById(R.id.sign_content);
            TextView close = view.findViewById(R.id.close);
            titleTx.setText(title);
            numberTx.setText(number);
            signContentTx.setText("请凭此凭证到医生出诊处加号，加号前可咨询医生出诊时间。");
            Glide.with(MyApplication.getContext())
                    .load(stamp)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(stampIv);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close();
                }
            });
            mDialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
