package com.newdjk.doctor.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.DiseaseInformationActivity;
import com.newdjk.doctor.ui.activity.AboutUsActivity;
import com.newdjk.doctor.ui.activity.ArchivesActivity;
import com.newdjk.doctor.ui.activity.CaseDetailActivity;
import com.newdjk.doctor.ui.activity.CustomLinkActivity;
import com.newdjk.doctor.ui.activity.DoctorPraiseActivity;
import com.newdjk.doctor.ui.activity.IM.GroupChatActivity;
import com.newdjk.doctor.ui.activity.Mdt.MDTInputReportFromListActivity;
import com.newdjk.doctor.ui.activity.Mdt.MYmdtInputReportActivity;
import com.newdjk.doctor.ui.activity.MedicalServiceActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.ShowOriginPictureActivity;
import com.newdjk.doctor.ui.activity.ZixunDoctorActivity;
import com.newdjk.doctor.ui.camera.FileUtil;
import com.newdjk.doctor.ui.entity.AdviceGoodDetailEntity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.GotoGroupChatEntity;
import com.newdjk.doctor.ui.entity.ImageInfoArrayEntity;
import com.newdjk.doctor.ui.entity.MDTDetailEntity;
import com.newdjk.doctor.ui.entity.MDTreportDetailEntity;
import com.newdjk.doctor.ui.entity.MsgContentEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.ui.entity.SendEndReportEntity;
import com.newdjk.doctor.ui.entity.SendExpertAdviceEntity;
import com.newdjk.doctor.ui.entity.WenZhenDiseaseEntity;
import com.newdjk.doctor.ui.fragment.MinFragment;
import com.newdjk.doctor.utils.DownloadCertUtils;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.GlideUtils;
import com.newdjk.doctor.utils.MyTIMMessage;
import com.newdjk.doctor.utils.PDFviewUtils;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.PlusDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMGroupTipsType;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageStatus;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.TIMVideoElem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gabriel on 2017/3/6.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    private List<MyTIMMessage> mTIMMessageList;
    private List<TIMUserProfile> timUserProfiles;
    private MediaPlayer mMediaPlayer;
    private static final String TAG = "ChatAdapter";
    public static AnimationDrawable mLastAnimationDrawable;
    private int mPosition = -1;
    private String mPath;
    private Context mContext;
    private Gson mGson;
    private String mDoctorMessage;
    private String mleftImagePath;
    private String mPatientName;
    private TIMConversation mTIMConversation;
    private boolean mIsHasOpenPres;
    private boolean mIsHasOpenTCMPres;
    private String identifer;

    public ChatAdapter(List<MyTIMMessage> timMessage, Context context, String doctorMessage, String leftImagePath, String patientName, TIMConversation timConversation, boolean isHasOpenPres, boolean isHasOpenTCMPres) {
        mContext = context;
        mTIMMessageList = timMessage;
        mDoctorMessage = doctorMessage;
        mMediaPlayer = new MediaPlayer();
        mleftImagePath = leftImagePath;
        mIsHasOpenPres = isHasOpenPres;
        mIsHasOpenTCMPres = isHasOpenTCMPres;
        mPatientName = patientName;
        mTIMConversation = timConversation;
        mGson = new Gson();
    }

    public ChatAdapter(List<MyTIMMessage> timMessage, List<TIMUserProfile> timUserProfiles, Context context, String doctorMessage, String leftImagePath, String patientName, TIMConversation timConversation, boolean isHasOpenPres, boolean isHasOpenTCMPres) {
        mContext = context;
        mTIMMessageList = timMessage;
        mDoctorMessage = doctorMessage;
        mMediaPlayer = new MediaPlayer();
        mleftImagePath = leftImagePath;
        mIsHasOpenPres = isHasOpenPres;
        mIsHasOpenTCMPres = isHasOpenTCMPres;
        mPatientName = patientName;
        mTIMConversation = timConversation;
        mGson = new Gson();
        timUserProfiles.clear();
        timUserProfiles.addAll(timUserProfiles);
    }


    public void setIsHasOpenPres(boolean isHasOpenPres) {
        mIsHasOpenPres = isHasOpenPres;
        notifyDataSetChanged();
    }

    public void setIsHasOpenTCMPres(boolean isHasOpenTCMPres) {
        mIsHasOpenTCMPres = isHasOpenTCMPres;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(MyApplication.getContext()).inflate(R.layout.item_message, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //由于sdk返回的list是按时间顺序排列的，所以显示时需要反过来
        position = mTIMMessageList.size() - position - 1;
        final int pposition = position;
        final MyTIMMessage myTIMMessage = mTIMMessageList.get(position);
        final TIMMessage timMessage = myTIMMessage.getTimMessage();
        final boolean isLocalMessage = myTIMMessage.isLocalMessage();
        boolean isRevoke = myTIMMessage.isRevoke();
        long timeStamp = 0;
        long lastimeStamp = 0;
        String sender = null;
        if (isLocalMessage) {
            timeStamp = myTIMMessage.getMsgTimestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
            sender = myTIMMessage.getSendTarget();
        } else {
            sender = timMessage.getSender();
            timeStamp = timMessage.timestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
        }

        final long timeStamp1 = timeStamp;
        Log.i(TAG, "sender=" + sender);
        holder.systemMessage.setVisibility(View.VISIBLE);

        holder.systemMessage.setText(TimeUtil.getChatTimeStr(timeStamp));
        holder.systemMessageLayout.setVisibility(View.GONE);
        /*holder.systemMessage1.setBackgroundColor(0);
        holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
        holder.systemMessageLayout.setBackgroundResource(0);*/
        if (position != mTIMMessageList.size() - 1) {
            TIMMessage lastTimMessage = mTIMMessageList.get(position + 1).getTimMessage();
            //如果上一条消息与当前这一条的时间间隔小于300秒，则不显示这一条消息的发送时间
            if (timeStamp - lastimeStamp < 300) {
                holder.systemMessage.setVisibility(View.GONE);
            }

        }


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);

        TIMElem element = timMessage.getElement(0);
        Log.i("aa", "sender=" + timMessage.getSender());
        Log.i("aa", "time=" + TimeUtil.getChatTimeStr(timeStamp));
        if (sender.equals(SpUtils.getString(Contants.identifier))) {     //自己向好友发送的消息
            //  GlideMediaLoader.load(MyApplication.getContext(), holder.rightAvatar, MinFragment.doctorPath);
       /*     holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "aaaaaa", Toast.LENGTH_SHORT).show();
                }
            });*/
            holder.rightMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long nowTime = System.currentTimeMillis() / 1000;
                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                        showPopupWindow(v, myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    } else {
                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

            });
            Log.d("头像", "个人头像" + MinFragment.doctorPath);
//            Glide.with(MyApplication.getContext())
//                    .load(MinFragment.doctorPath)
//                    .dontAnimate()
//                    .placeholder(R.drawable.doctor_default_img)
//                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(holder.rightAvatar);

            GlideUtils.loadDoctorImage(MinFragment.doctorPath, holder.rightAvatar);

            holder.leftPanel.setVisibility(View.GONE);
            holder.rightPanel.setVisibility(View.VISIBLE);
            holder.rightMessage.removeAllViews();
            holder.rightMessage.setBackgroundResource(R.drawable.boder_white_oval_doctor);
            //显示自己向好友发送的消息的发送状态
            if (isLocalMessage) {
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            } else {
                switch (timMessage.status()) {
                    case Sending:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.VISIBLE);
                        break;
                    case SendSucc:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                    case SendFail:
                        holder.sendError.setVisibility(View.VISIBLE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                }
            }
            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("你撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            //文字信息处理
            else if (element.getType() == TIMElemType.Text) {
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextSize(Dimension.SP, 14);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.rightMessage.addView(textView, layoutParams);
                //语音信息处理
            } else if (element.getType() == TIMElemType.Sound) {
                TIMSoundElem elem = (TIMSoundElem) element;
                final ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.right_voice3);
                imageView.setLayoutParams(layoutParams);
                holder.rightMessage.addView(imageView);
                Log.i("zdp", ((TIMSoundElem) element).getDuration() + "\"");
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                holder.rightMessage.addView(textView);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        if (mLastAnimationDrawable != null) {
                            mLastAnimationDrawable.selectDrawable(0);
                            mLastAnimationDrawable.stop();
                        }
                        //   animationDrawable.stop();
                    }
                });
                holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            if (mPosition == pposition) {
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                imageView.setImageResource(R.drawable.right_voice3);
                                mMediaPlayer.stop();
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                imageView.setImageResource(R.drawable.right_voice);
                                RelativeLayout relativeLayout = (RelativeLayout) v;
                                ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                operatePlaySound(AnimationDrawable);
                                //   mMediaPlayer = new MediaPlayer();
                                playSound(mMediaPlayer, timMessage, false, null, null);
                                mPosition = pposition;
                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });


                //视频消息处理 右边
            } else if (element.getType() == TIMElemType.Video) {

                holder.rightMessage.setBackgroundResource(0);

                View view = View.inflate(mContext, R.layout.item_video, null);
                final ImageView imageView = view.findViewById(R.id.video_image);
                final ImageView playvideo = view.findViewById(R.id.paly_image);
                final RelativeLayout relativeLayout = view.findViewById(R.id.lv_rv_root);

                final TIMVideoElem videoelem = (TIMVideoElem) element;


                int height = (int) videoelem.getSnapshotInfo().getHeight();
                int width = (int) videoelem.getSnapshotInfo().getWidth();
                if (height > width) {
                    height = 320;
                    width = 240;
                } else {
                    width = 320;
                    height = 240;
                }
                RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                        .LayoutParams(width
                        , height);
                imageView.setLayoutParams(pictureLayoutParams);
                Log.d("zdp", "image type: " + videoelem.getSnapshotInfo().getType() +
                        " image size " + videoelem.getSnapshotInfo().getSize() +
                        " image height " + videoelem.getSnapshotInfo().getHeight() +
                        " image width " + videoelem.getSnapshotInfo().getWidth());
                layoutParams.setMargins(0, 0, 0, 0);
                final String imagepath = FileUtil.sdkpath + videoelem.getSnapshotInfo().getUuid() + ".jpg";
                final String videopath = FileUtil.sdkpath + videoelem.getVideoInfo().getUuid() + ".mp4";
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getSnapshotInfo().getUuid()+".jpg");
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getVideoInfo().getUuid()+".mp4");
                //获取截图
                if (FileUtil.fileIsExists(imagepath)) {
                    Log.d(TAG, "图片文件存在");

                    GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, imagepath, R.drawable.new_nopic);

                } else {
                    videoelem.getSnapshotInfo().getImage(imagepath, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.d("zdp", "下载视频截图出错" + i + "  " + s);
                        }

                        @Override
                        public void onSuccess() {
                            // GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                            GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, imagepath, R.drawable.new_nopic);

                        }
                    });
                }
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (FileUtil.fileIsExists(videopath)) {
                            Log.d("zdp", "视频文件存在,直接播放" + pposition + " " + videopath);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            File file = new File(videopath);
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.fileprovider", file);
                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                uri = Uri.fromFile(file);
                            }
                            intent.setDataAndType(uri, "video/*");
                            mContext.startActivity(intent);

                        } else {
                            LoadDialog.show(mContext, "下载中");
                            videoelem.getVideoInfo().getVideo(videopath, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    LoadDialog.clear();
                                }

                                @Override
                                public void onSuccess() {
                                    LoadDialog.clear();
                                    Log.d("zdp", "下载完成后,再进行播放" + pposition);
                                    videoelem.setVideoPath(videopath);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String path = videoelem.getVideoPath();//该路径可以自定义
                                    File file = new File(path);
                                    Uri uri;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.fileprovider", file);
                                    } else {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        uri = Uri.fromFile(file);
                                    }
                                    intent.setDataAndType(uri, "video/*");
                                    mContext.startActivity(intent);
                                }
                            });
                        }

                    }
                });

                holder.rightMessage.addView(view);

            } else if (element.getType() == TIMElemType.Image) {
                holder.rightMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;
                for (TIMImage timImage : imageElem.getImageList()) {
                    Log.d(TAG, "图片消息右边" + timImage.getType() + "   " + timImage.getUrl());
                    if (timImage.getType().toString().equals("Original")) {
                        final String path = timImage.getUrl();
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                Log.d(TAG, "图片消息右边大图Original" + "   " + path);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                    if (timImage.getType().toString().equals("Large")) {
                    }

                    if (timImage.getType().toString().equals("Thumb")) {
                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        if (height > width) {
                            height = 320;
                            width = 240;
                        } else {
                            width = 320;
                            height = 240;
                        }
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setLayoutParams(pictureLayoutParams);
                        Log.d("zdp", "image type右边: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth() + timImage.getUrl());

                        GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, timImage.getUrl(), R.drawable.new_nopic);
                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.rightMessage.addView(imageView);

                    }
                }


            } else if (element.getType() == TIMElemType.GroupTips) {//群组消息
                TIMGroupTipsElem tipsElem = (TIMGroupTipsElem) element;
                String grouptitle = "";
                if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyGroupInfo) {
                    //群组事件通知
                    grouptitle = "[修改群名" + tipsElem.getGroupName() + "]";

                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Join) {
                    grouptitle = "加入群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Quit) {
                    grouptitle = "离开群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.SetAdmin) {
                    grouptitle = "设置新群主";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyMemberInfo) {
                    grouptitle = "修改群成员信息";
                }
                holder.systemMessage1.setText(grouptitle);
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            } else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    final String s = new String(customElem.getData());
                    Log.i("ChatAdapter", "s=" + s + " 消息时间" + timeStamp);

                   /* RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                            .LayoutParams(600
                            , RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                    Log.i("ChatAdapter", "ssss");
                    //音频消息类型
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
                        final ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.drawable.right_voice3);
                        imageView.setLayoutParams(layoutParams);
                        holder.rightMessage.addView(imageView);
                        //     Log.i("zdp", ((TIMSoundElem) element).getDuration() + "\"");
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "\"");
                        textView.setTextColor(mContext.getResources().getColor(R.color.white));
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        holder.rightMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        imageView.setImageResource(R.drawable.right_voice3);
                                        mMediaPlayer.stop();
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.right_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }

                            }

                        });
                        //图片类型
                    } else if (desc != null && desc.equals("TIMImageElem")) {
                        Log.i("TIMImageElem", "s=" + s);
                        holder.rightMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken<List<ImageInfoArrayEntity>>() {
                        }.getType();
                        List<ImageInfoArrayEntity> imageInfoArray = mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() > 0) {
                            for (int i = 0; i < imageInfoArray.size(); i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                    String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\", "/");
                                    holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else if (type == 3) {
                                    int height = imageInfoArrayEntity.getHeight();
                                    int width = imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());
                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
                                            " image size " + timImage.getSize() +
                                            " image height " + timImage.getHeight() +
                                            " image width " + timImage.getWidth());*/

                                    String url = imageInfoArrayEntity.getURL();
                                    String path = url.replace("\\", "/");
                                    Log.i("TIMImageElem", "URL=" + path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.rightMessage.addView(imageView, pictureLayoutParams);

                                }
                            }
                        }
                        //其他类型
                    }
                    //其他类型
                    else {

                        final CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = CustomMessageEntity.isIsSystem();
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            int showType = CustomMessageEntity.getShowType();

                            if (showType == 0 || showType == 2) {  //1.本人 2.医生  0.两者都显示
                                boolean IsShowDividingLine = CustomMessageEntity.isShowDividingLine();
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(CustomMessageEntity.getTitle());
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {

                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package_doctor, null);
//                            LinearLayout.LayoutParams layoutParam = new LinearLayout
//                                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
//                                    , LinearLayout.LayoutParams.WRAP_CONTENT);
                            //layoutParam.setMargins(10, 5, 0, 5);
                            // serviceView.setLayoutParams(layoutParam);
                            final TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            TextView checkDetailtext = serviceView.findViewById(R.id.check_detail_text);
                            RelativeLayout titleLayout = serviceView.findViewById(R.id.title_layout);
                            TextView tvxufang = serviceView.findViewById(R.id.tv_xufang);
                            View cutline = serviceView.findViewById(R.id.cutline);
                            TextView detailText = serviceView.findViewById(R.id.check_detail_text);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            CustomMessageDoctorAdapter adapter = new CustomMessageDoctorAdapter(CustomMessageEntity.getContent());
//                            list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                            list.setAdapter(adapter);
                            adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                                @Override
                                public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(view, myTIMMessage);
                                    } else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });

                            View line = serviceView.findViewById(R.id.line);
                            //  setListViewHeightBasedOnChildren(list);
                            //  adapter.setDatalist(CustomMessageEntity.getContent());
                            final String url = CustomMessageEntity.getLinkUrl() + "";
                            final CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                            if (extraData != null) {
                                Log.d(TAG, "打印数据" + extraData.toString());
                            }

                            // adapter.setDatalist(CustomMessageEntity.getContent());
                            int type1 = 0;
                            if (extraData == null) {
                                if (!TextUtils.isEmpty(url) && !"null".equals(url)) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                } else {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                }
                            } else {

                                type1 = extraData.getType();
                                Log.d(TAG, "获取type" + type1);
                                //隐藏 右边
                                if (type1 == 26 || type1 == 25 || type1 == 33 || type1 == 305 || type1 == 313 || type1 == 312 || type1 == 311 || type1 == 317 || type1 == 310 || type1 == 129 || type1 == 130 || type1 == 131 || type1 == 132 || type1 == 133 || type1 == 134|| type1 == 325) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    if (extraData.getType() == 28) {
                                        servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                                        detailText.setText("查看凭证");
                                    } else if (type1 == 41) {
                                        servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                                    }
                                }


                                //右边
                                if (type1 == 13 || type1 == 31 || type1 == 303 || type1 == 105 || type1 == 302 || type1 == 306 || type1 == 308 || type1 == 309 || type1 == 323|| type1 == 324) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    line.setVisibility(View.VISIBLE);
                                    tvxufang.setVisibility(View.VISIBLE);
                                    cutline.setVisibility(View.VISIBLE);
                                }

                                if (type1 == 306 || type1 == 308 || type1 == 309) {
                                    tvxufang.setVisibility(View.GONE);
                                    cutline.setVisibility(View.GONE);

                                }
                                if (type1 == 302 || type1 == 304) {
                                    tvxufang.setText("再次推荐");
                                }
                                if (type1 == 323||type1 == 324) {
                                    if (mIsHasOpenPres) {
                                        tvxufang.setText("开处方");
                                    } else {
                                        tvxufang.setText("转发需求");
                                    }

                                }

                                try {
                                    if (type1 == 316) {
                                        SendExpertAdviceEntity sendExpertAdviceEntity = mGson.fromJson(s, SendExpertAdviceEntity.class);

                                        if (sendExpertAdviceEntity.getExtData().getData().getDrId() == (MyApplication.mDoctorInfoByIdEntity.getDrId())) {
                                            checkDetail.setVisibility(View.VISIBLE);
                                            line.setVisibility(View.VISIBLE);
                                        } else {
                                            checkDetail.setVisibility(View.GONE);
                                            line.setVisibility(View.GONE);
                                        }

                                    }
                                } catch (Exception e) {

                                }


                                if (type1 == 319) {
                                    checkDetailtext.setText("进入专家会诊群");
                                }
                                if (type1 == 321) {
                                    checkDetailtext.setText("进入MDT会诊群");
                                }
                                //点击续方
                                tvxufang.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (extraData != null) {
                                            int type = extraData.getType();
                                            switch (type) {
                                                //续方
                                                case 13:
                                                    if (mIsHasOpenPres) {
                                                        //跳到开处方
                                                        toPrescriptionActivity(extraData, 1);

                                                    } else {
                                                        Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                    }
                                                    break;
                                                //开处方
                                                case 31:
                                                    if (mIsHasOpenPres) {
                                                        //跳到开处方
                                                        toPrescriptionActivity(extraData, 1);
                                                    } else {
                                                        Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                    }
                                                    break;

                                                //开中药处方
                                                case 303:
                                                    if (mIsHasOpenTCMPres) {
                                                        //跳到开处方
                                                        toPrescriptionActivity(extraData, 2);

                                                    } else {
                                                        Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                    }
                                                    break;
                                                case 105:
                                                    if (mIsHasOpenTCMPres) {
                                                        //跳到开处方
                                                        toPrescriptionActivity(extraData, 2);

                                                    } else {
                                                        Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                    }
                                                    break;

                                                case 304:
                                                    ToastUtil.setToast("优选推荐");
                                                    break;

                                                case 323:
                                                  Log.d("s",extraData.getData().getMedicationCompanyOrgId()+"数据")  ;

                                                    if (mIsHasOpenPres) { //有开处方权限 跳到开处方

                                                        toPrescriptionActivity(extraData, 3);
                                                    } else {

                                                        Intent intent = new Intent(mContext, ZixunDoctorActivity.class);  //转需求
                                                        intent.putExtra("MedicationCompanyOrgId",extraData.getData().getMedicationCompanyOrgId()+"");
                                                        intent.putExtra("PatRequireOrderId",extraData.getData().getPatRequireOrderId()+"");
                                                        mContext.startActivity(intent);

                                                    }
                                                    break;
                                                case 324:
                                                    Log.d("s",extraData.getData().getMedicationCompanyOrgId()+"数据")  ;

                                                    if (mIsHasOpenPres) { //有开处方权限 跳到开处方
                                                     toPrescriptionActivity(extraData, 3);
                                                    } else {

                                                        Intent intent = new Intent(mContext, ZixunDoctorActivity.class);  //转需求
                                                        intent.putExtra("MedicationCompanyOrgId",extraData.getData().getMedicationCompanyOrgId()+"");
                                                        intent.putExtra("PatRequireOrderId",extraData.getData().getPatRequireOrderId()+"");
                                                        mContext.startActivity(intent);

                                                    }
                                                    break;
                                            }

                                        }
                                    }
                                });

                                if (extraData.getType() == 399) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());
                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
                                            " image size " + timImage.getSize() +
                                            " image height " + timImage.getHeight() +
                                            " image width " + timImage.getWidth());*/


                                    final String path = extraData.getData().getImagePath();
                                    Log.i("TIMImageElem", "URL=" + path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.rightMessage.addView(imageView, pictureLayoutParams);
                                    holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                    return;
                                }

                            }


                            checkDetail.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(v, myTIMMessage);
                                    } else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                }
                            });
                            checkDetailtext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    if (!TextUtils.isEmpty(url) && !"null".equals(url)) {
                                        int type = extraData.getType();
                                        if (type != 304 && type != 306) {
                                            Log.d(TAG, "打印url=" + url + " type=" + type);
                                            Intent intent = new Intent(mContext, CustomLinkActivity.class);
                                            intent.putExtra("url", url);
                                            mContext.startActivity(intent);
                                        }
                                        if (type == 304) {
                                            AdviceGoodDetailEntity adviceGoodEntity = mGson.fromJson(s, AdviceGoodDetailEntity.class);
                                            Intent adviceintent = new Intent(mContext, PrescriptionActivity.class);
                                            adviceintent.putExtra("type", 12);
                                            adviceintent.putExtra("DataSource", adviceGoodEntity.getExtData().getData().getDataSourceOfDoc() + "");
                                            adviceintent.putExtra("DataId", adviceGoodEntity.getExtData().getData().getDataIdOfDoc() + "");
                                            adviceintent.putExtra("prescriptionMessage", mDoctorMessage);
                                            mContext.startActivity(adviceintent);
                                        }
                                        if (type == 306) {
//                                            Intent systemSettingIntent = new Intent(mContext, ReviewDiagnosisReportActivity.class);
//                                            systemSettingIntent.putExtra("MedicalReportPath", url);
//                                            systemSettingIntent.putExtra("isFromChat", "1");
//                                            mContext.startActivity(systemSettingIntent);

                                            PDFviewUtils.getInstanse().showPDF(mContext, url, "检验检查报告");
                                        }

                                    } else if (extraData != null) {
                                        int type = extraData.getType();
                                        Log.d(TAG, "type" + type);
                                        switch (type) {
                                            case 31:
                                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(prescriptionIntent);
                                                break;

                                            case 303:
                                                //查看中药处方详情
                                                if (mIsHasOpenPres) {
                                                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    prescriptionTCMIntent.putExtra("type", 18);
                                                    mContext.startActivity(prescriptionTCMIntent);
                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();

                                                }
                                                break;
                                            case 105:
                                                //查看中药处方详情
                                                if (mIsHasOpenPres) {
                                                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    prescriptionTCMIntent.putExtra("type", 18);
                                                    mContext.startActivity(prescriptionTCMIntent);
                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();

                                                }
                                                break;
                                            case 32:
                                                Intent intent = new Intent(mContext, MedicalServiceActivity.class);
                                                intent.putExtra("action", String.valueOf(extraData.getData().getServiceId()));
                                                intent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(intent);
                                                break;
                                            case 301:
                                                Intent CustomLinkintent = new Intent(mContext, CustomLinkActivity.class);
                                                CustomLinkintent.putExtra("url", extraData.getData().getLinkURL() + "");
                                                mContext.startActivity(CustomLinkintent);
                                                break;
                                            case 28:

                                                String title = CustomMessageEntity.getFocusTitle();
                                                List<com.newdjk.doctor.ui.entity.CustomMessageEntity.ContentBean> Content = CustomMessageEntity.getContent();
                                                String number = "";
                                                String content = "";
                                                String stamp = extraData.getData().getStamp();
                                                if (Content != null && Content.size() == 2) {
                                                    number = Content.get(0).getContentElem().getText();
                                                    content = Content.get(1).getContentElem().getText();
                                                }
                                                //showPlusSign(title,content,stamp,number);
                                                PlusDialog PlusDialog = new PlusDialog(mContext);
                                                PlusDialog.show(title, content, stamp, number);
                                                break;
                                            case 41:
                                                Log.d(TAG, extraData.getData().toString());
                                                Intent intentcheck = new Intent(mContext, PrescriptionActivity.class);
                                                intentcheck.putExtra("type", 9);
                                                intentcheck.putExtra("prescriptionMessage", mDoctorMessage);
                                                intentcheck.putExtra("CensorateRecordId", extraData.getData().getCensorateRecordId() + "");
                                                mContext.startActivity(intentcheck);
                                                break;

                                            case 304:

                                                AdviceGoodDetailEntity adviceGoodEntity = mGson.fromJson(s, AdviceGoodDetailEntity.class);
                                                Intent adviceintent = new Intent(mContext, PrescriptionActivity.class);
                                                adviceintent.putExtra("type", 12);
                                                adviceintent.putExtra("DataSource", adviceGoodEntity.getExtData().getData().getDataSourceOfDoc() + "");
                                                adviceintent.putExtra("DataId", adviceGoodEntity.getExtData().getData().getDataIdOfDoc() + "");
                                                adviceintent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(adviceintent);
                                                break;

                                            case 313:

                                                ToastUtil.setToast("MDT");
                                                break;

                                            case 311:
                                                Intent ArchivesIntent = new Intent(mContext, ArchivesActivity.class);
//                                                if (TextUtils.isEmpty(mPrescriptionMessage)) {
//                                                    mPrescriptionMessage = SpUtils.getString(Contants.LoginJson);
//                                                }
//                                                Log.d("chat", "mPrescriptionMessage-----" + mPrescriptionMessage);
//                                                ArchivesIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                mContext.startActivity(ArchivesIntent);
                                                break;

                                            case 309:
                                                MDTreportDetailEntity mdTreportDetailEntity = mGson.fromJson(s, MDTreportDetailEntity.class);
                                                toDiseaseDetail(mdTreportDetailEntity, 0);
                                                break;
                                            case 308:
                                                MDTreportDetailEntity mdTreportDetailEntity3 = mGson.fromJson(s, MDTreportDetailEntity.class);

                                                break;
                                            case 314:
                                                SendEndReportEntity sendEndReportEntity = mGson.fromJson(s, SendEndReportEntity.class);
                                                toReportDetail(sendEndReportEntity.getExtData().getData().getSubjectBuyRecordId());
                                                break;

                                            case 316:
                                                // ToastUtil.setToast("查看详情");MDTInputReportActivity

                                                SendExpertAdviceEntity sendExpertAdviceEntity = mGson.fromJson(s, SendExpertAdviceEntity.class);
                                                Intent intentsuggest = new Intent(mContext, MYmdtInputReportActivity.class);
                                                intentsuggest.putExtra("DrId", sendExpertAdviceEntity.getExtData().getData().getDrId() + "");
                                                intentsuggest.putExtra("SubjectBuyRecordId", sendExpertAdviceEntity.getExtData().getData().getSubjectBuyRecordId() + "");
                                                mContext.startActivity(intentsuggest);


                                                break;
                                            case 319:
                                                // ToastUtil.setToast("查看详情");MDTInputReportActivity

                                                GotoGroupChatEntity gotoGroupChatEntity = mGson.fromJson(s, GotoGroupChatEntity.class);
                                                getIMRelationRecord(gotoGroupChatEntity);


                                                break;
                                            case 321:
                                                // ToastUtil.setToast("查看详情");MDTInputReportActivity

                                                GotoGroupChatEntity gotoGroupChatEntity2 = mGson.fromJson(s, GotoGroupChatEntity.class);
                                                getIMRelationRecord(gotoGroupChatEntity2);

                                                break;

                                            case 323:
                                               // ToastUtil.setToast("跳转陈捷航详情界面");
                                                Log.d(TAG, extraData.toString());
                                                Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                prescriptionTCMIntent.putExtra("type", 40);
                                                mContext.startActivity(prescriptionTCMIntent);
                                                break;
                                            case 324:
                                                // ToastUtil.setToast("跳转陈捷航详情界面");
                                                Log.d(TAG, extraData.toString());
                                                Intent prescriptionTCMIntent2 = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionTCMIntent2.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                prescriptionTCMIntent2.putExtra("prescriptionMessage", mDoctorMessage);
                                                prescriptionTCMIntent2.putExtra("type", 40);
                                                mContext.startActivity(prescriptionTCMIntent2);
                                                break;
                                            case 326: //补充资料完成
                                                // ToastUtil.setToast("跳转陈捷航详情界面");
                                                Log.d(TAG, extraData.toString());
                                                Intent prescriptionTCMIntent3 = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionTCMIntent3.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                prescriptionTCMIntent3.putExtra("prescriptionMessage", mDoctorMessage);
                                                prescriptionTCMIntent3.putExtra("type", 40);
                                                mContext.startActivity(prescriptionTCMIntent3);
                                                break;
                                        }
                                    }
                                }
                            });
                            String title = CustomMessageEntity.getTitle();
                            Log.i("ChatAdapter==Title", title + "   " + s);


                            if (!TextUtils.isEmpty(title)) {
                                titleLayout.setVisibility(View.VISIBLE);
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText(title);

                            } else {

                                titleLayout.setVisibility(View.GONE);
                            }


                            if (type1 == 33) {
                                titleLayout.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("发放问卷：" + title);
                            } else if (type1 == 26) {
                                titleLayout.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("" + title);

                            } else if (type1 == 31 || type1 == 303) {
                                titleLayout.setVisibility(View.VISIBLE);
                                servicePackageName.setText("" + title);
                            } else if (type1 == 134) {

                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time;
                                if (data != null) {
                                    time = data.getTime();
                                } else {
                                    time = null;
                                }
                                if (time != null && !time.equals("")) {
                                    titleLayout.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.GONE);
                                    servicePackageName.setVisibility(View.VISIBLE);
                                    servicePackageName.setText("视频通话时长：" + time);
                                } else {
                                    titleLayout.setVisibility(View.VISIBLE);
                                    list.setVisibility(View.GONE);
                                    servicePackageName.setVisibility(View.VISIBLE);
                                    servicePackageName.setText("已挂断视频通话");
                                }


                            } else if (type1 == 133) {
                                titleLayout.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("我拒绝了视频邀请");
                            } else if (type1 == 132) {
                                titleLayout.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("对方无应答视频通话");

                            } else if (type1 == 131) {
                                titleLayout.setVisibility(View.VISIBLE);

                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("已取消视频通话");

                            } else if (CustomMessageEntity.getFocusTitle() == null) {
                                if (TextUtils.isEmpty(title)) {
                                    titleLayout.setVisibility(View.GONE);
                                }

                            } else {
                                list.setVisibility(View.VISIBLE);
                            }

                            if (type1 == 316) {
                                titleLayout.setVisibility(View.VISIBLE);
                            }
                            try {
                                if (type1 == 316) {
                                    SendExpertAdviceEntity sendExpertAdviceEntity = mGson.fromJson(s, SendExpertAdviceEntity.class);
                                    if (sendExpertAdviceEntity.getExtData().getData().getDrId() == (MyApplication.mDoctorInfoByIdEntity.getDrId())) {
                                        checkDetail.setVisibility(View.VISIBLE);
                                        line.setVisibility(View.VISIBLE);
                                    } else {
                                        checkDetail.setVisibility(View.GONE);
                                        line.setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e) {

                            }

                            if (type1 == 319) {
                                checkDetailtext.setText("进入专家会诊群");
                            }
                            if (type1 == 321) {
                                checkDetailtext.setText("进入MDT会诊群");
                            }
                       /* else {
                            servicePackageName.setVisibility(View.GONE);
                        }
                        if(CustomMessageEntity.getFocusTitle() != null) {
                            servicePackageName.setVisibility(View.VISIBLE);
                            servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                        }*/
                            if (type1 == 130 || type1 == 129) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.rightMessage.addView(serviceView);
                            }

                            // Log.i("ChatAdapter", "CustomMessageEntity=" + CustomMessageEntity.getTitle());
                        }
                    }
                } catch (JsonSyntaxException e) {
                    // Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }

              /*  else {
                    TextView textView = new TextView(MyApplication.getContext());
                    textView.setText(s);
                    textView.setTextSize(Dimension.SP, 16);
                    holder.rightMessage.addView(textView, layoutParams);
                }*/
            }
        } else {   //好友向自己发送的消息 接收消息
            holder.leftPanel.setVisibility(View.VISIBLE);
            holder.rightPanel.setVisibility(View.GONE);
            holder.leftMessage.removeAllViews();
            holder.sender.setVisibility(View.GONE);
            holder.sender.setText(timMessage.getSender());
            holder.leftMessage.setBackgroundResource(R.drawable.boder_white_oval_patient);
            // GlideMediaLoader.load(MyApplication.getContext(), holder.leftAvatar, mleftImagePath);

            Log.d("头像111", "个人头像" + mleftImagePath + "  发送者id" + timMessage.getSender());
            if (TextUtils.isEmpty(mleftImagePath)) {
                ArrayList<String> memberIds = new ArrayList<>();
                memberIds.add(timMessage.getSender());
                TIMFriendshipManager.getInstance().getUsersProfile(memberIds, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles.size() > 0) {
                            Glide.with(MyApplication.getContext())
                                    .load(timUserProfiles.get(0).getFaceUrl())
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.patient_default_img).
                                                    error(R.drawable.patient_default_img)
                                    )
                                    .into(holder.leftAvatar);


                        }

                    }
                });
            } else {
//                Glide.with(MyApplication.getContext())
//                        .load(mleftImagePath)
//                        .dontAnimate()
//                        .placeholder(R.drawable.patient_default_img)
//                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(holder.leftAvatar);
                GlideUtils.loadPatientImage(mleftImagePath, holder.leftAvatar);
            }


            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("对方撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            }
            //文字信息处理
            else if (element.getType() == TIMElemType.Text) {
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextColor(Color.DKGRAY);
                textView.setTextSize(Dimension.SP, 14);

                holder.leftMessage.addView(textView);

                //视频消息处理 左边
            } else if (element.getType() == TIMElemType.Video) {
                holder.leftMessage.setBackgroundResource(0);
                View view = View.inflate(mContext, R.layout.item_video, null);
                final ImageView imageView = view.findViewById(R.id.video_image);
                final ImageView playvideo = view.findViewById(R.id.paly_image);
                final RelativeLayout relativeLayout = view.findViewById(R.id.lv_rv_root);
                final TIMVideoElem videoelem = (TIMVideoElem) element;
                int height = (int) videoelem.getSnapshotInfo().getHeight();
                int width = (int) videoelem.getSnapshotInfo().getWidth();
                if (height > width) {
                    height = 320;
                    width = 240;
                } else {
                    width = 320;
                    height = 240;
                }
                RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                        .LayoutParams(width
                        , height);
                imageView.setLayoutParams(pictureLayoutParams);
                Log.d("zdp", "image type: " + videoelem.getSnapshotInfo().getType() +
                        " image size " + videoelem.getSnapshotInfo().getSize() +
                        " image height " + videoelem.getSnapshotInfo().getHeight() +
                        " image width " + videoelem.getSnapshotInfo().getWidth());
                //layoutParams.setMargins(0, 0, 0, 0);
                final String imagepath = FileUtil.sdkpath + videoelem.getSnapshotInfo().getUuid() + ".jpg";
                final String videopath = FileUtil.sdkpath + videoelem.getVideoInfo().getUuid() + ".mp4";
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getSnapshotInfo().getUuid()+".jpg");
//                FileUtil.createFile(FileUtil.sdkpath, videoelem.getVideoInfo().getUuid()+".mp4");
                //获取截图
                if (FileUtil.fileIsExists(imagepath)) {
                    Log.d(TAG, "图片文件存在");
                    GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, imagepath, R.drawable.new_nopic);
                } else {
                    videoelem.getSnapshotInfo().getImage(imagepath, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.d("zdp", "下载视频截图出错" + i + "  " + s);
                        }

                        @Override
                        public void onSuccess() {
                            // GlideMediaLoader.load(MyApplication.getContext(), imageView, imagepath);
                            GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, imagepath, R.drawable.new_nopic);

                        }
                    });
                }


                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (FileUtil.fileIsExists(videopath)) {
                            Log.d("zdp", "视频文件存在,直接播放" + pposition + " " + videopath);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            File file = new File(videopath);
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.fileprovider", file);
                            } else {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                uri = Uri.fromFile(file);
                            }
                            intent.setDataAndType(uri, "video/*");
                            mContext.startActivity(intent);

                        } else {
                            LoadDialog.show(mContext, "下载中");
                            videoelem.getVideoInfo().getVideo(videopath, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    LoadDialog.clear();
                                }

                                @Override
                                public void onSuccess() {
                                    LoadDialog.clear();
                                    Log.d("zdp", "下载完成后,再进行播放" + pposition);
                                    videoelem.setVideoPath(videopath);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String path = videoelem.getVideoPath();//该路径可以自定义
                                    File file = new File(path);
                                    Uri uri;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(mContext, "com.newdjk.doctor.fileprovider", file);
                                    } else {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        uri = Uri.fromFile(file);
                                    }
                                    intent.setDataAndType(uri, "video/*");
                                    mContext.startActivity(intent);
                                }
                            });
                        }

                    }
                });
                holder.leftMessage.addView(view);

            } else if (element.getType() == TIMElemType.Sound) {
                final ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.left_voice3);
                holder.leftMessage.addView(imageView);
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.leftMessage.addView(textView);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        if (mLastAnimationDrawable != null) {
                            mLastAnimationDrawable.selectDrawable(0);
                            mLastAnimationDrawable.stop();
                        }
                        //   animationDrawable.stop();
                    }
                });
                holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            if (mPosition == pposition) {
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                mMediaPlayer.stop();
                                imageView.setImageResource(R.drawable.left_voice3);
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                imageView.setImageResource(R.drawable.left_voice);
                                RelativeLayout relativeLayout = (RelativeLayout) v;
                                ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                operatePlaySound(AnimationDrawable);
                                //   mMediaPlayer = new MediaPlayer();
                                playSound(mMediaPlayer, timMessage, false, null, null);
                                mPosition = pposition;
                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });

                //图片信息处理
            } else if (element.getType() == TIMElemType.GroupTips) {//群组消息
                TIMGroupTipsElem tipsElem = (TIMGroupTipsElem) element;
                String grouptitle = "";
                String opname = tipsElem.getOpUserInfo().getNickName();
                if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyGroupInfo) {
                    //群组事件通知
                    grouptitle = opname + "修改群名为" + tipsElem.getGroupName() + "";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Join) {
                    Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                    Iterator<TIMUserProfile> iterator2 = values.iterator();
                    while (iterator2.hasNext()) {
                        grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                    }
                    grouptitle = grouptitle + "加入群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.Quit) {
                    Collection<TIMUserProfile> values = tipsElem.getChangedUserInfo().values();
                    Iterator<TIMUserProfile> iterator2 = values.iterator();
                    while (iterator2.hasNext()) {
                        grouptitle = grouptitle + (iterator2.next().getNickName() + ", ");
                    }
                    grouptitle = grouptitle + "离开群聊";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.SetAdmin) {
                    grouptitle = opname + "设置新群主";
                } else if (tipsElem.getTipsType() == TIMGroupTipsType.ModifyMemberInfo) {
                    grouptitle = opname + "修改群成员信息";
                }
                holder.systemMessage1.setText(grouptitle);
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            } else if (element.getType() == TIMElemType.Image) {
                holder.leftMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;

                for (TIMImage timImage : imageElem.getImageList()) {
                    Log.d(TAG, "图片消息左边" + timImage.getType() + "   " + timImage.getUrl());
                    if (timImage.getType().toString().equals("Large")) {
                    }

                    if (timImage.getType().toString().equals("Original")) {
                        final String path = timImage.getUrl();
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                //点击
                                Log.d(TAG, "图片消息左边Original  " + "   " + path);
                                mContext.startActivity(intent);
                            }
                        });
                    }

                    if (timImage.getType().toString().equals("Thumb")) {
                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        if (height > width) {
                            height = 320;
                            width = 240;
                        } else {
                            width = 320;
                            height = 240;
                        }
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);

                        ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setLayoutParams(pictureLayoutParams);
                        Log.d("zdp", "image type左边: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth());

                        GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, timImage.getUrl(), R.drawable.new_nopic);
                        // layoutParams.setMargins(0, 0, 0, 0);

                        holder.leftMessage.addView(imageView);

                    }
                   /* ImageView imageView = new ImageView(MyApplication.getContext());
                    Glide.with(MyApplication.getContext())
                            .load(timImage.getUrl())
                         //   .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.leftMessage.addView(imageView, layoutParams);*/
                }
            } else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    final String s = new String(customElem.getData());
                    Log.i("customElem", "s=" + s);
                    //   String s = mGson.toJson(data);
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
                        final ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.drawable.left_voice3);
                        holder.leftMessage.addView(imageView);
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "\"");
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        holder.leftMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    //  Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                       /* holder.sendError.setVisibility(View.GONE);
                                        holder.sending.setVisibility(View.GONE);*/
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        mMediaPlayer.stop();
                                        imageView.setImageResource(R.drawable.left_voice3);
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.left_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }
                            }
                        });


                    } else if (desc != null && desc.equals("图片")) {
                        //399
                        Log.d(TAG, "打印图片消息" + s);
                        final CustomMessageEntity custdata = mGson.fromJson(s, CustomMessageEntity.class);

                        final CustomMessageEntity.ExtDataBean extraData = custdata.getExtData();

                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
                                            " image size " + timImage.getSize() +
                                            " image height " + timImage.getHeight() +
                                            " image width " + timImage.getWidth());*/
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        final String path = extraData.getData().getImagePath();
                        Log.i("TIMImageElem", "URL=" + path);
                        GlideMediaLoader.loadRezise(MyApplication.getContext(), imageView, path, R.drawable.new_nopic);
                        //layoutParams.setMargins(0, 0, 0, 0);
                        holder.leftMessage.addView(imageView);
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                mContext.startActivity(intent);
                            }
                        });

                    } else if (desc != null && desc.equals("TIMImageElem")) {
                        holder.leftMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken<List<ImageInfoArrayEntity>>() {
                        }.getType();
                        Log.i("TIMImageElem", "s=" + s);
                        List<ImageInfoArrayEntity> imageInfoArray = mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() > 0) {
                            for (int i = 0; i < imageInfoArray.size(); i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                    String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\", "/");
                                    holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else if (type == 3) {
                                    int height = imageInfoArrayEntity.getHeight();
                                    int width = imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());

                                    String url = imageInfoArrayEntity.getURL();
                                    String path = url.replace("\\", "/");
                                    Log.i("TIMImageElem", "URL=" + path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    //layoutParams.setMargins(0, 0, 0, 0);
                                    holder.leftMessage.addView(imageView);

                                }


                            }
                        }
                    } else if (s != null && !s.equals("")) {
                        // holder.leftMessage.setBackgroundResource(0);
                        RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                                .LayoutParams(600
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = CustomMessageEntity.isIsSystem();
                        Log.i("ChatAdapter", "s=" + s + " 消息时间" + timeStamp);
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            holder.leftPanel.setVisibility(View.GONE);
                            holder.rightPanel.setVisibility(View.GONE);
                            holder.leftMessage.removeAllViews();
                            boolean IsShowDividingLine = CustomMessageEntity.isShowDividingLine();
                            int showType = CustomMessageEntity.getShowType();
                            if (showType == 0 || showType == 2) {
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(CustomMessageEntity.getTitle());
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {
                            //接收消息，显示在左边
                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package_patient, null, false);
                            TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            TextView checkDetailtext = serviceView.findViewById(R.id.check_detail_text);
                            TextView tvxufang = serviceView.findViewById(R.id.tv_xufang);
                            View cutline = serviceView.findViewById(R.id.cutline);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            View line = serviceView.findViewById(R.id.line);
                            CustomMessageAdapter adapter = new CustomMessageAdapter(CustomMessageEntity.getContent());
                            //  list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                            list.setAdapter(adapter);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            final String url = CustomMessageEntity.getLinkUrl() + "";
                            final CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                            if (extraData != null) {
                                Log.d(TAG, "左边打印数据" + extraData.getType());

                            }
                            // adapter.setDatalist(CustomMessageEntity.getContent());
                            int type1 = 0;
                            if (extraData == null) {
                                checkDetail.setVisibility(View.GONE);
                                line.setVisibility(View.GONE);
                            } else {
                                type1 = extraData.getType();  //左边
                                if (type1 == 0 || type1 == 34 || type1 > 100 || type1 == 313 || type1 == 312 || type1 == 311 || type1 == 317 || type1 == 310) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);

                                }
                                //显示查看详情
                                if (type1 == 13 || type1 == 31 || type1 == 303 || type1 == 105 || type1 == 302 || type1 == 306 || type1 == 314 || type1 == 308 || type1 == 309 || type1 == 321|| type1 == 326||type1==324||type1==323) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    line.setVisibility(View.VISIBLE);
                                    tvxufang.setVisibility(View.VISIBLE);
                                    cutline.setVisibility(View.VISIBLE);
                                }
                                if (type1 == 306 || type1 == 314 || type1 == 308 || type1 == 309 || type1 == 321|| type1 == 326) {
                                    tvxufang.setVisibility(View.GONE);
                                    cutline.setVisibility(View.GONE);

                                }

                                if (type1 == 302 || type1 == 304) {
                                    tvxufang.setText("再次推荐");
                                }

                                if (type1 == 323||type1 == 324) {
                                    if (mIsHasOpenPres) {
                                        tvxufang.setText("开处方");
                                    } else {
                                        tvxufang.setText("转发需求");
                                    }

                                }


                                if (type1 == 319) {
                                    checkDetailtext.setText("进入专家会诊群");
                                }
                                if (type1 == 321) {
                                    checkDetailtext.setText("进入MDT会诊群");
                                }
                            }
                            //续方  左边
                            tvxufang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                            //续方
                                            case 13:
                                                if (mIsHasOpenPres) {
                                                    //跳到开处方
                                                    toPrescriptionActivity(extraData, 1);

                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            //开处方
                                            case 31:
                                                if (mIsHasOpenPres) {
                                                    //跳到开处方
                                                    toPrescriptionActivity(extraData, 1);

                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                }
                                                break;


                                            //开中药处方
                                            case 303:
                                                if (mIsHasOpenTCMPres) {
                                                    //跳到开处方
                                                    toPrescriptionActivity(extraData, 2);

                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            //开中药处方
                                            case 105:
                                                if (mIsHasOpenTCMPres) {
                                                    //跳到开处方
                                                    toPrescriptionActivity(extraData, 2);

                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case 304:
                                                ToastUtil.setToast("优选推荐");
                                                break;

                                            case 314:
                                                SendEndReportEntity sendEndReportEntity = mGson.fromJson(s, SendEndReportEntity.class);
                                                toReportDetail(sendEndReportEntity.getExtData().getData().getSubjectBuyRecordId());

                                                break;


                                            case 38:
                                                ToastUtil.setToast("查看病情详情");

                                                break;
                                            case 324:
                                                Log.d("s",extraData.getData().getMedicationCompanyOrgId()+"数据")  ;

                                                if (mIsHasOpenPres) { //有开处方权限 跳到开处方

                                                    toPrescriptionActivity(extraData, 3);
                                                } else {

                                                    Intent intent = new Intent(mContext, ZixunDoctorActivity.class);  //转需求
                                                    intent.putExtra("MedicationCompanyOrgId",extraData.getData().getMedicationCompanyOrgId()+"");
                                                    intent.putExtra("PatRequireOrderId",extraData.getData().getPatRequireOrderId()+"");
                                                    mContext.startActivity(intent);

                                                }
                                                break;
                                            case 323:
                                                Log.d("s",extraData.getData().getMedicationCompanyOrgId()+"数据")  ;

                                                if (mIsHasOpenPres) { //有开处方权限 跳到开处方

                                                    toPrescriptionActivity(extraData, 3);
                                                } else {

                                                    Intent intent = new Intent(mContext, ZixunDoctorActivity.class);  //转需求
                                                    intent.putExtra("MedicationCompanyOrgId",extraData.getData().getMedicationCompanyOrgId()+"");
                                                    intent.putExtra("PatRequireOrderId",extraData.getData().getPatRequireOrderId()+"");
                                                    mContext.startActivity(intent);

                                                }
                                                break;
                                        }

                                    }
                                }
                            });
                            checkDetailtext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!TextUtils.isEmpty(url) && !"null".equals(url)) {
                                        int type = extraData.getType();
                                        if (type == 306) {
                                            PDFviewUtils.getInstanse().showPDF(mContext, url, "检验检查报告");
                                        } else {
                                            Intent intent = new Intent(mContext, CustomLinkActivity.class);
                                            intent.putExtra("url", url);
                                            mContext.startActivity(intent);
                                        }

                                    } else if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                           /* case 31:
                                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(prescriptionIntent);
                                                break;*/
                                            case 13:
                                                if (mIsHasOpenPres) {
//                                                    Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
//                                                    prescriptionIntent.putExtra("id", String.valueOf(extraData.getData().getPrescriptionId()));
//                                                    prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
//                                                    mContext.startActivity(prescriptionIntent);

                                                    Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    mContext.startActivity(prescriptionIntent);
                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case 32:
                                                Intent intent = new Intent(mContext, MedicalServiceActivity.class);
                                                intent.putExtra("action", String.valueOf(extraData.getData().getServiceId()));
                                                intent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(intent);
                                                break;
                                            case 27:
                                                Log.i("ChatAdapter", "imgId=" + extraData.getData().getImageId() + ",medicalTempId=" + extraData.getData().getMedicalTempId() + ",patientName=" + extraData.getData().getPatientName());
                                                Intent caseDetailIntent = new Intent(mContext, CaseDetailActivity.class);
                                                caseDetailIntent.putExtra("imgId", extraData.getData().getImageId());
                                                caseDetailIntent.putExtra("medicalTempId", extraData.getData().getMedicalTempId());
                                                caseDetailIntent.putExtra("patientName", extraData.getData().getPatientName());
                                                caseDetailIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(caseDetailIntent);
                                                break;
                                          /*  case 42:
                                                Intent doctorPraiseIntent = new Intent(mContext, DoctorPraiseActivity.class);
                                                mContext.startActivity(doctorPraiseIntent);
                                                break;*/
                                            case 37:
                                                Intent doctorPraiseIntent = new Intent(mContext, DoctorPraiseActivity.class);
                                                mContext.startActivity(doctorPraiseIntent);
                                                break;

                                            case 303:
                                                //查看中药处方详情
                                                if (mIsHasOpenPres) {
                                                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    prescriptionTCMIntent.putExtra("type", 18);
                                                    mContext.startActivity(prescriptionTCMIntent);
                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();

                                                }

                                                break;
                                            case 105:
                                                //查看中药处方详情
                                                if (mIsHasOpenPres) {
                                                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    prescriptionTCMIntent.putExtra("type", 18);
                                                    mContext.startActivity(prescriptionTCMIntent);
                                                } else {
                                                    Toast.makeText(mContext, "你的权限不足，无法开处方", Toast.LENGTH_SHORT).show();

                                                }

                                                break;
                                            case 304:
                                                ToastUtil.setToast("优选推荐");
                                                break;

                                            case 313:

                                                ToastUtil.setToast("MDT");
                                                break;

                                            case 311:
                                                Intent ArchivesIntent = new Intent(mContext, ArchivesActivity.class);
//                                                if (TextUtils.isEmpty(mPrescriptionMessage)) {
//                                                    mPrescriptionMessage = SpUtils.getString(Contants.LoginJson);
//                                                }
//                                                Log.d("chat", "mPrescriptionMessage-----" + mPrescriptionMessage);
//                                                ArchivesIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                                                mContext.startActivity(ArchivesIntent);
                                                break;

                                            case 309:
                                                MDTreportDetailEntity mdTreportDetailEntity = mGson.fromJson(s, MDTreportDetailEntity.class);
                                                toDiseaseDetail(mdTreportDetailEntity, 0);
                                                break;
                                            case 308:
                                                MDTreportDetailEntity mdTreportDetailEntity3 = mGson.fromJson(s, MDTreportDetailEntity.class);
                                                toDiseaseDetail(mdTreportDetailEntity3, 0);
                                                break;

                                            case 314:
                                                SendEndReportEntity sendEndReportEntity = mGson.fromJson(s, SendEndReportEntity.class);
                                                toReportDetail(sendEndReportEntity.getExtData().getData().getSubjectBuyRecordId());
                                                break;
                                            case 316:
                                                SendExpertAdviceEntity sendExpertAdviceEntity = mGson.fromJson(s, SendExpertAdviceEntity.class);
                                                Intent intentsuggest = new Intent(mContext, MYmdtInputReportActivity.class);
                                                intentsuggest.putExtra("DrId", sendExpertAdviceEntity.getExtData().getData().getDrId() + "");
                                                intentsuggest.putExtra("SubjectBuyRecordId", sendExpertAdviceEntity.getExtData().getData().getSubjectBuyRecordId() + "");
                                                mContext.startActivity(intentsuggest);

                                                break;

                                            case 319:
                                                // ToastUtil.setToast("查看详情");MDTInputReportActivity

                                                GotoGroupChatEntity gotoGroupChatEntity = mGson.fromJson(s, GotoGroupChatEntity.class);
                                                getIMRelationRecord(gotoGroupChatEntity);


                                                break;

                                            case 321:
                                                // ToastUtil.setToast("查看详情");MDTInputReportActivity

                                                GotoGroupChatEntity gotoGroupChatEntity2 = mGson.fromJson(s, GotoGroupChatEntity.class);
                                                getIMRelationRecord(gotoGroupChatEntity2);

                                                break;
                                            case 38:
                                                Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                                                WenZhenDiseaseEntity wenZhenDiseaseEntity = mGson.fromJson(s, WenZhenDiseaseEntity.class);
                                                prescriptionTCMIntent.putExtra("RecordId", wenZhenDiseaseEntity.getExtData().getData().getRecordId());
                                                prescriptionTCMIntent.putExtra("type", 35);
                                                prescriptionTCMIntent.putExtra("ServiceType", wenZhenDiseaseEntity.getExtData().getData().getServiceType());
                                                mContext.startActivity(prescriptionTCMIntent);

                                                break;

                                            case 326:
                                                Log.d(TAG, extraData.toString());
                                                Intent prescriptionTCMIntent3 = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionTCMIntent3.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                prescriptionTCMIntent3.putExtra("prescriptionMessage", mDoctorMessage);
                                                prescriptionTCMIntent3.putExtra("type", 40);
                                                mContext.startActivity(prescriptionTCMIntent3);
                                                break;
                                            case 324://查看详情
                                                Log.d(TAG, extraData.toString());
                                                Intent xuqiuintent = new Intent(mContext, PrescriptionActivity.class);
                                                xuqiuintent.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                xuqiuintent.putExtra("prescriptionMessage", mDoctorMessage);
                                                xuqiuintent.putExtra("type", 40);
                                                mContext.startActivity(xuqiuintent);
                                                break;
                                            case 323: //查看详情
                                                Log.d(TAG, extraData.toString());
                                                Intent xuqiuintent2 = new Intent(mContext, PrescriptionActivity.class);
                                                xuqiuintent2.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                                                xuqiuintent2.putExtra("prescriptionMessage", mDoctorMessage);
                                                xuqiuintent2.putExtra("type", 40);
                                                mContext.startActivity(xuqiuintent2);
                                                break;

                                        }
                                    }

                                }
                            });
                            String title = CustomMessageEntity.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                servicePackageName.setText(title);
                            } else {

                                servicePackageName.setVisibility(View.GONE);
                            }
                            //  视频相关功能需要将查看详情隐藏
                            if (type1 >= 129 && type1 <= 134) {
                                checkDetail.setVisibility(View.GONE);
                                line.setVisibility(View.GONE);
                            }


                            if (type1 == 129) {
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("对方开启了视频邀请");

                            } else if (type1 == 133) {
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("对方拒绝了视频邀请");

                            } else if (type1 == 134) {
                                servicePackageName.setVisibility(View.VISIBLE);
                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time = null;
                                if (data != null) {
                                    time = data.getTime();
                                }
                                if (time != null && !time.equals("")) {
                                    servicePackageName.setText("视频通话时长：" + time);
                                } else {
                                    servicePackageName.setText("对方挂断了视频");
                                }
                            } else if (type1 == 132) {
                                servicePackageName.setVisibility(View.VISIBLE);
                                servicePackageName.setText("对方无应答视频通话");

                            }
                            if (type1 == 130) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.rightMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.leftMessage.addView(serviceView);
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }
               /* TIMCustomElem customElem = (TIMCustomElem) element;
                String s = new String(customElem.getData());
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(s);
                textView.setTextSize(Dimension.SP, 16);
                holder.leftMessage.addView(textView, layoutParams);*/
                //其他类型消息


            }
        }
    }

    private void toReportDetail(String SubjectBuyRecordId) {

        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", SubjectBuyRecordId + "");

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.QueryMDTBySubjectBuyRecordId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Intent intentsuggest = new Intent(mContext, MDTInputReportFromListActivity.class);
                    intentsuggest.putExtra("MDTDetailEntity", entity.getData());
                    intentsuggest.putExtra("type", 2);//已完成
                    mContext.startActivity(intentsuggest);
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

    //跳转群聊
    private void getIMRelationRecord(final GotoGroupChatEntity gotoGroupChatEntity) {

        Map<String, String> map = new HashMap<>();
        map.put("IMGroupId", gotoGroupChatEntity.getExtData().getData().getIMGroupId());

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.QueryMDTBySubjectBuyIMGroupId).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Intent intent = new Intent(mContext, GroupChatActivity.class);
                    intent.putExtra("action", "MDT");
                    intent.putExtra("MDTDetailEntity", entity.getData());
                    intent.putExtra(Contants.FRIEND_NAME, "");
                    intent.putExtra(Contants.FRIEND_IDENTIFIER, gotoGroupChatEntity.getExtData().getData().getIMGroupId());
                    mContext.startActivity(intent);

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

    //跳转详情
    private void toDiseaseDetail(MDTreportDetailEntity mdTreportDetailEntity, final int type) {
        LoadDialog.show(mContext);
        Map<String, String> map = new HashMap<>();
        map.put("SubjectBuyRecordId", mdTreportDetailEntity.getExtData().getData().getSubjectBuyRecordId());
        map.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, -1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        MyApplication.getInstance().getMyOkHttp().get().url(HttpUrl.QueryDrSubjectBuyRecordDetail).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<MDTDetailEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<MDTDetailEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    MDTDetailEntity data = entity.getData();

                    if (type == 0) {
                        Intent DiseaseInformaintent = new Intent(mContext, DiseaseInformationActivity.class);
                        DiseaseInformaintent.putExtra(Contants.Type, 3);
                        DiseaseInformaintent.putExtra(Contants.MDTINFO, data);
                        mContext.startActivity(DiseaseInformaintent);
                    } else if (type == 1) {

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
    public int getItemCount() {
        return mTIMMessageList.size();
    }

    public void setIdentifer(String identifier) {
        this.identifer = identifier;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.system_message_layout)
        FrameLayout systemMessageLayout;

        @BindView(R.id.systemMessage)
        TextView systemMessage;

        @BindView(R.id.leftPanel)
        RelativeLayout leftPanel;

        @BindView(R.id.rightPanel)
        RelativeLayout rightPanel;

        @BindView(R.id.leftAvatar)
        CircleImageView leftAvatar;

        @BindView(R.id.rightAvatar)
        CircleImageView rightAvatar;

        @BindView(R.id.sender)
        TextView sender;

        @BindView(R.id.leftMessage)
        RelativeLayout leftMessage;

        @BindView(R.id.rightMessage)
        RelativeLayout rightMessage;

        @BindView(R.id.sendStatus)
        RelativeLayout sendStatus;

        @BindView(R.id.rightDesc)
        TextView rightDesc;

        @BindView(R.id.sending)
        ProgressBar sending;

        @BindView(R.id.sendError)
        ImageView sendError;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.systemMessage1)
        TextView systemMessage1;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void playSound(final MediaPlayer mediaPlayer, TIMMessage timMessage, boolean isLocalMessage, String uuid, final String urlPath) {
        mediaPlayer.reset();
        // mediaPlayer = new MediaPlayer();
       /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                animationDrawable .selectDrawable(0);
                animationDrawable.stop();
            }
        });*/
        if (isLocalMessage) {
            final String path = MyApplication.getContext().getFilesDir()
                    + File.separator + uuid;
            File soundFile = new File(path);
            Log.i("ChatAdapter", "path=" + path);
            if (soundFile.exists()) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                final MediaPlayer finalMediaPlayer = mediaPlayer;
                downFile(urlPath, soundFile, mediaPlayer);
            }
        } else {
            TIMElem element = timMessage.getElement(0);
            if (element.getType() == TIMElemType.Sound) {
                final TIMSoundElem soundElem = (TIMSoundElem) element;
                final String path = MyApplication.getContext().getFilesDir()
                        + File.separator + ((TIMSoundElem) element).getUuid();
                File soundFile = new File(path);
                Log.i("ChatAdapter", "path=" + path);
                if (soundFile.exists()) {
                    try {
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    final MediaPlayer finalMediaPlayer = mediaPlayer;
                    soundElem.getSoundToFile(path, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onSuccess() {
                            soundElem.setPath(path);
                            try {
                                finalMediaPlayer.setDataSource(path);
                                finalMediaPlayer.prepare();
                                finalMediaPlayer.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        }
    }

    public void operatePlaySound(AnimationDrawable animationDrawable) {
        if (mLastAnimationDrawable == null) {
            mLastAnimationDrawable = animationDrawable;
            mLastAnimationDrawable.start();
        } else {
            try {
                mLastAnimationDrawable.selectDrawable(0);
                mLastAnimationDrawable.stop();
                mLastAnimationDrawable = animationDrawable;
                mLastAnimationDrawable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showPlusSign(final String title, String content, String stamp, String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.plus_sign_detail, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);


        TextView titleTx = view.findViewById(R.id.title);
        TextView numberTx = view.findViewById(R.id.number);
        ImageView stampIv = view.findViewById(R.id.stamp);
        TextView signContentTx = view.findViewById(R.id.sign_content);
        titleTx.setText(title);
        numberTx.setText(number);
        signContentTx.setText(content);
        Glide.with(MyApplication.getContext())
                .load(stamp)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(stampIv);
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.show();
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void downFile(final String path, final File file, final MediaPlayer mediaPlayer) {
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
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    //    notifyDataSetChanged();
                    inputStream.close();
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showPopupWindow(View view, final MyTIMMessage myTIMMessage) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.revoke_window, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 设置按钮的点击事件
        TextView button = contentView.findViewById(R.id.revoke);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), true);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TIMMessage timMessage = myTIMMessage.getTimMessage();
                mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        popupWindow.dismiss();
                        Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        popupWindow.dismiss();
                        myTIMMessage.setRevoke(true);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        popupWindow.setTouchable(true);

      /*  popupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));*/

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);


    }


    //去开处方界面
    public void toPrescriptionActivity(final CustomMessageEntity.ExtDataBean extraData, final int type) {

        DownloadCertUtils.getinstanse().downloadCert((Activity) mContext, new DownloadCertUtils.DownloadListener() {

            @Override
            public void locationSuccess(String data) {

            }

            @Override
            public void locationFailed(String data) {

            }

            @Override
            public void drawStampFailed(String data) {

            }

            @Override
            public void drawStampSuccess(String data) {
                //中药处方
                if (type == 1) {
                    Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                    prescriptionIntent.putExtra("id", String.valueOf(extraData.getData().getPrescriptionId()));
                    prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                    mContext.startActivity(prescriptionIntent);
                } else if (type==2){
                    //西药处方
                    //ToastUtil.setToast("中药续方");
                    Log.d(TAG, extraData.toString());
                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                    prescriptionTCMIntent.putExtra("type", 17);
                    mContext.startActivity(prescriptionTCMIntent);

                }else if (type==3){
                    Intent prescriptionTCMIntent = new Intent(mContext, PrescriptionActivity.class);
                    prescriptionTCMIntent.putExtra("action", String.valueOf(extraData.getData().getPatRequireOrderId()));
                    prescriptionTCMIntent.putExtra("prescriptionMessage", mDoctorMessage);
                    prescriptionTCMIntent.putExtra("type", 39);
                    mContext.startActivity(prescriptionTCMIntent);
                }

            }

            @Override
            public void signFailed(String data) {

            }

            @Override
            public void signSuccess(String data) {

            }

            @Override
            public void keepPinFailed(String data) {

            }

            @Override
            public void keepPinSuccess(String data) {

            }
        });


    }


}
