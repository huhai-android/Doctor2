package com.newdjk.doctor.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 重置密码
 */
public class ResettingActivity extends BasicActivity {
    @BindView(R.id.input_Password)
    EditText inputPassword;
    @BindView(R.id.input_new_Password)
    EditText inputNewPassword;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private String userId = "";//1 = 用户id

    public static Intent getIntent(Context context, String userId) {
        Intent intent = new Intent(context, ResettingActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_resetting;
    }

    @Override
    protected void initView() {
        initBackTitle("找回密码");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 完成
     */
    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isNull()) {
            loading(true);
            Map<String, String> map = new HashMap<>();
            String NewPass = StrUtil.getString(inputPassword);
            String TwoPass = StrUtil.getString(inputNewPassword);
            Log.e("zdp", "isequal=" + NewPass.equals(TwoPass) + ",TwoPass=" + TwoPass + ",userId=" + userId);
            map.put("Id", StrUtil.getString(userId));
            map.put("NewPass", StrUtil.getString(inputPassword));
            map.put("TwoPass", StrUtil.getString(inputNewPassword));
          /*  Map<String, String> headMap = new HashMap<>();
            headMap.put("Authorization", SpUtils.getString(Contants.Token));*/
            mMyOkhttp.post().url(HttpUrl.ChangeDoctorPassword).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entituy) {
                    LoadDialog.clear();
                    if (entituy.getCode() == 0) {
                        LogOutUtil.getInstance().loginOut(ResettingActivity.this, false);
                    } else {
                        toast(entituy.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
    }

    public boolean isNull() {
        if (StrUtil.getString(inputPassword).length() < 6) {
            toast("新密码为6-12位数字或字母");
            return false;
        } else if (!StrUtil.getString(inputPassword).equals(StrUtil.getString(inputNewPassword))) {
            Log.i("zdp", "cccccc");
            toast("两次密码不一致");
            return false;
        } else if (!isValidPassword(inputPassword.getText().toString())) {
            toast("密码为6-12位数字或字母");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        return isMatcherFinded("^[a-zA-Z0-9]{6,12}$", password);
    }

    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

}
