package com.liheng.cateen.module.main;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.liheng.cateen.R;
import com.liheng.cateen.base.BaseActivity;
import com.liheng.cateen.config.GlobalConfig;
import com.liheng.cateen.module.fragment.CategoryFragment;
import com.liheng.cateen.module.main.adapter.CommonFragmentPagerAdapter;
import com.liheng.cateen.net.GlideImageLoader;
import com.liheng.cateen.utils.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.IMainView{

    @BindView(R.id.main_vp)
    ViewPager mViewPager;
    @BindView(R.id.main_banner)
    Banner mBanner;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_tab)
    DachshundTabLayout mDachshundTabLayout;

    MainContract.IMainPresenter mMainPresenter;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void intView(Bundle savedInstanceState) {
        mMainPresenter = new MainPresenter(this);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        String[] title = GlobalConfig.getCategoryNames();

        CommonFragmentPagerAdapter pagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(),title);

        CategoryFragment appFragment = CategoryFragment.newInstance(title[0]);
        CategoryFragment androidFragment = CategoryFragment.newInstance(title[1]);
        CategoryFragment iOSFragment = CategoryFragment.newInstance(title[2]);
        CategoryFragment frontEndFragment = CategoryFragment.newInstance(title[3]);
        CategoryFragment recommendFragment = CategoryFragment.newInstance(title[4]);
        CategoryFragment resourceFragment = CategoryFragment.newInstance(title[5]);

        pagerAdapter.addItem(appFragment);
        pagerAdapter.addItem(androidFragment);
        pagerAdapter.addItem(iOSFragment);
        pagerAdapter.addItem(frontEndFragment);
        pagerAdapter.addItem(recommendFragment);
        pagerAdapter.addItem(resourceFragment);

        mViewPager.setAdapter(pagerAdapter);
        mDachshundTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(6);

        mMainPresenter.subcribe();
    }


    @Override
    public void setBanner(List<String> imgUrls) {
        mBanner.setImages(imgUrls).setImageLoader(new GlideImageLoader()).start();
    }
}
