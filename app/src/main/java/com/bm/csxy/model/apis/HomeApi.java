package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.AdvertiseBean;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TypeBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface HomeApi {

    @POST(Urls.GET_HOME_TYPE)
    Observable<BaseData<List<TypeBean>>> getHomeType(@Query("regionId") String regionId);

    @POST(Urls.GET_ADS)
    Observable<BaseData<List<AdvertiseBean>>> getAds();
}
