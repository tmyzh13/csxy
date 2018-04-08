package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.AdvertiseBean;
import com.bm.csxy.model.bean.TypeBean;
import com.corelibs.base.BaseView;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public interface HomeView extends BaseView {

    void rendAds(List<AdvertiseBean> list);
    void rendTypes(List<TypeBean> list);

}
