package test.ojig.promotion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-05-07.
 */

public class Promotion_Focus extends AppCompatActivity {
    ImageView Img;
    TextView Txt_Name, Txt_Title, Txt_Date, Txt_DateNum, Txt_Company, Txt_Address, Txt_Memo;

    String promotion_pk = "";
    String name = "";
    String title = "";
    String date = "";
    String datenum = "";
    String company_name = "";
    String company_address = "";
    String contents = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_focus);

        init();
        Intent intent1 = getIntent();
        promotion_pk = intent1.getStringExtra("Promotion_Pk");

        Async async = new Async();
        async.execute(promotion_pk);
    }
    public void init(){
        Img = (ImageView) findViewById(R.id.img);
        Txt_Name = (TextView) findViewById(R.id.txt_name);
        Txt_Title = (TextView) findViewById(R.id.txt_title);
        Txt_Date = (TextView) findViewById(R.id.txt_date);
        Txt_DateNum = (TextView) findViewById(R.id.txt_datenum);
        Txt_Company = (TextView) findViewById(R.id.txt_company);
        Txt_Address = (TextView) findViewById(R.id.txt_address);
        Txt_Memo = (TextView) findViewById(R.id.txt_memo);
    }

    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Promotion_Focus.this);

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
                String result = http.HttpClient("Web_Ojig", "Adult_Promotion_Focus.jsp", params);
                parseredData = jsonParserList(result);

                return "succed";
            } catch (Exception e) {
                Toast.makeText(Promotion_Focus.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            name = parseredData[0][1];
            title = parseredData[0][2];
            date = parseredData[0][3];
            datenum = parseredData[0][4];
            company_name = parseredData[0][5];
            company_address = parseredData[0][6];
            contents = parseredData[0][7];

            Txt_Name.setText(name);
            Txt_Title.setText(title);
            Txt_Date.setText("등급분류일 : "+ date);
            Txt_DateNum.setText("등급분류번호 : "+ datenum);
            Txt_Company.setText(company_name);
            Txt_Address.setText(company_address);
            Txt_Memo.setText(contents);

            try {
                String En_img = URLEncoder.encode(promotion_pk, "utf-8");
                Glide.with(Promotion_Focus.this).load("http://13.209.35.228:8080/Promotion/" + En_img + ".jpg")
                        .into(Img);
                Log.i("test", En_img);
            } catch (UnsupportedEncodingException e) {

            }

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8"};
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
