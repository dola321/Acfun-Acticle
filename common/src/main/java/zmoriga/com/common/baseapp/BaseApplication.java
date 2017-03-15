package zmoriga.com.common.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.io.File;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    /**
     * 获得缓存目录 <br>
     * <b>NOTE:</b>请先调用  判断是否可用
     *
     * @param type
     *            and so on.
     * @return
     */
    public static File getExternalCacheDir(String type) {
        File cacheDir = new File(baseApplication.getExternalCacheDir(), type);
        cacheDir.mkdirs();
        return cacheDir;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
