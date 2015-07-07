package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.listener.SaveListener;


public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        LinearLayoutCompat dl = (LinearLayoutCompat) findViewById(R.id.main);
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
                        Toast.makeText(FeedbackActivity.this, getString(R.string.feedback_success), Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(FeedbackActivity.this, String.format(getString(R.string.feedback_failed), msg), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback, menu);
        return true;
    }
}
