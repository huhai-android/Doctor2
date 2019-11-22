package com.newdjk.doctor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.views.LoadDialog;
import com.youth.banner.loader.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureSlideFragment extends Fragment {
    private String url;
    private PhotoView imageView;
    private ImageLoader loader;     // 获取URL

    public static PictureSlideFragment newInstance(String url) {
        PictureSlideFragment f = new PictureSlideFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        /*加载视图*/
        View v = inflater.inflate(R.layout.fragment_picture_slide, container, false);
        imageView = v.findViewById(R.id.photo_view_pic);         /*加载图片*/
        LoadDialog.show(getContext());
        Glide.with(MyApplication.getContext())
                .load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LoadDialog.clear();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                LoadDialog.clear();
                return false;
            }
        })

                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        /*loader = ImageLoader.getInstance();
        loader.displayImage(url, imageView);*/

        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                            @Override
                                            public void onPhotoTap(View view, float x, float y) {
                                                getActivity().finish();
                                            }

                                            @Override
                                            public void onOutsidePhotoTap() {

                                            }
                                        }
        );
        return v;
    }

}
