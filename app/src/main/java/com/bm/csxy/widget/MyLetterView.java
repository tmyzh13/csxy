package com.bm.csxy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bm.csxy.R;


/**
 * Created by john on 2017/4/1.
 */

public class MyLetterView extends View {

    private Context context;
    public String b[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public Paint paint =new Paint();
    /**
     * 触摸字母监听
     */
    OnTouchingLetterChangedListener listener;
    /**
     * 控制是否显示背景颜色
     */
    public boolean showBg=false;

    public MyLetterView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        this.context=context;
    }

    public MyLetterView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context =context;
    }

    public MyLetterView(Context context){
        super(context);
        this.context =context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0x00000000);
        int height=getHeight();
        int width=getWidth();
        int singleHeight=height/b.length;

        for(int i=0;i<b.length;i++){
            paint.setColor(context.getResources().getColor(R.color.main_bg));
            paint.setTypeface(Typeface.DEFAULT);
            paint.setTextSize(25);
            paint.setAntiAlias(true);

            float xPos=width/2-paint.measureText(b[i])/2;//算出字母的x坐标
            float yPos=(i+1)*singleHeight;//算出字母的Y坐标

            canvas.drawText(b[i],xPos,yPos,paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action=event.getAction();
        final float yPos=event.getY();
        final int currentLetterIndex =(int)yPos*b.length/getHeight();//算出当前选中的字母的位置
        final OnTouchingLetterChangedListener l=listener;

        switch(action){
            case MotionEvent.ACTION_DOWN:
                showBg=false;
                if(currentLetterIndex>=0&&currentLetterIndex<b.length){
                    l.onTouchingLetterChanged(b[currentLetterIndex]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentLetterIndex>=0&&currentLetterIndex<b.length){
                    l.onTouchingLetterChanged(b[currentLetterIndex]);
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg=false;
                invalidate();
                break;
            default:
                break;
        }

        return true;
    }

    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener l){
        this.listener=l;
    }

}
