package com.newdjk.doctor.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.sort.CharacterParser;
import com.newdjk.doctor.sort.MyLetterSortView;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.NewlyBuildGroupActivity;
import com.newdjk.doctor.ui.activity.PatientActivity;
import com.newdjk.doctor.ui.activity.WebViewActivity;
import com.newdjk.doctor.ui.adapter.PatientAdapter;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.PatientListDataEntity;
import com.newdjk.doctor.ui.entity.PatientListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ClearEditText;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 消息中心
 */
public class MessageFragment extends BasicFragment {


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
    @BindView(R.id.et_patient_msg)
    ClearEditText etPatientMsg;
    @BindView(R.id.mSearchTv)
    TextView mSearchTv;
    @BindView(R.id.mSearchContainer)
    LinearLayout mSearchContainer;
    @BindView(R.id.newly_patients)
    LinearLayout newlyPatients;
    @BindView(R.id.lv_grouping)
    LinearLayout lvGrouping;
    @BindView(R.id.group_help)
    LinearLayout groupHelp;
    @BindView(R.id.newly_reported_patients)
    LinearLayout newlyReportedPatients;
    @BindView(R.id.focus_patient)
    TextView focusPatient;
    @BindView(R.id.lv_focus_patient)
    LinearLayout lvFocusPatient;
    @BindView(R.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.right_letter)
    MyLetterSortView rightLetter;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private InputMethodManager inputMethodManager;
    private CharacterParser characterParser;
    private String mPatientNameContent = "";
    private int mIndex = 1;
    private int mDoctype;
    private PatientAdapter mPatientAdapter;

    public static MessageFragment getFragment() {
        return new MessageFragment();
    }

    private List<PatientListDataEntity> mPaintList = new ArrayList<>();
    private List<String> letterList = new ArrayList<>();
    private final static String TAG = "Test";

    @Override
    protected int initViewResId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        topTitle.setTextColor(getResources().getColor(R.color.black));
        tvRight.setTextColor(getResources().getColor(R.color.black));
        tvLeft.setTextColor(getResources().getColor(R.color.theme));

