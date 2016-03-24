package com.stella.pals.views.base;

import android.support.v4.app.Fragment;

import com.stella.pals.interfaces.MyPrefs_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import io.realm.Realm;

/**
 * Created by Asa on 2016/03/16.
 * StellaPals
 */
@EFragment
public class BaseFragment extends Fragment {

    @App
    protected BaseApplication application;

    @Pref
    protected MyPrefs_ myPrefs;

    protected Realm realm;

    @AfterInject
    public void initRealm() {
        realm = Realm.getDefaultInstance();
    }

}
