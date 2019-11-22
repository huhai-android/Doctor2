package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateInfoActivity extends BasicActivity {
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
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.text_count)
    TextView textCount;
    @BindView(R.id.next_step)
    TextView nextStep;
    private static final int MAX_COUNT = 300;
    private String mAction;
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;
    private String mData;

    @Override
    protected int initViewResId() {
        return R.layout.edit_info;
    }

    @Override
    protected void initView() {
        mAction = getIntent().getStringExtra("action");
        mData = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(mAction)){
            if (mAction.equals("funGoodAt")) {
                initBackTitle("我的专长");
                etContent.setHint("请输入我的专长");
                etContent.setText(mData+"");
            }
            else if (mAction.equals("funTitle")) {
                initBackTitle("我的专治");
                etContent.setHint("请输入我的专治");
                etContent.setText(mData+"");
            }
            else if (mAction.equals("funIntroduction")) {
                initBackTitle("我的简介");
                etContent.setHint("请输入我的简介");
                etContent.setText(mData+"");
            }
        }

    }

    @Override
    protected void initListener() {
        nextStep.setOnClickListener(this);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(MAX_COUNT){

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub
                //获取字符个数(一个中文算2个字符)
                int destLen = getCharacterNum(dest.toString());
                int sourceLen = getCharacterNum(source.toString());
                if(destLen + sourceLen > MAX_COUNT){
                    toast("字数超过限制");
                    return "";
                }
                return source;

            }

        };
        etContent.setFilters(filters);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCount.setText("剩余字数：" + (MAX_COUNT - s.length()));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
     switch (view.getId()) {
         case R.id.next_step:
             String message = etContent.getText().toString();
             Intent intent = new Intent();
             intent.putExtra("message",message);
             setResult(RESULT_OK,intent);
             finish();
             break;
     }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    /**
     *
     * @param content
     * @return
     */
    public static int getCharacterNum(String content){
        if(content.equals("")||null == content){
            return 0;
        }else {
            return content.length()+getChineseNum(content);
        }

    }

    /**
     * 计算字符串的中文长度
     * @param s
     * @return
     */
    public static int getChineseNum(String s){
        int num = 0;
        char[] myChar = s.toCharArray();
        for(int i=0;i<myChar.length;i++){
            if((char)(byte)myChar[i] != myChar[i]){
                num++;
            }
        }
        return num;
    }
}
