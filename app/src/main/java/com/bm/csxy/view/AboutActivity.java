package com.bm.csxy.view;

import android.os.Bundle;

import com.bm.csxy.R;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by john on 2017/11/10.
 */

public class AboutActivity extends BaseActivity{

    @Bind(R.id.nav)
    NavBar nav;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("关于我们");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
