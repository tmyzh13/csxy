package com.bm.csxy.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.bm.csxy.R;
import com.bm.csxy.model.bean.SexBean;
import com.bm.csxy.utils.Tools;

import java.util.ArrayList;

;

/**
 * Created by john on 2017/4/6.
 */

public class TitlePopup extends PopupWindow {

    private Context mContext;
    //列表弹框的间隔
    public final int LIST_PADDING=0;
    //实例化一个矩形
    private Rect mRect=new Rect();
    //坐标的位置（x,y)
    private final int[] mLocation=new int[2];
    //屏幕的宽度和高度
    private int mScreenWidth,mScreenHeight;
    //判断是否需要添加或更新列表子类项
    private boolean mIsDirty;
    //位置不在中心
    private int popupGravity = Gravity.NO_GRAVITY;

    private OnItemOnClickListener mItemOnClickListener;
    private ListView mListView;
   //定义弹窗子类列表
    private ArrayList<SexBean> mActionItems =new ArrayList<>();

    public TitlePopup(Context context){
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public TitlePopup(Context context, int width, int height){
        this.mContext =context;

        //设置可以获得焦点
//        setFocusable(true);
        //设置弹窗内可点击
        setTouchable(true);
        //设置弹窗外可点击
        setOutsideTouchable(true);

        //获得屏幕的宽度和高度
        mScreenWidth = Tools.getScreenWidth(mContext);
        mScreenHeight= Tools.getScreenHeight(mContext);

        //设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.title_popup,null));
        initUI();
    }

    private void initUI(){
        mListView=(ListView)getContentView().findViewById(R.id.title_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if(mItemOnClickListener!=null){
                    mItemOnClickListener.onItemClick(mActionItems.get(position),position);
                }
            }
        });
    }

    public void show(View view){
        view.getLocationOnScreen(mLocation);

        mRect.set(mLocation[0],mLocation[1],mLocation[0]+view.getWidth(),mLocation[1]+view.getHeight());
        if(mIsDirty){
            populateActions();
        }
        int mx=mScreenWidth-Tools.dip2px(mContext,125);
        //显示弹窗的位置
        showAtLocation(view,popupGravity,mLocation[0],mRect.bottom);

    }

    private void populateActions(){
        mIsDirty=false;

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mActionItems.size();
            }

            @Override
            public Object getItem(int position) {
                return mActionItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView =null;
                if(convertView==null){
                    textView=new TextView(mContext);
                    textView.setTextColor(mContext.getResources().getColor(R.color.black));
                    textView.setTextSize(14);
                    //设置文本居中
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(0,10,0,10);
                    textView.setSingleLine(true);
                }else{
                    textView =(TextView)convertView;
                }

                String string =mActionItems.get(position).name;
                textView.setText(string);
                return textView;
            }
        });
    }

    public void addAction(SexBean action){
        if(action!=null){
            mActionItems.add(action);
            mIsDirty=true;
        }
    }

    public void clearAction(){
        if(mActionItems.isEmpty()){
            mActionItems.clear();
            mIsDirty=true;
        }
    }

    public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener){
        this.mItemOnClickListener=onItemOnClickListener;
    }

    public static interface OnItemOnClickListener{
        public void onItemClick(SexBean s, int position);
    }
}
