package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ScoreEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 11:58
 *  @描述：
 */
public class ScoreValueCopyEntity {


    /**
     * ResultId : 9
     * TotalScore : 2
     * Remark : 危险警告,怀疑胎儿窘迫,请立即向医生进行咨询,进行胎监报告解读
     * ScoreItem : {"Bfhr":2,"Var":0,"Cyc":0,"Acct":0,"Dect":0,"Fmcnt":0,"Fmv":0,"Fmd":0}
     */

    private int ResultId;
    private int TotalScore;
    private String Remark;
    private ScoreItemBean ScoreItem;

    public int getResultId() {
        return ResultId;
    }

    public void setResultId(int ResultId) {
        this.ResultId = ResultId;
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int TotalScore) {
        this.TotalScore = TotalScore;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public ScoreItemBean getScoreItem() {
        return ScoreItem;
    }

    public void setScoreItem(ScoreItemBean ScoreItem) {
        this.ScoreItem = ScoreItem;
    }

    public static class ScoreItemBean {
        /**
         * Bfhr : 2
         * Var : 0
         * Cyc : 0
         * Acct : 0
         * Dect : 0
         * Fmcnt : 0
         * Fmv : 0
         * Fmd : 0
         */

        private double Bfhr;
        private double Var;
        private double Cyc;
        private double Acct;
        private double Dect;
        private double Fmcnt;
        private double Fmv;
        private double Fmd;

        public double getBfhr() {
            return Bfhr;
        }

        public void setBfhr(double Bfhr) {
            this.Bfhr = Bfhr;
        }

        public double getVar() {
            return Var;
        }

        public void setVar(double Var) {
            this.Var = Var;
        }

        public double getCyc() {
            return Cyc;
        }

        public void setCyc(int Cyc) {
            this.Cyc = Cyc;
        }

        public double getAcct() {
            return Acct;
        }

        public void setAcct(double Acct) {
            this.Acct = Acct;
        }

        public double getDect() {
            return Dect;
        }

        public void setDect(double Dect) {
            this.Dect = Dect;
        }

        public double getFmcnt() {
            return Fmcnt;
        }

        public void setFmcnt(double Fmcnt) {
            this.Fmcnt = Fmcnt;
        }

        public double getFmv() {
            return Fmv;
        }

        public void setFmv(double Fmv) {
            this.Fmv = Fmv;
        }

        public double getFmd() {
            return Fmd;
        }

        public void setFmd(double Fmd) {
            this.Fmd = Fmd;
        }
    }
}
