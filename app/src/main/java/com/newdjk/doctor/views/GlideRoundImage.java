package com.newdjk.doctor.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.views
 *  @文件名:   GlideRoundImage
 *  @创建者:   huhai
 *  @创建时间:  2019/3/12 14:49
 *  @描述：
 */
public class GlideRoundImage  extends BitmapTransformation {

//    private static float radius = 0f;
//
//    public GlideRoundImage(Context context) {
//        this(context, 10);
//    }
//
//    public GlideRoundImage(Context context, int dp) {
//        super(context);
//        //设置圆角半径
//        radius = Resources.getSystem().getDisplayMetrics().density * dp;
//    }
//
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return roundCrop(pool, toTransform);
//        //Glide 4.0.0以上则使用下面的
//        //Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
//        //return roundCrop(pool, bitmap);
//    }
//    //出去重新绘制图片按照圆角矩形边框大小进行绘制
//    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) {
//            return null;
//        }
//
//        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        return result;
//    }
//
//    @Override
//    public String getId() {
//        return getClass().getName() + Math.round(radius);
//    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return null;
    }
}
