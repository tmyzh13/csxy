package com.bm.csxy.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bm.csxy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 标题栏
 * Created by Ryan on 2016/5/9.
 */
public class NavBar extends FrameLayout {

    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_stutas_color)
    LinearLayout llStutasColor;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.iv_seacher)
    ImageView iv_seacher;
    @Bind(R.id.tv_city)
    TextView tv_city;


    private final OnClickListener defaultBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
        }
    };

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_nav, this);
        ButterKnife.bind(this, this);

        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(defaultBackListener);
//        initSystemBarWithImmerged();
    }

    /**
     * 侵入状态栏
     */
    public void initSystemBarWithImmerged() {
//        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) llStutasColor.getLayoutParams();
//        layoutParams.height = StatusBarUtil.getStatusbarHeigth((Activity) getContext());
//        llStutasColor.setLayoutParams(layoutParams);
    }

    /**
     * 设置返回监听, 默认的返回监听是结束Activity
     **/
    public void setOnBackClickedListener(OnClickListener listener) {
        ivBack.setVisibility(VISIBLE);
        ivBack.setOnClickListener(listener);
    }

    /**
     * 隐藏返回按钮
     **/
    public void hideBack() {
        ivBack.setVisibility(GONE);
    }

    /**
     * 设置标题
     **/
    public void setTitle(String title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     **/
    public void setTitle(int title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
    }

    /**
     * 隐藏左边返回图标
     */
    public void hintLeftBackIcon() {
        ivBack.setVisibility(View.GONE);
    }






    /**
     * 设置标题栏背景
     **/
    public void setNavBackground(int res) {
        ivBg.setImageResource(res);
        ivBg.setVisibility(GONE);
    }

    public ImageView getTitleBg(){
        return ivBg;
    }

    /**
     * 将标题栏设置为透明
     **/
    public void setNavTransparent() {
        setNavBackground(R.color.transparent);
    }



//    /*
//      显示搜索框
//     */
//    public void showEditSearch() {
//        tvTitle.setVisibility(GONE);
//        llFakeEditSearch.setVisibility(View.VISIBLE);
//    }


    public void setRightText(String text,OnClickListener listener){
        tv_right.setVisibility(VISIBLE);
        tv_right.setText(text);
        tv_right.setOnClickListener(listener);
    }

    public void setRightTextGone(){
        tv_right.setVisibility(GONE);
    }

    public void setSeacherAction(OnClickListener listener){
        iv_seacher.setVisibility(VISIBLE);
        iv_seacher.setOnClickListener(listener);
    }

    public void setCityText(String text,OnClickListener listener){
        tv_city.setVisibility(VISIBLE);
        tv_city.setText(text);
        tv_city.setOnClickListener(listener);
    }

    public void setCity(String text){
        tv_city.setVisibility(VISIBLE);
        tv_city.setText(text);
    }

}
