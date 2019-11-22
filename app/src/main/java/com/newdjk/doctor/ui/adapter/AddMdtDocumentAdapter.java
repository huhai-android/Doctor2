package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MDTDocumentEntity;
import com.newdjk.doctor.utils.ToastUtil;

import java.util.List;


public class AddMdtDocumentAdapter extends BaseQuickAdapter<MDTDocumentEntity.UnDefalutMedicalsBean, BaseViewHolder> {
    private String TAG = "AddDocumentAdapter";
    private Gson mGson;
    private List<MDTDocumentEntity.UnDefalutMedicalsBean> datalist;
    private onDocumentAddListener listener;


    public AddMdtDocumentAdapter(@Nullable List<MDTDocumentEntity.UnDefalutMedicalsBean> data) {
        super(R.layout.diagnose__adddocument_item, data);
        datalist = data;
        mGson = new Gson();
    }

    @Override
    public int getItemViewType(int position) {
        return datalist.get(position).getIsCustom();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MDTDocumentEntity.UnDefalutMedicalsBean item) {
        Log.d(TAG, helper.getAdapterPosition() + "  " + datalist.size());
        if (helper.getAdapterPosition() == datalist.size() - 1) {
            helper.getView(R.id.add_custom_medical).setVisibility(View.VISIBLE);
            helper.getView(R.id.im_select).setVisibility(View.GONE);
            helper.setText(R.id.tv_title, item.getMedicalName());
            helper.setText(R.id.tv_desc, item.getMedicalDesc());
            final TextView textedit = helper.itemView.findViewById(R.id.add_custom_medical);
            final TextView textcancel = helper.itemView.findViewById(R.id.cancel_custom_medical);
            final EditText etTitle = helper.itemView.findViewById(R.id.et_title);
            final TextView etContent = helper.itemView.findViewById(R.id.et_content);
            textedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "点击了编辑");
                    if (textedit.getText().equals("编辑")) {
                        textedit.setText("保存");
                        textcancel.setVisibility(View.GONE);
                        helper.getView(R.id.lv_edit).setVisibility(View.VISIBLE);
                        helper.getView(R.id.cancel_custom_medical).setVisibility(View.VISIBLE);
                        helper.getView(R.id.lv_content).setVisibility(View.GONE);

                    } else {
                        if (TextUtils.isEmpty(etTitle.getText().toString())) {
                            ToastUtil.setToast("请输入标题");
                            return;
                        }
                        if (TextUtils.isEmpty(etContent.getText().toString())) {
                            ToastUtil.setToast("请输入内容");
                            return;
                        }
                        textedit.setText("编辑");
                        textcancel.setVisibility(View.GONE);
                        helper.getView(R.id.cancel_custom_medical).setVisibility(View.GONE);
                        helper.getView(R.id.lv_edit).setVisibility(View.GONE);
                        helper.getView(R.id.lv_content).setVisibility(View.VISIBLE);
                        listener.saveDocument(helper.getAdapterPosition(), etTitle.getText().toString(), etContent.getText().toString());

                    }
                }
            });
            textcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etTitle.setText("");
                    etContent.setText("");
                    textedit.setText("编辑");
                    helper.getView(R.id.lv_edit).setVisibility(View.GONE);
                    helper.getView(R.id.lv_content).setVisibility(View.VISIBLE);
                    textcancel.setVisibility(View.GONE);
                }
            });

        } else {
            helper.getView(R.id.add_custom_medical).setVisibility(View.GONE);
            helper.getView(R.id.cancel_custom_medical).setVisibility(View.GONE);

            if (item.isSelected()) {
                helper.getView(R.id.im_select).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.im_select).setVisibility(View.INVISIBLE);
            }
            helper.setText(R.id.tv_title, item.getMedicalName());
            helper.setText(R.id.tv_desc, item.getMedicalDesc());
            //    helper.addOnClickListener(R.id.consult_message_item);
        }
    }


    public interface onDocumentAddListener {
        void saveDocument(int position, String title, String content);

        void cancelDcoument(int position);
    }

    public void setOnDocumentAddListener(onDocumentAddListener listener) {
        this.listener = listener;
    }
}
