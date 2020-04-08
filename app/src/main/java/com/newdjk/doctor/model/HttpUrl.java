package com.newdjk.doctor.model;

import com.newdjk.doctor.BuildConfig;

public class HttpUrl {

    public static String ip = BuildConfig.IP;//？环境

    public static String ConsultWebAPI = ip + "/ConsultWebAPI";// 1、咨询、复诊：
    public static String PlatFormAPI = ip + "/PlatFormAPI";//	平台接口	包含用户、病人、角色权限、服务包以及知识库等各公共模块接口
    public static String ConsultAPI = ip + "/ConsultAPI";//		咨询、复诊接口	和咨询以及复诊相关接口
    public static String FetalHeartAPI = ip + "/FetalHeartAPI";//		母胎接口	和胎心监护相关接口
    public static String PRESCRIPTION = ip + "/Prescription";//		处方接口	处方相关接口
    public static String SSOAPI = ip + "/SSOAPI";//		医生  SSO接口	登录以及接口授权认证相关接口
    public static String ContinueServiceAPI = ip + "/ContinueServiceAPI";//		延续服务接口	延续服务接口
    public static String IMAPI = ip + "/IMAPI";//		获取IM信息接口
    public static String CONSULT = ConsultAPI + "/Consult"; //咨询记录控制器api
    public static String DOCTORPATIENT = PlatFormAPI + "/DoctorPatient"; //医护和患者信息
    public static String Prescription = PRESCRIPTION + "/Prescription";//		处方接口	处方相关接口
    public static String Pharmacy = PRESCRIPTION + "/Pharmacy";//		医药公司与门店 控制器api
    public static String PatientArchives = PlatFormAPI + "/PatientArchives";//		健康档案
    public static String PatientAccount = PlatFormAPI + "/PatientAccount";//		健康档案
    public static String KnowledgeBase = PlatFormAPI + "/KnowledgeBase";//		知识库(宣教、问卷)
    public static String Message = IMAPI + "/Message";//		IM即时通讯-消息 控制器api
    public static String AppMessage = PlatFormAPI + "/AppMessage";//		APP消息

    public static String SecondDiagnosisAPI = ip + "/SecondDiagnosisAPI";//	第二诊疗
    public static String MedicalService = SecondDiagnosisAPI + "/MedicalService";//	服务介绍、疾病相关
    public static String MedicalRecord = SecondDiagnosisAPI + "/MedicalRecord";//	诊疗记录相关接口
    public static String Medical = SecondDiagnosisAPI + "/Medical";//	主要问题和病历相关接口
    public static String DoctorReferral = PlatFormAPI + "/DoctorReferral"; //医生转诊操作
    public static String Banner = ip + "/AdAPI/";//		获取首页的banner数据
    public static String StatisticAPI = ip + "/StatisticAPI";//		获取首页的banner数据
    public static String AppointmentAPI = ip + "/AppointmentAPI";//		面诊预约
    public static String ActivityAPI = ip + "/ActivityAPI";//		活动
    public static String MDTAPI = ip + "/MDTAPI";//		MDT



    //    http://172.18.30.4/SSOAPI/Patient/Login";//登录
    //========================登陸===========================
    public static String LoginSendMobileCode = SSOAPI + "/PassPort/SendMobileCode";//登录f发送验证码
    public static String DoctorLogin = SSOAPI + "/Doctor/Login";//登录医生
    public static String Logout = SSOAPI + "/PassPort/Logout";//退出登录
    public static String loginIm = IMAPI + "/Account/GetIMAccount";//获取IM用户名和签名

