package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductDetailApi {

    @POST(Urls.TRAVEL_DETAIL)
    Observable<BaseData<TravelBean>> getTravelDetail(@Query("scenicid")String scenicid
                                                     );
 }
