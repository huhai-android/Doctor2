package com.newdjk.doctor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicFragment;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.GuideActivity;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.PrivacyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zmf on 2018/5/1.
 */

public class GuideFragment extends BasicFragment {
    @BindView(R.id.iv_guide)
    ImageView ivGuide;
    @BindView(R.id.lay_bottom)
    LinearLayout layBottom;
    Unbinder unbinder;
    private int position = 0;

    @Override
    protected int initViewResId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView() {
        if (position == 0) {
            ivGuide.setImageResource(R.drawable.guide1);
        } else if (position == 1) {
            ivGuide.setImageResource(R.drawable.guide2);
        } else if (position == 2) {
            ivGuide.setImageResource(R.drawable.guide3);
        } else if (position == 3) {
            ivGuide.setImageResource(R.drawable.guide4);
        } else if (position == 4) {
            ivGuide.setImageResource(R.drawable.guide5);
        } else if (position == 5) {
            ivGuide.setImageResource(R.drawable.guide2);
        }

    }

    @Override
    protected void initListener() {
        if (position == 4) {
            ivGuide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 boolean isagree=   SpUtils.getBoolean(Contants.IS_HAS_AGREE, false);
                 if (isagree){
                     SpUtils.put(Contants.IS_FIRST_USE, false);
                     Intent i = new Intent(getContext(), LoginActivity.class);
                     startActivity(i);
                     getActivity().finish();
                 }else {

                     PrivacyDialog mDialog = new PrivacyDialog(getContext());
                     mDialog.show("", "", new PrivacyDialog.DialogListener() {
                         @Override
                         public void confirm() {
                             //跳转登录
                             SpUtils.put(Contants.IS_FIRST_USE, false);
                             SpUtils.put(Contants.IS_HAS_AGREE, true);
                             Intent i = new Intent(getContext(), LoginActivity.class);
                             startActivity(i);
                             getActivity().finish();

                         }

                         @Override
                         public void cancel() {
                             SpUtils.put(Contants.IS_HAS_AGREE, false);
                         }
                     });

                 }

                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

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

    public void setdata(int position) {
        this.position = position;

    }
//    private static final int LOADING_SUCCESS = 2;
//
//    public static GuideFragment getFgt(int index) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("index", index);
//        GuideFragment fragment = new GuideFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @Override
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case LOADING_SUCCESS:
//                    PrivacyDialog mDialog = new PrivacyDialog(getContext());
//                    mDialog.show("", "", new PrivacyDialog.DialogListener() {
//                        @Override
//                        public void confirm() {
//                            //跳转隐私政策
//                        }
//
//                        @Override
//                        public void cancel() {
//
//                        }
//                    });
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_guide, null);
//        int index = 1;
//        /**
//         * 背景色
//         */
//        ImageView imageView = view.findViewById(R.id.iv_guide);
//        View mLogin = view.findViewById(R.id.lay_bottom);
//        index = getArguments().getInt("index", 1);
//        if (index == 1) {
//            mLogin.setVisibility(View.INVISIBLE);
//            imageView.setImageResource(R.drawable.guide1);
//        } else if (index == 2) {
//            mLogin.setVisibility(View.INVISIBLE);
//            imageView.setImageResource(R.drawable.guide2);
//        } else if (index == 3) {
//            mLogin.setVisibility(View.INVISIBLE);
//            imageView.setImageResource(R.drawable.guide3);
//        } else if (index == 4) {
//            mLogin.setVisibility(View.INVISIBLE);
//            imageView.setImageResource(R.drawable.guide4);
//        } else if (index == 5) {
//            //当显示第五章图的时候需要弹窗
//            mLogin.setVisibility(View.VISIBLE);
//            imageView.setImageResource(R.drawable.guide5);
//            mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 1000);
//        }
//        /**
//         * 登录
//         */
//        final int finalIndex = index;
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (finalIndex == 5) {
//                    GuideActivity activity = (GuideActivity) getActivity();
//                    activity.login();
//                }
//
//            }
//        });
//
//        return view;
//    }
//
//
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        int index = getArguments().getInt("index", 1);
//    }
}
