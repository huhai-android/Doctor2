package com.newdjk.doctor.ui.activity.min.accounts;//package com.lxq.compro.ui.activity.min.accounts;
//
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.PopupWindow;
//
//import com.bumptech.glide.Glide;
//import com.deceen.lxq.driver.R;
//import com.deceen.lxq.driver.activity.LoginActivity;
//import com.deceen.lxq.driver.entity.AccountManagement;
//import com.deceen.lxq.driver.entity.Entity;
//import com.deceen.lxq.driver.jpush.TagAliasOperatorHelper;
//import com.deceen.lxq.driver.model.DriverModel;
//import com.deceen.lxq.driver.model.HttpUrl;
//import com.deceen.lxq.driver.model.MyCallback;
//import com.deceen.lxq.driver.utils.CommonMethod;
//import com.deceen.lxq.driver.utils.SPInfo;
//import com.deceen.lxq.mylibrary.basic.BasicActivity;
//import com.deceen.lxq.mylibrary.tools.CircleImageView;
//import com.deceen.lxq.mylibrary.utils.ImageUtils;
//import com.deceen.lxq.mylibrary.utils.PermissionReq;
//import com.deceen.lxq.mylibrary.utils.SpUtils;
//import com.deceen.lxq.mylibrary.utils.StrUtil;
//import com.deceen.lxq.mylibrary.utils.U;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.deceen.lxq.mylibrary.utils.ImageUtils.REQUEST_CODE_FROM_ALBUM;
//import static com.deceen.lxq.mylibrary.utils.ImageUtils.REQUEST_CODE_FROM_CAMERA;
//
///**
// * 账户管理
// */
//@ContentView(R.custom_tab.activity_accounts)
//public class AccountsActivity extends BasicActivity {
//    private final int CAMERA = 1;
//    private final int ALBUM = 2;
//
//    @ViewInject(R.id.civImg)
//    CircleImageView civImg;
//
//    private String phone;
//
//    private PopupWindow pop;
//    private Uri imageUriFromCamera;
//
//    private String imgPath = "";
//
//    public static Intent create(Context context) {
//        return new Intent(context, AccountsActivity.class);
//    }
//
//    @Override
//    protected void initView() {
//        initBackTitle("账户管理");
//    }
//
//    @Override
//    protected void initListener() {
//
//    }
//
//    @Override
//    protected void initData() {
//        Glide.with(mContext).load(SpUtils.getString(SPInfo.userImg))
//                .error(R.mipmap.yonghutouxiang).into(civImg);
//        defaultRequst();
//
//    }
//
//    @Override
//    protected void otherViewClick(View view) {
//
//    }
//
//    /**
//     * 修改头像
//     *
//     * @param view
//     */
//    @Event(R.id.relat_xg_tx)
//    private void relatPws(View view) {
//        PermissionReq.with(this).permissions(
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .result(new PermissionReq.Result() {
//                    @Override
//                    public void onGranted() {// 准许
//                        pop = ImageUtils.showImagePickDialog((Activity) mContext, 0);
//                        pop.showAtLocation(U.inflate(R.custom_tab.activity_accounts), Gravity.BOTTOM, 0, 0);
//                    }
//
//                    @Override
//                    public void onDenied() {// 拒绝
//                        toast("更换头像功能需要相应的权限");
//                    }
//                }).request();
//
//    }
//
//    /**
//     * 修改手机
//     *
//     * @param view
//     */
//    @Event(R.id.relat_xg_phone)
//    private void relatXgPhone(View view) {
//        startActivity(ModifyPhoneActivity.create(this, phone));
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param view
//     */
//    @Event(R.id.relat_xg_pwd)
//    private void relatXgPwd(View view) {
//        startActivity(ModifyPwdActivity.create(this, phone));
//    }
//
//    /**
//     * 退出登录
//     *
//     * @param view
//     */
//    @Event(R.id.btn_confirm)
//    private void btnConfirm(View view) {
//        cancleTagAndAlias();
//        SpUtils.put(SPInfo.token, "");
//        SpUtils.put(SPInfo.mobilePhone, "");
//        SpUtils.put(SPInfo.userName, "");
//        SpUtils.put(SPInfo.userId, "");
//        SpUtils.put(SPInfo.driverId, "");
//        SpUtils.put(SPInfo.userImg, "");
//        SpUtils.put(SPInfo.orderStatus, "");//'司机接单状态（order_status：0-休息中、1-接单中（忙碌），2-接单中（空闲））
//        SpUtils.put(SPInfo.driverStatus, "");//'司机状态（v_status：0-未认证、1-资质待上传、2-资质待审核、3-审核驳回、4-已完成）',
//        SpUtils.put(SPInfo.vehicleStatus, "");
//        loading(true);
//        Map<String, String> map = CommonMethod.getMap();
//        map.put("userId", SpUtils.getString(SPInfo.userId));
//        DriverModel.submit2(HttpUrl.logout, map, new MyCallback<Entity>() {
//            @Override
//            public void get(Entity bean) {
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    /**
//     * 取消设置标签与别名
//     */
//    private void cancleTagAndAlias() {
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
//        tagAliasBean.alias = SpUtils.getString(SPInfo.userId);
////        tagAliasBean.tags = entity.getUser().getId();
//        tagAliasBean.isAliasAction = true;//true == alias  && false == tags
//        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), 0, tagAliasBean);
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_CODE_FROM_CAMERA://
//                if (resultCode == RESULT_OK) {
//                    imageUriFromCamera = ImageUtils.imageUriFromCamera;
//                    imgPath = ImageUtils.getImageAbsolutePath19(mContext, imageUriFromCamera);
//                    requst(imgPath, imageUriFromCamera);
//                }
//                break;
//            case REQUEST_CODE_FROM_ALBUM://
//                if (resultCode == RESULT_OK) {
//                    if (data != null) {
//                        Uri uri = data.getData();
//                        imgPath = ImageUtils.getImageAbsolutePath19(mContext, uri);
//                        requst(imgPath, uri);
//                    }
//                }
//                break;
//
//        }
//    }
//
//    /**
//     * rest/driverController/reviseTheHeadImage
//     * 参数userId为string类型是用户ID;
//     * 参数file是头像图片字段名
//     *
//     * @param imgPath
//     */
//    public void requst(String imgPath, Uri uri) {
//        if (StrUtil.isNotEmpty(imgPath, true)) {
//            loading(true);
//            Map<String, String> map = CommonMethod.getUserIdMap();
//            Map<String, File> mapFile = new HashMap<>();
//            mapFile.put("file", new File(imgPath));
//            DriverModel.upLoadFile(HttpUrl.reviseTheHeadImage, map, mapFile, new MyCallback<Entity>() {
//                @Override
//                public void get(Entity bean) {
//                    toast(bean.getMsg());
//                    civImg.setImageURI(imageUriFromCamera);
//                    defaultRequst();
//                }
//            });
//        }
//    }
//
//    private void defaultRequst() {
//        loading(true);
//        Map<String, String> map = CommonMethod.getUserIdMap();
//        DriverModel.accountManagement(map, new MyCallback<AccountManagement>() {
//            @Override
//            public void get(AccountManagement bean) {
//                phone = bean.getData().getMobilePhone();
//                SpUtils.put(SPInfo.userImg, StrUtil.getString(bean.getData().getPortrait()));
//                Glide.with(mContext).load(SpUtils.getString(SPInfo.userImg))
//                        .error(R.mipmap.yonghutouxiang).into(civImg);
//                Intent intent = new Intent("com.deceen.lxq.driver.PersonalFragment");
//                sendBroadcast(intent);
//            }
//        });
//    }
//
//}
