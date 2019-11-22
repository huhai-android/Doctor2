package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.QueryDiseaseEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SymptomSelectAdapter extends RecyclerView.Adapter<SymptomSelectAdapter.MyViewHolder> {



    private QueryDiseaseEntity mDataList;

    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private MyItemClickListener mItemClickListener;
    private List<QueryDiseaseEntity.ReturnDataBean> mHasSelectedDieaseList;

    public SymptomSelectAdapter(Activity activity) {
        mActivity = activity;
        mHasSelectedDieaseList = new ArrayList<>();
        //mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SymptomSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.chick_unit, parent, false);
        return new SymptomSelectAdapter.MyViewHolder(itemView,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(SymptomSelectAdapter.MyViewHolder holder, final int position) {
        holder.chickUnit.setText(mDataList.getReturnData().get(position).getDiseaseName());
        boolean isSelected = mDataList.getReturnData().get(position).isSelected();
        holder.selectBox.setChecked(isSelected);

       /* holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0;i<mDataList.size();i++) {
                    mDataList.get(i).setIsDefault(0);
                }
                mDataList.get(position).setIsDefault(1);
                notifyDataSetChanged();
            }
        });*/

      /*  if (mDataList != null && mDataList.size() > 0) {
            Log.e("zdp", mDataList.get(position).getPatientName());
            holder.patientName.setText(mDataList.get(position).getPatientName());
            int sexType = mDataList.get(position).getPatientSex();
            if (sexType == 1) {
                holder.sex.setText("男");
            } else if (sexType == 2) {
                holder.sex.setText("女");
            } else if (sexType == 3) {
                holder.sex.setText("未确定");
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.getReturnData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        @BindView(R.id.chick_unit)
        TextView chickUnit;
        @BindView(R.id.select_box)
        CheckBox selectBox;
        @BindView(R.id.item)
        RelativeLayout item;
        MyViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                boolean isSelected = !mDataList.getReturnData().get(getPosition()).isSelected();
                if (isSelected) {
                    mHasSelectedDieaseList.add( mDataList.getReturnData().get(getPosition()));
                }
                else {
                    mHasSelectedDieaseList.remove( mDataList.getReturnData().get(getPosition()));
                }
                mDataList.getReturnData().get(getPosition()).setSelected(isSelected );
                mListener.onItemClick(v, getPosition());
            }

        }

    }

    public void setDatalist(QueryDiseaseEntity dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }
    public List<QueryDiseaseEntity.ReturnDataBean> getDatalist() {
        return  mHasSelectedDieaseList;
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }


}
