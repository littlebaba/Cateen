package com.liheng.cateen.module.main;

import com.liheng.cateen.base.BasePresenter;

import java.util.List;

/**
 * Li
 * 2018/6/15.
 */

public interface MainContract {

    interface IMainView {
        void setBanner(List<String> imgUrls);
    }

    interface IMainPresenter extends BasePresenter{
        void getBannerData();
    }

}
