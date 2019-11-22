package com.newdjk.doctor.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newdjk.doctor.R;

public class ShareViewPopwin extends PopupWindow implements View.OnClickListener {
    private View view;
    private TextView mCancel;
    public ShareViewPopwin(Context context) {
        super(context);
        this.view = LayoutInflater.from(context).inflate(R.layout.share_view_popupwin, null);

        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        this.setContentView(this.view);
        mCancel = view.findViewById(R.id.cancel);
        //  this.setHeight(600);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.popupwindow_animation);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel :
                dismiss();
                break;
        }
    }
}
