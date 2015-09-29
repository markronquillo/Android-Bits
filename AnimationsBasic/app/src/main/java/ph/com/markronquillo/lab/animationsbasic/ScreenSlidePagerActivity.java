package ph.com.markronquillo.lab.animationsbasic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ScreenSlidePagerActivity extends FragmentActivity {

    /**
     * The number of pages (wizard steps) to show in this demo
     */
    private static final int NUM_PAGES = 5;

    /**
     * The Pager widget, which handles animation and allows
     * swiping horizontally to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The page adapter, which provides the pages to the view pager widget
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

//        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super (fm);
        }

        @Override
        public Fragment getItem(int i) {
            ScreenSlidePageFragment s = new ScreenSlidePageFragment();
            switch(i){
                case 0:
                    s.setBgColor(Color.RED);
                    return s;
                case 1:
                    s.setBgColor(Color.BLACK);
                    return s;
                case 2:
                    s.setBgColor(Color.GREEN);
                    return s;
                case 3:
                    s.setBgColor(Color.BLUE);
                    return s;
                case 4:
                    s.setBgColor(Color.YELLOW);
                    return s;
                default:
                    return s;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight=  view.getHeight();

            if (position < -1)
            {
                // [-Infinity, -1)
                // this page is way off-screen to the left
                view.setAlpha(0);;
            }
            else if (position <= 1)
            {
                // [-1, 1]
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationY(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) / (1- MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1, +Infinity]
                // This page is way off-screen to the right
                view.setAlpha(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity, -1]
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleY(scaleFactor);
                view.setScaleX(scaleFactor);
            } else { // (1, +Infinity]
                // This page is way off-screen to the right
                view.setAlpha(0);
            }
        }
    }
}
