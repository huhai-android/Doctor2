package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.adapter.PatientSelectAdapter;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.utils.LogUtils;

import java.util.List;

public class ShareDialog {

    private Context mContext;
    private Dialog mDialog;

    public ShareDialog(Context mContext) {
        this.mContext = mContext;


    }

    public void show(final List<PatientListDataEntity> data, final DialogListener listener) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_patient_share, null);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.setContentView(view);

            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            RecyclerView recyleview = view.findViewById(R.id.recyleview);
            TextView tvConfirm = view.findViewById(R.id.tv_confirm);
            CircleImageView headicon = view.findViewById(R.id.head_icon);
            TextView name = view.findViewById(R.id.tv_name);
//            Glide.with(MyApplication.getContext())
//                    .load(data.getPaPicturePath())
//                    .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
//                    .error(R.drawable.patient_default_img)
//                    .placeholder(R.drawable.patient_default_img)
//                    .into(headicon);
            final PatientSelectAdapter patientSelectAdapter = new PatientSelectAdapter(data);
            GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 3);
            recyleview.setLayoutManager(mManagerLayout);
            recyleview.setAdapter(patientSelectAdapter);


            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cancel();
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });

            patientSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = data.get(position).getPatientId();
                    patientSelectAdapter.remove(position);
                    listener.remove(id);
                }
            });


            // name.setText(TextUtils.isEmpty(data.getPatientName())?"":data.getPatientName());
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

        void remove(int id);

    }

}
