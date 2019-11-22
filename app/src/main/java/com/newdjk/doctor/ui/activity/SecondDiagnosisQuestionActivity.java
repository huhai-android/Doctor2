package com.newdjk.doctor.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.lxq.okhttp.utils.ReceivedCookiesInterceptor;
import com.newdjk.doctor.Audio.JSON;
import com.newdjk.doctor.Audio.api.ClientException;
import com.newdjk.doctor.Audio.model.ShortSpeechRequest;
import com.newdjk.doctor.Audio.requests.RequestsFactory;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.tools.MainConstant;
import com.newdjk.doctor.ui.adapter.MyDiagnoseAdapter;
import com.newdjk.doctor.ui.entity.AudioToTextEntity;
import com.newdjk.doctor.ui.entity.DoctorSendReportResponseEntity;
import com.newdjk.doctor.ui.entity.MedicalRecordByIdEntity;
import com.newdjk.doctor.ui.entity.MessageEvent;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.FileSizeUtil;
import com.newdjk.doctor.utils.FileToBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TokenInterceptor;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MedicalView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.values.EnvType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.activity
 *  @文件名:   SecondDiagnosisQuestionActivity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/17 15:34
 *  @描述：
 */
public class SecondDiagnosisQuestionActivity extends BasicActivity {


    private static final String TAG = "QuestionActivity";

    private static final int REQUEST_CODE_SELECT_FILE = 0;
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
    RecyclerView recyleview;
    @BindView(R.id.fr_root)
    FrameLayout frRoot;
    @BindView(R.id.lv_root)
    LinearLayout lvRoot;


    private Recorder audioRecord;
    private String mSoundPath;
    private PopupWindow mRecordWindow;
    private CountDownTimer mTimer;
    private Gson mGson;
    private List<MedicalRecordByIdEntity.MainProblemsBean> mList;
    private MyDiagnoseAdapter mMyDiagnoseAdapter;
    private int currentTime = 60;
    private int currentPosition = 0;
    private int mMedicalRecordId;
    private TextView patientName;
    private TextView age;
    private TextView sex;
    private TextView medicalRecordDate;
    private TextView mDiagnoseDesc;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private MedicalRecordByIdEntity mMedicalRecordByIdEntity;
    private String mMedicalReportPath;
    private MedicalView mMedicalView;
    private long currentTime1;
    private long mLastTime;

    @Override
    protected int initViewResId() {
        return R.layout.activity_diagnosis;
    }

    /* @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         EventBus.getDefault().register(this);
     }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        MyApplication.mActivities.add(this);
        if (BuildConfig.DEBUG) {
            BJCASDK.getInstance().setServerUrl(EnvType.INTEGRATE);
        } else {
            BJCASDK.getInstance().setServerUrl(EnvType.PUBLIC);
        }
        try {
            checkPermissionsIfCanContinue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMedicalRecordId = getIntent().getIntExtra("MedicalRecordId", 0);
        initBackTitle(getString(R.string.my_diagnose)).setRightText("补充材料");
        mList = new ArrayList<>();
        creatAudioRecord();
        inittimer();
        mMedicalView = new MedicalView(this, mMedicalRecordId);
        mMedicalView.show();
        mGson = new Gson();
     /*   for (int i = 0; i < 10; i++) {
            DiagnoseEntity diagnoseEntity = new DiagnoseEntity();
            diagnoseEntity.setNumber("Q" + (i + 1));
            diagnoseEntity.setQuestion("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            mList.add(diagnoseEntity);
        }*/

