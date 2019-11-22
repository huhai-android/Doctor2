package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   PrescriptionEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/28 16:09
 *  @描述：
 */
public class PrescriptionEntity {


    @Override
    public String toString() {
        return "PrescriptionEntity{" +
                "Total=" + Total +
                ", ReturnData=" + ReturnData +
                '}';
    }

    /**
     * Total : 58
     * ReturnData : [{"ListDetails":[{"Id":1048,"PrescriptionId":667,"MedicationId":7778,"Name":"复合维生素B片","Manufacturer":"华中药业股份有限公司","Specification":"100片","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":0,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":0,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":8.4,"Quantity":1,"PackageUnitId":0,"PackageUnit":"瓶","TotalPrice":8.4,"Remark":null,"UpdateTime":"2018-11-21 19:41:22","CreateTime":"2018-11-21 19:41:22"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181121/667/57.png","ApothecarySignImgUrl":"","Id":667,"No":"XQD201811211941021645","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":90,"PatientName":"香囊记","PatientSex":1,"Age":27,"Mobile":"15088886661","AreaName":null,"Region":null,"ApplicantId":48,"ApplicantName":null,"DoctorId":57,"DoctorName":"奥巴马","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"外科","Diagnoses":"巴蒂","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":8.4,"PrescribeTime":"2018-11-21 19:41:22","Status":2,"SignStatus":1,"AuditorId":109,"AuditorName":"胡歌","AuditTime":"2018-11-28 09:57:44","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-28 09:57:44","CreateTime":"2018-11-21 19:41:22"},{"ListDetails":[{"Id":1042,"PrescriptionId":661,"MedicationId":7482,"Name":"美洛昔康片","Manufacturer":"上海勃林格殷格翰药业有限公司","Specification":"7.5mg*7s","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":20.5,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":20.5,"Remark":null,"UpdateTime":"2018-11-21 10:34:18","CreateTime":"2018-11-21 10:34:18"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181121/661/57.png","ApothecarySignImgUrl":"","Id":661,"No":"XQD201811211033191309","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":54,"PatientName":"李爽玉","PatientSex":2,"Age":39,"Mobile":"13410328229","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":57,"DoctorName":"奥巴马","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"外科","Diagnoses":"毛病1","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":20.5,"PrescribeTime":"2018-11-21 10:34:18","Status":2,"SignStatus":1,"AuditorId":263,"AuditorName":"苹果","AuditTime":"2018-11-21 10:48:22","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-21 10:48:21","CreateTime":"2018-11-21 10:34:18"},{"ListDetails":[{"Id":1019,"PrescriptionId":645,"MedicationId":7912,"Name":"维生素AD软胶囊","Manufacturer":"杭州慧致源科技有限公司","Specification":"18g(300mg/粒)*60粒","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":1,"DosageUnitId":3,"DosageUnit":"粒","DosageDaysId":0,"DosageDays":"","Price":0,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":0,"Remark":null,"UpdateTime":"2018-11-19 16:47:50","CreateTime":"2018-11-19 16:47:50"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181119/645/57.png","ApothecarySignImgUrl":"","Id":645,"No":"XQD201811191647275418","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":275,"PatientName":"王大爷","PatientSex":1,"Age":19,"Mobile":"13410328215","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":57,"DoctorName":"奥巴马","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"外科","Diagnoses":"王大爷","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":0,"PrescribeTime":"2018-11-19 16:47:50","Status":2,"SignStatus":1,"AuditorId":263,"AuditorName":"苹果","AuditTime":"2018-11-19 16:55:37","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-19 16:55:37","CreateTime":"2018-11-19 16:47:50"},{"ListDetails":[{"Id":1018,"PrescriptionId":644,"MedicationId":7912,"Name":"维生素AD软胶囊","Manufacturer":"杭州慧致源科技有限公司","Specification":"18g(300mg/粒)*60粒","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":1,"DosageUnitId":3,"DosageUnit":"粒","DosageDaysId":0,"DosageDays":"","Price":0,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":0,"Remark":null,"UpdateTime":"2018-11-19 15:52:37","CreateTime":"2018-11-19 15:52:37"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181119/644/57.png","ApothecarySignImgUrl":"","Id":644,"No":"XQD201811191552251285","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":275,"PatientName":"王大爷","PatientSex":1,"Age":19,"Mobile":"13410328215","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":57,"DoctorName":"奥巴马","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"外科","Diagnoses":"土豪金","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":0,"PrescribeTime":"2018-11-19 15:52:37","Status":2,"SignStatus":1,"AuditorId":109,"AuditorName":"胡歌","AuditTime":"2018-11-19 18:44:05","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-19 18:44:05","CreateTime":"2018-11-19 15:52:37"},{"ListDetails":[{"Id":1009,"PrescriptionId":638,"MedicationId":7502,"Name":"氢氯噻嗪片","Manufacturer":"天津力生制药股份有限公司","Specification":"25mg*100T","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":12,"Quantity":1,"PackageUnitId":2,"PackageUnit":"瓶","TotalPrice":12,"Remark":null,"UpdateTime":"2018-11-17 20:53:25","CreateTime":"2018-11-17 20:53:25"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/638/18.png","ApothecarySignImgUrl":"","Id":638,"No":"XQD201811172053031440","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":100,"PatientName":"I很丑","PatientSex":2,"Age":14,"Mobile":"15088886666","AreaName":null,"Region":null,"ApplicantId":48,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"fhkbc","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":12,"PrescribeTime":"2018-11-17 20:53:26","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:03:21","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:03:20","CreateTime":"2018-11-17 20:53:25"},{"ListDetails":[{"Id":1008,"PrescriptionId":637,"MedicationId":7778,"Name":"复合维生素B片","Manufacturer":"华中药业股份有限公司","Specification":"100片","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":3,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":8.4,"Quantity":1,"PackageUnitId":2,"PackageUnit":"瓶","TotalPrice":8.4,"Remark":null,"UpdateTime":"2018-11-17 20:49:27","CreateTime":"2018-11-17 20:49:27"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/637/18.png","ApothecarySignImgUrl":"","Id":637,"No":"XQD201811172048553925","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":291,"PatientName":"新开始","PatientSex":2,"Age":44,"Mobile":"13410328229","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"嗯","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":8.4,"PrescribeTime":"2018-11-17 20:49:28","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:03:30","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:03:29","CreateTime":"2018-11-17 20:49:27"},{"ListDetails":[{"Id":1003,"PrescriptionId":636,"MedicationId":7527,"Name":"枸橼酸托法替布片","Manufacturer":"德国PfizerManufacturingDeutschlandGmbH","Specification":"5mg*28T","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":2,"DayDosage":"每日两次","Dosage":5,"DosageUnitId":15,"DosageUnit":"mg","DosageDaysId":0,"DosageDays":"","Price":1995,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":1995,"Remark":null,"UpdateTime":"2018-11-17 20:48:26","CreateTime":"2018-11-17 20:48:26"},{"Id":1004,"PrescriptionId":636,"MedicationId":7526,"Name":"恩格列净片","Manufacturer":"生产:BoehringerIngelheimPharmaGmbH&Co.KG(分装:上海勃林格殷格翰药业有限公司)","Specification":"10mg*10T*1板","UsageTimeId":2,"UsageTime":"饭后","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":180,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":180,"Remark":null,"UpdateTime":"2018-11-17 20:48:26","CreateTime":"2018-11-17 20:48:26"},{"Id":1005,"PrescriptionId":636,"MedicationId":7478,"Name":"阿奇霉素片","Manufacturer":"辉瑞制药有限公司","Specification":"0.25g*6s","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":1,"DayDosage":"每日一次","Dosage":2,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":72.3,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":72.3,"Remark":null,"UpdateTime":"2018-11-17 20:48:26","CreateTime":"2018-11-17 20:48:26"},{"Id":1006,"PrescriptionId":636,"MedicationId":7914,"Name":"营养强化复合粉II","Manufacturer":"上海冬泽特医食品有限公司","Specification":"20g*12袋","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"温水冲服","DayDosageId":3,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":4,"DosageUnit":"袋","DosageDaysId":0,"DosageDays":"","Price":380,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":380,"Remark":null,"UpdateTime":"2018-11-17 20:48:26","CreateTime":"2018-11-17 20:48:26"},{"Id":1007,"PrescriptionId":636,"MedicationId":7913,"Name":"营养强化蛋白复合粉（Ⅲ）型","Manufacturer":"上海首源生物技术有限公司分公司","Specification":"25g*18袋","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"温水冲服","DayDosageId":3,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":4,"DosageUnit":"袋","DosageDaysId":0,"DosageDays":"","Price":498,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":498,"Remark":null,"UpdateTime":"2018-11-17 20:48:26","CreateTime":"2018-11-17 20:48:26"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/636/18.png","ApothecarySignImgUrl":"","Id":636,"No":"XQD201811172047361881","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":291,"PatientName":"新开始","PatientSex":2,"Age":44,"Mobile":"13410328229","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"啊呸","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":3125.3,"PrescribeTime":"2018-11-17 20:48:27","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:03:37","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:03:37","CreateTime":"2018-11-17 20:48:26"},{"ListDetails":[{"Id":1002,"PrescriptionId":635,"MedicationId":7778,"Name":"复合维生素B片","Manufacturer":"华中药业股份有限公司","Specification":"100片","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":3,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":8.4,"Quantity":1,"PackageUnitId":2,"PackageUnit":"瓶","TotalPrice":8.4,"Remark":null,"UpdateTime":"2018-11-17 20:45:12","CreateTime":"2018-11-17 20:45:12"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/635/18.png","ApothecarySignImgUrl":"","Id":635,"No":"XQD201811172044472015","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":291,"PatientName":"新开始","PatientSex":2,"Age":44,"Mobile":"13410328229","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"桶，","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":8.4,"PrescribeTime":"2018-11-17 20:45:12","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:03:44","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:03:44","CreateTime":"2018-11-17 20:45:12"},{"ListDetails":[{"Id":1001,"PrescriptionId":634,"MedicationId":7778,"Name":"复合维生素B片","Manufacturer":"华中药业股份有限公司","Specification":"100片","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":3,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":1,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":8.4,"Quantity":1,"PackageUnitId":2,"PackageUnit":"瓶","TotalPrice":8.4,"Remark":null,"UpdateTime":"2018-11-17 20:44:09","CreateTime":"2018-11-17 20:44:09"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/634/18.png","ApothecarySignImgUrl":"","Id":634,"No":"XQD201811172043442111","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":291,"PatientName":"新开始","PatientSex":2,"Age":44,"Mobile":"13410328229","AreaName":null,"Region":null,"ApplicantId":34,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"机会","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":8.4,"PrescribeTime":"2018-11-17 20:44:09","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:03:52","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:03:52","CreateTime":"2018-11-17 20:44:09"},{"ListDetails":[{"Id":999,"PrescriptionId":633,"MedicationId":7923,"Name":"注射用重组人Ⅱ型肿瘤坏死因子受体-抗体融合蛋白","Manufacturer":"浙江海正药业股份有限公司","Specification":"25mg,生物学活性2.50×106AU/瓶","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"皮下注射","DayDosageId":2,"DayDosage":"每日两次","Dosage":1,"DosageUnitId":5,"DosageUnit":"支","DosageDaysId":0,"DosageDays":"","Price":520,"Quantity":1,"PackageUnitId":5,"PackageUnit":"支","TotalPrice":520,"Remark":null,"UpdateTime":"2018-11-17 20:27:09","CreateTime":"2018-11-17 20:27:09"},{"Id":1000,"PrescriptionId":633,"MedicationId":7527,"Name":"枸橼酸托法替布片","Manufacturer":"德国PfizerManufacturingDeutschlandGmbH","Specification":"5mg*28T","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":2,"DayDosage":"每日两次","Dosage":5,"DosageUnitId":15,"DosageUnit":"mg","DosageDaysId":0,"DosageDays":"","Price":1995,"Quantity":1,"PackageUnitId":1,"PackageUnit":"盒","TotalPrice":1995,"Remark":null,"UpdateTime":"2018-11-17 20:27:09","CreateTime":"2018-11-17 20:27:09"}],"PrescriptionCategory":0,"PrescriptionBuyRecord":null,"DoctorSignImgUrl":"http://resource.newstarthealth.cn/Recipe/20181117/633/18.png","ApothecarySignImgUrl":"","Id":633,"No":"XQD201811172026521157","ApplyId":0,"MPrescriptionId":0,"MPrescriptionNo":null,"PatientId":100,"PatientName":"I很丑","PatientSex":2,"Age":14,"Mobile":"15088886666","AreaName":null,"Region":null,"ApplicantId":48,"ApplicantName":null,"DoctorId":18,"DoctorName":"钟大潘","MedicationCompanyId":4,"OrgId":1,"OrgName":"新起点互联网医院","OrgPath":",0,1,","Type":2,"CostType":"自费","MedicalNo":null,"MedicalRecordNo":null,"Department":"内科2","Diagnoses":"U盾路","ValidDays":1,"ValidDaysRemark":null,"DosageDays":1,"DosageDaysRemark":null,"TotalPrice":2515,"PrescribeTime":"2018-11-17 20:27:09","Status":2,"SignStatus":1,"AuditorId":1,"AuditorName":"欧阳云生","AuditTime":"2018-11-17 21:04:01","AuditRemark":null,"RejectReasonId":null,"RandomCode":null,"Remark":null,"UpdateTime":"2018-11-17 21:04:00","CreateTime":"2018-11-17 20:27:09"}]
     */

