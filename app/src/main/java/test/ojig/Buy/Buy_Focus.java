package test.ojig.Buy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.ojig.R;
import test.ojig.Uitility.HttpClient;

public class Buy_Focus  extends AppCompatActivity {
    ImageView Img_Back;
    ImageView Img_deal;
    LinearLayout img_call;
    TextView txt_name, txt_title, txt_amount, txt_address, txt_company_name, txt_company_focus, txt_buy_name, txt_user, txt_memo;
    TextView txt_amount2;

    private String buy_pk = "";
    private String category = "";
    private String user_pk = "";
    private String name = "";
    private String title = "";
    private String address = "";
    private String amount = "";
    private String memo = "";
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
        setContentView(R.layout.activity_buy_focus);

        init();
        buy_pk = getIntent().getStringExtra("Buy_Pk");

        Async async = new Async();
        async.execute(buy_pk);
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        Img_deal = (ImageView) findViewById(R.id.img_deal);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_amount2 = (TextView) findViewById(R.id.txt_amount2);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_company_name = (TextView) findViewById(R.id.txt_company_name);
        txt_company_focus = (TextView) findViewById(R.id.txt_company_focus);
        txt_buy_name = (TextView) findViewById(R.id.txt_buy_name);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_memo = (TextView) findViewById(R.id.txt_memo);
        img_call = (LinearLayout) findViewById(R.id.img_call);

    }

    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Buy_Focus.this);

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

                String result = http.HttpClient("Web_Ojig", "Buy_Focus.jsp", params);
                parseredData = jsonParserList(result);


                return "succed";
            } catch (Exception e) {
                Toast.makeText(Buy_Focus.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            buy_pk = parseredData[0][0];
            category = parseredData[0][1];
            user_pk = parseredData[0][2];
            name = parseredData[0][3];
            title = parseredData[0][4];
            address = parseredData[0][5];
            amount = parseredData[0][6];
            memo = parseredData[0][7];
            status = parseredData[0][8];



            HttpClient http = new HttpClient();
            result = http.HttpClient("Web_Ojig", "User.jsp", user_pk);
            parseredData = UserjsonParserList(result);



            phone = parseredData[0][1];
            pass = parseredData[0][2];
            company_name = parseredData[0][3];
            company_num = parseredData[0][4];
            company_focus = parseredData[0][5];
            company_txt = parseredData[0][6];

            txt_name.setText(name);
            txt_title.setText(title);
            txt_address.setText(address);
            txt_amount.setText(amount + "대 희망");
            txt_amount2.setText(amount);
            txt_memo.setText(memo);
            txt_buy_name.setText(name);
            txt_user.setText(company_name);
            txt_company_focus.setText(company_focus);
            txt_company_name.setText(company_name);
            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone)));
                }
            });
            if(status.equals("possible")){
                Glide.with(Buy_Focus.this).load(R.drawable.deal_possible)
                        .into(Img_deal);
            }
            else if(status.equals("ing")){
                Glide.with(Buy_Focus.this).load(R.drawable.deal_ing)
                        .into(Img_deal);
            }
            else if(status.equals("finish")){
                Glide.with(Buy_Focus.this).load(R.drawable.deal_finish)
                        .into(Img_deal);
            }

//            Txt_Memo.setText(contents);

//            try {
//                String En_img = URLEncoder.encode(buy_pk, "utf-8");
//                Glide.with(Buy_Focus.this).load("http://13.209.35.228:8080/Promotion/" + En_img + ".jpg")
//                        .into(Img);
//                Log.i("test", En_img);
//            } catch (UnsupportedEncodingException e) {
//
//            }

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
