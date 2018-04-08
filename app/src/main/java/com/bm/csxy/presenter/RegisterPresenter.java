package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.RegisterApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.RegisterView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/7.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    RegisterApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(RegisterApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getCode(String phone,String type){
        if(Tools.isNull(phone)||!Tools.validatePhone(phone)){
            ToastMgr.show("请输入手机号");
            return;
        }
        view.showLoading();
        api.getCode(phone,type)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.getCodeSuccess();
                    }
                });
    }
    public void checkCode(String phone ,String code){
        if(Tools.isNull(phone)||!Tools.validatePhone(phone)){
            ToastMgr.show("请输入手机号");
            return;
        }
        if(Tools.isNull(code)){
            ToastMgr.show("请输入验证码");
            return;
        }
        view.showLoading();
        api.checkCode(phone,code)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.checkCodeSuccess();
                    }
                });
    }
}
