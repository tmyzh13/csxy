package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.ProductApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelListBean;
import com.bm.csxy.view.interfaces.ProductView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public class ProductPresenter extends PagePresenter<ProductView> {

    private ProductApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ProductApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getProduct(final boolean reload,String id){
        if(!doPagination(reload)) return;
        if(reload) view.showLoading();
        api.getTravelList(getPageNo(),getPageSize(),id)
                .compose(new ResponseTransformer<>(this.<BaseData<TravelListBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new PaginationSubscriber<BaseData<TravelListBean>>(view,this,reload) {
                    @Override
                    protected void onDataNotNull(BaseData<TravelListBean> travelListBeanBaseData) {
                        view.renderProducts(reload,travelListBeanBaseData.data.data);
                    }

                    @Override
                    protected Object getCondition(BaseData<TravelListBean> travelListBeanBaseData, boolean dataNotNull) {
                        return (travelListBeanBaseData.data.count/travelListBeanBaseData.data.pageSize+1);
                    }

                    @Override
                    protected List getListResult(BaseData<TravelListBean> travelListBeanBaseData, boolean dataNotNull) {
                        if (dataNotNull) return travelListBeanBaseData.data.data;
                        return null;
                    }
                });
    }
}
