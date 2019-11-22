package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;

import java.util.List;

public class CustomMessageAdapter extends BaseQuickAdapter< CustomMessageEntity.ContentBean, BaseViewHolder> {

    public CustomMessageAdapter(@Nullable List<CustomMessageEntity.ContentBean> data) {
        super( R.layout.list_item,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CustomMessageEntity.ContentBean item) {
        CustomMessageEntity.ContentBean.ContentElemBean contentElem = item.getContentElem();
        if (contentElem != null) {
            helper.setText(R.id.service_item_name,contentElem.getText());

            String num = contentElem.getDetail();
            if (!TextUtils.isEmpty(num)) {
                helper.setText(R.id.service_item_name,contentElem.getText()+"   "+num);
            }else {
                helper.setText(R.id.service_item_name,contentElem.getText());
            }
          //  helper.addOnClickListener(R.id.content_item);
        }
    }
    /*  public CustomMessageAdapter(Context activity,@Nullable List<CustomMessageEntity.ContentBean> dataList) {
        mActivity = activity;
        mDataList = dataList;
        super(R.layout.list_item, dataList);
        //mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.list_item, null);
            holder = new ViewHolder();

            holder.serviceItemName = (TextView) convertView.findViewById(R.id.service_item_name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 装配数据
       // Log.i("CustomMessageAdapter","num="+mDataList.get(position).getContentElem().getDetail());
        CustomMessageEntity.ContentBean  content = mDataList.get(position);
        if (content != null) {
            CustomMessageEntity.ContentBean.ContentElemBean contentElem = mDataList.get(position).getContentElem();
            if (contentElem != null) {
                holder.serviceItemName.setText(mDataList.get(position).getContentElem().getText());
                String num = mDataList.get(position).getContentElem().getDetail();
                if (num != null) {
                    holder.number.setText(num);
                }
            }
        }
       // holder.tv_item_name.setText(data.get(position).getName());
        return convertView;
    }






    public void setDatalist(List<CustomMessageEntity.ContentBean> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView serviceItemName;
        TextView number;
    }*/
}
