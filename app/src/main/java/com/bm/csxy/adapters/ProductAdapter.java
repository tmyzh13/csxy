package com.bm.csxy.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.bm.csxy.R;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.TravelBean;
import com.bumptech.glide.Glide;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/24.
 */

public class ProductAdapter extends QuickAdapter<TravelBean> {

    private Context context;
    public ProductAdapter(Context context){
        super(context, R.layout.item_product);
        this.context=context;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, TravelBean item, int position) {
        ImageView iv_product_icon=helper.getView(R.id.iv_product_icon);
        //用其它图片作为缩略图
//        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
//                .with(context)
//                .load(R.drawable.list_default_bg)
//                .override(mScreenWidth, height) //图片显示的分辨率 ，像素值
//                .centerCrop();
        Glide.with(context).load(Urls.ROOT_IMG+item.scenicpic)
                .override(320,320)
//                .transform(new CenterCrop(context), new RoundedTransformationBuilder().oval(true).build(context))
                .into(iv_product_icon);
        helper.setText(R.id.tv_product_tiltle,item.scenicname)
                .setText(R.id.tv_product_introduce,item.scenicdesc)
                .setText(R.id.tv_product_price,item.price+"")
                .setText(R.id.tv_product_days,item.timelength)
                .setText(R.id.tv_zan,item.goodtotal)
                .setText(R.id.tv_commets_num,item.commentNum);
    }
}
