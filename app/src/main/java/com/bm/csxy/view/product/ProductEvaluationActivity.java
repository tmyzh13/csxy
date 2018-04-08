package com.bm.csxy.view.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bm.csxy.R;
import com.bm.csxy.adapters.ProductEvaluationAdapter;
import com.bm.csxy.model.bean.EvaluationListBean;
import com.bm.csxy.model.bean.ProductEvaluationBean;
import com.bm.csxy.presenter.ProductEvaluationPresenter;
import com.bm.csxy.view.interfaces.ProductEvaluationView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by john on 2017/11/7.
 */

public class ProductEvaluationActivity extends BaseActivity<ProductEvaluationView,ProductEvaluationPresenter> implements ProductEvaluationView, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.ptrlayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrlayout;
    @Bind(R.id.lv_evaluation)
    AutoLoadMoreListView lv_evaluation;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tv_score)
    TextView tv_score;
    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_product_name)
    TextView tv_product_name;

    private Context context=ProductEvaluationActivity.this;
    private String id;
    private String title;
    private ProductEvaluationAdapter adapter;

    public static Intent getLauncher(Context context,String id,String title){
        Intent intent=new Intent(context,ProductEvaluationActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_evaluation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("产品评价");
        id=getIntent().getStringExtra("id");
        title=getIntent().getStringExtra("title");
        tv_product_name.setText(title);

        adapter=new ProductEvaluationAdapter(context);
        lv_evaluation.setAdapter(adapter);
        presenter.getEvaluationList(true,id);
        ptrlayout.setRefreshLoadCallback(this);
    }

    @Override
    protected ProductEvaluationPresenter createPresenter() {
        return new ProductEvaluationPresenter();
    }

    @Override
    public void showLoading() {
        ptrlayout.setRefreshing();
    }

    @Override
    public void hideLoading() {
        ptrlayout.complete();
    }

    @Override
    public void renderData(boolean reload, List<ProductEvaluationBean> bean,String gradeSum,String count) {
        if(reload){
            tv_score.setText(gradeSum);
            ratingBar.setRating(Float.parseFloat(gradeSum));
            tv_num.setText(count);
            adapter.replaceAll(bean);
        }else{
            adapter.addAll(bean);
        }
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
        ptrlayout.disableLoading();
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        presenter.getEvaluationList(true,id);
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        if(!frame.isAutoRefresh()){
            ptrlayout.enableLoading();
            presenter.getEvaluationList(true,id);
        }
    }
}
