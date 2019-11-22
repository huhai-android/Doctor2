package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.VideoInterrogationAppointmentActivity;
import com.newdjk.doctor.ui.adapter.ForVisistItemAdapter;
import com.newdjk.doctor.ui.entity.DoctorDutyComplexListEntity;
import com.newdjk.doctor.ui.entity.ForVisistMessageEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.UpdateConsultViewEntity1;
import com.newdjk.doctor.ui.entity.UpdateRenewalUnreadNum;
import com.newdjk.doctor.ui.entity.UpdateVideoUnreadNum;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ItemView;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoInterrogationScheduleFragment extends BasicFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = VideoInterrogationScheduleFragment.class.getSimpleName();
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.today_task)
    RelativeLayout todayTask;

    @BindView(R.id.appointment_visits_list)
    RecyclerView appointmentVisitsList;
    Unbinder unbinder;
    @BindView(R.id.not_task_tip)
    TextView notTaskTip;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.today_task_message)
    LinearLayout todayTaskMessage;
    @BindView(R.id.video_schdule_layout)
    LinearLayout videoSchduleLayout;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.click_refresh)
    TextView clickRefresh;
    @BindView(R.id.no_data_tip)
    LinearLayout noDataTip;
    @BindView(R.id.now_task)
    ItemView nowTask;
    private ForVisistItemAdapter mForVisistItemAdapter;
    private ForVisistMessageEntity mForVisistMessageEntity;
    public static boolean isreflash = false;

    public static VideoInterrogationScheduleFragment newInstance() {
        Bundle args = new Bundle();
        VideoInterrogationScheduleFragment myFragment = new VideoInterrogationScheduleFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.for_visit;
    }

    @Override
    protected void initView() {
        mForVisistItemAdapter = new ForVisistItemAdapter(getActivity());
        appointmentVisitsList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        appointmentVisitsList.setAdapter(mForVisistItemAdapter);
        appointmentVisitsList.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        todayTask.setOnClickListener(this);
        nowTask.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        clickRefresh.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDoctorDutyComplexList();
        //  getDoctorComingDutyList();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.today_task:
                if (mForVisistMessageEntity != null) {
                    Intent intent = new Intent(getContext(), VideoInterrogationAppointmentActivity.class);
                    intent.putExtra("id", mForVisistMessageEntity.getId());
                    startActivity(intent);
                } else {
                    toast("没有预约数据");
                }
                break;
            case R.id.click_refresh:
                refreshLayout.setRefreshing(true);
                getDoctorDutyComplexList();
                break;
            case R.id.now_task:
                Intent intent = new Intent(getContext(), VideoInterrogationAppointmentActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getDoctorComingDutyList() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetDoctorComingDutyList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorDutyComplexListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorDutyComplexListEntity> entity) {
                Log.d(TAG, "entity=" + entity.getData().toString());

                if (entity.getCode() == 0) {
                    mForVisistItemAdapter.setDatalist(entity.getData().getComingDutyList());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getDoctorDutyComplexList() {
        if (refreshLayout == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization",SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetDoctorDutyComplexList).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorDutyComplexListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorDutyComplexListEntity> entity) {
                Log.d(TAG, "entity=" + entity.getData().toString());
                if (refreshLayout == null) {
                    return;
                }
                LoadDialog.clear();
                refreshLayout.setRefreshing(false);
                if (entity.getCode() == 0) {
                    DoctorDutyComplexListEntity doctorDutyComplexListEntity = entity.getData();
                    if (doctorDutyComplexListEntity != null) {
                        if (entity.getData().getComingDutyList().getTotal() == 0) {
                            appointmentVisitsList.setVisibility(View.GONE);
                            noDataTip.setVisibility(View.VISIBLE);

                        } else {
                            appointmentVisitsList.setVisibility(View.VISIBLE);
                            noDataTip.setVisibility(View.GONE);
                            mForVisistItemAdapter.setDatalist(entity.getData().getComingDutyList());
                        }
                        if (entity.getData().getTodayDutyList().size() > 0) {
                            todayTask.setVisibility(View.VISIBLE);
                            mForVisistMessageEntity = entity.getData().getTodayDutyList().get(0);

                            if (mForVisistMessageEntity != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int number = mForVisistMessageEntity.getReserveNums();
                                        date.setText(mForVisistMessageEntity.getDutyDate());
                                        if (number > 0) {
                                            todayTaskMessage.setVisibility(View.VISIBLE);
                                            notTaskTip.setVisibility(View.GONE);
                                            String weekDay = "";
                                            int weekType = mForVisistMessageEntity.getWeekDay();
                                            switch (weekType) {
                                                case 1:
                                                    weekDay = "星期一";
                                                    break;
                                                case 2:
                                                    weekDay = "星期二";
                                                    break;
                                                case 3:
                                                    weekDay = "星期三";
                                                    break;
                                                case 4:
                                                    weekDay = "星期四";
                                                    break;
                                                case 5:
                                                    weekDay = "星期五";
                                                    break;
                                                case 6:
                                                    weekDay = "星期六";
                                                    break;
                                                case 7:
                                                    weekDay = "星期日";
                                                    break;
                                            }
                                            num.setText(number+"");
                                            time.setText(weekDay + "  " + mForVisistMessageEntity.getStartTime() + "-" + mForVisistMessageEntity.getEndTime());
                                        } else {
                                            todayTaskMessage.setVisibility(View.GONE);
                                            notTaskTip.setVisibility(View.VISIBLE);
                                        }

                                    }
                                });
                            }
                        } else {
                            notTaskTip.setVisibility(View.VISIBLE);
                            todayTask.setVisibility(View.GONE);
                        }
                    }
                } else {
                    toast(entity.getMessage());
                }
                //   mForVisistItemAdapter.setDatalist( entity.getData());
              /*  if (entity.getCode() == 200) {

                } else {
                    toast(entity.getMessage());
                }*/

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getDoctorDutyComplexList();
    }


    public void flashView (UpdateVideoUnreadNum userEvent) {
        refreshLayout.setRefreshing(true);
        getDoctorDutyComplexList();
    }
}
