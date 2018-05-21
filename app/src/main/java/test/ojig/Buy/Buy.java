package test.ojig.Buy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.ojig.Adapter.Buy_Adapter;
import test.ojig.Model.Buy_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-04-21.
 */

public class Buy extends AppCompatActivity implements View.OnClickListener {
    private ImageView Img_Back;
    private EditText Edt_search;
    private RecyclerView List_Buy;

    private List<Buy_Model> buy_list;
    private ArrayList<Buy_Model> buy_models;
    private Buy_Adapter buy_adapter;
    private String User_Pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        User_Pk = getIntent().getStringExtra("User_Pk");

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
        Edt_search = (EditText) findViewById(R.id.et_search);
        List_Buy = (RecyclerView) findViewById(R.id.list_buy);

        Edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = Edt_search.getText().toString();
                search(text);
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.i("aaaaa",String.valueOf(v.getId()));

        switch (v.getId()) {
            case R.id.btn_write:
                Intent intent = new Intent(Buy.this, Buy_Write.class);
                intent.putExtra("User_Pk",User_Pk);
                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }
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
                String result = http.HttpClient("Web_Ojig2", "buy_select.jsp", params);
                parseredData = jsonParserList(result);

                buy_list = new ArrayList<Buy_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    buy_list.add(new Buy_Model(Buy.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6], parseredData[i][7], parseredData[i][8]));
                }
                buy_models = new ArrayList<Buy_Model>();
                buy_models.addAll(buy_list);

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
            buy_adapter = new Buy_Adapter(Buy.this, (ArrayList<Buy_Model>) buy_list, 2);
            List_Buy.setLayoutManager(layoutManager1);
            List_Buy.setAdapter(buy_adapter);

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
    }

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        buy_list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            buy_list.addAll(buy_models);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < buy_models.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (buy_models.get(i).getTitle().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    buy_list.add(buy_models.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        buy_adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
