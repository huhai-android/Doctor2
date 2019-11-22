package com.newdjk.doctor.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.newdjk.doctor.ui.activity.ReviewDocumentActivity;
import com.newdjk.doctor.views.LoadDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   PDFviewUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/8/20 13:44
 *  @描述：
 */
public class PDFviewUtils {

    private  static PDFviewUtils instanse;
    private Context context;
    private String title;
    public static PDFviewUtils getInstanse(){
        if (instanse==null){
            instanse=new PDFviewUtils();
        }
        return instanse;
    }


    public  void  showPDF(Context context,String url,String title){
        this.context=context;
        this.title=title;
        downFile(url, PDFfile());
    }



    @NonNull
    private File PDFfile() {
        File file = new File(Environment.getExternalStorageDirectory(), "report.pdf");
        return file;
    }

    public  void downFile(final String path, final File file) {
        LoadDialog.show(context);
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
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();

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
                    Intent intent = new Intent(context, ReviewDocumentActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // API>=21，launch as a new document
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); // launch as a new document
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.fromFile(PDFfile()));
                    intent.putExtra("mMedicalRecordId", "");
                    intent.putExtra("isFromChat", "1");
                    intent.putExtra("title", title);
                    context.startActivity(intent);
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    LoadDialog.clear();
                }
            }
        });
    }
}
