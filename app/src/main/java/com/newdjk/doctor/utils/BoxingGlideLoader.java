package com.newdjk.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.newdjk.doctor.R;

/**
 * Created by EDZ on 2018/10/16.
 */

public class BoxingGlideLoader implements IBoxingMediaLoader {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
           // Glide.with(img.getContext()).load(path).placeholder(R.mipmap.ic_default_image).crossFade().centerCrop().into(img);
            GlideUtils.loadCommonmage(path,img,R.mipmap.ic_default_image);
        } catch (IllegalArgumentException ignore) {
        }

    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, int width, int height, final IBoxingCallback callback) {
        String path = "file://" + absPath;
//        Glide.with(img.getContext())
//                .load(path)
//
//                .listener(new RequestListener<String, Bitmap>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                        if (callback != null) {
//                            callback.onFail(e);
//                            return true;
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (resource != null && callback != null) {
//                            img.setImageBitmap(resource);
//                            callback.onSuccess();
//                            return true;
//                        }
//                        return false;
//                    }
//                })
//                .into(img);

        Glide.with(img.getContext())
               .load(path).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (callback != null) {
                            callback.onFail(e);
                            return true;
                        }
                        return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource != null && callback != null) {
                            img.setImageDrawable(resource);
                            callback.onSuccess();
                            return true;
                        }
                        return false;
            }
        }).into(img);
    }


}
