package com.bm.csxy.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import com.bm.csxy.R;
import com.bm.csxy.presenter.OrderCommentsPresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.OrderCommentsView;
import com.bm.csxy.widget.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2017/11/8.
 */

public class OrderCommentsActivity extends BaseActivity<OrderCommentsView,OrderCommentsPresenter> implements OrderCommentsView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_content)
    EditText et_content;
    @Bind(R.id.ratinbar_score)
    RatingBar ratinbar_score;


    private Context context=OrderCommentsActivity.this;
    private String id="";
    public static Intent getLauncher(Context context,String id){
        Intent intent=new Intent(context,OrderCommentsActivity.class);
        intent.putExtra("id",id);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_comments;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("评价");

        id=getIntent().getStringExtra("id");
    }

    @Override
    protected OrderCommentsPresenter createPresenter() {
        return new OrderCommentsPresenter();
    }

    @Override
    public void commentsSuccess() {
        ToastMgr.show("评价成功");
        finish();
    }

    @OnClick(R.id.tv_send)
    public void sendComment(){

        String content="";
        if(!Tools.isNull(et_content.getText().toString())){
            content=et_content.getText().toString();
        }
        presenter.comment(id,content,ratinbar_score.getRating()+"");
    }
}
