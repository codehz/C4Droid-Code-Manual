package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;


public class ContentActivity extends AppCompatActivity {
    private MaterialViewPager viewPager;
    private Toolbar toolbar;
    private String mTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTarget = savedInstanceState.getString("target");
        setContentView(R.layout.activity_content);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        viewPager.setColor(savedInstanceState.getInt("color") + 0xff000000, 0);
        toolbar = viewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initPager();
    }

    private void initPager() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
