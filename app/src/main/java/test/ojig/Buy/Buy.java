package test.ojig.Buy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import test.ojig.Adapter.Buy_Adapter;
import test.ojig.Model.Buy_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-04-21.
 */

public class Buy extends AppCompatActivity {
    Button btn_write;
    RecyclerView List_Buy;

    ArrayList<Buy_Model> buy_models;
    Buy_Adapter buy_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        init();

        Async async = new Async();
        async.execute();
    }
    public void init(){
        btn_write = (Button)findViewById(R.id.btn_write);
        List_Buy = (RecyclerView)findViewById(R.id.list_buy);
    }
    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Buy.this);

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
                //String result = http.HttpClient("Web_Blah", "Best_All.jsp", params);
                //parseredData = jsonParserList(result);

                buy_models = new ArrayList<Buy_Model>();
//                for (int i = 0; i < parseredData.length; i++) {
//                    buy_models.add(new Buy_Model(Buy.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6]));
//                }
                buy_models.add(new Buy_Model(Buy.this, "1", "1", "글쓴이 제목영역입니다.\n두줄가지 가능합니다.", "정성을 다해 모십니다.\n테스트입니다.", "서울", "20", "황금성게임기", "wait"));
                buy_models.add(new Buy_Model(Buy.this, "1", "1", "글쓴이 제목영역입니다.\n두줄가지 가능합니다.", "정성을 다해 모십니다.\n테스트입니다.", "서울", "20", "황금성게임기", "wait"));
                return "succed";
            } catch (Exception e) {
                Toast.makeText(Buy.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Buy.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);
            //베스트 무료체험 어댑터 셋팅
            buy_adapter = new Buy_Adapter(Buy.this, buy_models, 2);
            List_Buy.setLayoutManager(layoutManager1);
            List_Buy.setAdapter(buy_adapter);

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6","msg7"};
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
}
