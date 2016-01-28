package com.stella.pals.backend;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by DJ on 5/12/15.
 * Project: Stella Pals
 */
@Database(name = PalsDatabase.NAME, version = PalsDatabase.VERSION, foreignKeysSupported = true)
public class PalsDatabase {

    public static final String NAME = "Pals";
    public static final int VERSION = 1;
}
