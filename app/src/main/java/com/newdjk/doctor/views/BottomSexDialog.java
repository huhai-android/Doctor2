package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.newdjk.doctor.R;

public class BottomSexDialog {
    private Dialog mDialog;
    private View mView;
    private RelativeLayout rlMan, rlMail;
    private ImageView ivMan, ivMail;
    private static BottomSexDialog mInstance;

    public static BottomSexDialog getInstance() {
        if (mInstance == null) {
            synchronized (BottomSexDialog.class) {
                if (mInstance == null) {
                    mInstance = new BottomSexDialog();
                }
            }
        }
        return mInstance;
    }

    public void showBottomSexDialog(Context ctx, boolean flag, final SexSelectListener listener) {
        mView = View.inflate(ctx, R.layout.layout_bottom_sex_dialog, null);
        rlMan = mView.findViewById(R.id.rl_man);
        rlMail = mView.findViewById(R.id.rl_mail);
        ivMail = mView.findViewById(R.id.iv_mail);
        ivMan = mView.findViewById(R.id.iv_man);
        if (flag) {
            ivMan.setVisibility(View.VISIBLE);
            ivMail.setVisibility(View.GONE);
        } else {
            ivMail.setVisibility(View.VISIBLE);
            ivMan.setVisibility(View.GONE);
        }
        rlMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sexSelectListener(true);
                ivMan.setVisibility(View.VISIBLE);
                ivMail.setVisibility(View.GONE);
                if (mDialog != null)
                    mDialog.dismiss();
            }
        });

        rlMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sexSelectListener(false);
                ivMan.setVisibility(View.GONE);
                ivMail.setVisibility(View.VISIBLE);
                if (mDialog != null)
                    mDialog.dismiss();
            }
        });
        mDialog = new Dialog(ctx, R.style.ActionSheetDialogStyle);//dialog样式
        mDialog.setContentView(mView);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //DisplayMetrics d = ctx.getResources().getDisplayMetrics();
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

    public interface SexSelectListener {
        void sexSelectListener(boolean b);
    }
}
