package com.bm.csxy.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bm.csxy.R;
import com.bm.csxy.adapters.TravelOrderDetailInfoAdapter;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.presenter.OrderDetailPresenter;
import com.bm.csxy.utils.IntentUtil;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.OrderDetailView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.NoScrollingListView;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by john on 2017/11/8.
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailView,OrderDetailPresenter> implements OrderDetailView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tv_travel_order_detail_name)
    TextView tv_travel_order_detail_name;
    @Bind(R.id.tv_travel_order_detail_num)
    TextView tv_travel_order_detail_num;
    @Bind(R.id.tv_travel_order_detail_time)
    TextView tv_travel_order_detail_time;
    @Bind(R.id.tv_travel_order_detail_total_price)
    TextView tv_travel_order_detail_total_price;
    @Bind(R.id.tv_travel_order_detail_pay_time)
    TextView tv_travel_order_detail_pay_time;
    @Bind(R.id.tv_travel_order_detail_count)
    TextView tv_travel_order_detail_count;
    @Bind(R.id.tv_travel_order_time)
    TextView tv_travel_order_time;
    @Bind(R.id.lv_info)
    NoScrollingListView lv_info;

    private Context context=OrderDetailActivity.this;
    private String id;
    private TravelOrderDetailInfoAdapter adapter;
    public static Intent getLauncher(Context context,String id){
        Intent intent=new Intent(context,OrderDetailActivity.class);
        intent.putExtra("id",id);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_order_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        id=getIntent().getStringExtra("id");
        nav.setTitle("订单详情");

        adapter=new TravelOrderDetailInfoAdapter(context);
        lv_info.setAdapter(adapter);

        presenter.getOrderDetail(id);
    }

    @Override
    protected OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter();
    }
    private TravelOrderBean bean;
    @Override
    public void renderData(TravelOrderBean bean) {
        this.bean=bean;
        //标题设置订单名称
        nav.setTitle(bean.scenicname);

        tv_travel_order_detail_name.setText(bean.scenicname);
        tv_travel_order_detail_num.setText(bean.ordercode);
        tv_travel_order_detail_time.setText(bean.startday);
        tv_travel_order_detail_total_price.setText(bean.amount+"");
        if(Tools.isNull(bean.paydatestr)){
            tv_travel_order_detail_pay_time.setText("");
        }else{
            tv_travel_order_detail_pay_time.setText(bean.paydatestr);
        }
        tv_travel_order_detail_count.setText(bean.buynum);
        tv_travel_order_time.setText(bean.createdateStr);
        adapter.replaceAll(bean.scenicOrderUserList);
    }

    @OnClick(R.id.ll_custom)
    public void goCustomPhone(){
        if(Tools.isNull(bean.customPhone)){
            ToastMgr.show("暂无电话");
        }else{
            new MaterialDialog.Builder(context)
                    .title("提示").content("呼叫"+UserHelper.getSavedUser().customPhone)
                    .positiveText("确定")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            IntentUtil.tell(context, UserHelper.getSavedUser().customPhone/*.substring(5).replace("-", "").trim()*/);
                        }
                    }).show();
        }
    }

    @OnClick(R.id.ll_chat)
    public void goChat(){
        RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE,"customId"+UserHelper.getSavedUser().customId,"1");

    }
}