    // ===========================  Doctor  ( 医护 )============================================
    public static String Doctor = PlatFormAPI + "/Doctor";
    //    POST Doctor/SendMobileCode  发送医生手机验证码
    public static String SendMobileCode = Doctor + "/SendMobileCode";
    //    POST Doctor/VerifyMobileCode  验证医生手机验证码(注册验证)
    public static String VerifyMobileCode = Doctor + "/VerifyMobileCode";
    //    POST Doctor/DoctorRegister  医护注册
    public static String DoctorRegister = Doctor + "/DoctorRegister";
    //    POST Doctor/QueryDoctorByMobileCode  根据手机号码和验证码获取医生信息
    public static String QueryDoctorByMobileCode = Doctor + "/QueryDoctorByMobileCode";
    //    POST Doctor/ChangeDoctorPassword  修改医生密码(医生Id)
    public static String ChangeDoctorPassword = Doctor + "/ChangeDoctorPassword";
    //    GET Doctor/QueryDoctorInfoByDrId?DrId={DrId}  根据医生Id获取医生信息
    public static String QueryDoctorInfoByDrId = Doctor + "/QueryDoctorInfoByDrId";
    //    POST Doctor/UpdateDoctorInfo  修改医生信息
    public static String UpdateDoctorInfo = Doctor + "/UpdateDoctorInfo";
    //    POST Doctor/QueryDoctorInfoByPage  获取医生列表(查找医生) 综合排序：选项包括护理咨询量(ConsultCount)、图文问诊量(ConsultVolume)、视频问诊量(VolumeVideo)、好评率(PraiseRate)、24 小时回复率(ResponseRate)
    public static String QueryDoctorInfoByPage = Doctor + "/QueryDoctorInfoByPage";
    //    GET Doctor/QueryDoctorInfoForHot?HosGroupId={HosGroupId}&OrgId={OrgId}&DrType={DrType}  获取热门医生列表
    public static String QueryDoctorInfoForHot = Doctor + "/QueryDoctorInfoForHot";
    //    POST Doctor/DoctorImagUpload  医生证照\签名\头像上传
    public static String DoctorImagUpload = Doctor + "/DoctorImagUpload";
    //    POST Doctor/DoctorImagSave  医生证照保存(批量保存)
    public static String DoctorImagSave = Doctor + "/DoctorImagSave";
    //    GET Doctor/QueryDoctorRegImagByDrId?DrId={DrId}  根据医生Id获取医生证照
    public static String QueryDoctorRegImagByDrId = Doctor + "/QueryDoctorRegImagByDrId";
    //    GET Doctor/DeleteDoctorRegImag?DrRegImgId={DrRegImgId}  根据主键Id删除医生证照
    public static String DeleteDoctorRegImag = Doctor + "/DeleteDoctorRegImag";
    //    GET Doctor/QueryDoctorSignByDrId?DrId={DrId}  根据医生Id获取医生签名
    public static String QueryDoctorSignByDrId = Doctor + "/QueryDoctorSignByDrId";
    //    POST Doctor/DoctorSignSave  新增或者修改医生签名
    public static String DoctorSignSave = Doctor + "/DoctorSignSave";
    //    GET Doctor/QueryDoctorQRCodeByDrId?DrId={DrId}  根据医生Id获取医生二维码(二维码名片)
    public static String QueryDoctorQRCodeByDrId = Doctor + "/QueryDoctorQRCodeByDrId";
    //    POST Doctor/QueryHospitalPage  获取医院列表
    public static String QueryHospitalPage = Doctor + "/QueryHospitalPage";
    //    POST Doctor/QueryHospitalForHot?AreaId={AreaId}  获取热门医院列表
    public static String QueryHospitalForHot = Doctor + "/QueryHospitalForHot";
    //    POST Doctor/QueryDepartmentPage  获取科室列表
    public static String QueryDepartmentPage = Doctor + "/QueryDepartmentPage";
    //    POST Doctor/QueryDepartmentForHot  获取热门科室列表
    public static String QueryDepartmentForHot = Doctor + "/QueryDepartmentForHot";
    //    GET Doctor/QueryDoctorBankCardByDrId?DrId={DrId} 根据医生Id获取医生银行卡
    public static String QueryDoctorBankCardByDrId = Doctor + "/QueryDoctorBankCardByDrId";
    //    POST Doctor/SaveDoctorBankCard 新增银行卡信息
    public static String SaveDoctorBankCard = Doctor + "/SaveDoctorBankCard";
    //    GET Doctor/DeleteDoctorBankCard?DrBankCardId={DrBankCardId} 删除银行卡信息
    public static String DeleteDoctorBankCard = Doctor + "/DeleteDoctorBankCard";
    //    GET Doctor/QueryDoctorInSummaryByDrId?DrId={DrId} 根据医生Id获取医生收入信息
    public static String QueryDoctorInSummaryByDrId = Doctor + "/QueryDoctorInSummaryByDrId";
    //    GET Doctor/QueryDoctorMonSummaryByDrId?DrId={DrId}&InYear={InYear} 根据医生Id和年份获取医生收入信息
    public static String QueryDoctorMonSummaryByDrId = Doctor + "/QueryDoctorMonSummaryByDrId";
    //    GET Doctor/QueryDoctorInDetailByPage 获取医生收入明细信息
    public static String QueryDoctorInDetailByPage = Doctor + "/QueryDoctorInDetailByPage";
    //    GET Doctor/UpdateDoctorOnline?DrId={DrId}&IsOnline={IsOnline} 修改医生是否出诊(出诊开关)
    public static String UpdateDoctorOnline = Doctor + "/UpdateDoctorOnline";
    public static String IsUploadDoctorRegImag = Doctor + "/IsUploadDoctorRegImag";   //医生是否已经上传证照
    public static String QueryDoctorAppMessageByPage = Doctor + "/QueryDoctorAppMessageByPage";   //获取医生APP消息分页
    public static String GetDoctorAuthenInfo = Doctor + "/GetDoctorAuthenInfo";   //获取医生资质认证信息


