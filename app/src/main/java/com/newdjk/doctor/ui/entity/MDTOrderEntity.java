package com.newdjk.doctor.ui.entity;

import java.io.Serializable;
import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   MDTOrderEntity
 *  @创建者:   huhai
 *  @创建时间:  2019/9/9 16:04
 *  @描述：
 */
public class MDTOrderEntity implements Serializable {

    /**
     * Total : 1
     * ReturnData : [{"SubjectBuyRecordId":48,"SubjectRecordId":38,"PatientId":16,"PatientName":"HUU","PatientMobile":"15088131724","PatientSex":2,"PatientAge":"26岁","PatPicturePath":"http://userimage.newstarthealth.cn/pat/0/16.jpg?dt=201909090228262","SpecialistHosGroupId":5,"HosGroupName":"专治疑难杂症","Description":null,"Diseases":null,"DiseasesTimeNo":0,"DiseasesTimeText":null,"SeeDoctorNo":0,"SeeDoctorText":null,"SeeDoctor":null,"SeeDepartment":null,"IsHasImg":0,"IsDrug":0,"DrugUse":null,"OperationDes":null,"ChemoDes":null,"DiseaseNatureNo":0,"DiseaseNatureText":null,"IsAllergy":0,"AllergyDes":null,"Advice":null,"ConsultAmount":"0.00","IMGroupId":"G201909091601081761","PayStatus":2,"PayOrderNo":"20190909160108086844","PayOrderTime":null,"PayTime":"2019-09-09 16:01:08","ReceiptTime":null,"IssueTime":null,"SubjectStatus":0,"DrId":57,"DrName":"奥巴马","SubjectBuyRecordNo":"1909091601053098042","SubjectReportPath":null}]
     */

    private int Total;
    private List<MDTDetailEntity> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<MDTDetailEntity> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<MDTDetailEntity> ReturnData) {
        this.ReturnData = ReturnData;
    }


}
