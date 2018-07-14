package com.liheng.cateen.module.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.liheng.cateen.R;
import com.liheng.cateen.base.BaseFragment;
import com.liheng.cateen.model.ImageResult;
import com.liheng.cateen.module.fragment.adapter.CategoryRecyclerAdapter;
import com.liheng.cateen.widget.OnLoadMoreListener;
import com.liheng.cateen.widget.RecyclerViewWithFooter;

import java.util.List;

import butterknife.BindView;

/**
 * Li
 * 2018/6/17.
 */

public class CategoryFragment extends BaseFragment implements CategoryContract.ICategoryView,SwipeRefreshLayout.OnRefreshListener,OnLoadMoreListener{

    public static final String CATEGORY_NAME = "com.liheng.cateen.module.fragment.CategoryFragment.CATEGORY_NAME";

    @BindView(R.id.recyclerView)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    CategoryRecyclerAdapter mAdapter;

    private CategoryContract.ICategoryPresenter mCategoryPresenter;
    private String mCategoryName;
    public static CategoryFragment newInstance(String mCategoryName){
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_NAME,mCategoryName);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }


    @Override
    protected void initView() {
        mCategoryPresenter = new CategoryPresenter(this);
        mCategoryName = getArguments().getString(CATEGORY_NAME);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new CategoryRecyclerAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setEmpty();
        mCategoryPresenter.subcribe();
    }


    @Override
    public void addCategoryItems(List<ImageResult.ResultBean> data) {
        mAdapter.addData(data);
    }

    @Override
    public void setCategoryItems(List<ImageResult.ResultBean> data) {
        mAdapter.setData(data);
    }

    @Override
    public String getCategoryName() {
        return mCategoryName;
    }


    @Override
    public void onRefresh() {
        mCategoryPresenter.getCategoryItems(true);
    }

    @Override
    public void showSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoading() {
        mRecyclerView.setLoading();
    }

    @Override
    public void setNoMore() {
        mRecyclerView.setEnd("没有更多数据");
    }

    @Override
    public void onLoadMore() {
        mCategoryPresenter.getCategoryItems(false);
    }
}
