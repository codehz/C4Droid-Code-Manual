package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kenny.snackbar.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobUser;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        final MaterialEditText username_met = (MaterialEditText) findViewById(R.id.username);
        final MaterialEditText password_met = (MaterialEditText) findViewById(R.id.password);
        final MaterialEditText email_met = (MaterialEditText) findViewById(R.id.email);
        final BmobUser user = new BmobUser();
        user.setUsername(username_met.getText().toString());
        user.setPassword(password_met.getText().toString());
        user.setEmail(email_met.getText().toString());
        AppApplication.Get().sign(user, new SignUpSaveCallback());
        return super.onOptionsItemSelected(item);
    }

    private class SignUpSaveCallback implements AppApplication.Callback<BmobUser> {
        @Override
        public void done(final BmobUser t, final Exception e) {
            if (t != null) {
                SnackBar.show(MainActivity.self, R.string.sign_up_success);
                finish();
            } else SnackBar.show(SignUpActivity.this, R.string.sign_up_failed);
        }
    }
}
