package com.stella.pals.views.message_groups;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.jobs.MessageGroupsJob;
import com.stella.pals.models.MessageGroup;
import com.stella.pals.utils.DateTimeUtil;
import com.stella.pals.views.adapter.MessageGroupAdapter;
import com.stella.pals.views.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;

@EFragment(R.layout.fragment_message_groups)
public class MessageGroupsFragment extends BaseFragment {

    @ViewById(R.id.lv_message_groups)
    ListView lvMessageGroups;
    @ViewById(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    MessageGroupAdapter adapter;

    @AfterViews
    protected void initAdapter() {
        RealmResults<MessageGroup> messageGroups = realm.where(MessageGroup.class).findAllSorted("date", Sort.DESCENDING);
        adapter = new MessageGroupAdapter(getContext(), messageGroups, true);
        lvMessageGroups.setAdapter(adapter);

        long lastFullMessageLoad = myPrefs.lastFullMessageGroupLoad().getOr(0L);
        Date now = new Date();
        if (new Date(lastFullMessageLoad + DateTimeUtil.MILLISECONDS_DAY).before(now)) {
            myPrefs.lastFullMessageGroupLoad().put(now.getTime());
            application.getNetworkJobManager().addJob(new MessageGroupsJob(true));
        } else {
            application.getNetworkJobManager().addJob(new MessageGroupsJob(1));
        }
    }

    @AfterViews
    protected void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        lvMessageGroups.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
//                    Global.updateMessageGroups(Global.lastPage, adapter);
//                    application.getNetworkJobManager().addJob(new MessageGroupsJob(2));
                }
            }
        });
    }

    @ItemClick
    void lvMessageGroupItemClicked(MessageGroup messageGroup) {
//                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
//                intent.putExtra("thread_id", Global.messageGroups.get(position).getUser().getId());
//                ActivityCompat.startActivity(MainActivity.this, intent, null);
    }

}
