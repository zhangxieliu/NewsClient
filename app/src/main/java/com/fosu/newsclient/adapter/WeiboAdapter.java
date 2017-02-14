package com.fosu.newsclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.WeiboHot;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WeiboAdapter extends BaseAdapter {
    private Context context;
    private List<WeiboHot> contents;

    public WeiboAdapter(Context context, List<WeiboHot> contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_healthy_lv_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WeiboHot weiboHot = contents.get(position);
        Log.i("TAG", "getView: " + weiboHot.getNewinfo());
        holder.title.setText(weiboHot.getDesc());
        holder.image.setImageUrl(weiboHot.getImg());
        holder.content.setText(weiboHot.getNewinfo());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.image)
        SmartImageView image;
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
