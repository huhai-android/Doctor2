package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.service.AuthService;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.GotoMainactivity;
import com.newdjk.doctor.ui.entity.IMMessageEntity;
import com.newdjk.doctor.ui.entity.LoginOutEntity;
import com.newdjk.doctor.ui.entity.LoginSuccess;
import com.newdjk.doctor.ui.entity.NoticeLoginEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TranslateToPatientView;
import com.newdjk.doctor.ui.entity.UpdateAllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.UpdateRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.UpdateViewEntity;
import com.newdjk.doctor.ui.fragment.HomeFragment;
import com.newdjk.doctor.ui.fragment.MessageFragment;
import com.newdjk.doctor.ui.fragment.MinFragment;
import com.newdjk.doctor.utils.BadgeUtil;
import com.newdjk.doctor.utils.CertUtis;
import com.newdjk.doctor.utils.FragmentController;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.UtilsStyle;
import com.newdjk.doctor.views.BottomTabRadioButton;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;
import com.tencent.TIMUserStatusListener;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.values.ConstantParams;
import cn.org.bjca.sdk.core.values.EnvType;

import static com.newdjk.doctor.MyApplication.getContext;


/**
 * 主界面
 */
public class MainActivity extends BasicActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.rb_tab1)
    BottomTabRadioButton rbTab1;
    @BindView(R.id.rb_tab3)
    BottomTabRadioButton rbTab3;
    @BindView(R.id.rb_tab4)
    BottomTabRadioButton rbTab4;

    private List<Fragment> listFragment = new ArrayList();
    private FragmentController fgtController = null;
    public static final int REQUEST_CODE = 111;

    private String SIG = "eJxlj11PgzAYhe-5FQ23GNdSasVkF6iQaVk25vdVg1BIYyilrcuc8b*bsSWS*N4*T84577cHAPAf84fzsqr6T*W4*9LCB1fAh-7ZH9Ra1rx0HJv6HxQ7LY3gZeOEGSEihIQQTh1ZC*VkI0-Gi3jnzriKw3Ai2fqDj03HlAhChGlMpjlWtiNcpsXN3SJ-y00xpEnKbNuwhq5Ywe5bxmZ0E6sqTPaoKdVK3F4Eibwe1sO62zwtX7Nq15kgsZfbffHMdBnMFM1ZZhfdkFLWZn07n08qnezE6S0c4whHaDpoK4yVvRqFECKCQgwP53s-3i9aql6S";
    private int mDoctype = 0;


    public static Intent getAct(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        if (!NotificationsUtils.isNotificationEnabled(this)) {
//            toSetting();
//        }

        MyApplication.gotoMainactivity = 1;
        // setStatusBarColor(this,R.color.tm);
//
//
//        UtilsStyle.statusBarLightMode(this);
//        //  setStatusBarColor(this,R.color.white);
//        setAndroidNativeLightStatusBar(this,false);
        //设置状态栏上的字体为黑色-因为本页面是白色必须设置
//        UtilsStyle.statusBarLightMode(this);
//        //  setStatusBarColor(this,R.color.tm);
//        setAndroidNativeLightStatusBar(this,false);
        setAndroidNativeLightStatusBar(this,false);

        SQLiteUtils.getInstance().deleteAllImData();
        mDoctype = (int) SpUtils.get(Contants.DocType, 0);
        EventBus.getDefault().register(this);
        rbTab1.setDrawablesTop(R.drawable.home_selectot);
        rbTab3.setDrawablesTop(R.drawable.message_selectot);
        rbTab4.setDrawablesTop(R.drawable.personal_selectot);
        listFragment.add(HomeFragment.getFragment());
        listFragment.add(MessageFragment.getFragment());
        if (mDoctype == 3) {
            rbTab3.setVisibility(View.GONE);

        }
        initfilepath();
        listFragment.add(MinFragment.getFragment());
        fgtController = new FragmentController(this, R.id.fl_main, listFragment);

        //初始化证书
        initcert();

       // setStatusBarColor(this, R.color.colorPrimary);

    }

    private void initcert() {
        boolean isExists = BJCASDK.getInstance().existsCert(mContext);
        boolean ExistStamp = BJCASDK.getInstance().existsStamp(mContext);
        if (BuildConfig.DEBUG) {
            BJCASDK.getInstance().setServerUrl(EnvType.INTEGRATE);
        } else {
            BJCASDK.getInstance().setServerUrl(EnvType.PUBLIC);
        }

        CertUtis.getUserInfo(MainActivity.this);
        Log.d("chat", "证书" + isExists + "  " + ExistStamp);
    }



    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void initfilepath() {
        createFile(Environment.getExternalStorageDirectory() + "/MDT");
    }

    public boolean createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            /**  注意这里是 mkdirs()方法  可以创建多个文件夹 */
            file.mkdirs();
        }

        return false;
    }

    @Override
    protected void initListener() {

        rbTab1.setOnClickListener(this);
        rbTab3.setOnClickListener(this);
        rbTab4.setOnClickListener(this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "收到切换界面消息");
        if (MyApplication.tag == 1) {
            fgtController.showFragment(1);
            rbTab1.setChecked(false);
            rbTab3.setChecked(true);
            rbTab4.setChecked(false);
            MyApplication.tag = 0;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

        //  ILiveSDK.getInstance().initSdk(mContext, 1400129246, 36176);
        fgtController.showFragment(0);
        rbTab1.setChecked(true);
        rbTab3.setChecked(false);
        rbTab4.setChecked(false);
        checkPermission();


        getImMessage();

        getBaiduToken();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {

        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        int len = permissions.size();
        if (len != 0) {
            String[] per = new String[len];
            for (int i = 0; i < len; i++) {
                per[i] = permissions.get(i);
            }
            requestPermissions(
                    per,
                    REQUEST_CODE);
            return false;
        }
        return true;

    }

    private void getBaiduToken() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String baidutoken = AuthService.getAuth();
                Log.i("MainActivity", "baidutoken=" + baidutoken);
                MyApplication.baiduToken = baidutoken;
            }
        }).start();


    }

    @Override
    protected void otherViewClick(View view) {


        switch (view.getId()) {
            case R.id.rb_tab1:
                fgtController.showFragment(0);
                rbTab1.setChecked(true);
                rbTab3.setChecked(false);
                rbTab4.setChecked(false);


                //设置状态栏上的字体为黑色-因为本页面是白色必须设置
                UtilsStyle.statusBarLightMode(this);
              //  setStatusBarColor(this,R.color.tm);
                setAndroidNativeLightStatusBar(this,true);
                break;

            case R.id.rb_tab3:
                fgtController.showFragment(1);
                rbTab1.setChecked(false);
                rbTab3.setChecked(true);
                rbTab4.setChecked(false);
                //changeStatusBarTextColor(true);
                //设置状态栏上的字体为黑色-因为本页面是白色必须设置
                UtilsStyle.statusBarLightMode(this);
              //  setStatusBarColor(this,R.color.white);
                setAndroidNativeLightStatusBar(this,true);
                break;
            case R.id.rb_tab4:
                fgtController.showFragment(2);
                rbTab1.setChecked(false);
                rbTab3.setChecked(false);
                rbTab4.setChecked(true);
               // changeStatusBarTextColor(true);
                //设置状态栏上的字体为黑色-因为本页面是白色必须设置
                UtilsStyle.statusBarLightMode(this);
                //  setStatusBarColor(this,R.color.white);
                setAndroidNativeLightStatusBar(this,true);

                break;
        }
//        toActivity(new Intent(this, LoginActivity.class));
    }


    private void Logout(final String error) {
        Map<String, String> map = new HashMap<>();
        map.put("Token", SpUtils.getString(Contants.Token));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.Logout).headers(headMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entituy) {

                if (entituy.getCode() == 0) {
                    boolean result = (boolean) entituy.getData();
                    if (result) {

                        //   EventBus.getDefault().post(new LoginOutEntity(true));
                    }
                } else {
                    toast(entituy.getMessage());
                }
                CommonMethod.mIsCanStartService = false;
                stopService(new Intent(MainActivity.this, MyService.class));
                LogOutUtil.getInstance().loginOut(MainActivity.this, true);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.mIsCanStartService = false;
                stopService(new Intent(MainActivity.this, MyService.class));
                LogOutUtil.getInstance().loginOut(MainActivity.this, true);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == HomeFragment.REQUEST_CODE) {
            HomeFragment homeFragment = (HomeFragment) listFragment.get(0);
            homeFragment.onActivityResult(requestCode, resultCode, intent);
        } else if (requestCode == ConstantParams.ACTIVITY_QR_SIGN) {
            String result = intent.getStringExtra(ConstantParams.KEY_SIGN_BACK);
            Log.i("HomeFragment", "result2 =" + result);
        } else if (requestCode == ConstantParams.ACTIVITY_QR_OAUTH) {
            String result = intent.getStringExtra(ConstantParams.KEY_SIGN_BACK);
            Log.i("HomeFragment", "result1 =" + result);
        }

    }


    /**
     * 双击退出登录
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            killAll();
            // System.exit(0);
          /*  //登出
            loading(true);
            TIMManager.getInstance().logout(new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    LoadDialog.clear();
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 列表请参见错误码表
                    Log.d("MainActivity", "logout failed. code: " + code + " errmsg: " + desc);
                    killAll();
                    System.exit(0);
                }
                @Override
                public void onSuccess() {
                    LoadDialog.clear();
                    Log.i("MainActivity","退出成功");
                    killAll();
                    System.exit(0);
                    //登出成功
                }
            });*/

        }
    }

    private void getImMessage() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        int id = SpUtils.getInt(Contants.Id, 0);
        map.put("UserId", String.valueOf(id));
        map.put("UserType", "2");
        if (mMyOkhttp == null) {
            mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        }
        mMyOkhttp.post().url(HttpUrl.loginIm).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<IMMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<IMMessageEntity> entity) {
                if (entity.getCode() == 0) {
                    IMMessageEntity iMMessageEntity = entity.getData();
                    if (iMMessageEntity != null) {
                        Log.i("zdp", "statusCode = " + statusCode);
                        Log.i("zdp", "usersig=" + iMMessageEntity.getUserSig());
                        loginSDK(iMMessageEntity.getIdentifier(), iMMessageEntity.getUserSig());
                    } else {
                        Toast.makeText(MainActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                        LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                    }
                    //  loginSDK("Web_trtc_02", SIG);
                } else {
                    Log.e("zdp", "error=" + entity.getMessage());
                    LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                    Toast.makeText(MainActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                    //  toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

      /*  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final Gson gson = new Gson();
        IMPostEntity iMPostEntity = new IMPostEntity();
        int id = SpUtils.getInt(Contants.Id,0);
        Log.i("zdp","id="+id);
        iMPostEntity.setUserId(id);
        iMPostEntity.setUserType(2);
        String headMessage = gson.toJson(iMPostEntity);
        RequestBody body = RequestBody.create(JSON, headMessage);
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(HttpUrl.loginIm)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zdp", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.i("zdp","responseStr="+responseStr);
                if(responseStr!=null) {
                    UserEntity entity = gson.fromJson(responseStr, UserEntity.class);
                    loginSDK(entity.getData().getIdentifier(), entity.getData().getUserSig());
                }
            }
        });*/
    }

    private void loginSDK(final String id, final String userSig) {
        ILiveLoginManager.getInstance().iLiveLogin(id, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                //  登录成功后，上报证书 ID 及设备 token

                TIMOfflinePushToken param = new TIMOfflinePushToken();
                param.setToken(MyApplication.mRegId);
                param.setBussid(MyApplication.busid);
                TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.i("ChatActivity", "errorCode=" + i + ",s=" + s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("ChatActivity", "onSuccess=");


                    }
                });

               /* getFriendsList();
                refreshView();*/
                //设置用户状态变更监听器，在回调中进行相应的处理
                GetAllRecordForDoctor(null);
                TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        Log.i("LogOutUtil", ILiveLoginManager.getInstance().getMyUserId() + "    " + TIMManager.getInstance().getLoginUser());
                        //  EventBus.getDefault().post(new NoticeLoginEntity(true));
                        Logout("当前账号在其它设备登录，请重新登录");
                        Toast.makeText(MainActivity.this, "当前账号在其它设备登录，请重新登录", Toast.LENGTH_SHORT).show();
                        SpUtils.put(Contants.canLogin, 1);
                    }

                    @Override
                    public void onUserSigExpired() {
                        Logout("当前账号凭证已过期，请重新登录");
                        SpUtils.put(Contants.canLogin, 1);
                    }
                });


                Log.i("LogOutUtil", ILiveLoginManager.getInstance().getMyUserId() + "    " + TIMManager.getInstance().getLoginUser());
                CommonMethod.mIsCanStartService = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(MainActivity.this, MyService.class));
                } else {
                   startService(new Intent(MainActivity.this, MyService.class));
                }
                //   startService(new Intent(MainActivity.this, MyService.class));
                String identifier = TIMManager.getInstance().getLoginUser();
                if (identifier != null && !identifier.equals("")) {
                    SpUtils.put(Contants.identifier, id);
                    SpUtils.put(Contants.UserSig, userSig);
                    EventBus.getDefault().post(new UpdateViewEntity(null));
                    //告诉首页去刷新列表
                    EventBus.getDefault().post(new LoginSuccess(true));
                } else {
                    LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                }
                //  Toast.makeText(mContext, ILiveLoginManager.getInstance().getMyUserId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (errCode == 6208) {
                    loginSDK(id, userSig);
                } else {
                    LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                    Toast.makeText(mContext, "Login failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(TranslateToPatientView userEvent) {
        Log.d(TAG, "收到切换界面消息");
        fgtController.showFragment(1);
        rbTab1.setChecked(false);
        rbTab3.setChecked(true);
        rbTab4.setChecked(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateAllRecordForDoctorEntity userEvent) {
        String imId = userEvent.imId;
        GetAllRecordForDoctor(imId);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateRecordForDoctorEntity userEvent) {
        GetAllRecordForDoctor(null);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(NoticeLoginEntity userEvent) {
        getImMessage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(LoginOutEntity userEvent) {
        clearAll();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(GotoMainactivity gotoMainactivity) {
        if (gotoMainactivity.isHasGotoMainActivity()) {
            MyApplication.FrontTime = System.currentTimeMillis();
            Log.d(TAG, "切换到了前台时间" + ((MyApplication.FrontTime - MyApplication.backTime) / 1000) + "s");
            long time = (MyApplication.FrontTime - MyApplication.backTime) / 1000;
            MyApplication.badgeNumber=0;
            Log.d("BadgeUtil","mainActivity红点显示个数"+MyApplication.badgeNumber);

            BadgeUtil.setBadgeCount(this,MyApplication.badgeNumber);
            String vendor = Build.MANUFACTURER;
            if (vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {

            } else {
                if (time > 15) {
                    Log.d(TAG, "切换到了前台时间大于15s，刷新数据");
                    SQLiteUtils.getInstance().deleteAllImData();
                    serviceTime = null;
                    GetAllRecordForDoctor(null);
                }
            }

        } else {
            Log.d(TAG, "切换到了后台");
            toast("芸医生已经切换到后台");
            MyApplication.backTime = System.currentTimeMillis();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonMethod.mIsCanStartService = false;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments"); //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }

        HMSAgent.connect(this, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.d(TAG, "HMS connect end:" + rst);
                getToken();
            }
        });



        super.onCreate(savedInstanceState);
    }

    /**
     * 获取token
     */

    private void getToken() {
        Log.d(TAG, "get token: begin");
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rst) {
                Log.d(TAG, "get token: end" + rst);
            }


        });
    }

    protected void clearAll() {
        // loading(true);
        MyApplication.exit();
        //   LoadDialog.clear();
        String userName = SpUtils.getString(Contants.userName);
        String password = SpUtils.getString(Contants.Password);
      /*  Intent intent = new Intent(MainActivity.this,MyService.class);
        stopService(intent);*/
        Intent loginOutIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        SpUtils.clear();
        SpUtils.put(Contants.IS_FIRST_USE, false);
        SpUtils.put(Contants.userName, userName);
        SpUtils.put(Contants.Password, password);
        SpUtils.put(Contants.Haslogin, true);
        startActivity(loginOutIntent);
    }

    private void toSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String vendor = Build.MANUFACTURER;
        if (vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
            Log.d(TAG, "针对华为手机单独处理");
            SQLiteUtils.getInstance().deleteAllImData();
            serviceTime = null;
            GetAllRecordForDoctor(null);
        }
    }
}
