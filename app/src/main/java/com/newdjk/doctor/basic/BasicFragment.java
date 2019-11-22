package com.newdjk.doctor.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;
import com.newdjk.doctor.utils.PermissionReq;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.TitleBuilder;


import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018-3-12.
 */

public abstract class BasicFragment extends Fragment implements View.OnClickListener {

    // 管理activity
    protected Context mContext;
    protected Activity mActivity;
    protected com.lxq.okhttp.MyOkHttp mMyOkhttp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(initViewResId(), container, false);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        mContext = getActivity();
        changeStatusBarTextColor(true);
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        initView();
        initListener();
        initData();
        return view;
    }

    protected void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                //恢复状态栏白色字体
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }


    /**
     * 初始化界面
     */
    protected abstract int initViewResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化界面
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 点击事件
     *
     * @param view
     */
    protected abstract void otherViewClick(View view);

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }

    /**
     * @param str 日志的处理
     */
    protected void logE(String str) {
        LogUtils.e("" + this.getClass().getSimpleName(), str);
    }

    /**
     * @param str 显示一个内容为str的toast
     */
    protected void toast(String str) {
        ToastUtil.setToast(str);
    }


    //标题栏 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder( View.inflate(mContext, R.layout.layout_titlebar, null))
                .setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.finish();
                    }
                });
    }

    /**
     * 左侧没有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initTitle(String title) {
        return new TitleBuilder( View.inflate(mContext, R.layout.layout_titlebar, null))
                .setTitleText(title);
    }
    //标题栏 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        if (intent == null) {
            logE("toActivity  intent == null >> return;");
            return;
        }
        //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
        if (requestCode < 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
        if (showAnimation) {
            getActivity().overridePendingTransition(R.anim.right_push_in, R.anim.hold);
        } else {
            getActivity().overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        }
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //手机返回键和菜单键实现同点击标题栏左右按钮效果<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    /**
     * 退出
     */
    protected void finishs() {
        if (enterAnim > 0 && exitAnim > 0) {
            try {
                getActivity().overridePendingTransition(enterAnim, exitAnim);
            } catch (Exception e) {
                logE("finish overridePendingTransition(enterAnim, exitAnim);" +
                        " >> catch (Exception e) {  " + e.getMessage());
            }
        }
        getActivity().finish();
        LoadDialog.clear();
    }
    //手机返回键和菜单键实现同点击标题栏左右按钮效果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMyOkhttp.cancel(this);
        LoadDialog.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 加载效果
     *
     * @param bool
     */
    protected void loading(boolean bool) {
        if (bool) {
            LoadDialog.show(mContext);
        } else {
            LoadDialog.clear();
        }
    }

    /**
     * 加载效果
     *
     * @param bool
     */
    protected void loading(boolean bool, String content) {
        if (bool) {
            LoadDialog.show(mContext, content);
        } else {
            LoadDialog.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class MyOkHttp {
    }
}
