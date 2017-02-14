package com.fosu.newsclient.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fosu.newsclient.R;
import com.fosu.newsclient.activity.ImageViewActivity;
import com.fosu.newsclient.bean.JokeEntity;
import com.fosu.newsclient.utils.ImageUtils;
import com.fosu.newsclient.utils.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Administrator on 2016/10/24.
 */

public class JokeAdapter extends BaseAdapter {
    private Context context;
    private List<JokeEntity> jokeEntities;
    private AlertDialog dialog;
    private View view;
    private VideoView video;
    private ImageView imageControl;

    public JokeAdapter(Context context, List<JokeEntity> jokeEntities) {
        this.context = context;
        this.jokeEntities = jokeEntities;
    }

    @Override
    public int getCount() {
        return jokeEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return jokeEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_lv_item_joke, null);
            holder = new ViewHolder(convertView);
            holder.videoImage.setDrawingCacheEnabled(false);
            convertView.setTag(holder);
            view = View.inflate(context, R.layout.layout_video_dialog, null);
            video = (VideoView) view.findViewById(R.id.video_view);
            imageControl = (ImageView) view.findViewById(R.id.image);
            imageControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (video.isPlaying())
                        video.pause();
                    else
                        video.start();
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final JokeEntity jokeEntity = jokeEntities.get(position);
        holder.profileImage.setImageURI(jokeEntity.getProfileImage());
        holder.name.setText(jokeEntity.getName());
        holder.createTime.setText(jokeEntity.getCreateTime());
        holder.text.setText(jokeEntity.getText().replace("\n", "").replace(" ", ""));
        if (!TextUtils.isEmpty(jokeEntity.getImage3())) {
            holder.image.setVisibility(View.VISIBLE);
            if (jokeEntity.getImage3().endsWith(".gif")) {
                Glide.with(context)
                        .load(jokeEntity.getImage3())
                        .asGif()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //跳过硬盘缓存
                        .placeholder(R.drawable.loading)
                        .into(holder.image);
                holder.loadingIndicator.hide();
            } else {
                holder.loadingIndicator.show();
                Glide.with(context)
                        .load(jokeEntity.getImage3())
                        .asBitmap()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Bitmap bitmap = Tools.getCropImageView(resource, holder.image.getWidth());
                                holder.image.setImageBitmap(bitmap);
                                holder.loadingIndicator.hide();
                            }
                        });
            }
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra("image_url", jokeEntity.getImage3());
                    context.startActivity(intent);
                }
            });
        } else if (!TextUtils.isEmpty(jokeEntity.getVideoUri())) {
            Observable
                    .create(new Observable.OnSubscribe<Bitmap>() {
                        @Override
                        public void call(Subscriber<? super Bitmap> subscriber) {
                            FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
                            mmr.setDataSource(jokeEntity.getVideoUri());
                            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
                            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
                            Bitmap bitmap = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
                            subscriber.onNext(bitmap);
                            mmr.release();
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Bitmap>() {
                        @Override
                        public void call(final Bitmap bitmap) {
                            holder.videoImage.setTag(jokeEntity.getVideoUri());
                            holder.videoImage.setImageBitmap(bitmap);
                            holder.videoImage.setVisibility(View.VISIBLE);
                            holder.videoImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    video.setBackground(new BitmapDrawable(bitmap));
                                    playVideo(jokeEntity.getVideoUri());
                                }
                            });
                        }
                    });
        }
        holder.loadingIndicator.hide();
        return convertView;
    }

    private AlertDialog createDialog() {
        video.requestFocus();
        video.setZOrderOnTop(true);
        video.setMediaController(new MediaController(context));
        AlertDialog dlg = new AlertDialog.Builder(context)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        video.stopPlayback();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.setView(view);
        return dlg;
    }

    private void playVideo(String url) {
        if (dialog == null) {
            dialog = createDialog();
        }
        dialog.show();
        video.setVideoPath(url);
    }

    static class ViewHolder {
        @BindView(R.id.profile_image)
        SimpleDraweeView profileImage;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.create_time)
        TextView createTime;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.video_image)
        ImageView videoImage;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.loading_indicator)
        AVLoadingIndicatorView loadingIndicator;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
