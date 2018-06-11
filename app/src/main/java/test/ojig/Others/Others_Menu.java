package test.ojig.Others;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import test.ojig.Adapter.Others_Menu_Adapter;
import test.ojig.Model.Others_Menu_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-06-11.
 */

public class Others_Menu extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences preferences = null; //캐쉬 데이터 생성

    private ImageView Img_Back;
    private RecyclerView List_Menu;

    private ArrayList<Others_Menu_Model> menu_models;
    private Others_Menu_Adapter menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_menu);

        init();

        Async async = new Async();
        async.execute();
    }

    public void init() {
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        List_Menu = (RecyclerView) findViewById(R.id.list_menu);
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
        String[][] parseredData;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //베스트 다운로드 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Ojig", "Others_Menu.jsp", params);
                parseredData = jsonParserList(result);

                menu_models = new ArrayList<Others_Menu_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    menu_models.add(new Others_Menu_Model(Others_Menu.this, parseredData[i][0], parseredData[i][1], parseredData[i][2]));
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

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Others_Menu.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            menu_adapter = new Others_Menu_Adapter(Others_Menu.this, menu_models);
            List_Menu.setLayoutManager(layoutManager1);
            List_Menu.setAdapter(menu_adapter);

        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3"};
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
