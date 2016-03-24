package com.stella.pals.interfaces;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Asa on 2016/02/20.
 * StellaPals
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface MyPrefs {

    @DefaultString("")
    String lastUser();
    @DefaultString("")
    String pass();

    @DefaultLong(0)
    long lastFullMessageGroupLoad();

}
