package com.bm.csxy.view.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bm.csxy.MainActivity;
import com.bm.csxy.R;
import com.bm.csxy.adapters.ChoiceProductAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.PassenegerInfo;
import com.bm.csxy.model.bean.PayInfoBean;
import com.bm.csxy.model.bean.TravelBean;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.bm.csxy.presenter.ChoiceProductPresenter;
import com.bm.csxy.utils.IntentUtil;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.ChoiceProductView;
import com.bm.csxy.view.order.PayActivity;
import com.bm.csxy.widget.AddView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.NoScrollingListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2017/11/7.
 */

public class ChoiceProductActivity extends BaseActivity<ChoiceProductView,ChoiceProductPresenter> implements ChoiceProductView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tv_total_price)
    TextView tv_total_price;
    @Bind(R.id.addview)
    AddView addview;
    @Bind(R.id.tv_year)
    TextView tv_year;
    @Bind(R.id.tv_month)
    TextView tv_month;
    @Bind(R.id.tv_day)
    TextView tv_day;
    @Bind(R.id.ll_month)
    LinearLayout ll_month;
    @Bind(R.id.ll_year)
    LinearLayout ll_year;
    @Bind(R.id.ll_day)
    LinearLayout ll_day;

    @Bind(R.id.lv_passenger)
    NoScrollingListView lv_passenger;
    @Bind(R.id.tv_product_name)
    TextView tv_product_name;
    @Bind(R.id.tv_product_introduce)
    TextView tv_product_introduce;
    @Bind(R.id.tv_product_name_)
    TextView tv_product_name_;
    @Bind(R.id.tv_product_contain)
    TextView tv_product_contain;
    @Bind(R.id.tv_product_no_contain)
    TextView tv_product_no_contain;
    @Bind(R.id.tv_show_time)
    TextView tv_show_time;
    @Bind(R.id.tv_ticket_type)
    TextView tv_ticket_type;
    @Bind(R.id.tv_product_price)
    TextView tv_product_price;

    private Context context=ChoiceProductActivity.this;
    private TravelBean bean;
    private double singlePrice;
    public String choiceDate;
    public double total;
    private ChoiceProductAdapter adapter;
    private List<PassenegerInfo> list;

    public static Intent getLuancher(Context context, TravelBean bean){
        Intent intent =new Intent(context,ChoiceProductActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("bean",bean);
        intent.putExtra("bundle",bundle);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_product;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        nav.setTitle("我要报名");
        Bundle bundle=getIntent().getBundleExtra("bundle");
        bean=(TravelBean) bundle.getParcelable("bean");

        singlePrice=bean.price;
        tv_product_name.setText(bean.scenicname);
        tv_product_introduce.setText(bean.scenicdesc);
        tv_product_name_.setText(bean.scenicname);
        tv_product_contain.setText(bean.feecontain);
        tv_product_no_contain.setText(bean.feenotcontain);
//        tv_show_time.setText(bean.playtime);
        tv_ticket_type.setText(bean.tickettype);
        tv_product_price.setText(bean.price+"");

        addview.setAddOrReduceListener(new AddView.OnClickListener() {
            @Override
            public void click(int count, boolean addOrreduce, int positon) {
                tv_total_price.setText(singlePrice*count+"");
                total=singlePrice*count;
                if(addOrreduce){
                    PassenegerInfo bean =new PassenegerInfo();
                    adapter.add(bean);
                }else{
                    if(adapter.getCount()!=0)
                        adapter.remove(adapter.getCount()-1);

                }
            }
        },1);

        //初始总价
        total=singlePrice;
        tv_total_price.setText(total+"");

        adapter =new ChoiceProductAdapter(context);
        lv_passenger.setAdapter(adapter);
        list=new ArrayList<>();
        PassenegerInfo bean=new PassenegerInfo();
        list.add(bean);
        adapter.replaceAll(list);

        //默认选择今天
        Time time = new Time();
        time.setToNow();
        tv_year.setText(time.year+"");
        tv_month.setText((time.month+1)+"");
        tv_day.setText(time.monthDay+"");
        choiceDate=time.year+"-"+(time.month+1)+"-"+time.monthDay;
        tv_show_time.setText(choiceDate);
    }

    @Override
    protected ChoiceProductPresenter createPresenter() {
        return new ChoiceProductPresenter();
    }


    @OnClick(R.id.ll_confirm_order)
    public void goConfirm(){
        if(Tools.isNull(choiceDate)){
            ToastMgr.show("请选择出行时间");
            return;
        }

        if(total==0){
            ToastMgr.show("购买不能少于一份");
            return;
        }

        if(checkPassengerInfo(adapter.getData())){
            return;
        }

        //接口
        Log.e("yzh",new Gson().toJson(adapter.getData())+"--");

        presenter.orderProduct(bean.id,adapter.getCount(),UserHelper.getUserId()+"",
                bean.customId,choiceDate,new Gson().toJson(adapter.getData()));
    }



    private boolean checkPassengerInfo(List<PassenegerInfo> list){
        for(int i=0;i<list.size();i++){
            PassenegerInfo bean=list.get(i);
            if(Tools.isNull(bean.name)){
                ToastMgr.show("第"+(i+1)+"个游客姓名未填");
                return true;
            }
            if(Tools.isNull(bean.idcard)||!Tools.validateIdCard(bean.idcard)){
                ToastMgr.show("第"+(i+1)+"个游客的身份证有误");
                return true;
            }
            if(Tools.isNull(bean.phone)||!Tools.validatePhone(bean.phone)){
                ToastMgr.show("第"+(i+1)+"个游客的电话有误");
                return true;
            }

        }
        return false;
    }

    private PopupWindow popTimeWindow;

    @OnClick(R.id.ll_year)
    public void choiceYear(){
        choiceTime();
    }

    @OnClick(R.id.ll_month)
    public void choiceMonth(){
        choiceTime();
    }

    @OnClick(R.id.ll_day)
    public void choiceDay(){
        choiceTime();
    }

    public void choiceTime(){
        if(popTimeWindow==null){
            createTimePick();
        }
        popTimeWindow.showAtLocation(nav, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    DatePicker dpTimeSelecter;
    RelativeLayout pw_parent;
    public void createTimePick(){
        LayoutInflater li = LayoutInflater.from(context);
        View viewTimePopWindow = li.inflate(R.layout.popup_window_date_select,
                null);
        dpTimeSelecter = (DatePicker) viewTimePopWindow
                .findViewById(R.id.date_picker_selecter);
        pw_parent=(RelativeLayout) viewTimePopWindow.findViewById(R.id.pw_parent);
        pw_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = dpTimeSelecter.getYear();
                int m = dpTimeSelecter.getMonth() + 1;
                String month="";
                if(m<10){
                    month="0"+m;
                }else{
                    month=m+"";
                }

                int d = dpTimeSelecter.getDayOfMonth();
                String day="";
                if(d<10){
                    day="0"+d;
                }else{
                    day=d+"";
                }
                Time time = new Time();
                time.setToNow();
                if(Tools.getTimeValue(y+"-"+month+"-"+day)<Tools.getTimeValue(time.year+"-"+(time.month+1)+"-"+time.monthDay)){
                    ToastMgr.show("时间已过期");
                    popTimeWindow.dismiss();
                    return;
                }else{
                    choiceDate=y+"-"+month+"-"+day;
//                    tv_date.setText(y+"/"+month+"/"+day);
                    tv_year.setText(y+"");
                    tv_month.setText(month+"");
                    tv_day.setText(day+"");
                    tv_show_time.setText(choiceDate);
                    popTimeWindow.dismiss();
                }
            }
        });
        // 创建时间选择的popwindow
        popTimeWindow = new PopupWindow(viewTimePopWindow,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置此参数获得焦点，否则无法点击
        popTimeWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popTimeWindow.setOutsideTouchable(true);
        // 需要设置一下此参数，点击外边可消失
        popTimeWindow.setBackgroundDrawable(new BitmapDrawable());
        popTimeWindow.update();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dpTimeSelecter.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                Calendar cldTime = Calendar.getInstance();
                cldTime.set(year, monthOfYear, dayOfMonth);
            }
        });

        //设置datePick的点击编辑效果
        dpTimeSelecter  .setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        dpTimeSelecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = dpTimeSelecter.getYear();
                int m = dpTimeSelecter.getMonth() + 1;
                String month="";
                if(m<10){
                    month="0"+m;
                }else{
                    month=m+"";
                }

                int d = dpTimeSelecter.getDayOfMonth();
                String day="";
                if(d<10){
                    day="0"+d;
                }else{
                    day=d+"";
                }
                Time time = new Time();
                time.setToNow();
                if(Tools.getTimeValue(y+"-"+month+"-"+day)<Tools.getTimeValue(time.year+"-"+(time.month+1)+"-"+time.monthDay)){
                    ToastMgr.show("时间已过期");
                    return;
                }else{
                    choiceDate=y+"-"+month+"-"+day;
//                    tv_date.setText(y+"/"+month+"/"+day);
                    tv_year.setText(y+"");
                    tv_month.setText(month+"");
                    tv_day.setText(day+"");
                    tv_show_time.setText(choiceDate);
                    popTimeWindow.dismiss();
                }

            }
        });


    }

    @OnClick(R.id.tv_connect_customer)
    public void goConnectCustomer(){

        if(Tools.isNull(UserHelper.getSavedUser().customPhone)){
            ToastMgr.show("暂无电话");
        }else{
            new MaterialDialog.Builder(context)
                    .title("提示").content("呼叫"+UserHelper.getSavedUser().customPhone)
                    .positiveText("确定")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            IntentUtil.tell(context, UserHelper.getSavedUser().customPhone/*.substring(5).replace("-", "").trim()*/);
                        }
                    }).show();
        }

    }

    @Override
    public void orderProductSuccess(TravelOrderBean bean) {
        ToastMgr.show("下单成功");
//        RxBus.getDefault().send(new Object(), Constant.ORDER_SUCCESS);
        PayInfoBean payInfoBean=new PayInfoBean();
        payInfoBean.ordercode=bean.ordercode;
        payInfoBean.amount=bean.amount;
        payInfoBean.ordername=bean.ordername;
        startActivity(PayActivity.getLauncher(context,payInfoBean));
    }
}
