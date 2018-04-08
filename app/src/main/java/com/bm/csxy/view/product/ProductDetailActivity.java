package com.bm.csxy.view.product;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bm.csxy.R;
import com.bm.csxy.adapters.ProductEvaluationAdapter;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.ProductEvaluationBean;
import com.bm.csxy.model.bean.TravelBean;
import com.bm.csxy.presenter.ProductDetailPresenter;
import com.bm.csxy.presenter.ProductPresenter;
import com.bm.csxy.utils.ImageTextUtil;
import com.bm.csxy.utils.IntentUtil;
import com.bm.csxy.utils.MusicPlayer;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.utils.VoicePlayingBgUtil;
import com.bm.csxy.view.entery.LoginActivity;
import com.bm.csxy.view.interfaces.ProductDetailView;
import com.bm.csxy.view.interfaces.ProductView;
import com.bm.csxy.widget.NavBar;
import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.NoScrollingListView;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by john on 2017/11/7.
 */

public class ProductDetailActivity extends BaseActivity<ProductDetailView,ProductDetailPresenter> implements ProductDetailView, MediaPlayer.OnCompletionListener, PtrLollipopLayout.RefreshCallback {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.lv_evaluation)
    NoScrollingListView lv_evaluation;
    @Bind(R.id.tv_travel_see)
    WebView tv_travel_see;
    @Bind(R.id.tv_play)
    TextView tv_play;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.iv_voice)
    ImageView iv_voice;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.tv_product_name)
    TextView tv_product_name;
    @Bind(R.id.tv_product_content)
    TextView tv_product_content;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_product_days)
    TextView tv_product_days;
    @Bind(R.id.tv_voice_name)
    TextView tv_voice_name;
    @Bind(R.id.tv_customer_phone)
    TextView tv_customer_phone;
    @Bind(R.id.tv_customer_time)
    TextView tv_customer_time;
    @Bind(R.id.tv_customer_wechat)
    TextView tv_customer_wechat;
    @Bind(R.id.tv_product_score)
    TextView tv_product_score;
    @Bind(R.id.tv_evaluation_nums)
    TextView tv_evaluation_num;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.ptr_layout)
    PtrLollipopLayout ptrLayout;

    private Context context=ProductDetailActivity.this;
    private String id;
    private String title;
    private ProductEvaluationAdapter evaluationAdapter;
    private MusicPlayer musicPlayer;
    private VoicePlayingBgUtil voicePlayingBgUtil;
    private Timer timer;
    private TimerTask timerTask;
    boolean isStart=false;

    private boolean isStartTime=false;

    public static Intent getLauncher(Context context, String id, String title){
        Intent intent =new Intent(context,ProductDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        return intent;
    }
    private int currentPosition=0;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            if(msg.what == 100) {
                //设置进度条当前的完成进度
                progressBar.setProgress(currentPosition);
                tv_start_time.setText(Tools.formatTime(currentPosition));
            }
        }

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id=getIntent().getStringExtra("id");
        title=getIntent().getStringExtra("title");
        navBar.setTitle(title);

        voicePlayingBgUtil=new VoicePlayingBgUtil(new Handler());
        voicePlayingBgUtil.setImageView(iv_voice);

        musicPlayer=new MusicPlayer(context);
        musicPlayer.mediaPlayer.setOnCompletionListener(this);
        musicPlayer.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                showLoading();
                Constant.MUSIC_POSITION=0;
                Log.e("yzh","OnErrorListener");
                tv_voice_name.setText("郎在高山打一望");
                musicPlayer.setMusicData("music1.mp3");
//                musicPlayer.play();
                isStart=true;
                return false;
            }
        });

        timer=new Timer();

        timerTask=new TimerTask() {
            @Override
            public void run() {
                currentPosition = musicPlayer.mediaPlayer.getCurrentPosition();
                mHandler.sendEmptyMessage(100);
                    }
            };
        musicPlayer.setGetTimeListener(new MusicPlayer.GetTimeListener() {
            @Override
            public void getTime(int time) {
                //设置媒体音乐时长
                hideLoading();
                progressBar.setMax(time);
                int tempPosition=0;
                if(Constant.MUSIC_ID.equals(bean.id)){
                    //记录的上一个缓存
                    tempPosition=Constant.MUSIC_POSITION;
                }else{
                    tempPosition=0;
                }
                musicPlayer.mediaPlayer.seekTo(tempPosition);
                tv_start_time.setText(Tools.formatTime(tempPosition));
                progressBar.setProgress(tempPosition);
                tv_end_time.setText(Tools.formatTime(time));
            }
        });
        presenter.getProductDetail(id);
