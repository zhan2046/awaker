package com.future.awaker.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.future.awaker.R;
import com.future.awaker.base.ViewModelHolder;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.databinding.ActivityHomeBinding;
import com.future.awaker.news.NewViewModel;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeActivity extends AppCompatActivity {

    public static final String MAIN_VIEW_MODEL_TAG = "MAIN_VIEW_MODEL_TAG";


    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    private NewViewModel newViewModel;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name,
                R.string.app_name);
        binding.drawerLayout.setDrawerListener(drawerToggle);

        setupFab();
        setupTabs();
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
        List<String> titles = Arrays.asList("资讯","视频精选");

        newViewModel = findOrCreateViewModel();
        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), titles);
        adapter.setViewModel(newViewModel);
        binding.viewpager.setAdapter(adapter);

        updateFab(binding.viewpager.getCurrentItem());
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateFab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateFab(int position) {
        switch (position) {
            case 0:
                materialSheetFab.showFab();
                break;
            case 1:
                materialSheetFab.hideSheetThenFab();
                break;
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
                statusBarColor = getStatusBarColor();
                setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark2));
            }

            @Override
            public void onHideSheet() {
                super.onHideSheet();
                setStatusBarColor(statusBarColor);
            }
        });
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    private NewViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<NewViewModel> retainedViewModel = (ViewModelHolder<NewViewModel>) getSupportFragmentManager()
                .findFragmentByTag(MAIN_VIEW_MODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewModel() != null) {
            return retainedViewModel.getViewModel();
        } else {
            NewViewModel newViewModel = new NewViewModel(NewRepository.get());
            ViewModelHolder viewModelHolder = ViewModelHolder.createContainer(newViewModel);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(viewModelHolder, MAIN_VIEW_MODEL_TAG);
            transaction.commit();
            return newViewModel;
        }
    }
}
