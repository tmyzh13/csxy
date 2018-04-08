package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface SetPasswordApi {
    @POST(Urls.REGISTER)
    Observable<BaseData<UserBean>> register(@Query("password")String password,
                                            @Query("phone")String phone,
                                            @Query("nickname")String nickname,
                                            @Query("userPicture") String userPicture);

    @POST(Urls.FORGET_PASSWORD)
    Observable<BaseData<UserBean>> forgetPassword(@Query("username") String username,
                                                  @Query("password") String password);
}
