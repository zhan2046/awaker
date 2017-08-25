package com.future.awaker.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.HttpRequestMethod;
import com.allenliu.versionchecklib.core.VersionParams;
import com.future.awaker.Account;
import com.future.awaker.BuildConfig;
import com.future.awaker.R;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.base.listener.onPageSelectedListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.ActivityHomeBinding;
import com.future.awaker.fir.Fir;
import com.future.awaker.fir.FirService;
import com.future.awaker.home.adapter.HomeAdapter;
import com.future.awaker.login.LoginActivity;
import com.future.awaker.setting.SettingActivity;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.ResUtils;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_HOME = 1000;
    private static final int BACK_TIME = 2000;

    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private MaterialSheetFab materialSheetFab;

    private int statusBarColor;
    private long firstTime = 0;

    private HomeClickListener homeClickListener = new HomeClickListener();
    private HomeAdapter homeAdapter;

    private ImageView userIconIv;
    private View userLoginLl;
    private TextView userNameTv;
    private TextView userOtherDescTv;
    private TextView userLoginTv;
    private TextView userRegisterTv;
    private TextView userExitLoginTv;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setTitle(R.string.app_name);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name,
                R.string.app_name);
        binding.drawerLayout.setDrawerListener(drawerToggle);

        initHeaderView();
        initNavigationViewMenu();

        setupFab();
        setupTabs();

        if (BuildConfig.BUILD_TYPE.equals("release")) {
            checkVersionUpdate();
        }
    }

    private void initNavigationViewMenu() {
        //set null for item icon tint，这样使得icon颜色恢复本色
        binding.navigationView.setItemIconTintList(null);

        ColorStateList csl = getResources().getColorStateList(R.color.navigation_menu_item_color);
        binding.navigationView.setItemTextColor(csl);

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.develop_desc:
                    SettingActivity.launch(this, SettingActivity.DEVELOP_DESC);
                    break;
                case R.id.cache_clear:
                    new MaterialDialog.Builder(this)
                            .title(R.string.cache_clear)
                            .content(R.string.cache_clear_desc)
                            .positiveText(R.string.confirm)
                            .negativeText(R.string.cancel)
                            .theme(Theme.LIGHT)
                            .negativeColorRes(R.color.text_color)
                            .positiveColorRes(R.color.text_color)
                            .onPositive((dialog, which) -> {
                                AwakerRepository.get().clearAll();
                                Toast.makeText(this, R.string.cache_clear_finish,
                                        Toast.LENGTH_SHORT).show();
                            })
                            .onNegative((dialog, which) -> dialog.dismiss())
                            .show();
                    break;
                case R.id.user_back:
                    SettingActivity.launch(this, SettingActivity.USER_BACK);
                    break;
                default:
                    Toast.makeText(this, R.string.future_desc, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    private void initHeaderView() {
        View headerView = binding.navigationView.getHeaderView(0);
        userIconIv = (ImageView) headerView.findViewById(R.id.icon_iv);
        userLoginTv = (TextView) headerView.findViewById(R.id.login_tv);
        userRegisterTv = (TextView) headerView.findViewById(R.id.register_tv);
        userLoginLl = headerView.findViewById(R.id.login_ll);
        userNameTv = (TextView) headerView.findViewById(R.id.name_tv);
        userOtherDescTv = (TextView) headerView.findViewById(R.id.other_tv);
        userExitLoginTv = (TextView) headerView.findViewById(R.id.exit_login_tv);

        updateAccountInfo();

        userLoginTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                LoginActivity.launch(HomeActivity.this, REQUEST_CODE_HOME,
                        LoginActivity.LOGIN_FLAG);
            }
        });

        userRegisterTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                LoginActivity.launch(HomeActivity.this, REQUEST_CODE_HOME,
                        LoginActivity.REGISTER_FLAG);
            }
        });

        userExitLoginTv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                exitLogin();
            }
        });
    }

    private void exitLogin() {
        if (Account.get().isLogin()) {
            new MaterialDialog.Builder(this)
                    .title(R.string.login_out)
                    .content(R.string.login_out_desc)
                    .positiveText(R.string.confirm)
                    .negativeText(R.string.cancel)
                    .theme(Theme.LIGHT)
                    .negativeColorRes(R.color.text_color)
                    .positiveColorRes(R.color.text_color)
                    .onPositive((dialog, which) -> {
                        Account.get().clearUserInfo();
                        updateAccountInfo();
                        Toast.makeText(this, R.string.login_out_finish, Toast.LENGTH_SHORT).show();

                        binding.drawerLayout.postDelayed(() -> {
                            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                                binding.drawerLayout.closeDrawer(GravityCompat.START);
                            }
                        }, 600);
                    })
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .show();

        } else {
            Toast.makeText(this, R.string.login_no, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkVersionUpdate() {
        VersionParams.Builder builder = new VersionParams.Builder()
                .setRequestMethod(HttpRequestMethod.GET)
                .setRequestUrl(Fir.VERSION_URL)
                .setService(FirService.class);
        AllenChecker.startVersionCheck(this, builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggleDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleDrawer() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void setupTabs() {
        List<String> titles = Arrays.asList(ResUtils.getString(R.string.home),
                ResUtils.getString(R.string.news),
                ResUtils.getString(R.string.video));

        homeAdapter = new HomeAdapter(getSupportFragmentManager(), titles);
        binding.viewpager.setAdapter(homeAdapter);
        binding.viewpager.setOffscreenPageLimit(titles.size());
        binding.tabs.setupWithViewPager(binding.viewpager);

        materialSheetFab.hideSheetThenFab();

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateFab(position);

                binding.toolbar.postDelayed(() -> {
                    Fragment fragment = homeAdapter.getCurrentFrag(position);
                    if (fragment instanceof onPageSelectedListener) {
                        ((onPageSelectedListener) fragment).onPageSelected(position);
                    }
                }, 300);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.toolbar.post(() -> {
            if (binding.viewpager != null) {
                binding.viewpager.setCurrentItem(HomeAdapter.NEW, false);
            }
        });
    }

    private void updateFab(int position) {
        switch (position) {
            case HomeAdapter.HOME:
                materialSheetFab.hideSheetThenFab();
                break;
            case HomeAdapter.NEW:
                materialSheetFab.hideSheetThenFab();
                break;
            case HomeAdapter.VIDEO:
                materialSheetFab.showFab();
                break;
        }
    }

    public void showFab(boolean isShow) {
        if (isShow) {
            materialSheetFab.showFab();
        } else {
            materialSheetFab.hideSheetThenFab();
        }
    }

    private void setupFab() {
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.colorAccent);

        materialSheetFab = new MaterialSheetFab<>(binding.fab, binding.fabSheet, binding.overlay,
                sheetColor, fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                super.onShowSheet();
            }

            @Override
            public void onHideSheet() {
                super.onHideSheet();
            }
        });

        binding.fabSheetItemUfo.setOnClickListener(homeClickListener);
        binding.fabSheetItemTheory.setOnClickListener(homeClickListener);
        binding.fabSheetItemSpirit.setOnClickListener(homeClickListener);
        binding.fabSheetItemFree.setOnClickListener(homeClickListener);
        binding.fabSheetItemNormal.setOnClickListener(homeClickListener);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private class HomeClickListener extends DebouncingOnClickListener {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.fab_sheet_item_ufo:
                    homeAdapter.setCat(Special.UFO);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_theory:
                    homeAdapter.setCat(Special.THEORY);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_spirit:
                    homeAdapter.setCat(Special.SPIRIT);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_free:
                    homeAdapter.setCat(Special.FREE);
                    materialSheetFab.hideSheet();
                    break;
                case R.id.fab_sheet_item_normal:
                    homeAdapter.setCat(Special.NORMAL);
                    materialSheetFab.hideSheet();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            if (materialSheetFab.isSheetVisible()) {
                materialSheetFab.hideSheet();
                return true;
            }

            if (secondTime - firstTime < BACK_TIME) {
                System.exit(0);
            } else {
                Toast.makeText(HomeActivity.this, R.string.exit_app, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_HOME) {
            if (resultCode == LoginActivity.RESULT_CODE_SUC) {
                updateAccountInfo();
            }
        }
    }

    private void updateAccountInfo() {
        String openId = Account.get().getOpenId();

        userLoginLl.setVisibility(TextUtils.isEmpty(openId) ? View.VISIBLE : View.GONE);
        userNameTv.setVisibility(TextUtils.isEmpty(openId) ? View.GONE : View.VISIBLE);
        userExitLoginTv.setVisibility(TextUtils.isEmpty(openId) ? View.GONE : View.VISIBLE);
        userOtherDescTv.setText(TextUtils.isEmpty(openId) ?
                R.string.launch_desc : R.string.other_desc_str);
        userIconIv.setImageResource(R.drawable.ic_gongjihui2);

        String userName = Account.get().getUserName();
        userNameTv.setText(userName);
    }
}
