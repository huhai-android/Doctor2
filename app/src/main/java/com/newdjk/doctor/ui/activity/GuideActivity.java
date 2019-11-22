package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.ui.activity.login.RegisterActivity;
import com.newdjk.doctor.ui.fragment.GuideFragment;
import com.newdjk.doctor.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AbsGuideActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public List<SinglePage> buildGuideContent() {
        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mCustomFragment = GuideFragment.getFgt(1);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mCustomFragment = GuideFragment.getFgt(2);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mCustomFragment = GuideFragment.getFgt(3);
        guideContent.add(page03);
        SinglePage page04 = new SinglePage();
        page04.mCustomFragment = GuideFragment.getFgt(4);
        guideContent.add(page04);

        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = GuideFragment.getFgt(5);
        guideContent.add(page05);

        return guideContent;
    }

    @Override
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
    }

    @Override
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
    }

    @Override
    public boolean drawDot() {
        return false;
    }


    /**
     * You need provide an id to the pager. You could define an id in
     * values/ids.xml and use it.
     */
    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }

    /**
     * 注册
     */
    public void register() {
        SpUtils.put(Contants.IS_FIRST_USE, false);
        finish();
        Intent i = RegisterActivity.getAct(GuideActivity.this);
        startActivity(i);
    }

    /**
     * 登录
     */
    public void login() {
        SpUtils.put(Contants.IS_FIRST_USE, false);
        finish();
        Intent i = LoginActivity.getAct(GuideActivity.this);
        startActivity(i);
    }
}
