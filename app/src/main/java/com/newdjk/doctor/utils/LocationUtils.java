package com.newdjk.doctor.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   LocationUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/5/20 11:08
 *  @描述：
 */
public class LocationUtils {
    private static final String TAG = "LocationUtils";
    public static LocationUtils locationUtils;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption = null;
    private locationStatusListener mLocationStatusListener = null;

    public void initlocation(Context context, locationStatusListener locationStatusListener) {

        this.mLocationStatusListener = locationStatusListener;

        //初始化client
        locationClient = new AMapLocationClient(context.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                if (null != location) {

                    StringBuffer sb = new StringBuffer();
                    //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                    if (location.getErrorCode() == 0) {
                        sb.append("定位成功" + "\n");
                        sb.append("定位类型: " + location.getLocationType() + "\n");
                        sb.append("经    度    : " + location.getLongitude() + "\n");
                        sb.append("纬    度    : " + location.getLatitude() + "\n");
                        sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                        sb.append("提供者    : " + location.getProvider() + "\n");

                        sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                        sb.append("角    度    : " + location.getBearing() + "\n");
                        // 获取当前提供定位服务的卫星个数
                        sb.append("星    数    : " + location.getSatellites() + "\n");
                        sb.append("国    家    : " + location.getCountry() + "\n");
                        sb.append("省            : " + location.getProvince() + "\n");
                        sb.append("市            : " + location.getCity() + "\n");
                        sb.append("城市编码 : " + location.getCityCode() + "\n");
                        sb.append("区            : " + location.getDistrict() + "\n");
                        sb.append("区域 码   : " + location.getAdCode() + "\n");
                        sb.append("地    址    : " + location.getAddress() + "\n");
                        sb.append("兴趣点    : " + location.getPoiName() + "\n");
                        //定位完成的时间
                        sb.append("定位时间: " + GaoDeUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");

                        if (mLocationStatusListener != null) {
                            mLocationStatusListener.locationSuccess(location.getCity());
                        }


                    } else {
                        //定位失败
                        sb.append("定位失败" + "\n");
                        sb.append("错误码:" + location.getErrorCode() + "\n");
                        sb.append("错误信息:" + location.getErrorInfo() + "\n");
                        sb.append("错误描述:" + location.getLocationDetail() + "\n");
                        if (mLocationStatusListener != null) {
                            mLocationStatusListener.locationFailed(location.getErrorInfo() + "");
                        }
                    }
                    sb.append("***定位质量报告***").append("\n");
                    sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                    sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                    sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                    sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                    sb.append("****************").append("\n");
                    //定位之后的回调时间
                    sb.append("回调时间: " + GaoDeUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                    //解析定位结果，
                    String result = sb.toString();
                    //ToastUtil.setToast(result);


                } else {
                    // ToastUtil.setToast("定位失败，loc is null");
                    if (mLocationStatusListener != null) {
                        mLocationStatusListener.locationSuccess("定位失败，loc is null");
                    }
                }
            }
        });

        startLocation();
    }


    public static LocationUtils getinstanse() {
        if (locationUtils == null) {
            locationUtils = new LocationUtils();
        }
        return locationUtils;

    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }


    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {

        // 设置定位参数
        if (locationClient != null) {
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }

    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void stopLocation() {
        // 停止定位
        if (locationClient != null) {
            locationClient.stopLocation();
            destroyLocation();
        }

    }


    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {

        }
    };


    public interface locationStatusListener {

        void locationSuccess(String data);

        void locationFailed(String data);
    }
}
