package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.HomeApi;
import com.bm.csxy.model.bean.AdvertiseBean;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.TypeBean;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.HomeView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    private HomeApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(HomeApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getHomeType(String id){
        view.showLoading();
        api.getHomeType(id)
                .compose(new ResponseTransformer<>(this.<BaseData<List<TypeBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<TypeBean>>>(view) {
                    @Override
                    public void success(BaseData<List<TypeBean>> listBaseData) {
                        if(listBaseData.data!=null||listBaseData.data.size()!=0)
                        view.rendTypes(listBaseData.data);
                    }

                    @Override
                    public boolean operationError(BaseData<List<TypeBean>> listBaseData, int status, String message) {
                        if(Tools.isNull(listBaseData.msg)){
                            ToastMgr.show("获取数据失败");
                        }else{
                            ToastMgr.show(listBaseData.msg);
                        }
                        return true;
                    }
                });
    }

    public void getAds(){
        api.getAds().compose(new ResponseTransformer<>(this.<BaseData<List<AdvertiseBean>>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<List<AdvertiseBean>>>() {
                    @Override
                    public void success(BaseData<List<AdvertiseBean>> baseData) {
                        view.rendAds(baseData.data);
                    }
                });
    }
}
