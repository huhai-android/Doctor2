package com.newdjk.doctor.ui.activity.login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.DoctorImagSaveRequestEntity;
import com.newdjk.doctor.ui.entity.DoctorRegImgEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.uploadimagelib.GridViewAdapter;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.SelectedPictureDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;

/**
 * 注册认证 第三步
 */
public class Authentication3Activity extends BasicActivity {


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
    @BindView(R.id.licensed_first_image)
    ImageView licensedFirstImage;
    @BindView(R.id.licensed_second_image)
    ImageView licensedSecondImage;
    @BindView(R.id.practice_license_number)
    TextView practiceLicenseNumber;
    @BindView(R.id.register_date)
    TextView registerDate;
    @BindView(R.id.register_date_select_layout)
    RelativeLayout registerDateSelectLayout;
    @BindView(R.id.register_validity)
    TextView registerValidity;
    @BindView(R.id.register_validity_select_layout)
    RelativeLayout registerValiditySelectLayout;
    @BindView(R.id.next_step)
    TextView nextStep;
    @BindView(R.id.practice_license)
    EditText practiceLicense;
    private Calendar mCalendar;
    private SelectedPictureDialog mSelectedPictureDialog;
    private SimpleDateFormat simpleDateFormat;
    private Date mRegisterData;
    private Date mValidData;
    private String mName;
    private String mIdCard;
    private String mFirstPicturePath;
    private String mSecondPicturePath;
    private String mThridPicturePath;
    private String mFourthPicturePath;
    private List<DoctorRegImgEntity> mList;
    private Gson mGson;
    private int mDoctype;

    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList = new ArrayList<>(); //上传的图片凭证的数据源

