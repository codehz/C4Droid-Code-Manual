package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;


public class ContentActivity extends AppCompatActivity {
    private MaterialViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int category = getIntent().getIntExtra("category", 0);
        int num = getIntent().getIntExtra("num", 0);
        setContentView(R.layout.activity_content);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        viewPager.setColor(getIntent().getIntExtra("color", 0) + 0xff000000, 0);
        viewPager.getViewPager().setAdapter(
                new ContentFragmentStatePagerAdapter(getSupportFragmentManager(), category, num));
        viewPager.getViewPager().setCurrentItem(0);
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

    private class ContentFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private int category, num;

        public ContentFragmentStatePagerAdapter(FragmentManager fm, int category, int num) {
            super(fm);
            this.category = category;
            this.num = num;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.content);
        }

        @Override
        public Fragment getItem(int position) {
            return ContentFragment.newInstance(
                    AppApplication.Get().repositories.get(category).get(num));
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
