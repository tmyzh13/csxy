package com.bm.csxy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.csxy.R;


/**
 * 图片选择弹出窗
 * Created by Ryan on 2016/3/8.
 */
public class ChooseImagePopupWindow extends PopupWindow {

    private OnTypeChosenListener listener;

    private View.OnClickListener dismiss = new View.OnClickListener() {
        @Override public void onClick(View v) {
            dismiss();
        }
    };

    public ChooseImagePopupWindow(Context context) {
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_image, null);

        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable(null, (Bitmap) null));
        setAnimationStyle(R.style.anim_style);

        TextView camera = (TextView) view.findViewById(R.id.tv_camera);
        TextView gallery = (TextView) view.findViewById(R.id.tv_gallery);

        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);

        cancel.setOnClickListener(dismiss);
        view.setOnClickListener(dismiss);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) listener.onCamera();
                dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null) listener.onGallery();
                dismiss();
            }
        });
    }

    public void showAtBottom(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void setOnTypeChosenListener(OnTypeChosenListener l) {
        listener = l;
    }

    public interface OnTypeChosenListener {
        void onCamera();
        void onGallery();
    }

}