    public static String Category = PlatFormAPI + "/Category";
    public static String QueryItemByCategoryId = Category + "/QueryItemByCategoryId";

    //咨询
    public static String CheckConsultNumberSource = CONSULT + "/CheckConsultNumberSource";//判断医生是否还有咨询号源
    public static String SaveConsultRecord = CONSULT + "/SaveConsultRecord";  //保存咨询记录
    public static String UpdateConsultRecordStatus = CONSULT + "/UpdateConsultRecordStatus"; //修改咨询记录状态
    public static String GetConsultRecordList = CONSULT + "/GetConsultRecordList"; //获取咨询记录列表(医生端)
    public static String GetConsultRecordByPatientDoctor = CONSULT + "/GetConsultRecordByPatientDoctor"; //获取会员对指定医生的咨询记录
    public static String GetConsultRecord = CONSULT + "/GetConsultRecord"; //获取咨询记录
    public static String GetConsultSetting = CONSULT + "/GetConsultSetting"; //获取咨询服务项开通信息(包括图文咨询和护理咨询) 图文咨询针对医生，护理咨询针对护士
    public static String SaveConsultSetting = CONSULT + "/SaveConsultSetting"; //保存咨询服务项开通设置
    public static String SaveInquiryRecord = CONSULT + "/SaveInquiryRecord"; //保存问诊记录
    public static String SaveReserveRecord = CONSULT + "/SaveReserveRecord"; //保存问诊预约记录
    public static String UpdateInquiryRecordStatus = CONSULT + "/UpdateInquiryRecordStatus"; //修改问诊记录状态
    public static String GetInquiryRecordList = CONSULT + "/GetInquiryRecordList"; //获取问诊记录列表(医生端)
    public static String GetDoctorDutyComplexList = CONSULT + "/GetDoctorDutyComplexList"; //获取视频问诊首页医生排班(医生端)
    public static String GetDoctorComingDutyList = CONSULT + "/GetDoctorComingDutyList"; //获取医生即将开始的排班列表
    public static String GetInquiryRecord = CONSULT + "/GetInquiryRecord"; //获取问诊记录
    public static String GetReserveRecord = CONSULT + "/GetReserveRecord"; //获取预约记录
    public static String GetReserveRecordByInquiryId = CONSULT + "/GetReserveRecordByInquiryId"; //获取预约记录（根据问诊记录Id）
    public static String GetInquirySetting = CONSULT + "/GetInquirySetting"; //获取问诊服务项开通信息(包括视频问诊和远程护理) 视频问诊针对医生，远程护理针对护士
    public static String SaveInquirySetting = CONSULT + "/SaveInquirySetting"; //保存问诊服务项开通设置
    public static String GetServiceTypeOfPatientDoctor = CONSULT + "/GetServiceTypeOfPatientDoctor"; //获取医生的指定会员正在进行的服务类型

