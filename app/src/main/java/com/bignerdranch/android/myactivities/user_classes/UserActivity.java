package com.bignerdranch.android.myactivities.user_classes;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.myactivities.SingleFragmentActivity;

/**
 * Created by Robert on 14/09/2017.
 */

public class UserActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new UserFragment();
    }
}
