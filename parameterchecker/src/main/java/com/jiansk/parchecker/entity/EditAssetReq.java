package com.jiansk.parchecker.entity;

/**
 * Created by zhaokun19863 on 2017/1/23.
 */
public class EditAssetReq {

    /**
     * 客户号
     */
    private String custNo;

    /**
     * 账号
     */
    private String assetNo;

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }
}