    public static Intent getAct(Context context) {
        return new Intent(context, Authentication3Activity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_authentication3;
    }

    @Override
    protected void initView() {
        nextStep.setOnClickListener(this);
        mDoctype = SpUtils.getInt(Contants.DocType,0);
        mCalendar = Calendar.getInstance(Locale.CHINA);
        licensedFirstImage.setOnClickListener(this);
        licensedSecondImage.setOnClickListener(this);
        registerDateSelectLayout.setOnClickListener(this);
        registerValiditySelectLayout.setOnClickListener(this);
        initBackTitle("注册认证");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.licensed_first_image:
                mSelectedPictureDialog = new SelectedPictureDialog(Authentication3Activity.this, "first");
                mSelectedPictureDialog.show();
                break;
            case R.id.licensed_second_image:
                mSelectedPictureDialog = new SelectedPictureDialog(Authentication3Activity.this, "second");
                mSelectedPictureDialog.show();
                break;
            case R.id.register_date_select_layout:

                showDatePickerDialog(Authentication3Activity.this, R.style.AppTheme, mCalendar, 1);
                break;
            case R.id.register_validity_select_layout:
                if (registerDate.getText() != null && !registerDate.getText().equals("")) {
                    showDatePickerDialog(Authentication3Activity.this, R.style.AppTheme, mCalendar, 2);
                } else {
                    Toast.makeText(Authentication3Activity.this, getString(R.string.register_data_not_select_tip), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next_step:
                mList.clear();
                doctorImagSave();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mIdCard = intent.getStringExtra("idCardNumber");
        mFirstPicturePath = intent.getStringExtra("firstPicturePath");
        mSecondPicturePath = intent.getStringExtra("secondPicturePath");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void showDatePickerDialog(Activity activity, int themeResId, Calendar calendar, final int action) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                String month;
                if (monthOfYear + 1 < 10) {
                    month = "0"+(monthOfYear + 1);
                }
                else {
                    month = String.valueOf(monthOfYear + 1);
                }
                String data = year + "-" + month + "-" + dayOfMonth;

                //必须捕获异常
                try {
                    Date date = simpleDateFormat.parse(data);

                        if (action == 1) {
                            mRegisterData = date;
                            registerDate.setText(data);
                            registerValidity.setText("");
                        } else if (action == 2) {
                            if (mRegisterData.getTime() > date.getTime()) {
                                registerValidity.setText("");
                                Toast.makeText(Authentication3Activity.this, getString(R.string.register_validity_not_invalid), Toast.LENGTH_SHORT).show();
                            } else {
                                registerValidity.setText(data);
                            }
                        }
                    System.out.println(date);
                } catch (ParseException px) {
                    Log.e("zdp", "px=" + px);
                    px.printStackTrace();
                }

            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("zdp", "aaa");
        if (resultCode == RESULT_OK) {
            Log.i("zdp", "requestCode=" + requestCode);
            switch (requestCode) {
                case 1:
                    Log.i("zdp", "path=" + mSelectedPictureDialog.getPicturePath());
                    uploadPicture(mSelectedPictureDialog.getPicturePath(),3);
                    Glide.with(MyApplication.getContext())
                            .load(mSelectedPictureDialog.getPicturePath())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(licensedFirstImage);

                    break;
                case 2:
                    uploadPicture(mSelectedPictureDialog.getPicturePath(),4);
                    Glide.with(MyApplication.getContext())
                            .load(mSelectedPictureDialog.getPicturePath())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(licensedSecondImage);
                    break;
                case 3:
                    Log.i("zdp", "fdsf");
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        uploadPicture(path,3);
                        Glide.with(MyApplication.getContext())
                                .load(path)
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(licensedFirstImage);
                        cursor.close();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        uploadPicture(path,4);
                        Log.i("zdp", "path=" + path);
                        Glide.with(this)
                                .load(path)
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(licensedSecondImage);
                        cursor.close();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPicture(String path, final int action) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.DoctorImagUpload).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                       if (action == 3) {
                           mThridPicturePath = entituy.getData().getSavePath();
                       }
                       else if  (action == 4) {
                           mFourthPicturePath = entituy.getData().getSavePath();
                       }
                       /* DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        if (imageType>3) {
                            if (!practiceLicense.getText().toString().equals("")) {
                                doctorRegImgEntity.setNumber(practiceLicense.getText().toString());
                            }
                            if (registerDate.getText().toString().equals("")) {
                                doctorRegImgEntity.setRegisterTime(registerDate.getText().toString());
                            }
                            if(registerValidity.getText().toString().equals("")) {
                                doctorRegImgEntity.setValidTime(registerValidity.getText().toString());
                            }
                        }
                        doctorRegImgEntity.setImgPath(entituy.getData().getSavePath());
                        doctorRegImgEntity.setImgType(imageType);
                        mList.add(doctorRegImgEntity);*/
                            Log.i("zdp", "statusCode=" + statusCode);
                        Log.i("zdp", "data=" + entituy.getData());
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        Log.i("zdp", "error=" + statusCode + ",errormsg=" + errorMsg);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }
    private void doctorImagSave() {
        DoctorRegImgEntity doctorRegImgEntity1 = new DoctorRegImgEntity();
        doctorRegImgEntity1.setImgPath(mFirstPicturePath);
        doctorRegImgEntity1.setImgType(1);
        mList.add(doctorRegImgEntity1);
        DoctorRegImgEntity doctorRegImgEntity2 = new DoctorRegImgEntity();
        doctorRegImgEntity2.setImgPath(mSecondPicturePath);
        doctorRegImgEntity2.setImgType(3);
        mList.add(doctorRegImgEntity2);
        DoctorRegImgEntity doctorRegImgEntity3 = new DoctorRegImgEntity();
        DoctorRegImgEntity doctorRegImgEntity4 = new DoctorRegImgEntity();
        if (!practiceLicense.getText().toString().equals("")) {
            doctorRegImgEntity3.setNumber(practiceLicense.getText().toString());
            doctorRegImgEntity4.setNumber(practiceLicense.getText().toString());
        }
        if (!registerDate.getText().toString().equals("")) {
            doctorRegImgEntity3.setRegisterTime(registerDate.getText().toString());
            doctorRegImgEntity4.setRegisterTime(registerDate.getText().toString());
        }
        if(!registerValidity.getText().toString().equals("")) {
            doctorRegImgEntity3.setValidTime(registerValidity.getText().toString());
            doctorRegImgEntity4.setValidTime(registerValidity.getText().toString());
        }
        doctorRegImgEntity3.setImgPath(mThridPicturePath);
        doctorRegImgEntity3.setImgType(4);
        doctorRegImgEntity4.setImgPath(mFourthPicturePath);
        doctorRegImgEntity4.setImgType(4);
        mList.add(doctorRegImgEntity3);
        mList.add(doctorRegImgEntity4);
        DoctorImagSaveRequestEntity doctorImagSaveRequestEntity = new DoctorImagSaveRequestEntity();
        doctorImagSaveRequestEntity.setDrId( SpUtils.getInt(Contants.Id,0));
        doctorImagSaveRequestEntity.setDrName(mName);
        doctorImagSaveRequestEntity.setCredNo(mIdCard);
        doctorImagSaveRequestEntity.setDoctorRegImgs(mList);
        String json = mGson.toJson(doctorImagSaveRequestEntity);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.i("zdp","json="+json);
        mMyOkhttp.post().url(HttpUrl.DoctorImagSave).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                Log.i("bb","code="+entity.getCode()+",message="+entity.getMessage());
               if (entity.getCode() == 0) {
                   Intent intent = new Intent(Authentication3Activity.this,RegisterSuccessActivity.class);
                   startActivity(intent);
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


}

