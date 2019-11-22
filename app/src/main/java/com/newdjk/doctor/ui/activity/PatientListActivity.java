package com.newdjk.doctor.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.PatientListAdapter;
import com.newdjk.doctor.ui.entity.AdBannerInfo;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.ui.entity.PatientListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SendActivity;
import com.newdjk.doctor.ui.entity.SendGoodEntity;
import com.newdjk.doctor.ui.entity.SendGoodsEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ClearEditText;
import com.newdjk.doctor.views.ShareDialog;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import static java.util.regex.Pattern.compile;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   PatientListActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/6/12 13:48
 *  @描述：
 */
public class PatientListActivity extends BasicActivity {
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
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    @BindView(R.id.et_patient_msg)
    ClearEditText etPatientMsg;
    @BindView(R.id.mSearchTv)
    TextView mSearchTv;
    @BindView(R.id.mSearchContainer)
    LinearLayout mSearchContainer;
    private PatientListAdapter mPatientAdapter;
    private List<PatientListDataEntity> mPaintList = new ArrayList<>();
    private List<PatientListDataEntity> mPaintListSelect = new ArrayList<>();
    private String mPatientNameContent = "";
    private int mIndex = 1;
    private List<String> letterList = new ArrayList<>();
    private Gson mGson;
    private SendGoodsEntity mSendGoodsEntity;
    private ShareDialog mDialog;
    private int mType;
    private AdBannerInfo.DataBean mDataBean;

    @Override
    protected int initViewResId() {
        return R.layout.activity_apatientlist;
    }

    @Override
    protected void initView() {
        initTitle("患者列表").setRightText("发送(0)").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyApplication.mActivities.add(this);
        mGson = new Gson();
        String data = getIntent().getStringExtra("SendGoodsEntity");
        mDataBean = (AdBannerInfo.DataBean) getIntent().getSerializableExtra("bannerInfo");
//        mDataBean.setLinkContent("xxxxxxxxxxxxxxxxxx");
//        mDataBean.setShareTitle("活动分享");
//        mDataBean.setShareLink("www.baidu.com");
//        mDataBean.setShareCotent("娃哈哈娃哈哈哈哈哈哈哈哈哈哈哈傻大姐哈吉斯的哈建华大街回到家阿回到家安徽的");
        mType = getIntent().getIntExtra("type", 0);
        Log.d("PatientListActivity", "打印接受数据" + data);
        tvRight.setTextColor(getResources().getColor(R.color.colorPrimary));
        mSendGoodsEntity = mGson.fromJson(data, SendGoodsEntity.class);
        mPatientAdapter = new PatientListAdapter(this);
        recyleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyleview.setAdapter(mPatientAdapter);
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                mIndex++;

                requestAllPaientMessage();
            }

