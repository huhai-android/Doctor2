package com.newdjk.doctor.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.MainActivity;
import com.newdjk.doctor.ui.entity.DePartmentEntity;
import com.newdjk.doctor.ui.entity.DoctorInfoByIdEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.HospitalEntity;
import com.newdjk.doctor.ui.entity.LoginEb;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.TitleEntity;
import com.newdjk.doctor.ui.entity.UpdateImageView;
import com.newdjk.doctor.utils.AuthenticationCommentUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.BottomSexDialog;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;


/**
 * 注册认证 第一步
 */
public class Authentication1Activity extends BasicActivity {


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
    @BindView(R.id.fl_header)
    FrameLayout flHeader;
    @BindView(R.id.name_tip)
    TextView nameTip;
    @BindView(R.id.drName)
    EditText drName;
    @BindView(R.id.sex_tip)
    TextView sexTip;
    @BindView(R.id.tv_dr_sex)
    TextView tvDrSex;
    @BindView(R.id.rl_sex)
    LinearLayout rlSex;
    @BindView(R.id.hospital_name)
    TextView hospitalName;
    @BindView(R.id.hospital)
    LinearLayout hospital;
    @BindView(R.id.department_name)
    TextView departmentName;
    @BindView(R.id.department)
    LinearLayout department;
    @BindView(R.id.profession_name)
    TextView professionName;
    @BindView(R.id.profession)
    LinearLayout profession;
    @BindView(R.id.post_name)
    TextView postName;
    @BindView(R.id.post)
    LinearLayout post;
    @BindView(R.id.et_be_good_at)
    EditText etBeGoodAt;
    @BindView(R.id.next_step)
    TextView nextStep;
    private Gson mGson;
    private int mUserType;
    private String mobile;
    private String patientPassword;
    private String mobileCode;
    private HospitalEntity.DataBean.ReturnDataBean hospitalMessageBean; //医院实体
    private TitleEntity.DataBean titleBean;
    private DePartmentEntity.DataBean.ReturnDataBean departmentBean;
    private int mFlag;
    private String isCustomHospital="";
    private DoctorInfoByIdEntity mDoctorInfoByIdEntity;

    private final static String TAG = "注册认证第一步";
    private final static int REQUEST_CODE = 10;
    private boolean mSex = true;
    private int mTag, mTip;


