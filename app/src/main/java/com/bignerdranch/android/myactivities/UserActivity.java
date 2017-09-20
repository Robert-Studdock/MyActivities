package com.bignerdranch.android.myactivities;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Robert on 14/09/2017.
 */

public class UserActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new UserFragment();
    }
}
