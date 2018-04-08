package com.bm.csxy.model.bean;

import com.corelibs.subscriber.ResponseHandler;

import java.io.Serializable;

public class BaseData<T> implements Serializable, ResponseHandler.IBaseData {
    public int status;
    public String msg;
    public String Token;
    public T data;
    public Page page;

    @Override
    public boolean isSuccess() {
        return status == 0;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String msg() {
        return msg;
    }

}