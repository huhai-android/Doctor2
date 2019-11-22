package com.newdjk.doctor.views;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.iInterface.SignSuccessClickLister;


public class SignSuccessDialog extends Dialog {

    private static TextView spinner;
    private static TextView tvSure;
    private static TextView tvCancel;
    private static int selectadvice = 0;
    private static ImageView imArrow;

    public SignSuccessDialog(Context context) {
        super(context);
    }

    public SignSuccessDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private final Context mContext;
        private final View imClosed;
        private final TextView tvPoint;
        private SignSuccessClickLister singleButtonClickListener;
        private View layout;
        private SignSuccessDialog dialog;
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;


        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            this.mContext = context;
            dialog = new SignSuccessDialog(context, R.style.Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_sign_success, null);
            imClosed = layout.findViewById(R.id.im_closed);
            tvPoint = layout.findViewById(R.id.tv_point);
            initlistener();
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        private void initlistener() {
            imClosed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleButtonClickListener.onPositiveButtonClick(v);
                }
            });
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public Builder setSingleButton(String singleButtonText, SignSuccessClickLister listener) {
            this.singleButtonText = singleButtonText;
            tvPoint.setText(singleButtonText);
            this.singleButtonClickListener = listener;
            return this;
        }

        /**
         * 创建单按钮对话框
         *
         * @return
         */
        public SignSuccessDialog createSingleButtonDialog() {

            create();
            return dialog;
        }


        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private void create() {

            dialog.setContentView(layout);
            dialog.setCancelable(false);     //用户不可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
        }

        /**
         * 显示双按钮布局，隐藏单按钮
         */
        private void showTwoButton() {

        }

        /**
         * 显示单按钮布局，隐藏双按钮
         */
        private void showSingleButton() {

        }

    }

}
