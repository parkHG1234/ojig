package test.ojig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.drakeet.materialdialog.MaterialDialog;
import test.ojig.Uitility.HttpClient;
import test.ojig.User.Login;

/**
 * Created by 박효근 on 2018-02-26.
 */

public class Flash extends AppCompatActivity {
    String Project_version = "1.0";
    String[][] ParsedData_Setting;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String User_Pk = "";
    Boolean Guide = true;

    TimerTask myTask;
    Timer timer;
    String strCurToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");
        //Guide = preferences.getBoolean("Guide", true);

        currentTime();

        Check_Setting();

        try {
            PackageInfo info = getPackageManager().getPackageInfo("test.blahblah", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("ttt","key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("ttt",e +"");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("ttt",e +"");
        }
    }
    public void Check_Setting(){
        HttpClient http_setting = new HttpClient();
        String result = http_setting.HttpClient("Web_Ojig","Setting.jsp");
        ParsedData_Setting = jsonParserList_Setting(result);
        if(!Project_version.equals(ParsedData_Setting[0][0])){
            LayoutInflater inflater = (LayoutInflater)Flash.this.getSystemService(Flash.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_update, (ViewGroup)findViewById(R.id.Customdialog_Update_Root));
            final Button Customdialog_Update_Button_Ok = (Button)layout.findViewById(R.id.Customdialog_Update_Button_Ok);
            final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Flash.this);
            TeamInfo_Dialog
                    .setContentView(layout)
                    .setCanceledOnTouchOutside(true);
            TeamInfo_Dialog.show();
            Customdialog_Update_Button_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=trophy.projetc2"));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            myTask = new TimerTask() {
                int i = 3;

                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 해당 작업을 처리함
                            if (i <= 0) {
                                HttpClient http_count = new HttpClient();
                                http_count.HttpClient("Web_Blah","Today_Counting.jsp", strCurToday);
                                timer.cancel();

                                if(User_Pk.equals(".")){
                                    Intent intent = new Intent(Flash.this, Login.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                                }
                                else{
                                    Intent intent = new Intent(Flash.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                                }
                                finish();
                            }
                        }
                    });
                    i--;
                    //시간이 초과된 경우 game 내 데이터 삭제 및 초기화.

                }
            };
            timer = new Timer();
            timer.schedule(myTask, 1000, 1000); // 5초후 첫실행, 1초마다 계속실행
        }
    }
    public String[][] jsonParserList_Setting(String pRecvServerPage) {
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
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMdd");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
    }

}
