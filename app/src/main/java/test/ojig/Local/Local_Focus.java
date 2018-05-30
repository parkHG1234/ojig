package test.ojig.Local;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.ojig.Adapter.Buy_Adapter;
import test.ojig.Adapter.Local_Adapter;
import test.ojig.Buy.Buy;
import test.ojig.Model.Buy_Model;
import test.ojig.Model.Local_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

public class Local_Focus extends AppCompatActivity {
    private RecyclerView List_Local;
    private Local_Adapter local_adapter;
    private ArrayList<Local_Model> local_list;
    public static int graph_width = 0;
    private String Area = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_focus);

        Area = getIntent().getStringExtra("Area");

        List_Local = (RecyclerView)findViewById(R.id.list_local);

        Async async = new Async();
        async.execute(Area);
    }
    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Local_Focus.this);

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

                String result = http.HttpClient("Web_Ojig", "Local.jsp", params);
                parseredData = jsonParserList(result);

                local_list = new ArrayList<Local_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    local_list.add(new Local_Model(parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[0][2]));
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

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Local_Focus.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            local_adapter = new Local_Adapter(Local_Focus.this, local_list, 2);
            List_Local.setLayoutManager(layoutManager1);
            List_Local.setAdapter(local_adapter);

            asyncDialog.dismiss();
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
