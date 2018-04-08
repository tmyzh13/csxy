package com.bm.csxy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.BackHomeBean;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.presenter.MainPresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.ChatFragment;
import com.bm.csxy.view.HomeFragment;
import com.bm.csxy.view.MineFragment;
import com.bm.csxy.view.OrderFragmemt;
import com.bm.csxy.view.entery.LoginActivity;
import com.bm.csxy.view.interfaces.MainView;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;
import com.corelibs.views.tab.InterceptedFragmentTabHost;
import com.corelibs.views.tab.TabChangeInterceptor;
import com.corelibs.views.tab.TabNavigator;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity<MainView,MainPresenter> implements MainView, TabNavigator.TabNavigatorContent {

    @Bind(android.R.id.tabhost)
    InterceptedFragmentTabHost tabHost;


    private TabNavigator navigator = new TabNavigator();
    private String[] tabTags;
    private Context context=MainActivity.this;
    private int bgRecourse[] = new int[]{
            R.drawable.tab_home,
            R.drawable.tab_msg,
            R.drawable.tab_order,
            R.drawable.tab_mine
    };
    public String currentTabId="首页";

    public static Intent getLauncher(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabTags = new String[]{getString(R.string.tab_home), getString(R.string.tab_msg),getString(R.string.tab_order),
                getString(R.string.tab_mine)};

        navigator.setup(this, tabHost, this, getSupportFragmentManager(), R.id.real_tab_content);

        navigator.setTabChangeInterceptor(new TabChangeInterceptor() {
            @Override
            public boolean canTab(String tabId) {
                if(tabId.equals("我的")){
                    if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
                        startActivity(LoginActivity.getLauncher(context));
                        return false;
                    }
                }
                if(tabId.equals("订单")){
                    if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
                        startActivity(LoginActivity.getLauncher(context));
                        return false;
                    }
                }
                if(tabId.equals("消息")){
                    if(Tools.isNull(PreferencesHelper.getData(Constant.LOGIN))){
                        startActivity(LoginActivity.getLauncher(context));
                        return false;
                    }else{
                        RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE,"customId"+UserHelper.getSavedUser().customId,currentTabId);
                    }
                }
                currentTabId=tabId;
                return true;
            }

            @Override
            public void onTabIntercepted(String tabId) {
            }
        });

        RxBus.getDefault().toObservable(Object.class, Constant.EXIT_LOGIN)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        tabHost.setCurrentTab(0);
                    }
                });
        RxBus.getDefault().toObservable(Object.class, Constant.ORDER_SUCCESS)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        tabHost.setCurrentTab(2);
                    }
                });
        RxBus.getDefault().toObservable(BackHomeBean.class, Constant.CHAT_TO_HOME)
                .compose(this.<BackHomeBean>bindToLifecycle())
                .subscribe(new RxBusSubscriber<BackHomeBean>() {
                    @Override
                    public void receive(BackHomeBean data) {
                        if(data.backStyle.equals("首页")){
                            currentTabId="首页";
                            tabHost.setCurrentTab(0);
                        }else if(data.backStyle.equals("消息")){
                            currentTabId="消息";
                            tabHost.setCurrentTab(1);
                        }else if(data.backStyle.equals("订单")){
                            currentTabId="订单";
                            tabHost.setCurrentTab(2);
                        }else if(data.backStyle.equals("我的")){
                            currentTabId="我的";
                            tabHost.setCurrentTab(3);
                        }

                    }
                });
        if(UserHelper.getSavedUser()!=null&&!Tools.isNull(UserHelper.getSavedUser().rongyunToken)){
            RongIM.connect(UserHelper.getSavedUser().rongyunToken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    hideLoading();
                    ToastMgr.show("聊天信息获取失败");
                }

                @Override
                public void onSuccess(String s) {
                    Log.e("yzh","--onstring--"+s);
                    String iconUrl="";
                    if(UserHelper.getSavedUser().headerUrl.contains("http")||UserHelper.getSavedUser().headerUrl.contains("https")){
                        iconUrl=UserHelper.getSavedUser().headerUrl;
                    }else{
                        iconUrl=Urls.ROOT_IMG+UserHelper.getSavedUser().headerUrl;
                    }
                    UserInfo userInfo=new UserInfo("userId"+UserHelper.getSavedUser().id, UserHelper.getSavedUser().userNickname, Uri.parse(iconUrl));
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
//                    hideLoading();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    hideLoading();
                    ToastMgr.show("聊天信息获取失败");
                }
            });
        }

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {

                Log.e("yzh","--userId="+userId);

                if(userId.contains("userId")){
                    //用户id
                    presenter.getUserId(userId.replace("userId",""),userId);
                }else if(userId.contains("customId")){
                    //客服id
                    presenter.getCustomId(userId.replace("customId",""),userId);
                }
                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
    }

    @Override
    public View getTabView(int position) {
        View view = getLayoutInflater().inflate(R.layout.view_tab_content, null);

        ImageView icon = (ImageView) view.findViewById(R.id.iv_tab_icon);
        TextView text = (TextView) view.findViewById(R.id.tv_tab_text);

        icon.setImageResource(bgRecourse[position]);
        text.setText(tabTags[position]);
        return view;
    }

    @Override
    public Bundle getArgs(int position) {
        return null;
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[]{HomeFragment.class, ChatFragment.class, OrderFragmemt.class,MineFragment.class};
    }

    @Override
    public String[] getTabTags() {
        return tabTags;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    /**
     * 物理返回键拦截
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doublePressBackToast();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 双击两次返回键退出应用
     */
    private boolean isBackPressed = false;//判断是否已经点击过一次回退键

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            showToast("再次点击返回退出程序");
        } else {
            AppManager.getAppManager().finishAllActivity();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

    @Override
    public void renderData(UserBean bean, String rongId) {
        UserInfo userInfo=new UserInfo(rongId,bean.name, Uri.parse(Urls.ROOT_IMG+bean.headerUrl));
//                RongIM.getInstance().setCurrentUserInfo(userInfo);
        RongIM.getInstance().refreshUserInfoCache(userInfo);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();
    }
}
