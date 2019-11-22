package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   SignRepoetEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/7 20:26
 *  @描述：
 */
public class SignRepoetEntity {

    /**
     * AskId : 1
     * DoctorName : sample string 2
     * Sctype : 64
     * ResultId : 4
     * Advise : sample string 5
     * ScoreItem : {"Bfhr":1.1,"Var":2.1,"Cyc":3.1,"Acct":4.1,"Dect":5.1,"Fmcnt":6.1,"Fmv":7.1,"Fmd":8.1}
     * DoctorId : 6
     */

    private int AskId;
    private String DoctorName;
    private int Sctype;
    private int ResultId;
    private String Advise;
    private ScoreItemBean ScoreItem;
    private int DoctorId;

    public int getAskId() {
        return AskId;
    }

    public void setAskId(int AskId) {
        this.AskId = AskId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getSctype() {
        return Sctype;
    }

    public void setSctype(int Sctype) {
        this.Sctype = Sctype;
    }

    public int getResultId() {
        return ResultId;
    }

    public void setResultId(int ResultId) {
        this.ResultId = ResultId;
    }

    public String getAdvise() {
        return Advise;
    }

    public void setAdvise(String Advise) {
        this.Advise = Advise;
    }

    public ScoreItemBean getScoreItem() {
        return ScoreItem;
    }

    public void setScoreItem(ScoreItemBean ScoreItem) {
        this.ScoreItem = ScoreItem;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public static class ScoreItemBean {
        /**
         * Bfhr : 1.1
         * Var : 2.1
         * Cyc : 3.1
         * Acct : 4.1
         * Dect : 5.1
         * Fmcnt : 6.1
         * Fmv : 7.1
         * Fmd : 8.1
         */

        private String Bfhr;
        private String Var;
        private String Cyc;
        private String Acct;
        private String Dect;
        private String Fmcnt;
        private String Fmv;
        private String Fmd;

        public String getBfhr() {
            return Bfhr;
        }

        public void setBfhr(String Bfhr) {
            this.Bfhr = Bfhr;
        }

        public String getVar() {
            return Var;
        }

        public void setVar(String Var) {
            this.Var = Var;
        }

        public String getCyc() {
            return Cyc;
        }

        public void setCyc(String Cyc) {
            this.Cyc = Cyc;
        }

        public String getAcct() {
            return Acct;
        }

        public void setAcct(String Acct) {
            this.Acct = Acct;
        }

        public String getDect() {
            return Dect;
        }

        public void setDect(String Dect) {
            this.Dect = Dect;
        }

        public String getFmcnt() {
            return Fmcnt;
        }

        public void setFmcnt(String Fmcnt) {
            this.Fmcnt = Fmcnt;
        }

        public String getFmv() {
            return Fmv;
        }

        public void setFmv(String Fmv) {
            this.Fmv = Fmv;
        }

        public String getFmd() {
            return Fmd;
        }

        public void setFmd(String Fmd) {
            this.Fmd = Fmd;
        }
    }
}
