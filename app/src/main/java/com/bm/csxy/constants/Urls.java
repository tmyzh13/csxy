package com.bm.csxy.constants;

/**
 * Created by john on 2017/11/7.
 */

public class Urls {



//    public static final String ROOT="https://m.zyh188.com/wy/";
//    http://192.168.1.130:8089/wy-app/
    public static final String ROOT="http://m.zyh198.com/wy-app/";
//    m.zyh198.com/wy-platform/
    public static final String ROOT_IMG="http://m.zyh198.com";

    //获取首页景区
    public static final String GET_HOME_TYPE="lvyou/getAllScenicTypeList";
    //获取景区列表
    public static final String URL_GET_TRAVEL_TYPE="lvyou/getAllScenicInfoPage";
    //登陆
    public static final String LOGIN="userPort/login";
    //三方登陆
    public static final String THIRD_LOGIN="userPort/thLogin";
    //景区详情
    public static final String TRAVEL_DETAIL="lvyou/getAllScenicInfoDetails";
    //评价列表
    public static final String EVALUATION_LIST="lvyou/getScenicCommentPage";
    //提交订单
    public static final String ORDER_PRODUCT="lvyouOrder/deal";
    //获取订单列表
    public static final String GET_ORDER_LIST="lvyou/getScenicOrderPage";
    //删除订单
    public static final String DELETE_ORDER="lvyou/hideScenicOrder";
    //获取订单详情
    public static final String GET_ORDER_DETAIL="lvyou/getScenicOrderDetails";
    //评论订单
    public static final String COMMENTS_ORDER="lvyouOrder/addComment";
    //获取融云客服的信息
    public static final String GET_RONGYUN_CUSTOM_INFO="custom/getCustomDetail";
    //获取融云用户的信息
    public static final String GET_RONGYUN_USER_INFO="api/user/detail";
    //获取验证吗
    public static final String GET_CODE="userPort/getAuthCode";
    //校验验证码
    public static final String CHECK_CODE="userPort/checkCode";
    //注册
    public static final String REGISTER="userPort/register";
    //忘记密码
    public static final String FORGET_PASSWORD="userPort/forgetPassword";
    //修改密码
    public static final String CHANGER_PASSWOR="userPort/changePassword";
    //修改个人信息
    public static final String ALTER_USER_INFO="userPort/updateUserInfo";
    //上传图片
    public static final String LOAD_IMG="upload/uploadImg";
    //获取轮播图
    public static final String GET_ADS="carouselPic/getCarouselPC";
    //获取城市列表
    public static final String GET_CITYS="api/region/getOpenCityList";
}
