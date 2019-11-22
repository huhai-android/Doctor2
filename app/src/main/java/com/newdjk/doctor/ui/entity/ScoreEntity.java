package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   ScoreEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/8 11:58
 *  @描述：
 */
public class ScoreEntity {

    /**
     * ScoreType : 1
     * Item : {"Bfhr":1.1,"Var":2.1,"Cyc":3.1,"Acct":4.1,"Dect":5.1,"Fmcnt":6.1,"Fmv":7.1,"Fmd":8.1}
     */

    private int ScoreType;
    private ItemBean Item;

    public int getScoreType() {
        return ScoreType;
    }

    public void setScoreType(int ScoreType) {
        this.ScoreType = ScoreType;
    }

    public ItemBean getItem() {
        return Item;
    }

    public void setItem(ItemBean Item) {
        this.Item = Item;
    }

    public static class ItemBean {
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
