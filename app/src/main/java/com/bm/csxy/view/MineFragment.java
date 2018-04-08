package com.bm.csxy.view;

import android.os.Bundle;

import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.view.entery.LoginActivity;
import com.bm.csxy.view.mine.AlterPassword;
import com.bm.csxy.view.mine.UserInfoActivity;
import com.bm.csxy.widget.CircleImageView;
import com.bm.csxy.widget.NavBar;
import com.bumptech.glide.Glide;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by john on 2017/11/7.
 */

public class MineFragment extends BaseFragment {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_user_icon)
    CircleImageView iv_user_icon;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("个人中心");
        nav.hideBack();

        if(UserHelper.getSavedUser()!=null){
            if(UserHelper.getSavedUser().headerUrl.contains("http")||UserHelper.getSavedUser().headerUrl.contains("https")){
                Glide.with(getContext()).load(UserHelper.getSavedUser().headerUrl).override(200,200).into(iv_user_icon);
            }else{
                Glide.with(getContext()).load(Urls.ROOT_IMG+UserHelper.getSavedUser().headerUrl).override(200,200).into(iv_user_icon);
            }

        }

        RxBus.getDefault().toObservable(Object.class, Constant.REFRESH_ICON)
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        if(UserHelper.getSavedUser().headerUrl.contains("http")||UserHelper.getSavedUser().headerUrl.contains("https")){
                            Glide.with(getContext()).load(UserHelper.getSavedUser().headerUrl).override(200,200).into(iv_user_icon);
                        }else{
                            Glide.with(getContext()).load(Urls.ROOT_IMG+UserHelper.getSavedUser().headerUrl).override(200,200).into(iv_user_icon);
                        }
                    }
                });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.mine_exit)
    public void exit(){
        PreferencesHelper.saveData(Constant.LOGIN,"");
        RongIM.getInstance().logout();
        startActivity(LoginActivity.getLauncher(getContext()));
        RxBus.getDefault().send(new Object(),Constant.EXIT_LOGIN);
    }

    @OnClick(R.id.iv_user_icon)
    public void goUserInfo(){
        startActivity(UserInfoActivity.getLauncher(getContext()));
    }

    @OnClick(R.id.my_password_rl)
    public void goAlter(){
        startActivity(AlterPassword.getLauncher(getContext()));
    }

    @OnClick(R.id.my_order_rl)
    public void goOrder(){
        RxBus.getDefault().send(Object.class,Constant.ORDER_SUCCESS);
    }

    @OnClick(R.id.rl_msg)
    public void goMsg(){
        RongIM.getInstance().startConversation(getContext(), Conversation.ConversationType.PRIVATE,"customId"+UserHelper.getSavedUser().customId,"1");
    }
}
