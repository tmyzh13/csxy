package com.bm.csxy.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.bm.csxy.R;
import com.bm.csxy.model.bean.BaseData;
import com.bm.csxy.presenter.AlterPasswordPresenter;
import com.bm.csxy.view.interfaces.AlterPasswordView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2017/11/8.
 */

public class AlterPassword extends BaseActivity<AlterPasswordView,AlterPasswordPresenter> implements AlterPasswordView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.modify_old_password_et)
    EditText modify_old_password_et;
    @Bind(R.id.modify_new_password_confirm_et)
    EditText modify_new_password_confirm_et;
    @Bind(R.id.modify_new_comfir_password_confirm_et)
    EditText modify_new_comfir_password_confirm_et;

    private Context context=AlterPassword.this;

    public static Intent getLauncher(Context context){
        Intent intent=new Intent(context,AlterPassword.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
            nav.setTitle("修改密码");
    }

    @Override
    protected AlterPasswordPresenter createPresenter() {
        return new AlterPasswordPresenter();
    }

    @Override
    public void alterPasswordSuccess() {
        ToastMgr.show("修改密码成功");
        finish();
    }

    @OnClick(R.id.modify_sure_btn)
    public void alterPassword(){
        presenter.alterPassword(modify_old_password_et.getText().toString(),
                modify_new_password_confirm_et.getText().toString(),
                modify_new_comfir_password_confirm_et.getText().toString());
    }
}
