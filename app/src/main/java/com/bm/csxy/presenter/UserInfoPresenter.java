package com.bm.csxy.presenter;

import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.UserInfoApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.IconBean;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.UserInfoVIew;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.RequestBodyCreator;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.File;

/**
 * Created by john on 2017/11/8.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoVIew> {

    UserInfoApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(UserInfoApi.class);
    }

    @Override
    public void onStart() {

    }

    public void loadFile(File file){
        view.showLoading();
        api.upload(RequestBodyCreator.create("1"),RequestBodyCreator.create(file))
                .compose(new ResponseTransformer<>(this.<BaseData<IconBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<IconBean>>(view) {
                    @Override
                    public void success(BaseData<IconBean> baseData) {
                        view.loadPicSuccess(baseData.data);
                    }
                });
    }

    public void alterUserInfo(String name,String sex,String icon){
        if(Tools.isNull(name)){
            ToastMgr.show("昵称不能为空");
            return;
        }
        view.showLoading();
        api.alteruserInfo(icon,name,sex, UserHelper.getUserId()+"")
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.alterUserInfoSuccess();
                    }
                });
    }
}
