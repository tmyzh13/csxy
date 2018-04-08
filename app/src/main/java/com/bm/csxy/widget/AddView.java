package com.bm.csxy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.csxy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/18.
 */

public class AddView extends LinearLayout {
    @Bind(R.id.iv_reduce)
    ImageView iv_reduce;
    @Bind(R.id.iv_add)
    ImageView iv_add;
    @Bind(R.id.tv_hour)
    TextView tv_hour;
    private int count = 1 ; //默认 位0
    public OnClickListener listener;
    private int positon;
    public AddView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_add,this);
        ButterKnife.bind(this,this);
        tv_hour.setText(1+"");

    }
    @OnClick({R.id.iv_reduce, R.id.iv_add})
    public void OnClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.iv_add:
                count++;
                if(listener!=null){
                    listener.click(count,true,positon);//true 表示数量加
                }

                tv_hour.setText(count+"");
                break;
            case R.id.iv_reduce:
                count--;
                if(count==-1){
                    count=0;
                 }
                if(listener!=null){
                    listener.click(count,false,positon);//false 表示数量减
                }

                tv_hour.setText(count+"");
                break;
        }
    }
    public interface OnClickListener{
        void click(int count, boolean addOrreduce, int positon);
    }
    public  void setAddOrReduceListener(OnClickListener listener , int positon){
        this.listener = listener;
        this.positon = positon;
    }
}
