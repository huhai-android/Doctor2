package com.newdjk.doctor.ui.activity;

import android.app.Service;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.AllApplicationAdapter;
import com.newdjk.doctor.ui.adapter.UserApplicationAdapter;
import com.newdjk.doctor.ui.entity.AppLicationEntity;
import com.newdjk.doctor.ui.entity.FunctionDataEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.AppLicationUtils;
import com.newdjk.doctor.utils.SQLiteUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   AllApplicationActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/1/17 10:44
 *  @描述：
 */
public class AllApplicationActivity extends BasicActivity {
    private static final String TAG = "AllApplicationActivity";
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
    @BindView(R.id.re_user_app)
    RecyclerView reUserApp;
    @BindView(R.id.re_all_app)
    RecyclerView reAllApp;

    List<AppLicationEntity> listall = new ArrayList<>();
    List<AppLicationEntity> listuse = new ArrayList<>();
    private UserApplicationAdapter mAdapterUse;
    private AllApplicationAdapter mAdapterALL;
    private Gson mGson;
    private String mAlljson;
    private String mUsejson;
    private ItemTouchHelper mItemTouchHelper;
    private FunctionDataEntity mFunctionDataEntity;
    private String type = "完成";

    @Override
    protected int initViewResId() {
        return R.layout.activity_application;
    }

    @Override
    protected void initView() {
        initBackTitle("我的应用").setRightText("完成");
        initAppData();
        tvRight.setVisibility(View.GONE);
        GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 4);
        reUserApp.setLayoutManager(mManagerLayout);
        mAdapterUse = new UserApplicationAdapter(listuse);
        reUserApp.setAdapter(mAdapterUse);

        //拖拽逻辑处理
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (type.equals("完成")) {
                    if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                        final int swipeFlags = 0;
                        return makeMovementFlags(dragFlags, swipeFlags);
                    } else {
                        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                        final int swipeFlags = 0;
                        return makeMovementFlags(dragFlags, swipeFlags);
                    }
                }
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                if (type.equals("完成")) {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //拿到当前拖拽到的item的viewHolder
                    int toPosition = target.getAdapterPosition();
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(listuse, i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(listuse, i, i - 1);
                        }
                    }
                    mAdapterUse.notifyItemMoved(fromPosition, toPosition);
                    finishEdit();
                }
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.alpha_1));
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                  viewHolder.itemView.setBackgroundColor(0);
            }
        });
        mItemTouchHelper.attachToRecyclerView(reUserApp);


        GridLayoutManager mManagerLayout2 = new GridLayoutManager(mContext, 4);
        reAllApp.setLayoutManager(mManagerLayout2);
        mAdapterALL = new AllApplicationAdapter(listall);
        reAllApp.setAdapter(mAdapterALL);

    }

    private void initAppData() {
        mGson = new Gson();
        mFunctionDataEntity = SQLiteUtils.getInstance().selectFunctionDataById(SpUtils.getInt(Contants.Id, 0));
        String use = null;
        String all = null;
        List<AppLicationEntity> initListall = AppLicationUtils.getListall();
        if (mFunctionDataEntity != null) {
            use = mFunctionDataEntity.getData();
            all = mFunctionDataEntity.getAllFunctionData();
        }
        Log.d(TAG, "常用" + use);
        Log.d(TAG, "所有" + all);

        if (TextUtils.isEmpty(use)) {
            listuse = AppLicationUtils.getListuse();
        } else {
            listuse = mGson.fromJson(use, new TypeToken<List<AppLicationEntity>>() {
            }.getType());
        }
        if (TextUtils.isEmpty(all)) {
            listall.clear();
            listall.addAll(initListall);
        } else {
            listall.clear();
            List<AppLicationEntity> listdemo = mGson.fromJson(all, new TypeToken<List<AppLicationEntity>>() {
            }.getType());
            listall.addAll(listdemo);

//            if (BuildConfig.IS_DEBUG) {
//                if (listall.size() < initListall.size()) {
//                    AppLicationEntity appLicationEntity = new AppLicationEntity();
//                    appLicationEntity.setAppID(8);
//                    appLicationEntity.setAppDesc("视频问诊");
//                    appLicationEntity.setImageID(8);
//                    listall.add(appLicationEntity);
//                }
//            }
        }

    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);
        mAdapterUse.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "常用" + position);
                for (int i = 0; i < listall.size(); i++) {
                    if (listall.get(i).getAppID() == listuse.get(position).getAppID()) {
                        listall.get(i).setIsselect(false);
                    }
                }
                listuse.remove(position);
                finishEdit();
                mAdapterUse.notifyDataSetChanged();
                mAdapterALL.notifyDataSetChanged();
            }
        });

        mAdapterALL.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "常用" + position);
                if (!listall.get(position).isIsselect()) {
                    listuse.add(listall.get(position));
                    listall.get(position).setIsselect(true);
                }
                finishEdit();
                mAdapterUse.notifyDataSetChanged();
                mAdapterALL.notifyDataSetChanged();
            }
        });


        reUserApp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //获取系统震动服务
                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                //震动70毫秒
                vib.vibrate(70);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        //筛选数据
        mAdapterUse.notifyDataSetChanged();
        mAdapterALL.notifyDataSetChanged();

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
//                if (tvRight.getText().equals("完成")) {
//                    tvRight.setText("完成");
//                    type = "完成";
//                    mAdapterUse.settype("完成");
//                    mAdapterALL.settype("完成");
//                } else {
                //创建json集合
                tvRight.setText("完成");
                mAdapterUse.settype("完成");
                mAdapterALL.settype("完成");
                finishEdit();
                finish();
                //    }

                break;


        }
    }

    private void finishEdit() {

        mAlljson = mGson.toJson(listall);
        mUsejson = mGson.toJson(listuse);
        FunctionDataEntity functionDataEntity = new FunctionDataEntity(null, mUsejson, SpUtils.getInt(Contants.Id, 0), mAlljson);
               /* SpUtils.put(Contants.USEJSON, mUsejson);
                SpUtils.put(Contants.ALLJSON, mAlljson);*/
        SQLiteUtils.getInstance().addFunctionData(functionDataEntity);
       // ToastUtil.setToast("保存成功");
        EventBus.getDefault().post(new UpdatePushView(11));
    }


}
