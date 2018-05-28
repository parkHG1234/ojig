package test.ojig.Machine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.ojig.Adapter.Machine_Adapter;
import test.ojig.Model.Machine_Model;
import test.ojig.R;
import test.ojig.Store.Store_Write;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-04-21.
 */

public class Machine extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences preferences = null; //캐쉬 데이터 생성
    private EditText Edt_search;
    private RecyclerView List_Machine;
//
    private List<Machine_Model> machine_list;
    private ArrayList<Machine_Model> machine_models;
    private Machine_Adapter machine_adapter;
    private String User_Pk;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

                init();


        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");

        List_Machine = (RecyclerView) findViewById(R.id.list_machine);
        Async async = new Async();
        async.execute();
    }

    public void init() {
        Edt_search = (EditText) findViewById(R.id.et_search);

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
        switch (v.getId()) {
            case R.id.btn_write:
                Intent intent = new Intent(Machine.this, Machine_Write.class);
                Log.i("User_Pk",User_Pk);
                intent.putExtra("User_Pk",User_Pk);
                startActivity(intent);
                break;
        }
    }
//
    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Machine.this);

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
//                베스트 다운로드 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Ojig2", "machine_select.jsp", params);
                parseredData = jsonParserList(result);

                machine_list = new ArrayList<Machine_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    machine_list.add(new Machine_Model(Machine.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6], parseredData[i][7], parseredData[i][8]));
                }
                machine_models = new ArrayList<Machine_Model>();
                machine_models.addAll(machine_list);

                return "succed";
            } catch (Exception e) {
                Toast.makeText(Machine.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Machine.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);

            //어댑터 셋팅
            machine_adapter = new Machine_Adapter(Machine.this, (ArrayList<Machine_Model>) machine_list, 2);
            List_Machine.setLayoutManager(layoutManager1);
            List_Machine.setAdapter(machine_adapter);

            asyncDialog.dismiss();
        }

        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 게임 내용", pRecvServerPage);
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
        machine_list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            machine_list.addAll(machine_models);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < machine_models.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (machine_models.get(i).getTitle().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    machine_list.add(machine_models.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        machine_adapter.notifyDataSetChanged();
    }
}
