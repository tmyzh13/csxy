package com.bm.csxy.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.bm.csxy.R;
import com.bm.csxy.adapters.OrderTypeAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.bean.ActionOrderBean;
import com.bm.csxy.model.bean.BackHomeBean;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;

/**
 * Created by john on 2017/11/7.
 */

public class OrderFragmemt extends BaseFragment {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    private Handler handler=new Handler();

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("订单");
        nav.hideBack();

        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("待付款"));
        tabLayout.addTab(tabLayout.newTab().setText("待评价"));
        tabLayout.addTab(tabLayout.newTab().setText("退款"));

        OrderTypeAdapter adapter=new OrderTypeAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        RxBus.getDefault().toObservable(ActionOrderBean.class, Constant.ACTION_ORDER)
                .compose(this.<ActionOrderBean>bindToLifecycle())
                .subscribe(new RxBusSubscriber<ActionOrderBean>() {
                    @Override
                    public void receive(final ActionOrderBean data) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tabLayout.getTabAt(data.position).select();
                            }
                        },500);


                    }
                });


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
