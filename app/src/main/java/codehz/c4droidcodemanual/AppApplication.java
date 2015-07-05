package codehz.c4droidcodemanual;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class AppApplication extends Application {
    private static AppApplication Self;
    BmobUser bmobUser;

    public static AppApplication Get() {
        return Self;
    }

    @Override
    public void onCreate() {
        Bmob.initialize(this, "da1ccd1c8100bad2b32825bf1ab9fa34");
        bmobUser = BmobUser.getCurrentUser(this);
        Self = this;
    }
}
