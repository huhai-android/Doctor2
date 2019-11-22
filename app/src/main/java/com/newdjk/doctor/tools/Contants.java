package com.newdjk.doctor.tools;


import com.newdjk.doctor.BuildConfig;

/**
 * 常量工具类
 *
 * @author Lemon
 * @warn TODO 修改里面所有常量
 */
public class Contants {
    public static final String IS_FIRST_USE = "IS_FIRST_USE";//判断第一次安装app
    /**
     * 用户名
     */
    public static final String userName = "userName";//用户名
    public static final String Id = "Id";//用户id
    public static final String Name = "Name";//名字
    public static final String Sex = "Sex";//性别
    public static final String Token = "Token";//
    public static final String OrgName = "OrgName";
    public static final String Password = "Password";
    public static final String Status = "Status";
    public static final String DocType = "DocType";
    public static final String LoginJson = "LoginJson";
    public static final String OrgId = "OrgId";//用户名
    public static final String IsUploadDoctorRegImag = "IsUploadDoctorRegImag";
    public static final String identifier = "identifier";
    public static final String Title = "title";
    public static String categoryItemId = "categoryItemId";
    public static final String Position = "position";
    public static final String PositionName = "PositionName";
    public static final String IsIgnore = "IsIgnore";
    public static final String JobName = "JobName";//职称
    public static final String UserSig = "userSig";//职称
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
//    public static final String Token = "Token";//
    /**
     * 接入qq登录接口的app_id
     */
    public static final String APP_ID = "1105887603";

    public static final String QCLOUD_BASE_API = "https://console.tim.qq.com/v4/";

    public static final String QCLOUD_MANAGER = "admin";
    public static final String FRIEND_IDENTIFIER = "identifier";
    public static final String FRIEND_NAME = "friend_name";

    /**
     * 第一次进入应用时登录态是否有效
     */
    public static boolean isSessionValid = false;

    /**
     * 接入腾讯云服务的帐号集成体系所用的accountType
     */
    public static final int ACCOUNT_TYPE = 36176;  //10879

    /**
     * 接入腾讯云服务的SdkAppId
     */
    public static final long SDK_APP_ID = 1400129246; //1400025340


    /**
     * 接入腾讯云服务的public_key
     */
    public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEz3IZ7neLwljaJ/zIue4ZK3VSevEUVQMH\n" +
            "V946UZ3cFO11oo7/GmJ1XXfiIx/IguBxtnyoW8+J4DOJozYEyrowqA==\n" +
            "-----END PUBLIC KEY-----";

    /**
     * 接入腾讯云服务的sig
     */
   /* public static final String SIG = "eJx10E9PwjAYx-H7XkXTszHVtVtn4mF-cEIQdDphp6as3XwilLIVojG*dy" +
            "OQuIvP9fdJvsnz5SGE8Mv0*VLW9XZvnHCfVmN0g-AV4WGEL-6AtaCEdMLv1AlQQsg18ykZKP1hodNCNk53J" +
            "8XCgJPfGyhQ2jho4Gyk2oAZzL16F8fc-50e2uP4MCrT8d0ki0xSRMv0yaQHv91t87U-74uEsYmN62RKZ1wl" +
            "arUqeQyjmGX5bJH0jwd4m5Oy2S9tcZ-tSJlX1diphaVttn6VjNu2uh0kHWzOn6Gcs4DQMMDet-cDeWVXRg__";*/
    public static final String SIG = "eJxlj0tPwkAUhff9FU23GjNPTd1ZilhBdBR5uJn0MYUJMp1MLwVr-O-GSmIT7-b7cs65n57v*8Fs8nKR5nm1NyDhw6rAv-YDFJz-QWt1IVOQ1BX-oDpa7ZRMS1Cug5hzThDqO7pQBnSpT4ZRhxpSB3nqVM*qi63sqn5jGEKYhIRd9hW97uDDcDVIRPxeTt-yahtlesT0csme3TxbNFH72j7y2f34bhFyU*zatnkSyeZmUmJ7EHQwik12O4Toan02tQ1ezZNNnB0Fa0NRZWNXwx56laB36vQX5SENCe0PapSrdWU6gSDMMaHo5wLvy-sGNChhaQ__";
    // public static final String SIG = "eJxlz0FvgjAUwPE7n6LhumVrqRVdsoNRMg3iwnCDnRpCS61G6GpBwOy7L0OTkexdf-*Xl3exAAD2dh09pFlWVoWhplXcBk-Ahvb9HyolGU0NxZr9Q94oqTlNc8N1j4gQ4kA4bCTjhZG5vBUdUwM8sQPtL1y3RxAijKaT8TCRosfAe5*vwjl7bccN7pC7SPzKIzAJX461k70Vn2wp*A6RWSZkFJ9NMFuJIE68bnd*DJblxx32m269z-ViX*btYRNVxg*-VB5PSb3B4nlw0sgjv72DCXZH0J0MtOb6JMuiDxyICHIw-B3b*rZ*AIV4XU0_";
    /**
     * 腾讯云登录返回的sig
     */
    public static String LOGIN_SIG = "login_sig";

    /**
     * 腾讯云登录返回的identifier
     */
    public static String LOGIN_IDENTIFIER = "login_identifier";

    // public static String clientId  = "2018090310541968"; //测试医签网ID
    public static String clientId = BuildConfig.CLIENTID; //正式医签网ID
    public static String APPKEY = "2807886627";
    public static String AppSecret = "971a2d3e328546e3d38408a4c21ca92f";
    public static String Environment = "Environment";
    public static String USEJSON = "usejson";
    public static String ALLJSON = "alljson";
    public static boolean isDownload = false;
    public static final String ImId = "ImId";
    public static final String patientID = "patientID";
    public static final String patientName = "patientName";
    public static final String canLogin = "canLogin";
    public static final String chosearea = "chosearea";
    public static final String customHospital = "customHospital";
    public static final String chooseAreaID = "chooseAreaID";
    public static final String isShowIncome = "isShowIncome";
    public static final String GroupID= "GroupID";
    public static final String GroupNAME= "GroupNAME";
    public static final String PatientID= "PatientID";
    public static final String Type= "Type";
    public static final String MDTINFO= "MDTINFO";
    public static final String SubjectBuyRecordId= "SubjectBuyRecordId";

























}