        mMyDiagnoseAdapter = new MyDiagnoseAdapter(mList);
        recyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyleview.setAdapter(mMyDiagnoseAdapter);
        recyleview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mMyDiagnoseAdapter.addHeaderView(getHeaderView());
        mMyDiagnoseAdapter.addFooterView(getButtonView());
        mMyDiagnoseAdapter.setOnAudioListener(new MyDiagnoseAdapter.onRecodeAudioListener() {
            @Override
            public void startRecodAudio(int position) {
                // Log.d(TAG, "开始录音");
                currentPosition = position;
                showPopUpWindow();
                startTimer();
                //开始录音
                startRecode();

            }

            @Override
            public void stopRecodAudio(int position) {
                //   Log.d(TAG, "停止录音");
                currentPosition = position;
                hidePopUpWindow();
                stopRecordAndFile(position);

            }
        });
    }

    @Override
    protected void initListener() {
        tvRight.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        GetMedicalRecordById();
    }

    private View getButtonView() {
        View view = View.inflate(this, R.layout.diagnose_buttomview, null);
        TextView tvSendReport = view.findViewById(R.id.tv_send_report);
        tvSendReport.setOnClickListener(this);
        return view;
    }

    private View getHeaderView() {
        View view = View.inflate(this, R.layout.diagnose_headerview, null);
        patientName = view.findViewById(R.id.patient_name);
        sex = view.findViewById(R.id.sex);
        age = view.findViewById(R.id.age);
        medicalRecordDate = view.findViewById(R.id.medical_record_date);
        mDiagnoseDesc = view.findViewById(R.id.tv_diagnosis__desc);
        return view;
    }


    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

            case R.id.tv_right:
                Intent systemSettingIntent2 = new Intent(SecondDiagnosisQuestionActivity.this, AddDocumentActivity.class);
                systemSettingIntent2.putExtra("MedicalRecordId", mMedicalRecordId);
                startActivity(systemSettingIntent2);
                break;

            case R.id.tv_send_report:
                mLastTime = currentTime1;
                currentTime1 = System.currentTimeMillis();
                if (currentTime1 - mLastTime > 2000) {
                    Log.i("SecondDiagnosis", "mLastTime=" + mLastTime + ",currentTime1=" + currentTime1);
                    for (int i = 0; i < mList.size(); i++) {
                        String answer = mList.get(i).getAnswer();
                        if (answer == null || answer.equals("")) {
                            if (i==mList.size()-1){
                                toast("请填写专家意见！");
                                return;
                            }
                            toast("请回答第"+(i+1)+"个问题！");
                            return;
                        }

                    }
                    if (mList.size() > 0) {
                        String advice = mList.get(mList.size() - 1).getAnswer();
                        DoctorSendReport(advice);
                    }
                }
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SecondDiagnosisQuestionActivity.class);
        return intent;
    }


    //---------------------录音相关--------------//
    private void creatAudioRecord() {
        audioRecord = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override
                    public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());

    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 16000
                )
        );
    }

    private void animateVoice(final float maxPeak) {
        ImageView recordButton = new ImageView(SecondDiagnosisQuestionActivity.this);
        recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    private void startRecode() {
        if (audioRecord == null) {
            creatAudioRecord();
        }
        audioRecord.startRecording();
    }

    @NonNull
    private File file() {
        File file = new File(Environment.getExternalStorageDirectory(), "aaaa.wav");
        mSoundPath = file.getPath();
        return file;
    }


    @NonNull
    private File PDFfile() {
        File file = new File(Environment.getExternalStorageDirectory(), "report.pdf");
        return file;
    }


    public void stopRecordAndFile(int position) {
        try {
            if (audioRecord != null) {
                audioRecord.stopRecording();
                audioRecord = null;
                mTimer.cancel();
                if (currentTime > 58) {
                    toast("录音时间过短");
                } else {
                    Log.d(TAG, "录音文件大小" + FileSizeUtil.getFileOrFilesSize(mSoundPath, 2));
                    if (FileSizeUtil.getFileOrFilesSize(mSoundPath, 2) >= 2048) {
                        toast("录音文件超过2M，请重新录制");
                        return;
                    }
                    getTextByAudio(position);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //---------------------录音相关--------------//


    //---------------------语音转换--------------//

    public void getTextByAudio(final int position) {
        loading(true, "正在转换...");
        //请求头          String api_params_base64 = "eydlbmdpbmVfdHlwZSc6ICdzbXMxNmsnLCAnYXVlJzoncGNtJywgJ3NjZW5lJzonbWFpbicsICdmbGFncyc6IDB9";
        // String devKey = "a06448b8a69c40e7ae81b057a950ece6";
        String devKey = "64c1dad5e9774e44b03fe8b2f343e83d";
        String engine_type = "sms16k";
        String aue = "pcm";
        String scene = "main";
        String xDevId = "3037";
        String xSignature = "";
        String api_params = "{\"engine_type\": \"" + engine_type + "\", \"aue\":\"" + aue + "\", \"scene\":\"" + scene + "\", \"flags\": 0}";
        Long ts = System.currentTimeMillis() / 1000;
        String signKey = xDevId + ts;
        String api_params_base64 = FileToBase64.getBase64(api_params);
        String param_string = devKey + ts + api_params_base64;
        ShortSpeechRequest shortSpeechRequest;
        RequestBody requestBody = null;


        String param_checksum = FileToBase64.encode(param_string);
        //自定义OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new ReceivedCookiesInterceptor())//你定义的cookie接收监听器
                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new StethoInterceptor()) //添加抓包工具
                .build();
        //构建FormBody，传入要提交的参数


        try {
            RequestsFactory requestsFactory = new RequestsFactory();
            ShortSpeechRequest.AueEnum aue2 = ShortSpeechRequest.AueEnum.PCM;
            shortSpeechRequest = requestsFactory.generateShortSpeechRequestWithFile(file().getAbsolutePath(), aue2, null);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(shortSpeechRequest);
            Log.d(TAG,jsonStr);

            requestBody = serialize(shortSpeechRequest, "application/json");
        } catch (ClientException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        String postBody = "{\"audio\": \"" + FileToBase64.getBase64(file()) + "\"}";
        try {
            //  xSignature = AuthenticationUtils.computeSignatureHmacSha256(xDevId + ts.toString(), devKey);
            xSignature = calculateRFC2104HMAC(signKey, devKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  Log.d(TAG, "devKey=" + devKey + "  ts=" + ts + "   api_params_base64=" + api_params_base64 + "  param_checksum=" + param_checksum);

        //RequestBody body = FormBody.create(MediaType.parse("application/json"), postBody);
        final Request request = new Request.Builder()
                .url(HttpUrl.getTextByAudio)
                .post(requestBody)// post json提
                .addHeader("x-dev-id", xDevId)
                .addHeader("x-request-send-timestamp", ts + "")
                .addHeader("x-signature", xSignature)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loading(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

                AudioToTextEntity audioToTextEntity = mGson.fromJson(responseStr, AudioToTextEntity.class);
                if (audioToTextEntity.getRtn() == 0) {
                    final MedicalRecordByIdEntity.MainProblemsBean data = mList.get(currentPosition - 1);
                    if (TextUtils.isEmpty(audioToTextEntity.getResultText())) {
                        toast("无法识别语音");
                        loading(false);
                        return;
                    }
                    if (!TextUtils.isEmpty(data.getAnswer())){
                        data.setAnswer(data.getAnswer() + audioToTextEntity.getResultText());
                    }else {
                        data.setAnswer( audioToTextEntity.getResultText());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //    Log.d(TAG, "更新数据" + (currentPosition - 1) + " " + data.toString());
                            //  mMyDiagnoseAdapter.notifyItemChanged(currentPosition,data);
                            mMyDiagnoseAdapter.setData(currentPosition - 1, data);
                            //  recyleview.scrollToPosition(currentPosition);
                        }
                    });
                } else {
                    toast("转换失败，请重新录入");
                }
                loading(false);
            }
        });


    }
    //---------------------语音转换--------------//

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static String calculateRFC2104HMAC(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }


    public RequestBody serialize(Object obj, String contentType) {
        if (obj instanceof byte[]) {
            // Binary (byte array) body parameter support.
            return RequestBody.create(MediaType.parse(contentType), (byte[]) obj);
        } else if (obj instanceof File) {
            // File body parameter support.
            return RequestBody.create(MediaType.parse(contentType), (File) obj);
        } else if (isJsonMime(contentType)) {
            String content;
            if (obj != null) {
                JSON json = new JSON();

                content = json.serialize(obj);
            } else {
                content = null;
            }
            return RequestBody.create(MediaType.parse(contentType), content);
        } else {
            try {
                throw new Exception("Content type \"" + contentType + "\" is not supported");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isJsonMime(String mime) {
        String jsonMime = "(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$";
        return mime != null && (mime.matches(jsonMime) || mime.equals("*/*"));
    }

    //---------------------popwindow---------------//
    private void showPopUpWindow() {
        View view = View.inflate(SecondDiagnosisQuestionActivity.this, R.layout.popup_audio_recode, null);
        ImageView mStateIV = view.findViewById(R.id.rc_audio_state_image);
        TextView mTimerTV = view.findViewById(R.id.rc_audio_timer);
        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        mStateIV.setBackgroundResource(R.drawable.audio_play_animator);
        AnimationDrawable animationDrawable = (AnimationDrawable) mStateIV.getBackground();
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(recyleview, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    private void hidePopUpWindow() {
        if (mRecordWindow != null) {
            mRecordWindow.dismiss();
            mRecordWindow = null;
        }
    }
    //---------------------popwindow---------------//


    //---------------------定时器---------------//
    private void startTimer() {
        if (mTimer == null) {
            inittimer();
        }
        mTimer.start();
    }

    private void GetMedicalRecordById() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetMedicalRecordById + "?MedicalRecordId=" + mMedicalRecordId;
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MedicalRecordByIdEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MedicalRecordByIdEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.clear();
                    mMedicalRecordByIdEntity = entity.getData();
                    mList.addAll(mMedicalRecordByIdEntity.getMainProblems());
                    MedicalRecordByIdEntity.MainProblemsBean mainProblemsBean = new MedicalRecordByIdEntity.MainProblemsBean();
                    String answer = mMedicalRecordByIdEntity.getAdvice();
                    mainProblemsBean.setProblem("专家意见");
                    mainProblemsBean.setAnswer(answer);
                    mList.add(mainProblemsBean);
                    mMyDiagnoseAdapter.setNewData(mList);
                    String patientName1 = mMedicalRecordByIdEntity.getPatientName();
                    String age1 = mMedicalRecordByIdEntity.getAge();
                    String sex1 = mMedicalRecordByIdEntity.getSex();
                    String medicalRecordDate1 = mMedicalRecordByIdEntity.getMedicalRecordDate();
                    if (patientName1 != null) {
                        patientName.setText("姓名：" + patientName1);
                    } else {
                        patientName.setText("姓名：");
                    }
                    if (age != null) {
                        age.setText("年龄：" + age1);
                    } else {
                        age.setText("年龄：");
                    }
                    if (sex != null) {
                        sex.setText("性别：" + sex1);
                    } else {
                        sex.setText("性别：");
                    }
                    medicalRecordDate.setText(medicalRecordDate1 == null ? "" : medicalRecordDate1);
                    mDiagnoseDesc.setText((TextUtils.isEmpty(mMedicalRecordByIdEntity.getDescription()) ? "" : mMedicalRecordByIdEntity.getDescription()));
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

    private void inittimer() {
        //开始定时器
        if (mTimer == null) {
            mTimer = new CountDownTimer((long) (60 * 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int remainTime = (int) (millisUntilFinished / 1000L);
                    currentTime = remainTime;
                    Log.d(TAG, "倒计时" + remainTime);
                }

                @Override
                public void onFinish() {
                    //          Log.d(TAG, "倒计时结束");
                    hidePopUpWindow();
                    stopRecordAndFile(currentPosition);
                }
            };
        }
    }

    //---------------------定时器---------------//
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        SecondDiagnosisQuestionActivity.this.finish();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                audioRecord = null;
                creatAudioRecord();
                break;


            default:
                break;
        }
    }


    boolean checkPermissionsIfCanContinue(Activity activity) throws Exception {

        LinkedList<String> llNoGrandPermission = new LinkedList<String>();
        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        boolean[] isAllows = new boolean[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            isAllows[i] = ContextCompat.checkSelfPermission(activity,
                    permissions.get(i)) == PackageManager.PERMISSION_GRANTED;
            if (!isAllows[i]) {
                llNoGrandPermission.add(permissions.get(i));
            }
        }

        boolean isAllPass = true;
        for (int i = 0; i < isAllows.length; i++) {
            if (!isAllows[i]) {
                isAllPass = false;
                break;
            }
        }

        if (isAllPass)
            return true;

        String[] noGrandPers = new String[llNoGrandPermission.size()];
        ActivityCompat
                .requestPermissions(this,
                        llNoGrandPermission.toArray(noGrandPers),
                        REQ_PERMISSION_CODE);

        for (int i = 0; i < permissions.size(); i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permissions.get(i))) {
                throw new Exception(permissions.get(i));
            }
        }
        return false;

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
        mMedicalView.destory();
    }


    private void DoctorSendReport(String answer) {
        mMedicalRecordByIdEntity.setAdvice(answer);
        mMedicalRecordByIdEntity.setIsSave(1);//1生成报告；2暂存
        String json = mGson.toJson(mMedicalRecordByIdEntity);
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.post().url(HttpUrl.DoctorSendReport).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<DoctorSendReportResponseEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<DoctorSendReportResponseEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    DoctorSendReportResponseEntity doctorSendReportResponseEntity = entity.getData();
                    mMedicalReportPath = doctorSendReportResponseEntity.getMedicalReportPath();
//                    String signId = doctorSendReportResponseEntity.getUniqueId();
//                    int result = BJCASDK.getInstance().signRecipe(SecondDiagnosisQuestionActivity.this, Contants.clientId, signId);
//
//                    if (result != ConstantParams.CALL_SUCCESS) {
//                        //调用失败，可以根据集成文档查看失败原因
//                        toast("调用失败！错误返回码为:" + result);
//                    } else {
//                        //  toast("调用成功");
//                    }
                    //先调用后台接口生成pdf文档
                    //下载pdf到缓存中
                    loading(true, "加载pdf");
                    downFile(mMedicalReportPath, PDFfile());

                    // setDownloadButtonListener(mMedicalReportPath);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getType()) {
            case MainConstant.UPDATE_MEDICAL:
                GetMedicalRecordById();

                break;
        }

    }


    public void downFile(final String path, final File file) {
        LoadDialog.show(mContext);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .get()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    Log.i("SecondDiagnosisquestion", "zzzz");
                    InputStream inputStream = response.body().byteStream();
                 /*   Log.i("ChatActivity","path1="+path+",uuid1="+uuid);
                    //将图片保存到本地存储卡中
                    File file = new File( MyApplication.getContext().getFilesDir()
                            + File.separator + uuid);*/
                    //  File file = new File(Environment.getExternalStorageDirectory(), uuid);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] temp = new byte[2048];
                    int length;
                    while ((length = inputStream.read(temp)) != -1) {
                        fileOutputStream.write(temp, 0, length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    //    notifyDataSetChanged();
                    inputStream.close();

                    Intent intent = new Intent(SecondDiagnosisQuestionActivity.this, SignDiagnosisReportActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // API>=21，launch as a new document
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET); // launch as a new document
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.fromFile(PDFfile()));
                    intent.putExtra("mMedicalRecordId", mMedicalRecordId);
                    startActivity(intent);
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    LoadDialog.clear();
                }
            }
        });
    }
}
