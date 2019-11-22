package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

public class DialogProgress {
    private Context mContext;
    private Dialog mDialog;
    private ProgressBar mBar;

    public DialogProgress(Context mContext) {
        this.mContext = mContext;
    }

    public void show() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_download_progress, null);
            mDialog.setContentView(view);
             mBar = view.findViewById(R.id.pbg);

            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
            lp.height = 300;
            dialogWindow.setAttributes(lp);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    public void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


   public void setProgress(int p){
       Log.e("===", "setProgress: "+p );
        if (p == 60){
           close();
        }
        mBar.setProgress(p);
   }
}
