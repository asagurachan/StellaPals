package com.stella.pals.frontend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stella.pals.frontend.message_groups.MessageGroupsFragment_;
import com.stella.pals.frontend.settings.SettingsFragment;

/**
 * Created by Asa on 2016/02/21.
 * StellaPals
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MessageGroupsFragment_();
//            case 1:
//                return ;
//            case 2:
//                return ;
//            case 3:
//                return ;
            case 4: default:
                return new SettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
