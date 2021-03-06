package com.brstdev.ideafaris.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brstdev.ideafaris.R;
import com.brstdev.ideafaris.fragment.EditProfileFragment;
import com.brstdev.ideafaris.fragment.EditProfilePrivateFragment;
import com.brstdev.ideafaris.fragment.GlobalStoryFragmentPrivate;
import com.brstdev.ideafaris.fragment.HistoryFragment;
import com.brstdev.ideafaris.fragment.RecentStoryFragment;
import com.brstdev.ideafaris.fragment.TopRatedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brst-pc16 on 9/14/16.
 */
public class TabIconTextPrivate extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout1);
        // initToolbar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setupTabIcons();
            }
        });
        // tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(1);
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Profile");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Current Story");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.officelist, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Top Rated");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Recent Story");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hourglass, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("History");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clock, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new EditProfilePrivateFragment(), "Profile");
        adapter.addFrag(new GlobalStoryFragmentPrivate(), "Current Story");
        adapter.addFrag(new TopRatedFragment(), "Top");
        adapter.addFrag(new RecentStoryFragment(), "Recent");
        adapter.addFrag(new HistoryFragment(), "History");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


