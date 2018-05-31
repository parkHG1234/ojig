package test.ojig.promotion;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import test.ojig.Adapter.Promotion_Adapter;
import test.ojig.Model.Promotion_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-05-05.
 */

public class Promotion extends AppCompatActivity {
    SharedPreferences preferences = null; //캐쉬 데이터 생성

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TimerTask myTask;
    private Timer timer;

    ImageView Img_Back;

    RecyclerView List_Promotion;
    ArrayList<Promotion_Model> promotion_models;
    Promotion_Adapter promotion_adapter;

    String[][] parseredData_banner;

    private String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        Category = preferences.getString("Category", ".");

        init();
        setImg_Back();

        Async async = new Async();
        async.execute();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        List_Promotion = (RecyclerView)findViewById(R.id.list_promotion);
    }
    public void setImg_Back(){
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
    public void setViewPager(){
        //프래그먼트 정의
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final DotIndicator indicator = (DotIndicator)findViewById(R.id.indicator);
        // 도트 색 지정
        indicator.setSelectedDotColor(getResources().getColor(R.color.point));
        indicator.setUnselectedDotColor(Color.parseColor("#dadada"));
        indicator.bringToFront();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager)findViewById(R.id.main1_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(parseredData_banner.length);
        ////////////
        final int pageCount = parseredData_banner.length;
        indicator.setNumberOfItems(pageCount);

        //페이지 자동 전환
        myTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = mViewPager.getCurrentItem();
                        if( currentPage >= pageCount - 1 ) mViewPager.setCurrentItem( 0, true );
                        else mViewPager.setCurrentItem( currentPage + 1, true );
                        indicator.setSelectedItem( ( currentPage + 1 == pageCount ) ? 0 : currentPage + 1, true );
                    }
                });
            }
        };
        timer = new Timer();
        //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
        timer.schedule(myTask, 500, 3000); // 5초후 첫실행, 3초마다 계속실행

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
        ProgressDialog asyncDialog = new ProgressDialog(Promotion.this);

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
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
                HttpClient http = new HttpClient();
                //배너
                String result1 = http.HttpClient("Web_Ojig", "Promotion_Banner.jsp", Category+"_banner");
                parseredData_banner = jsonParserList(result1);

                //아이템
                String result = http.HttpClient("Web_Ojig", "Promotion.jsp", Category);
                parseredData = jsonParserList(result);

                promotion_models = new ArrayList<Promotion_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    promotion_models.add(new Promotion_Model(Promotion.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4]));
                    Log.i("test123","ttt12");
                }
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            setViewPager();

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Promotion.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            promotion_adapter = new Promotion_Adapter(Promotion.this, promotion_models, parseredData.length);
            List_Promotion.setLayoutManager(layoutManager1);
            List_Promotion.setAdapter(promotion_adapter);

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5"};
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
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //프래그 먼트 생성
            Fragment f = new Promotion_ViewPage();
            Bundle bundle = new Bundle();


            //이미지 URL 동적 전송 ex) 1_1
            String promotion_pk =  parseredData_banner[position][0];
            bundle.putString("Promotion_Pk", promotion_pk);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return parseredData_banner.length;
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
