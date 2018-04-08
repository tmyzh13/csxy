package com.bm.csxy.model.bean;

/**
 * Created by yizh on 2016/3/21.
 */
public class UserBean {

    public long id;
    /**
     * 用户名
     */
    public String userName;

    /**
     * 真实姓名
     */
    public String realName;

    /**
     * 用户电话号码
     */
//    public String phone;

    private String accountId;
    /**
     * 用户id
     */
    public String userId;
    /**
     * 用户昵称
     */
    public String userNickname;
    /**
     * 头像图片路径
     */
    public String avatar;
    /**
     * 性别 1为男，2为女，0为未知
     */
    public String gender = "0";
    // /**
    // * 生日
    // */
    // public String birthday;

    public String token = "";

    /**
     * 实名认证
     */
    public String identity = "0";

    public String is_company;

    public String companyId;

    public String userPhone;

    public String userRealName;

    public String userCompanyName;

    public String companyName;

    public String companyCreditNo;

    public String companyLicenceNo;

    public String companyAddress;

    public String userType;

    public String integral="0";

    public String card;

    public String customWeixin;
    public String customPhone;
    public String customName;
    public String customId;
    public String customHeaderUrl;

    public String phone;
    public String name;
    public String sex;
    public String weixinName;
    public String headerUrl;
    public String isAdmin;//is_admin    1-普通客服,2-管理员
    public String salt;
    public String weight;

    public String rongyunToken;
}
