package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface AlterPasswordApi {

    @POST(Urls.CHANGER_PASSWOR)
    Observable<BaseData> changePassword(@Query("username")String username,
                                        @Query("oldPassword") String oldPassword,
                                        @Query("newpassword") String newpassword);
}
