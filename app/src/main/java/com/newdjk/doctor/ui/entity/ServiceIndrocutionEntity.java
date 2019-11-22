package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ServiceIndrocutionEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/20 17:06
 *  @描述：
 */
public class ServiceIndrocutionEntity {

    /**
     * title : 服务介绍
     * linkUrl : http://drwechat.newstarthealth.cn/index.html#/incomeIntro
     */

    private String title;
    private String linkUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
