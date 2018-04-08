package com.bm.csxy.view.entery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.presenter.LoginPresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.LoginVIew;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by john on 2017/11/7.
 */

public class LoginActivity extends BaseActivity<LoginVIew,LoginPresenter> implements LoginVIew{

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.rember_password_cb)
    CheckBox rember_password_cb;
    @Bind(R.id.et_ac_login_phonenum)
    EditText et_ac_login_phonenum;
    @Bind(R.id.et_ac_login_password)
    EditText et_ac_login_password;

    private Context context=LoginActivity.this;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,LoginActivity.class);
        return intent;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("登陆");
        nav.setRightText("注册", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册
                startActivity(RegisterActivity.getLauncher(context,"2"));
            }
        });
        if(Tools.isNull(PreferencesHelper.getData(Constant.REMEMBER))){
            rember_password_cb.setChecked(false);
        }else{
            rember_password_cb.setChecked(true);
            if(!Tools.isNull(PreferencesHelper.getData(Constant.LOGIN_ACOUNT))&&!Tools.isNull(PreferencesHelper.getData(Constant.LOGIN_PASSWORD))){
                et_ac_login_phonenum.setText(PreferencesHelper.getData(Constant.LOGIN_ACOUNT));
                et_ac_login_password.setText(PreferencesHelper.getData(Constant.LOGIN_PASSWORD));
            }
        }

        rember_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesHelper.saveData(Constant.REMEMBER,"1");
                }else{
                    PreferencesHelper.saveData(Constant.REMEMBER,"");
                }
            }
        });

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.ll_wechat)
    public void goWeChat(){

    }

    @OnClick(R.id.btn_ac_login_btn_ac_login)
    public void goLogin(){

        presenter.login(et_ac_login_phonenum.getText().toString(),et_ac_login_password.getText().toString());
    }

    @Override
    public void loginSuccess() {
        if(rember_password_cb.isChecked()){
            PreferencesHelper.saveData(Constant.LOGIN_ACOUNT,et_ac_login_phonenum.getText().toString());
            PreferencesHelper.saveData(Constant.LOGIN_PASSWORD,et_ac_login_password.getText().toString());
        }
        ToastMgr.show("登陆成功");
        RongIM.connect(UserHelper.getSavedUser().rongyunToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                ToastMgr.show("聊天信息获取失败");
                finish();
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
                finish();
//                    hideLoading();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ToastMgr.show("聊天信息获取失败");
                finish();
            }
        });

    }

    @OnClick(R.id.ll_wechat)
    public void goWechat(){
//        PlatformConfig.setWeixin("wxed9bd1c579c77654","a205baf5925a081c02ed3e09f12682c2");
        UMShareAPI.get(context).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            Log.e("yzh","onStart");
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            String nickname=data.get("name");
            String id=data.get("unionid");
            String icon=data.get("iconurl");
            String nameCode="";
            if(URLEncoder.encode(nickname).contains("%F0%9F")) {
                //说明三方昵称带表情符号
                nameCode = URLEncoder.encode(nickname).substring(0, URLEncoder.encode(nickname).lastIndexOf("%F0%9F"));
                Log.e("yzh", URLDecoder.decode(nameCode) + "???");
            }
            Toast.makeText(getApplicationContext(), "三方登陆成功", Toast.LENGTH_SHORT).show();
            presenter.thirdLogin(URLDecoder.decode(nameCode),id,icon);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.e("yzh","onError"+action+"---"+t.getMessage());
            Toast.makeText( getApplicationContext(), "三方登陆失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.e("yzh","onCancel");
            Toast.makeText( getApplicationContext(), "三方登陆失败", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.tv_ac_login_forget_password)
    public void forgetPassword(){
        startActivity(RegisterActivity.getLauncher(context,"1"));
    }
}
