package com.sarkisian.gh.ui.main;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sarkisian.gh.R;
import com.sarkisian.gh.ui.base.BaseActivity;
import com.sarkisian.gh.ui.repos.RepoListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main_container, RepoListFragment.newInstance());
            transaction.commit();
        }
    }

}
