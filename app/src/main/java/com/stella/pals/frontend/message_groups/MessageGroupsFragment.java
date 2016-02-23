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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link groups.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link groups#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_message_groups)
public class MessageGroupsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageGroups.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageGroupsFragment newInstance(String param1, String param2) {
        MessageGroupsFragment fragment = new MessageGroupsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
