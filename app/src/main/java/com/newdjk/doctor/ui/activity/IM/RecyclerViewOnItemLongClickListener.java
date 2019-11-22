package com.newdjk.doctor.ui.activity.IM;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HUHAI on 2015/11/5.
 */
public interface RecyclerViewOnItemLongClickListener {

    boolean onItemLongClick(RecyclerView.ViewHolder baseViewHolder, View view, int position);

}
