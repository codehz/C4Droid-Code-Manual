package codehz.c4droidcodemanual;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends AppCompatActivity
        implements MaterialViewPager.MaterialViewPagerListener {
    private MaterialViewPager viewPager;
    private Toolbar toolbar;
    private List<CodeCategories> pagerList;
    private AppCompatTextView UsernameView;
    private AppCompatButton LoginOrLogoutButton, SignUpOrChangePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = viewPager.getToolbar();
        UsernameView = (AppCompatTextView) findViewById(R.id.main_username);
        LoginOrLogoutButton = (AppCompatButton) findViewById(R.id.main_login_or_logout);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initPager();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        SignUpOrChangePasswordButton =
                (AppCompatButton) findViewById(R.id.main_sign_up_or_change_password);
        TryLogin();
    }

    private void initPager() {
        final BmobQuery<CodeCategories> codeCategoriesBmobQuery = new BmobQuery<>();
        codeCategoriesBmobQuery.order("Order");
        codeCategoriesBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        codeCategoriesBmobQuery.findObjects(this, new CodeCategoriesFindListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (((DrawerLayout) findViewById(R.id.drawer)).isDrawerOpen(GravityCompat.START)) {
            TryLogin();
            if (AppApplication.Get().bmobUser == null) return;
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

    @Override
    public HeaderDesign getHeaderDesign(int page) {
        if (Build.VERSION.SDK_INT >= 21)
            MainActivity.this.setTaskDescription(
                    new ActivityManager.TaskDescription(
                            String.format(
                                    getString(R.string.main_title),
                                    pagerList.get(page).getNameEN()),
                            BitmapFactory.decodeResource(
                                    getResources(), R.mipmap.ic_launcher),
                            pagerList.get(page).getThemeColor() + 0xFF000000));
        //viewPager.setColor(pagerList.get(page).getThemeColor() + 0xFF000000, 0);
        return HeaderDesign.fromColorAndUrl(pagerList.get(page).getThemeColor() + 0xff000000,
                pagerList.get(page).getThemePic().getFileUrl(this));
    }

    private class CodeCategoriesFindListener extends FindListener<CodeCategories> {
        @Override
        public void onSuccess(List<CodeCategories> list) {
            pagerList = list;
            /*for (int i = 0; i < list.size(); i++) {
                BmobQuery<CodeRepositories> codeRepositoriesBmobQuery = new BmobQuery<>();
                codeRepositoriesBmobQuery.addWhereEqualTo("Category", list.get(i));
                codeRepositoriesBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                AppApplication.Get().repositories.add(i, null);
                codeRepositoriesBmobQuery.findObjects(MainActivity.this,
                        new CodeRepositoriesFindListener(i));
            }*/
            for (int i = 0; i < list.size(); i++) {
                AppApplication.Get().repositories.add(i, null);
            }
            BmobQuery<CodeRepositories> codeRepositoriesBmobQuery = new BmobQuery<>();
            codeRepositoriesBmobQuery.include("Category");
            codeRepositoriesBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
            codeRepositoriesBmobQuery.findObjects(MainActivity.this,
                    new CodeRepositoriesFindListener());
            viewPager.getViewPager().setAdapter(
                    new BaseFragmentStatePagerAdapter(getSupportFragmentManager()));
            viewPager.setMaterialViewPagerListener(MainActivity.this);
            if (Build.VERSION.SDK_INT >= 21)
                MainActivity.this.setTaskDescription(
                        new ActivityManager.TaskDescription(
                                String.format(
                                        getString(R.string.main_title),
                                        pagerList.get(0).getNameEN()),
                                BitmapFactory.decodeResource(
                                        getResources(), R.mipmap.ic_launcher),
                                pagerList.get(0).getThemeColor() + 0xFF000000));
            viewPager.setColor(pagerList.get(0).getThemeColor() + 0xFF000000, 0);
            viewPager.setImageUrl(pagerList.get(0).getThemePic().getFileUrl(MainActivity.this),
                    0);
            viewPager.getViewPager().setOffscreenPageLimit(
                    viewPager.getViewPager().getAdapter().getCount());
            viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
            viewPager.getViewPager().setCurrentItem(0);
        }

        @Override
        public void onError(int i, String s) {
            SnackBar.show(MainActivity.this,
                    String.format(getString(R.string.error_when_query),
                            s));
        }
    }

    private class CodeRepositoriesFindListener extends FindListener<CodeRepositories> {

        @Override
        public void onSuccess(List<CodeRepositories> list) {
            synchronized (AppApplication.Get().repositories) {
                Log.d("MainActivity", "Data:" + list.size());
                for (int i = 0; i < pagerList.size(); i++) {
                    AppApplication.Get().repositories.set(i, new ArrayList<CodeRepositories>());
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < pagerList.size(); j++) {
                        if (list.get(i).getCategory().getOrder().intValue() ==
                                pagerList.get(j).getOrder().intValue()) {
                            AppApplication.Get().repositories.get(j).add(list.get(i));
                            Log.d("MainActivity", String.format("Resolve type(%s):%s",
                                    pagerList.get(j).getNameEN(), list.get(i).getTitle()));
                            break;
                        }
                    }
                }
                AppApplication.Get().repositories.notifyAll();
            }
        }

        @Override
        public void onError(int errno, String s) {
            synchronized (AppApplication.Get().repositories) {
                for (int i = 0; i < pagerList.size(); i++) {
                    AppApplication.Get().repositories.set(i, new ArrayList<CodeRepositories>());
                }
                AppApplication.Get().repositories.notifyAll();
                SnackBar.show(MainActivity.this,
                        String.format(getString(R.string.error_when_query),
                                s));
            }
        }
    }

    private class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public BaseFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagerList.get(position).getNameEN();
        }

        @Override
        public Fragment getItem(int position) {
            return BaseFragment.newInstance(position, pagerList.get(position).getThemeColor());
        }
    }
}
