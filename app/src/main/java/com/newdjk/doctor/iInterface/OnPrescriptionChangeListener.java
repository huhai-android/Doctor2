package com.newdjk.doctor.iInterface;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.iInterface
 *  @文件名:   OnRedNumberChangeListener
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 16:49
 *  @描述：
 */
public interface OnPrescriptionChangeListener {

   void onWaitForcheckchange(int number);
   void onHaveCountChange(int number);
   void onRefuseCountChange(int number);

}
