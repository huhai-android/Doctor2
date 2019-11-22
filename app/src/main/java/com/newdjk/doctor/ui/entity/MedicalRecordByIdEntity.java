package com.newdjk.doctor.ui.entity;

import java.util.List;

public class MedicalRecordByIdEntity {

    /**
     * MedicalRecordId : 1
     * PatientId : 2
     * PatientName : sample string 3
     * Sex : sample string 4
     * Age : sample string 5
     * MedicalRecordDate : 2018-12-20T17:16:23.7830422+08:00
     * Description : sample string 7
     * MedicalReportPath : sample string 8
     * MedicalStatus : 64
     * MainProblems : [{"MainProblemId":1,"MedicalRecordId":2,"Problem":"sample string 3","Answer":"sample string 4","AnswerTime":"2018-12-20T17:16:23.7840423+08:00","IsAnswer":64,"Invalid":64,"CreateTime":"2018-12-20T17:16:23.7840423+08:00","UpdateTime":"2018-12-20T17:16:23.7840423+08:00"},{"MainProblemId":1,"MedicalRecordId":2,"Problem":"sample string 3","Answer":"sample string 4","AnswerTime":"2018-12-20T17:16:23.7840423+08:00","IsAnswer":64,"Invalid":64,"CreateTime":"2018-12-20T17:16:23.7840423+08:00","UpdateTime":"2018-12-20T17:16:23.7840423+08:00"}]
     */

    private int MedicalRecordId;
    private int PatientId;
    private String PatientName;
    private String Sex;
    private String Age;
    private String MedicalRecordDate;
    private String Description;
    private String MedicalReportPath;

    public int getIsSave() {
        return IsSave;
    }

    public void setIsSave(int isSave) {
        IsSave = isSave;
    }

    private int IsSave;

    public String getCancellation() {
        return Cancellation;
    }

    public void setCancellation(String cancellation) {
        Cancellation = cancellation;
    }

    private String Cancellation;
    private int MedicalStatus;
    private List<MainProblemsBean> MainProblems;
    private String Advice;

    public int getMedicalRecordId() {
        return MedicalRecordId;
    }

    public void setMedicalRecordId(int MedicalRecordId) {
        this.MedicalRecordId = MedicalRecordId;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getMedicalRecordDate() {
        return MedicalRecordDate;
    }

    public void setMedicalRecordDate(String MedicalRecordDate) {
        this.MedicalRecordDate = MedicalRecordDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMedicalReportPath() {
        return MedicalReportPath;
    }

    public void setMedicalReportPath(String MedicalReportPath) {
        this.MedicalReportPath = MedicalReportPath;
    }

    public int getMedicalStatus() {
        return MedicalStatus;
    }

    public void setMedicalStatus(int MedicalStatus) {
        this.MedicalStatus = MedicalStatus;
    }

    public List<MainProblemsBean> getMainProblems() {
        return MainProblems;
    }

    public void setMainProblems(List<MainProblemsBean> MainProblems) {
        this.MainProblems = MainProblems;
    }

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String advice) {
        Advice = advice;
    }

    public static class MainProblemsBean {
        /**
         * MainProblemId : 1
         * MedicalRecordId : 2
         * Problem : sample string 3
         * Answer : sample string 4
         * AnswerTime : 2018-12-20T17:16:23.7840423+08:00
         * IsAnswer : 64
         * Invalid : 64
         * CreateTime : 2018-12-20T17:16:23.7840423+08:00
         * UpdateTime : 2018-12-20T17:16:23.7840423+08:00
         */

        private int MainProblemId;
        private int MedicalRecordId;
        private String Problem;
        private String Answer;
        private String AnswerTime;
        private int IsAnswer;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;

        public int getMainProblemId() {
            return MainProblemId;
        }

        public void setMainProblemId(int MainProblemId) {
            this.MainProblemId = MainProblemId;
        }

        public int getMedicalRecordId() {
            return MedicalRecordId;
        }

        public void setMedicalRecordId(int MedicalRecordId) {
            this.MedicalRecordId = MedicalRecordId;
        }

        public String getProblem() {
            return Problem;
        }

        public void setProblem(String Problem) {
            this.Problem = Problem;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String Answer) {
            this.Answer = Answer;
        }

        public String getAnswerTime() {
            return AnswerTime;
        }

        public void setAnswerTime(String AnswerTime) {
            this.AnswerTime = AnswerTime;
        }

        public int getIsAnswer() {
            return IsAnswer;
        }

        public void setIsAnswer(int IsAnswer) {
            this.IsAnswer = IsAnswer;
        }

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
