package com.bm.csxy.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.bm.csxy.MainActivity;
import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.bean.ActionOrderBean;
import com.bm.csxy.model.bean.BackHomeBean;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2017/11/9.
 */

public class PaySuccessActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_pay)
    ImageView iv_pay;

    private Context context=PaySuccessActivity.this;
    private String type;

    public static Intent getLauncher(Context context,String type){
        Intent intent =new Intent(context,PaySuccessActivity.class);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        nav.hideBack();

        type=getIntent().getStringExtra("type");
        if(type.equals("1")){
            //成功
            nav.setTitle("支付成功");
            iv_pay.setImageResource(R.mipmap.pay_success);
        }else if(type.equals("2")){
            //失败
            nav.setTitle("支付失败");
            iv_pay.setImageResource(R.mipmap.pay_fail);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    @OnClick(R.id.tv_back_home)
    public void backHome(){
        BackHomeBean bean =new BackHomeBean();
        bean.backStyle="首页";
        RxBus.getDefault().send(bean, Constant.CHAT_TO_HOME);

        startActivity(MainActivity.getLauncher(context));
        finish();
    }

    @OnClick(R.id.tv_back_order)
    public void backOrder(){
        BackHomeBean bean =new BackHomeBean();
        bean.backStyle="订单";
        RxBus.getDefault().send(bean, Constant.CHAT_TO_HOME);

        //给订单那边进入待评价 并且刷新
        ActionOrderBean actionOrderBean=new ActionOrderBean();
        actionOrderBean.position=0;
        RxBus.getDefault().send(actionOrderBean,Constant.ACTION_ORDER);
        startActivity(MainActivity.getLauncher(context));
        finish();
    }
}
