package com.bm.csxy.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.bm.csxy.R;

import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.TypeBean;
import com.bumptech.glide.Glide;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/24.
 */

public class TypeAdapter extends QuickAdapter<TypeBean> {

    public TypeAdapter(Context context){
        super(context, R.layout.item_type_bean);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, TypeBean item, int position) {

        ImageView iv_type=helper.getView(R.id.iv_type);
        Glide.with(context).load(Urls.ROOT_IMG+item.icon)
                .override(320,320)
//                .transform(new CenterCrop(context), new RoundedTransformationBuilder().oval(true).build(context))
                .into(iv_type);
        helper.setText(R.id.tv_type,item.typename)
        .setText(R.id.tv_desc,item.describ)
        ;
    }
}
