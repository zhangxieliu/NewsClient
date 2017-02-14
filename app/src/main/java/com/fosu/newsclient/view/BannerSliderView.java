package com.fosu.newsclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.fosu.newsclient.R;
import com.loopj.android.image.SmartImageView;

/**
 * Created by Administrator on 2016/10/24.
 */

public class BannerSliderView extends BaseSliderView {
    public BannerSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        SmartImageView target = (SmartImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        target.setImageUrl(getUrl());
        bindEventAndShow(v, target);
        return v;
    }
}
