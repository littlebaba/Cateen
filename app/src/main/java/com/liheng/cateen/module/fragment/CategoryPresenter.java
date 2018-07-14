package com.liheng.cateen.module.fragment;

import com.liheng.cateen.model.ImageResult;
import com.liheng.cateen.net.Network;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Li
 * 2018/6/20.
 */

public class CategoryPresenter implements CategoryContract.ICategoryPresenter {


    CategoryContract.ICategoryView mCategoryView;
    Subscription mSubscription;
    private int mPage = 1;

    public CategoryPresenter(CategoryContract.ICategoryView mCategoryView) {
        this.mCategoryView = mCategoryView;
    }

    @Override
    public void subcribe() {
        getCategoryItems(true);
    }

    @Override
    public void unSubcribe() {

    }

    @Override
    public void getCategoryItems(final boolean isRefresh) {

        if (isRefresh){
            mPage=1;
            mCategoryView.showSwipeLoading();
        }else {
            mPage++;
        }

        mSubscription = Network.getGankApi().getImageData(mCategoryView.getCategoryName(), 10,mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ImageResult imageResult) {
                        if (!imageResult.error){
                            if (imageResult.results !=null && imageResult.results.size()>0){
                                if (isRefresh){
                                    mCategoryView.setCategoryItems(imageResult.results);
                                    mCategoryView.hideSwipeLoading();
                                    mCategoryView.setLoading();

                                }else {
                                    mCategoryView.addCategoryItems(imageResult.results);
                                }
                                if (imageResult.results.size() < 10){
                                    mCategoryView.setNoMore();
                                }
                            }else {
                                //数据为空

                            }
                        }else {
                            //获取数据失败
                        }
                    }
                });
    }
}
