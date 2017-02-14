package com.fosu.newsclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.WeiXinEntity;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/19.
 */

public class WeiXinAdapter extends BaseAdapter {
    private Context context;
    private List<WeiXinEntity.Content> contents;

    public WeiXinAdapter(Context context, List<WeiXinEntity.Content> contents) {
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
            convertView = View.inflate(context, R.layout.layout_list_item_weixin, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WeiXinEntity.Content content = contents.get(position);
        holder.image.setImageUrl(content.getFirstImg());
        holder.title.setText(content.getTitle());
        holder.realType.setText(content.getSource());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.real_type)
        TextView realType;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.image)
        SmartImageView image;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
