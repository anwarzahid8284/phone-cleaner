package com.example.phonerepaire.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.phonerepaire.Adpatar.FragmentViewPager;
import com.example.phonerepaire.Fragments.HomeFragment;
import com.example.phonerepaire.Fragments.MoreFragment;
import com.example.phonerepaire.Fragments.SystemInfoFragment;
import com.example.phonerepaire.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    FragmentViewPager fragmentViewPager;
    List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        fragmentList = new ArrayList<>();
        loadViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void loadViewPager() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SystemInfoFragment());
        fragmentList.add(new MoreFragment());
        fragmentViewPager = new FragmentViewPager(fragmentList, getSupportFragmentManager());
        viewPager.setAdapter(fragmentViewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home_menu_id).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.info_menu_id).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.more_menu_id).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_id:
                viewPager.setCurrentItem(0);
                break;
            case R.id.info_menu_id:
                viewPager.setCurrentItem(1);
                break;
            case R.id.more_menu_id:
                viewPager.setCurrentItem(2);
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            viewPager.setCurrentItem(0, true);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}