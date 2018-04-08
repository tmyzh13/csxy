package com.bm.csxy.view.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.csxy.R;
import com.bm.csxy.constants.Constant;
import com.bm.csxy.constants.Urls;
import com.bm.csxy.model.UserHelper;
import com.bm.csxy.model.bean.IconBean;
import com.bm.csxy.model.bean.SexBean;
import com.bm.csxy.model.bean.UserBean;
import com.bm.csxy.presenter.UserInfoPresenter;
import com.bm.csxy.utils.Tools;
import com.bm.csxy.view.interfaces.UserInfoVIew;
import com.bm.csxy.widget.ChooseImagePopupWindow;
import com.bm.csxy.widget.CircleImageView;
import com.bm.csxy.widget.NavBar;
import com.bm.csxy.widget.TitlePopup;
import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by john on 2017/11/8.
 */

public class UserInfoActivity extends BaseActivity<UserInfoVIew,UserInfoPresenter> implements UserInfoVIew, ChooseImagePopupWindow.OnTypeChosenListener {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_nick_name)
    EditText et_nick_name;
    @Bind(R.id.user_head)
    CircleImageView user_head;
    @Bind(R.id.tv_sex)
    TextView tv_sex;

    private Context context =UserInfoActivity.this;
    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,UserInfoActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setTitle("个人中心");
        if(UserHelper.getSavedUser().headerUrl.contains("http")||UserHelper.getSavedUser().headerUrl.contains("https")){
            Glide.with(context).load(UserHelper.getSavedUser().headerUrl).override(200,200).into(user_head);
        }else{
            Glide.with(context).load(Urls.ROOT_IMG+UserHelper.getSavedUser().headerUrl).override(200,200).into(user_head);
        }
        sex=UserHelper.getSavedUser().sex;
        if(!Tools.isNull(sex)){
            if(sex.equals("1")){
                tv_sex.setText("男");
            }else if(sex.equals("2")){
                tv_sex.setText("女");
            }
        }
        et_nick_name.setText(UserHelper.getSavedUser().userNickname);
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @OnClick(R.id.user_info_save)
    public void goSave(){
        presenter.alterUserInfo(et_nick_name.getText().toString(),sex,iconUrl);
    }

    @Override
    public void alterUserInfoSuccess() {
        ToastMgr.show("修改信息成功");
        UserBean bean=UserHelper.getSavedUser();
        bean.sex=sex;
        bean.userNickname=et_nick_name.getText().toString();
        if(!Tools.isNull(iconUrl)){
            bean.avatar=iconUrl;
        }
        UserHelper.saveUser(bean);
        RxBus.getDefault().send(Object.class, Constant.REFRESH_ICON);
        finish();
    }

    private String iconUrl="";
    private String sex="1";

    @Override
    public void loadPicSuccess(IconBean bean) {
        iconUrl=bean.url;
    }


    private ChooseImagePopupWindow window;
    private File file=null;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;
    @OnClick(R.id.rl_user_icon)
    public void choiceUserIcon(){
        IMEUtil.closeIME(et_nick_name, context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则请求授权
            ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        } else {
            //有授权，直接开启摄像头
            if (window == null) {
                window = new ChooseImagePopupWindow(context);
            }
            window.setOnTypeChosenListener(this);
            window.showAtBottom(nav);
        }
    }

    @Override
    public void onCamera() {
        GalleryFinal.openCamera(0, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                file=new File(resultList.get(0).getPhotoPath());
                Glide.with(context).load(file)
//                        .centerCrop().transform(new RoundedTransformationBuilder().oval(true).build(context))
                        .into(user_head);
//                presenter.uploadPhoto(ImageUploadHelper.compressFile(context, resultList.get(0).getPhotoPath()));
                presenter.loadFile(file);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    @Override
    public void onGallery() {
        GalleryFinal.openGallerySingle(1, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                file=new File(resultList.get(0).getPhotoPath());
                Glide.with(context).load(file)
//                        .centerCrop().transform(new RoundedTransformationBuilder().oval(true).build(context))
                        .into(user_head);
//                presenter.uploadPhoto(ImageUploadHelper.compressFile(context, resultList.get(0).getPhotoPath()));
                presenter.loadFile(file);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
    private TitlePopup sexPopup;
    @OnClick(R.id.tv_sex)
    public void choiceSex(){
        if(sexPopup==null){
            sexPopup=new TitlePopup(context, tv_sex.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            SexBean bean=new SexBean();
            bean.name="男";
            SexBean bean1=new SexBean();
            bean1.name="女";
            sexPopup.addAction(bean);
            sexPopup.addAction(bean1);
            sexPopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
                @Override
                public void onItemClick(SexBean s, int position) {
                    tv_sex.setText(s.name);
                    if(position==0){
                        sex="1";
                    }else if(position==1){
                        sex="2";
                    }
                }
            });
        }

        if(!sexPopup.isShowing()){
            sexPopup.show(tv_sex);
        }
    }
}