    public static Intent getAct(Context context) {
        return new Intent(context, Authentication1Activity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_authentication1;
    }

    @Override
    protected void initView() {

        hospital.setOnClickListener(this);
        department.setOnClickListener(this);
        profession.setOnClickListener(this);
        post.setOnClickListener(this);
        nextStep.setOnClickListener(this);
        mTag = getIntent().getIntExtra("tag", -1);
        mTip = getIntent().getIntExtra("tip", -1);
        hospitalMessageBean = new HospitalEntity.DataBean.ReturnDataBean();
        titleBean = new TitleEntity.DataBean();
        departmentBean = new DePartmentEntity.DataBean.ReturnDataBean();
        tvDrSex.setText("男");
        mUserType = 9;
        SpUtils.put(Contants.canLogin,1);
        if (mTag != 1) {
            flHeader.setVisibility(View.VISIBLE);
            nextStep.setText("下一步");
            topTitle.setText("资质认证");
        } else {
            initBackTitle("完善资料");
        }

        topLeft.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        rlSex.setOnClickListener(this);
        topLeft.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mFlag = SpUtils.getInt(Contants.Id, -1);
        if (mFlag != -1) {
            getDorInfo();
        }
    }

    /**
     * 如果存在id 获取医生信息
     */
    private void getDorInfo() {

     /*   Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));*/
        String url = HttpUrl.QueryDoctorInfoByDrId + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        if (mMyOkhttp==null){
            mMyOkhttp= MyApplication.getInstance().getMyOkHttp();
        }
        mMyOkhttp.get().url(url).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorInfoByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, final ResponseEntity<DoctorInfoByIdEntity> entity) {
                if (entity.getCode() == 0) {
                    LoadDialog.clear();
                    mDoctorInfoByIdEntity = entity.getData();
                    MyApplication.mDoctorInfoByIdEntity = mDoctorInfoByIdEntity;
                    if (mDoctorInfoByIdEntity != null) {
                        //设置医护姓名
                        drName.setText(mDoctorInfoByIdEntity.getDrName() == null ? "" : mDoctorInfoByIdEntity.getDrName());
                        //设置医院 text
                        hospitalName.setText(mDoctorInfoByIdEntity.getHospitalName() == null ? "" : mDoctorInfoByIdEntity.getHospitalName());
                        //设置科室 text
                        departmentName.setText(mDoctorInfoByIdEntity.getDepartmentName() == null ? "" : mDoctorInfoByIdEntity.getDepartmentName());
                        departmentBean.setDepartmentName(mDoctorInfoByIdEntity.getDepartmentName() == null ? "" : mDoctorInfoByIdEntity.getDepartmentName());
                        departmentBean.setDepartmentId(mDoctorInfoByIdEntity.getDepartmentId());
                        //设置医生类别 text


                        //设置医院名称和id
                        hospitalMessageBean.setHospitalName(mDoctorInfoByIdEntity.getHospitalName() == null ? "" : mDoctorInfoByIdEntity.getHospitalName());
                        hospitalMessageBean.setHospitalId(mDoctorInfoByIdEntity.getHospitalId());

                        //设置职称
                        String positionName = mDoctorInfoByIdEntity.getPositionName();
                        titleBean.setCategoryItemName(positionName == null ? "" : positionName);
//                        if (positionName.contains("主任")) {
//                            SpUtils.put(Contants.categoryItemId, "44");
//                        }
                        titleBean.setCategoryItemValue(mDoctorInfoByIdEntity.getPosition());
                        postName.setText(titleBean.getCategoryItemName());
                        if (titleBean.getCategoryItemName().contains("乡村")) {
                            professionName.setText("乡村医生");
                            mUserType = 9;
                        } else {
                            if (mDoctorInfoByIdEntity.getDrType() == 1) {
                                professionName.setText("医生");
                                mUserType = 9;
                            } else if (mDoctorInfoByIdEntity.getDrType() == 2) {
                                professionName.setText("护士");
                                mUserType = 8;
                            }
                        }

                        String skill = mDoctorInfoByIdEntity.getDoctorSkill();
                        etBeGoodAt.setText(skill == null ? "" : skill);
                        String sex;
                        switch (mDoctorInfoByIdEntity.getDrSex()) {
                            case 1:
                                sex = "男";
                                mSex = true;
                                break;
                            case 2:
                                sex = "女";
                                mSex = false;
                                break;
                            default:
                                mSex = true;
                                sex = "男";
                                break;
                        }
                        tvDrSex.setText(sex);
                    }


                } else {
                    toast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.hospital:
                //选择医院
                Intent mChooseIntent = new Intent(this, ChooseHospitalActivity.class);
                startActivityForResult(mChooseIntent, 1);
                break;
            case R.id.department:
                //选择科室
                Intent mDepartmentIntent = new Intent(this, ChooseDepartmentActivity.class);
                startActivityForResult(mDepartmentIntent, 4);
                break;
            case R.id.profession:
                //选择职业
                if (mDoctorInfoByIdEntity != null) {
                    if (TextUtils.isEmpty(mDoctorInfoByIdEntity.getPositionName())) {
                        if (TextUtils.isEmpty(mDoctorInfoByIdEntity.getDrName())) {
                            Intent mChooseUserTypeIntent = new Intent(this, ChooseUserTypeActivity.class);
                            mChooseUserTypeIntent.putExtra("PositionName", professionName.getText());
                            startActivityForResult(mChooseUserTypeIntent, 2);
                        } else {
                            Intent mChooseUserTypeIntent = new Intent(this, ChooseUserTypeActivity.class);
                            mChooseUserTypeIntent.putExtra("PositionName", professionName.getText());
                            startActivityForResult(mChooseUserTypeIntent, 2);
                        }
                    } else {
                        toast("不能更改职业！");
                    }

                }

                break;
            case R.id.post:
                if (mUserType == 0) {
                    toast("请先选择职业");
                    return;
                }
                if (professionName.getText().toString().trim().equals("乡村医生")) {
                    toast("无法修改乡村医生职称！");
                    return;
                }
                Intent mTitleIntent = new Intent(this, ChooseTitleActivity.class);
                mTitleIntent.putExtra("type", mUserType);
                startActivityForResult(mTitleIntent, 3);

                break;
            case R.id.next_step:

                register();
                break;

            case R.id.rl_sex:
                BottomSexDialog.getInstance().showBottomSexDialog(this, mSex, new BottomSexDialog.SexSelectListener() {
                    @Override
                    public void sexSelectListener(boolean b) {
                        mSex = b;
                        if (mSex) {
                            tvDrSex.setText("男");
                        } else {
                            tvDrSex.setText("女");
                        }
                    }
                });
                break;
            case R.id.top_left:
                this.finish();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        Intent intent = getIntent();
        mobile = intent.getStringExtra("Mobile");
        patientPassword = intent.getStringExtra("PatientPassword");
        mobileCode = intent.getStringExtra("MobileCode");
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    hospitalMessageBean = (HospitalEntity.DataBean.ReturnDataBean) data.getSerializableExtra("hospital_message");
                    hospitalName.setText(hospitalMessageBean.getHospitalName());
                    isCustomHospital="";
                    break;
                case 2:
                    mUserType = data.getIntExtra("type", 0);
                    if (mUserType != 0) {
                        String typeName = data.getStringExtra("typeName");
                        professionName.setText(typeName);
                        if (typeName.equals("护士")) {
                            if (!TextUtils.isEmpty(postName.getText().toString().trim())) {
                                if (postName.getText().toString().trim().contains("医")) {
                                    postName.setText("");
                                    titleBean.setCategoryItemName("");
                                }
                                if (postName.getText().toString().trim().contains("乡村")) {
                                    postName.setText("");
                                    titleBean.setCategoryItemName("");
                                }

                            }

                        } else if (typeName.equals("医生")) {
                            if (!TextUtils.isEmpty(postName.getText().toString().trim())) {
                                if (postName.getText().toString().trim().contains("护")) {
                                    postName.setText("");
                                    titleBean.setCategoryItemName("");
                                }
                                if (postName.getText().toString().trim().contains("乡村")) {
                                    postName.setText("");
                                    titleBean.setCategoryItemName("");
                                }
                            }
                        } else if (typeName.equals("乡村医生")) {
                            postName.setText("乡村医生");
                            titleBean.setCategoryItemName("乡村医生");
                            titleBean.setCategoryItemValue(11);

                        }


                    }
                    break;
                case 3:
                    titleBean = (TitleEntity.DataBean) data.getSerializableExtra("titleBean");
                    postName.setText(titleBean.getCategoryItemName());
                    SpUtils.put(Contants.categoryItemId, String.valueOf(titleBean.getCategoryItemId()));
                    break;
                case 4:
                    departmentBean = (DePartmentEntity.DataBean.ReturnDataBean) data.getSerializableExtra("returnDataBean");
                    departmentName.setText(departmentBean.getDepartmentName());
                    break;
                case 10:
                    //mFlag = true;
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void register() {
        if (TextUtils.isEmpty(StrUtil.getString(drName))) {
            toast("请输入医护姓名");
            return;
        }
        if (mUserType == 0) {
            toast("请选择职业");
            return;
        }
        if (TextUtils.isEmpty(hospitalName.getText())) {
            toast("请选择医院");
            return;
        }
        if (TextUtils.isEmpty(departmentBean.getDepartmentName())) {
            toast("请选择科室");
            return;
        }

        if (TextUtils.isEmpty(titleBean.getCategoryItemName())) {
            toast("请选择职称");
            return;
        }

        SpUtils.put(Contants.Name, StrUtil.getString(drName));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("DrName", StrUtil.getString(drName));//医护姓名
        bodyMap.put("HospitalId",(TextUtils.isEmpty(isCustomHospital)?hospitalMessageBean.getHospitalId():isCustomHospital ) + "");//医院id
        bodyMap.put("HospitalName", hospitalName.getText().toString());//医院名称
        bodyMap.put("DepartmentId", departmentBean.getDepartmentId() + "");//科室id
        bodyMap.put("DepartmentName", departmentBean.getDepartmentName());//科室名称
        bodyMap.put("DrType", mUserType == 8 ? "2" : "1");//医生类别(1-医生，2-护士)，职业
        bodyMap.put("Position", titleBean.getCategoryItemValue() + "");//职称
        // String id = String.valueOf(SpUtils.getInt(Contants.Id, -1));
        bodyMap.put("DrId", mFlag + "");//医护主键Id(新增的时赋值0


        if (mSex) {
            bodyMap.put("DrSex", "1");
        } else {
            bodyMap.put("DrSex", "2");
        }
        String goodAt = etBeGoodAt.getText().toString().trim();
        if (TextUtils.isEmpty(goodAt)) {
            bodyMap.put("DoctorSkill", "");
        } else {
            bodyMap.put("DoctorSkill", goodAt);
        }

        SpUtils.put(Contants.DocType, mUserType == 8 ? 2 : 1);
        //返回修改提交
        loading(true);
        mMyOkhttp.post().url(HttpUrl.UpdateBasicDoctorInfo).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                Log.e(TAG, "onSuccess: " + entity.getMessage() + "   " + entity.getCode() + "   " + entity.getData());
                if (entity.getCode() == 0) {

                    Intent intent = null;
                    if (mTag == 1) {
                        doLoginRequest();
                    } else {
                        intent = new Intent(Authentication1Activity.this, Authentication2Activity.class);
                        intent.putExtra(Contants.JobName, titleBean.getCategoryItemName());
                        toActivity(intent);
                    }
                    EventBus.getDefault().post(new UpdateImageView(true));


                } else {
                    toast(entity.getMessage());
                }

                loading(false);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                loading(false);
            }
        });




        /*MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        final Request request = new Request.Builder()
                .url(HttpUrl.DoctorRegister)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("zdp", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.i("zdp", "register=" + responseStr);
                Type jsonType = new TypeToken<ResponseEntity<UserMessageEntity>>() {
                }.getType();
                ResponseEntity<UserMessageEntity> responseEntity = mGson.fromJson(responseStr,jsonType);
                SpUtils.put(Contants.Id,responseEntity.getData().getDrId());
                SpUtils.put(Contants.Mobile,responseEntity.getData().getMobile());
                SpUtils.put(Contants.Name,responseEntity.getData().getDrName());
                SpUtils.put(Contants.Password,responseEntity.getData().getPassword());
                Intent intent = new Intent(Authentication1Activity.this,Authentication2Activity.class);
                startActivity(intent);
            }
        });*/
    }

