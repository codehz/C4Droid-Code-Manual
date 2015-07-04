package codehz.c4droidcodemanual;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        Bmob.initialize(this, "da1ccd1c8100bad2b32825bf1ab9fa34");
    }
}
