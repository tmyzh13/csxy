package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.ChoiceProductApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.view.interfaces.ChoiceProductView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/7.
 */

public class ChoiceProductPresenter extends BasePresenter<ChoiceProductView> {

    private ChoiceProductApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ChoiceProductApi.class);
    }

    @Override
    public void onStart() {

    }

    public void orderProduct(String id,int num,String usrId,String customid,String startDate,String userJson){
        view.showLoading();
        api.confirmOrder(id,num,usrId,customid,startDate,userJson)
                .compose(new ResponseTransformer<>(this.<BaseData<TravelOrderBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<TravelOrderBean>>(view) {
                    @Override
                    public void success(BaseData<TravelOrderBean> baseData) {
                        view.orderProductSuccess(baseData.data);
                    }
                });
    }
}
