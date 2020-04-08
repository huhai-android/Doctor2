package com.newdjk.doctor.utils;

import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AppLicationEntity;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   AppLicationUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/1/17 14:42
 *  @描述：
 */
public class AppLicationUtils {
    static List<AppLicationEntity> listall = new ArrayList<>();
    static List<AppLicationEntity> listuse = new ArrayList<>();
    static List<AppLicationEntity> recordListuse = new ArrayList<>();
    static List<AppLicationEntity> listchat = new ArrayList<>();


    public static List<AppLicationEntity> getListall() {
        listall.clear();
        if (listall.size() == 0) {

            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
            appLicationEntity12.setAppDesc("邀请患者");
            appLicationEntity12.setImageID(7);
            appLicationEntity12.setAppID(7);
            appLicationEntity12.setIsselect(true);
            listall.add(appLicationEntity12);

            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
            appLicationEntity5.setAppDesc("专科协作");
            appLicationEntity5.setImageID(10);
            appLicationEntity5.setAppID(10);
            appLicationEntity5.setIsselect(true);
            listall.add(appLicationEntity5);

            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
            appLicationEntity4.setAppDesc("优选推荐");
            appLicationEntity4.setImageID(11);
            appLicationEntity4.setAppID(11);
            appLicationEntity4.setIsselect(true);
            listall.add(appLicationEntity4);


            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
            appLicationEntity6.setAppDesc("我的药房");
            appLicationEntity6.setImageID(6);
            appLicationEntity6.setAppID(6);
            appLicationEntity6.setIsselect(true);
            listall.add(appLicationEntity6);


            AppLicationEntity appLicationEntity7 = new AppLicationEntity();
            appLicationEntity7.setAppDesc("面诊预约");
            appLicationEntity7.setImageID(12);
            appLicationEntity7.setAppID(12);
            appLicationEntity7.setIsselect(true);
            listall.add(appLicationEntity7);


            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("胎心判读");
            appLicationEntity10.setImageID(4);
            appLicationEntity10.setAppID(4);
            appLicationEntity10.setIsselect(true);
            listall.add(appLicationEntity10);


            AppLicationEntity appLicationEntity9 = new AppLicationEntity();
            appLicationEntity9.setAppDesc("分级转诊");
            appLicationEntity9.setImageID(9);
            appLicationEntity9.setAppID(9);
            appLicationEntity9.setIsselect(true);
            listall.add(appLicationEntity9);


//            AppLicationEntity appLicationEntity = new AppLicationEntity();
//            appLicationEntity.setAppDesc("图文问诊");
//            appLicationEntity.setImageID(1);
//            appLicationEntity.setAppID(1);
//            appLicationEntity.setIsselect(true);
//            listall.add(appLicationEntity);
//
//            AppLicationEntity appLicationEntity2 = new AppLicationEntity();
//            appLicationEntity2.setAppDesc("在线续方");
//            appLicationEntity2.setImageID(2);
//            appLicationEntity2.setAppID(2);
//            appLicationEntity2.setIsselect(true);
//            listall.add(appLicationEntity2);
//
//            AppLicationEntity appLicationEntity3 = new AppLicationEntity();
//            appLicationEntity3.setAppDesc("第二诊疗意见");
//            appLicationEntity3.setImageID(3);
//            appLicationEntity3.setAppID(3);
//            appLicationEntity3.setIsselect(true);
//            listall.add(appLicationEntity3);
//
//
//
//
//
//
//            AppLicationEntity appLicationEntity13 = new AppLicationEntity();
//            appLicationEntity13.setAppDesc("帮助中心");
//            appLicationEntity13.setImageID(13);
//            appLicationEntity13.setAppID(13);
//            appLicationEntity13.setIsselect(true);
//            listall.add(appLicationEntity13);
//
//
//
//
//            AppLicationEntity appLicationEntity8 = new AppLicationEntity();
//            appLicationEntity8.setAppDesc("视频问诊");
//            appLicationEntity8.setImageID(8);
//            appLicationEntity8.setAppID(8);
//            appLicationEntity8.setIsselect(true);
//            listall.add(appLicationEntity8);
//
//
//
//
//
//
//
//
//
//            AppLicationEntity appLicationEntity11 = new AppLicationEntity();
//            appLicationEntity11.setAppDesc("履约记录");
//            appLicationEntity11.setImageID(5);
//            appLicationEntity11.setAppID(5);
//            appLicationEntity11.setIsselect(true);
//            listall.add(appLicationEntity11);
//
//


            return listall;
        }


        return listall;

    }

