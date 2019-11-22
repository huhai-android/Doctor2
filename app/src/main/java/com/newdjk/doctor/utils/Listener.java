package com.newdjk.doctor.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Listener {

    /**
     * 0.5秒刷新一下
     */
    public static final int PRE = 500;
    /**
     * 无数据
     */
    public static final int NONE = 0;
    /**
     * 有数据
     */
    public static final int BEAT = 1;
    /**
     * 表示多少 就开始存写
     */
    private final int MAX = 1000;
    /**
     * 预产期-周
     */
    public int week;
    /**
     * 预测期-天
     */
    public int day;
    /**
     * 手动胎动次数
     */
    public int beatTimes;
    public int secTime = -1;
    /**
     * 计时（秒）
     */
    public String timing;
    /**
     * 开始时间
     */
    public String startTime;
    public long startT;
    /**
     * 存储写入信息文件
     */
    private File file;
    /**
     * 时间段数据
     */
    public LinkedList<TimeData> dataList;

    public Listener() {

        dataList = new LinkedList<TimeData>();

        startT = System.currentTimeMillis();
    }

    /**
     * 添加一个
     */
    public void addBeat(TimeData timeData) {

        setEndT(System.currentTimeMillis());

        if (dataList.size() >= MAX) {

            save();
        }

        dataList.add(timeData);
    }

    /**
     * 每个时间戳的 数据
     */
    public static class TimeData implements Cloneable {
        static TimeData timeData = new TimeData();
        /**
         * 手动获取胎动
         */
        public int beatZd = NONE;
        /**
         * 自动获取胎动
         */
        public int beatBd = NONE;
        /**
         * 心跳频率（次/分）
         */
        public int heartRate;
        /**
         * 自动胎动波形
         */
        public int afmWave;
        /**
         * 宫缩曲线
         */
        public int tocoWave;
        /**
         * 状态1
         * <p>
         * bit1:bit0：胎心信号质量
         * <p>
         * 01：信号质量差
         * <p>
         * 10：信号质量一般
         * <p>
         * 11：信号质量好
         * <p>
         * bit2：自动胎动标记，1 表示自动检测到一次胎动，否则为0
         * <p>
         * bit3：手动胎动标记，1 表示孕妇感知到一次胎动，否则为0
         * <p>
         * bit7:bit4：为0
         */
        public int status1;
        /**
         * 状态2
         * <p>
         * bit2:bit0：设备电量，0-4 档
         * <p>
         * bit3：设备状态，1 表示在充电，0 表示没有在充电
         */
        public int status2;

        public TimeData() {

        }

        public TimeData(int heartRate, int beatBd) {

            /** 心跳频率（次/分） */
            this.heartRate = heartRate;
            /** 主动点击胎动 */
            this.beatBd = beatBd;
        }

        public TimeData(int heartRate, int tocoWave, int afmWave, int status1,
                        int status2, int beatBd) {

            this.heartRate = heartRate;
            this.tocoWave = tocoWave;
            this.afmWave = afmWave;
            this.status1 = status1;
            this.status2 = status2;
            this.beatBd = beatBd;
            if (beatBd != 0)
                this.status1 = this.status1 & (~(1 << 2)) | (1 << 2);
        }

        public TimeData clone() throws CloneNotSupportedException {
            return (TimeData) super.clone();
        }

        public static TimeData getOnePerson() {
            try {
                return timeData.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String toString() {
            return "TimeData{" +
                    "beatZd=" + beatZd +
                    ", beatBd=" + beatBd +
                    ", heartRate=" + heartRate +
                    ", afmWave=" + afmWave +
                    ", tocoWave=" + tocoWave +
                    ", status1=" + status1 +
                    ", status2=" + status2 +
                    '}';
        }
    }

    /**
     * 保存文件
     */
    public void save() {
        if (file == null) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            int length = dataList.size();
            for (int i = 0; i < length; i++) {
                TimeData data = dataList.get(i);
                fos.write(data.heartRate);
                fos.write(data.tocoWave);
                fos.write(data.afmWave);
                fos.write(data.status1);
                fos.write(data.status2);
            }
            dataList.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setEndT(long endT) {
        int secTime = (int) ((endT - startT) / 1000);
        if (this.secTime == secTime) {
            return;
        }
        this.secTime = secTime;
        timing = formatTime(secTime);
    }

    /**
     * 时间 格式（s）
     *
     * @param s 单位为秒
     */
    public static String formatTime(int s) {
        String t;
        int m = s / 60;
        s = s % 60;
        if (m < 10) {
            t = "0" + m;
        } else {
            t = Integer.toString(m);
        }
        if (s < 10) {
            t += (":0" + s);
        } else {
            t += (":" + s);
        }
        return t;
    }
}
