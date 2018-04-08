package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.TravelBean;
import com.corelibs.base.BaseView;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductDetailView extends BaseView {

    void renderData(TravelBean bean);
    void getDataFail();
}
