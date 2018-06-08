package test.ojig.Uitility;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.ojig.Fragment.FullImage_ViewPager_Fragment;
import test.ojig.R;

public class FullScreenImage extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String sell_Pk="";
    private String Image="";
    private int PageCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        sell_Pk = getIntent().getStringExtra("Pk");
        Image = getIntent().getStringExtra("Image");
        PageCount = Integer.parseInt(getIntent().getStringExtra("PageCount"));

        setViewPager();
    }

    public void setViewPager() {
        //프래그먼트 정의

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final DotIndicator indicator = (DotIndicator) findViewById(R.id.indicator);
        // 도트 색 지정
        indicator.setSelectedDotColor(getResources().getColor(R.color.point));
        indicator.setUnselectedDotColor(Color.parseColor("#dadada"));
        indicator.bringToFront();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.fullscreen_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(3);
        ////////////
        indicator.setNumberOfItems(PageCount);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelectedItem(mViewPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //프래그 먼트 생성
            Fragment f = new FullImage_ViewPager_Fragment();
            Bundle bundle = new Bundle();

            //이미지 URL 동적 전송 ex) 1_1
            String Image_txt = sell_Pk+"_"+position;
            bundle.putString("Image", Image_txt);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return PageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
