package codehz.c4droidcodemanual;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;


public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        LinearLayout dl = (LinearLayout) findViewById(R.id.feedback_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            ((LinearLayout.LayoutParams) dl.getLayoutParams()).topMargin =
                    Resources.getSystem().getDimensionPixelSize(
                    Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        }
        setSupportActionBar((Toolbar) findViewById(R.id.feedback_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void SendFeedback() {
        new Feedback(((EditText) findViewById(R.id.feedback_edit_title)).getText().toString(),
                ((EditText) findViewById(R.id.feedback_edit_content)).getText().toString(), null)
                .save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(FeedbackActivity.this, "创建数据成功!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(FeedbackActivity.this, "创建数据失败:" + msg, Toast.LENGTH_LONG).show();
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
