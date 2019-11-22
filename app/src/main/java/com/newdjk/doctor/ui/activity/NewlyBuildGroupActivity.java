package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.GroupChoosePersonData;
import com.newdjk.doctor.ui.entity.GroupsEntity;
import com.newdjk.doctor.ui.entity.PicturePathEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SendGroupMessageEntity;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.StrUtil;
import com.newdjk.doctor.views.DisplayUtil;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.MultiImageUploadView;
import com.newdjk.doctor.views.RoundImageUploadView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewlyBuildGroupActivity extends BasicActivity {


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
    @BindView(R.id.mGroupBtn)
    TextView mGroupBtn;
    @BindView(R.id.mEditContent)
    EditText mEditContent;
    @BindView(R.id.mDate)
    TextView mDate;
    @BindView(R.id.mPersonContent)
    TextView mPersonContent;
    @BindView(R.id.upload_view)
    RoundImageUploadView uploadView;

    private String ids;
    private Gson mGson;
    private static final int IMG_REQUEST_CODE = 0x010;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<String> mSavePicList = new ArrayList<>(); //上传的图片凭证的数据源
    @Override
    protected int initViewResId() {
        return R.layout.activity_newly_build_group;
    }

    @Override
    protected void initView() {
        initTitle("新建群发").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setVisibility(View.GONE);
        tvRight.setText("群发历史");
        setUploadView();
    }

    private void setUploadView() {
        uploadView.setAddHandlerImage(R.drawable.icon_upload_pic);
        uploadView.setImageSizeConfig(new MultiImageUploadView.ImageSizeConfig() {
            @Override
            public int getWidth() {
                return 200;
            }

            @Override
            public int getHeight() {
                return 200;
            }
        });
        uploadView.setMax(9);
        uploadView.setNumCol(4);
        uploadView.setImgMargin(DisplayUtil.dp2px(this, 10));
        uploadView.setCloseHandlerImage(R.drawable.ic_delete_photo);
        uploadView.setOnImageChangeListener(new MultiImageUploadView.OnImageChangeListener() {
            @Override
            public void onImageChage(List<File> imgFiles, int maxSize) {

            }
        });
        uploadView.setPadding(0, 0, 0, 0);
        uploadView.setAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectImage();
            }
        });

        uploadView.setOnDelPicListener(new MultiImageUploadView.OnDelPicListener() {
            @Override
            public void onDelPicListener(int pos) {
                mSavePicList.remove(pos);
            }
        });
    }


    private void startSelectImage() {
        BoxingConfig mulitImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG)
                .needCamera(R.drawable.ic_camera)
                .needGif()
                .withMaxCount(uploadView.getMax() - uploadView.getFiles().size());
        Boxing.of(mulitImgConfig).
                withIntent(this, MyBoxingActivity.class).
                start(this, IMG_REQUEST_CODE);
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGson = new Gson();
        Calendar now = Calendar.getInstance();
        mDate.setText((now.get(Calendar.MONTH) + 1 + "月" + now.get(Calendar.DAY_OF_MONTH) + "日"));
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.tv_right, R.id.mChoosePerson, R.id.mGroupBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mChoosePerson:
                Intent mChoosePersonIntent = new Intent(this, ChoosePersonWebActivity.class);
                startActivityForResult(mChoosePersonIntent, 1);
                break;
            case R.id.mGroupBtn:
                if (!StrUtil.isNotEmpty(mEditContent, true)) {
                    toast("请输入要发送的内容");
                    return;
                }
                if (TextUtils.isEmpty(ids)) {
                    toast("请选择患者");
                    return;
                }

                LoadDialog.show(this);
                Integer DrId = (Integer) SpUtils.get(Contants.Id, 0);
                String DrName = (String) SpUtils.get(Contants.Name, "");
                SendGroupMessageEntity mSendGroupMessageEntity = new SendGroupMessageEntity();
                mSendGroupMessageEntity.setQueryTypeId(2);
                mSendGroupMessageEntity.setQueryText(ids);
                mSendGroupMessageEntity.setDrId(DrId);
                mSendGroupMessageEntity.setDrName(DrName);
                mSendGroupMessageEntity.setContent(mEditContent.getText().toString());
                mSendGroupMessageEntity.setImagePaths(mSavePicList);

                String json = mGson.toJson(mSendGroupMessageEntity);
                Map<String, String> headMap = new HashMap<>();
                headMap.put("Authorization", SpUtils.getString(Contants.Token));
                mMyOkhttp.post().url(HttpUrl.GroupsAssistant).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<GroupsEntity>() {
                    @Override
                    public void onSuccess(int statusCode, GroupsEntity entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            if (entituy.isData()) {
                                toast("群发消息成功");
                                finish();
                                return;
                            }
                        }
                    }
                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.tv_right:
                Intent intent = new Intent(NewlyBuildGroupActivity.this, GroupSendHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<BaseMedia> picList) {
        mPicList.clear();
        for (BaseMedia localMedia : picList) {
            //被压缩后的图片路径
            String compressPath = localMedia.getPath(); //压缩后的图片路径
            mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
        }

        for (String path : mPicList) {
            uploadPicture(path);
        }
    }

    private void uploadPicture(String path) {
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
                loading(true);
                Map<String, String> headMap = new HashMap<>();
                headMap.put("Authorization",SpUtils.getString(Contants.Token));
                mMyOkhttp.post().url(HttpUrl.SendGroupImageUpload).params(map).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<PicturePathEntity>>() {
                    @Override
                    public void onSuccess(int statusCode, ResponseEntity<PicturePathEntity> entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            mSavePicList.add(entituy.getData().getSavePath());
                            Log.d("", mSavePicList.size() + "");
                        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ids = data.getStringExtra("ids");
                    Log.d("NewlyBuildGroupActivity", "打印数据------" + ids);
                    GroupChoosePersonData mResultData = mGson.fromJson(ids, GroupChoosePersonData.class);
                    ids = mResultData.getData();
                    mPersonContent.setText(mResultData.getName());
                    break;
                case IMG_REQUEST_CODE:
                    ArrayList<BaseMedia> images = Boxing.getResult(data);
                    if (images != null) {
                        for (BaseMedia image : images) {
                            uploadView.addFile(new File(image.getPath()));
                        }
                    }
                    refreshAdapter(images);
                    break;
            }
        }
    }
}
