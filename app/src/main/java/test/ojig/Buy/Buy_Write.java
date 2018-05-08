package test.ojig.Buy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import test.ojig.Model.Buy_Model;
import test.ojig.Model.User_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

public class Buy_Write extends AppCompatActivity implements View.OnClickListener {

    private String area;
    private Button btn_area;
    private AlertDialog dialog;
    private String User_Pk;
    private ArrayList<User_Model> user_models;
    private String[][] parseredData;
    private EditText edt_title, edt_name, edt_amount, edt_memo, edt_company_name, edt_phone, edt_company_focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_write);

        init();
    }


    private void init() {

        User_Pk = getIntent().getStringExtra("User_Pk");
        btn_area = (Button) findViewById(R.id.btn_area);
        btn_area.setOnClickListener(this);
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        edt_memo = (EditText) findViewById(R.id.edt_memo);
        edt_company_name = (EditText) findViewById(R.id.edt_company_name);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_company_focus = (EditText) findViewById(R.id.edt_company_focus);

        HttpClient http = new HttpClient();
        String result = http.HttpClient("Web_Ojig2", "user_select.jsp", User_Pk);
        parseredData = jsonParserList(result);


        user_models = new ArrayList<User_Model>();
        for (int i = 0; i < parseredData.length; i++) {
            user_models.add(new User_Model(Buy_Write.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6]));
        }

        edt_company_name.setText(user_models.get(0).getCompany_Name());
        edt_phone.setText(user_models.get(0).getPhone());
        edt_company_focus.setText(user_models.get(0).getCompany_Focus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_area:
                setDialog_Video(v);
                break;
            case R.id.seoul:
                dialogset("서울");
                break;
            case R.id.gyeonggi:
                dialogset("경기");
                break;
            case R.id.incheon:
                dialogset("인천");
                break;
            case R.id.gangwon:
                dialogset("강원");
                break;
            case R.id.chungbuk:
                dialogset("충북");
                break;
            case R.id.chungnam:
                dialogset("충남");
                break;
            case R.id.jeonbuk:
                dialogset("전북");
                break;
            case R.id.jeonnam:
                dialogset("전남");
                break;
            case R.id.gyeongbuk:
                dialogset("경북");
                break;
            case R.id.gyeongnam:
                dialogset("경남");
                break;
            case R.id.jeju:
                dialogset("제주");
                break;
            case R.id.btn_write:
                btn_write();
                break;
        }
    }

    private void btn_write(){
        Log.i("aaaa",edt_memo.getText().toString());
        if (edt_title.getText().toString() != null||edt_title.getText().toString() != "")
            if (edt_name.getText().toString() != null||edt_name.getText().toString() != "")
                if (area != null||area != "")
                    if (edt_amount.getText().toString() != null||edt_amount.getText().toString() != "")
                        if (edt_memo.getText().toString() != null||edt_memo.getText().toString() != "")
                            if (edt_company_name.getText().toString() != null||edt_company_name.getText().toString() != "")
                                if (edt_phone.getText().toString() != null||edt_phone.getText().toString() != "")
                                    if (edt_company_focus.getText().toString() != null||edt_company_focus.getText().toString() != "") {
                                        HttpClient http = new HttpClient();
                                        String result = http.HttpClient("Web_Ojig2", "buy_write.jsp", User_Pk, edt_title.getText().toString(), edt_name.getText().toString(), area, edt_amount.getText().toString(),
                                                edt_memo.getText().toString(), edt_company_name.getText().toString(), edt_phone.getText().toString(), edt_company_focus.getText().toString());
                                        Log.i("result", result);
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("msg1").equals("succed")){
                                                finish();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "인터넷연결을 확인해주세요", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else
                                        Toast.makeText(getApplicationContext(), "주소를 입력해주세요", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), "핸드폰번호를 입력해주세요", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "회사명을 입력해주세요", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "메모를 입력해주세요", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "구입희망 수량을 입력해주세요", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "지역을 선택해주세요", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "구입희망 게임기을 입력해주세요", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "글제목을 입력해주세요", Toast.LENGTH_LONG).show();

    }

    public void setDialog_Video(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_area, (ViewGroup) view.findViewById(R.id.Root));

        final AlertDialog.Builder aDialog = new AlertDialog.Builder(Buy_Write.this);
        aDialog.setCancelable(false);
        aDialog.setView(layout);
        // Dialog 사이즈 조절 하기
        dialog = aDialog.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void dialogset(String area) {
        this.area = area;
        btn_area.setText(area);
        dialog.dismiss();
    }


    public String[][] jsonParserList(String pRecvServerPage) {
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
