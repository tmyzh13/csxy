package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelListBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductApi {
    @POST(Urls.URL_GET_TRAVEL_TYPE)
    Observable<BaseData<TravelListBean>> getTravelList(@Query("page") int page,@Query("pagesize") int pagesize,
                                                       @Query("typeid") String typeId);
}
