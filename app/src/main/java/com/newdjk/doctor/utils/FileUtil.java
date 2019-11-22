package com.newdjk.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DELL on 2017/12/1.
 */

public class FileUtil {
    /** 目录根路径 */
    public static final String DIR_HOME;
    /** 回答的录音音频 */
    public static final String DIR_REPLY_AUDIOS;
    static {
        DIR_HOME = Environment
                .getExternalStorageDirectory() + "/UNI/";
        DIR_REPLY_AUDIOS = DIR_HOME + "ReplyAudios/";
    }

    /**
     * 判断SD卡是否可用
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String saveBitmapToFile(String aimpath, String _file, int quality) {
        File file = new File(_file);
        return file.exists() && file.length() != 0L?_file:(saveBitmap(BitmapFactory.decodeFile(aimpath), _file, quality)?_file:aimpath);
    }
    public static boolean saveBitmapToFile(Bitmap bitmap, String _file) {
        return saveBitmap(bitmap, _file, 100);
    }

    private static boolean saveBitmap(Bitmap bitmap, String _file, int quality) {
        BufferedOutputStream os = null;

        try {
            File file = new File(_file);
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if(!filePath.exists()) {
                filePath.mkdirs();
            }

            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            boolean var8 = true;
            return var8;
        } catch (IOException var18) {
            Log.e("-->", var18.getMessage() + "  " + _file);
            var18.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException var17) {
                    Log.e("-->", var17.getMessage(), var17);
                }
            }

        }

        return false;
    }
    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数
        int hasRead = 0;
        while ( (hasRead = fis.read(bbuf)) > 0 ) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

}
