package com.bm.csxy.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bm.csxy.R;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.bean.CityBean;
import com.bumptech.glide.Glide;
import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by john on 2017/10/27.
 */

public class MailListAdapter extends QuickAdapter<CityBean> {


    public List<CityBean> list;
    private final int VIEW_TYPE=1;
    private String current,prevous,next;
    /**
     * 存储字母索引表
     */
    private HashMap<String,Integer> alphaIndex;

    public MailListAdapter(Context context, List<CityBean> list){
        super(context, R.layout.item_mail_list);
        addAll(list);
        this.list=list;
        alphaIndex=new HashMap<>();
        for(int i=0;i<list.size();i++){
            if(i==0){
                alphaIndex.put(getFirstLetter(list.get(i).enName),i);
            }else{
                current =getFirstLetter(list.get(i).enName);
                prevous=getFirstLetter(list.get(i-1).enName);

                if(!current.equals(prevous)){
                    alphaIndex.put(current,i);
                }
            }
        }
    }

    public List<CityBean> getList(){
        return list;
    }

    public void setList(List<CityBean> list){
        this.list=list;
        replaceAll(list);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CityBean item, int position) {
        TextView alphaTextView=helper.getView(R.id.alpha);
        View divider=helper.getView(R.id.divider);

        helper.setText(R.id.tv_name,item.regionName);

        if(position<getCount()-1){
            next=getFirstLetter(list.get(position+1).enName);
        }

        if(position==0){
            alphaTextView.setText(getFirstLetter(list.get(position).enName));
            alphaTextView.setVisibility(View.VISIBLE);
        }else{
            current=getFirstLetter(list.get(position).enName);
            prevous=getFirstLetter(list.get(position-1).enName);
            if(!current.equals(prevous)){
                alphaTextView.setText(current);
                alphaTextView.setVisibility(View.VISIBLE);
            }else{
                alphaTextView.setVisibility(View.GONE);
            }
        }
        if(!current.equals(next)){
            divider.setVisibility(View.GONE);
        }else{
            divider.setVisibility(View.VISIBLE);
        }
    }

    private String getFirstLetter(String str){
        char c=str.trim().substring(0,1).charAt(0);
        return (c+"").toUpperCase();
    }

    public HashMap<String,Integer> getAlphaIndex(){
        if(alphaIndex!=null){
            return alphaIndex;
        }else{
            return null;
        }
    }
}
