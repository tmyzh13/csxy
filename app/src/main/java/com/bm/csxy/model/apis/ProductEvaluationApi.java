package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.EvaluationBean;
import com.bm.csxy.model.bean.EvaluationListBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductEvaluationApi {

    @POST(Urls.EVALUATION_LIST)
    Observable<BaseData<EvaluationBean>> getEvaluationList(@Query("scenicid") String id,
                                                           @Query("page") int page,
                                                           @Query("pagesize") int pagesize);
}
