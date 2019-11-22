package com.newdjk.doctor.ui.entity;

public class ChickUnitEntity {

    /**
     * IsDefault : 1
     * Id : 1
     * Name : 新起点药房
     * Code : XQDYF
     * PicturePath : null
     * Type : 1
     * DeliveryMethod : 1,2
     * BuyWaysOfFetch : 1,2
     * IsSupportInsure : 0
     * IsSendReport : 0
     * Email : 0
     * Contact : 0755-88888888
     * ServiceScope : null
     * FetchAttention : 来吧，自取吧
     * ExpressPromise : 快递提示语，问题
     * FlashAttention : 闪送提示语
     * InsureAttention : 保价提示语
     * MessageMobile : null
     * DockingMode : Xqd
     * DockingParam : null
     * Description : 无
     * Invalid : 0
     * CreateTime : 2018-09-11 15:45:35
     * UpdateTime : 2019-01-23 16:00:33
     */

    private int IsDefault;
    private int Id;
    private String Name;
    private String Code;
    private Object PicturePath;
    private int Type;
    private String DeliveryMethod;
    private String BuyWaysOfFetch;
    private int IsSupportInsure;
    private int IsSendReport;
    private String Email;
    private String Contact;
    private String ServiceScope;
    private String FetchAttention;
    private String ExpressPromise;
    private String FlashAttention;
    private String InsureAttention;
    private String MessageMobile;
    private String DockingMode;
    private String DockingParam;
    private String Description;
    private int Invalid;
    private int MedicationCount;
    private int ActivityNum;

    public int getActivityNum() {
        return ActivityNum;
    }

    public void setActivityNum(int activityNum) {
        ActivityNum = activityNum;
    }

    public int getMedicationCount() {
        return MedicationCount;
    }

    public void setMedicationCount(int medicationCount) {
        MedicationCount = medicationCount;
    }

    private String CreateTime;
    private String UpdateTime;

    public int getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(int IsDefault) {
        this.IsDefault = IsDefault;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public Object getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(Object PicturePath) {
        this.PicturePath = PicturePath;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getDeliveryMethod() {
        return DeliveryMethod;
    }

    public void setDeliveryMethod(String DeliveryMethod) {
        this.DeliveryMethod = DeliveryMethod;
    }

    public String getBuyWaysOfFetch() {
        return BuyWaysOfFetch;
    }

    public void setBuyWaysOfFetch(String BuyWaysOfFetch) {
        this.BuyWaysOfFetch = BuyWaysOfFetch;
    }

    public int getIsSupportInsure() {
        return IsSupportInsure;
    }

    public void setIsSupportInsure(int IsSupportInsure) {
        this.IsSupportInsure = IsSupportInsure;
    }

    public int getIsSendReport() {
        return IsSendReport;
    }

    public void setIsSendReport(int IsSendReport) {
        this.IsSendReport = IsSendReport;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getServiceScope() {
        return ServiceScope;
    }

    public void setServiceScope(String ServiceScope) {
        this.ServiceScope = ServiceScope;
    }

    public String getFetchAttention() {
        return FetchAttention;
    }

    public void setFetchAttention(String FetchAttention) {
        this.FetchAttention = FetchAttention;
    }

    public String getExpressPromise() {
        return ExpressPromise;
    }

    public void setExpressPromise(String ExpressPromise) {
        this.ExpressPromise = ExpressPromise;
    }

    public String getFlashAttention() {
        return FlashAttention;
    }

    public void setFlashAttention(String FlashAttention) {
        this.FlashAttention = FlashAttention;
    }

    public String getInsureAttention() {
        return InsureAttention;
    }

    public void setInsureAttention(String InsureAttention) {
        this.InsureAttention = InsureAttention;
    }

    public Object getMessageMobile() {
        return MessageMobile;
    }

    public void setMessageMobile(String MessageMobile) {
        this.MessageMobile = MessageMobile;
    }

    public String getDockingMode() {
        return DockingMode;
    }

    public void setDockingMode(String DockingMode) {
        this.DockingMode = DockingMode;
    }

    public Object getDockingParam() {
        return DockingParam;
    }

    public void setDockingParam(String DockingParam) {
        this.DockingParam = DockingParam;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int Invalid) {
        this.Invalid = Invalid;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    @Override
    public String toString() {
        return "ChickUnitEntity{" +
                "IsDefault=" + IsDefault +
                ", Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Code='" + Code + '\'' +
                ", PicturePath=" + PicturePath +
                ", Type=" + Type +
                ", DeliveryMethod='" + DeliveryMethod + '\'' +
                ", BuyWaysOfFetch='" + BuyWaysOfFetch + '\'' +
                ", IsSupportInsure=" + IsSupportInsure +
                ", IsSendReport=" + IsSendReport +
                ", Email='" + Email + '\'' +
                ", Contact='" + Contact + '\'' +
                ", ServiceScope='" + ServiceScope + '\'' +
                ", FetchAttention='" + FetchAttention + '\'' +
                ", ExpressPromise='" + ExpressPromise + '\'' +
                ", FlashAttention='" + FlashAttention + '\'' +
                ", InsureAttention='" + InsureAttention + '\'' +
                ", MessageMobile='" + MessageMobile + '\'' +
                ", DockingMode='" + DockingMode + '\'' +
                ", DockingParam='" + DockingParam + '\'' +
                ", Description='" + Description + '\'' +
                ", Invalid=" + Invalid +
                ", CreateTime='" + CreateTime + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                '}';
    }
}
