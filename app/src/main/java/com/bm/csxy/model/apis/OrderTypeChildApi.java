package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderListBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface OrderTypeChildApi {

    @POST(Urls.GET_ORDER_LIST)
    Observable<BaseData<TravelOrderListBean>> getOrderList(@Query("userid") String userid,
                                                           @Query("page") int page,
                                                           @Query("pagesize") int pagesize,
                                                           @Query("orderstatus") String orderstatus);

    @POST(Urls.DELETE_ORDER)
    Observable<BaseData> deleteOrder(@Query("id") String id);
}
