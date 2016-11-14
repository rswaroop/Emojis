package com.swaroop.emojis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.swaroop.emojis.R;
import com.swaroop.emojis.model.Emoji;
import com.swaroop.emojis.utils.PicassoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class EmojiViewAdapter extends ArrayAdapter<Emoji> {
    private final Logger mLog = LoggerFactory.getLogger(getClass());
    private Context mContext;
    private int mLayoutResourceId;
    private ArrayList<Emoji> mEmojis;

    public EmojiViewAdapter(Context context, int resource, List<Emoji> emojis) {
        super(context, resource, emojis);
        mLog.trace("EmojiViewAdapter({})", context, resource, emojis);
        mContext = context;
        mLayoutResourceId = resource;
        mEmojis = new ArrayList<>(emojis);
    }

    public void setData(final ArrayList<Emoji> emojis) {
        mLog.trace("setData({})", emojis);
        mEmojis = new ArrayList<>(emojis);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.emoji);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (mEmojis.size() > 0) {
            PicassoFactory.getPicassoInstance(mContext).load(mEmojis.get(position).imageUrl).into(holder.imageView);
        }

        return view;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
