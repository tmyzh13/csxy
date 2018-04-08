package com.bm.csxy.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bm.csxy.R;
import com.bm.csxy.model.bean.TravelOrderBean;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/26.
 */

public class TravelOrderAdapter extends QuickAdapter<TravelOrderBean> {

    public TravelOrderAdapter(Context context){
        super(context, R.layout.item_travel_order);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final TravelOrderBean item, final int position) {

        TextView tv_order_statue=helper.getView(R.id.tv_order_statue);
        TextView tv_order_pay=helper.getView(R.id.tv_travel_order_pay);
        TextView tv_order_comment=helper.getView(R.id.tv_travel_order_comment);
        TextView tv_order_delete=helper.getView(R.id.tv_travel_order_delete);
        TextView tv_order_check_detail=helper.getView(R.id.tv_travel_order_check_detail);
        TextView tv_order_refund=helper.getView(R.id.tv_travel_order_refund);

        helper.setText(R.id.tv_order_num,item.ordercode)
                .setText(R.id.tv_order_product_name,item.scenicname)
                .setText(R.id.tv_order_product_content,item.scenicdesc)
                .setText(R.id.tv_product_count,item.buynum)
                .setText(R.id.tv_order_total_price,"￥"+item.amount);

        if(item.orderstatus.equals("1")){
            tv_order_statue.setText(context.getString(R.string.travel_order_paying));
            tv_order_pay.setVisibility(View.VISIBLE);
            tv_order_comment.setVisibility(View.GONE);
            tv_order_check_detail.setVisibility(View.GONE);
            tv_order_delete.setVisibility(View.VISIBLE);
            tv_order_refund.setVisibility(View.GONE);
        }else if(item.orderstatus.equals("2")){
            tv_order_statue.setText(context.getString(R.string.travel_order_commenting));
            tv_order_pay.setVisibility(View.GONE);
            tv_order_comment.setVisibility(View.VISIBLE);
            tv_order_check_detail.setVisibility(View.GONE);
            tv_order_delete.setVisibility(View.GONE);
            tv_order_refund.setVisibility(View.VISIBLE);
        }else if(item.orderstatus.equals("4")){
            tv_order_statue.setText(context.getString(R.string.travel_order_refund));
            tv_order_pay.setVisibility(View.GONE);
            tv_order_comment.setVisibility(View.GONE);
            tv_order_check_detail.setVisibility(View.VISIBLE);
            tv_order_delete.setVisibility(View.GONE);
            tv_order_refund.setVisibility(View.GONE);
        }else if(item.orderstatus.equals("3")){
            tv_order_statue.setText("已完成");
            tv_order_pay.setVisibility(View.GONE);
            tv_order_comment.setVisibility(View.GONE);
            tv_order_check_detail.setVisibility(View.VISIBLE);
            tv_order_delete.setVisibility(View.GONE);
            tv_order_refund.setVisibility(View.GONE);
        }

        tv_order_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionListener!=null){
                    actionListener.action("1",item.id,position);
                }
            }
        });
        tv_order_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionListener!=null){
                    actionListener.action("0",item.id,position);
                }
            }
        });
        tv_order_check_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionListener!=null){
                    actionListener.action("2",item.id,position);
                }
            }
        });
        tv_order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionListener!=null){
                    actionListener.action("3",item.id,position);
                }
            }
        });

        tv_order_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionListener!=null){
                    actionListener.action("4",item.id,position);
                }
            }
        });
    }

    //type 0 付款 1评价 2查看详情
    public interface ActionListener{
        void action(String type, String id,int position);
    }

    ActionListener actionListener;

    public void setActionListener(ActionListener actionListener){
        this.actionListener=actionListener;
    }


}
