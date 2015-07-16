package codehz.c4droidcodemanual;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.listener.SaveListener;


public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SendFeedback() {
        MaterialEditText title_met = (MaterialEditText) findViewById(R.id.edit_title);
        MaterialEditText content_met = (MaterialEditText) findViewById(R.id.edit_content);
        boolean valid = true;
        if (!title_met.isCharactersCountValid()) {
            title_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!content_met.isCharactersCountValid()) {
            content_met.setError(getString(R.string.TheLengthOfYourInputDoesNotMeetTheRequirements));
            valid = false;
        }
        if (!valid) {
            return;
        }
        new Feedback(title_met.getText().toString(),
                content_met.getText().toString(), null)
                .save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        SnackBar.show(FeedbackActivity.this, R.string.feedback_success, new SnackBarListener() {
                            @Override
                            public void onSnackBarStarted(Object o) {
                                ((InputMethodManager) FeedbackActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE))
                                        .hideSoftInputFromWindow(findViewById(R.id.edit_title).getWindowToken(), 0);
                            }

                            @Override
                            public void onSnackBarFinished(Object o, boolean b) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        SnackBar.show(FeedbackActivity.this, String.format(getString(R.string.feedback_failed), msg));
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.feedback_menu_send:
                SendFeedback();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SnackBar.cancelSnackBars(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.feedback, menu);
        return true;
    }
}
