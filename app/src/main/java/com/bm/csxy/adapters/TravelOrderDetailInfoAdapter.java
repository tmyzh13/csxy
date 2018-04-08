package com.bm.csxy.adapters;

import android.content.Context;

import com.bm.csxy.R;
import com.bm.csxy.model.bean.PassenegerInfo;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/26.
 */

public class TravelOrderDetailInfoAdapter extends QuickAdapter<PassenegerInfo> {

    public TravelOrderDetailInfoAdapter(Context context){
        super(context, R.layout.item_travel_order_info);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, PassenegerInfo item, int position) {
        helper.setText(R.id.tv_travel_order_detail_phone,item.phone)
                .setText(R.id.tv_travel_order_detail_travel_name,item.name)
                .setText(R.id.tv_travel_order_detail_identify,item.idcard);
    }
}
