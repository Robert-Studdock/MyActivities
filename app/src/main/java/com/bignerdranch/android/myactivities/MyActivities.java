package com.bignerdranch.android.myactivities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

public class MyActivities extends SingleFragmentActivity {

    private static final String EXTRA_ACTIVITY_ID =
            "com.bignerdranh.myactivities.activity_id";

    public static Intent newIntent(Context packageContext, UUID activityId) {
        Intent intent = new Intent(packageContext, MyActivities.class);
        intent.putExtra(EXTRA_ACTIVITY_ID, activityId);
        return intent;
    }

    @Override
    protected  Fragment createFragment() {
        UUID activityId = (UUID) getIntent().getSerializableExtra(EXTRA_ACTIVITY_ID);
        return ActivityFragment.newInstance(activityId);
    }
}
