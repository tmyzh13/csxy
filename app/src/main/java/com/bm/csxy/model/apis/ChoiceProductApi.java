package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface ChoiceProductApi {

    @FormUrlEncoded
    @POST(Urls.ORDER_PRODUCT)
    Observable<BaseData<TravelOrderBean>> confirmOrder(@Field("scenicid") String scenicid,
                                                       @Field("buynum") int buynum,
                                                       @Field("userid")String userid,
                                                       @Field("customid")String customid,
                                                       @Field("startday") String startday,
                                                       @Field("userJson") String userJson);
}
