package com.newdjk.doctor.ui.entity;

import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AllDoctorCheckEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/6 14:48
 *  @描述：
 */
public class AllDoctorCheckReportEntity {


    /**
     * AskId : 8
     * MonitorId : 13
     * AskNo : 2000008
     * ResultItems : [{"Id":1,"Name":"无反应型"},{"Id":2,"Name":"可疑型"},{"Id":3,"Name":"反应型"},{"Id":4,"Name":"正弦曲线"},{"Id":5,"Name":"NST无法判读"},{"Id":9,"Name":"危险警告"}]
     * ScoreResult : {"TotalScore":9,"ScoreItem":{"Bfhr":{"Value":"140","Score":2},"Var":{"Value":"11","Score":2},"Cyc":{"Value":"8","Score":0},"Acct":{"Value":"4","Score":0},"Dect":{"Value":"0","Score":0},"Fmcnt":{"Value":"4","Score":2},"Fmv":{"Value":"14","Score":1},"Fmd":{"Value":"25","Score":2}}}
     * DrRemark : null
     * OrgName : null
     * PatientName : ad
     * Age : null
     * Weeks : null
     * Mobile : null
     * Sex : 0
     * Duration : 1202
     * BeginDate : 2017-11-01 09:39:57
     * EndDate : 2017-11-01 09:59:59
     * MonitorTime : 2018-10-15 15:20:13
     */

    private int AskId;
    private int MonitorId;
    private String AskNo;
    private ScoreResultBean ScoreResult;
    private String DrRemark;
    private String OrgName;
    private String PatientName;
    private String Age;
    private String Weeks;
    private String Mobile;
    private int Sex;
    private int Duration;
    private String BeginDate;
    private String EndDate;
    private String MonitorTime;
    private List<ResultItemsBean> ResultItems;

    @Override
    public String toString() {
        return "AllDoctorCheckReportEntity{" +
                "AskId=" + AskId +
                ", MonitorId=" + MonitorId +
                ", AskNo='" + AskNo + '\'' +
                ", ScoreResult=" + ScoreResult +
                ", DrRemark='" + DrRemark + '\'' +
                ", OrgName='" + OrgName + '\'' +
                ", PatientName='" + PatientName + '\'' +
                ", Age='" + Age + '\'' +
                ", Weeks='" + Weeks + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Sex=" + Sex +
                ", Duration=" + Duration +
                ", BeginDate='" + BeginDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", MonitorTime='" + MonitorTime + '\'' +
                ", ResultItems=" + ResultItems +
                '}';
    }

    public int getAskId() {
        return AskId;
    }

    public void setAskId(int AskId) {
        this.AskId = AskId;
    }

    public int getMonitorId() {
        return MonitorId;
    }

    public void setMonitorId(int MonitorId) {
        this.MonitorId = MonitorId;
    }

    public String getAskNo() {
        return AskNo;
    }

    public void setAskNo(String AskNo) {
        this.AskNo = AskNo;
    }

    public ScoreResultBean getScoreResult() {
        return ScoreResult;
    }

    public void setScoreResult(ScoreResultBean ScoreResult) {
        this.ScoreResult = ScoreResult;
    }

    public String getDrRemark() {
        return DrRemark;
    }

