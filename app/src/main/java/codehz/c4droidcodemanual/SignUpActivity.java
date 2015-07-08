package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.kenny.snackbar.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;


public class SignUpActivity extends AppCompatActivity {
    private final Pattern emailFormat =
            Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final MaterialEditText email_met = (MaterialEditText) findViewById(R.id.email);
        email_met.addValidator(new METValidator(getString(R.string.email_format_error)) {
            @Override
            public boolean isValid(@NonNull CharSequence charSequence, boolean b) {
                return emailFormat.matcher(charSequence).matches();
            }
        });
        email_met.setAutoValidate(true);
        ((MaterialEditText) findViewById(R.id.password))
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            trySign();
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    private void trySign() {
        final MaterialEditText username_met = (MaterialEditText) findViewById(R.id.username);
        final MaterialEditText password_met = (MaterialEditText) findViewById(R.id.password);
        final MaterialEditText email_met = (MaterialEditText) findViewById(R.id.email);
        boolean valid = true;
        if (!username_met.isCharactersCountValid()) {
            username_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!password_met.isCharactersCountValid()) {
            password_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!email_met.isCharactersCountValid()) {
            password_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!valid || !email_met.isCharactersCountValid()) return;
        final BmobUser user = new BmobUser();
        user.setUsername(username_met.getText().toString());
        user.setPassword(password_met.getText().toString());
        user.setEmail(email_met.getText().toString());
        AppApplication.Get().sign(user, new SignUpSaveCallback());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        trySign();
        return super.onOptionsItemSelected(item);
    }

    private class SignUpSaveCallback implements AppApplication.Callback<BmobUser> {
        @Override
        public void done(final BmobUser t, final Exception e) {
            if (t != null) finish();
            else SnackBar.show(SignUpActivity.this, R.string.sign_up_failed);
        }
    }
}
