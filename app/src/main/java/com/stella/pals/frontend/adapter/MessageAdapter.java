package com.stella.pals.frontend.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.backend.model.Message;

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

    public MessageAdapter(Context context, ArrayList<Message> messages) {
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
                convertView = mLayoutInflater.inflate(R.layout.item_message, parent, false);
                viewHolder.mTvMessage = (TextView) convertView.findViewById(R.id.tv_message);

                convertView.setTag(viewHolder);
            } else {
                convertView = mLayoutInflater.inflate(R.layout.item_message, parent, false);
                viewHolder.mTvMessage = (TextView) convertView.findViewById(R.id.tv_message);

                convertView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = (Message) getItem(position);

        viewHolder.mTvMessage.setText(Html.fromHtml(message.getMessage()));

        return convertView;
    }

    class ViewHolder {
        TextView mTvMessage;
    }
}
