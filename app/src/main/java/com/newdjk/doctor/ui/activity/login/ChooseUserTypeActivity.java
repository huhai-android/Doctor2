package com.newdjk.doctor.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseUserTypeActivity extends BasicActivity {


    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.rv_nurse)
    LinearLayout rvNurse;
    @BindView(R.id.mNurse)
    ImageView mNurse;
    @BindView(R.id.mNurseType)
    LinearLayout mNurseType;
    @BindView(R.id.rv_doctor)
    LinearLayout rvDoctor;
    @BindView(R.id.mDoctor)
    ImageView mDoctor;
    @BindView(R.id.mDoctorType)
    LinearLayout mDoctorType;
    @BindView(R.id.rv_countryside_doctor)
    LinearLayout rvCountrysideDoctor;
    @BindView(R.id.mCounrtysideDoctor)
    ImageView mCounrtysideDoctor;
    @BindView(R.id.mCounrtysideDocType)
    LinearLayout mCounrtysideDocType;
    private int type; //8为护士  9为医生 10乡村医生
    private String mPositionName;

    @Override
    protected int initViewResId() {
        return R.layout.activity_choose_user_type;
    }

    @Override
    protected void initView() {
        initTitle("选择职业").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPositionName = getIntent().getStringExtra("PositionName");
        updateSelect();
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("保存");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", type);
                if (type == 8) {
                    intent.putExtra("typeName", "护士");
                }
                if (type == 9) {
                    intent.putExtra("typeName", "医生");
                }
                if (type == 10) {
                    intent.putExtra("typeName", "乡村医生");
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void updateSelect() {

        if (mPositionName.equals("护士")) {
            type = 8;
            mNurse.setSelected(true);
            mDoctor.setSelected(false);
            mCounrtysideDoctor.setSelected(false);
        } else if (mPositionName.equals("医生")) {
            type = 9;
            mDoctor.setSelected(true);
            mNurse.setSelected(false);
            mCounrtysideDoctor.setSelected(false);
        } else if (mPositionName.equals("乡村医生")) {
            type = 10;
            mCounrtysideDoctor.setSelected(true);
            mDoctor.setSelected(false);
            mNurse.setSelected(false);
        }


    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.mNurseType, R.id.mDoctorType, R.id.mCounrtysideDocType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mNurseType:
                mNurse.setSelected(true);
                mDoctor.setSelected(false);
                mCounrtysideDoctor.setSelected(false);
                type = 8;
                break;
            case R.id.mDoctorType:
                mDoctor.setSelected(true);
                mNurse.setSelected(false);
                mCounrtysideDoctor.setSelected(false);
                type = 9;
                break;
            case R.id.mCounrtysideDocType:
                mCounrtysideDoctor.setSelected(true);
                mDoctor.setSelected(false);
                mNurse.setSelected(false);
                type = 10;
                break;
        }
    }


}
