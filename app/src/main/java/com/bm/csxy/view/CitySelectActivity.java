package com.bm.csxy.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bm.csxy.R;
import com.bm.csxy.adapters.MailListAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.model.bean.CityBean;
import com.bm.csxy.presenter.CitySelectPresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.CitySelectView;
import com.bm.csxy.widget.MyLetterView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.cityselect.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by john on 2017/11/8.
 */

public class CitySelectActivity extends BaseActivity<CitySelectView,CitySelectPresenter> implements CitySelectView, MyLetterView.OnTouchingLetterChangedListener {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seacher)
    EditText et_seacher;
    @Bind(R.id.iv_canecl)
    ImageView iv_cancel;
    @Bind(R.id.lv_passenage)
    ListView lv_cities;
    @Bind(R.id.letter_view)
    MyLetterView letter_view;


    private Context context =CitySelectActivity.this;
    private MailListAdapter adapter;
    /**
     *存储字母索引的哈希map
     */
    private HashMap<String,Integer> alphaIndex;

    private Handler mHandler;
    private SearchTask mSearchTesk;
    private String searchContent="";
    private List<CityBean> list;
    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,CitySelectActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_select;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("城市选择");
        presenter.getCity();
        letter_view.setOnTouchingLetterChangedListener(this);
        mHandler=new Handler();
        mSearchTesk=new SearchTask();

        et_seacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 0) {
                    searchContent=charSequence.toString();
                    mHandler.removeCallbacks(mSearchTesk);
                    mHandler.postDelayed(mSearchTesk,500);
                } else {
                    searchContent="";
                    mHandler.removeCallbacks(mSearchTesk);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Tools.isNull(editable.toString())){
                    iv_cancel.setVisibility(View.VISIBLE);

                }else{
                    iv_cancel.setVisibility(View.GONE);
                }
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_seacher.setText("");
                adapter.setList(list);
            }
        });
        lv_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityBean bean=adapter.getItem(i);
                PreferencesHelper.saveData(Constant.CITY_NAME,bean.regionName);
                PreferencesHelper.saveData(Constant.CITY_ID,bean.regionId);
                RxBus.getDefault().send(Object.class,Constant.REFRESH_HOME_CITY);
                finish();
            }
        });
    }

    @Override
    protected CitySelectPresenter createPresenter() {
        return new CitySelectPresenter();
    }

    @Override
    public void renderCitys(List<CityBean> list) {
        for(int i=0;i<list.size();i++){
            CityBean bean =list.get(i);
            bean.enName=Tools.getHanyuPinyin(bean.regionName);
        }
        this.list=list;
        Collections.sort(list,new CityComparator());
        adapter=new MailListAdapter(context,list);
        lv_cities.setAdapter(adapter);
        alphaIndex=adapter.getAlphaIndex();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if(alphaIndex.get(s)!=null){
            int position=alphaIndex.get(s);
            lv_cities.setSelection(position);
        }
    }

    /**
     * 搜索任务
     */
    class SearchTask implements Runnable {

        @Override
        public void run() {
            Log.e("---SearchTask---","开始查询");
            getSeacherResult(searchContent);
        }
    }
    public void getSeacherResult(String searchContent){
        List<CityBean> listTemp=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).regionName.contains(searchContent.toUpperCase())||list.get(i).enName.contains(searchContent.toUpperCase())){
                listTemp.add(list.get(i));
            }
        }
        adapter.setList(listTemp);
    }

    class CityComparator implements Comparator<CityBean> {

        @Override
        public int compare(CityBean o1, CityBean o2) {
            String lhsString =o1.enName.substring(0,1).toLowerCase();
            String rhsString=o2.enName.substring(0,1).toLowerCase();
            return lhsString.compareTo(rhsString);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mSearchTesk);
    }
}
