package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.TravelOrderBean;
import com.corelibs.base.BasePaginationView;

import java.util.List;

/**
 * Created by john on 2017/11/8.
 */

public interface OrderTypeChildView extends BasePaginationView {

    void renderData(boolean reload, List<TravelOrderBean> list);
    void deleteOrderSuccess();
}
