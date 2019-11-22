package com.newdjk.doctor.ui.camera;

import android.content.Context;


public class TUIKitImpl {

    private static final String TAG = TUIKitImpl.class.getSimpleName();

    private static Context sAppContext;
    private static TUIKitConfigs sConfigs;
    private static IMEventListener sIMEventListener;

    /**
     * TUIKit的初始化函数
     *
     * @param context  应用的上下文，一般为对应应用的ApplicationContext
     * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
     * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
     */
    public static void init(Context context, int sdkAppID, TUIKitConfigs configs) {
        sAppContext = context;
        sConfigs = configs;
        if (sConfigs.getGeneralConfig() == null) {
            GeneralConfig generalConfig = new GeneralConfig();
            sConfigs.setGeneralConfig(generalConfig);
        }
        sConfigs.getGeneralConfig().setAppCacheDir(context.getFilesDir().getPath());

        BackgroundTasks.initInstance();
        FileUtil.initPath(); // 取决于app什么时候获取到权限，即使在application中初始化，首次安装时，存在获取不到权限，建议app端在activity中再初始化一次，确保文件目录完整创建
        FaceManager.loadFaceFiles();
    }

    public static void login(String userid, String usersig, final IUIKitCallBack callback) {


    }

    public static void unInit() {
    }


    public static Context getAppContext() {
        return sAppContext;
    }

    public static TUIKitConfigs getConfigs() {
        if (sConfigs == null) {
            sConfigs = TUIKitConfigs.getConfigs();
        }
        return sConfigs;
    }

    public static void setIMEventListener(IMEventListener listener) {
        sIMEventListener = listener;
    }
}
