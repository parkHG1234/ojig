package test.ojig.Store;

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

import java.text.DecimalFormat;

import test.ojig.Machine.Machine_Focus;
import test.ojig.R;
import test.ojig.Uitility.FullScreenVideoActivity;
import test.ojig.Uitility.HttpClient;
import test.ojig.Uitility.convertHangul;

public class Store_Focus extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout img_call;
    private TextView tv_title, tv_type, tv_address, tv_price, tv_deposit, tv_rental, tv_company_name, tv_company_focus, txt_user, txt_memo;
    private LinearLayout layout_deal, layout_rent;
    private ImageView img_video, player, img_status;

    private String store_pk = "";
    private String category = "";
    private String type = "";
    private String user_pk = "";
    private String title = "";
    private String space = "";
    private String layer = "";
    private String price = "";
    private String deposit = "";
    private String rental = "";
    private String address = "";
    private String memo = "";
    private String status = "";
    private String video = "";

    private String phone = "";
    private String pass = "";
    private String company_name = "";
    private String company_num = "";
    private String company_txt = "";
    private String company_focus = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_focus);

        init();
        store_pk = getIntent().getStringExtra("Store_Pk");

        Async async = new Async();
        async.execute(store_pk);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    public void init() {

        layout_deal = (LinearLayout) findViewById(R.id.layout_deal);
        layout_rent = (LinearLayout) findViewById(R.id.layout_rent);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        tv_rental = (TextView) findViewById(R.id.tv_rental);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_company_focus = (TextView) findViewById(R.id.tv_company_focus);
        img_call = (LinearLayout) findViewById(R.id.img_call);
        player = (ImageView) findViewById(R.id.player);
        img_status = (ImageView) findViewById(R.id.img_status);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_memo = (TextView) findViewById(R.id.txt_memo);
    }

    public void setVideo_Img() {
        img_video = (ImageView) findViewById(R.id.img_video);
        if(video.equals("true")){
            player.setVisibility(View.VISIBLE);
            Glide.with(this).load("http://13.209.35.228:8080/Video_Store/thumbnail/" + store_pk + ".jpg")
                    .into(img_video);
        }
        else{
            player.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.basic_mainlist)
                    .into(img_video);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
            case R.id.player:
                player.setVisibility(View.GONE);
                if(video.equals("true")){
                    Intent intent = new Intent(Store_Focus.this, FullScreenVideoActivity.class);
                    intent.putExtra("Url","http://13.209.35.228:8080/Video_Store/" + store_pk + ".mp4");
                    startActivity(intent);
                }
                else{
                }


                break;
        }
    }

    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Store_Focus.this);
        HttpClient http;
        String result;

        String[][] store_parseredData;
        String[][] user_parseredData;

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

                result = http.HttpClient("Web_Ojig", "Store_Focus.jsp", params);
                store_parseredData = jsonParserList(result);


                result = http.HttpClient("Web_Ojig", "User.jsp", store_parseredData[0][3]);
                user_parseredData = UserjsonParserList(result);


                return "succed";
            } catch (Exception e) {
                Toast.makeText(Store_Focus.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//
            store_pk = store_parseredData[0][0];
            category = store_parseredData[0][1];
            type = store_parseredData[0][2];
            user_pk = store_parseredData[0][3];
            title = store_parseredData[0][4];
            space = store_parseredData[0][5];
            layer = store_parseredData[0][6];
            price = store_parseredData[0][7];
            deposit = store_parseredData[0][8];
            rental = store_parseredData[0][9];
            address = store_parseredData[0][10];
            memo = store_parseredData[0][11];
            status = store_parseredData[0][12];
            video = store_parseredData[0][13];

            phone = user_parseredData[0][1];
            pass = user_parseredData[0][2];
            company_name = user_parseredData[0][3];
            company_num = user_parseredData[0][4];
            company_txt = user_parseredData[0][5];
            company_focus = user_parseredData[0][6];


            if (type.equals("deal")) {
                tv_type.setText("매매");
                layout_deal.setVisibility(View.VISIBLE);
                layout_rent.setVisibility(View.GONE);
                convertHangul convertHangul = new convertHangul();
                tv_price.setText(convertHangul.convertHangul(price)+"원");
            } else if (type.equals("rent")) {
                tv_type.setText("임대");
                layout_deal.setVisibility(View.GONE);
                layout_rent.setVisibility(View.VISIBLE);
                tv_price.setText(deposit + " / " + rental);
            }

            if(status.equals("possible")){
                Glide.with(Store_Focus.this).load(R.drawable.deal_possible)
                        .into(img_status);
            }
            else if(status.equals("ing")){
                Glide.with(Store_Focus.this).load(R.drawable.deal_ing)
                        .into(img_status);
            }
            else if(status.equals("finish")){
                Glide.with(Store_Focus.this).load(R.drawable.deal_finish)
                        .into(img_status);
            }

            tv_title.setText(title);
            tv_address.setText(address);
            tv_deposit.setText(deposit);
            tv_rental.setText(rental);
            tv_company_name.setText(company_name);
            tv_company_focus.setText(company_txt + company_focus);

            txt_user.setText(company_name);
            txt_memo.setText(memo);

            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
                }
            });
            setVideo_Img();

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9", "msg10", "msg11", "msg12", "msg13", "msg14"};
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
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
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
