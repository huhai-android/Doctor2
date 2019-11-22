package com.newdjk.doctor.utils;

import java.math.BigDecimal;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   MathUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/8/23 9:39
 *  @描述：
 */
public class MathUtils {


    //保留两位小数并且返回 并且去除多余的0
    public static String getDealValue(String data) {
        try {
            double value = Double.parseDouble(data);
            double yushu = value - (int) value;
            if (yushu > 0) {
                BigDecimal bg3 = new BigDecimal(value);
                double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            DecimalFormat df = new DecimalFormat("0.00");
//            String s = df.format(f3);
                return f3 + "";
            } else {
                return ((int) value) + "";
            }
        } catch (Exception e) {
            return "";
        }


    }


    //保留两位小数并且返回  并且去除多余的0
    public static String getDealValue(double data) {
        try {
            double yushu = data - (int) data;
            if (yushu > 0) {
                BigDecimal bg3 = new BigDecimal(data);
                double f3 = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            DecimalFormat df = new DecimalFormat("0.00");
//            String s = df.format(f3);
                return f3 + "";
            } else {
                return ((int) data) + "";
            }
        } catch (Exception e) {
            return "";
        }

    }
}
