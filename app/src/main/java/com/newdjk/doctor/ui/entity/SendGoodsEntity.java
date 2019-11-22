package com.newdjk.doctor.ui.entity;

/**
 * Created by Struggle on 2018/10/15.
 */

public class SendGoodsEntity {


    /**
     * DataSource : 64
     * DataId : 2
     * LinkAddress : www.baidu.com
     */

    private int DataSource;
    private int DataId;
    private String LinkAddress;

    public int getDataSource() {
        return DataSource;
    }

    public void setDataSource(int DataSource) {
        this.DataSource = DataSource;
    }

    public int getDataId() {
        return DataId;
    }

    public void setDataId(int DataId) {
        this.DataId = DataId;
    }

    public String getLinkAddress() {
        return LinkAddress;
    }

    public void setLinkAddress(String LinkAddress) {
        this.LinkAddress = LinkAddress;
    }
}
