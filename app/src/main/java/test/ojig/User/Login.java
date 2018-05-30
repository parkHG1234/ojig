package test.ojig.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.ojig.Choice;
import test.ojig.MainActivity;
import test.ojig.R;
import test.ojig.Start;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-04-29.
 */

public class Login extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    LinearLayout Layout_Join, Layout_Find;
    RelativeLayout Layout_Login;
    EditText Edit_Phone, Edit_Pass;
    static Activity act_Login;
    String User_Pk = "";
    String Category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");
        Category = preferences.getString("Category", ".");

        init();

    }
    public void init(){
        act_Login = this;
        Layout_Join = (LinearLayout)findViewById(R.id.layout_join);
        Edit_Phone = (EditText)findViewById(R.id.edit_phone);
        Edit_Pass = (EditText)findViewById(R.id.edit_pass);
        Layout_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Join.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Layout_Find = (LinearLayout)findViewById(R.id.layout_find);
        Layout_Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Join.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Layout_Login = (RelativeLayout)findViewById(R.id.layout_login);
        Layout_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http_login = new HttpClient();
                String result = http_login.HttpClient("Web_Ojig", "Login.jsp", Edit_Phone.getText().toString(), Edit_Pass.getText().toString());
                String[][] parseredData_login = jsonParserList_login(result);
                if (parseredData_login[0][0].equals("failed")) {
                    Toast.makeText(Login.this, "정보를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("User_Pk", parseredData_login[0][0]);
                    editor.commit();

                    if(Category.equals(".")){
                        Intent intent = new Intent(Login.this, Choice.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
    public String[][] jsonParserList_login(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1"};
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

