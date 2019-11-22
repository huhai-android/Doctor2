package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   CloseMDTEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/15 10:55
 *  @描述：
 */
public class CloseMDTEntity {

    private List<Integer> SubjectBuyRecordIds;

    public List<Integer> getSubjectBuyRecordIds() {
        return SubjectBuyRecordIds;
    }

    public void setSubjectBuyRecordIds(List<Integer> SubjectBuyRecordIds) {
        this.SubjectBuyRecordIds = SubjectBuyRecordIds;
    }
}
