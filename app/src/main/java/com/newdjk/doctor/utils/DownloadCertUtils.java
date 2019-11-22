package com.newdjk.doctor.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.views.DownloadCertDialog;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   DownloadCertUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/5/21 16:35
 *  @描述：
 */
public  class DownloadCertUtils  {

    private static DownloadCertUtils instanse;
    private static Gson mGson;
    private DownloadCertDialog mDialog;
    private DownloadListener mlistener;

    public static DownloadCertUtils getinstanse() {
        if (instanse == null) {
            instanse = new DownloadCertUtils();
            mGson = new Gson();
        }
        return instanse;
    }


    public void downloadCert(final Activity activity, DownloadListener listener) {

        mlistener = listener;

        boolean isExists = BJCASDK.getInstance().existsCert(activity);

        boolean ExistStamp = BJCASDK.getInstance().existsStamp(activity);
        /// BJCASDK.getInstance().clearCert(ChatActivity.this);
        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
        // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
        if (!isExists) {
            mDialog = new DownloadCertDialog(activity);
            mDialog.show("温馨提示", "", new DownloadCertDialog.DialogListener() {
                @Override
                public void confirm() {
                    Log.i("ChatActivity", "aaa");
                    BJCASDK.getInstance().certDown(activity, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                        @Override
                        public void callback(String s) {
                            Log.d("chat", "callback" + s);
                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                            String status = yWXListenerEntity.getStatus();
                            String message = yWXListenerEntity.getMessage();

                            BJCASDK.getInstance().getUserInfo(activity, Contants.clientId, new YWXListener() {
                                @Override
                                public void callback(String s) {
                                    Log.d("chat", "证书" + s);
                                }
                            });

                            boolean ExistStamp2 = BJCASDK.getInstance().existsStamp(activity);
                            Log.d("chat", "证书" + ExistStamp2);

                            if (status != null && status.equals("0")) {
                                boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(activity);
                                if (!ExistStamp1) {

                                    BJCASDK.getInstance().drawStamp(activity, Contants.clientId, new YWXListener() {
                                        @Override
                                        public void callback(String msg) {
                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                            String status = yWXListenerEntity.getStatus();
                                            String message = yWXListenerEntity.getMessage();
                                            if (status != null && status.equals("0")) {
//                                                Intent prescriptionIntent = new Intent(activity, PrescriptionActivity.class);
//                                                prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
//                                                activity.startActivity(prescriptionIntent);
                                                //设置签章成功
                                                mlistener.drawStampSuccess(status);

                                            } else {
                                                //   toast(message);
                                                ToastUtil.setToast("设置签章失败，请重试！" + (status));
                                                mlistener.drawStampFailed(message);
                                                //设置签章失败
                                            }
                                        }
                                    });
                                } else {

//                                    Intent prescriptionIntent = new Intent(activity, PrescriptionActivity.class);
//                                    prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
//                                    activity.startActivity(prescriptionIntent);
                                    mlistener.drawStampSuccess(status);
                                    //有签章

                                }
                            } else {
                                // toast(message);
                                Toast.makeText(activity, "下载证书失败，请重试(" + status + ")", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }
            });


        } else {
            //   BJCASDK.getInstance().clearCert(ChatActivity.this);
            Log.i("zdp", "证书已下载");
            if (!ExistStamp) {
                BJCASDK.getInstance().drawStamp(activity, Contants.clientId, new YWXListener() {
                    @Override
                    public void callback(String msg) {
                        YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                        String status = yWXListenerEntity.getStatus();
                        String message = yWXListenerEntity.getMessage();
                        if (status != null && status.equals("0")) {
//
//                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
//                                prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
//                                activity.startActivity(prescriptionIntent);
                            //设置签章成功
                            mlistener.drawStampSuccess(status);

                        } else {
                            //  toast(message);
                            ToastUtil.setToast("设置签章失败，请重试！+(" + status + ")");
                            mlistener.drawStampFailed(message);
                            //设置签章失败
                        }
                    }
                });
            } else {

//                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
//                prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
//                activity.startActivity(prescriptionIntent);
                mlistener.drawStampSuccess("0");
                //下载后有签章

            }

        }


    }


    public   interface DownloadListener

    {

        //下载的回调
        void locationSuccess(String data);

        void locationFailed(String data);



        //设置签章的回调
        void drawStampFailed(String data);


        void drawStampSuccess(String data);



        //签名成功回调
        void signFailed(String data);


        void signSuccess(String data);



        //保存密码成功回调
        void keepPinFailed(String data);


        void keepPinSuccess(String data);



    }
}



