package com.stella.pals.interfaces;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Asa on 2016/02/20.
 * StellaPals
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface MyPrefs {

    String lastUser();

}
