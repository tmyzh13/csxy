package com.bm.csxy.adapters;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bm.csxy.R;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.ProductEvaluationBean;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/25.
 */

public class ProductEvaluationAdapter extends QuickAdapter<ProductEvaluationBean> {

    public ProductEvaluationAdapter(Context context){
        super(context, R.layout.item_evaluation);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProductEvaluationBean item, int position) {
//

        TextView tv_recover=helper.getView(R.id.tv_recover);
        helper.setText(R.id.tv_name,item.userName)
                .setText(R.id.commentCtime,item.contenttimeStr)
                .setText(R.id.commentContent,item.content)
                .setText(R.id.tv_score,item.grade+"")
//                .setText(R.id.tv_recover,item.recover)
        ;

        RatingBar rb=helper.getView(R.id.ratingBar);
        rb.setRating(Float.parseFloat(item.grade));
        CircleImageView iv=helper.getView(R.id.iv_icon);
        Glide.with(context).load(Urls.ROOT_IMG+item.userAvatar).override(320,320).into(iv);
        if(Tools.isNull(item.reply)){
            tv_recover.setVisibility(View.GONE);
        }else{
            tv_recover.setVisibility(View.VISIBLE);
            tv_recover.setText("客户回复："+item.reply);
        }
    }
}