    //医护和患者信息
    public static String QueryDoctorPatientSummary = DOCTORPATIENT + "/QueryDoctorPatientSummary";//患者档案
    public static String QueryDoctorSummary = DOCTORPATIENT + "/QueryDoctorSummary";//我的成就(医生)
    public static String QueryDoctorINcome = StatisticAPI + "/IncomeActive/QueryDoctorInSummaryByDrId";//我的收入

    public static String QueryDoctorPatientPage = DOCTORPATIENT + "/QueryDoctorPatientPage";//我的患者/新报道患者/重点关注(通过不同的查询条件区分)
    public static String QueryPatientMainDoctorPage = DOCTORPATIENT + "/QueryPatientMainDoctorPage";//我的主诊医生
    public static String SaveDoctorPatient = DOCTORPATIENT + "/SaveDoctorPatient";//新增医生患者(设置主诊医生)
    public static String SetDrKey = DOCTORPATIENT + "/SetDrKey";//设置医生重点关注患者
    public static String SetRelation = DOCTORPATIENT + "/SetRelation";//设置医生新报道患者
    public static String QueryDoctorEvaluationByPage = DOCTORPATIENT + "/QueryDoctorEvaluationByPage";//获取评价列表(医生/会员-我的评价列表)(通过不同的查询条件区分)
    public static String UpdatePatientDrRemark = DOCTORPATIENT + "/UpdatePatientDrRemark";//患者档案-备注
    public static String DoctorEvImgUpload = DOCTORPATIENT + "/DoctorEvImgUpload";//评价图片上传
    public static String SaveDoctorEvaluation = DOCTORPATIENT + "/SaveDoctorEvaluation";//评价(患者评价)
    //处方单
    public static String GetPrescriptionSetting = Prescription + "/GetPrescriptionSetting";//获取续方开通设置
    public static String SavePrescriptionSetting = Prescription + "/SavePrescriptionSetting";//保存续方服务项开通设置
    public static String GetApplyRecordList = Prescription + "/GetApplyRecordList";//获取续方记录分页列表(手机端)
    public static String UpdatePrescriptionApplyStatus = Prescription + "/UpdatePrescriptionApplyStatus";//修改申请单状态（1：待处理，2：已接收，3：被驳回）
    public static String GetMyMedicationCompany = Pharmacy + "/GetMyMedicationCompany";//获取我的供药单位
    public static String SetMyMedicationCompany = Pharmacy + "/SetMyMedicationCompany";//设置我的供药单位
    public static String GetPrescription = Prescription + "/GetPrescription";//获取续方订单消息
    public static String GetPrescriptionApply = Prescription + "/GetPrescriptionApply";//查看续方申请单

    //首页的ad
    public static String QueryAdBannerInfo = Banner + "/Ads/QueryAdsList";
    //病历
    public static String ReportImageUpload = PatientArchives + "/ReportImageUpload"; //患者病历上传
    //群发助手
    public static String GroupsAssistant = PlatFormAPI + "/Memberfeatures/SendGroupMessage";
    //群发历史
    public static String QueryGroupMessagePage = PlatFormAPI + "/Memberfeatures/QueryGroupMessagesPage";
    //再发一条
    public static String ResendGroupMessage = PlatFormAPI + "/Memberfeatures/ResendGroupMessages";
    //根据父Id获取区域信息
    public static String QueryAreaByParentId = PlatFormAPI + "/Area/QueryAreaByParentId";
    //意见与反馈图片上传
    public static String FeedbackImgLoad = PlatFormAPI + "/DoctorPatient/FeedBackImgUpload";
    //群发助手图片上传
    public static String SendGroupImageUpload = PlatFormAPI + "/Memberfeatures/SendGroupImageUpload";
    //保存意见与反馈
    public static String AddFeedBack = PlatFormAPI + "/DoctorPatient/AddFeedBack";
    //关于我们
    public static String GetAboutInfo = KnowledgeBase + "/GetAboutInfo";
    //我的服务设置 QueryDoctorServiceSet
    public static String ServicePackItem = PlatFormAPI + "/ServicePackItem/QueryDoctorServiceSet";
    //注册返回修改基础信息
    public static String UpdateBasicDoctorInfo = Doctor + "/UpdateBasicDoctorInfo";
    //获取app版本信息
    public static String GetAppManage = KnowledgeBase + "/GetAppManage";

