package com.newdjk.doctor.ui.activity.min.accounts;//package com.lxq.compro.ui.activity.min.accounts;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.deceen.lxq.driver.R;
//import com.deceen.lxq.driver.entity.Entity;
//import com.deceen.lxq.driver.model.DriverModel;
//import com.deceen.lxq.driver.model.HttpUrl;
//import com.deceen.lxq.driver.model.MyCallback;
//import com.deceen.lxq.driver.utils.CommonMethod;
//import com.deceen.lxq.driver.utils.MyCountDownTimer;
//import com.deceen.lxq.driver.utils.SPInfo;
//import com.deceen.lxq.mylibrary.basic.BasicActivity;
//import com.deceen.lxq.mylibrary.utils.SpUtils;
//import com.deceen.lxq.mylibrary.utils.StrUtil;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//import java.util.Map;
//
///**
// * 修改密码
// */
//@ContentView(R.custom_tab.activity_modify_pwd)
//public class ModifyPwdActivity extends BasicActivity {
//    @ViewInject(R.id.input_phone)
//    EditText inputPhone;
//    @ViewInject(R.id.input_code)
//    EditText inputCode;
//    @ViewInject(R.id.tv_code)
//    TextView tvCode;
//    @ViewInject(R.id.new_password)
//    EditText newPassword;
//    private MyCountDownTimer myCountDownTimer;
//
//    public static Intent create(Context context, String phone) {
//        Intent intent = new Intent(context, ModifyPwdActivity.class);
//        intent.putExtra("phone", phone);
//        return intent;
//    }
//
//    @Override
//    protected void initView() {
//        initBackTitle("修改密码");
//        inputPhone.setText(StrUtil.getString(getIntent().getStringExtra("phone")));
//    }
//
//    @Override
//    protected void initListener() {
//        tvCode.setOnClickListener(this);
//    }
//
//    @Override
//    protected void initData() {
//        ////new倒计时对象,总共的时间,每隔多少秒更新一次时间
//        myCountDownTimer = new MyCountDownTimer(tvCode, 60000, 1000);
//    }
//
//    @Override
//    protected void otherViewClick(View view) {
//
//        if (StrUtil.isNotEmpty(inputPhone, true)) {
//            loading(true);
//            myCountDownTimer.start();
//            Map<String, String> map = CommonMethod.getUserIdMap();
//            map.put("PhoneNumber", StrUtil.getString(inputPhone));
//            DriverModel.submit(HttpUrl.updatePassword, map, new MyCallback<Entity>() {
//                @Override
//                public void get(Entity bean) {
//                    toast(bean.getMsg());
//                }
//            });
//        } else {
//            toast("手机号码不能为空");
//        }
//    }
//
//    /**
//     * 提交
//     *
//     * @param view 参数userId为string类型是用户ID;
//     *             参数 PhoneNumber为String类型是手机号码；（不能为空）
//     *             参数newPassword为string类型是新密码（不能为空）
//     */
//    @Event(R.id.btn_confirm)
//    private void confirmClick(View view) {
//        loading(true);
//        if (!StrUtil.isNotEmpty(inputCode, true)) {
//            toast("验证码不能为空");
//        } else if (!StrUtil.isNotEmpty(newPassword, true)) {
//            toast("新密码不能为空");
//        } else {
//            loading(true);
//            Map<String, String> map = CommonMethod.getUserIdMap();
//            map.put(SPInfo.driverId, (String) SpUtils.get(SPInfo.driverId, ""));
//            map.put("verificationCode", StrUtil.getString(inputCode));
//            map.put("newPassword", StrUtil.getString(newPassword));
//
//            DriverModel.submit(HttpUrl.updatePasswordDetermine, map, new MyCallback<Entity>() {
//                @Override
//                public void get(Entity bean) {
//                    toast(bean.getMsg());
//                    finish();
//                }
//            });
//        }
//    }
//}
