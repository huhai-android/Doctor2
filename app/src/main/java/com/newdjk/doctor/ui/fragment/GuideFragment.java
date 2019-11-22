package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.activity.GuideActivity;


/**
 * Created by zmf on 2018/5/1.
 */

public class GuideFragment extends Fragment {

    public static GuideFragment getFgt(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, null);
       int index = 1;
        /**
         * 背景色
         */
        ImageView imageView = view.findViewById(R.id.iv_guide);
        View mLogin = view.findViewById(R.id.lay_bottom);
        index =  getArguments().getInt("index" ,1);
        if(index == 1){
            mLogin.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.guide1);
        }else if(index == 2){
            mLogin.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.guide2);
        }else if(index == 3){
            mLogin.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.guide3);
        } else if(index == 4){
            mLogin.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.guide4);
        }else if(index == 5){
            mLogin.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.guide5);
        }
        /**
         * 登录
         */
        final int finalIndex = index;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalIndex ==5){
                    GuideActivity activity = (GuideActivity)getActivity();
                    activity.login();
                }

            }
        });
        return view;
    }
}
