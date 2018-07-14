package com.liheng.cateen.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Li
 * 2018/6/17.
 */

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter{

    private String[] titles;
    private List<Fragment> fragments=new ArrayList<>();

    public CommonFragmentPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.titles = title;
    }


    public void addItem(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