    public static List<AppLicationEntity> getNurseall() {
        listall.clear();
        if (listall.size() == 0) {

            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
            appLicationEntity12.setAppDesc("邀请患者");
            appLicationEntity12.setImageID(7);
            appLicationEntity12.setAppID(7);
            appLicationEntity12.setIsselect(true);
            listall.add(appLicationEntity12);


            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
            appLicationEntity4.setAppDesc("优选推荐");
            appLicationEntity4.setImageID(11);
            appLicationEntity4.setAppID(11);
            appLicationEntity4.setIsselect(true);
            listall.add(appLicationEntity4);


            return listall;
        }


        return listall;

    }


    public static List<AppLicationEntity> getDoctortype1() {
        listall.clear();
        if (listall.size() == 0) {

            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
            appLicationEntity12.setAppDesc("我的处方");
            appLicationEntity12.setImageID(14);
            appLicationEntity12.setAppID(14);
            appLicationEntity12.setIsselect(true);
            listall.add(appLicationEntity12);

            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
            appLicationEntity5.setAppDesc("优选推荐");
            appLicationEntity5.setImageID(15);
            appLicationEntity5.setAppID(15);
            appLicationEntity5.setIsselect(true);
            listall.add(appLicationEntity5);

            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
            appLicationEntity4.setAppDesc("我的检查单");
            appLicationEntity4.setImageID(16);
            appLicationEntity4.setAppID(16);
            appLicationEntity4.setIsselect(true);
            listall.add(appLicationEntity4);


            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
            appLicationEntity6.setAppDesc("问诊记录");
            appLicationEntity6.setImageID(17);
            appLicationEntity6.setAppID(17);
            appLicationEntity6.setIsselect(true);
            listall.add(appLicationEntity6);


//            AppLicationEntity appLicationEntity7 = new AppLicationEntity();
//            appLicationEntity7.setAppDesc("面诊预约记录");
//            appLicationEntity7.setImageID(18);
//            appLicationEntity7.setAppID(18);
//            appLicationEntity7.setIsselect(true);
//            listall.add(appLicationEntity7);


            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("服务包记录");
            appLicationEntity10.setImageID(19);
            appLicationEntity10.setAppID(19);
            appLicationEntity10.setIsselect(true);
            listall.add(appLicationEntity10);


        }
        return listall;
    }


    public static List<AppLicationEntity> getDoctortype2() {
        listall.clear();
        if (listall.size() == 0) {
            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
            appLicationEntity6.setAppDesc("问诊记录");
            appLicationEntity6.setImageID(17);
            appLicationEntity6.setAppID(17);
            appLicationEntity6.setIsselect(true);
            listall.add(appLicationEntity6);


            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
            appLicationEntity5.setAppDesc("优选推荐");
            appLicationEntity5.setImageID(15);
            appLicationEntity5.setAppID(15);
            appLicationEntity5.setIsselect(true);
            listall.add(appLicationEntity5);

            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("服务包记录");
            appLicationEntity10.setImageID(19);
            appLicationEntity10.setAppID(19);
            appLicationEntity10.setIsselect(true);
            listall.add(appLicationEntity10);

        }
        return listall;
    }

    public static List<AppLicationEntity> getDoctortype3() {
        listall.clear();
        if (listall.size() == 0) {

            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("审方记录");
            appLicationEntity10.setImageID(20);
            appLicationEntity10.setAppID(20);
            appLicationEntity10.setIsselect(true);
            listall.add(appLicationEntity10);

        }
        return listall;
    }


