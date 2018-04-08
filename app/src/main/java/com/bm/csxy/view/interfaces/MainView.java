package com.bm.csxy.view.interfaces;

import com.bm.csxy.model.bean.UserBean;
import com.corelibs.base.BaseView;

/**
 * Created by john on 2017/11/6.
 */

public interface MainView extends BaseView {

    void renderData(UserBean bean, String rongId);
}
