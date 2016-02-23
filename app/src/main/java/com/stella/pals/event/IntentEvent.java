package com.stella.pals.event;

import org.androidannotations.api.builder.ActivityIntentBuilder;

/**
 * Created by Asa on 2016/02/20.
 * StellaPals
 */
public class IntentEvent {

    public final ActivityIntentBuilder intentBuilder;
    public final boolean finishActivity;

    public IntentEvent(ActivityIntentBuilder intentBuilder) {
        this(intentBuilder, false);
    }

    public IntentEvent(ActivityIntentBuilder intentBuilder, boolean finishActivity) {
        this.intentBuilder = intentBuilder;
        this.finishActivity = finishActivity;
    }

}
