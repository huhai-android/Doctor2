package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class ReviewDiagnosisReportActivity extends BasicActivity {
    private int mMedicalRecordId;
    private String mMedicalReportPath;
    private static final int REQUEST_CODE_SELECT_FILE = 0;
    private String isFromChat;

    @Override
    protected int initViewResId() {
        return R.layout.activity_remote_pdf;
    }

    @Override
    protected void initView() {
        loading(true, "正在加载");
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);
        mMedicalReportPath = getIntent().getStringExtra("MedicalReportPath");
        isFromChat = getIntent().getStringExtra("isFromChat");

        if (mMedicalReportPath != null && !mMedicalReportPath.equals("")) {
            downFile(mMedicalReportPath, PDFfile());
        } else {
            GetMedicalRecordById();
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }


    @NonNull
    private File PDFfile() {
        File file = new File(Environment.getExternalStorageDirectory(), "report.pdf");
        return file;
    }


    public static void open(Context context) {
        Intent i = new Intent(context, ReviewDiagnosisReportActivity.class);
        context.startActivity(i);
    }


    private void GetMedicalRecordById() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetMedicalRecordById + "?MedicalRecordId=" + mMedicalRecordId;
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MedicalRecordByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MedicalRecordByIdEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    MedicalRecordByIdEntity medicalRecordByIdEntity = entity.getData();
                    if (medicalRecordByIdEntity != null) {
                        mMedicalReportPath = medicalRecordByIdEntity.getMedicalReportPath();
                        downFile(mMedicalReportPath, PDFfile());
                    }

                } else {
                    toast(entity.getMessage() + "aaa");
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                loading(true, "加载失败");
                finish();
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    public void downFile(final String path, final File file) {
        LoadDialog.show(mContext);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .get()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();

                }catch (Exception e2){

                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {

                    InputStream inputStream = response.body().byteStream();
                 /*   Log.i("ChatActivity","path1="+path+",uuid1="+uuid);
                    //将图片保存到本地存储卡中
                    File file = new File( MyApplication.getContext().getFilesDir()
                            + File.separator + uuid);*/
                    //  File file = new File(Environment.getExternalStorageDirectory(), uuid);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] temp = new byte[2048];
                    int length;
                    while ((length = inputStream.read(temp)) != -1) {
                        fileOutputStream.write(temp, 0, length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    //    notifyDataSetChanged();
                    inputStream.close();

                    // 调用系统自带的文件选择器
                    Intent intent = new Intent(ReviewDiagnosisReportActivity.this, ReviewDocumentActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // API>=21，launch as a new document
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); // launch as a new document
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.fromFile(PDFfile()));
                    intent.putExtra("mMedicalRecordId", mMedicalRecordId);
                    intent.putExtra("isFromChat", isFromChat);
                    startActivity(intent);
                    finish();
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    LoadDialog.clear();
                }
            }
        });
    }

}
