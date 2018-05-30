package test.ojig.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.ojig.Choice;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;

import static test.ojig.MainActivity.activity_main;

/**
 * Created by 박효근 on 2018-05-29.
 */

public class Setting extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences preferences; //캐쉬 데이터 생성
    SharedPreferences.Editor editor; //캐쉬 데이터 에디터 생성

    String User_Pk = "";
    String Category = "";

    ImageView category;
    TextView Company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        editor = preferences.edit();

        User_Pk = preferences.getString("User_Pk", ".");
        Category = preferences.getString("Category", ".");

        init();
        setUser();
    }
    public void init(){
        category = (ImageView)findViewById(R.id.category);
        if(Category.equals("all")){
            category.setImageResource(R.drawable.choice_all);
        }
        else{
            category.setImageResource(R.drawable.choice_19);
        }

        Company = (TextView)findViewById(R.id.company);
    }
    public void setUser(){
        HttpClient http_login = new HttpClient();
        String result = http_login.HttpClient("Web_Ojig", "User.jsp", User_Pk);
        String[][] parseredData = jsonParserList(result);

        Company.setText(parseredData[0][3]);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
            case R.id.logout:
                editor.putString("User_Pk", ".");
                editor.commit();

                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                activity_main.finish();
                finish();
                break;
            case R.id.category_change:
                editor.putString("Category", ".");
                editor.commit();

                Intent intent1 = new Intent(Setting.this, Choice.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                activity_main.finish();
                finish();
                break;
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7"};
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}


