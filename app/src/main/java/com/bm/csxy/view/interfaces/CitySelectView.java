package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.CityBean;
import com.corelibs.base.BaseView;

import java.util.List;

/**
 * Created by john on 2017/11/8.
 */

public interface CitySelectView extends BaseView{

    void renderCitys(List<CityBean> list);
}
