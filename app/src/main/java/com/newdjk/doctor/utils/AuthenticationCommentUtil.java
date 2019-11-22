package com.newdjk.doctor.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.views.GroupButtonDialog;

/**
 * 统一处理注册认证工具类
 */
public class AuthenticationCommentUtil {
    public static int STATUS = -1;//	医生状态(0-未审核，1-审核通过，2-审核失败，3-审核中)
    public static final String AUTH = "AUTH";
    public static GroupButtonDialog mDialog;

    public static AuthenticationCommentUtil getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AuthenticationCommentUtil instance = new AuthenticationCommentUtil();
    }

    public void checkAuthentication(Context ctx) {
        if (STATUS != 1) {
            mDialog = new GroupButtonDialog(ctx);
            mDialog.show("温馨提示", "用户资格未未进行认证，去认证!", new GroupButtonDialog.DialogListener() {
                @Override
                public void cancel() {

                }

                @Override
                public void confirm() {

                }
            });

            return;
        }
    }

    /**
     * 本地保存
     *
     * @param entituy
     */
    public void myShared(LoginEntity entituy) {
        PrescriptionMessageEntity prescriptionMessageEntity = new PrescriptionMessageEntity();
        prescriptionMessageEntity.setDoctor(entituy);
        Gson gson = new Gson();
        String loginJson = gson.toJson(prescriptionMessageEntity);
        SpUtils.put(Contants.LoginJson, loginJson);
        Log.d("login", "id=" + entituy.toString());
        SpUtils.put(Contants.Id, entituy.getData().getData().getDoctorId());
        SpUtils.put(Contants.OrgName, entituy.getData().getData().getOrgName());
        SpUtils.put(Contants.userName, entituy.getData().getData().getMobile());
        SpUtils.put(Contants.Name, entituy.getData().getData().getDoctorName());
        SpUtils.put(Contants.Sex, entituy.getData().getData().getSex());
        SpUtils.put(Contants.Token, entituy.getData().getToken());
        SpUtils.put(Contants.Status, entituy.getData().getData().getStatus());
        SpUtils.put(Contants.DocType, entituy.getData().getData().getDrType());
        SpUtils.put(Contants.Position, entituy.getData().getData().getPosition());
    }


}
