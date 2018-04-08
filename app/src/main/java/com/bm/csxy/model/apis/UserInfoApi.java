package com.bm.csxy.model.apis;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.IconBean;
import com.corelibs.api.RequestBodyCreator;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by john on 2017/11/8.
 */

public interface UserInfoApi {

    @POST(Urls.ALTER_USER_INFO)
    Observable<BaseData> alteruserInfo(@Query("imgBase") String imageBase ,
                                       @Query("userNickname") String userNickname,
                                       @Query("sex") String sex,
                                       @Query("id") String id);

    @Multipart
    @POST(Urls.LOAD_IMG)
    Observable<BaseData<IconBean>> upload(@Part("type") RequestBody type,

                                          @Part("file"+ RequestBodyCreator.MULTIPART_HACK) RequestBody file);
}
