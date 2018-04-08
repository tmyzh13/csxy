package com.bm.csxy.model.apis;



import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/6.
 */

public interface UserApi {

    @POST(Urls.GET_RONGYUN_CUSTOM_INFO)
    Observable<BaseData<UserBean>> getRongyunCustomInfo(@Query("id") String id);

    @POST(Urls.GET_RONGYUN_USER_INFO)
    Observable<BaseData<UserBean>> getRongyunUserInfo(@Query("userId") String userId);
}
