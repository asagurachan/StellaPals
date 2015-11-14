package com.stella.pals.frontend.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.utils.ImageUtil;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class MessageGroupAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    public MessageGroupAdapter(Context context) {
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
            viewHolder.mIvThumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
            viewHolder.mTvUsername = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.mTvSneakMessage = (TextView) convertView.findViewById(R.id.tv_sneak_message);
            viewHolder.position = -1;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.position != position) {
            MessageGroup messageGroup = (MessageGroup) getItem(position);


            viewHolder.mTvUsername.setText(messageGroup.getUser().getUsername());
            viewHolder.mTvSneakMessage.setText(messageGroup.getSneakMessage());

            User user = messageGroup.getUser();

            if (user.getSex() == User.FEMALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(BaseApplication.getAppContext(), R.color.purple));
                Global.IMAGE_LOADER.displayImage(messageGroup.getUser().getThumb(), viewHolder.mIvThumb, ImageUtil.displayFemalePhotoOptions);
            } else if (user.getSex() == User.MALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(BaseApplication.getAppContext(), R.color.blue));
                Global.IMAGE_LOADER.displayImage(user.getThumb(), viewHolder.mIvThumb, ImageUtil.displayMalePhotoOptions);
            }

            viewHolder.position = position;
        }

        return convertView;
    }

    class ViewHolder {
        int position;
        TextView mTvUsername, mTvSneakMessage;
        ImageView mIvThumb;
    }
}
