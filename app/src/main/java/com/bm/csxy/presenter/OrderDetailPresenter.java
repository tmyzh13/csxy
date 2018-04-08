package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.OrderDetailApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.view.interfaces.OrderDetailView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/8.
 */

public class OrderDetailPresenter extends BasePresenter<OrderDetailView> {

    OrderDetailApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(OrderDetailApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getOrderDetail(String id){
        view.showLoading();;
        api.getOrderDetail(id)
                .compose(new ResponseTransformer<>(this.<BaseData<TravelOrderBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<TravelOrderBean>>(view) {
                    @Override
                    public void success(BaseData<TravelOrderBean> travelOrderBeanBaseData) {
                            view.renderData(travelOrderBeanBaseData.data);
                    }
                });

    }
}
