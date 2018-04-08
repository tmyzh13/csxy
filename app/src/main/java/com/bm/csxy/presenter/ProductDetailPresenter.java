package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.ProductDetailApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TravelBean;
import com.bm.csxy.view.interfaces.ProductDetailView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/7.
 */

public class ProductDetailPresenter extends BasePresenter<ProductDetailView> {

    ProductDetailApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ProductDetailApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getProductDetail(String id){
        view.showLoading();
        api.getTravelDetail(id).compose(new ResponseTransformer<>(this.<BaseData<TravelBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<TravelBean>>(view) {
                    @Override
                    public void success(BaseData<TravelBean> travelBeanBaseData) {
                        view.renderData(travelBeanBaseData.data);
                    }

                    @Override
                    public boolean operationError(BaseData<TravelBean> travelBeanBaseData, int status, String message) {
                        view.getDataFail();
                        return super.operationError(travelBeanBaseData, status, message);
                    }
                });
    }
}
