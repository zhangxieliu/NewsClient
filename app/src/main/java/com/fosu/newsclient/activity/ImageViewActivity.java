package com.fosu.newsclient.activity;

import android.graphics.Bitmap;
import android.opengl.GLES10;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fosu.newsclient.R;
import com.fosu.newsclient.utils.Tools;
import com.fosu.newsclient.utils.WindowUtils;
import com.wang.avi.AVLoadingIndicatorView;

import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        loadingIndicator.show();
        final String imageUrl = getIntent().getStringExtra("image_url");
        if (imageUrl.endsWith(".gif")) {
            Glide.with(this)
                    .load(imageUrl)
                    .asGif()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //跳过硬盘缓存
                    .placeholder(R.drawable.loading)
                    .into(image);
            loadingIndicator.hide();
        } else {
            Glide.with(this)
                    .load(imageUrl)
                    .asBitmap()
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

//                            Bitmap bitmap = Tools.getScaleImageView(resource, WindowUtils.getWindowWidth(ImageViewActivity.this) / 5);
                            image.setImageBitmap(resource);
                            loadingIndicator.hide();
                        }
                    });

//            imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                    Bitmap bitmap = Tools.getScaleImageView(loadedImage, image.getWidth());
//                    image.setImageBitmap(loadedImage);
//                    loadingIndicator.hide();
//                }
//            });
        }
    }
}
