package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.TravelBean;
import com.corelibs.base.BasePaginationView;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public interface ProductView extends BasePaginationView {

    void renderProducts(boolean reload, List<TravelBean> list);
}
