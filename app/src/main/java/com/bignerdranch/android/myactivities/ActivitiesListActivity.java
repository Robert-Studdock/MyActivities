package com.bignerdranch.android.myactivities;

import android.support.v4.app.Fragment;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivitiesListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ActivitiesListFragment();
    }
}
