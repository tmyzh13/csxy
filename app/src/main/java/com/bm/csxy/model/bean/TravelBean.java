package com.bm.csxy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by john on 2017/11/7.
 */

public class TravelBean implements Parcelable{
    public String isonline;
    public String songurl;
    public String feenotcontain;
    public String timelength;
    public String feecontain;
    public String goodtotal;
    public String tickettype;
    public String id;
    public double price;
    public String typeid;
    public String scenicname;
    public String scenicdesc;
    public String playtime;
    public List<ProductTravelBean> scenicRouteList;
    public List<ProductPlayBean> scenicStrategyList;
    public String songname;
    public String customId;
    public String customphone;
    public String customweixin;
    public List<ProductEvaluationBean> scenicCommentList;
    public String gradeSum;
    public String commentNum;
    public String scenicstrategy;
    public String scenicroute;
    public String scenicpic;


    protected TravelBean(Parcel in) {
        isonline = in.readString();
        songurl = in.readString();
        feenotcontain = in.readString();
        timelength = in.readString();
        feecontain = in.readString();
        goodtotal = in.readString();
        tickettype = in.readString();
        id = in.readString();
        price = in.readDouble();
        typeid = in.readString();
        scenicname = in.readString();
        scenicdesc = in.readString();
        playtime = in.readString();
        songname = in.readString();
        customId = in.readString();
        customphone = in.readString();
        customweixin = in.readString();
        gradeSum = in.readString();
        commentNum = in.readString();
        scenicstrategy = in.readString();
        scenicroute = in.readString();
        scenicpic = in.readString();
    }

    public static final Creator<TravelBean> CREATOR = new Creator<TravelBean>() {
        @Override
        public TravelBean createFromParcel(Parcel in) {
            return new TravelBean(in);
        }

        @Override
        public TravelBean[] newArray(int size) {
            return new TravelBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(isonline);
        parcel.writeString(songurl);
        parcel.writeString(feenotcontain);
        parcel.writeString(timelength);
        parcel.writeString(feecontain);
        parcel.writeString(goodtotal);
        parcel.writeString(tickettype);
        parcel.writeString(id);
        parcel.writeDouble(price);
        parcel.writeString(typeid);
        parcel.writeString(scenicname);
        parcel.writeString(scenicdesc);
        parcel.writeString(playtime);
        parcel.writeString(songname);
        parcel.writeString(customId);
        parcel.writeString(customphone);
        parcel.writeString(customweixin);
        parcel.writeString(gradeSum);
        parcel.writeString(commentNum);
        parcel.writeString(scenicstrategy);
        parcel.writeString(scenicroute);
        parcel.writeString(scenicpic);
    }
}