//        ptrLayout.setRefreshCallback(this);
        ptrLayout.setCanRefresh(false);
    }

    @Override
    protected ProductDetailPresenter createPresenter() {
        return new ProductDetailPresenter();
    }

    private TravelBean bean ;

    @Override
    public void renderData(TravelBean bean) {
        ptrLayout.complete();
        this.bean=bean;
        navBar.setTitle(bean.scenicname);
        musicPlayer.playUrl(Urls.ROOT_IMG+bean.songurl);
        initEvaluationAdapter(bean.scenicCommentList);

        Glide.with(context).load(Urls.ROOT_IMG+bean.scenicpic).into(iv_photo);
        ratingBar.setRating(Float.parseFloat(bean.gradeSum));
        tv_product_score.setText(bean.gradeSum);
        tv_evaluation_num.setText(bean.commentNum);
        tv_product_name.setText(bean.scenicname);
        tv_product_content.setText(bean.scenicdesc);
        tv_price.setText(bean.price+"");
        tv_product_days.setText(bean.timelength);
        tv_voice_name.setText(bean.songname);
        if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
            tv_customer_wechat.setText(bean.customweixin);
            tv_customer_phone.setText(bean.customphone);
        }else{
            tv_customer_wechat.setText(UserHelper.getSavedUser().customWeixin);
            tv_customer_phone.setText(UserHelper.getSavedUser().customPhone);
        }

//        if(!Tools.isNull(bean.scenicroute)){
//            ImageTextUtil.setImageText(tv_travel_see,bean.scenicroute);
//        }
        if(!Tools.isNull(bean.scenicstrategy)){
            tv_travel_see.getSettings().setJavaScriptEnabled(true);
//            tv_travel_see.getSettings().setUseWideViewPort(true);
//            tv_travel_see.getSettings().setLoadWithOverviewMode(true);
            tv_travel_see.loadDataWithBaseURL(Urls.ROOT_IMG, bean.scenicstrategy, "text/html" , "utf-8", null);
            tv_travel_see.setWebViewClient(new MyWebViewClient());
        }
    }
    //设置webview代理加载图片
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void imgReset() {
        tv_travel_see.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()");
    }
    @Override
    public void getDataFail() {
        finish();
    }

    @OnClick(R.id.ll_voice)
    public void actionVoice(){
        if(isStart){
            isStart=false;
            voicePlayingBgUtil.stopPlay();
            musicPlayer.musicStop();
            Constant.MUSIC_ID=bean.id;
            Constant.MUSIC_POSITION=musicPlayer.mediaPlayer.getCurrentPosition();
        }else{
            isStart=true;
            voicePlayingBgUtil.voicePlay();
            musicPlayer.musicPlay();
            if(!isStartTime){
                timer.schedule(timerTask, 10,1000);
                isStartTime=true;
            }
//
        }

    }

    private void initEvaluationAdapter(List<ProductEvaluationBean> list){
        evaluationAdapter=new ProductEvaluationAdapter(context);
        if(list!=null||list.size()!=0)
            evaluationAdapter.addAll(list);
        lv_evaluation.setAdapter(evaluationAdapter);
    }

    @OnClick(R.id.ll_evaluation)
    public void goEvaluation(){
        startActivity(ProductEvaluationActivity.getLauncher(context,id,bean.scenicname));
    }

    @OnClick(R.id.ll_baoming)
    public void goBoming(){
//        musicPlayer.mediaPlayer.pause();
        if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
            startActivity(LoginActivity.getLauncher(context));
        }else{
            if(bean!=null)
                startActivity(ChoiceProductActivity.getLuancher(context,bean));
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (musicPlayer.mediaPlayer.isPlaying()) {
            Constant.MUSIC_ID = bean.id;
            Constant.MUSIC_POSITION = musicPlayer.mediaPlayer.getCurrentPosition();
        }
        musicPlayer.pause();
        voicePlayingBgUtil.stopPlay();
        isStart=false;
    }
    String phone="";
    @OnClick({R.id.ll_phone,R.id.tv_customer_phone})
    public void goPhone(){

        if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
            //未登录
            phone=bean.customphone;
        }else{
            phone=UserHelper.getSavedUser().customPhone;
        }
        if(Tools.isNull(phone)){
            ToastMgr.show("暂无电话");
        }else{
            new MaterialDialog.Builder(context)
                    .title("提示").content("呼叫"+phone)
                    .positiveText("确定")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            IntentUtil.tell(context,phone/*.substring(5).replace("-", "").trim()*/);
                        }
                    }).show();
        }

    }

    private String customerId="";
    @OnClick(R.id.ll_customer)
    public void goCustomer(){
        if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
            //未登录
            customerId=bean.customId;
        }else{
            customerId=UserHelper.getSavedUser().customId;
        }
        RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE,"customId"+customerId,"1");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        voicePlayingBgUtil.stopPlay();
        isStart=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(musicPlayer.mediaPlayer.isPlaying()){
            //还在播放记录一下播放时间
            Constant.MUSIC_ID=bean.id;
            Constant.MUSIC_POSITION=musicPlayer.mediaPlayer.getCurrentPosition();
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
        if (mHandler != null) {
            mHandler.removeMessages(100);
            mHandler = null;
        }
        musicPlayer.stop();
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        presenter.getProductDetail(id);
    }
}
