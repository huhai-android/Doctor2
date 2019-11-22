package com.newdjk.doctor.views;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.iInterface.RefuseDialogClickLister;
import com.newdjk.doctor.utils.ToastUtil;

import java.util.List;


public class RefuseDialog extends Dialog {

    private static TextView spinner;
    private static TextView tvSure;
    private static TextView tvCancel;
    private static int selectadvice = 0;
    private static ImageView imArrow;

    public RefuseDialog(Context context) {
        super(context);
    }

    public RefuseDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private final List<String> adviceList;
        private final EditText etAdvice;
        private final Context mContext;
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private RefuseDialogClickLister positiveButtonClickListener;
        private RefuseDialogClickLister negativeButtonClickListener;
        private RefuseDialogClickLister singleButtonClickListener;
        private SpinerPopWindow spinerPopWindow;
        private View layout;
        private RefuseDialog dialog;
        private boolean isRead = false;
        private boolean isShowCheckBox = true;


        public Builder(Context context, List<String> adviceList) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            this.mContext = context;
            dialog = new RefuseDialog(context, R.style.Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_layout, null);
            spinner = layout.findViewById(R.id.splider);
            tvSure = layout.findViewById(R.id.tv_sure);
            tvCancel = layout.findViewById(R.id.tv_cancel);
            etAdvice = layout.findViewById(R.id.et_advice);
            imArrow = layout.findViewById(R.id.im_arrow);
            initlistener();
            this.adviceList = adviceList;
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            SimpleAdapter adapter = new SimpleAdapter(context, adviceList);
//
//            spinner.setAdapter(adapter);
            selectadvice=0;
            if (adviceList.size()>0){
                spinner.setText(adviceList.get(0));
            }

        }

        private void initlistener() {

            spinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinerPopWindow = new SpinerPopWindow(spinner, mContext, adviceList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectadvice = position;
                            spinner.setText(adviceList.get(position));
                            spinerPopWindow.dismiss();
                            imArrow.setImageResource(R.mipmap.decline);
                            spinerPopWindow=null;
                        }
                    });

                    spinerPopWindow.showAsDropDown(spinner, 0, 0);
                    spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            imArrow.setImageResource(R.mipmap.decline);
                        }
                    });
                    imArrow.setImageResource(R.mipmap.rise);
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectadvice==adviceList.size()-1&& TextUtils.isEmpty(etAdvice.getText().toString())){
                        ToastUtil.setToast("请输入驳回理由");
                    }else {
                        singleButtonClickListener.onPositiveButtonClick(v, selectadvice, etAdvice.getText().toString());
                        dialog.dismiss();
                        dialog=null;
                    }

                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog=null;
                }
            });

            imArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinerPopWindow = new SpinerPopWindow(spinner, mContext, adviceList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectadvice = position;
                            spinner.setText(adviceList.get(position));
                            spinerPopWindow.dismiss();
                            imArrow.setImageResource(R.mipmap.decline);
                            spinerPopWindow=null;
                        }
                    });

                    spinerPopWindow.showAsDropDown(spinner, 0, 0);
                    spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            imArrow.setImageResource(R.mipmap.decline);
                        }
                    });
                    imArrow.setImageResource(R.mipmap.rise);
                }
            });

        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder showCheckBox(boolean isShowCheckBox) {
            this.isShowCheckBox = isShowCheckBox;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, RefuseDialogClickLister listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, RefuseDialogClickLister listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setSingleButton(String singleButtonText, RefuseDialogClickLister listener) {
            this.singleButtonText = singleButtonText;
            this.singleButtonClickListener = listener;
            return this;
        }

        /**
         * 创建单按钮对话框
         *
         * @return
         */
        public RefuseDialog createSingleButtonDialog() {

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
