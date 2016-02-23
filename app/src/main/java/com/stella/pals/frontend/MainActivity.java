package com.stella.pals.frontend;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.stella.pals.R;
import com.stella.pals.frontend.adapter.MainViewPagerAdapter;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.base.BaseApplication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @App
    BaseApplication application;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;
    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @AfterViews
    protected void initViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        initTabLayout();
    }

    protected void initTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.female_default);
        tabLayout.getTabAt(1).setIcon(R.drawable.female_default);
        tabLayout.getTabAt(2).setIcon(R.drawable.female_default);
        tabLayout.getTabAt(3).setIcon(R.drawable.female_default);
        tabLayout.getTabAt(4).setIcon(R.drawable.female_default);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Subscribe
//    public void onSuccessEvent(SuccessEvent event) {
//        if (event.code == JobCodes.MESSAGE_GROUP) {
////            adapter.notifyDataSetChanged();
//            dismissProgressOverlay();
//        }
//    }
//
//    @Subscribe
//    public void onFailedEvent(FailedEvent event) {
//        if (event.code == JobCodes.MESSAGE_GROUP) {
//            dismissProgressOverlay();
//        }
//    }
}
