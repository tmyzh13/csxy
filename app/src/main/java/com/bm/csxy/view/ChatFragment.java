package com.bm.csxy.view;

import android.os.Bundle;

import com.bm.csxy.R;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;

/**
 * Created by john on 2017/11/7.
 */

public class ChatFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
