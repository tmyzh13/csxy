package com.bm.csxy.view.entery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.bm.csxy.MainActivity;
import com.bm.csxy.R;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.presenter.SettingPasswordPresenter;
import com.bm.csxy.view.interfaces.SettingPasswordView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by john on 2017/11/7.
 */

public class SettingPasswordActivity extends BaseActivity<SettingPasswordView,SettingPasswordPresenter> implements SettingPasswordView{

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.setting_password_et)
    EditText setting_password_et;
    @Bind(R.id.setting_password_confirm_et)
    EditText setting_password_confirm_et;

    public Context context=SettingPasswordActivity.this;
    private String phone;
    private String type;
    public static Intent getLauncher(Context context,String phone,String type){
        Intent intent=new Intent(context,SettingPasswordActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setTitle("注册");
        phone=getIntent().getStringExtra("phone");
        type=getIntent().getStringExtra("type");
    }

    @Override
    protected SettingPasswordPresenter createPresenter() {
        return new SettingPasswordPresenter();
    }

    @OnClick(R.id.register_btn)
    public void goRegister(){
        if (type.equals("2"))
       presenter.register(phone,setting_password_et.getText().toString(),setting_password_confirm_et.getText().toString());
        else
        presenter.forgetPassword(phone,setting_password_et.getText().toString(),setting_password_confirm_et.getText().toString());
    }

    @Override
    public void registerSuccess() {
        if(type.equals("2")){
            ToastMgr.show("注册成功");
        }else if(type.equals("1")){
            ToastMgr.show("修改成功");
        }

        RongIM.connect(UserHelper.getSavedUser().rongyunToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                ToastMgr.show("聊天信息获取失败");
                startActivity(MainActivity.getLauncher(context));
            }

            @Override
            public void onSuccess(String s) {
                Log.e("yzh","--onstring--"+s);
                String icon="";
                if(UserHelper.getSavedUser().headerUrl.contains("http")||UserHelper.getSavedUser().headerUrl.contains("https")){
                    icon=UserHelper.getSavedUser().headerUrl;
                }else{
                    icon=Urls.ROOT_IMG+UserHelper.getSavedUser().headerUrl;
                }
                UserInfo userInfo=new UserInfo("userId"+UserHelper.getSavedUser().id, UserHelper.getSavedUser().userNickname, Uri.parse(icon));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                startActivity(MainActivity.getLauncher(context));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ToastMgr.show("聊天信息获取失败");
                startActivity(MainActivity.getLauncher(context));
            }
        });
    }
}
