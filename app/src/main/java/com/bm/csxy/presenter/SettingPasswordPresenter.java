package com.bm.csxy.presenter;

import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.SetPasswordApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.SettingPasswordView;
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

public class SettingPasswordPresenter extends BasePresenter<SettingPasswordView> {

    SetPasswordApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(SetPasswordApi.class);
    }

    @Override
    public void onStart() {

    }

    public void register(String phone,String password,String confirmPassword){
        if(Tools.isNull(password)){
            ToastMgr.show("请输入密码");
            return;
        }

        if(Tools.isNull(confirmPassword)){
            ToastMgr.show("请输入确认密码");
            return;
        }

        if(!password.equals(confirmPassword)){
            ToastMgr.show("两次密码输入不一致");
            return;
        }
        view.showLoading();
        api.register(password,phone,phone,"")
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>(view) {
                    @Override
                    public void success(BaseData<UserBean> userBeanBaseData) {
                        UserHelper.saveUser(userBeanBaseData.data);
                        PreferencesHelper.saveData(Constant.LOGIN,"1");
                        view.registerSuccess();
                    }
                });
    }
    public void forgetPassword(String username,String password,String confirmPassword){
        if(Tools.isNull(password)){
            ToastMgr.show("请输入密码");
            return;
        }

        if(Tools.isNull(confirmPassword)){
            ToastMgr.show("请输入确认密码");
            return;
        }

        if(!password.equals(confirmPassword)){
            ToastMgr.show("两次密码输入不一致");
            return;
        }
        view.showLoading();
        api.forgetPassword(username,password)
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>(view) {
                    @Override
                    public void success(BaseData<UserBean> userBeanBaseData) {
                        UserHelper.saveUser(userBeanBaseData.data);
                        PreferencesHelper.saveData(Constant.LOGIN,"1");
                        view.registerSuccess();
                    }
                });
    }
}
