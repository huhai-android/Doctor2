package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.DoctorCheckReportAdapter;
import com.newdjk.doctor.ui.entity.AllDoctorCheckReportEntity;
import com.newdjk.doctor.ui.entity.ChatDataEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.ScoreEntity;
import com.newdjk.doctor.ui.entity.ScoreValueCopyEntity;
import com.newdjk.doctor.ui.entity.SignRepoetEntity;
import com.newdjk.doctor.ui.entity.SignReportSuccess;
import com.newdjk.doctor.utils.Listener;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.PlaydemoView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import okhttp3.MediaType;
import pub.devrel.easypermissions.EasyPermissions;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   CustodyReportActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 16:41
 *  @描述：
 */
public class CustodyReportActivity extends BasicActivity implements EasyPermissions.PermissionCallbacks  {
    private static final int CALL_PHONE = 1;
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
    @BindView(R.id.tv_check_date)
    TextView tvCheckDate;
    @BindView(R.id.tv_check_number)
    TextView tvCheckNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_check_duration)
    TextView tvCheckDuration;
    @BindView(R.id.tv_check_start_time)
    TextView tvCheckStartTime;
    @BindView(R.id.tv_check_end_time)
    TextView tvCheckEndTime;
    @BindView(R.id.playdemoView)
    PlaydemoView playdemoView;
    @BindView(R.id.tv_check_doctor_name)
    TextView tvCheckDoctorName;
    @BindView(R.id.tv_check_doctor_sign_time)
    TextView tvCheckDoctorSignTime;
    @BindView(R.id.tv_sign_report)
    TextView tvSignReport;
    @BindView(R.id.tv_check_bmp)
    TextView tvCheckBmp;
    @BindView(R.id.tv_check_bmp_point)
    TextView tvCheckBmpPoint;
    @BindView(R.id.et_check_bmp)
    EditText etCheckBmp;
    @BindView(R.id.tv_check_bmp_change)
    TextView tvCheckBmpChange;
    @BindView(R.id.tv_check_bmp_change_point)
    TextView tvCheckBmpChangePoint;
    @BindView(R.id.et_check_bmp_change)
    EditText etCheckBmpChange;
    @BindView(R.id.tv_check_speed)
    TextView tvCheckSpeed;
    @BindView(R.id.tv_check_speed_point)
    TextView tvCheckSpeedPoint;
    @BindView(R.id.et_check_speed)
    EditText etCheckSpeed;
    @BindView(R.id.tv_check_speed_reduce)
    TextView tvCheckSpeedReduce;
    @BindView(R.id.tv_check_speed_reduce_point)
    TextView tvCheckSpeedReducePoint;
    @BindView(R.id.et_check_speed_time)
    EditText etCheckAddtime;
    @BindView(R.id.tv_check_taidong)
    TextView tvCheckTaidong;
    @BindView(R.id.tv_check_taidong_point)
    TextView tvCheckTaidongPoint;
    @BindView(R.id.et_check_taidong)
    EditText etCheckTaidong;
    @BindView(R.id.tv_check_point)
    TextView tvCheckPoint;
    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.et_advice)
    EditText etAdvice;
    @BindView(R.id.lv_root)
    LinearLayout lvRoot;
    @BindView(R.id.tv_heart_number)
    TextView rateTv;
    @BindView(R.id.tv_gongsuo_number)
    TextView tocoTv;
    @BindView(R.id.tv_reduce_one)
    TextView tvReduceOne;
    @BindView(R.id.tv_add_one)
    TextView tvAddOne;
    @BindView(R.id.tv_reduce_two)
    TextView tvReduceTwo;
    @BindView(R.id.tv_add_two)
    TextView tvAddTwo;
    @BindView(R.id.tv_reduce_three)
    TextView tvReduceThree;
    @BindView(R.id.tv_add_three)
    TextView tvAddThree;
    @BindView(R.id.tv_reduce_four)
    TextView tvReduceFour;
    @BindView(R.id.tv_add_four)
    TextView tvAddFour;
    @BindView(R.id.tv_reduce_five)
    TextView tvReduceFive;
    @BindView(R.id.tv_add_five)
    TextView tvAddFive;
    @BindView(R.id.tv_check_type)
    TextView tvCheckType;
    private String TAG = "CustodyReportActivity";
    private int maskid;
    private boolean isSign = false;
    private Listener.TimeData[] datas;
    private Random ra = new Random();
    private String mMonitorId;
    private List<AllDoctorCheckReportEntity.ResultItemsBean> datalist;
    private DoctorCheckReportAdapter doctorCheckReportAdapter;
    private int ResultId;
    private Gson mGson;
    private String time;
    private AllDoctorCheckReportEntity mDoctorInfoByDrIdEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_custody_report;
    }

    @Override
    protected void initView() {
        initBackTitle(getString(R.string.title_custody_report));
        tvCheckDoctorName.setText(getString(R.string.check_report_sign_name) + " " + SpUtils.getString(Contants.Name));
        doctorCheckReportAdapter = new DoctorCheckReportAdapter(datalist);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(doctorCheckReportAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        mGson = new Gson();
    }

    @Override
    protected void initListener() {
        doctorCheckReportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AllDoctorCheckReportEntity.ResultItemsBean item = datalist.get(position);
                Log.d(TAG, "-----------" + item.toString());
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    for (int i = 0; i < datalist.size(); i++) {
                        if (i != position) {
                            datalist.get(i).setChecked(false);
                        }
                    }
                }
                etAdvice.setText(item.getRemark()+"");
                doctorCheckReportAdapter.notifyDataSetChanged();

            }
        });

        tvSignReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先获取resultiddatalist.get(i)
                if (datalist != null) {
                    for (int i = 0; i < datalist.size(); i++) {
                        if (datalist.get(i).isChecked() == true) {
                            ResultId = datalist.get(i).getId();
                        }
                    }
                }


                signReport(ResultId);
            }
        });


        playdemoView.setNotifycrolledListener(new PlaydemoView.notifyScrolledListener() {

            @Override
            public void notifyScrolled(int time) {

                setRate(time);
            }
        });
        tvReduceOne.setOnClickListener(this);
        tvReduceTwo.setOnClickListener(this);
        tvReduceThree.setOnClickListener(this);
        tvReduceFour.setOnClickListener(this);
        tvReduceFive.setOnClickListener(this);
        tvAddOne.setOnClickListener(this);
        tvAddTwo.setOnClickListener(this);
        tvAddThree.setOnClickListener(this);
        tvAddFour.setOnClickListener(this);
        tvAddFive.setOnClickListener(this);
        tvNumber.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        maskid = getIntent().getIntExtra("askid", 0);
        isSign = getIntent().getBooleanExtra("isSign", false);
        getConsultRecordList(maskid + "");
        getCurrentTime();
    }

    private void getCurrentTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {

                time = response.getData().toString();
                tvCheckDoctorSignTime.setText(getString(R.string.check_report_sign_time) + " " + time);


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reduce_one:
                int data1 = Integer.parseInt(TextUtils.isEmpty(etCheckBmp.getText().toString()) ? "0" : etCheckBmp.getText().toString());
                data1--;
                if (data1 <= 0) {
                    data1 = 0;
                }
                etCheckBmp.setText(data1 + "");
                getScore();
                break;
            case R.id.tv_reduce_two:
                int data2 = Integer.parseInt(TextUtils.isEmpty(etCheckBmpChange.getText().toString()) ? "0" : etCheckBmpChange.getText().toString());
                data2--;
                if (data2 <= 0) {
                    data2 = 0;
                }
                etCheckBmpChange.setText(data2 + "");
                getScore();
                break;
            case R.id.tv_reduce_three:
                int data3 = Integer.parseInt(TextUtils.isEmpty(etCheckSpeed.getText().toString()) ? "0" : etCheckSpeed.getText().toString());
                data3--;
                if (data3 <= 0) {
                    data3 = 0;
                }
                etCheckSpeed.setText(data3 + "");
                getScore();
                break;
            case R.id.tv_reduce_four:
                int data4 = Integer.parseInt(TextUtils.isEmpty(etCheckAddtime.getText().toString()) ? "0" : etCheckAddtime.getText().toString());
                data4--;
                if (data4 <= 0) {
                    data4 = 0;
                }
                etCheckAddtime.setText(data4 + "");
                getScore();
                break;
            case R.id.tv_reduce_five:
                int data5 = Integer.parseInt(TextUtils.isEmpty(etCheckTaidong.getText().toString()) ? "0" : etCheckTaidong.getText().toString());
                data5--;
                if (data5 <= 0) {
                    data5 = 0;
                }
                etCheckTaidong.setText(data5 + "");
                getScore();
                break;

            case R.id.tv_add_one:
                int data6 = Integer.parseInt(TextUtils.isEmpty(etCheckBmp.getText().toString()) ? "0" : etCheckBmp.getText().toString() + "");
                data6++;
                etCheckBmp.setText(data6 + "");
                getScore();
                break;
            case R.id.tv_add_two:
                int data7 = Integer.parseInt(TextUtils.isEmpty(etCheckBmpChange.getText().toString()) ? "0" : etCheckBmpChange.getText().toString());
                data7++;
                etCheckBmpChange.setText(data7 + "");
                getScore();
                break;
            case R.id.tv_add_three:
                int data8 = Integer.parseInt(TextUtils.isEmpty(etCheckSpeed.getText().toString()) ? "0" : etCheckSpeed.getText().toString());
                data8++;
                etCheckSpeed.setText(data8 + "");
                getScore();
                break;
            case R.id.tv_add_four:
                int data9 = Integer.parseInt(TextUtils.isEmpty(etCheckAddtime.getText().toString()) ? "0" : etCheckAddtime.getText().toString());
                data9++;
                etCheckAddtime.setText(data9 + "");
                getScore();
                break;
            case R.id.tv_add_five:
                int data10 = Integer.parseInt(TextUtils.isEmpty(etCheckTaidong.getText().toString()) ? "0" : etCheckTaidong.getText().toString());
                data10++;
                etCheckTaidong.setText(data10 + "");
                getScore();
                break;


            case R.id.tv_number:
                String[] perms = {Manifest.permission.CALL_PHONE};

                if (EasyPermissions.hasPermissions(CustodyReportActivity.this, perms)) {
                    callPhone(mDoctorInfoByDrIdEntity.getMobile());
                } else {

                    EasyPermissions.requestPermissions(this, "拨打电话需要权限",
                            CALL_PHONE, perms);
                }
                break;
        }
    }

    private void setRate(int milliseconds) {
        playdemoView.setTime(milliseconds);
        String currStr = Listener.formatTime(milliseconds / 1000);
        int index = milliseconds / 500;
        setRate(index, currStr);
    }

    /**
     * 显示胎心率数据
     */
    private void setRate(int index, String currStr) {
        // timingTv.setText(currStr + "/" + toatTimeStr);
        if (index >= datas.length || index < 0)
            return;
        Listener.TimeData data = datas[index];

        if (data.heartRate < 50 || data.heartRate > 210) {
            rateTv.setText(getString(R.string.data_none));
        } else {
            rateTv.setText(String.valueOf(data.heartRate));
        }
        // currRate = data.heartRate;
        tocoTv.setText(String.valueOf(data.tocoWave));
    }

    private void getConsultRecordList(String askid) {
        Map<String, String> map = new HashMap<>();
        map.put("id", askid);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.getAllReadDataDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<AllDoctorCheckReportEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<AllDoctorCheckReportEntity> entity) {
                mDoctorInfoByDrIdEntity = entity.getData();
                //   Log.d(TAG, doctorInfoByDrIdEntity.toString() + "总分" + entity.getData().getScoreResult().getTotalScore());
                if (mDoctorInfoByDrIdEntity != null) {
                    mMonitorId = entity.getData().getMonitorId() + "";
                    getChatData();

                    tvCheckDate.setText(getString(R.string.title_custody_report_date) + "" + (entity.getData().getBeginDate().substring(0, 10)));
                    tvCheckNumber.setText(getString(R.string.title_custody_report_number) + "" + (entity.getData().getAskNo() == null ? "" : entity.getData().getAskNo()));
                    tvName.setText(getString(R.string.name_point) + "" + (entity.getData().getPatientName() == null ? "" : entity.getData().getPatientName()));
                    tvAge.setText(getString(R.string.age) + "" + (entity.getData().getAge() == null ? "" : entity.getData().getAge()));
                    tvWeek.setText(getString(R.string.week) + "" + (entity.getData().getWeeks() == null ? "" : entity.getData().getWeeks()));
                    tvNumber.setText(getString(R.string.phone) + "" + (entity.getData().getMobile() == null ? "" : entity.getData().getMobile()));
                    tvCheckDuration.setText(getString(R.string.check_duration) + "" + (timechanslate(entity.getData().getDuration()) == null ? "" : timechanslate(entity.getData().getDuration())));
                    tvCheckStartTime.setText(getString(R.string.check_start_time) + "" + (entity.getData().getBeginDate() == null ? "" : entity.getData().getBeginDate()));
                    tvCheckEndTime.setText(getString(R.string.check_end_time) + "" + (entity.getData().getEndDate() == null ? "" : entity.getData().getEndDate()));
                    //更新评分数据评分项: Bfhr:心率基线（bpm)、Var:基线变异（bpm)、Acct:加速、Dect:减速、Fmcnt:胎动次数、Fmv:加速变化(bpm) Fmd:加速时间(s)
                    if (entity.getData().getScoreResult() != null) {
                        tvCheckBmpPoint.setText(entity.getData().getScoreResult().getScoreItem().getBfhr().getScore() + "分");
                        etCheckBmp.setText(entity.getData().getScoreResult().getScoreItem().getBfhr().getValue());

                        tvCheckBmpChangePoint.setText(entity.getData().getScoreResult().getScoreItem().getVar().getScore() + "分");
                        etCheckBmpChange.setText(entity.getData().getScoreResult().getScoreItem().getVar().getValue());

                        tvCheckSpeedPoint.setText(entity.getData().getScoreResult().getScoreItem().getFmv().getScore() + "分");
                        etCheckSpeed.setText(entity.getData().getScoreResult().getScoreItem().getFmv().getValue());


                        tvCheckSpeedReducePoint.setText(entity.getData().getScoreResult().getScoreItem().getFmd().getScore() + "分");
                        etCheckAddtime.setText(entity.getData().getScoreResult().getScoreItem().getFmd().getValue());


                        tvCheckTaidongPoint.setText(entity.getData().getScoreResult().getScoreItem().getFmcnt().getScore() + "分");
                        etCheckTaidong.setText(entity.getData().getScoreResult().getScoreItem().getFmcnt().getValue());

                        //总分
                        tvCheckPoint.setText(getString(R.string.check_report_result_point).concat(entity.getData().getScoreResult().getTotalScore() + "分"));
                    }


                    Log.d(TAG, "获取图表格数据长度" + entity.getData().getResultItems());
                    datalist = entity.getData().getResultItems();
                    for (int i = 0; i <datalist.size() ; i++) {
                        if (datalist.get(i).isChecked()){
                            etAdvice.setText(datalist.get(i).getRemark());
                        }
                    }
                    doctorCheckReportAdapter.setNewData(datalist);

                } else {
                    toast(entity.getMessage());
                }


            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //获取表格数据
    private void getChatData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mMonitorId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.getchatData).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ChatDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ChatDataEntity> entity) {
                final ChatDataEntity doctorInfoByDrIdEntity = entity.getData();

                if (doctorInfoByDrIdEntity != null) {
                    // Log.d(TAG, "获取图表格数据" + entity.getData().toString() + Thread.currentThread());
                    if (doctorInfoByDrIdEntity.isAutofh()) {
                        tvCheckType.setText(R.string.auto_check);
                    } else {
                        tvCheckType.setText(R.string.manaul_ncheck);

                    }
                    if (entity.getData().getData() != null) {
                        String[] alldata = entity.getData().getData().toString().substring(1, entity.getData().getData().toString().length()).split(",");
                        datas = new Listener.TimeData[alldata.length / 10];
                        for (int i = 0; i < alldata.length / 10; i++) {
                            datas[i] = new Listener.TimeData();
                            for (int j = 0; j < 10; j++) {
                                if (j == 0) {
                                    //宫缩在下面
                                    datas[i].heartRate = Integer.parseInt(alldata[i * 10 + j].trim());
                                }
                                if (j == 3) {
                                    //心率 在上面
                                    datas[i].tocoWave = Integer.parseInt(alldata[i * 10 + j].trim());
                                }
                                if (j == 6) {
                                    //红心的标志位
                                    datas[i].status1 = Integer.parseInt(alldata[i * 10 + j].trim());
                                }

                            }
                            //   Log.d(TAG, "第" + i + "条数据" + datas[i].toString());
                        }
                        // Log.d(TAG, "数据长度" + datas.length);
                        playdemoView.setDatas(datas);
                    }

                    //
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private String timechanslate(int duration) {
        String time = "";
        int minutes = 0;
        int seconds = 0;
        minutes = duration / 60;
        seconds = duration % 60;
        if (minutes < 10) {
            time = "0" + minutes;
        } else {
            time = "" + minutes;
        }
        if (seconds < 10) {
            time = time + ":" + "0" + seconds;
        } else {
            time = time + ":" + seconds;
        }
        return time;
    }


    //签发报告  bfhr基线(bpm) ---var基线变异(bpm) ---fmcnt胎动次数（次/20min)---fmv加速变化(bpm) ---fmd加速时间(s)
    private void signReport(int Result) {
        loading(true);
        SignRepoetEntity signRepoetEntity = new SignRepoetEntity();
        signRepoetEntity.setDoctorId(SpUtils.getInt(Contants.Id, 0));
        signRepoetEntity.setAskId(maskid);
        signRepoetEntity.setDoctorName(SpUtils.getString(Contants.Name));
        signRepoetEntity.setSctype(2);
        signRepoetEntity.setResultId(Result);
        signRepoetEntity.setAdvise(etAdvice.getText().toString().isEmpty() ? "" : etAdvice.getText().toString());
        SignRepoetEntity.ScoreItemBean scoreItemBean = new SignRepoetEntity.ScoreItemBean();
        signRepoetEntity.setScoreItem(scoreItemBean);
        scoreItemBean.setBfhr(etCheckBmp.getText().toString().isEmpty() ? "" : etCheckBmp.getText().toString());
        scoreItemBean.setVar(etCheckBmpChange.getText().toString().isEmpty() ? "" : etCheckBmpChange.getText().toString());
        scoreItemBean.setCyc("0");
        scoreItemBean.setAcct("0");
        scoreItemBean.setDect("0");
        scoreItemBean.setFmcnt(etCheckTaidong.getText().toString().isEmpty() ? "" : etCheckTaidong.getText().toString());
        scoreItemBean.setFmd(etCheckAddtime.getText().toString().isEmpty() ? "" : etCheckAddtime.getText().toString());
        scoreItemBean.setFmv(etCheckSpeed.getText().toString().isEmpty() ? "" : etCheckSpeed.getText().toString());
        String json = mGson.toJson(signRepoetEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i("zdp", "json=" + json);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.submitSignReport).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                loading(false);
                Log.d(TAG, "签发报告" + "code=" + entity.getCode() + ",message=" + entity.getMessage());
                if (entity.getCode() == 0) {
                    toast("签发报告成功");
                    EventBus.getDefault().post(new SignReportSuccess(true));
                    finish();
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                loading(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    //签发报告
    private void getScore() {
        ScoreEntity scoreEntity = new ScoreEntity();
        ScoreEntity.ItemBean itemBean = new ScoreEntity.ItemBean();
        scoreEntity.setItem(itemBean);
        scoreEntity.setScoreType(2);
        itemBean.setBfhr(etCheckBmp.getText().toString().isEmpty() ? "0" : etCheckBmp.getText().toString());
        itemBean.setVar(etCheckBmpChange.getText().toString().isEmpty() ? "0" : etCheckBmpChange.getText().toString());
        itemBean.setCyc("0");
        itemBean.setAcct("0");
        itemBean.setDect("0");
        itemBean.setFmcnt(etCheckTaidong.getText().toString().isEmpty() ? "0" : etCheckTaidong.getText().toString());
        //加速时间
        itemBean.setFmd(etCheckAddtime.getText().toString().isEmpty() ? "0" : etCheckAddtime.getText().toString());
        //加速变化
        itemBean.setFmv(etCheckSpeed.getText().toString().isEmpty() ? "0" : etCheckSpeed.getText().toString());
        String json = mGson.toJson(scoreEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i("zdp", "json=" + json);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.post().url(HttpUrl.getScore).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ScoreValueCopyEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ScoreValueCopyEntity> entity) {
                Log.d(TAG, "获取评分" + "code=" + entity.getData());
                ScoreValueCopyEntity data = entity.getData();
                if (data != null) {
                    etAdvice.setText(data.getRemark()+"");
                    //更新评分数据评分项: Bfhr:心率基线（bpm)、Var:基线变异（bpm)、Acct:加速、Dect:减速、Fmcnt:胎动次数、Fmv:加速变化(bpm) Fmd:加速时间(s)
                    tvCheckBmpPoint.setText(data.getScoreItem().getBfhr() + "分");
                    tvCheckBmpChangePoint.setText(data.getScoreItem().getVar() + "分");
                    tvCheckSpeedPoint.setText(data.getScoreItem().getFmv() + "分");
                    tvCheckSpeedReducePoint.setText(data.getScoreItem().getFmd() + "分");
                    tvCheckTaidongPoint.setText(data.getScoreItem().getFmcnt() + "分");
                    tvCheckPoint.setText(getString(R.string.check_report_result_point).concat((data.getScoreItem().getBfhr() + data.getScoreItem().getVar() + data.getScoreItem().getFmd() + data.getScoreItem().getFmv() + data.getScoreItem().getFmcnt()) + "分"));

                } else {

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
                try {

                    if (v.getTag().toString().equals("1")) {
                        Log.d(TAG, "更新评分数据");
                        lvRoot.setFocusable(true);
                        lvRoot.requestFocus();
                        lvRoot.setFocusableInTouchMode(true);
                        lvRoot.requestFocusFromTouch();
                        getScore();

                    } else {
                        Log.d(TAG, "不可以更新评分数据");

                    }
                } catch (Exception e) {
                    Log.d(TAG, "数据强转失败" + e.toString());
                }

            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        callPhone(mDoctorInfoByDrIdEntity.getMobile());
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        toast("获取拨打电话权限失败");
    }
}