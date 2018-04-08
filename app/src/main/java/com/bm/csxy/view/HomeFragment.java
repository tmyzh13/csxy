package com.bm.csxy.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.bm.csxy.R;
import com.bm.csxy.adapters.TypeAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.AdvertiseBean;
import com.bm.csxy.model.bean.TypeBean;
import com.bm.csxy.presenter.HomePresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.HomeView;
import com.bm.csxy.view.product.ProductActivity;
import com.bm.csxy.view.product.ProductDetailActivity;
import com.bm.csxy.widget.AdView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.NoScrollingGridView;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by john on 2017/11/7.
 */

public class HomeFragment extends BaseFragment<HomeView,HomePresenter> implements HomeView, PtrLollipopLayout.RefreshCallback {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.v_ad)
    AdView v_ad;
    @Bind(R.id.gv_type)
    NoScrollingGridView gv_type;
    @Bind(R.id.ptrLayout)
    PtrLollipopLayout ptrLayout;

    private TypeAdapter adapter;
    private String defultCityId="2126";
    private String defaultCity="张家界市";
    private List<String> list_img=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle(getString(R.string.tab_home));
        nav.hideBack();
        if(Tools.isNull(PreferencesHelper.getData(Constant.CITY_NAME))){
            PreferencesHelper.saveData(Constant.CITY_NAME,"张家界市");
            PreferencesHelper.saveData(Constant.CITY_ID,"2126");

        }

        nav.setCityText(PreferencesHelper.getData(Constant.CITY_NAME), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //城市选择页面
                startActivity(CitySelectActivity.getLauncher(getContext()));
            }
        });

        adapter=new TypeAdapter(getContext());
        gv_type.setAdapter(adapter);

        ptrLayout.setRefreshCallback(this);
        presenter.getHomeType(PreferencesHelper.getData(Constant.CITY_ID));
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510046704197&di=bd2c98cb86be90b7784995da91c41e40&imgtype=0&src=http%3A%2F%2Fwww.dfgjgh.com%2Fuploads%2Fallimg%2F140522%2F1-14052210364a44.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510046723712&di=81428b2dad0b437de7da8df119a46e66&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ffd%2Ftg%2Fg3%2FM06%2F95%2F09%2FCggYGlXq-F-AGGFAAANAuQpZouw592.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510046723708&di=7eb2f5f7b38878cfab0558adf5876261&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb151f8198618367a17597fe725738bd4b31ce53c.jpg");
//        v_ad.setAds(list);
        v_ad.setOnAdClickedListener(new AdView.OnAdClickedListener() {
            @Override
            public void onAdClicked(LinearLayout view, int position) {
                startActivity(ProductDetailActivity.getLauncher(getContext(),list.get(position).relationId,""));
            }
        });
        ptrLayout.disableWhenHorizontalMove(true);
        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ProductActivity.getLuancher(getContext(),adapter.getData().get(i).id));
            }
        });
        presenter.getAds();

        RxBus.getDefault().toObservable(Object.class, Constant.REFRESH_HOME_CITY)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        nav.setCity(PreferencesHelper.getData(Constant.CITY_NAME));
                        presenter.getHomeType(PreferencesHelper.getData(Constant.CITY_ID));
                    }
                });
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    private List<AdvertiseBean> list;

    @Override
    public void rendAds(List<AdvertiseBean> list) {
        this.list=list;
        list_img=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            list_img.add(Urls.ROOT_IMG+list.get(i).imgUrl);
        }
        v_ad.setAds(list_img);
    }

    @Override
    public void rendTypes(List<TypeBean> list) {
        adapter.replaceAll(list);
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
    public void onRefreshing(PtrFrameLayout frame) {
        if(!frame.isAutoRefresh()){
            presenter.getHomeType(defultCityId);
            presenter.getAds();
        }

    }
}
