package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;

import java.util.List;


public class MyDiagnoseAdapter extends BaseQuickAdapter<MedicalRecordByIdEntity.MainProblemsBean, BaseViewHolder> {
    private String TAG = "MyDiagnoseAdapter";
    private Gson mGson;
    private List<MedicalRecordByIdEntity.MainProblemsBean> datalist;
    private onRecodeAudioListener listener;


    public MyDiagnoseAdapter(@Nullable List<MedicalRecordByIdEntity.MainProblemsBean> data) {
        super(R.layout.diagnose_item, data);
        datalist = data;
        mGson = new Gson();
    }


    public void setOnAudioListener(onRecodeAudioListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MedicalRecordByIdEntity.MainProblemsBean item) {


        try {
            Log.d(TAG, helper.getAdapterPosition() + "-------" + datalist.size());
            final EditText editResult = helper.itemView.findViewById(R.id.et_result);
            LinearLayout imRecord = helper.itemView.findViewById(R.id.im_record);
            editResult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    datalist.get(helper.getAdapterPosition() - 1).setAnswer(s.toString());
//                    if (!TextUtils.isEmpty(s)){
//                        editResult.setBackgroundResource(R.drawable.et_diagnose);
//                    }else {
//                        editResult.setBackgroundResource(R.drawable.et_diagnose_input);
//                    }

                }
            });
            imRecord.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Log.d(TAG, "录音开始");
                        listener.startRecodAudio(helper.getAdapterPosition());

                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                            || event.getAction() == MotionEvent.ACTION_UP) {
                        // Log.d(TAG,"ACTION_CANCEL,ACTION_UP");
                        Log.d(TAG, "录音结束");
                        listener.stopRecodAudio(helper.getAdapterPosition());
                    }
                    return true;
                }
            });
            if (helper.getAdapterPosition() == datalist.size()) {
                helper.setText(R.id.tv_number, "专家意见");
                helper.setText(R.id.tv_title, "");
                if (TextUtils.isEmpty(item.getAnswer())) {
//                    editResult.setText("专家意见");
//                    editResult.setHint("");
                } else {
                    editResult.setText(item.getAnswer());
                    editResult.setSelection(editResult.getText().toString().length());
                }

            } else {
                helper.setText(R.id.tv_number, helper.getAdapterPosition() + "");
                helper.setText(R.id.tv_title, item.getProblem());
                if (TextUtils.isEmpty(item.getAnswer())) {
                   /* editResult.setText("普通");
                    editResult.setHint("");*/
                } else {
                    editResult.setText(item.getAnswer());
                    editResult.setSelection(editResult.getText().toString().length());
                }

            }


        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }


    public interface onRecodeAudioListener {
        void startRecodAudio(int position);

        void stopRecodAudio(int position);
    }
}