    private int Total;
    private List<ReturnDataBean> ReturnData;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<ReturnDataBean> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ReturnDataBean> ReturnData) {
        this.ReturnData = ReturnData;
    }

    public static class ReturnDataBean {
        /**
         * ListDetails : [{"Id":1048,"PrescriptionId":667,"MedicationId":7778,"Name":"复合维生素B片","Manufacturer":"华中药业股份有限公司","Specification":"100片","UsageTimeId":0,"UsageTime":"","UsageMethodId":0,"UsageMethod":"口服","DayDosageId":0,"DayDosage":"每日三次","Dosage":1,"DosageUnitId":0,"DosageUnit":"片","DosageDaysId":0,"DosageDays":"","Price":8.4,"Quantity":1,"PackageUnitId":0,"PackageUnit":"瓶","TotalPrice":8.4,"Remark":null,"UpdateTime":"2018-11-21 19:41:22","CreateTime":"2018-11-21 19:41:22"}]
         * PrescriptionCategory : 0
         * PrescriptionBuyRecord : null
         * DoctorSignImgUrl : http://resource.newstarthealth.cn/Recipe/20181121/667/57.png
         * ApothecarySignImgUrl :
         * Id : 667
         * No : XQD201811211941021645
         * ApplyId : 0
         * MPrescriptionId : 0
         * MPrescriptionNo : null
         * PatientId : 90
         * PatientName : 香囊记
         * PatientSex : 1
         * Age : 27
         * Mobile : 15088886661
         * AreaName : null
         * Region : null
         * ApplicantId : 48
         * ApplicantName : null
         * DoctorId : 57
         * DoctorName : 奥巴马
         * MedicationCompanyId : 4
         * OrgId : 1
         * OrgName : 新起点互联网医院
         * OrgPath : ,0,1,
         * Type : 2
         * CostType : 自费
         * MedicalNo : null
         * MedicalRecordNo : null
         * Department : 外科
         * Diagnoses : 巴蒂
         * ValidDays : 1
         * ValidDaysRemark : null
         * DosageDays : 1
         * DosageDaysRemark : null
         * TotalPrice : 8.4
         * PrescribeTime : 2018-11-21 19:41:22
         * Status : 2
         * SignStatus : 1
         * AuditorId : 109
         * AuditorName : 胡歌
         * AuditTime : 2018-11-28 09:57:44
         * AuditRemark : null
         * RejectReasonId : null
         * RandomCode : null
         * Remark : null
         * UpdateTime : 2018-11-28 09:57:44
         * CreateTime : 2018-11-21 19:41:22
         */

        private int PrescriptionCategory;
        private Object PrescriptionBuyRecord;
        private String DoctorSignImgUrl;
        private String ApothecarySignImgUrl;
        private int Id;
        private String No;
        private int ApplyId;
        private int MPrescriptionId;
        private Object MPrescriptionNo;
        private int PatientId;
        private String PatientName;
        private int PatientSex;
        private int Age;
        private String Mobile;
        private Object AreaName;
        private Object Region;
        private int ApplicantId;
        private Object ApplicantName;
        private int DoctorId;
        private String DoctorName;
        private int MedicationCompanyId;
        private int OrgId;
        private String OrgName;
        private String OrgPath;
        private int Type;
        private String CostType;
        private Object MedicalNo;
        private Object MedicalRecordNo;
        private String Department;
        private String Diagnoses;
        private int ValidDays;
        private Object ValidDaysRemark;
        private int DosageDays;
        private Object DosageDaysRemark;
        private double TotalPrice;
        private String PrescribeTime;
        private int Status;
        private int SignStatus;
        private int AuditorId;
        private String AuditorName;
        private String AuditTime;
        private Object AuditRemark;
        private Object RejectReasonId;
        private Object RandomCode;
        private Object Remark;
        private String UpdateTime;
        private String CreateTime;

        public int getPrescriptionType() {
            return PrescriptionType;
        }

        public void setPrescriptionType(int prescriptionType) {
            PrescriptionType = prescriptionType;
        }

        private int PrescriptionType;
        private List<ListDetailsBean> ListDetails;
        private boolean isPass;

        @Override
        public String toString() {
            return "ReturnDataBean{" +
                    "PrescriptionCategory=" + PrescriptionCategory +
                    ", PrescriptionBuyRecord=" + PrescriptionBuyRecord +
                    ", DoctorSignImgUrl='" + DoctorSignImgUrl + '\'' +
                    ", ApothecarySignImgUrl='" + ApothecarySignImgUrl + '\'' +
                    ", Id=" + Id +
                    ", No='" + No + '\'' +
                    ", ApplyId=" + ApplyId +
                    ", MPrescriptionId=" + MPrescriptionId +
                    ", MPrescriptionNo=" + MPrescriptionNo +
                    ", PatientId=" + PatientId +
                    ", PatientName='" + PatientName + '\'' +
                    ", PatientSex=" + PatientSex +
                    ", Age=" + Age +
                    ", Mobile='" + Mobile + '\'' +
                    ", AreaName=" + AreaName +
                    ", Region=" + Region +
                    ", ApplicantId=" + ApplicantId +
                    ", ApplicantName=" + ApplicantName +
                    ", DoctorId=" + DoctorId +
                    ", DoctorName='" + DoctorName + '\'' +
                    ", MedicationCompanyId=" + MedicationCompanyId +
                    ", OrgId=" + OrgId +
                    ", OrgName='" + OrgName + '\'' +
                    ", OrgPath='" + OrgPath + '\'' +
                    ", Type=" + Type +
                    ", CostType='" + CostType + '\'' +
                    ", MedicalNo=" + MedicalNo +
                    ", MedicalRecordNo=" + MedicalRecordNo +
                    ", Department='" + Department + '\'' +
                    ", Diagnoses='" + Diagnoses + '\'' +
                    ", ValidDays=" + ValidDays +
                    ", ValidDaysRemark=" + ValidDaysRemark +
                    ", DosageDays=" + DosageDays +
                    ", DosageDaysRemark=" + DosageDaysRemark +
                    ", TotalPrice=" + TotalPrice +
                    ", PrescribeTime='" + PrescribeTime + '\'' +
                    ", Status=" + Status +
                    ", SignStatus=" + SignStatus +
                    ", AuditorId=" + AuditorId +
                    ", AuditorName='" + AuditorName + '\'' +
                    ", AuditTime='" + AuditTime + '\'' +
                    ", AuditRemark=" + AuditRemark +
                    ", RejectReasonId=" + RejectReasonId +
                    ", RandomCode=" + RandomCode +
                    ", Remark=" + Remark +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", ListDetails=" + ListDetails +
                    ", isPass=" + isPass +
                    '}';
        }

        public boolean isPass() {
            return isPass;
        }

        public void setPass(boolean pass) {
            isPass = pass;
        }

        public int getPrescriptionCategory() {
            return PrescriptionCategory;
        }

        public void setPrescriptionCategory(int PrescriptionCategory) {
            this.PrescriptionCategory = PrescriptionCategory;
        }

        public Object getPrescriptionBuyRecord() {
            return PrescriptionBuyRecord;
        }

        public void setPrescriptionBuyRecord(Object PrescriptionBuyRecord) {
            this.PrescriptionBuyRecord = PrescriptionBuyRecord;
        }

        public String getDoctorSignImgUrl() {
            return DoctorSignImgUrl;
        }

        public void setDoctorSignImgUrl(String DoctorSignImgUrl) {
            this.DoctorSignImgUrl = DoctorSignImgUrl;
        }

        public String getApothecarySignImgUrl() {
            return ApothecarySignImgUrl;
        }

        public void setApothecarySignImgUrl(String ApothecarySignImgUrl) {
            this.ApothecarySignImgUrl = ApothecarySignImgUrl;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getNo() {
            return No;
        }

        public void setNo(String No) {
            this.No = No;
        }

        public int getApplyId() {
            return ApplyId;
        }

        public void setApplyId(int ApplyId) {
            this.ApplyId = ApplyId;
        }

        public int getMPrescriptionId() {
            return MPrescriptionId;
        }

        public void setMPrescriptionId(int MPrescriptionId) {
            this.MPrescriptionId = MPrescriptionId;
        }

        public Object getMPrescriptionNo() {
            return MPrescriptionNo;
        }

        public void setMPrescriptionNo(Object MPrescriptionNo) {
            this.MPrescriptionNo = MPrescriptionNo;
        }

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }

        public int getPatientSex() {
            return PatientSex;
        }

        public void setPatientSex(int PatientSex) {
            this.PatientSex = PatientSex;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public Object getAreaName() {
            return AreaName;
        }

        public void setAreaName(Object AreaName) {
            this.AreaName = AreaName;
        }

        public Object getRegion() {
            return Region;
        }

        public void setRegion(Object Region) {
            this.Region = Region;
        }

        public int getApplicantId() {
            return ApplicantId;
        }

        public void setApplicantId(int ApplicantId) {
            this.ApplicantId = ApplicantId;
        }

        public Object getApplicantName() {
            return ApplicantName;
        }

        public void setApplicantName(Object ApplicantName) {
            this.ApplicantName = ApplicantName;
        }

        public int getDoctorId() {
            return DoctorId;
        }

        public void setDoctorId(int DoctorId) {
            this.DoctorId = DoctorId;
        }

        public String getDoctorName() {
            return DoctorName;
        }

        public void setDoctorName(String DoctorName) {
            this.DoctorName = DoctorName;
        }

        public int getMedicationCompanyId() {
            return MedicationCompanyId;
        }

        public void setMedicationCompanyId(int MedicationCompanyId) {
            this.MedicationCompanyId = MedicationCompanyId;
        }

        public int getOrgId() {
            return OrgId;
        }

        public void setOrgId(int OrgId) {
            this.OrgId = OrgId;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getOrgPath() {
            return OrgPath;
        }

        public void setOrgPath(String OrgPath) {
            this.OrgPath = OrgPath;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getCostType() {
            return CostType;
        }

        public void setCostType(String CostType) {
            this.CostType = CostType;
        }

        public Object getMedicalNo() {
            return MedicalNo;
        }

        public void setMedicalNo(Object MedicalNo) {
            this.MedicalNo = MedicalNo;
        }

        public Object getMedicalRecordNo() {
            return MedicalRecordNo;
        }

        public void setMedicalRecordNo(Object MedicalRecordNo) {
            this.MedicalRecordNo = MedicalRecordNo;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String Department) {
            this.Department = Department;
        }

        public String getDiagnoses() {
            return Diagnoses;
        }

        public void setDiagnoses(String Diagnoses) {
            this.Diagnoses = Diagnoses;
        }

        public int getValidDays() {
            return ValidDays;
        }

        public void setValidDays(int ValidDays) {
            this.ValidDays = ValidDays;
        }

        public Object getValidDaysRemark() {
            return ValidDaysRemark;
        }

        public void setValidDaysRemark(Object ValidDaysRemark) {
            this.ValidDaysRemark = ValidDaysRemark;
        }

        public int getDosageDays() {
            return DosageDays;
        }

        public void setDosageDays(int DosageDays) {
            this.DosageDays = DosageDays;
        }

        public Object getDosageDaysRemark() {
            return DosageDaysRemark;
        }

        public void setDosageDaysRemark(Object DosageDaysRemark) {
            this.DosageDaysRemark = DosageDaysRemark;
        }

        public double getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(double TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public String getPrescribeTime() {
            return PrescribeTime;
        }

        public void setPrescribeTime(String PrescribeTime) {
            this.PrescribeTime = PrescribeTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getSignStatus() {
            return SignStatus;
        }

        public void setSignStatus(int SignStatus) {
            this.SignStatus = SignStatus;
        }

        public int getAuditorId() {
            return AuditorId;
        }

        public void setAuditorId(int AuditorId) {
            this.AuditorId = AuditorId;
        }

        public String getAuditorName() {
            return AuditorName;
        }

        public void setAuditorName(String AuditorName) {
            this.AuditorName = AuditorName;
        }

        public String getAuditTime() {
            return AuditTime;
        }

        public void setAuditTime(String AuditTime) {
            this.AuditTime = AuditTime;
        }

        public Object getAuditRemark() {
            return AuditRemark;
        }

        public void setAuditRemark(Object AuditRemark) {
            this.AuditRemark = AuditRemark;
        }

        public Object getRejectReasonId() {
            return RejectReasonId;
        }

        public void setRejectReasonId(Object RejectReasonId) {
            this.RejectReasonId = RejectReasonId;
        }

        public Object getRandomCode() {
            return RandomCode;
        }

        public void setRandomCode(Object RandomCode) {
            this.RandomCode = RandomCode;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public List<ListDetailsBean> getListDetails() {
            return ListDetails;
        }

        public void setListDetails(List<ListDetailsBean> ListDetails) {
            this.ListDetails = ListDetails;
        }

        public static class ListDetailsBean {
            /**
             * Id : 1048
             * PrescriptionId : 667
             * MedicationId : 7778
             * Name : 复合维生素B片
             * Manufacturer : 华中药业股份有限公司
             * Specification : 100片
             * UsageTimeId : 0
             * UsageTime :
             * UsageMethodId : 0
             * UsageMethod : 口服
             * DayDosageId : 0
             * DayDosage : 每日三次
             * Dosage : 1.0
             * DosageUnitId : 0
             * DosageUnit : 片
             * DosageDaysId : 0
             * DosageDays :
             * Price : 8.4
             * Quantity : 1
             * PackageUnitId : 0
             * PackageUnit : 瓶
             * TotalPrice : 8.4
             * Remark : null
             * UpdateTime : 2018-11-21 19:41:22
             * CreateTime : 2018-11-21 19:41:22
             */

            private int Id;
            private int PrescriptionId;
            private int MedicationId;
            private String Name;
            private String Manufacturer;
            private String Specification;
            private int UsageTimeId;
            private String UsageTime;
            private int UsageMethodId;
            private String UsageMethod;
            private int DayDosageId;
            private String DayDosage;
            private double Dosage;
            private int DosageUnitId;
            private String DosageUnit;
            private int DosageDaysId;
            private String DosageDays;
            private double Price;
            private int Quantity;
            private int PackageUnitId;
            private String PackageUnit;
            private double TotalPrice;
            private Object Remark;
            private String UpdateTime;
            private String CreateTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getPrescriptionId() {
                return PrescriptionId;
            }

            public void setPrescriptionId(int PrescriptionId) {
                this.PrescriptionId = PrescriptionId;
            }

            public int getMedicationId() {
                return MedicationId;
            }

            public void setMedicationId(int MedicationId) {
                this.MedicationId = MedicationId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getManufacturer() {
                return Manufacturer;
            }

            public void setManufacturer(String Manufacturer) {
                this.Manufacturer = Manufacturer;
            }

            public String getSpecification() {
                return Specification;
            }

            public void setSpecification(String Specification) {
                this.Specification = Specification;
            }

            public int getUsageTimeId() {
                return UsageTimeId;
            }

            public void setUsageTimeId(int UsageTimeId) {
                this.UsageTimeId = UsageTimeId;
            }

            public String getUsageTime() {
                return UsageTime;
            }

            public void setUsageTime(String UsageTime) {
                this.UsageTime = UsageTime;
            }

            public int getUsageMethodId() {
                return UsageMethodId;
            }

            public void setUsageMethodId(int UsageMethodId) {
                this.UsageMethodId = UsageMethodId;
            }

            public String getUsageMethod() {
                return UsageMethod;
            }

            public void setUsageMethod(String UsageMethod) {
                this.UsageMethod = UsageMethod;
            }

            public int getDayDosageId() {
                return DayDosageId;
            }

            public void setDayDosageId(int DayDosageId) {
                this.DayDosageId = DayDosageId;
            }

            public String getDayDosage() {
                return DayDosage;
            }

            public void setDayDosage(String DayDosage) {
                this.DayDosage = DayDosage;
            }

            public double getDosage() {
                return Dosage;
            }

            public void setDosage(double Dosage) {
                this.Dosage = Dosage;
            }

            public int getDosageUnitId() {
                return DosageUnitId;
            }

            public void setDosageUnitId(int DosageUnitId) {
                this.DosageUnitId = DosageUnitId;
            }

            public String getDosageUnit() {
                return DosageUnit;
            }

            public void setDosageUnit(String DosageUnit) {
                this.DosageUnit = DosageUnit;
            }

            public int getDosageDaysId() {
                return DosageDaysId;
            }

            public void setDosageDaysId(int DosageDaysId) {
                this.DosageDaysId = DosageDaysId;
            }

            public String getDosageDays() {
                return DosageDays;
            }

            public void setDosageDays(String DosageDays) {
                this.DosageDays = DosageDays;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public int getQuantity() {
                return Quantity;
            }

            public void setQuantity(int Quantity) {
                this.Quantity = Quantity;
            }

            public int getPackageUnitId() {
                return PackageUnitId;
            }

            public void setPackageUnitId(int PackageUnitId) {
                this.PackageUnitId = PackageUnitId;
            }

            public String getPackageUnit() {
                return PackageUnit;
            }

            public void setPackageUnit(String PackageUnit) {
                this.PackageUnit = PackageUnit;
            }

            public double getTotalPrice() {
                return TotalPrice;
            }

            public void setTotalPrice(double TotalPrice) {
                this.TotalPrice = TotalPrice;
            }

            public Object getRemark() {
                return Remark;
            }

            public void setRemark(Object Remark) {
                this.Remark = Remark;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }

    }
}
