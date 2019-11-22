package com.newdjk.doctor.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.ui.adapter.HospitalMessageListAdapter;
import com.newdjk.doctor.ui.entity.CategoryMessageEntity;
import com.newdjk.doctor.ui.entity.DepartmentMessageDataEntity;
import com.newdjk.doctor.ui.entity.DepartmentMessageEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.HospitalListRequestEntity;
import com.newdjk.doctor.ui.entity.HospitalMessageDataEntity;
import com.newdjk.doctor.ui.entity.HospitalMessageEntity;
import com.newdjk.doctor.ui.entity.ItemEntity;
import com.newdjk.doctor.ui.entity.LoginEb;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HospitalMessageListActivity extends BasicActivity {
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
    @BindView(R.id.message_list)
    RecyclerView messageList;
    private HospitalMessageListAdapter  mHospitalMessageListAdapter;
    private List<ItemEntity> mHospitalMessageList;
    private final String ACTIONTIP = "action";
    private int mAction;
    private Gson mGson;
    private int mProfessionId;
    private String TAG = "HospitalMessageListActivity";

    @Override
    protected int initViewResId() {
        return R.layout.hospital_message_list;
    }

    @Override
    protected void initView() {
        mHospitalMessageList = new ArrayList<>();
       // mHospitalMessageList.add("人民医院");
//        mHospitalMessageListAdapter = new HospitalMessageListAdapter(this,mHospitalMessageList);
        messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setAdapter(mHospitalMessageListAdapter);
        messageList.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mAction = intent.getIntExtra(ACTIONTIP,0);
        mProfessionId = intent.getIntExtra("professionId",0);
        mHospitalMessageList.clear();
        requestData(mAction);

    }

    @Override
    protected void otherViewClick(View view) {

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
    private void requestData(int action) {
        mGson = new Gson();
        switch (action) {
            case 1 :
                topTitle.setText(R.string.hospital_title);


                request(HttpUrl.QueryHospitalPage,1,10);
                break;
            case  2 :
                topTitle.setText(R.string.department_title);
                requestDepartment(HttpUrl.QueryDepartmentPage,1,10);
                //request(HttpUrl.QueryDepartmentPage);
                break;
            case 3:
                topTitle.setText(R.string.profession_title);
                showprofession();
               // request(HttpUrl.QueryHospitalPage);
                break;
            case 4 :
                topTitle.setText(R.string.post_title);
                requestPost(HttpUrl.QueryItemByCategoryId,mProfessionId);
             //   request(HttpUrl.QueryHospitalPage);
                break;
        }
    }
    private void request(String url,int pageIndex,int pageSize) {
        HospitalListRequestEntity hospitalListRequestEntity = new HospitalListRequestEntity();
        hospitalListRequestEntity.setPageIndex(pageIndex);
        hospitalListRequestEntity.setPageSize(pageSize);
        String json = mGson.toJson(hospitalListRequestEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

                try {
                    Type jsonType = new TypeToken<ResponseEntity<HospitalMessageEntity>>() {
                    }.getType();
                    ResponseEntity<HospitalMessageEntity> responseEntity = mGson.fromJson(responseStr,jsonType);
                    List<HospitalMessageDataEntity> list = responseEntity.getData().getReturnData();
                    for (int i = 0; i < list.size();i++) {
                        ItemEntity itemEntity = new ItemEntity();
                        itemEntity.setName(list.get(i).getHospitalName());
                        itemEntity.setId(list.get(i).getHospitalId());
                        mHospitalMessageList.add(itemEntity);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                HospitalMessageListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"mHospitalMessageList="+mHospitalMessageList.size());
                        mHospitalMessageListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
    private void showprofession() {
        ItemEntity doctorItemEntity = new ItemEntity();
        doctorItemEntity.setName(getString(R.string.doctor));
        doctorItemEntity.setId(1);
        mHospitalMessageList.add(doctorItemEntity);
        ItemEntity nurseItemEntity = new ItemEntity();
        nurseItemEntity.setName(getString(R.string.nurse));
        nurseItemEntity.setId(2);
        mHospitalMessageList.add(nurseItemEntity);
    }
    private void requestDepartment(String url,int pageIndex,int pageSize) {
        HospitalListRequestEntity hospitalListRequestEntity = new HospitalListRequestEntity();
        hospitalListRequestEntity.setPageIndex(pageIndex);
        hospitalListRequestEntity.setPageSize(pageSize);
        String json = mGson.toJson(hospitalListRequestEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.i("zdp","responseStr="+responseStr);
                try {
                    Type jsonType = new TypeToken<ResponseEntity<DepartmentMessageEntity>>() {
                    }.getType();
                    ResponseEntity<DepartmentMessageEntity> responseEntity = mGson.fromJson(responseStr,jsonType);
                    List<DepartmentMessageDataEntity> list = responseEntity.getData().getReturnData();
                    for (int i = 0; i < list.size();i++) {
                        ItemEntity doctorItemEntity = new ItemEntity();
                        doctorItemEntity.setName(list.get(i).getDepartmentName());
                        doctorItemEntity.setId(list.get(i).getDepartmentId());
                        mHospitalMessageList.add(doctorItemEntity);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                HospitalMessageListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"mHospitalMessageList="+mHospitalMessageList.size());
                        mHospitalMessageListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
    private void requestPost(String url,int categoryId) {
        url = url+"?CategoryId="+categoryId;
        Log.i("zdp","url="+url);
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.i("zdp","responseStr="+responseStr);
                try {
                    Type jsonType = new TypeToken<ResponseEntity<List<CategoryMessageEntity>>>() {
                    }.getType();
                    ResponseEntity<List<CategoryMessageEntity>> responseEntity = mGson.fromJson(responseStr,jsonType);
                    List<CategoryMessageEntity> list = responseEntity.getData();
                    for (int i = 0; i < list.size();i++) {
                        ItemEntity doctorItemEntity = new ItemEntity();
                        doctorItemEntity.setName(list.get(i).getCategoryItemName());
                        doctorItemEntity.setId(list.get(i).getCategoryItemValue());
                        mHospitalMessageList.add(doctorItemEntity);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                HospitalMessageListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"mHospitalMessageList="+mHospitalMessageList.size());
                        mHospitalMessageListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}
