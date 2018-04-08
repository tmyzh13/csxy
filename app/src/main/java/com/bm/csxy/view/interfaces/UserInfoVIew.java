package com.bm.csxy.view.interfaces;

import android.graphics.drawable.Icon;

import com.bm.csxy.model.bean.IconBean;
import com.corelibs.base.BaseView;

/**
 * Created by john on 2017/11/8.
 */

public interface UserInfoVIew extends BaseView {

    void alterUserInfoSuccess();
    void loadPicSuccess(IconBean bean);
}
