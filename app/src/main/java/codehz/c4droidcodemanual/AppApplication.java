package codehz.c4droidcodemanual;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class AppApplication extends Application {
    private static AppApplication Self;
    public BmobUser bmobUser;

    public static AppApplication Get() {
        return Self;
    }

    @Override
    public void onCreate() {
        Bmob.initialize(this, "da1ccd1c8100bad2b32825bf1ab9fa34");
        bmobUser = BmobUser.getCurrentUser(this);
        Self = this;
    }

    public void login(final String username, final String password, final Callback<BmobUser> callback) {
        BmobUser.loginByAccount(this, username, password, new AppLogInListener(callback));
    }

    public void sign(final BmobUser user, final Callback<BmobUser> callback) {
        user.signUp(this, new AppSignUpListener(user, callback));
    }

    public interface Callback<T1> {
        void done(final T1 t, final Exception e);
    }

    private class AppLogInListener extends LogInListener<BmobUser> {
        private Callback<BmobUser> callback;

        private AppLogInListener(final Callback<BmobUser> callback) {
            this.callback = callback;
        }

        @Override
        public void done(final BmobUser bmobUser, final BmobException e) {
            if (bmobUser != null) {
                AppApplication.this.bmobUser = bmobUser;
            }
            callback.done(bmobUser, e);
        }
    }

    private class AppSignUpListener extends SaveListener {
        private BmobUser bmobUser;
        private Callback<BmobUser> callback;

        private AppSignUpListener(final BmobUser bmobUser, final Callback<BmobUser> callback) {
            this.bmobUser = bmobUser;
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            login(bmobUser.getUsername(), bmobUser.getPassword(), callback);
        }

        @Override
        public void onFailure(final int i, final String s) {
            callback.done(null, new BmobException(s));
        }
    }
}
