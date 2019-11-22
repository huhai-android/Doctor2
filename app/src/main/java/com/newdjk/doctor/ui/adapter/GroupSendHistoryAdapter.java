package com.newdjk.doctor.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.GroupSendEntity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class GroupSendHistoryAdapter extends BaseQuickAdapter<GroupSendEntity.DataBean.ReturnDataBean,BaseViewHolder> {
    public GroupSendHistoryAdapter(@Nullable List<GroupSendEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.group_send_history,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupSendEntity.DataBean.ReturnDataBean item) {

        SpannableString spannableString = new SpannableString(item.getAllPeople()+mContext.getResources().getString(R.string.person_size));
        String allPeople = String.valueOf(item.getAllPeople());
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffa417")), 0, allPeople.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.mTime,item.getCreateTime());
        helper.setText(R.id.mPesonSize,item.getAllPeople()+mContext.getResources().getString(R.string.person_size));
        helper.setText(R.id.mSendContent,item.getContent());
        helper.setText(R.id.mSendPerson,item.getPatientText());
        helper.addOnClickListener(R.id.mSendBtn);
    }
}
