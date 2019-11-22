package com.newdjk.doctor.iInterface;

import android.view.View;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.iInterface
 *  @文件名:   RefuseDialogClickLister
 *  @创建者:   huhai
 *  @创建时间:  2018/11/29 17:13
 *  @描述：
 */
public interface RefuseDialogClickLister {

   void onPositiveButtonClick(View view,int seleadvice,String adviece);
   void onNegativeButtonClick(View view);
}
