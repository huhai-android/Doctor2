package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.ui.MianzhenDetailActivity;
import com.newdjk.doctor.ui.entity.MianzhenListEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.fragment.MianzhenFragment1;
import com.newdjk.doctor.utils.HeadUitl;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.MianzhenDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MianzhenAdapter extends BaseQuickAdapter<MianzhenListEntity.ReturnDataBean, BaseViewHolder> {

    private final Fragment fragment;
    private Gson mGson;
    private OnCancelListener listener;
    private final MyOkHttp mMyOkhttp;
    private MianzhenDialog mDialog;

    public MianzhenAdapter(@Nullable List<MianzhenListEntity.ReturnDataBean> data, Fragment context) {
        super(R.layout.list_mianzhen_item, data);
        mGson = new Gson();
        this.fragment = context;
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MianzhenListEntity.ReturnDataBean item) {

        helper.setText(R.id.tv_time, item.getAppointmentDate().substring(0, 10) + "");
        RecyclerView recyclerView = helper.itemView.findViewById(R.id.recyleviewitem);
        List<MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean> datalist = item.getFaceConsultationRecords();
        final MianzhenChildAdapter mMianzhenAdapterr = new MianzhenChildAdapter(item.getFaceConsultationRecords(), fragment);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mMianzhenAdapterr);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        helper.setText(R.id.tv_week, getweek(datalist) + "");
        if (datalist != null) {
            helper.setText(R.id.tv_number, item.getReserveNums() + "");
        } else {
            helper.setText(R.id.tv_number, "0");
        }
        if (item.isIshide()) {
            helper.setImageResource(R.id.im_arrow, R.mipmap.decline);
            recyclerView.setVisibility(View.GONE);
        } else {
            helper.setImageResource(R.id.im_arrow, R.mipmap.rise);
            recyclerView.setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.im_arrow);

        mMianzhenAdapterr.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //点击取消预约按钮，弹出对话框，

                if (fragment instanceof MianzhenFragment1) {
                    if (item.getFaceConsultationRecords().get(position).getAppointmentStatus() == 0) {
                        showdialog(item, item.getFaceConsultationRecords().get(position).getRecordId(), helper.getAdapterPosition(), position, mMianzhenAdapterr);
                    }
                } else {
                   // ToastUtil.setToast("已过期");
                }


            }
        });

        mMianzhenAdapterr.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent adviceintent = new Intent(mContext, MianzhenDetailActivity.class);
                adviceintent.putExtra("id", item.getFaceConsultationRecords().get(position).getRecordId() + "");
                mContext.startActivity(adviceintent);
            }
        });
    }


    public String getweek(List<MianzhenListEntity.ReturnDataBean.FaceConsultationRecordsBean> datalist) {
        String week = "";
        if (datalist != null) {
            if (datalist.size() > 0) {
                switch (datalist.get(0).getWeekDay()) {
                    case 1:
                        week = "星期一";
                        break;
                    case 2:
                        week = "星期二";
                        break;
                    case 3:
                        week = "星期三";
                        break;
                    case 4:
                        week = "星期四";
                        break;
                    case 5:
                        week = "星期五";
                        break;
                    case 6:
                        week = "星期六";
                        break;
                    case 7:
                        week = "星期日";
                        break;

                }
            }


        }
        return week;
    }


    public void addOnCancelListener(OnCancelListener listener) {
        this.listener = listener;
    }

    public void refreshdata(int position) {


    }

    public interface OnCancelListener {

        void cancel(int positionout, int positionInside);

    }


    public void CancelFaceConsultation(final MianzhenListEntity.ReturnDataBean item, final int getRecordId, final int positionout, final int positionInside, final MianzhenChildAdapter mMianzhenAdapterr) {

        Map<String, String> params = new HashMap<>();
        params.put("RecordId", getRecordId + "");
        params.put("PatientIdOrDrId", MyApplication.mDoctorInfoByIdEntity.getDrId() + "");//就诊人id/医生id。如果是会员取消传就诊人id，如果是医生取消传医生id
        params.put("CancelType", "2");//取消途径。1-会员主动取消；2-医生取消
        params.put("CancelReason", "");
        //  params.put("DrId", "68");
        // String json = mGson.toJson(params);

        mMyOkhttp.post().url(HttpUrl.CancelFaceConsultation).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                if (entity.getCode() == 0) {
                    //   mMianzhenAdapterr.notifyDataSetChanged(positionInside);
                    listener.cancel(positionout, positionInside);
                    item.getFaceConsultationRecords().get(positionInside).setAppointmentStatus(2);
                    mMianzhenAdapterr.notifyItemChanged(positionInside);

                } else {
                    // toast(entity.getMessage());
                    ToastUtil.setToast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
//                if (easylayout == null) {
//                    return;
//                }
//                if (easylayout.isRefreshing()) {
//                    easylayout.refreshComplete();
//                }
//                Log.d("errMsg", errorMsg);
                ToastUtil.setToast(errorMsg);
            }
        });

    }


    private void showdialog(final MianzhenListEntity.ReturnDataBean item, final int getRecordId, final int positionout, final int positioninside, final MianzhenChildAdapter mianzhenChildAdapter) {

        if (mDialog == null) {
            mDialog = new MianzhenDialog(mContext);
        }
        mDialog.show("", "", new MianzhenDialog.DialogListener() {
            @Override
            public void cancel() {
                mDialog = null;
            }

            @Override
            public void confirm() {
                mDialog = null;
                CancelFaceConsultation(item, getRecordId, positionout, positioninside, mianzhenChildAdapter);
            }
        });

    }

}
