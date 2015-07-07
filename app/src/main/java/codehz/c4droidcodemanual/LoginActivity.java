package codehz.c4droidcodemanual;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayoutCompat dl = (LinearLayoutCompat) findViewById(R.id.main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((LinearLayoutCompat.LayoutParams) dl.getLayoutParams()).topMargin =
                    Resources.getSystem().getDimensionPixelSize(
                            Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BmobUser.loginByAccount(this,
                ((MaterialEditText) findViewById(R.id.username)).getText().toString(),
                ((MaterialEditText) findViewById(R.id.password)).getText().toString(),
                new LogInListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (bmobUser != null) {
                            Toast.makeText(LoginActivity.this,
                                    String.format(getString(R.string.login_success),
                                            bmobUser.getUsername()), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    String.format(getString(R.string.login_failed),
                                            e.getMessage()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        return true;
    }
}