        topTitle.setText("我的患者");
        tvRight.setVisibility(View.GONE);
        tvRight.setText("群发助手");
        tvLeft.setText("新报道患者");
        tvRight.setVisibility(View.GONE);
        //tvLeft.setTextColor(getResources().getColor(R.color.white));
        tvLeft.setVisibility(View.GONE);
        topLeft.setVisibility(View.GONE);
        /*addFriends.setOnClickListener(this);*/
        mPatientAdapter = new PatientAdapter(getActivity());
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mPatientAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        tvRight.setOnClickListener(this);
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void initListener() {
        newlyReportedPatients.setOnClickListener(this);
        focusPatient.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        mSearchTv.setOnClickListener(this);
        lvFocusPatient.setOnClickListener(this);
        lvGrouping.setOnClickListener(this);
        groupHelp.setOnClickListener(this);
        newlyPatients.setOnClickListener(this);
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
//        if (mDoctype == 1) {
//            newlyReportedPatients.setText("分级转诊");
//        } else {
//            newlyReportedPatients.setText("新报道患者");
//
//        }
        messageRecyclerViewEvent();

        messageRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getActivity().getCurrentFocus() != null)
                        if (inputMethodManager != null)
                            inputMethodManager.hideSoftInputFromWindow(
                                    getActivity().getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        rightLetter.setOnTouchingLetterChangedListener(new MyLetterSortView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mPatientAdapter.getPositionForSection(s.charAt(0));
                Log.e(TAG, "onTouchingLetterChanged: " + position + "  " + s);
                if (position != -1) {
                    messageRecyclerView.smoothScrollToPosition(position);
                }

                if (!TextUtils.isEmpty(s)) {
                    tvChoose.setVisibility(View.VISIBLE);
                    tvChoose.setText(s + "");
                }

            }

            @Override
            public void onCancel() {
                tvChoose.setVisibility(View.GONE);
            }
        });

        etPatientMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    String content = etPatientMsg.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        toast("请输入患者信息,然后进行搜索");
                        return true;
                    }
                    //搜索
                    mPatientNameContent = etPatientMsg.getText().toString().trim();
                    mIndex = 1;
                    requestAllPaientMessage();
                }
                return false;
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

        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                mIndex++;

                requestAllPaientMessage();
            }

            @Override
            public void onRefreshing() {
                mIndex = 1;
                letterList.clear();
                easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                requestAllPaientMessage();
            }
        });
    }

    private void messageRecyclerViewEvent() {
        //  messageRecyclerView
    }

    @Override
    protected void initData() {
        characterParser = CharacterParser.getInstance();
        requestAllPaientMessage();
    }

    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 注册用户名(*托管模式，独立模式下请向自己私有服务器注册)
     */
 /*   private void regist(String account, String password) {
        if (bTLSAccount) {
            ILiveLoginManager.getInstance().tlsRegister(account, password, new ILiveCallBack() {
                @Override
                public void onSuccess(Object data) {
                    Toast.makeText(mContext, "regist success!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    Toast.makeText(mContext, "regist failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mAccountMgr.regist(account, password, new AccountMgr.RequestCallBack() {
                @Override
                public void onResult(int error, String response) {
                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).s嚜ssaghow();
                }
            });
        }
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, null, savedInstanceState);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newly_reported_patients:
                if (mDoctype == 1) {
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("type", 7);
                    startActivity(intent);
                } else {
                    Intent reportedIntent = new Intent(getContext(), PatientActivity.class);
                    reportedIntent.putExtra("action", 1);
                    startActivity(reportedIntent);
                }

                break;
            case R.id.focus_patient:
                Intent focusIntent = new Intent(getContext(), PatientActivity.class);
                focusIntent.putExtra("action", 2);
                startActivity(focusIntent);
                break;

            case R.id.tv_right:
                Intent newLyBuildIntent = new Intent(getContext(), NewlyBuildGroupActivity.class);
                startActivity(newLyBuildIntent);
                break;
            case R.id.tv_left:
//                Intent doctorHomeCard = new Intent(getContext(), DoctorHomeCardActivity.class);
//                doctorHomeCard.putExtra("title", "新增患者");
//                if (mDoctorInfoByIdEntity != null) {
//                    doctorHomeCard.putExtra("doctorInfoByIdEntity", mDoctorInfoByIdEntity);
//                    startActivity(doctorHomeCard);
//                }

                Intent reportedIntent = new Intent(getContext(), PatientActivity.class);
                reportedIntent.putExtra("action", 1);
                startActivity(reportedIntent);
                break;
            case R.id.mSearchTv:
                //搜索
                String content = etPatientMsg.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    toast("请输入患者信息,然后进行搜索");
                    return;
                }
                mPatientNameContent = etPatientMsg.getText().toString().trim();
                mIndex = 1;
                requestAllPaientMessage();
                break;


            case R.id.lv_focus_patient:
                Intent focusPatientIntent = new Intent(getContext(), PatientActivity.class);
                focusPatientIntent.putExtra("action", 2);
                startActivity(focusPatientIntent);
                break;
            case R.id.lv_grouping:
                Intent groupPatientIntent = new Intent(getContext(), PatientActivity.class);
                groupPatientIntent.putExtra("action", 3);
                startActivity(groupPatientIntent);
                break;
            case R.id.group_help:
                Intent grouphelpIntent = new Intent(getContext(), NewlyBuildGroupActivity.class);
                startActivity(grouphelpIntent);
                break;
            case R.id.newly_patients:
                Intent reportedIntent2 = new Intent(getContext(), PatientActivity.class);
                reportedIntent2.putExtra("action", 1);
                startActivity(reportedIntent2);
                break;
        }
    }


    private void getFriendsList() {
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("zdp", "getFriendList failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                for (TIMUserProfile res : result) {
                    Log.e("zdp", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
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


    private void requestAllPaientMessage() {
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
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                //mEasylayout.setVisibility(View.VISIBLE);
                messageRecyclerView.setVisibility(View.VISIBLE);
                if (entity.getCode() == 0) {
                    if (mIndex == 1) {
                        mPaintList.clear();
                    } else {
                        easylayout.loadMoreComplete();
                    }
                    List<PatientListDataEntity> list = entity.getData().getReturnData();
                    int total = list.size();

                    if (total < 20) {
                        easylayout.setLoadMoreModel(LoadModel.NONE);
                    } else {
                        easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                    }

                    final int len = list.size();


                    for (int i = 0; i < len; i++) {

                        PatientListDataEntity patientListDataEntity = list.get(i);
                        String patientName = patientListDataEntity.getPatientName();
                        Log.d("名字", "位置=" + i + "   姓名=" + patientName + "   缩写=" + patientListDataEntity.getNameLetter());
                        if (patientName.length() == 0) {
                            continue;
                        }

                        //组合首字母
                        if (!TextUtils.isEmpty(patientListDataEntity.getNameLetter())) {
                            if (!letterList.contains(patientListDataEntity.getNameLetter().substring(0, 1))) {
                                letterList.add(patientListDataEntity.getNameLetter().substring(0, 1));
                            }

                        } else {
                            if (!letterList.contains("#")){
                                letterList.add("#");
                            }

                        }

                        //组合数据
                        if (!TextUtils.isEmpty(patientListDataEntity.getNameLetter())) {
                            patientName = new StringBuffer(patientListDataEntity.getNameLetter().substring(0, 1)).append("&").append(patientName).toString();
                            patientListDataEntity.setPatientName(patientName);
                            list.set(i, patientListDataEntity);
                        } else {
                            patientName = new StringBuffer("#").append("&").append(patientName).toString();
                            patientListDataEntity.setPatientName(patientName);
                            list.set(i, patientListDataEntity);
                        }

                        mPaintList.add(patientListDataEntity);
                    }
                    if (mPaintList.size() > 0 && len == 0) {
                        toast("没有更多数据");
                    }
                    // mPaintList.addAll(list);
                    if (mPaintList.size() <= 0) {
                        Log.i("MessageFragment", "lenError");
                        // mEasylayout.setVisibility(View.GONE);
                        messageRecyclerView.setVisibility(View.GONE);
                        mNodataContainer.setVisibility(View.VISIBLE);
                    } else {
                        //  mEasylayout.setVisibility(View.VISIBLE);
                        messageRecyclerView.setVisibility(View.VISIBLE);
                        mNodataContainer.setVisibility(View.GONE);
                    }
                    Log.i("MessageFragment", "size=" + mPaintList.size());
                    Log.i("MessageFragment", "" + mPaintList.toString());
                    mPatientAdapter.setDatalist(mPaintList);
                    rightLetter.setdata(letterList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            topTitle.setText("我的患者(" + entity.getData().getTotal() + ")");
                        }
                    });
                } else {
                    toast(entity.getMessage());
                    Log.i("MessageFragment", "toastError");
                    //mEasylayout.setVisibility(View.GONE);
                    messageRecyclerView.setVisibility(View.GONE);
                }
                if (mPaintList.size() == 0) {
                    rightLetter.setVisibility(View.GONE);
                    mNodataContainer.setVisibility(View.VISIBLE);
                } else {
                    rightLetter.setVisibility(View.VISIBLE);
                    mNodataContainer.setVisibility(View.GONE);
                    mPatientAdapter.setDatalist(mPaintList);
                    rightLetter.setdata(letterList);
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                //mEasylayout.setVisibility(View.GONE);
                Log.i("MessageFragment", "onFailures");
                CommonMethod.requestError(statusCode, errorMsg);
                if (easylayout != null) {
                    easylayout.loadMoreComplete();
                    easylayout.refreshComplete();
                }


            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdatePatientViewEntity userEvent) {
        mIndex = 1;
        letterList.clear();
        requestAllPaientMessage();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.i(TAG, "AAAAAA");
        super.onDestroy();
    }


    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(DoctorInfoByIdEntity doctorInfoByIdEntity) {
        mDoctorInfoByIdEntity = doctorInfoByIdEntity;
    }

    @Override
    public void onResume() {
        super.onResume();
        // requestAllPaientMessage();
    }

    //判断字符串是不是以数字开头
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        return isNum.matches();
    }

}
