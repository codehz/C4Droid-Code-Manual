package codehz.c4droidcodemanual;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class EditActivity extends BaseActivity {
    private int category, num;
    private EditCardView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getIntent().getIntExtra("category", 0);
        num = getIntent().getIntExtra("num", 0);
        setContentView(R.layout.activity_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditCardView) findViewById(R.id.main_edit);

        editText.setContent(AppApplication.Get().repositories.get(category).get(num).getContent());
        editText.decode();

        setStatusBar();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBar() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.main_color));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_commit) {
            CodeRepositories temp = AppApplication.Get().repositories.get(category).get(num);
            temp.setContent(editText.encode());
            temp.update(AppApplication.Get());
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
