package com.stella.pals.frontend.message_groups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.frontend.adapter.MessageGroupAdapter;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.job.MessageGroupsJob;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@EFragment(R.layout.fragment_message_groups)
public class MessageGroupsFragment extends Fragment {
    @App
    BaseApplication application;

    @ViewById(R.id.lv_message_groups)
    ListView lvMessageGroups;
    @ViewById(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    MessageGroupAdapter adapter;

    Realm realm;

    public MessageGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    @AfterViews
    @UiThread
    protected void initAdapter() {
        RealmResults<MessageGroup> messageGroups = realm.where(MessageGroup.class).findAllSorted("date", Sort.DESCENDING);
        adapter = new MessageGroupAdapter(getContext(), messageGroups, true);
        lvMessageGroups.setAdapter(adapter);
        application.getNetworkJobManager().addJob(new MessageGroupsJob(1));
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
        lvMessageGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
//                intent.putExtra("thread_id", Global.messageGroups.get(position).getUser().getId());
//                ActivityCompat.startActivity(MainActivity.this, intent, null);
            }
        });
    }

}
