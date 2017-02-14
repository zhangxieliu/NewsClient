package com.fosu.newsclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES10;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/10/17.
 */

public class Tools {
    private static Retrofit retrofit;
    public static Map<String, String> params = new HashMap<>();

    static {
        params.put("showapi_appid", Commons.SHOW_API_APPID);
        params.put("showapi_sign", Commons.SHOW_API_SECRET);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Commons.SHOW_API_URL)
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Bitmap ImageCropWithRect(Bitmap bitmap, boolean isRecycleed) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, wh, null, true);
        if (isRecycleed && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }

    public static Bitmap getCropImageView(Bitmap bitmap, float viewWidth) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        float scaleX = viewWidth / w;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleX);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, wh, matrix, true);
        return bmp;
    }

    public static Bitmap getScaleImageView(Bitmap bitmap, float viewWidth) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        float scaleX = viewWidth / w;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleX);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return bmp;
    }
}
