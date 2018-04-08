package com.bm.csxy.presenter;

import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.AlterPasswordApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.AlterPasswordView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/8.
 */

public class AlterPasswordPresenter extends BasePresenter<AlterPasswordView> {

    AlterPasswordApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(AlterPasswordApi.class);
    }

    @Override
    public void onStart() {

    }

    public void alterPassword(String oldPassword,String newPassword,String confirmPassword){
        if(Tools.isNull(oldPassword)){
            ToastMgr.show("请输入原始密码");
            return;
        }

        if(Tools.isNull(newPassword)){
            ToastMgr.show("请输入新密码");
            return;
        }

        if(Tools.isNull(confirmPassword)){
            ToastMgr.show("请确认密码");
            return;
        }
        if(!newPassword.equals(confirmPassword)){
            ToastMgr.show("新密码输入不一致");
            return;
        }
        view.showLoading();
        api.changePassword(UserHelper.getSavedUser().phone,oldPassword,newPassword)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.alterPasswordSuccess();
                    }
                });
    }
}
