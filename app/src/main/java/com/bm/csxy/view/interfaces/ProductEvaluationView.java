package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.EvaluationListBean;
import com.bm.csxy.model.bean.ProductEvaluationBean;
import com.corelibs.base.BasePaginationView;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductEvaluationView extends BasePaginationView {

    void renderData(boolean reload, List<ProductEvaluationBean> bean,String gradeSum,String nums);
}
