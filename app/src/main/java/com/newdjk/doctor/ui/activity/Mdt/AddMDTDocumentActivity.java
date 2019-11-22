package com.newdjk.doctor.ui.activity.Mdt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.AddMdtDocumentAdapter;
import com.newdjk.doctor.ui.entity.DeleteMedicaEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MDTDocumentEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MedicalView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class AddMDTDocumentActivity extends BasicActivity {
    private static final String TAG = "AddDocumentActivity";
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.recyleview)
    SwipeMenuRecyclerView recyleview;
    @BindView(R.id.tv_sign_report)
    TextView tvSignReport;
    private AddMdtDocumentAdapter mMyDiagnoseAdapter;
    private List<MDTDocumentEntity.UnDefalutMedicalsBean> mList;
    private int mMedicalRecordId;
    private List<Integer> mSelectedList=new ArrayList<>();
    private List<Integer> mDeleteList = new ArrayList<>();
    private MedicalView mMedicalView;
    private DeleteMedicaEntity mDeleteMedicaEntity;
    private MDTDetailEntity mMDTDetailEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_adddocument;
    }

    @Override
    protected void initView() {
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);

        mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");
        MyApplication.mActivities.add(this);
        initBackTitle(getString(R.string.add_document));
        mList = new ArrayList<>();
        mDeleteMedicaEntity = new DeleteMedicaEntity();
        mDeleteMedicaEntity.setDrId(MyApplication.mDoctorInfoByIdEntity.getDrId());
        mDeleteMedicaEntity.setDrName(MyApplication.mDoctorInfoByIdEntity.getDrName());
        mDeleteMedicaEntity.setMedicalIds(mSelectedList);
//        mMedicalView = new MedicalView(this, mMedicalRecordId);
//        mMedicalView.show();

        recyleview.setSwipeMenuCreator(swipeMenuCreator);
        recyleview.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mMyDiagnoseAdapter = new AddMdtDocumentAdapter(mList);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mMyDiagnoseAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mActivities.remove(this);
       // mMedicalView.destory();
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int viewType = mMyDiagnoseAdapter.getItemViewType(position);
            if (viewType!=0){
                SwipeMenuItem deleteItem = new SwipeMenuItem(AddMDTDocumentActivity.this).setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();
            if (mList.get(position).getIsCustom()==0){
                toast("非自定义消息无法删除");
                return;
            }
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            Log.d(TAG, "删除" + position);
            mDeleteList.clear();
            mDeleteList.add(mList.get(position).getMedicalId());
            DeleteMedicaEntity deleteMedicaEntity = new DeleteMedicaEntity();
            deleteMedicaEntity.setMedicalIds(mDeleteList);
            deleteDefinedMedical(position, deleteMedicaEntity);

        }
    };


    @Override
    protected void initListener() {
        tvSignReport.setOnClickListener(this);
        mMyDiagnoseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position < mList.size() - 1) {
                    if (mList.get(position).isSelected()) {
                        mList.get(position).setSelected(false);
                        try {
                            mDeleteMedicaEntity.getMedicalIds().remove(mList.get(position).getMedicalId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMyDiagnoseAdapter.setData(position, mList.get(position));

                    } else {
                        mList.get(position).setSelected(true);
                        try {
                            mDeleteMedicaEntity.getMedicalIds().add(mList.get(position).getMedicalId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMyDiagnoseAdapter.setData(position, mList.get(position));

                    }
                }
            }
        });


        mMyDiagnoseAdapter.setOnDocumentAddListener(new AddMdtDocumentAdapter.onDocumentAddListener() {
            @Override
            public void saveDocument(int position, String title, String content) {
                //保存
                drAddDefinedMedical(title, content);
            }

            @Override
            public void cancelDcoument(int position) {
                //取消

            }
        });

    }

    @Override
    protected void initData() {
        getMedicalItemsByMedicalRecordId();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

            case R.id.tv_sign_report:
                if (mSelectedList.size() > 0) {
                    SendNeedSupplyMedical();
                } else {
                    toast("请先选择要发送的材料！");
                }
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddMDTDocumentActivity.class);
        return intent;
    }

    //获取记录
    private void getMedicalItemsByMedicalRecordId() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));


        String url = HttpUrl.QuerySupplyMDTMedical + "?SubjectBuyRecordId=" + mMDTDetailEntity.getSubjectBuyRecordId();
        // String url = "http://172.18.30.4/NetHospSecondDiagnosipsAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDocumentEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDocumentEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.clear();
                    List<MDTDocumentEntity.UnDefalutMedicalsBean> documentlist = entity.getData().getUnDefalutMedicals();
                    mList.addAll(documentlist);
                    mList.addAll(mList.size(), entity.getData().getDefinedMedicals());
                    MDTDocumentEntity.UnDefalutMedicalsBean medicalsBean = new MDTDocumentEntity.UnDefalutMedicalsBean();
                    medicalsBean.setMedicalName("自定义材料");
                    medicalsBean.setMedicalDesc("请输入其它需要上传的材料");
                    mList.add(medicalsBean);
                    mMyDiagnoseAdapter.setNewData(mList);

                    Log.i("SymptomsSelectActivity", "entity=" + entity.getData().toString());

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //发送材料
    private void SendNeedSupplyMedical() {
        loading(true);
        String json = new Gson().toJson(mDeleteMedicaEntity);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.MDTSendNeedSupplyMedical).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("发送成功");
                       MyApplication.exit();
                    } else {
                        toast("发送失败");
                    }
                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //添加自定义材料
    private void drAddDefinedMedical(String medicalName, String medicalDesc) {
        loading(true);
        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId()+"");
        map.put("MedicalName", String.valueOf(medicalName));
        map.put("MedicalDesc", medicalDesc);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.MdtDrAddDefinedMedical).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDocumentEntity.UnDefalutMedicalsBean>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDocumentEntity.UnDefalutMedicalsBean> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    MDTDocumentEntity.UnDefalutMedicalsBean medicalsBean = entity.getData();
                    mList.add(mList.size() - 1, medicalsBean);
                    mMyDiagnoseAdapter.notifyDataSetChanged();
                } else {
                    toast(entity.getMessage() + "");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //删除自定义材料
    private void deleteDefinedMedical(final int position, DeleteMedicaEntity deleteMedicaEntity) {

        loading(true);
        String json = new Gson().toJson(deleteMedicaEntity);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.MDTDeleteDefinedMedical).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.remove(position);
                    mMyDiagnoseAdapter.notifyDataSetChanged();
                } else {
                    toast(entity.getMessage() + "");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
