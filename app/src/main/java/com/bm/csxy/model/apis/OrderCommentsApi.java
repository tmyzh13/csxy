package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface OrderCommentsApi {

    @POST(Urls.COMMENTS_ORDER)
    Observable<BaseData> commentsOrder(@Query("userid") String userid,
                                       @Query("orderid") String orderid,
                                       @Query("content") String content,
                                       @Query("grade") String grade);
}
