package com.bm.csxy;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.bm.csxy.constants.Constant;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.GuaidActivity;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;

/**
 * Created by john on 2017/11/7.
 */

public class WelComeActivity extends BaseActivity {

    private Context context =WelComeActivity.this;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                startApp();
            }
        }, 2000);
    }
    private void startApp(){

            if(Tools.isNull(PreferencesHelper.getData(Constant.FIRST_INSTALL))){
                //进入欢迎页
                PreferencesHelper.saveData(Constant.FIRST_INSTALL,"1");
                startActivity(GuaidActivity.getLuancher(context));
                finish();
            }else{
                startActivity(MainActivity.getLauncher(context));
                finish();
            }

    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
