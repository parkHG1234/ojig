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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        sell_Pk = getIntent().getStringExtra("Pk");

        Async async = new Async();
        async.execute(sell_Pk);
    }

    public void setViewPager(String[][] data) {
        //프래그먼트 정의

        final int pageCount = data.length;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
        final DotIndicator indicator = (DotIndicator) findViewById(R.id.indicator);
        // 도트 색 지정
        indicator.setSelectedDotColor(getResources().getColor(R.color.point));
        indicator.setUnselectedDotColor(Color.parseColor("#dadada"));
        indicator.bringToFront();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.fullscreen_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(data.length);
        ////////////
        indicator.setNumberOfItems(pageCount);

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


    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(FullScreenImage.this);
        HttpClient http;
        String result;

        String[][] image_parseredData;

        @Override
        protected void onPreExecute() {
            http = new HttpClient();
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("wait...");
            // show dialog
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //베스트 다운로드 데이터 셋팅
                result = http.HttpClient("Web_Ojig2", "sell_focus_image_select.jsp", sell_Pk);
                image_parseredData = ImagejsonParserList(result);

                return "succed";
            } catch (Exception e) {
                Toast.makeText(FullScreenImage.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setViewPager(image_parseredData);

            asyncDialog.dismiss();
        }

        public String[][] ImagejsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 FullImage 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2"};
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for (int i = 0; i < jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    for (int j = 0; j < jsonName.length; j++) {
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[][] data;
        int pagercount;

        public SectionsPagerAdapter(FragmentManager fm, String[][] data) {
            super(fm);
            this.data = data;
            if (data.length == 0) {
                pagercount = 1;
            } else {
                pagercount = data.length;
            }
        }

        @Override
        public Fragment getItem(int position) {

            //프래그 먼트 생성
            Fragment f = new FullImage_ViewPager_Fragment();
            Bundle bundle = new Bundle();

            //이미지 URL 동적 전송 ex) 1_1
            String Image_txt;
            if (data.length == 0) {
                Image_txt = "";
            } else {
                Image_txt = data[position][1];
            }
            bundle.putString("Image", Image_txt);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return pagercount;
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
