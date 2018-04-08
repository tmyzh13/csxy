package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.TravelOrderBean;
import com.corelibs.base.BaseView;

/**
 * Created by john on 2017/11/7.
 */

public interface ChoiceProductView extends BaseView {

    void orderProductSuccess(TravelOrderBean bean);

}
