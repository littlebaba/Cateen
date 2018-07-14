package com.liheng.cateen.module.main;

import android.content.Context;

import com.liheng.cateen.model.ImageResult;
import com.liheng.cateen.net.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Li
 * 2018/6/15.
 */

public class MainPresenter implements MainContract.IMainPresenter {

    private static Logger logger = Logger.getLogger(MainPresenter.class.getName());

    Subscription mSubscription;
    MainContract.IMainView mMainView;


    public MainPresenter(MainContract.IMainView mainView){
        this.mMainView = mainView;
    }

    @Override
    public void getBannerData() {
        mSubscription = Network.getGankApi().getImageData("福利", 5, 5)
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
                        if (imageResult != null && imageResult.results != null && imageResult.results.size() > 0){
                            List<String> imgUrls = new ArrayList<>();
                            for (ImageResult.ResultBean resultBean : imageResult.results) {
                                if (!resultBean.url.isEmpty()){
                                    imgUrls.add(resultBean.url);
                                }
                            }
                            mMainView.setBanner(imgUrls);
                        }
                    }
                });
    }


    @Override
    public void subcribe() {
        getBannerData();
    }

    @Override
    public void unSubcribe() {

    }
}
