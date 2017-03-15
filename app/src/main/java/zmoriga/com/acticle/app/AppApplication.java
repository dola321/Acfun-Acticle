package zmoriga.com.acticle.app;

import zmoriga.com.acticle.BuildConfig;
import zmoriga.com.common.baseapp.BaseApplication;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtil.logInit(BuildConfig.LOG_DEBUG);
    }
}
