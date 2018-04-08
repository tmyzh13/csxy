package com.bm.csxy.view;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.bean.BackHomeBean;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;

/**
 * Created by john on 2017/10/28.
 */

public class ChatActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;

    private String title="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("聊天界面");
        title=getIntent().getData().getQueryParameter("title");
        Log.e("yzh","title=="+title);

        nav.setOnBackClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.equals("1")){
                    finish();
                }else{
                    //首页模块进入
                    BackHomeBean bean =new BackHomeBean();
                    bean.backStyle=title;
                    RxBus.getDefault().send(bean, Constant.CHAT_TO_HOME);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(title.equals("1")){
            }else{
                //首页模块进入
                BackHomeBean bean =new BackHomeBean();
                bean.backStyle=title;
                RxBus.getDefault().send(bean, Constant.CHAT_TO_HOME);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
