package com.stella.pals.frontend.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.utils.ImageUtil;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class MessageGroupAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public MessageGroupAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Global.messageGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return Global.messageGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_message_group, parent, false);
            viewHolder.mParent = (RelativeLayout) convertView.findViewById(R.id.parent);
            viewHolder.mIvThumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
            viewHolder.mTvUsername = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mTvSneakMessage = (TextView) convertView.findViewById(R.id.tv_sneak_message);
            viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.position = -1;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.position != position) {
            MessageGroup messageGroup = (MessageGroup) getItem(position);

            if (messageGroup.isNewMessage()) {
                viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primary));
            } else {
                viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }

            viewHolder.mTvUsername.setText(messageGroup.getUser().getUsername());
            viewHolder.mTvSneakMessage.setText(messageGroup.getSneakMessage());
            viewHolder.mTvTime.setText(messageGroup.getTime());

            User user = messageGroup.getUser();

            if (user.getSex() == User.FEMALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(mContext, R.color.purple));
                Global.IMAGE_LOADER.displayImage(messageGroup.getUser().getThumb(), viewHolder.mIvThumb, ImageUtil.displayFemalePhotoOptions);
            } else if (user.getSex() == User.MALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                Global.IMAGE_LOADER.displayImage(user.getThumb(), viewHolder.mIvThumb, ImageUtil.displayMalePhotoOptions);
            }

            viewHolder.position = position;
        }

        return convertView;
    }

    class ViewHolder {
        int position;
        RelativeLayout mParent;
        TextView mTvUsername, mTvSneakMessage, mTvTime;
        ImageView mIvThumb;
    }
}
