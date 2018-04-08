package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.UserApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.view.interfaces.MainView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/6.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private UserApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(UserApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getCustomId(String id, final String rongId){
        api.getRongyunCustomInfo(id)
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>() {
                    @Override
                    public void success(BaseData<UserBean> baseData) {
                        view.renderData(baseData.data,rongId);
                    }
                });
    }

    public void getUserId(String id,final String rongId){
        api.getRongyunUserInfo(id)
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>() {
                    @Override
                    public void success(BaseData<UserBean> baseData) {
                        view.renderData(baseData.data,rongId);
                    }
                });
    }
}
