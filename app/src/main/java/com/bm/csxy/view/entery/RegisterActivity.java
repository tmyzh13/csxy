package com.bm.csxy.view.entery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bm.csxy.R;
import com.bm.csxy.presenter.RegisterPresenter;
import com.bm.csxy.utils.MyCountDownTimer;
import com.bm.csxy.view.interfaces.RegisterView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2017/11/7.
 */

public class RegisterActivity extends BaseActivity<RegisterView,RegisterPresenter> implements RegisterView{

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.register_mobile_et)
    EditText register_mobile_et;
    @Bind(R.id.register_code_et)
    EditText register_code_et;
    @Bind(R.id.register_code_btn)
    Button register_code_btn;

    public Context context=RegisterActivity.this;
    private String type;
    public static Intent getLauncher(Context context,String type){
        Intent intent=new Intent(context,RegisterActivity.class);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("注册");
        type=getIntent().getStringExtra("type");
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @OnClick(R.id.register_code_btn)
    public void getCode(){
        presenter.getCode(register_mobile_et.getText().toString(),type);

    }

    @OnClick(R.id.register_next)
    public void goNext(){
        presenter.checkCode(register_mobile_et.getText().toString(),register_code_et.getText().toString());

    }

    //倒计时
    private void daojishi(){
        new MyCountDownTimer(register_code_btn, 60 * 1000, 1000,true).start();
    }

    @Override
    public void getCodeSuccess() {
        //获取验证码成功
        ToastMgr.show("获取验证码成功");
        daojishi();
    }

    @Override
    public void checkCodeSuccess() {
        startActivity(SettingPasswordActivity.getLauncher(context,register_mobile_et.getText().toString(),type));
    }
}
