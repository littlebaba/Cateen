package com.liheng.cateen.net.Api;

import com.liheng.cateen.model.ImageResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Li
 * 2018/6/15.
 */

public interface GankApi {

    @GET("data/{category}/{count}/{page}")
    Observable<ImageResult> getImageData(
            @Path("category") String category,
            @Path("count") int count,
            @Path("page") int page);

}