    public void setDrRemark(String DrRemark) {
        this.DrRemark = DrRemark;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getWeeks() {
        return Weeks;
    }

    public void setWeeks(String Weeks) {
        this.Weeks = Weeks;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String BeginDate) {
        this.BeginDate = BeginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getMonitorTime() {
        return MonitorTime;
    }

    public void setMonitorTime(String MonitorTime) {
        this.MonitorTime = MonitorTime;
    }

    public List<ResultItemsBean> getResultItems() {
        return ResultItems;
    }

    public void setResultItems(List<ResultItemsBean> ResultItems) {
        this.ResultItems = ResultItems;
    }

    public static class ScoreResultBean {
        /**
         * TotalScore : 9
         * ScoreItem : {"Bfhr":{"Value":"140","Score":2},"Var":{"Value":"11","Score":2},"Cyc":{"Value":"8","Score":0},"Acct":{"Value":"4","Score":0},"Dect":{"Value":"0","Score":0},"Fmcnt":{"Value":"4","Score":2},"Fmv":{"Value":"14","Score":1},"Fmd":{"Value":"25","Score":2}}
         */

        private double TotalScore;
        private ScoreItemBean ScoreItem;

        public double getTotalScore() {
            return TotalScore;
        }

        public void setTotalScore(int TotalScore) {
            this.TotalScore = TotalScore;
        }

        public ScoreItemBean getScoreItem() {
            return ScoreItem;
        }

        public void setScoreItem(ScoreItemBean ScoreItem) {
            this.ScoreItem = ScoreItem;
        }

        public static class ScoreItemBean {
            /**
             * Bfhr : {"Value":"140","Score":2}
             * Var : {"Value":"11","Score":2}
             * Cyc : {"Value":"8","Score":0}
             * Acct : {"Value":"4","Score":0}
             * Dect : {"Value":"0","Score":0}
             * Fmcnt : {"Value":"4","Score":2}
             * Fmv : {"Value":"14","Score":1}
             * Fmd : {"Value":"25","Score":2}
             */

            private BfhrBean Bfhr;
            private VarBean Var;
            private CycBean Cyc;
            private AcctBean Acct;
            private DectBean Dect;
            private FmcntBean Fmcnt;
            private FmvBean Fmv;
            private FmdBean Fmd;

            @Override
            public String toString() {
                return "ScoreItemBean{" +
                        "Bfhr=" + Bfhr +
                        ", Var=" + Var +
                        ", Cyc=" + Cyc +
                        ", Acct=" + Acct +
                        ", Dect=" + Dect +
                        ", Fmcnt=" + Fmcnt +
                        ", Fmv=" + Fmv +
                        ", Fmd=" + Fmd +
                        '}';
            }

            public BfhrBean getBfhr() {
                return Bfhr;
            }

            public void setBfhr(BfhrBean Bfhr) {
                this.Bfhr = Bfhr;
            }

            public VarBean getVar() {
                return Var;
            }

            public void setVar(VarBean Var) {
                this.Var = Var;
            }

            public CycBean getCyc() {
                return Cyc;
            }

            public void setCyc(CycBean Cyc) {
                this.Cyc = Cyc;
            }

            public AcctBean getAcct() {
                return Acct;
            }

            public void setAcct(AcctBean Acct) {
                this.Acct = Acct;
            }

            public DectBean getDect() {
                return Dect;
            }

            public void setDect(DectBean Dect) {
                this.Dect = Dect;
            }

            public FmcntBean getFmcnt() {
                return Fmcnt;
            }

            public void setFmcnt(FmcntBean Fmcnt) {
                this.Fmcnt = Fmcnt;
            }

            public FmvBean getFmv() {
                return Fmv;
            }

            public void setFmv(FmvBean Fmv) {
                this.Fmv = Fmv;
            }

            public FmdBean getFmd() {
                return Fmd;
            }

            public void setFmd(FmdBean Fmd) {
                this.Fmd = Fmd;
            }

            public static class BfhrBean {
                /**
                 * Value : 140
                 * Score : 2
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "BfhrBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class VarBean {
                /**
                 * Value : 11
                 * Score : 2
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "VarBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class CycBean {
                /**
                 * Value : 8
                 * Score : 0
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "CycBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class AcctBean {
                /**
                 * Value : 4
                 * Score : 0
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "AcctBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class DectBean {
                /**
                 * Value : 0
                 * Score : 0
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "DectBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class FmcntBean {
                /**
                 * Value : 4
                 * Score : 2
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "FmcntBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class FmvBean {
                /**
                 * Value : 14
                 * Score : 1
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "FmvBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }

            public static class FmdBean {
                /**
                 * Value : 25
                 * Score : 2
                 */

                private String Value;
                private double Score;

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public double getScore() {
                    return Score;
                }

                public void setScore(int Score) {
                    this.Score = Score;
                }

                @Override
                public String toString() {
                    return "FmdBean{" +
                            "Value='" + Value + '\'' +
                            ", Score=" + Score +
                            '}';
                }
            }
        }
    }

    public static class ResultItemsBean {
        /**
         * Id : 1
         * Name : 无反应型
         */

        private int Id;
        private String Name;
        private boolean Checked;
        private String Remark;

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public boolean isChecked() {
            return Checked;
        }

        public void setChecked(boolean checked) {
            Checked = checked;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        @Override
        public String toString() {
            return "ResultItemsBean{" +
                    "Id=" + Id +
                    ", Name='" + Name + '\'' +
                    ", Checked=" + Checked +
                    '}';
        }
    }
}