            @Override
            public void onRefreshing() {
                mIndex = 1;
                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                requestAllPaientMessage();
            }
        });

        etPatientMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIndex = 1;
                letterList.clear();
                mPatientNameContent = etPatientMsg.getText().toString().trim();
                requestAllPaientMessage();
            }
        });

        mSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                String content = etPatientMsg.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    toast("请输入患者信息,然后进行搜索");
                    return;
                }
                mPatientNameContent = etPatientMsg.getText().toString().trim();
                mIndex = 1;
                requestAllPaientMessage();
            }
        });

        mPatientAdapter.setOnItemClickListener(new PatientListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // toast("点击了");

                //  showSharedialog(position);
                if (mPaintList.size() > 0) {
                    boolean isselect = mPaintList.get(position).isIselect();
                    if (isselect) {
                        mPaintList.get(position).setIselect(false);
                        //移除对应id的数据
                        if (mPaintListSelect.size() > 0) {
                            for (int i = mPaintListSelect.size() - 1; i >= 0; i--) {
                                if (mPaintList.get(position).getPatientId() == mPaintListSelect.get(i).getPatientId()) {
                                    mPaintListSelect.remove(i);
                                }
                            }
                        }
                    } else {
                        if (mPaintListSelect.size() >= 9) {
                            toast("最多只能选择9人");
                            return;
                        }
                        mPaintList.get(position).setIselect(true);
                        mPaintListSelect.add(mPaintList.get(position));
                    }
                    tvRight.setText("发送(" + mPaintListSelect.size() + ")");
                    mPatientAdapter.notifyItemChanged(position);
                }


            }

            @Override
            public void onItemchildClick(View view, int position) {
                //  toast("选中");
            }
        });
                    tvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mPaintListSelect.size() == 0) {
                    toast("最少选择一人进行分享");
                    return;
                }
                showSharedialog(mPaintListSelect);
            }
        });
    }

    @Override
    protected void initData() {
        requestAllPaientMessage();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:

                break;
        }
    }

    private void requestAllPaientMessage() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        map.put("PatientId", "-1");
        map.put("RelationStatus", "1");
        map.put("IsPatientMain", "-1");
        map.put("IsDrKey", "-1");
        map.put("PatientName", mPatientNameContent);
        map.put("PageIndex", mIndex + "");
        map.put("PageSize", "20");
        Log.i("MessageFragment", "mIndex=" + mIndex);
        mMyOkhttp.post().url(HttpUrl.QueryDoctorPatientPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PatientListEntity>>() {
            @Override
            public void onSuccess(int statusCode, final ResponseEntity<PatientListEntity> entity) {
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                mEasylayout.setVisibility(View.VISIBLE);
                if (entity.getCode() == 0) {
                    if (mIndex == 1) {
                        mPaintList.clear();
                    } else {
                        mEasylayout.loadMoreComplete();
                    }
                    List<PatientListDataEntity> list = entity.getData().getReturnData();
                    int total = list.size();

                    if (total < 20) {
                        mEasylayout.setLoadMoreModel(LoadModel.NONE);
                    } else {
                        mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                    }

                    final int len = list.size();


                    for (int i = 0; i < len; i++) {
                        PatientListDataEntity patientListDataEntity = list.get(i);
                        if (mPaintListSelect.size() > 0 && list.size() > 0) {

                            for (int k = mPaintListSelect.size() - 1; k >= 0; k--) {
                                if (list.get(i).getPatientId() == mPaintListSelect.get(k).getPatientId()) {
                                    patientListDataEntity.setIselect(true);
                                }
                            }


                        }
                        mPaintList.add(patientListDataEntity);
                    }
                    if (mPaintList.size() > 0 && len == 0) {
                        toast("没有更多数据");
                    }
                    // mPaintList.addAll(list);
                    if (mPaintList.size() <= 0) {
                        Log.i("MessageFragment", "lenError");
                        mEasylayout.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    } else {
                        mEasylayout.setVisibility(View.VISIBLE);
                        mNodataContainer.setVisibility(View.GONE);
                    }
                    Log.i("MessageFragment", "size=" + mPaintList.size());
                    mPatientAdapter.setDatalist(mPaintList);

                } else {
                    toast(entity.getMessage());
                    Log.i("MessageFragment", "toastError");
                    mEasylayout.setVisibility(View.GONE);

                }
                if (mPaintList.size() == 0) {

                    mNodataContainer.setVisibility(View.VISIBLE);
                } else {
                    mNodataContainer.setVisibility(View.GONE);
                    mPatientAdapter.setDatalist(mPaintList);
                }
                loading(false);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                //mEasylayout.setVisibility(View.GONE);
                Log.i("MessageFragment", "onFailures");
                CommonMethod.requestError(statusCode, errorMsg);
                loading(false);
                if (mEasylayout != null) {
                    mEasylayout.loadMoreComplete();
                    mEasylayout.refreshComplete();
                }
                if (mPaintList.size() == 0) {
                    mNodataContainer.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public static String getAlphabet(String str) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin = null;
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(str.charAt(0),
                    defaultFormat)[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyin.substring(0, 1).toUpperCase();
    }

    //判断字符串是不是以数字开头
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        return isNum.matches();
    }

    private void showSharedialog(List<PatientListDataEntity> list) {
        if (mDialog == null) {
            mDialog = new ShareDialog(mActivity);
        }
        mDialog.show(list, new ShareDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
                if (mType == 0) {
                    SendGoodsRecommend(mPaintListSelect);

                } else {
                    SendWXMessageToPatient(mPaintListSelect);
                }
            }

            @Override
            public void remove(int patientid) {
//                if (mPaintListSelect.size() > 0) {
//                    for (int i = 0; i < mPaintList.size(); i++) {
//                        if (mPaintListSelect.get(position).getPatientId() == mPaintList.get(i).getPatientId()) {
//                            mPaintListSelect.get(position).setIselect(false);
//                            mPatientAdapter.notifyItemChanged(i);
//                        }
//                    }
//                    mPaintListSelect.remove(position);
//
//                }
                for (int i = 0; i < mPaintList.size(); i++) {
                    if (mPaintList.get(i).getPatientId() == patientid) {
                        mPaintList.get(i).setIselect(false);
                        mPatientAdapter.notifyItemChanged(i);
                    }
                }

                tvRight.setText("发送(" + mPaintListSelect.size() + ")");
            }
        });
    }

    //分享活动
    private void SendWXMessageToPatient(List<PatientListDataEntity> selectlist) {

        if (mDataBean == null) {
            toast("分享失败");
            return;
        }

        if (selectlist.size() == 0) {
            toast("最少选择一个病人");
            return;
        }
        if (TextUtils.isEmpty(mDataBean.getShareCotent())) {
            toast("分享内容为空，请重新登录并检查数据");
            return;
        }

        if (TextUtils.isEmpty(mDataBean.getShareLink())) {
            toast("分享连接为空，请重新登录并检查数据");
            return;
        }
        if (TextUtils.isEmpty(mDataBean.getShareLink())) {
            toast("分享标题为空，请重新登录并检查数据");
            return;
        }

        loading(true);

        SendActivity sendActivity = new SendActivity();
        List<SendActivity.PatientsBean> list = new ArrayList<>();
        for (int i = 0; i < selectlist.size(); i++) {
            SendActivity.PatientsBean patientsBean = new SendActivity.PatientsBean();
            patientsBean.setAccountId(selectlist.get(i).getAccountId());
            patientsBean.setPatientName(selectlist.get(i).getPatientName());
            list.add(patientsBean);
        }

        sendActivity.setPatients(list);
        sendActivity.setContent(mDataBean.getShareCotent());
        sendActivity.setDrId(MyApplication.mDoctorInfoByIdEntity.getDrId());
        sendActivity.setShareLink(mDataBean.getShareLink().replace("{drid}", String.valueOf(SpUtils.getInt(Contants.Id, 0))));
        sendActivity.setTitle(mDataBean.getShareTitle());
        sendActivity.setTitle(mDataBean.getShareTitle());

        String json = mGson.toJson(sendActivity);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.SendWXMessageToPatient).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {

                if (entity.getCode() == 0) {

                    toast("分享成功");
                    MyApplication.exit();
                } else {
                    toast("分享失败");
                    Log.d("xxxxxx", "33333333");
                }
                loading(false);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.d("aaaaaaaaaa", "TOKEN失效");
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    //分享商品
    private void SendGoodsRecommend(List<PatientListDataEntity> selectlist) {
        if (mSendGoodsEntity == null) {
            toast("分享内容为空");
            return;
        }
        if (selectlist.size() == 0) {
            toast("最少选择一个病人");
            return;
        }

        loading(true);
//        Map<String, String> map = new HashMap<>();
//        map.put("DataSource", mSendGoodsEntity.getDataSource() + "");
//        map.put("DataId", mSendGoodsEntity.getDataId() + "");
//        // map.put("PatientIdList", mPaintList.get(position).getPatientId() + "");
        // map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        SendGoodEntity sendGoodEntity = new SendGoodEntity();
        List<Integer> list = new ArrayList<>();
        sendGoodEntity.setDataId(mSendGoodsEntity.getDataId());
        sendGoodEntity.setDataSource(mSendGoodsEntity.getDataSource());


        for (int i = 0; i < selectlist.size(); i++) {
            list.add(selectlist.get(i).getPatientId());
        }
        sendGoodEntity.setPatientIdList(list);
        String json = mGson.toJson(sendGoodEntity);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.SendGoodsRecommend).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {

                if (entity.getCode() == 0) {

                    toast("分享成功");
                    MyApplication.exit();
                } else {
                    toast("分享失败");
                    Log.d("xxxxxx", "33333333");
                }
                loading(false);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.d("aaaaaaaaaa", "TOKEN失效");
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mActivities.remove(this);
    }
}
