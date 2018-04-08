package com.bm.csxy.model.bean;

import java.util.List;

/**
 * Created by john on 2017/10/26.
 */

public class TravelOrderBean {

    public String orderNum;//订单编号
    public String orderStatue;//订单状态 0 待付款 1待评价 2退款
    public String orderName;//产品名称
    public String orderContent;//产品内容
    public String count;//订单数量
    public String totalPrice;//订单总价

    public double amount;
    public String orderstatus;//订单状态0-全部   1-待付款,2-待评价，3-已完成，4-已退款
    public String customid;
    public String customPhone;
    public String id;
    public String ordercode;//订单编号
    public String startday;
    public String isfirstpayback;
    public String scenicname;
    public String scenicdesc;
    public String buynum;
    public String paydatestr;
    public String createdateStr;
    public List<PassenegerInfo> scenicOrderUserList;
    public String ordername;
}
