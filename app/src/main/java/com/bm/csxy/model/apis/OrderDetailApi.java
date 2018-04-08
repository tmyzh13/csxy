package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface OrderDetailApi {

    @POST(Urls.GET_ORDER_DETAIL)
    Observable<BaseData<TravelOrderBean>> getOrderDetail(@Query("id") String id);
}