    //获取医生判读统计
    public static String getDoctorData = FetalHeartAPI + "/Doctor/GetReadCount";
    public static String getAllReadData = FetalHeartAPI + "/Doctor/GetReadByPage";
    public static String getAllReadDataDetail = FetalHeartAPI + "/Doctor/GetAskDetail";
    public static String getAllDoctorSetting = FetalHeartAPI + "/Doctor/GetReadingSet";
    public static String submitAllDoctorSetting = FetalHeartAPI + "/Doctor/SubmitReadSet";
    public static String submitSignReport = FetalHeartAPI + "/Doctor/SubmitRead";
    public static String getchatData = FetalHeartAPI + "/Monitor/GetMonitorData";
    public static String getScore = FetalHeartAPI + "/Monitor/GetScore";
    public static String getReportDetail = FetalHeartAPI + "/Monitor/GetReportDetail";

    //IM即时通讯-消息 控制器api
    public static String GetAllRecordForDoctor = Message + "/GetAllRecordForDoctor";
    public static String GetChatHistoryByPage = Message + "/GetChatHistoryByPage";
    public static String GetGroupChatHistoryByPage = Message + "/GetGroupChatHistoryByPage";


    //处方审核
    public static String PrescriptionCheckList = PRESCRIPTION + "/Prescription/GetPrescriptionListOfAuditor";
    //修改订单状态
    public static String changeDealStatus = PRESCRIPTION + "/Prescription/AuditPrescription";
    //审方师审核


    public static String getPrescriptionDetail = PRESCRIPTION + "/Prescription/GetApothecaryStatistics";
    public static String Read = AppMessage + "/Read";    //APP消息已读

    //获取当前准确时间
    //关于我们
    public static String getCurrentTime = KnowledgeBase + "/GetNowDateTime";

    //获取处方单信息
    public static String getetPrescriptionDta = PRESCRIPTION + "/Prescription/GetPrescription";

    //获取中药处方单信息
    public static String getetTCMPrescriptionDta = PRESCRIPTION + "/Prescription/GetTCMPrescription";
    public static String QueryDoctorDiseases = MedicalService + "/QueryDoctorDiseases";//	获取医生适应病症
    public static String SaveDoctorDisease = MedicalService + "/SaveDoctorDisease";//	保存医生适应病症
    public static String GetSecondDiagnosisSetting = MedicalService + "/GetSecondDiagnosisSetting";//	获取第二诊疗意见设置
    public static String SaveSecondDiagnosisSetting = MedicalService + "/SaveSecondDiagnosisSetting";//保存第二诊疗意见设置
    public static String QueryDiseaseByPage = MedicalService + "/QueryDiseaseByPage";//获取服务涵盖的疾病
    public static String QueryDoctorMedicalRecords = MedicalRecord + "/QueryDoctorMedicalRecords";//获取医生第二诊疗记录
    public static String GetDoctorMedicalStatistics = MedicalRecord + "/GetDoctorMedicalStatistics";//根据id获取医生累计报告
    public static String GetMedicalItemsByMedicalRecordId = Medical + "/GetMedicalItemsByMedicalRecordId";//根据问诊id获取病历项列表(医生选择补充资料)
    public static String SendNeedSupplyMedical = Medical + "/SendNeedSupplyMedical";//医生发送补充病历
    public static String QueryLookMedical = Medical + "/QueryLookMedical";//查看病历(只看)

