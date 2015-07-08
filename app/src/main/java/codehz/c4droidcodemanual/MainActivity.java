package codehz.c4droidcodemanual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<DataModel> modelList;
    private List<String> pagerList;
    private DataAdapter dataAdapter;
    private AppCompatTextView UsernameView;
    private AppCompatButton LoginOrLogoutButton, SignUpOrChangePasswordButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MaterialViewPager viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        pagerList = new ArrayList<>();
        pagerList.add("Recommend");
        pagerList.add("Pure C");
        viewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return pagerList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pagerList.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                return BaseFragment.newInstance(pagerList.get(position));
            }
        });
        viewPager.getViewPager().setOffscreenPageLimit(viewPager.getViewPager().getAdapter().getCount());
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
        viewPager.getViewPager().setCurrentItem(0);
        final Toolbar toolbar = viewPager.getToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.main_color));
        viewPager.getPagerTitleStrip().setBackgroundColor(getResources().getColor(R.color.main_color));

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        UsernameView = (AppCompatTextView) findViewById(R.id.main_username);
        LoginOrLogoutButton = (AppCompatButton) findViewById(R.id.main_login_or_logout);
        SignUpOrChangePasswordButton = (AppCompatButton) findViewById(R.id.main_sign_up_or_change_password);
        TryLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (((DrawerLayout) findViewById(R.id.drawer)).isDrawerOpen(GravityCompat.START)) {
            TryLogin();
            SnackBar.show(this,
                    String.format(getString(R.string.login_success),
                            AppApplication.Get().bmobUser.getUsername()), new SnackBarListener() {
                        @Override
                        public void onSnackBarStarted(Object o) {
                        }

                        @Override
                        public void onSnackBarFinished(Object o, boolean b) {
                            ((DrawerLayout) findViewById(R.id.drawer)).closeDrawers();
                        }
                    });
        }
    }

    public void TryLogin() {
        if (AppApplication.Get().bmobUser == null) {
            UsernameView.setText(R.string.not_login);
            LoginOrLogoutButton.setText(R.string.login);
            LoginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            SignUpOrChangePasswordButton.setText(R.string.sign_up);
            SignUpOrChangePasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                }
            });
        } else {
            UsernameView.setText(AppApplication.Get().bmobUser.getUsername());
            LoginOrLogoutButton.setText(R.string.logout);
            SignUpOrChangePasswordButton.setText(R.string.account);
        }
    }
    public void Feedback_Click(View view) {
        final Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }
}
