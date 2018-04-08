package com.bm.csxy.presenter;

import com.bm.csxy.model.apis.ProductEvaluationApi;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.model.bean.EvaluationBean;
import com.bm.csxy.model.bean.EvaluationListBean;
import com.bm.csxy.model.bean.ProductEvaluationBean;
import com.bm.csxy.view.interfaces.ProductEvaluationView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public class ProductEvaluationPresenter extends PagePresenter<ProductEvaluationView>{

    private ProductEvaluationApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ProductEvaluationApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getEvaluationList(final boolean reload,String id){
        if(!doPagination(reload)) return;
        if(reload) view.showLoading();
        api.getEvaluationList(id,getPageNo(),getPageSize())
                .compose(new ResponseTransformer<>(this.<BaseData<EvaluationBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new PaginationSubscriber<BaseData<EvaluationBean>>(view,this,reload) {
                    @Override
                    protected void onDataNotNull(BaseData<EvaluationBean> evaluationListBeanBaseData) {
                        if(evaluationListBeanBaseData.data!=null)
                        view.renderData(reload,evaluationListBeanBaseData.data.scenicCommentList.data,evaluationListBeanBaseData.data.gradeSum,
                                evaluationListBeanBaseData.data.scenicCommentList.count+"");
                    }

                    @Override
                    protected Object getCondition(BaseData<EvaluationBean> evaluationListBeanBaseData, boolean dataNotNull) {
                        return (evaluationListBeanBaseData.data.scenicCommentList.count/evaluationListBeanBaseData.data.scenicCommentList.pageSize+1);
                    }

                    @Override
                    protected List getListResult(BaseData<EvaluationBean> evaluationListBeanBaseData, boolean dataNotNull) {
                        if (dataNotNull){
                            List<ProductEvaluationBean> list=new ArrayList<ProductEvaluationBean>();
                            view.renderData(reload,list,"0","0");
                            return evaluationListBeanBaseData.data.scenicCommentList.data;
                        }

                        return null;
                    }
                });
    }
}
