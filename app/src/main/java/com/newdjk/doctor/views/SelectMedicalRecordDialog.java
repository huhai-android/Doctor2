package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

import butterknife.BindView;

public class SelectMedicalRecordDialog {
    RadioButton medicalRecord1;
    RadioButton medicalRecord2;
    RadioGroup radioGroupGender;
    TextView sure;
    private Context mContext;
    private Dialog mDialog;
    private int mId;
    public SelectMedicalRecordDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.seledct_medical_record_dialog, null);
            mDialog.setContentView(view);
             medicalRecord1 = view.findViewById(R.id.medical_record1);
            medicalRecord2 = view.findViewById(R.id.medical_record2);
            radioGroupGender =  view.findViewById(R.id.radioGroup_gender);
            sure =  view.findViewById(R.id.sure);
            radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.medical_record1:
                            mId = 1;
                            break;
                        case R.id.medical_record2:
                            mId = 2;
                            break;
                    }
                }
            });
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mDialog.show();
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
