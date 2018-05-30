package test.ojig.Sell;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import test.ojig.Buy.Buy_Focus;
import test.ojig.Fragment.Sell_ViewPager_Fragment;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;
import test.ojig.Uitility.convertHangul;

public class Sell_Focus extends AppCompatActivity implements View.OnClickListener {
    private ImageView Img_Back, img_status;
    private LinearLayout img_call;
    private TextView txt_name, txt_title, txt_amount, txt_address, txt_company_name, txt_company_focus, txt_sell_name, txt_user, txt_memo, txt_amount2, txt_buy_name, txt_price;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TimerTask myTask;
    private Timer timer;


    private String sell_pk = "";
    private String category = "";
    private String user_pk = "";
    private String name = "";
    private String title = "";
    private String price = "";
    private String address = "";
    private String amount = "";
    private String memo = "";
    private String img = "";
    private String video = "";
    private String status = "";

    private String phone = "";
    private String pass = "";
    private String company_name = "";
    private String company_num = "";
    private String company_txt = "";
    private String company_focus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_focus);

        init();
        sell_pk = getIntent().getStringExtra("Sell_Pk");

        Async async = new Async();
        async.execute(sell_pk);
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
        mViewPager = (ViewPager) findViewById(R.id.sell_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(data.length);
        ////////////
        indicator.setNumberOfItems(pageCount);

//        if (pageCount > 1) {
//            //페이지 자동 전환 보류
//            myTask = new TimerTask() {
//                public void run() {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            int currentPage = mViewPager.getCurrentItem();
//                            if (currentPage >= pageCount - 1) mViewPager.setCurrentItem(0, true);
//                            else mViewPager.setCurrentItem(currentPage + 1, true);
//                            indicator.setSelectedItem((currentPage + 1 == pageCount) ? 0 : currentPage + 1, true);
//                        }
//                    });
//                }
//            };
//            timer = new Timer();
//            //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
//            timer.schedule(myTask, 500, 3000); // 5초후 첫실행, 3초마다 계속실행
//
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

    public void init() {
        Img_Back = (ImageView) findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        img_status = (ImageView) findViewById(R.id.img_status);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_price = (TextView)findViewById(R.id.txt_price);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_amount2 = (TextView) findViewById(R.id.txt_amount2);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_company_name = (TextView) findViewById(R.id.txt_company_name);
        txt_company_focus = (TextView) findViewById(R.id.txt_company_focus);
        txt_sell_name = (TextView) findViewById(R.id.txt_sell_name);
        txt_buy_name = (TextView)findViewById(R.id.txt_buy_name);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_memo = (TextView) findViewById(R.id.txt_memo);
        img_call = (LinearLayout) findViewById(R.id.img_call);

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
        ProgressDialog asyncDialog = new ProgressDialog(Sell_Focus.this);
        HttpClient http;
        String result;

        String[][] sell_parseredData;
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

                result = http.HttpClient("Web_Ojig2", "sell_focus_select.jsp", params);
                sell_parseredData = jsonParserList(result);


                result = http.HttpClient("Web_Ojig2", "user_select.jsp", sell_parseredData[0][2]);
                user_parseredData = UserjsonParserList(result);


                result = http.HttpClient("Web_Ojig2", "sell_focus_image_select.jsp", sell_pk);
                image_parseredData = ImagejsonParserList(result);


                return "succed";
            } catch (Exception e) {
                Toast.makeText(Sell_Focus.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            sell_pk = sell_parseredData[0][0];
            category = sell_parseredData[0][1];
            user_pk = sell_parseredData[0][2];
            name = sell_parseredData[0][3];
            title = sell_parseredData[0][4];
            price = sell_parseredData[0][5];
            address = sell_parseredData[0][6];
            amount = sell_parseredData[0][7];
            memo = sell_parseredData[0][8];
            status = sell_parseredData[0][9];


            phone = user_parseredData[0][1];
            pass = user_parseredData[0][2];
            company_name = user_parseredData[0][3];
            company_num = user_parseredData[0][4];
            company_txt = user_parseredData[0][5];
            company_focus = user_parseredData[0][6];


            setViewPager(image_parseredData);


            if(status.equals("possible")){
                Glide.with(Sell_Focus.this).load(R.drawable.deal_possible)
                        .into(img_status);
            }
            else if(status.equals("ing")){
                Glide.with(Sell_Focus.this).load(R.drawable.deal_ing)
                        .into(img_status);
            }
            else if(status.equals("finish")){
                Glide.with(Sell_Focus.this).load(R.drawable.deal_finish)
                        .into(img_status);
            }

            txt_name.setText(name);
            txt_buy_name.setText(name);
            txt_title.setText(title);
            txt_address.setText(address);
            convertHangul convertHangul = new convertHangul();
            txt_price.setText(convertHangul.convertHangul(price)+"원");
            txt_amount.setText(amount + "대 판매");
            txt_amount2.setText(amount);
            txt_memo.setText(memo);
            txt_sell_name.setText(address);
            txt_user.setText(company_name);
            txt_company_focus.setText(company_txt+" "+company_focus);
            txt_company_name.setText(company_name);
            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
                }
            });


//            Txt_Memo.setText(contents);


            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 Sell 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9", "msg10"};
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
            Log.i("서버에서 받은 Sell_유저 내용", pRecvServerPage);
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
            Log.i("서버에서 받은 Sell_이미지 내용", pRecvServerPage);
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
            Fragment f = new Sell_ViewPager_Fragment();
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
