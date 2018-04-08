package com.bm.csxy.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bm.csxy.R;
import com.bm.csxy.model.bean.PassenegerInfo;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

/**
 * Created by john on 2017/10/30.
 */

public class ChoiceProductAdapter extends QuickAdapter<PassenegerInfo> {

    public ChoiceProductAdapter(Context context){
        super(context, R.layout.item_choice_adapter);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final PassenegerInfo item, int position) {
        final EditText et_choice_product_name=helper.getView(R.id.et_choice_product_name);
        final EditText et_choice_product_phone=helper.getView(R.id.et_choice_product_phone);
        final EditText et_choice_product_identify=helper.getView(R.id.et_choice_product_identity);

        et_choice_product_name.setTag(position);
        et_choice_product_phone.setTag(position);
        et_choice_product_identify.setTag(position);

        et_choice_product_name.setText(item.name);
        et_choice_product_phone.setText(item.phone);
        et_choice_product_identify.setText(item.idcard);

        et_choice_product_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                getData().get(Integer.parseInt(et_choice_product_name.getTag()+"")).name=editable.toString();
            }
        });
        et_choice_product_identify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getData().get(Integer.parseInt(et_choice_product_identify.getTag()+"")).idcard=editable.toString();
            }
        });
//
        et_choice_product_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getData().get(Integer.parseInt(et_choice_product_phone.getTag()+"")).phone=editable.toString();
            }
        });
    }

}
