package com.bm.csxy.presenter;

import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.LoginApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.model.bean.UserThirdBean;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.LoginVIew;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/7.
 */

public class LoginPresenter extends BasePresenter<LoginVIew> {

    LoginApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(LoginApi.class);
    }

    @Override
    public void onStart() {

    }

    public void login(String username,String password){
        if(Tools.isNull(username)||!Tools.validatePhone(username)){
            ToastMgr.show("请输入手机号");
            return;
        }
        if(Tools.isNull(password)){
            ToastMgr.show("请输入密码");
            return;
        }
        view.showLoading();
        api.login(username,password,"2")
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>(view) {
                    @Override
                    public void success(BaseData<UserBean> baseData) {
                        UserHelper.saveUser(baseData.data);
                        PreferencesHelper.saveData(Constant.LOGIN,"1");
                        view.loginSuccess();
                    }
                });
    }

    public void thirdLogin(String nickName,String openId,String headerBase){
        view.showLoading();
        api.thirdLogin(nickName,openId,"Wechat",headerBase)
                .compose(new ResponseTransformer<>(this.<BaseData<UserThirdBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserThirdBean>>(view) {
                    @Override
                    public void success(BaseData<UserThirdBean> userThirdBeanBaseData) {
                        UserHelper.saveUser(userThirdBeanBaseData.data.userInfo);
                        PreferencesHelper.saveData(Constant.LOGIN,"1");
                        view.loginSuccess();
                    }
                });
    }
}
