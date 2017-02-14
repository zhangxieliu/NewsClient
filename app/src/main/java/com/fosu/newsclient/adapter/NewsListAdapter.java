package com.fosu.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.NewsBean;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/16.
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsBean> newsBeens;

    public NewsListAdapter(Context context, List<NewsBean> newsBeens) {
        this.context = context;
        this.newsBeens = newsBeens;
    }

    @Override
    public int getCount() {
        return newsBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout__lv_item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsBean newsBean = newsBeens.get(position);
        holder.image.setImageUrl(newsBean.getThumbnail_pic_s());
        holder.title.setText(newsBean.getTitle());
        holder.realType.setText(newsBean.getRealtype());
        holder.type.setText(newsBean.getType());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image)
        SmartImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.real_type)
        TextView realType;
        @BindView(R.id.type)
        TextView type;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
