package com.bm.csxy.view.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bm.csxy.R;
import com.bm.csxy.adapters.ProductAdapter;
import com.bm.csxy.model.bean.TravelBean;
import com.bm.csxy.presenter.ProductPresenter;
import com.bm.csxy.view.interfaces.ProductView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by john on 2017/11/7.
 */

public class ProductActivity extends BaseActivity<ProductView,ProductPresenter> implements ProductView, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.lv_product)
    AutoLoadMoreListView lv_product;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private  Context context=ProductActivity.this;
    private String id;
    private ProductAdapter adapter;

    public static Intent getLuancher(Context context,String id){
        Intent intent =new Intent(context,ProductActivity.class);
        intent.putExtra("id",id);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("产品列表");
        id=getIntent().getStringExtra("id");
        adapter=new ProductAdapter(context);
        lv_product.setAdapter(adapter);
        presenter.getProduct(true,id);
        ptrLayout.setRefreshLoadCallback(this);

      lv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ProductDetailActivity.getLauncher(context,adapter.getData().get(i).id,
                        adapter.getData().get(i).scenicname));
          }
      });
    }

    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter();
    }

    @Override
    public void renderProducts(boolean reload, List<TravelBean> list) {
        if(reload)
            adapter.replaceAll(list);
        else
            adapter.addAll(list);
    }

    @Override
    public void showLoading() {
        ptrLayout.setRefreshing();
    }

    @Override
    public void hideLoading() {
        ptrLayout.complete();
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
        ptrLayout.disableLoading();
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        presenter.getProduct(false,id);
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        if(!frame.isAutoRefresh()){
            ptrLayout.enableLoading();
            presenter.getProduct(true,id);
        }
    }
}
