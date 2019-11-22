package com.newdjk.doctor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;


/**
 * Created by EDZ on 2018/9/18.
 */

public class ItemView extends RelativeLayout {
    Context mContext;
    private View view;
    private RelativeLayout rlItemView;
    private TextView tvItemLeftText;
    private TextView tvItemRightText;
    private ImageView ivItemRightImage;
    private Drawable leftIcon;
    private String leftText;
    private String rightText;
    private Boolean isShowRightImg;
    private Drawable RightImage;
    private int RightTextColor;
    private int leftColor;
    private Drawable RightTextIcom;

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        this.leftIcon = ta.getDrawable(R.styleable.ItemView_leftTVTextDrawLeftImg);
        this.leftText = ta.getString(R.styleable.ItemView_leftTVText);
        this.rightText = ta.getString(R.styleable.ItemView_RightTVText);
        this.isShowRightImg = Boolean.valueOf(ta.getBoolean(R.styleable.ItemView_RightImgShow, true));
        this.RightImage = ta.getDrawable(R.styleable.ItemView_SetRightImg);
        this.RightTextColor = ta.getColor(R.styleable.ItemView_SetRightTextColor, 0);
        this.RightTextIcom = ta.getDrawable(R.styleable.ItemView_SetRightTextIcom);
        this.leftColor = ta.getColor(R.styleable.ItemView_SetLeftTextColor, 0);
        ta.recycle();
        this.initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
        this.view = inflater.inflate(R.layout.common_item_view, null);
        if(this.view != null) {
            this.rlItemView = this.view.findViewById(R.id.item_layout);
            this.tvItemLeftText = this.view.findViewById(R.id.item_left_text);
            this.ivItemRightImage = this.view.findViewById(R.id.item_right_img);
            this.tvItemRightText = this.view.findViewById(R.id.item_right_text);
            this.addView(this.view);
        }

        this.setLeftTextDrawableLeft(this.leftIcon, this.leftText);
        this.setRightText(this.rightText);
        this.setRightImageVisibility(this.isShowRightImg);
        this.setRightImage(this.RightImage);
        this.setRightTextColor(this.RightTextColor);
        this.setRightTexticon(this.RightTextIcom);
        this.setLeftTextTextColor(this.leftColor);
    }



    public TextView getTvItemLeftText() {
        return this.tvItemLeftText;
    }

    public void setTvItemLeftText(TextView tvItemLeftText) {
        this.tvItemLeftText = tvItemLeftText;
    }

    public TextView getTvItemRightText() {
        return this.tvItemRightText;
    }

    public void setTvItemRightText(TextView tvItemRightText) {
        this.tvItemRightText = tvItemRightText;
    }

    public ImageView getIvItemRightImage() {
        return this.ivItemRightImage;
    }

    public void setIvItemRightImage(ImageView ivItemRightImage) {
        this.ivItemRightImage = ivItemRightImage;
    }

    public void setLeftTextDrawableLeft(Drawable drawable, String itemString) {
        if(drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.getTvItemLeftText().setCompoundDrawables(drawable, null, null, null);
        } else {
            this.getTvItemLeftText().setCompoundDrawables(null, null, null, null);
        }

        if(itemString != null) {
            this.getTvItemLeftText().setText(itemString);
        }

    }

    public void setRightText(String itemString) {
        if(itemString != null) {
            this.getTvItemRightText().setVisibility(0);
            this.getTvItemRightText().setText(itemString);
        } else {
            this.getTvItemRightText().setText("");
        }

    }

    public void setRightImageVisibility(Boolean isShow) {
        if(isShow.booleanValue()) {
            this.getIvItemRightImage().setVisibility(VISIBLE);
        } else {
            this.getIvItemRightImage().setVisibility(INVISIBLE);
        }

    }

    public void setRightImage(Drawable drawable) {
        if(drawable != null) {
            this.getIvItemRightImage().setImageDrawable(drawable);
        }

    }

    public void setRightTextColor(int RightTextColor) {
        if(RightTextColor != 0) {
            this.getTvItemRightText().setTextColor(RightTextColor);
        }

    }

    public void setLeftTextTextColor(int RightTextColor) {
        if(RightTextColor != 0) {
            this.getTvItemLeftText().setTextColor(RightTextColor);
        }

    }

    public void setRightTexticon(Drawable drawable) {
        if(drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.getTvItemRightText().setCompoundDrawables(drawable, null, null, null);
        } else {
            this.getTvItemRightText().setCompoundDrawables(null, null, null, null);
        }

    }
}