    public static String DoctorOrderTaking = MedicalRecord + "/DoctorOrderTaking";//第二诊疗医生接单
    public static String DoctorOrderCancel = MedicalRecord + "/DoctorOrderCancel";//第二诊疗医生退单
    public static String DrAddDefinedMedical = Medical + "/DrAddDefinedMedical";//医生添加自定义病历
    public static String DoctorSendReport = MedicalRecord + "/DoctorSendReport";//医生发送第二诊疗报告
    public static String DeleteDefinedMedical = Medical + "/DeleteDefinedMedical";//删除自定义病例
    public static String GetMedicalRecordById = MedicalRecord + "/GetMedicalRecordById";//根据第二诊疗问诊id获取第二诊疗问诊详情
    public static String QueryDoctorDrStatus = Doctor + "/QueryDoctorDrStatus";//查询医生认证状态
    public static String sendRepoetTosign = MedicalRecord + "/DoctorMedicalReportSign";//查询医生认证状态
    public static String signSuccess = MedicalRecord + "/DoctorSignComplete";//签名完成

    //语音识别
   // public static String getTextByAudio = "http://asr-prod.yitutech.com/v1/speech/asr";
    public static String getTextByAudio = "http://asr-prod.yitutech.com/v2/asr";
    public static String isDoctorSign=Doctor +"/GetDoctorCurrentIntegral";
    public static String sign=Doctor +"/DoctorSigninByDrId";
    public static String getSignList=Doctor +"/GetIntegralSet";
    public static String getMypoint=Doctor +"/GetDoctorIntegralRecordByDrId";
    public static String getDoctorQrcode=Doctor +"/GetDoctorRecommendCode";
    public static String DoctorRecommends=Doctor +"/DoctorRecommends";
    public static String IsHasOpenPres=Doctor +"/IsHasOpenPres";
    public static String IsHasOpenTCMPres=Doctor +"/IsHasOpenTCMPres";

    public static String ExistDoctorDisGroup=DoctorReferral +"/ExistDoctorDisGroup";  //医生是否已经设置疾病分组
    public static String openDoctorDisGroup=DoctorReferral +"/OpenDrReferral";  //开通分级转诊

    public static String QueryDoctorIdByQRCodeUrl = PlatFormAPI + "/Doctor/QueryDoctorIdByQRCodeUrl";//  根据扫描结果
    public static String ScanDoctorQRCode = DoctorReferral + "/ScanDoctorQRCode";//  扫码转诊
    public static String QueryDocReferralRecordPage = DoctorReferral + "/QueryDocReferralRecordPage";//  查询记录
    public static String QueryReferralRecordCount = DoctorReferral + "/QueryReferralRecordCount";//  查询记录
    public static String SaveReferralRecordBack = DoctorReferral + "/SaveReferralRecordBack";//  撤回
    public static String sendVideo = PlatFormAPI + "/AppMessage/CallVideoPush";//  撤回
    public static String signChufang = Prescription + "/SignPrescription";//  告诉后台开处方成功