    public static List<AppLicationEntity> getListuse() {
        listall.clear();
        if (listall.size() == 0) {
            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
            appLicationEntity12.setAppDesc("邀请患者");
            appLicationEntity12.setImageID(7);
            appLicationEntity12.setAppID(7);
            appLicationEntity12.setIsselect(true);
            listall.add(appLicationEntity12);

            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
            appLicationEntity5.setAppDesc("专科协作");
            appLicationEntity5.setImageID(10);
            appLicationEntity5.setAppID(10);
            appLicationEntity5.setIsselect(true);
            listall.add(appLicationEntity5);

            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
            appLicationEntity4.setAppDesc("优选推荐");
            appLicationEntity4.setImageID(11);
            appLicationEntity4.setAppID(11);
            appLicationEntity4.setIsselect(true);
            listall.add(appLicationEntity4);


            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
            appLicationEntity6.setAppDesc("我的药房");
            appLicationEntity6.setImageID(6);
            appLicationEntity6.setAppID(6);
            appLicationEntity6.setIsselect(true);
            listall.add(appLicationEntity6);


            AppLicationEntity appLicationEntity7 = new AppLicationEntity();
            appLicationEntity7.setAppDesc("面诊预约");
            appLicationEntity7.setImageID(12);
            appLicationEntity7.setAppID(12);
            appLicationEntity7.setIsselect(true);
            listall.add(appLicationEntity7);


            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
            appLicationEntity10.setAppDesc("胎心判读");
            appLicationEntity10.setImageID(4);
            appLicationEntity10.setAppID(4);
            appLicationEntity10.setIsselect(true);
            listall.add(appLicationEntity10);


            AppLicationEntity appLicationEntity9 = new AppLicationEntity();
            appLicationEntity9.setAppDesc("分级转诊");
            appLicationEntity9.setImageID(9);
            appLicationEntity9.setAppID(9);
            appLicationEntity9.setIsselect(true);
            listall.add(appLicationEntity9);

//            AppLicationEntity appLicationEntity = new AppLicationEntity();
//            appLicationEntity.setAppDesc("图文问诊");
//            appLicationEntity.setImageID(1);
//            appLicationEntity.setAppID(1);
//            appLicationEntity.setIsselect(true);
//            listall.add(appLicationEntity);
//
//            AppLicationEntity appLicationEntity2 = new AppLicationEntity();
//            appLicationEntity2.setAppDesc("在线续方");
//            appLicationEntity2.setImageID(2);
//            appLicationEntity2.setAppID(2);
//            appLicationEntity2.setIsselect(true);
//            listall.add(appLicationEntity2);
//
//            AppLicationEntity appLicationEntity3 = new AppLicationEntity();
//            appLicationEntity3.setAppDesc("第二诊疗意见");
//            appLicationEntity3.setImageID(3);
//            appLicationEntity3.setAppID(3);
//            appLicationEntity3.setIsselect(true);
//            listall.add(appLicationEntity3);
//
//
//            AppLicationEntity appLicationEntity4 = new AppLicationEntity();
//            appLicationEntity4.setAppDesc("优选推荐");
//            appLicationEntity4.setImageID(11);
//            appLicationEntity4.setAppID(11);
//            appLicationEntity4.setIsselect(true);
//            listall.add(appLicationEntity4);
//
//
//            AppLicationEntity appLicationEntity5 = new AppLicationEntity();
//            appLicationEntity5.setAppDesc("专科协作");
//            appLicationEntity5.setImageID(10);
//            appLicationEntity5.setAppID(10);
//            appLicationEntity5.setIsselect(true);
//            listall.add(appLicationEntity5);
//
//            AppLicationEntity appLicationEntity13 = new AppLicationEntity();
//            appLicationEntity13.setAppDesc("帮助中心");
//            appLicationEntity13.setImageID(13);
//            appLicationEntity13.setAppID(13);
//            appLicationEntity13.setIsselect(true);
//            listall.add(appLicationEntity13);
//
//
//            AppLicationEntity appLicationEntity7 = new AppLicationEntity();
//            appLicationEntity7.setAppDesc("面诊预约");
//            appLicationEntity7.setImageID(12);
//            appLicationEntity7.setAppID(12);
//            appLicationEntity7.setIsselect(true);
//            listall.add(appLicationEntity7);
//
//
//            AppLicationEntity appLicationEntity8 = new AppLicationEntity();
//            appLicationEntity8.setAppDesc("视频问诊");
//            appLicationEntity8.setImageID(8);
//            appLicationEntity8.setAppID(8);
//            appLicationEntity8.setIsselect(true);
//            listall.add(appLicationEntity8);
//
//
//            AppLicationEntity appLicationEntity6 = new AppLicationEntity();
//            appLicationEntity6.setAppDesc("我的药房");
//            appLicationEntity6.setImageID(6);
//            appLicationEntity6.setAppID(6);
//            appLicationEntity6.setIsselect(true);
//            listall.add(appLicationEntity6);
//
//            AppLicationEntity appLicationEntity9 = new AppLicationEntity();
//            appLicationEntity9.setAppDesc("分级转诊");
//            appLicationEntity9.setImageID(9);
//            appLicationEntity9.setAppID(9);
//            appLicationEntity9.setIsselect(true);
//            listall.add(appLicationEntity9);
//
//
//            AppLicationEntity appLicationEntity10 = new AppLicationEntity();
//            appLicationEntity10.setAppDesc("胎心判读");
//            appLicationEntity10.setImageID(4);
//            appLicationEntity10.setAppID(4);
//            appLicationEntity10.setIsselect(true);
//            listall.add(appLicationEntity10);
//
//
//            AppLicationEntity appLicationEntity11 = new AppLicationEntity();
//            appLicationEntity11.setAppDesc("履约记录");
//            appLicationEntity11.setImageID(5);
//            appLicationEntity11.setAppID(5);
//            appLicationEntity11.setIsselect(true);
//            listall.add(appLicationEntity11);
//
//            AppLicationEntity appLicationEntity12 = new AppLicationEntity();
//            appLicationEntity12.setAppDesc("邀请患者");
//            appLicationEntity12.setImageID(7);
//            appLicationEntity12.setAppID(7);
//            appLicationEntity12.setIsselect(true);
//            listall.add(appLicationEntity12);


            return listall;
        }


        return listall;

    }

