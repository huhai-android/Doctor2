package com.newdjk.doctor.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.ToastUtil;

import java.util.Locale;


public class CameraActivity extends Activity {

    private static final String TAG = CameraActivity.class.getSimpleName();

    private JCameraView jCameraView;
    public static IUIKitCallBack mCallBack;
    private int REQUEST_CODE_PICK = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        jCameraView = findViewById(R.id.jcameraview);
        //设置视频保存路径
        //jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        //  FileUtil.initPath(); // 取决于app什么时候获取到权限，即使在application中初始化，首次安装时，存在获取不到权限，建议app端在activity中再初始化一次，确保文件目录完整创建

        int state = getIntent().getIntExtra(TUIKitConstants.CAMERA_TYPE, JCameraView.BUTTON_STATE_BOTH);
        jCameraView.setFeatures(state);
        if (state == JCameraView.BUTTON_STATE_ONLY_CAPTURE) {
            jCameraView.setTip("点击拍照");
        } else if (state == JCameraView.BUTTON_STATE_ONLY_RECORDER) {
            jCameraView.setTip("长按摄像");
        }


        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i(TAG, "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                ToastUtil.setToast("给点录音权限可以?");
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                String path = FileUtil.saveBitmap("JCamera", bitmap);
               /* Intent intent = new Intent();
                intent.putExtra(ILiveConstants.CAMERA_IMAGE_PATH, path);
                setResult(-1, intent);*/
                if (mCallBack != null) {
                    mCallBack.onSuccess(path);
                }
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame, long duration) {
                //获取视频路径
                String path = FileUtil.saveBitmap("JCamera", firstFrame);
                Intent intent = new Intent();
                intent.putExtra(TUIKitConstants.IMAGE_WIDTH, firstFrame.getWidth());
                intent.putExtra(TUIKitConstants.IMAGE_HEIGHT, firstFrame.getHeight());
                intent.putExtra(TUIKitConstants.VIDEO_TIME, duration);
                intent.putExtra(TUIKitConstants.CAMERA_IMAGE_PATH, path);
                intent.putExtra(TUIKitConstants.CAMERA_VIDEO_PATH, url);
                firstFrame.getWidth();
                //setResult(-1, intent);
                if (mCallBack != null) {
                    mCallBack.onSuccess(intent);
                }
                finish();
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                //
                String vendor = Build.MANUFACTURER;

                if (vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
                    startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), REQUEST_CODE_PICK);
                } else {//直接跳到系统相册去选取视频
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT < 19) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("video/*");
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("video/*");
                    }
                    startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), REQUEST_CODE_PICK);
                }

            }
        });
        //jCameraView.setVisibility(View.GONE);
        Log.i(TAG, DeviceUtil.getDeviceModel());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK && resultCode == RESULT_OK) {//从相册选取视频
            String videoPath = GetPathFromUri.getPath(this, data.getData());
           // ToastUtil.setToast("选择视频" + videoPath);
            //获取搜略图
            Bitmap firstFrame = FileUtil.getVideoThumb(videoPath);

            String path = FileUtil.saveBitmap("JCamera", firstFrame);
            Intent intent = new Intent();
            intent.putExtra(TUIKitConstants.IMAGE_WIDTH, firstFrame.getWidth());
            intent.putExtra(TUIKitConstants.IMAGE_HEIGHT, firstFrame.getHeight());
            intent.putExtra(TUIKitConstants.VIDEO_TIME, FileUtil.getLocalVideoDuration(videoPath));
            intent.putExtra(TUIKitConstants.CAMERA_IMAGE_PATH, path);
            intent.putExtra(TUIKitConstants.CAMERA_VIDEO_PATH, videoPath);
            firstFrame.getWidth();
            //setResult(-1, intent);
            if (mCallBack != null) {
                mCallBack.onSuccess(intent);
            }
            finish();

        }
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }
}
