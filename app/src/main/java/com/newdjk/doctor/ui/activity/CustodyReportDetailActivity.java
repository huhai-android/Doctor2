package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AllDoctorCheckReportEntity;
import com.newdjk.doctor.ui.entity.ChatDataEntity;
import com.newdjk.doctor.ui.entity.ReportDetailEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.Listener;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.PlaydemoView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   CustodyReportActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 16:41
 *  @描述：
 */
public class CustodyReportDetailActivity extends BasicActivity implements EasyPermissions.PermissionCallbacks {

    private static final int CALL_PHONE =1 ;
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
    @BindView(R.id.tv_check_doctor_advice)
    TextView tvCheckDoctorAdvice;
    @BindView(R.id.tv_check_doctor_name)
    TextView tvCheckDoctorName;
    @BindView(R.id.tv_check_doctor_sign_time)
    TextView tvCheckDoctorSignTime;
    @BindView(R.id.tv_check_report_result)
    TextView tvCheckReportResult;
    @BindView(R.id.tv_check_report_score)
    TextView tvCheckReportScore;
    @BindView(R.id.tv_heart_number)
    TextView rateTv;
    @BindView(R.id.tv_gongsuo_number)
    TextView tocoTv;
    @BindView(R.id.tv_check_type)
    TextView tvCheckType;
    private String TAG = "CustodyReportActivity";
    private int maskid;
    private boolean isSign = false;
    private Listener.TimeData[] datas;
    private Random ra = new Random();
    private String mMonitorId;
    private List<AllDoctorCheckReportEntity.ResultItemsBean> datalist;
    private int ResultId;
    private Gson mGson;
    private AllDoctorCheckReportEntity mDoctorInfoByDrIdEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_custody_report_detail;
    }

    @Override
    protected void initView() {
        initBackTitle(getString(R.string.title_custody_report));
        tvCheckDoctorName.setText(getString(R.string.check_report_sign_name) + " " + SpUtils.getString(Contants.Name));
        mGson = new Gson();

    }

    @Override
    protected void initListener() {
        playdemoView.setNotifycrolledListener(new PlaydemoView.notifyScrolledListener() {

            @Override
            public void notifyScrolled(int time) {

                setRate(time);
            }
        });

        tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = {Manifest.permission.CALL_PHONE};
                if (EasyPermissions.hasPermissions(CustodyReportDetailActivity.this, perms)) {
                    EasyPermissions.requestPermissions(CustodyReportDetailActivity.this, "拨打电话需要权限",
                            CALL_PHONE, perms);
                } else {

                    callPhone(mDoctorInfoByDrIdEntity.getMobile());
                }


            }
        });

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

    @Override
    protected void initData() {
        maskid = getIntent().getIntExtra("askid", 0);
        isSign = getIntent().getBooleanExtra("isSign", false);
        getConsultRecordList(maskid + "");
    }

    @Override
    protected void otherViewClick(View view) {

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

                Log.d(TAG, entity.getData().toString() + Thread.currentThread());

                if (mDoctorInfoByDrIdEntity != null) {
                    mMonitorId = entity.getData().getMonitorId() + "";
                    getChatData();
                    getReportDetail();
                    tvCheckNumber.setText(getString(R.string.title_custody_report_number) + "" + (entity.getData().getAskNo() == null ? "" : entity.getData().getAskNo()));
                    tvName.setText(getString(R.string.name_point) + "" + (entity.getData().getPatientName() == null ? "" : entity.getData().getPatientName()));
                    tvAge.setText(getString(R.string.age) + "" + (entity.getData().getAge() == null ? "" : entity.getData().getAge()));
                    tvWeek.setText(getString(R.string.week) + "" + (entity.getData().getWeeks() == null ? "" : entity.getData().getWeeks()));
                    tvNumber.setText(getString(R.string.phone) + "" + (entity.getData().getMobile() == null ? "" : entity.getData().getMobile()));
                    tvCheckDuration.setText(getString(R.string.check_duration) + "" + (timechanslate(entity.getData().getDuration()) == null ? "" : timechanslate(entity.getData().getDuration())));
                    tvCheckStartTime.setText(getString(R.string.check_start_time) + "" + (entity.getData().getBeginDate() == null ? "" : entity.getData().getBeginDate()));
                    tvCheckEndTime.setText(getString(R.string.check_end_time) + "" + (entity.getData().getEndDate() == null ? "" : entity.getData().getEndDate()));
                    Log.d(TAG, "获取图表格数据长度" + entity.getData().getResultItems());
                    datalist = entity.getData().getResultItems();


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

    private void getReportDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mMonitorId);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", MyApplication.token);
        mMyOkhttp.get().url(HttpUrl.getReportDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ReportDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ReportDetailEntity> entity) {
                final ReportDetailEntity doctorInfoByDrIdEntity = entity.getData();
                Log.d(TAG, "获取报告详情" + entity.getData().toString() + Thread.currentThread());

                if (doctorInfoByDrIdEntity != null) {
                    tvCheckReportScore.setText(doctorInfoByDrIdEntity.getScTypeName() + ": " + (TextUtils.isEmpty(doctorInfoByDrIdEntity.getScore())?"0":doctorInfoByDrIdEntity.getScore()) + "分");
                    tvCheckReportResult.setText(doctorInfoByDrIdEntity.getResult());
                    if (TextUtils.isEmpty(doctorInfoByDrIdEntity.getReadTime())) {
                        tvCheckDoctorSignTime.setText(getString(R.string.check_report_sign_time));
                    } else {
                        tvCheckDoctorSignTime.setText(getString(R.string.check_report_sign_time) + " " + doctorInfoByDrIdEntity.getReadTime());
                    }
                    tvCheckDate.setText(getString(R.string.title_custody_report_date) + " " + doctorInfoByDrIdEntity.getMonitorTime().substring(0, 10));
                    tvCheckDoctorAdvice.setText(TextUtils.isEmpty(doctorInfoByDrIdEntity.getAdvice()) ? "无" : doctorInfoByDrIdEntity.getAdvice());
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

                    if (doctorInfoByDrIdEntity.isAutofh()) {
                        tvCheckType.setText(R.string.auto_check);
                    } else {
                        tvCheckType.setText(R.string.manaul_ncheck);

                    }
                    if (entity.getData().getData() != null) {
                        // Log.d(TAG, "获取图表格数据" + entity.getData().toString() + Thread.currentThread());
                        String[] alldata = entity.getData().getData().toString().substring(1, entity.getData().getData().toString().length()).split(",");
//                    Log.d(TAG, "获取图表格数据长度" + alldata.toString());
//                    Log.d(TAG, "获取图表格数据长度" + alldata[0]);

                        //弄了些假数据
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

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent2.setData(data);
        startActivity(intent2);
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
