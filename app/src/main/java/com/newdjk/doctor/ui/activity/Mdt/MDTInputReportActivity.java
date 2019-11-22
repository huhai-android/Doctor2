package com.newdjk.doctor.ui.activity.Mdt;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.AudioToTextEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MySaveAdviceEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.FileSizeUtil;
import com.newdjk.doctor.utils.FileToBase64;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TokenInterceptor;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
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
 *  @文件名:   MDTActivity
 *  @创建者:   huhai
 *  @创建时间:  2019/8/29 10:26
 *  @描述：
 */
public class MDTInputReportActivity extends BasicActivity {

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
    @BindView(R.id.civImg)
    CircleImageView civImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.lv_parent)
    LinearLayout lvParent;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.im_record)
    LinearLayout imRecord;
    @BindView(R.id.tv_disease_desc)
    TextView tvDiseaseDesc;
    @BindView(R.id.btn_send_report)
    AppCompatButton btnSendReport;
    @BindView(R.id.tv_patient_history)
    TextView tvPatientHistory;
    @BindView(R.id.tv_expert_desc)
    TextView tvExpertDesc;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.rv_input_advice)
    RelativeLayout rvInputAdvice;
    private CountDownTimer mTimer;
    private int currentPosition = 0;

    private String TAG = "MDTActivity";
    public Gson mGson = new Gson();
    private PopupWindow mRecordWindow;
    private int currentTime = 60;
    private Recorder audioRecord;
    private String mSoundPath;
    private MDTDetailEntity mMDTDetailEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_input_mdtreport;
    }

    @Override
    protected void initView() {
        initTitle("填写专家意见").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.theme));

        mMDTDetailEntity = (MDTDetailEntity) getIntent().getSerializableExtra("MDTDetailEntity");

        tvName.setText(mMDTDetailEntity.getPatientName() + "");
        tvAge.setText(mMDTDetailEntity.getPatientAge());
        if (mMDTDetailEntity.getPatientSex() == 1) {
            tvSex.setText("男");
        } else if (mMDTDetailEntity.getPatientSex() == 2) {
            tvSex.setText("女");
        } else {
            tvSex.setText("未知");
        }

        GlideUtils.loadPatientImage(mMDTDetailEntity.getPatPicturePath(),civImg);

        tvDiseaseDesc.setText(mMDTDetailEntity.getDescription() + "");
        tvExpertDesc.setText("" + (TextUtils.isEmpty(mMDTDetailEntity.getAdvice()) ? "暂无内容" : mMDTDetailEntity.getAdvice()));

        String diseaseName = mMDTDetailEntity.getDiseases();
        String diseaseTime = mMDTDetailEntity.getDiseasesTimeText();
        String seeDoctorText = mMDTDetailEntity.getSeeDoctorText();
        String hospital = mMDTDetailEntity.getSeeDoctor();
        String department = mMDTDetailEntity.getSeeDepartment();
        if (TextUtils.isEmpty(diseaseTime)) {
            diseaseTime = "";
        } else {
            diseaseTime = diseaseTime + ",";
        }

        if (TextUtils.isEmpty(diseaseName)) {
            diseaseName = "";
        } else {
            diseaseName = diseaseName + ",";
        }

        if (TextUtils.isEmpty(seeDoctorText)) {
            seeDoctorText = "";
        } else {
            seeDoctorText = seeDoctorText + ",";
        }

        if (TextUtils.isEmpty(hospital)) {
            hospital = "";
        } else {
            hospital = hospital + ",";
        }
        if (TextUtils.isEmpty(department)) {
            department = "";
        }
        //组合文字
        tvPatientHistory.setText(diseaseName + diseaseTime + seeDoctorText + hospital + department);

    }


    @Override
    protected void initListener() {
        imRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Log.d(TAG, "开始录音");

                    showPopUpWindow();
                    startTimer();
                    //开始录音
                    startRecode();

                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    hidePopUpWindow();
                    stopRecordAndFile();

                }
                return true;
            }
        });

        btnSendReport.setOnClickListener(this);

    }


    @Override
    protected void initData() {
        QueryManySubjectDoctor();
    }

    private void QueryManySubjectDoctor() {
        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.QueryManySubjectDoctor).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MySaveAdviceEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MySaveAdviceEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (entity.getData()!=null){
                        if (!TextUtils.isEmpty(entity.getData().getAdvice())){
                            etContent.setText(entity.getData().getAdvice());
                        }
                    }

                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });
    }


    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_right:

                toast("预览");
                break;

            case R.id.btn_send_report:
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    toast("专家意见不能为空");
                } else {
                    sendReport(1);
                }

                break;


        }
    }

    private void sendReport(final int submit) {

        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mMDTDetailEntity.getSubjectBuyRecordId() + "");
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        map.put("SubjectType", mMDTDetailEntity.getSubjectType() + "");
        map.put("Advice", etContent.getText().toString() + "");
        map.put("IsSubmit", submit + "");
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SendDoctorAdvice).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    if (submit == 0) {
                        toast("暂存报告成功");
                    } else {
                        toast("发送报告成功");
                        MyApplication.exit();
                    }
                    finish();
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
                LoadDialog.clear();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    //---------------------定时器---------------//
    private void startTimer() {
        if (mTimer == null) {
            inittimer();
        }
        mTimer.start();
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
                    stopRecordAndFile();
                }
            };
        }
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
        ImageView recordButton = new ImageView(MDTInputReportActivity.this);
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

    public void stopRecordAndFile() {
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
                    getTextByAudio();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getTextByAudio() {
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
            Log.d(TAG, jsonStr);

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

                final AudioToTextEntity audioToTextEntity = mGson.fromJson(responseStr, AudioToTextEntity.class);
                if (audioToTextEntity.getRtn() == 0) {
                    if (TextUtils.isEmpty(audioToTextEntity.getResultText())) {
                        toast("无法识别语音");
                        loading(false);
                        return;
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "更新数据" + audioToTextEntity);
                            //  mMyDiagnoseAdapter.notifyItemChanged(currentPosition,data);
                            String advice = etContent.getText().toString();
                            if (TextUtils.isEmpty(advice)) {
                                etContent.setText(audioToTextEntity.getResultText() + "");

                            } else {
                                etContent.setText(advice + audioToTextEntity.getResultText() + "");
                            }
                            //光标定位到最后面
                            etContent.setSelection(etContent.getText().toString().length());
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

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static String calculateRFC2104HMAC(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }


    //---------------------popwindow---------------//
    private void showPopUpWindow() {
        View view = View.inflate(MDTInputReportActivity.this, R.layout.popup_audio_recode, null);
        ImageView mStateIV = view.findViewById(R.id.rc_audio_state_image);
        TextView mTimerTV = view.findViewById(R.id.rc_audio_timer);
        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        mStateIV.setBackgroundResource(R.drawable.audio_play_animator);
        AnimationDrawable animationDrawable = (AnimationDrawable) mStateIV.getBackground();
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(lvParent, 17, 0, 0);
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

    public boolean isJsonMime(String mime) {
        String jsonMime = "(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$";
        return mime != null && (mime.matches(jsonMime) || mime.equals("*/*"));
    }


    //---------------------popwindow---------------//


}

