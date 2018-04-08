package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.model.bean.UserThirdBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/7.
 */

public interface LoginApi {
    @POST(Urls.LOGIN)
    Observable<BaseData<UserBean>> login(@Query("username") String username,
                                         @Query("password") String password,
                                         @Query("simple") String simple);

    @POST(Urls.THIRD_LOGIN)
    Observable<BaseData<UserThirdBean>> thirdLogin(@Query("userNickname") String nickUsername,
                                                   @Query("thOpenid")String thOpenid,
                                                   @Query("thLoginType") String loginType,
                                                   @Query("headerBase") String headerBase);
}
