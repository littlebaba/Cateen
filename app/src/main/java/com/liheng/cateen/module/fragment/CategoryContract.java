package com.liheng.cateen.module.fragment;

import com.liheng.cateen.base.BasePresenter;
import com.liheng.cateen.model.ImageResult;

import java.util.List;

/**
 * Li
 * 2018/6/20.
 */

public interface CategoryContract {
    interface ICategoryView{
        void addCategoryItems(List<ImageResult.ResultBean> data);
        void setCategoryItems(List<ImageResult.ResultBean> data);
        String getCategoryName();

        void showSwipeLoading();
        void hideSwipeLoading();
        void setLoading();

        void setNoMore();
    }

    interface ICategoryPresenter extends BasePresenter{
        void getCategoryItems(boolean isRefresh);
    }
}
