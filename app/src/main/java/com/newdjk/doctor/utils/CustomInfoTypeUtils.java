package com.newdjk.doctor.utils;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   CustomInfoTypeUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/4/17 17:36
 *  @描述：
 */
public class CustomInfoTypeUtils {

    public static String getCustomDescByType(int type) {

        switch (type) {
            case 10:
                return "新患者报道消息";

            case 11:
                return "处方笺消息";


            case 12:
                return "您有评价消息";

            case 13:
                return "处方申请消息";

            case 14:
                return "图文问诊消息";

            case 15:
                return "视频问诊消息";

            case 16:
                return "处方被驳回消息";


            case 100:
                return "新的胎心判读请求消息";

            case 101:
                return "处方待审核消息";

            case 102:
                return "服务包审核消息";

            case 20:
                return "患者报道消息";

            case 21:
                return "处方单消息";

            case 22:
                return "患者处方支付成功提醒";

            case 23:
                return "问诊结束消息";

            case 24:
                return "视频问诊预约成功提醒";

            case 25:
                return "提醒患者评价消息";

            case 26:
                return "提醒用户去上传病历消息";

            case 27:
                return "用户主动上传病历，请查看";

            case 28:
                return "加号凭证";

            case 29:
                return "";


            case 200:
                return "监护报告提醒";

            case 201:

                return "母胎监护服务结算驳回";

            case 202:
                return "处方驳回通知患者";

            case 203:

            case 204:
                return "处方药品已收货通知";

            case 205:
                return "处方药品已被取消通知";

            case 206:
                return "处方订单下单成功";

            case 207:
                return "健康宣教";

            case 208:
                return "复诊提醒";

            case 209:
                return "复查提醒";

            case 210:
                return "用药提醒";


            case 211:
                return "问卷量表";

            case 212:
                return "上传病历及检验检查报告提醒";

            case 213:
                return "康复指导";

            case 214:
                return "第二诊疗推荐提醒(通知患者)";

            case 215:
                return "补充资料提醒(通知患者)";

            case 216:
                return "收到报告提醒";

            case 217:
                return "第二诊疗支付成功(国内医生-通知患者)";

            case 218:
                return "第二诊疗支付成功";

            case 219:
                return "新的请求提醒";

            case 220:
                return "收到患者补充病历提醒";

            case 221:
                return "医生资质认证审核通过";
            case 222:
                return "医生资质认证审核驳回";
            case 223:
                return "医生转诊转回";
            case 224:
                return "检验检查单支付成功";
            case 225:
                return "检验检查单推送";
            case 226:
                return "转诊提醒提醒";

            case 227:
                return "检验检查单推送";

            case 30:
                return "跳转到IM";
            case 31:
                return "新处方消息";

            case 32:
                return "推荐服务包";
            case 33:
                return "医生发放问卷";
            case 34:
                return "购买服务包成功消息";

            case 35:
                return "开始问诊";

            case 36:
                return "结束问诊";


            case 37:
                return "用户提交评价之后提醒医生查看";

            case 38:
                return "患者已经支付问诊提醒";

            case 39:
                return "会员答完问卷消息";

            case 301:
                return "健康资讯消息";

            case 399:
                return "图片消息";

            case 302:
                return "医生推荐商品给患者";


            default:
                return "您有一条新消息";

        }

    }

}
