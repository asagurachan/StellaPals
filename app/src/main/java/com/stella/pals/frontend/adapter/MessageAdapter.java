package com.stella.pals.frontend.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.backend.model.Message;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.utils.ImageUtil;

import java.util.ArrayList;

/**
 * Created by DJ on 13/11/15.
 * Project: Stella Pals
 */
public class MessageAdapter extends BaseAdapter {

    private static final int OWN_MESSAGE = 0;
    private static final int OTHER_MESSAGE = 1;

    private ArrayList<Message> mMessages;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((Message) getItem(position)).isOwnMessage() ? OWN_MESSAGE : OTHER_MESSAGE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            if (getItemViewType(position) == OWN_MESSAGE) {
                convertView = mLayoutInflater.inflate(R.layout.item_message_right, parent, false);
            } else {
                convertView = mLayoutInflater.inflate(R.layout.item_message_left, parent, false);
            }

            viewHolder.mParent = (RelativeLayout) convertView.findViewById(R.id.parent);
            viewHolder.mTvMessage = (TextView) convertView.findViewById(R.id.tv_message);
            viewHolder.mIvProfile = (ImageView) convertView.findViewById(R.id.iv_profile);
            viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.tv_time);

            viewHolder.mTvMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Global.randomTest = true;
                    } else {
                        Global.randomTest = false;
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = (Message) getItem(position);

        if (message.isUnread()) {
            viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primary));
        } else {
            viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        }

        viewHolder.mTvTime.setText(message.getTime());
        viewHolder.mTvMessage.setText(Html.fromHtml(message.getMessage()));
        Global.IMAGE_LOADER.displayImage(message.getProfile(), viewHolder.mIvProfile, ImageUtil.displayFemalePhotoOptions);

        return convertView;
    }

    class ViewHolder {
        RelativeLayout mParent;
        ImageView mIvProfile;
        TextView mTvMessage, mTvTime;
    }
}
