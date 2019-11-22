package com.newdjk.doctor.ui.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.DocQualificationEntity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.IDCard;
import com.newdjk.doctor.utils.IdcardRecognise;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.SelectedPictureDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注册认证第二步
 */
public class Authentication2Activity extends BasicActivity {

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
    @BindView(R.id.id_card_image)
    ImageView idCardImage;
    @BindView(R.id.hand_id_card_image)
    ImageView handIdCardImage;
    @BindView(R.id.name_tip)
    TextView nameTip;
    @BindView(R.id.id_card)
    TextView idCard;
    @BindView(R.id.next_step)
    TextView nextStep;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.id_card_number)
    EditText idCardNumber;
    @BindView(R.id.tv_positive)
    TextView tvPositive;
    @BindView(R.id.tv_other_side)
    TextView tvOtherSide;
    @BindView(R.id.rl_background1)
    RelativeLayout rlBg1;
    @BindView(R.id.rl_background2)
    RelativeLayout rlBg2;
    private SelectedPictureDialog mSelectedPictureDialog;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private String mIdNumber;
    private String[] mIdCardUrl = new String[2];
    private String mIdNo;
    private String mJobname;
    private String mNumber = "";

    public static Intent getAct(Context context) {
        return new Intent(context, Authentication2Activity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_authentication2;
    }

    @Override
    protected void initView() {
        idCardImage.setOnClickListener(this);
        nextStep.setOnClickListener(this);
        handIdCardImage.setOnClickListener(this);
        name.setText(SpUtils.getString(Contants.Name));
        initBackTitle("资质认证").setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResult();
            }
        });

        mJobname = getIntent().getStringExtra(Contants.JobName);
    }

    @Override
    protected void initListener() {
        idCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mIdNumber = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 18) {
                    idCardNumber.setText(mIdNumber);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {
        name.setText(SpUtils.getString(Contants.Name));
        getIdCardImage();
    }

    /**
     * 获取上次信息展示
     */
    private void getIdCardImage() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        loading(true);
        mMyOkhttp.get().url(HttpUrl.QueryDoctorRegImagByDrId).headers(headMap).params(params).tag(this).enqueue(new GsonResponseHandler<DocQualificationEntity>() {
            @Override
            public void onSuccess(int statusCode, DocQualificationEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    List<DocQualificationEntity.DataBean> list = response.getData();
                    int len = response.getData().size();
                    for (int i = 0; i < len; i++) {
                        DocQualificationEntity.DataBean bean = list.get(i);
                        if (bean.getImgType() == 1) {//身份证正面
                            mIdCardUrl[0] = bean.getImgPath();
                            Glide.with(MyApplication.getContext())
                                    .load(mIdCardUrl[0])
                                    .into(idCardImage);
                            mIdNo = bean.getNumber();
                            idCardNumber.setText(mIdNo);
                            clearBg(tvPositive, rlBg1);
                        } else if (bean.getImgType() == 2) {//身份证反面
                            mIdCardUrl[1] = bean.getImgPath();
                            Glide.with(MyApplication.getContext())
                                    .load(mIdCardUrl[1])
                                    .into(handIdCardImage);
                            clearBg(tvOtherSide, rlBg2);
                        }
                    }


                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }


        });


    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.id_card_image:
                mSelectedPictureDialog = new SelectedPictureDialog(Authentication2Activity.this, "first");
                mSelectedPictureDialog.show();
                break;
            case R.id.hand_id_card_image:
                mSelectedPictureDialog = new SelectedPictureDialog(Authentication2Activity.this, "second");
                mSelectedPictureDialog.show();
                break;
            case R.id.next_step:

                if (mIdCardUrl.length == 2 && !name.getText().toString().equals("") && !idCardNumber.getText().toString().equals("")) {
                    if (TextUtils.isEmpty(mIdCardUrl[0])) {
                        toast("请上传身份证正面照");
                        return;
                    }

                    if (TextUtils.isEmpty(mIdCardUrl[1])) {
                        toast("请上传身份证反面照");
                        return;
                    }

                    if (idCardNumber.getText().toString().length() != 18) {
                        toast("请输入正确的身份证号码");
                        return;
                    }

                    if (!IDCard.validate_effective(idCardNumber.getText().toString())) {
                        toast("请输入正确的身份证号码");
                        return;
                    }
                    Intent intent = new Intent(Authentication2Activity.this, Authentication3ActivityNew.class);
                    intent.putExtra("firstPicturePath", mIdCardUrl[0]);
                    intent.putExtra("secondPicturePath", mIdCardUrl[1]);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("idCardNumber", idCardNumber.getText().toString());
                    intent.putExtra(Contants.JobName, mJobname);
                    startActivity(intent);
                } else {
                    toast("请将资料补充完整");
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("zdp", "aaa");
        if (resultCode == RESULT_OK) {
            Log.i("zdp", "requestCode=" + requestCode);
            switch (requestCode) {
                case 1:
                    Log.i("zdp", "path=" + mSelectedPictureDialog.getPicturePath());
                    //  String image64 = ImageBase64.encodeImage(mSelectedPictureDialog.getPicturePath());
                    if (mSelectedPictureDialog != null) {
                        uploadPicture(mSelectedPictureDialog.getPicturePath(), 1);
                        identityCardRecognition(mSelectedPictureDialog.getPicturePath());
                    }
                    break;
                case 2:
                    if (mSelectedPictureDialog != null) {
                        uploadPicture(mSelectedPictureDialog.getPicturePath(), 2);
                    }

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

                        uploadPicture(path, 1);
                        identityCardRecognition(path);
//                        Glide.with(MyApplication.getContext())
//                                .load(path)
//                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(idCardImage);
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

                        uploadPicture(path, 2);
                        Log.i("zdp", "path=" + path);
//                        Glide.with(this)
//                                .load(path)
//                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(handIdCardImage);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }

    private Context getContext() {
        return this;
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(Authentication2Activity.this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    /*  private void uploadPicture(String path) {
          new AsyncTask<String, Integer, String>() {
              @Override
              protected String doInBackground(String... strings) {
                  String image64 = ImageBase64.bitmapToString(strings[0]);
                  Log.i("zdp", "size=" + image64.length());
                  return image64;
              }

              @Override
              protected void onPostExecute(String s) {
                  Map<String, String> map = new HashMap<>();
                  map.put("Base64Str", s);
                  mMyOkhttp.post().url(HttpUrl.DoctorImagUpload).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                      @Override
                      public void onSuccess(int statusCode, Entity entituy) {
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

      }*/
    private void uploadPicture(final String path, final int action) {
        loading(true, "身份证照片上传中");
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToStringfromAuth(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.DoctorImagUpload).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        loading(false);
                        if (entituy.getCode() == 0) {
                            if (action == 1) {
                                Glide.with(MyApplication.getContext())
                                        .load(path)
                                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(idCardImage);
                                clearBg(tvPositive, rlBg1);
                                mIdCardUrl[0] = entituy.getData().getSavePath();
                            } else if (action == 2) {
                                mIdCardUrl[1] = entituy.getData().getSavePath();
                                Glide.with(MyApplication.getContext())
                                        .load(path)
                                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(handIdCardImage);
                                clearBg(tvOtherSide, rlBg2);
                            }
                        } else {
                            toast("上传图片失败，请检查网络！");
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
                        loading(false);
                        toast("上传图片失败，请检查网络！");
                    }
                });
            }
        }.execute(path);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            activityResult();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void activityResult() {
        Intent intent = this.getIntent();
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    private void clearBg(TextView tv, RelativeLayout rl) {
        tv.setText("");
        rl.setBackgroundColor(getResources().getColor(R.color.activity_bg));
    }


    //身份证识别接口
    private void identityCardRecognition(final String picturePath) {
        // loading(true, "身份证号码识别中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    result = IdcardRecognise.getIDNumber(picturePath);
                    JSONObject jsonObject = new JSONObject(result);
                    //最外层是对象，创建对象
                    JSONObject data = jsonObject.getJSONObject("words_result");
                    //data对象中还是对象（carArchive），再次创建对象
                    JSONObject carArchive = data.getJSONObject("公民身份号码");
                    //data对象中还是对象（usedCar），再次创建对象
                    mNumber = carArchive.getString("words");


//                    ID id = GsonUtils.fromJson(result, ID.class);
//                    mNumber = id.getWords_result().get公民身份号码().getWords();
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(2);
                }

            }
        }).start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!TextUtils.isEmpty(mNumber + "")) {
                        idCardNumber.setText(mNumber + "");
                        idCardNumber.setSelection(mNumber.length());
                    }

                    loading(false);
                    break;
                case 2:
                    loading(false);
                    break;
                default:
                    break;
            }
        }

    };
}
