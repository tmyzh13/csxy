package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface RegisterApi {

    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Query("phone") String phone,@Query("authCodeType") String authCodeType);

    @POST(Urls.CHECK_CODE)
    Observable<BaseData> checkCode(@Query("send_to") String send_to,
                                   @Query("code") String code);
}
