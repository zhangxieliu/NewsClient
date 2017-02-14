package com.fosu.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.JokeEntity;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/16.
 */

public class JokeListAdapter extends BaseAdapter {
    private Context context;
    private List<JokeEntity> jokeEntities;

    public JokeListAdapter(Context context, List<JokeEntity> jokeEntities) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout__lv_item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JokeEntity jokeEntity = jokeEntities.get(position);
        holder.image.setImageUrl(jokeEntity.getProfileImage());
        holder.title.setText(jokeEntity.getText().replace("\n", "").replace(" ", ""));
        holder.realType.setText(jokeEntity.getName());
        holder.type.setText(jokeEntity.getCreateTime());
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
