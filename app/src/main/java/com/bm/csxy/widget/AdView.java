package com.bm.csxy.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bm.csxy.R;
import com.bumptech.glide.Glide;
import com.corelibs.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播广告
 */
public class AdView extends FrameLayout implements OnClickListener
{

    public static final int SCROLL_SPEED = 4000;

    private ViewPager vp_ads;
    private LinearLayout ll_dots;

    private List<String> ads;
    private List<LinearLayout> images;
    private List<View> dots;

    private int count = 0;
    public int[] ints={R.string.tag_first,R.string.tag_two,R.string.tag_third,R.string.tag_four,R.string.tag_five,R.string.tag_six,
    R.string.tag_seven,R.string.tag_eight,R.string.tag_nine,R.string.tag_ten};

    private Handler handler = new Handler();
    private OnAdClickedListener listener;

    public AdView(Context context)
    {
        super(context);
        init();
    }

    public AdView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    protected void init()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ad, this);

        vp_ads = (ViewPager) view.findViewById(R.id.vp_ads);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
    }

    public void setAds(List<String> ads)
    {
        this.ads = ads;
        initViews();
        initViewPager();
    }


    public void setOnAdClickedListener(OnAdClickedListener listener)
    {
        this.listener = listener;
    }

    private boolean isCenter=false;

    public void setCenter(boolean isCenter){
        this.isCenter=isCenter;
    }

    /**
     * 根据广告数量初始化图片以及小圆点
     */
    private void initViews()
    {
        count = ads.size();

        images = new ArrayList<>();
        dots = new ArrayList<>();
        ll_dots.removeAllViews();
        for (int i = 0; i < count; i++)
        {
            View view = buildChildView();
            ll_dots.addView(view);
            dots.add(view);
        }

        for (int i = 0; i < count; i++)
        {
            View v= LayoutInflater.from(getContext()).inflate(R.layout.item_ad_view,null);


            LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.ll);
            ImageView imageView = (ImageView)v.findViewById(R.id.iv);
            if(isCenter){
                imageView.setScaleType(ScaleType.CENTER_CROP);
            }else{
                imageView.setScaleType(ScaleType.FIT_XY);
            }

            linearLayout.setTag(i);
            linearLayout.setOnClickListener(this);
            Glide.with(getContext()).load(ads.get(i)).into(imageView);
            images.add(linearLayout);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager()
    {
        vp_ads.setAdapter(new RecommendAdAdapter());
        vp_ads.addOnPageChangeListener(new RecommendAdPageChangedListener());
        vp_ads.setCurrentItem(0);
        dots.get(0).setBackgroundResource(R.drawable.dot_focused);
        handler.postDelayed(runnable, SCROLL_SPEED);
    }

    /**
     * 生成小圆点
     */
    private View buildChildView()
    {
        View view = new View(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DisplayUtil.dip2px(getContext(), 8), DisplayUtil.dip2px(getContext(), 8));
        view.setBackgroundResource(R.drawable.dot_normal);
        lp.setMargins(DisplayUtil.dip2px(
                getContext(), 3), 0, DisplayUtil.dip2px(getContext(), 3), 0);
        view.setLayoutParams(lp);

        return view;
    }

    class RecommendAdPageChangedListener implements OnPageChangeListener
    {

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageSelected(int arg0)
        {

            handler.removeCallbacks(runnable);

            int position = arg0 % count;
            for (int i = 0; i < count; i++)
            {
                if (i == position)
                    dots.get(i).setBackgroundResource(R.drawable.dot_focused);
                else
                    dots.get(i).setBackgroundResource(R.drawable.dot_normal);
            }

            handler.postDelayed(runnable, SCROLL_SPEED);
        }

    }

    /**
     * 用于自动轮播的线程体
     */
    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            int current = vp_ads.getCurrentItem();
            vp_ads.setCurrentItem(current + 1);
        }
    };

    /**
     * 用于存储当页面被添加或删除时的position 当页面个数为3的时候, 无限轮播会出现bug,
     * 必须在destroyItem中判断 当Math.abs(add - remove) == 0的时候不删除页面, 不然会出现空页面的情况.
     */
    private int add = 0;

    class RecommendAdAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return count< 3 ? count : Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int i = position % count;
            add = i;
            if (images.get(i).getParent() != null)
            {
                ((ViewPager) images.get(i).getParent()).removeView(images.get(i));
            }
            container.addView(images.get(i));
            return images.get(i);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object arg2)
        {
            int i = position % count;
            if (count == 3 && Math.abs(add - i) == 0)
                return;
            container.removeView(images.get(i));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        handler.postDelayed(runnable, SCROLL_SPEED);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v)
    {
        int position = (int) v.getTag();
        if (listener != null) listener.onAdClicked((LinearLayout) v, position);
    }

    public interface OnAdClickedListener
    {
        void onAdClicked(LinearLayout view, int position);
    }

    public interface IAd
    {
        String getImage();
    }

}