    public static List<AppLicationEntity> getRecordListuse() {
        listchat.clear();
        if (listchat.size() == 0) {
            for (int i = 1; i <= 4; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图文问诊");
                        appLicationEntity.setImageID(R.drawable.me_record_icon1);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("在线续方");
                        appLicationEntity.setImageID(R.drawable.me_record_icon2);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("服务包记录");
                        appLicationEntity.setImageID(R.drawable.me_record_icon3);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("第二诊疗意见");
                        appLicationEntity.setImageID(R.drawable.me_record_icon3);
                        break;


                }
                appLicationEntity.setIsselect(true);

                listchat.add(appLicationEntity);
            }
        } else {
            return listchat;
        }
        return listchat;

    }


    public static List<AppLicationEntity> getListChat() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 6; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.drawable.im_photo);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.drawable.im_camera);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("快速回复");
                        appLicationEntity.setImageID(R.drawable.im_reply);
                        break;
//                    case 4:
//                        appLicationEntity.setAppDesc("补充病历");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon4);
//                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("加号");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
//                        break;
//                    case 7:
//                        appLicationEntity.setAppDesc("视频通话");
//                        appLicationEntity.setImageID(R.drawable.videowenzhen);
//                        break;
                    case 4:
                        appLicationEntity.setAppDesc("MDT分诊");
                        appLicationEntity.setImageID(R.drawable.im_mdt);
                        break;

//                    case 7:
////                        appLicationEntity.setAppDesc("问诊表");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;

//                    case 8:
//                        appLicationEntity.setAppDesc("宣教");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;
                    case 5:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.im_video);
                        break;
                    case 6:

                        appLicationEntity.setAppDesc("结束问诊");
                        appLicationEntity.setImageID(R.drawable.im_turnoff);
                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }


    public static List<AppLicationEntity> getListChatwithoutMDT() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 5; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.drawable.im_photo);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.drawable.im_camera);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("快速回复");
                        appLicationEntity.setImageID(R.drawable.im_reply);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("补充病历");
                        appLicationEntity.setImageID(R.drawable.im_physician);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.im_video);
                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("加号");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
//                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("视频通话");
//                        appLicationEntity.setImageID(R.drawable.videowenzhen);
//                        break;
//                    case 6:
////                        appLicationEntity.setAppDesc("问诊表");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;

//                    case 7:
//                        appLicationEntity.setAppDesc("宣教");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;

//                    case 7:
////                        appLicationEntity.setAppDesc("结束问诊");
////                        appLicationEntity.setImageID(R.drawable.stop);
//                        break;

//                    case 11:
//                        appLicationEntity.setAppDesc("MDT分诊");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }


    public static List<AppLicationEntity> getListChatHuizhen() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 5; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.drawable.im_photo);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.drawable.im_camera);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("快速回复");
                        appLicationEntity.setImageID(R.drawable.im_reply);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("补充病历");
                        appLicationEntity.setImageID(R.drawable.im_physician);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.im_video);
                        break;
//                    case 4:
//                        appLicationEntity.setAppDesc("补充病历");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon4);
//                        break;
//                    case 5:
//                        appLicationEntity.setAppDesc("优选推荐");
//                        appLicationEntity.setImageID(R.drawable.advice);
//                        break;
////                    case 6:
////                        appLicationEntity.setAppDesc("加号");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
////                        break;
////                    case 6:
////                        appLicationEntity.setAppDesc("视频通话");
////                        appLicationEntity.setImageID(R.drawable.videowenzhen);
////                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("问诊表");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;
//
////                    case 7:
////                        appLicationEntity.setAppDesc("宣教");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
////                        break;
//
//                    case 7:
//                        appLicationEntity.setAppDesc("结束问诊");
//                        appLicationEntity.setImageID(R.drawable.stop);
//                        break;

