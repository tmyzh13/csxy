package com.bm.csxy.utils;

import android.os.Handler;
import android.widget.ImageView;

import com.bm.csxy.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by john on 2017/10/29.
 */

public class VoicePlayingBgUtil {

    private Handler handler;
    private ImageView imageView;
    private ImageView lastImageView;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private int i;
    private int modelType=1;//类型
    private int[] leftVoiceBg = new int[] { R.mipmap.gray_1, R.mipmap.gray_2, R.mipmap.gray_3 };


    public VoicePlayingBgUtil(Handler handler) {
        super();
        this.handler = handler;
    }

    public void voicePlay() {
        if (imageView == null) {
            return;
        }
        i = 0;
        timerTask = new TimerTask() {

            @Override
            public void run() {
                if (imageView != null) {

                        changeBg(leftVoiceBg[i % 3], false);

                }
                else {
                    return;
                }
                i++;
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    public void stopPlay() {
        lastImageView = imageView;
        if (lastImageView != null) {
            switch (modelType) {
                case 1:
                    changeBg(R.mipmap.gray_3, true);
                    break;
//                case 2:
//                    changeBg(R.drawable.green3, true);
//                    break;
//                case 3:
//                    changeBg(R.drawable.collect_voice_3, true);
                default:
                    changeBg(R.mipmap.gray_3, true);
                    break;
            }
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    private void changeBg(final int id, final boolean isStop) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isStop) {
                    lastImageView.setImageResource(id);
                }
                else {
                    imageView.setImageResource(id);

                }
            }
        });
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

}
