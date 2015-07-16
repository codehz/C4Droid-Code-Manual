package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class EditActivity extends BaseActivity {
    private int category, num;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getIntent().getIntExtra("category", 0);
        num = getIntent().getIntExtra("num", 0);
        setContentView(R.layout.activity_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditText) findViewById(R.id.main_edit);
        editText.setText(AppApplication.Get().repositories.get(category).get(num).getContent());
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
            temp.setContent(editText.getText().toString());
            temp.update(AppApplication.Get());
        }

        return super.onOptionsItemSelected(item);
    }
}
