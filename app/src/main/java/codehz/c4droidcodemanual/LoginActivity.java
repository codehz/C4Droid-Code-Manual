package codehz.c4droidcodemanual;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobUser;


public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MaterialEditText) findViewById(R.id.password))
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            tryLogin();
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void tryLogin() {
        final MaterialEditText username_met = (MaterialEditText) findViewById(R.id.username);
        final MaterialEditText password_met = (MaterialEditText) findViewById(R.id.password);
        boolean valid = true;
        if (!username_met.isCharactersCountValid()) {
            username_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!password_met.isCharactersCountValid()) {
            password_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!valid) return;
        AppApplication.Get().login(
                username_met.getText().toString(), password_met.getText().toString(),
                new LoginCallback());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        tryLogin();
        return true;
    }

    private class LoginCallback implements AppApplication.Callback<BmobUser> {
        @Override
        public void done(final BmobUser bmobUser, final Exception e) {
            if (bmobUser == null) {
                Snackbar.make(getWindow().getDecorView(),
                        String.format(getString(R.string.login_failed), e.getMessage()),
                        Snackbar.LENGTH_LONG).show();
                ((InputMethodManager) LoginActivity.this.getSystemService
                        (Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(findViewById(R.id.username).getWindowToken(), 0);
            } else finish();
        }
    }
}
