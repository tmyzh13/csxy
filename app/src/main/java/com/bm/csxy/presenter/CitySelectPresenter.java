package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.CitySelectApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.CityBean;
import com.bm.csxy.view.interfaces.CitySelectView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by john on 2017/11/8.
 */

public class CitySelectPresenter extends BasePresenter<CitySelectView> {

    CitySelectApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(CitySelectApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getCity(){
        view.showLoading();
        api.getCitys("0")
                .compose(new ResponseTransformer<>(this.<BaseData<List<CityBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<CityBean>>>(view) {
                    @Override
                    public void success(BaseData<List<CityBean>> baseData) {
                        view.renderCitys(baseData.data);
                    }
                });
    }
}
