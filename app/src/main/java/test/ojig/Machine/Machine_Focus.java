package test.ojig.Machine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import test.ojig.Fragment.Machine_ViewPager_Fragment;
import test.ojig.Fragment.Sell_ViewPager_Fragment;
import test.ojig.R;
import test.ojig.Sell.Sell_Focus;
import test.ojig.Uitility.HttpClient;

public class Machine_Focus extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TimerTask myTask;
    private Timer timer;

    private TextView tv_title, tv_count, tv_address, tv_price, tv_company_name, tv_company_focus, tv_store_name, tv_memo, tv_address2, tv_count2, txt_buy_name, txt_user;
    private ImageView img_status;
    private LinearLayout img_call;
    private String machine_pk = "";
    private String user_pk="";
    private String title="";
    private String machine="";
    private String address="";
    private String price="";
    private String count="";
    private String memo="";
    private String status="";

    private String phone = "";
    private String pass = "";
    private String company_name = "";
    private String company_num = "";
    private String company_txt = "";
    private String company_focus = "";


    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_focus);

        init();
        machine_pk = getIntent().getStringExtra("Machine_Pk");


        Async async = new Async();
        async.execute(machine_pk);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void init() {

        img_status = (ImageView)findViewById(R.id.img_status);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_count = (TextView)findViewById(R.id.tv_count);
        tv_address = (TextView)findViewById(R.id.tv_address);
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_company_name = (TextView)findViewById(R.id.tv_company_name);
        tv_company_focus = (TextView)findViewById(R.id.tv_company_focus);
        tv_store_name = (TextView)findViewById(R.id.tv_store_name);
        tv_memo = (TextView)findViewById(R.id.tv_memo);
        txt_buy_name = (TextView)findViewById(R.id.txt_buy_name);
        txt_user = (TextView)findViewById(R.id.txt_user);

        tv_address2 = (TextView)findViewById(R.id.tv_address2);
        tv_count2 = (TextView)findViewById(R.id.tv_count2);
        img_call = (LinearLayout)findViewById(R.id.img_call);

    }

    public void setViewPager(String[][] data) {
        //프래그먼트 정의

        final int pageCount = data.length;
        Log.i("aaaaa",String.valueOf(pageCount));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
        final DotIndicator indicator = (DotIndicator) findViewById(R.id.indicator);
        // 도트 색 지정
        indicator.setSelectedDotColor(getResources().getColor(R.color.point));
        indicator.setUnselectedDotColor(Color.parseColor("#dadada"));
        indicator.bringToFront();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.machine_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(data.length - 1);
        ////////////
        indicator.setNumberOfItems(pageCount);


        //페이지 자동 전환 보류
        myTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = mViewPager.getCurrentItem();
                        if (currentPage >= pageCount - 1) mViewPager.setCurrentItem(0, true);
                        else mViewPager.setCurrentItem(currentPage + 1, true);
                        indicator.setSelectedItem((currentPage + 1 == pageCount) ? 0 : currentPage + 1, true);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }
    }


    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Machine_Focus.this);
        HttpClient http;
        String result;

        String[][] machine_parseredData;
        String[][] user_parseredData;
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

                result = http.HttpClient("Web_Ojig2", "machine_focus_select.jsp", params);
                machine_parseredData = jsonParserList(result);


                result = http.HttpClient("Web_Ojig2", "user_select.jsp", machine_parseredData[0][1]);
                user_parseredData = UserjsonParserList(result);

                Log.i("machine_pk",machine_pk);
                result = http.HttpClient("Web_Ojig2", "machine_focus_image_select.jsp", machine_pk);
                image_parseredData = ImagejsonParserList(result);

                return "succed";
            } catch (Exception e) {
                Toast.makeText(Machine_Focus.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            machine_pk = machine_parseredData[0][0];
            Log.i("machine_pk",machine_pk);
            user_pk = machine_parseredData[0][1];
            title = machine_parseredData[0][2];
            machine = machine_parseredData[0][3];
            address = machine_parseredData[0][4];
            price = machine_parseredData[0][5];
            count = machine_parseredData[0][6];
            memo = machine_parseredData[0][7];
            status = machine_parseredData[0][8];

            phone = user_parseredData[0][1];
            pass = user_parseredData[0][2];
            company_name = user_parseredData[0][3];
            company_num = user_parseredData[0][4];
            company_txt = user_parseredData[0][5];
            company_focus = user_parseredData[0][6];

            tv_title.setText(title);
            tv_address.setText(address);
            tv_price.setText(setPoint_rest(price) + "원");
            tv_count.setText(count +"대 보유");
            tv_memo.setText(memo);
            tv_address2.setText(address);
            tv_count2.setText(count);
            txt_buy_name.setText(machine);
            txt_user.setText(company_name);

            setViewPager(image_parseredData);

            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
                }
            });

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 Machine 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9"};
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


        public String[][] UserjsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 유저 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7"};
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


        public String[][] ImagejsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 이미지 내용", pRecvServerPage);
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
            if(data.length != 0) {
                pagercount = data.length;
            }else{
                pagercount = 1;
            }
        }

        @Override
        public Fragment getItem(int position) {

            //프래그 먼트 생성
            Fragment f = new Machine_ViewPager_Fragment();
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
    //금액 콤마 표현
    public String setPoint_rest(String point){
        DecimalFormat df = new DecimalFormat("#,##0");

        return df.format(Integer.parseInt(point))+"";
    }
}