    public static String GetIMRelationRecord = Message + "/GetIMRelationRecord";  //获取IM 消息关联业务记录
    public static String SendGoodsRecommend = PlatFormAPI + "/SpecialistHosGroup/SendGoodsRecommend";  //发送推荐
    public static String GetGoodsRecommendWxShareConten = PlatFormAPI + "/SpecialistHosGroup/GetGoodsRecommendWxShareContent";  //获取推荐标题
    public static String GetDrFaceConsultationRecord = AppointmentAPI + "/FaceConsultation/GetDrFaceConsultationRecord";  //面诊预约记录
    public static String CancelFaceConsultation = AppointmentAPI + "/FaceConsultation/CancelFaceConsultation";  //取消
    public static String GetFaceConsultationDetail = AppointmentAPI + "/FaceConsultation/GetFaceConsultationDetail";  //获取详情
    public static String SendWXMessageToPatient = ActivityAPI + "/GroupBuy/SendWXMessageToPatient";  //获取详情
    public static String QueryServiceItemDivideRation = PlatFormAPI + "/ServicePackItem/QueryServiceItemDivideRation";  //获取详情
    public static String SaveMDTConsultSetting = MDTAPI + "/MDT/SaveMDTConsultSetting";  //获取详情
    public static String QueryUnReceiveBuyRecordPage = MDTAPI + "/MDT/QueryUnReceiveBuyRecordPage";  //(1-接单，2-抢单
    public static String GetMDTConsultSetting = MDTAPI + "/MDT/GetMDTConsultSetting";  //(1-接单，2-抢单
    public static String QueryDoctorMDTReportPage = MDTAPI + "/MDT/QueryDoctorMDTReportPage";  //分页获取医生的报告/医生的订单
    public static String QueryMDTBySubjectBuyIMGroupId = MDTAPI + "/MDT/QueryMDTBySubjectBuyIMGroupId";  //分页获取医生的报告/医生的订单
    public static String QueryDrSubjectBuyRecordDetail = MDTAPI + "/MDT/QueryDrSubjectBuyRecordDetail";  //分页获取医生的报告/医生的订单
    public static String ReceiveOrRejecteMDT = MDTAPI + "/MDT/ReceiveOrRejecteMDT";  //接诊或者矩阵
    public static String SendDoctorAdvice = MDTAPI + "/MDT/SendDoctorAdvice";  //发送报告
    public static String SendDoctorFinalAdvice = MDTAPI + "/MDT/SendDoctorFinalAdvice";  //发送报告
    public static String QueryDrSpecialistHosGroupForPage = PlatFormAPI + "/SpecialistHosGroup/QueryDrSpecialistHosGroupForPage";  //发送报告
    public static String RemindDoctorAdvice = MDTAPI + "/MDT/RemindDoctorAdvice";  //提醒
    public static String QueryMDTBySubjectBuyRecordId = MDTAPI + "/MDT/QueryMDTBySubjectBuyRecordId";  //提醒
    public static String QuerySupplyMDTMedical = MDTAPI + "/MDTMedical/QuerySupplyMDTMedical";  //提醒
    public static String MdtDrAddDefinedMedical = MDTAPI + "/MDTMedical/DrAddDefinedMedical";  //提醒
    public static String MDTSendNeedSupplyMedical = MDTAPI + "/MDTMedical/SendNeedSupplyMedical";  //发送材料
    public static String MDTDeleteDefinedMedical = MDTAPI + "/MDTMedical/DeleteDefinedMedical";  //删除材料
    public static String CloseMDT = MDTAPI + "/MDT/CloseMDT";  //删除材料
    public static String QueryManySubjectDoctor = MDTAPI + "/MDT/QueryManySubjectDoctor";  //删除材料
    public static String IsOpenMDT = MDTAPI + "/MDT/IsOpenMDT";  //是否开通MDT
    public static String GetNoticeManagePageList = PlatFormAPI + "/HealthInformation/GetNoticeManagePageList";  //公告管理-列表
    public static String QueryCountDoctorOrderMerge = PlatFormAPI + "/PayOrder/QueryCountDoctorOrderMerge";  //公告管理-列表
    public static String GetNoticeManageById = PlatFormAPI + "/HealthInformation/GetNoticeManageById";  //公告管理-列表
    public static String QueryUnReceiveDoctorOrderMerge = PlatFormAPI + "/PayOrder/QueryUnReceiveDoctorOrderMerge";  //待处理-待问诊(医生端)
    public static String QueryDoctorOrderMergePage = PlatFormAPI + "/PayOrder/QueryDoctorOrderMergePage";  //待处理-待问诊(医生端)
    public static String SaveDrServiceItemPrice = Doctor + "/SaveDrServiceItemPrice";  //待处理-待问诊(医生端)
    public static String GetDrServiceItemPriceForSet = Doctor + "/GetDrServiceItemPriceForSet";  //待处理-待问诊(医生端)


}
