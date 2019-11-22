package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.ZhuanZhenPatirntEntity;
import com.newdjk.doctor.utils.LogUtils;

public class ZhuanZhenDialog {
    private Context mContext;
    private Dialog mDialog;

    public ZhuanZhenDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show(String title, ZhuanZhenPatirntEntity content, final DialogListener listener) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_group_zhuanzhen, null);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.setContentView(view);

            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvContent = view.findViewById(R.id.tv_content);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvConfirm = view.findViewById(R.id.tv_confirm);
            TextView tvName = view.findViewById(R.id.tv_name);
            TextView tvDoctorTo = view.findViewById(R.id.tv_doctor_to);
            TextView tvtime = view.findViewById(R.id.tv_time);
            TextView tvStatus = view.findViewById(R.id.tv_status);
            TextView tvDoctorToFrom = view.findViewById(R.id.tv_doctor_from);
            TextView tvtimeFrom = view.findViewById(R.id.tv_time_from);
            if (content.getPatientSex()==1){
                tvName.setText(""+content.getPatientName()+"  男"+"  "+content.getAge());
            }else if (content.getPatientSex()==2){
                tvName.setText(""+content.getPatientName()+"  女"+"  "+content.getAge());
            }else {
                tvName.setText(""+content.getPatientName()+"  未知"+"  "+content.getAge());
            }
            if (TextUtils.isEmpty(content.getToDrName())){
                tvDoctorTo.setText("");
            }else {
                tvDoctorTo.setText(""+content.getToDrName());
            }

            tvContent.setText(""+(TextUtils.isEmpty(content.getReferralRemark())?"":content.getReferralRemark()));
            tvtime.setText(""+(TextUtils.isEmpty(content.getReceiveTime())?"":content.getReceiveTime().substring(0,16)));
            tvStatus.setText(""+(content.getReferralStatus()==1?"未接诊":"已就诊"));
            tvDoctorToFrom.setText(""+content.getFromDrName());
            tvtimeFrom.setText(""+(TextUtils.isEmpty(content.getReferralTime())?"":content.getReferralTime().substring(0,16)));
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }
            tvCancel.setText("关闭");
            tvConfirm.setText("患者档案");



            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cancel();
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.confirm();
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

        void confirm();
    }
}
