package com.bm.csxy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 2017/11/9.
 */

public class PayInfoBean implements Parcelable{

    public String ordername;
    public double amount;
    public String ordercode;

    public PayInfoBean(){

    }

    protected PayInfoBean(Parcel in) {
        ordername = in.readString();
        amount = in.readDouble();
        ordercode = in.readString();
    }

    public static final Creator<PayInfoBean> CREATOR = new Creator<PayInfoBean>() {
        @Override
        public PayInfoBean createFromParcel(Parcel in) {
            return new PayInfoBean(in);
        }

        @Override
        public PayInfoBean[] newArray(int size) {
            return new PayInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ordername);
        parcel.writeDouble(amount);
        parcel.writeString(ordercode);
    }
}
