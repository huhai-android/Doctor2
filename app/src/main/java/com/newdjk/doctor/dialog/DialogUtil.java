package com.newdjk.doctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newdjk.doctor.R;

public class DialogUtil {
    private Context mContext;
    private Dialog mDialog;
    static DialogUtil mDialogUtil = new DialogUtil();

    public DialogUtil(Context mContext) {
        this.mContext = mContext;
    }

    public DialogUtil() {

    }

    public static DialogUtil getInstanse() {
        return mDialogUtil;
    }


    public void show(Context mContext,String title, String content, int layoutid, final DialogListener listener) {
        this.mContext = mContext;
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(mContext, layoutid, null);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

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
