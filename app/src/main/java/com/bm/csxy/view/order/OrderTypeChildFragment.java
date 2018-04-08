package com.bm.csxy.view.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bm.csxy.R;
import com.bm.csxy.adapters.TravelOrderAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.ActionOrderBean;
import com.bm.csxy.model.bean.PayInfoBean;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.presenter.OrderTypeChildPresenter;
import com.bm.csxy.utils.IntentUtil;
import com.bm.csxy.view.interfaces.OrderTypeChildView;
import com.corelibs.base.BaseFragment;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by john on 2017/11/7.
 */

public class OrderTypeChildFragment extends BaseFragment<OrderTypeChildView,OrderTypeChildPresenter> implements OrderTypeChildView, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.lv_order)
    AutoLoadMoreListView lv_order;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private int position;
    private TravelOrderAdapter adapter;

    public static OrderTypeChildFragment newInstance(int position){
        OrderTypeChildFragment fragment =new OrderTypeChildFragment();
        Bundle b =new Bundle();
        b.putInt("position",position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_child;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        position= getArguments().getInt("position");

        adapter=new TravelOrderAdapter(getContext());
        lv_order.setAdapter(adapter);

        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(OrderDetailActivity.getLauncher(getContext(),adapter.getData().get(i).id));
            }
        });
        ptrLayout.setRefreshLoadCallback(this);
        loadData(true);

        adapter.setActionListener(new TravelOrderAdapter.ActionListener() {
            @Override
            public void action(String type, final String id,int position) {
                //type 0 付款 1评价 2查看详情 3 删除  4退款
                if(type.equals("0")){
                    TravelOrderBean travelOrderBean=adapter.getItem(position);
                    PayInfoBean payInfoBean =new PayInfoBean();
                    payInfoBean.ordername=travelOrderBean.scenicname;
                    payInfoBean.ordercode=travelOrderBean.ordercode;
                    payInfoBean.amount=travelOrderBean.amount;
                    startActivity(PayActivity.getLauncher(getContext(),payInfoBean));
                }else if(type.equals("1")){
                    startActivity(OrderCommentsActivity.getLauncher(getContext(),id));
                }else if(type.equals("2")){
                    startActivity(OrderDetailActivity.getLauncher(getContext(),id));
                }else if(type.equals("3")){
                    //删除
                    new MaterialDialog.Builder(getContext())
                            .title("提示").content("是否删除？")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    presenter.deleteOrder(id);
                                }
                            }).show();
                }else if(type.equals("4")){
                    //退款
                    new MaterialDialog.Builder(getContext())
                            .title("提示").content("是否联系客服退款")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    IntentUtil.tell(getContext(), UserHelper.getSavedUser().customPhone/*.substring(5).replace("-", "").trim()*/);
                                }
                            }).show();
                }
            }
        });
        RxBus.getDefault().toObservable(ActionOrderBean.class, Constant.ACTION_ORDER)
                .compose(this.<ActionOrderBean>bindToLifecycle())
                .subscribe(new RxBusSubscriber<ActionOrderBean>() {
                    @Override
                    public void receive(ActionOrderBean data) {
                        if(position==data.position){
                            //刷新的是当前页面才刷新
                            loadData(true);
                        }
                    }
                });
    }

    @Override
    protected OrderTypeChildPresenter createPresenter() {
        return new OrderTypeChildPresenter();
    }

    @Override
    public void renderData(boolean reload, List<TravelOrderBean> list) {
        if(reload)
            adapter.replaceAll(list);
        else
            adapter.addAll(list);
    }

    @Override
    public void deleteOrderSuccess() {
        ToastMgr.show("删除成功");
        loadData(true);
    }

    @Override
    public void showLoading() {
        ptrLayout.setRefreshing();
    }

    @Override
    public void hideLoading() {
        ptrLayout.complete();
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
        ptrLayout.disableLoading();
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        loadData(false);
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        if(!frame.isAutoRefresh()){
            ptrLayout.enableLoading();
            loadData(true);
        }
    }

    private void loadData(boolean reload){
        String statue="";
        if(position==3){
            statue="4";
        }else{
            statue=position+"";
        }
        presenter.getOrderList(reload,statue);
    }
}
