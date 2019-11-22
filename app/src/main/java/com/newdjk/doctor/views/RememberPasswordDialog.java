package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

public class RememberPasswordDialog {

    private Context mContext;
    private Dialog mDialog;
    private int mDay;

    public RememberPasswordDialog(Context mContext) {
        this.mContext = mContext;


    }

    public void show(String title, String content, final DialogListener listener) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }

            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_remember_password, null);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.setContentView(view);
            TextView tvsure = view.findViewById(R.id.tv_sure);
            RadioButton notRemember = view.findViewById(R.id.not_remember);
            RadioButton rememberOneWeek = view.findViewById(R.id.remember_one_week);
            RadioButton rememberOneday = view.findViewById(R.id.remember_one_day);
            RadioButton rememberTowMonth = view.findViewById(R.id.remember_tow_month);
            RadioGroup  rememberGroup = view.findViewById(R.id.remember_group);
            notRemember.setChecked(true);
            mDay = 0;
            rememberGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.not_remember :
                            mDay = 0;
                            break;
                        case R.id.remember_one_day :
                            mDay = 1;
                            break;
                        case R.id.remember_one_week :
                            mDay = 7;
                            break;
                        case R.id.remember_tow_month:
                            mDay = 60;
                            break;
                    }
                }
            });
            tvsure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        listener.confirm(mDay);
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            mDialog.show();
            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
            dialogWindow.setAttributes(lp);

        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public interface DialogListener {
        void cancel();

        void confirm(int keepDay );
    }
}