//                    case 11:
//                        appLicationEntity.setAppDesc("MDT分诊");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }

    //单聊
    public static List<AppLicationEntity> getListSinglChat() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 11; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图片");
                        appLicationEntity.setImageID(R.drawable.im_photo);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("拍摄");
                        appLicationEntity.setImageID(R.drawable.im_camera);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("视频通话");
                        appLicationEntity.setImageID(R.drawable.im_video);
                        break;




                    case 4:

                        appLicationEntity.setAppDesc("补充病例");
                        appLicationEntity.setImageID(R.drawable.im_addcase);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("快速回复");
                        appLicationEntity.setImageID(R.drawable.im_reply);
                        break;
                    case 6:


                        appLicationEntity.setAppDesc("MDT会诊");
                        appLicationEntity.setImageID(R.drawable.im_mdt);
                        break;
                    case 7:

                        appLicationEntity.setAppDesc("问诊表");
                        appLicationEntity.setImageID(R.drawable.im_physician);
                        break;
                    case 8:
                        appLicationEntity.setAppDesc("结束问诊");
                        appLicationEntity.setImageID(R.drawable.im_turnoff);
                        break;
                    case 9:

                        appLicationEntity.setAppDesc("宣教");
                        appLicationEntity.setImageID(R.drawable.im_mission);

                        break;
                    case 10:

                        appLicationEntity.setAppDesc("加号");
                        appLicationEntity.setImageID(R.drawable.im_add);
                        break;

                    case 11:
                        appLicationEntity.setAppDesc("拍摄视频");
                        appLicationEntity.setImageID(R.drawable.im_video);
                        break;
////                    case 6:
////                        appLicationEntity.setAppDesc("加号");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon6);
////                        break;
////                    case 6:
////                        appLicationEntity.setAppDesc("视频通话");
////                        appLicationEntity.setImageID(R.drawable.videowenzhen);
////                        break;
//                    case 6:
//                        appLicationEntity.setAppDesc("问诊表");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon5);
//                        break;
//
////                    case 7:
////                        appLicationEntity.setAppDesc("宣教");
////                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
////                        break;
//
//                    case 7:
//                        appLicationEntity.setAppDesc("结束问诊");
//                        appLicationEntity.setImageID(R.drawable.stop);
//                        break;

//                    case 11:
//                        appLicationEntity.setAppDesc("MDT分诊");
//                        appLicationEntity.setImageID(R.mipmap.doctor_chat_icon8);
//                        break;

                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }


    public static int getimageID(int id) {
        int defaultiID = R.mipmap.doctor_home_icon1;
        switch (id) {
            case 0:  //这是默认的全部
                defaultiID = R.drawable.all_function;
                break;
            case 1:
                defaultiID = R.drawable.doctor_home_icon1;
                break;
            case 2:
                defaultiID = R.drawable.doctor_home_icon3;
                break;
            case 3:
                defaultiID = R.drawable.seconddiagnose;
                break;
            case 4:
                defaultiID = R.mipmap.icon_taixinpandu;
                break;
            case 5:
                defaultiID = R.drawable.lvyue;
                break;
            case 6:
                defaultiID = R.mipmap.icon_wode_yaofang;
                break;
            case 7:
                defaultiID = R.mipmap.icon_yaoqinghuanzhe;
                break;
            case 8:
                defaultiID = R.drawable.doctor_home_icon2;
                break;
            case 9:
                defaultiID = R.mipmap.icon_fenjizhuanzhen;
                break;
            case 10:
                defaultiID = R.mipmap.icon_zhuankexiezuo;
                break;
            case 11:
                defaultiID = R.mipmap.icon_youxuantuijian;
                break;
            case 12:
                defaultiID = R.mipmap.icon_mianhenyuyue;
                break;
            case 13:
                defaultiID = R.drawable.help_center;
                break;

            //我的界面
            case 14:
                defaultiID = R.mipmap.icon_wodechufang;
                break;

            case 15:
                defaultiID = R.mipmap.icon_youxuantuijiana;
                break;
            case 16:
                defaultiID = R.mipmap.icon_wode_jianchadan;
                break;
            case 17:
                defaultiID = R.mipmap.icon_wenzhenjilu;
                break;
            case 18:
                defaultiID = R.mipmap.icon_mianzhenyuyuejilu;
                break;
            case 19:
                defaultiID = R.mipmap.icon_fuwubaojilu;
                break;

            case 20:
                defaultiID = R.mipmap.icon_fuwubaojilu;
                break;



            default:
                defaultiID = R.drawable.doctor_home_icon1;
                break;
        }
        return defaultiID;
    }
}
