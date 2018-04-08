package com.bm.csxy.presenter;

import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.apis.OrderCommentsApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.view.interfaces.OrderCommentsView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by john on 2017/11/8.
 */

public class OrderCommentsPresenter extends BasePresenter<OrderCommentsView> {

    OrderCommentsApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(OrderCommentsApi.class);
    }

    @Override
    public void onStart() {

    }

    public void comment(String id,String content,String grade){
        view.showLoading();
        api.commentsOrder(UserHelper.getUserId()+"",id,content,grade)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.commentsSuccess();
                    }
                });
    }
}
