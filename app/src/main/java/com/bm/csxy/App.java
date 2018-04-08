package com.bm.csxy;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bm.csxy.constants.Urls;
import com.corelibs.api.ApiFactory;
import com.corelibs.common.Configuration;
import com.corelibs.utils.GalleryFinalConfigurator;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.liulishuo.filedownloader.FileDownloader;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.rong.imkit.RongIM;


/**
 * Created by john on 2017/6/14.
 */

public class App extends MultiDexApplication {
    static App instance;

    public App() {
        instance = this;
    }

    public static synchronized App getInstance() {
        return instance;
    }

    private boolean isAidl;

    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }

    public  static boolean isNewScan=true;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void init() {
        isAidl = true;


//        GlobalExceptionHandler.getInstance().init(this, getResources().getString(R.string.app_name));
//        CrashReport.initCrashReport(getApplicationContext(), "e772c92b3f", true);
        ToastMgr.init(getApplicationContext());
        Configuration.enableLoggingNetworkParams();
        ApiFactory.getFactory().add(Urls.ROOT); //初始化Retrofit接口工厂
        PreferencesHelper.init(getApplicationContext());
        FileDownloader.init(getApplicationContext());
        GalleryFinalConfigurator.config(getApplicationContext());
        UMShareAPI.get(this);
        //微信 appid appsecret
//		PlatformConfig.setWeixin("wxabd11cb58ec0fc01", "1ed2deb977da131f82c2058bae9f0aff");
        PlatformConfig.setWeixin("wxed9bd1c579c77654","a205baf5925a081c02ed3e09f12682c2");

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
            RongIM.init(this);

    }
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
