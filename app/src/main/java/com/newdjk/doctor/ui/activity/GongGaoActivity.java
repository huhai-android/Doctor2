/*
package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.GonggaoAdapter;
import com.newdjk.doctor.ui.entity.GongGaoListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.newdjk.doctor.MyApplication.getContext;


public class GongGaoActivity extends BasicActivity {


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
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    private GongGaoListEntity mGonggao;
    private List<GongGaoListEntity.ReturnDataBean> list = new ArrayList<>();
    private GonggaoAdapter mAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_gonggao;
    }

    @Override
    protected void initView() {
        initBackTitle("公告");
        initrevyleview();
    }

    private void initrevyleview() {
        mAdapter = new GonggaoAdapter(list);
        recyleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyleview.setAdapter(mAdapter);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mdtIntent = new Intent(GongGaoActivity.this, PrescriptionActivity.class);
                mdtIntent.putExtra("type", 32);
                mdtIntent.putExtra("NoticeManageId", list.get(position).getNoticeManageId());
                startActivity(mdtIntent);
            }
        });
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                initData();
            }
        });
    }

    @Override
    protected void initData() {

        GetNoticeManagePageList();

    }

    @Override
    protected void otherViewClick(View view) {

    }


    //获取公告
    private void GetNoticeManagePageList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("PageIndex", "1");
        params.put("PageSize", "100");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetNoticeManagePageList).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GongGaoListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GongGaoListEntity> entity) {


                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mGonggao = entity.getData();
                        list.clear();
                        if (entity.getData().getReturnData() != null) {
                            if (entity.getData().getReturnData().size() > 0) {
                                list.addAll(entity.getData().getReturnData());
                                mAdapter.setNewData(list);
                            }
                        }
                    }

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
            }
        });

    }
}
*/
package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.GonggaoAdapter;
import com.newdjk.doctor.ui.entity.GongGaoListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdatePushView;
import com.newdjk.doctor.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.newdjk.doctor.MyApplication.getContext;


public class GongGaoActivity extends BasicActivity {


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
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    private GongGaoListEntity mGonggao;
    private List<GongGaoListEntity.ReturnDataBean> list = new ArrayList<>();
    private GonggaoAdapter mAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_gonggao;
    }

    @Override
    protected void initView() {
        initBackTitle("公告");
        initrevyleview();
    }

    private void initrevyleview() {
        mAdapter = new GonggaoAdapter(list);
        recyleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyleview.setAdapter(mAdapter);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        easylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                EventBus.getDefault().post(new UpdatePushView(8));
                initData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(list.get(position).getLinkUrl())) {
                    Intent mdtIntent = new Intent(GongGaoActivity.this, PrescriptionActivity.class);
                    mdtIntent.putExtra("type", 32);
                    mdtIntent.putExtra("NoticeManageId", list.get(position).getNoticeManageId());

                    startActivity(mdtIntent);
                } else {

                    if (list.get(position).getLinkUrl().startsWith("http")) {
                        Intent mdtIntent = new Intent(GongGaoActivity.this, PrescriptionActivity.class);
                        mdtIntent.putExtra("LinkUrl", list.get(position).getLinkUrl());
                        mdtIntent.putExtra("type", 38);
                        mdtIntent.putExtra("NoticeManageId", list.get(position).getNoticeManageId());

                        startActivity(mdtIntent);
                    } else {
                        Intent mdtIntent = new Intent(GongGaoActivity.this, PrescriptionActivity.class);
                        mdtIntent.putExtra("LinkUrl", list.get(position).getLinkUrl());
                        mdtIntent.putExtra("type", 37);
                        mdtIntent.putExtra("NoticeManageId", list.get(position).getNoticeManageId());

                        startActivity(mdtIntent);
                    }

                }

            }
        });
    }

    @Override
    protected void initData() {
//        mGonggao = (GongGaoListEntity) getIntent().getSerializableExtra("Gonggao");
//        if (mGonggao != null) {
//            list.addAll(mGonggao.getReturnData());
//        }
        GetNoticeManagePageList();


    }

    @Override
    protected void otherViewClick(View view) {

    }

    //获取公告
    private void GetNoticeManagePageList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("PageIndex", "1");
        params.put("PageSize", "100");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetNoticeManagePageList).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GongGaoListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GongGaoListEntity> entity) {


                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mGonggao = entity.getData();
                        list.clear();
                        if (entity.getData().getReturnData() != null) {
                            if (entity.getData().getReturnData().size() > 0) {
                                list.addAll(entity.getData().getReturnData());
                                mAdapter.setNewData(list);
                            }
                        }
                    }

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
            }
        });

    }


}

/*
package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.GonggaoAdapter;
import com.newdjk.doctor.ui.entity.GongGaoListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.newdjk.doctor.MyApplication.getContext;


public class GongGaoActivity extends BasicActivity {


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
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    private GongGaoListEntity mGonggao;
    private List<GongGaoListEntity.ReturnDataBean> list = new ArrayList<>();
    private GonggaoAdapter mAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_gonggao;
    }

    @Override
    protected void initView() {
        initBackTitle("公告");
        initrevyleview();
    }

    private void initrevyleview() {
        mAdapter = new GonggaoAdapter(list);
        recyleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyleview.setAdapter(mAdapter);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mdtIntent = new Intent(GongGaoActivity.this, PrescriptionActivity.class);
                mdtIntent.putExtra("type", 32);
                mdtIntent.putExtra("NoticeManageId", list.get(position).getNoticeManageId());
                startActivity(mdtIntent);
            }
        });
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                initData();
            }
        });
    }

    @Override
    protected void initData() {

        GetNoticeManagePageList();

    }

    @Override
    protected void otherViewClick(View view) {

    }


    //获取公告
    private void GetNoticeManagePageList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("PageIndex", "1");
        params.put("PageSize", "100");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetNoticeManagePageList).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GongGaoListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GongGaoListEntity> entity) {


                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        mGonggao = entity.getData();
                        list.clear();
                        if (entity.getData().getReturnData() != null) {
                            if (entity.getData().getReturnData().size() > 0) {
                                list.addAll(entity.getData().getReturnData());
                                mAdapter.setNewData(list);
                            }
                        }
                    }

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
            }
        });

    }
}
*/
