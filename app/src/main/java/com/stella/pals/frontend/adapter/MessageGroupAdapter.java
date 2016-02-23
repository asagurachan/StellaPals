package com.stella.pals.frontend.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.stella.pals.R;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.base.BaseApplication_;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class MessageGroupAdapter extends RealmBaseAdapter<MessageGroup> implements ListAdapter {

    public MessageGroupAdapter(Context context, RealmResults<MessageGroup> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public int getCount() {
        return realmResults.size();
    }

    @Override
    public MessageGroup getItem(int position) {
        return realmResults.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_message_group, parent, false);
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

//        if (viewHolder.position != position) {
            MessageGroup messageGroup = getItem(position);

            if (messageGroup.isNewMessage()) {
                viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(context, R.color.primary));
            } else {
                viewHolder.mParent.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }

            viewHolder.mTvUsername.setText(messageGroup.getUser().getUsername());
            viewHolder.mTvSneakMessage.setText(messageGroup.getSneakMessage());
            viewHolder.mTvTime.setText(messageGroup.getTime());

            User user = messageGroup.getUser();

            if (user.getSex() == User.FEMALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(context, R.color.purple));
//                Global.IMAGE_LOADER.displayImage(messageGroup.getUser().getThumb(), viewHolder.mIvThumb, ImageUtil.displayFemalePhotoOptions);
                Glide.with(BaseApplication_.getInstance()).load(messageGroup.getUser().getThumb()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("TAG", e.getMessage(), e);
                        Log.e("TAG", model);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(viewHolder.mIvThumb);
            } else if (user.getSex() == User.MALE) {
                viewHolder.mTvUsername.setTextColor(ContextCompat.getColor(context, R.color.blue));
//                Global.IMAGE_LOADER.displayImage(user.getThumb(), viewHolder.mIvThumb, ImageUtil.displayMalePhotoOptions);
                Glide.with(context).load(messageGroup.getUser().getThumb()).into(viewHolder.mIvThumb);
            }

            viewHolder.position = position;
//        }

        return convertView;
    }

    class ViewHolder {
        int position;
        RelativeLayout mParent;
        TextView mTvUsername, mTvSneakMessage, mTvTime;
        ImageView mIvThumb;
    }
}
