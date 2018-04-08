package com.bm.csxy.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bm.csxy.MainActivity;
import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

/**
 * Created by john on 2017/11/9.
 */

public class GuaidActivity extends BaseActivity {

    private Context context=GuaidActivity.this;
    public static Intent getLuancher(Context context){
        Intent intent=new Intent(context,GuaidActivity.class);
        return intent;
    }

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
                startActivity(MainActivity.getLauncher(context));
                finish();
            }
        }, 2000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
