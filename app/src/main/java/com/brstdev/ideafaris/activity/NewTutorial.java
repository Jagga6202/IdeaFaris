package com.brstdev.ideafaris.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brstdev.ideafaris.LoginActivity;
import com.brstdev.ideafaris.MainAcivity;
import com.brstdev.ideafaris.R;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by brst-pc16 on 9/15/16.
 */
public class NewTutorial extends AppCompatActivity {
    Toolbar mToolbar;
    TextView toolbar_title, skip;
    ViewPager viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtutorial);
        ImageAdapter adapter = new ImageAdapter(this);

        skip = (TextView) findViewById(R.id.skip);
        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    skip.setText("Skip");
                } else if (position == 1) {
                    skip.setText("Skip");
                } else if (position == 2) {
                    skip.setText("Done");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTutorial.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initToolbar();

    }

    void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Tutorial");

        mToolbar.setNavigationIcon(R.drawable.arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class ImageAdapter extends PagerAdapter {
        Context context;
        private int[] GalImages = new int[]{
                R.drawable.tutorial,
                R.drawable.tutorial,
                R.drawable.tutorial
        };

        ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return GalImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
            //imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(GalImages[position]);

            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