    private void doLoginRequest() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", "APP");
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Mobile", SpUtils.getString(Contants.userName));
        bodyMap.put("Type", "2");//登录类型:1验证码，2账号
        bodyMap.put("Code", SpUtils.getString(Contants.Password));

        mMyOkhttp.post().url(HttpUrl.DoctorLogin).params(bodyMap).headers(headMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
            @Override
            public void onSuccess(int statusCode, LoginEntity entituy) {
                LoadDialog.clear();
                Log.e("test", "onSuccess: " + entituy.toString());
                if (entituy.getCode() == 0) {
                    String mobile = SpUtils.getString(Contants.userName);
                    //    if (mobile != null && !mobile.equals(entituy.getData().getData().getMobile())) {
                    BJCASDK.getInstance().clearCert(Authentication1Activity.this);
                    //      }
                    EventBus.getDefault().post(new LoginEb(SpUtils.getString(Contants.userName), SpUtils.getString(Contants.Password)));
                    MyApplication.token = entituy.getData().getToken();
                    AuthenticationCommentUtil.getInstance().myShared(entituy);
                    Intent intent = new Intent(Authentication1Activity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (mTip != 2) {
                        toast("注册成功");
                    }
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                if (statusCode == 1001) {
                    errorMsg = "账号或密码错误！";
                }
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HospitalEntity.DataBean.ReturnDataBean event) {
        Log.d(TAG, "收到新消息" + event.toString());
        hospitalName.setText(event.getHospitalName());
        hospitalMessageBean=event;
        if (event.isCustom()){
            isCustomHospital="0";
        }else {
            isCustomHospital="";
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
