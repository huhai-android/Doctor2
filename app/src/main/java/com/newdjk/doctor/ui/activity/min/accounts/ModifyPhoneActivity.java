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
// * 修改手机
// */
//@ContentView(R.custom_tab.activity_modify_phone)
//public class ModifyPhoneActivity extends BasicActivity {
//    @ViewInject(R.id.input_phone)
//    EditText inputPhone;
//    @ViewInject(R.id.input_code)
//    EditText inputCode;
//    @ViewInject(R.id.tv_code)
//    TextView tvCode;
//    @ViewInject(R.id.new_password)
//    EditText newPwd;
//    private MyCountDownTimer myCountDownTimer;
//
//    public static Intent create(Context context, String phone) {
//        Intent intent = new Intent(context, ModifyPhoneActivity.class);
//        intent.putExtra("phone", phone);
//        return intent;
//    }
//
//
//    @Override
//    protected void initView() {
//        initBackTitle("修改手机");
////        inputPhone.setText(StrUtil.getString(getIntent().getStringExtra("phone")));
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
//    /**
//     * 参数userId为string类型是用户ID;
//     * 参数 PhoneNumber为String类型是手机号码；（不能为空）
//     *
//     * @param view
//     */
//    @Override
//    protected void otherViewClick(View view) {
//        if (StrUtil.isNotEmpty(inputPhone, true)) {
//            loading(true);
//            myCountDownTimer.start();
//            Map<String, String> map = CommonMethod.getUserIdMap();
//            map.put("PhoneNumber", StrUtil.getString(inputPhone));
//            DriverModel.submit(HttpUrl.sendAuthenticationCode, map, new MyCallback<Entity>() {
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
//     * 参数userId为string类型是用户ID;
//     * 参数driverId为String类型是司机ID；
//     * 参数 verificationCode为String类型是验证码；（不能为空）
//     * 参数 PhoneNumber为String类型是手机号码；（不能为空）
//     *
//     * @param view
//     */
//    @Event(R.id.btn_confirm)
//    private void confirmClick(View view) {
//        if (!StrUtil.isNotEmpty(inputPhone, true)) {
//            toast("新手机号码不能为空");
//        } else if (!StrUtil.isNotEmpty(newPwd, true)) {
//            toast("登录密码不能为空");
//        }  else if (!StrUtil.isNotEmpty(inputCode, true)) {
//            toast("验证码不能为空");
//        } else if (StrUtil.getString(newPwd).length() < 6) {
//            toast("密码不能少于6位");
//        }  else {
//            loading(true);
//            Map<String, String> map = CommonMethod.getUserIdMap();
//            map.put("verificationCode", StrUtil.getString(inputCode));//：验证码
//            map.put("PhoneNumber", StrUtil.getString(inputPhone));//：手机号
//            map.put("pwd", StrUtil.getString(newPwd));//：登录密码
//            map.put("appType", "3");//：用户类型    货主-1,车主-2,司机-3
//            DriverModel.submit(HttpUrl.updatePhoneNumber, map, new MyCallback<Entity>() {
//                @Override
//                public void get(Entity bean) {
//                    toast(bean.getMsg());
//                    SpUtils.put(SPInfo.mobilePhone, StrUtil.getString(inputPhone));
//                    Intent intent = new Intent("com.deceen.lxq.driver.PersonalFragment");
//                    sendBroadcast(intent);
//                    finish();
//
//                }
//            });
//        }
//    }
//}
