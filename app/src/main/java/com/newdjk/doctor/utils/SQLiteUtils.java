package com.newdjk.doctor.utils;

import android.database.Cursor;

import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.greendao.gen.AllRecordForDoctorEntityDao;
import com.newdjk.doctor.greendao.gen.ChatHistoryEntityDao;
import com.newdjk.doctor.greendao.gen.DaoSession;
import com.newdjk.doctor.greendao.gen.FunctionDataEntityDao;
import com.newdjk.doctor.greendao.gen.PushDataDaoEntityDao;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ChatHistoryEntity;
import com.newdjk.doctor.ui.entity.FunctionDataEntity;
import com.newdjk.doctor.ui.entity.PushDataDaoEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteUtils {
    private static SQLiteUtils instance;
    PushDataDaoEntityDao pushDataDaoEntityDao;
    AllRecordForDoctorEntityDao allRecordForDoctorEntityDao;
    FunctionDataEntityDao functionDataEntityDao;
    ChatHistoryEntityDao mChatHistoryEntityDao;
    DaoSession daoSession;

    private SQLiteUtils() {
        if (MyApplication.getInstance().getDaoSession() != null) {
            pushDataDaoEntityDao = MyApplication.getInstance().getDaoSession().getPushDataDaoEntityDao();
            allRecordForDoctorEntityDao = MyApplication.getInstance().getDaoSession().getAllRecordForDoctorEntityDao();
            functionDataEntityDao = MyApplication.getInstance().getDaoSession().getFunctionDataEntityDao();
            mChatHistoryEntityDao = MyApplication.getInstance().getDaoSession().getChatHistoryEntityDao();
        }

        daoSession = MyApplication.getInstance().getDaoSession();
    }

    public static SQLiteUtils getInstance() {
        if (instance == null) {
            synchronized (SQLiteUtils.class) {
                if (instance == null) {
                    instance = new SQLiteUtils();
                }
            }
        }
        return instance;
    }

    //增加推送数据
    public void addPushData(PushDataDaoEntity testBean) {
        try {
            pushDataDaoEntityDao.insertOrReplace(testBean); //修复bugly上崩溃
        } catch (Exception e) {

        }

    }

    //删除推送数据
    public void deletePushData(PushDataDaoEntity testBean) {
        pushDataDaoEntityDao.delete(testBean);
    }

    //修改推送数据
    public void updatePushData(PushDataDaoEntity testBean) {
        if (pushDataDaoEntityDao!=null){
            pushDataDaoEntityDao.update(testBean);

        }
    }

    //条件查询推送数据
    public List<PushDataDaoEntity> selectAllContactsByDoctorId(int doctorId) {
        if (pushDataDaoEntityDao == null) {

            return new ArrayList<PushDataDaoEntity>();
        }

        pushDataDaoEntityDao.detachAll();//清除缓存

        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId)).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }

    //分页查询系统消息
    public List<PushDataDaoEntity> selectSystemContactsByDoctorId(int doctorId, Date time) {
        pushDataDaoEntityDao.detachAll();//清除缓存

        // List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder() .where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId),PushDataDaoEntityDao.Properties.Title.notEq("新患者报道"),PushDataDaoEntityDao.Properties.Time.lt(time)).orderDesc(PushDataDaoEntityDao.Properties.Time).limit(20).list();
        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId), PushDataDaoEntityDao.Properties.Time.lt(time)).orderDesc(PushDataDaoEntityDao.Properties.Time).limit(20).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }

    //分页查询新患者报道消息
    public List<PushDataDaoEntity> selectNewReportContactsByDoctorId(int doctorId, Date time) {
        pushDataDaoEntityDao.detachAll();//清除缓存

        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId), PushDataDaoEntityDao.Properties.Title.eq("新患者报道"), PushDataDaoEntityDao.Properties.Time.lt(time)).orderDesc(PushDataDaoEntityDao.Properties.Time).limit(20).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }

    //条件查询系统消息
    public List<PushDataDaoEntity> selectAllSystemContactsByDoctorId(int doctorId) {
        try {
            if (pushDataDaoEntityDao != null) {
                pushDataDaoEntityDao.detachAll();//清除缓存

                // List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().distinct() .where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId),PushDataDaoEntityDao.Properties.Title.notEq("新患者报道")).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
                List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().distinct().where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId)).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
                return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
            }

        } catch (Exception e) {

        }
        return new ArrayList<>();
    }


    //条件查询新患者报道消息
    public List<PushDataDaoEntity> selectAllReportsContactsByDoctorId(int doctorId) {
        try {
            if (pushDataDaoEntityDao != null) {
                pushDataDaoEntityDao.detachAll();//清除缓存
                List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().distinct().where(PushDataDaoEntityDao.Properties.DrId.eq(doctorId), PushDataDaoEntityDao.Properties.Title.eq("新患者报道")).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
                return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
            }

        } catch (Exception e) {
            return new ArrayList<PushDataDaoEntity>();
        }
        return new ArrayList<PushDataDaoEntity>();
    }

    //删除推送数据表中所有内容
    public void deleteAllPushData() {
        pushDataDaoEntityDao.deleteAll();
    }


    //增加业务数据
    public void addImData(AllRecordForDoctorEntity dataBean) {
        if (allRecordForDoctorEntityDao!=null){
            allRecordForDoctorEntityDao.insert(dataBean);
        }

    }

    //增加业务数据
    public void addImDataOrUpdate(AllRecordForDoctorEntity dataBean) {
        if (allRecordForDoctorEntityDao!=null){
            allRecordForDoctorEntityDao.insertOrReplace(dataBean);

        }
    }

    //删除业务数据
    public void deleteImData(AllRecordForDoctorEntity dataBean) {
        if (allRecordForDoctorEntityDao!=null) {
            allRecordForDoctorEntityDao.delete(dataBean);

        }
    }

    //修改业务数据
    public void updateImData(AllRecordForDoctorEntity dataBean) {
        if (allRecordForDoctorEntityDao!=null) {
            allRecordForDoctorEntityDao.update(dataBean);

        }
    }

    //根据patientId修改业务数据
    public void updateImDataByPatientId(String isKey, String patientId) {
        if (daoSession!=null){
            String sql = "update ALL_RECORD_FOR_DOCTOR_ENTITY set IS_DR_KEY=? where PATIENT_ID = ?";
            daoSession.getDatabase().execSQL(sql, new String[]{isKey, patientId});
        }

    }

    //根据imid修改业务数据
    public void updateImData(String id) {
        if (daoSession!=null){
            String sql = "update ALL_RECORD_FOR_DOCTOR_ENTITY set UN_READ_NUM=0 where APPLICANT_IMID = ?";
            daoSession.getDatabase().execSQL(sql, new String[]{id});
        }

    }

    //删除业务数据表中所有内容
    public void deleteAllImData() {
        if (allRecordForDoctorEntityDao != null) {
            allRecordForDoctorEntityDao.deleteAll();
        }

    }

    //根据业务码和状态查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByServiceCodeAndStatus(String serviceCode, int status) {
        allRecordForDoctorEntityDao.detachAll();//清除缓存

        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode), AllRecordForDoctorEntityDao.Properties.Status.eq(status)).orderDesc(AllRecordForDoctorEntityDao.Properties.TimeStamp).list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
    }

    //根据业务码和id查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByServiceCodeAndId(String serviceCode, String id) {
        allRecordForDoctorEntityDao.detachAll();//清除缓存

        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode), AllRecordForDoctorEntityDao.Properties.ApplicantIMId.eq(id)).orderDesc(AllRecordForDoctorEntityDao.Properties.TimeStamp).list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
    }

    //根据业务码和id查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByServiceCodeAnDStatueAndDateTime(String serviceCode, int status, String dateTime) {
        allRecordForDoctorEntityDao.detachAll();//清除缓存
        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode), AllRecordForDoctorEntityDao.Properties.Status.eq(status), AllRecordForDoctorEntityDao.Properties.DateTime.eq(dateTime)).orderDesc(AllRecordForDoctorEntityDao.Properties.TimeStamp).list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
    }

    //根据业务码和TYPE查询Im数据
    public String selectImNameByImId(String imId) {

        String sql = "select PATIENT_NAME from ALL_RECORD_FOR_DOCTOR_ENTITY where APPLICANT_IMID=?";
        Cursor cursor = daoSession.getDatabase().rawQuery(sql, new String[]{imId});
        cursor.moveToFirst();
        String patientName = null;
        try {
            patientName = cursor.getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return patientName;
    }

    //根据业务码,TYPE和时间戳模糊查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByImId(String serviceCode, int status, Date dateTime) {
        allRecordForDoctorEntityDao.detachAll();//清除缓存

        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode), AllRecordForDoctorEntityDao.Properties.Status.eq(status), AllRecordForDoctorEntityDao.Properties.DateTime.eq(dateTime)).orderDesc(AllRecordForDoctorEntityDao.Properties.TimeStamp).list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;

    }

    //根据业务码和TYPE查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByServiceCodeAndRecord(String serviceCode, int recordId) {
        if (allRecordForDoctorEntityDao!=null){
            allRecordForDoctorEntityDao.detachAll();//清除缓存

            List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode), AllRecordForDoctorEntityDao.Properties.RecordId.eq(recordId)).orderDesc(AllRecordForDoctorEntityDao.Properties.TimeStamp).list();
            return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
        }
        return  new ArrayList<AllRecordForDoctorEntity>();
    }

    //根据业务id查询Im数据
    public List<AllRecordForDoctorEntity> selectImDataByRecordId(int recordId) {
        allRecordForDoctorEntityDao.detachAll();//清除缓存

        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.RecordId.eq(recordId)).list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
    }

    //查询所有Im数据
    public List<AllRecordForDoctorEntity> selectAllImData() {
        allRecordForDoctorEntityDao.detachAll();//清除缓存

        List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().list();
        return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
    }

    //根据业务查询所有未读的Im数据
    public List<AllRecordForDoctorEntity> selectAllUnreadImDataByServiceCode(String serviceCode) {
        if (allRecordForDoctorEntityDao!=null){
            allRecordForDoctorEntityDao.detachAll();//清除缓存

            List<AllRecordForDoctorEntity> list1 = allRecordForDoctorEntityDao.queryBuilder().where(AllRecordForDoctorEntityDao.Properties.UnReadNum.gt(0), AllRecordForDoctorEntityDao.Properties.ServiceCode.eq(serviceCode)).list();
            return list1 == null ? new ArrayList<AllRecordForDoctorEntity>() : list1;
        }
        return  new ArrayList<AllRecordForDoctorEntity>();
    }

    //使用sql语句根据查询未读会话条数
    public int selectSqlImDataCountByServiceCodeAndType(String serviceCode, int type) {
        if (daoSession!=null){
            Cursor cursor;
            if (type == -1) {
                String sql = "select count(UN_READ_NUM) from ALL_RECORD_FOR_DOCTOR_ENTITY where UN_READ_NUM > 0 and SERVICE_CODE=?";
                cursor = daoSession.getDatabase().rawQuery(sql, new String[]{serviceCode});
            } else {
                String sql = "select count(UN_READ_NUM) from ALL_RECORD_FOR_DOCTOR_ENTITY where UN_READ_NUM > 0 and SERVICE_CODE=? and STATUS= ?";
                cursor = daoSession.getDatabase().rawQuery(sql, new String[]{serviceCode, String.valueOf(type)});
            }
            cursor.moveToFirst();
            int count = 0;
            try {
                count = cursor.getInt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.close();
            return count;
        }
     return 0;
    }

    //根据id查询所有聊天历史数据
    public List<ChatHistoryEntity> selectHistoryDataById(long id) {
        mChatHistoryEntityDao.detachAll();//清除缓存

        List<ChatHistoryEntity> list1 = mChatHistoryEntityDao.queryBuilder().where(ChatHistoryEntityDao.Properties.Id.eq(id)).orderDesc(ChatHistoryEntityDao.Properties.MsgTimestamp).list();
        return list1 == null ? new ArrayList<ChatHistoryEntity>() : list1;
    }

    //查询所有聊天历史数据
    public List<ChatHistoryEntity> selectHistoryData() {
        mChatHistoryEntityDao.detachAll();//清除缓存

        List<ChatHistoryEntity> list1 = mChatHistoryEntityDao.queryBuilder().orderDesc(ChatHistoryEntityDao.Properties.MsgTimestamp).list();
        return list1 == null ? new ArrayList<ChatHistoryEntity>() : list1;
    }

    //查询所有聊天历史数据
    public List<ChatHistoryEntity> selectHistoryDataByPage(int doctorId, int patientId, int msgTimestampStart) {
        if (functionDataEntityDao!=null){
            functionDataEntityDao.detachAll();//清除缓存

            List<ChatHistoryEntity> list1 = mChatHistoryEntityDao.queryBuilder().distinct().where(ChatHistoryEntityDao.Properties.DoctorId.eq(doctorId), ChatHistoryEntityDao.Properties.PatientId.eq(patientId), ChatHistoryEntityDao.Properties.MsgTimestamp.lt(msgTimestampStart)).orderDesc(ChatHistoryEntityDao.Properties.MsgTimestamp).limit(20).list();
            return list1 == null ? new ArrayList<ChatHistoryEntity>() : list1;
        }
        return  new ArrayList<ChatHistoryEntity>();

    }

    //增加聊天历史数据
    public void addHistoryData(ChatHistoryEntity dataBean) {
        mChatHistoryEntityDao.insert(dataBean);
    }

    //医生功能模块操作
    //查询所有聊天历史数据
    public FunctionDataEntity selectFunctionDataById(int doctorId) {
        if (functionDataEntityDao == null) {
            return null;
        }
        functionDataEntityDao.detachAll();//清除缓存

        List<FunctionDataEntity> list1 = functionDataEntityDao.queryBuilder().distinct().where(FunctionDataEntityDao.Properties.DoctorId.eq(doctorId)).list();
        if (list1 != null && list1.size() > 0) {
            return list1.get(0);
        } else {
            return null;
        }
    }

    //增加聊天历史数据
    public void addFunctionData(FunctionDataEntity dataBean) {
        if (functionDataEntityDao!=null){
            functionDataEntityDao.insertOrReplace(dataBean);
        }
    }

}
