package com.bm.csxy.presenter;

import android.util.Log;

import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.OrderTypeChildApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.model.bean.TravelOrderListBean;
import com.bm.csxy.view.interfaces.OrderTypeChildView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2017/11/8.
 */

public class OrderTypeChildPresenter extends PagePresenter<OrderTypeChildView> {

    private OrderTypeChildApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(OrderTypeChildApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getOrderList(final boolean reload,String orderStatue){
        if(!doPagination(reload)) return;
        if(reload) view.showLoading();
        api.getOrderList(UserHelper.getUserId()+"",getPageNo(),getPageSize(),orderStatue)
                .compose(new ResponseTransformer<>(this.<BaseData<TravelOrderListBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new PaginationSubscriber<BaseData<TravelOrderListBean>>(view,this,reload) {
                    @Override
                    protected void onDataNotNull(BaseData<TravelOrderListBean> travelOrderListBeanBaseData) {
                        view.renderData(reload,travelOrderListBeanBaseData.data.data);
                    }

                    @Override
                    protected Object getCondition(BaseData<TravelOrderListBean> travelOrderListBeanBaseData, boolean dataNotNull) {
                        return (travelOrderListBeanBaseData.data.count/travelOrderListBeanBaseData.data.pageSize+1);
                    }

                    @Override
                    protected List getListResult(BaseData<TravelOrderListBean> travelOrderListBeanBaseData, boolean dataNotNull) {
                        if(dataNotNull){
                            List<TravelOrderBean> list=new ArrayList<TravelOrderBean>();
                            view.renderData(reload,list);
                            return travelOrderListBeanBaseData.data.data;
                        }


                        return null;
                    }
                });
    }

    public void deleteOrder(String id){
        api.deleteOrder(id)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        view.deleteOrderSuccess();
                    }
                });
    }
}
