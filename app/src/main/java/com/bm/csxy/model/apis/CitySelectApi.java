package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.CityBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface CitySelectApi {

    @POST(Urls.GET_CITYS)
    Observable<BaseData<List<CityBean>>> getCitys(@Query("isChina") String isChina);
}
