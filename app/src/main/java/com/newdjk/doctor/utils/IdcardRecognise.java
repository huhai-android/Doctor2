package com.newdjk.doctor.utils;

import com.newdjk.doctor.MyApplication;

import java.net.URLEncoder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   IdcardRecognise
 *  @创建者:   huhai
 *  @创建时间:  2019/2/18 10:57
 *  @描述：
 */
public class IdcardRecognise {
    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String getIDNumber(String filePath) throws Exception {
        // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 本地图片路径


        byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
        // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
        String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(imgStr, "UTF-8");
        /**
         * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
         */
        String accessToken = MyApplication.baiduToken;
        String result = HttpUtil.post(idcardIdentificate, accessToken, params);
        System.out.println(result);
        return result;

    }


    public static String getDoctorUnmber(String filePath) throws Exception {
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
        // 本地图片路径


        byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
        String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
        /**
         * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
         */
        String accessToken = MyApplication.baiduToken;
        String result = HttpUtil.post(otherHost, accessToken, params);
        System.out.println(result);

        return result;

    }
}
