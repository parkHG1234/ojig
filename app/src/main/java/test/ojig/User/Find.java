package test.ojig.User;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import test.ojig.R;
import test.ojig.Uitility.HttpClient;

public class Find extends AppCompatActivity {
    EditText edit_phone;
    TextView line_phone;
    ImageView img_phonecheck;

    TextView txt_sms;
    LinearLayout layout_smsline1, layout_smsline2, layout_certi;
    EditText edit_certi;
    ImageView img_cetricheck;
    TextView txt_cetritime;
    TextView line_certi, txt_certi_succed;

    LinearLayout Layout_Pass, Layout_PassCheck;
    TextView Line_Pass, Line_PassCheck;
    EditText Edit_Pass, Edit_PassCheck;
    ImageView Img_PassCheck, Img_PassCheckCheck;

    LinearLayout Layout_Join;

    String str_phone = "", str_pass = "";
    Boolean flag_phone = false, flag_pass = false;

    TimerTask myTask;
    Timer timer;
    int rnd = 0; int mygame_count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_changepass);

        init();
        setEdit_PhoneEvent();
        setBtn_Sms();
        setResms_Event();
        setEdit_PassEvent();
        setEdit_PassCheckEvent();
        setJoin_Event();
    }
    public void init(){
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        line_phone = (TextView) findViewById(R.id.line_phone);
        img_phonecheck = (ImageView) findViewById(R.id.img_phonecheck);

        layout_smsline1 = (LinearLayout) findViewById(R.id.layout_smsline1);
        layout_smsline2 = (LinearLayout) findViewById(R.id.layout_smsline2);
        txt_sms = (TextView) findViewById(R.id.txt_sms);

        layout_certi = (LinearLayout)findViewById(R.id.layout_certi);
        edit_certi = (EditText)findViewById(R.id.edit_certi);
        line_certi = (TextView)findViewById(R.id.line_certi);
        img_cetricheck = (ImageView)findViewById(R.id.img_cetricheck);
        txt_certi_succed = (TextView)findViewById(R.id.txt_certi_succed);
        txt_certi_succed.setVisibility(View.GONE);
        txt_cetritime = (TextView)findViewById(R.id.txt_cetritime);

        Layout_Pass = (LinearLayout)findViewById(R.id.layout_pass);
        Layout_Pass.setVisibility(View.GONE);
        Layout_PassCheck = (LinearLayout)findViewById(R.id.layout_passcheck);
        Layout_PassCheck.setVisibility(View.GONE);
        Line_Pass = (TextView)findViewById(R.id.line_pass);
        Line_Pass.setVisibility(View.GONE);
        Line_PassCheck = (TextView)findViewById(R.id.line_passcheck);
        Line_PassCheck.setVisibility(View.GONE);
        Edit_Pass = (EditText)findViewById(R.id.edit_pass);
        Edit_PassCheck = (EditText)findViewById(R.id.edit_passcheck);
        Img_PassCheck = (ImageView)findViewById(R.id.img_passcheck);
        Img_PassCheckCheck = (ImageView)findViewById(R.id.img_passcheckcheck);

        Layout_Join = (LinearLayout)findViewById(R.id.layout_join);
    }
    public void setEdit_PhoneEvent() {

        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line_phone.setBackgroundColor(getResources().getColor(R.color.point));
                str_phone = edit_phone.getText().toString().replaceAll("-", "");
                //번호에 - 추가 셋팅
                if (edit_phone.getText().toString().length() == 3) {
                    edit_phone.setText(edit_phone.getText().toString() + "-");
                    edit_phone.setSelection(edit_phone.getText().length());
                } else if (edit_phone.getText().toString().length() == 8) {
                    edit_phone.setText(edit_phone.getText().toString() + "-");
                    edit_phone.setSelection(edit_phone.getText().length());
                }


                //번호가 다 입력됐을 경우 버튼 이벤트
                if (edit_phone.getText().toString().length() == 13) {
                    layout_smsline1.setBackgroundColor(getResources().getColor(R.color.point));
                    layout_smsline2.setBackgroundColor(getResources().getColor(R.color.point));
                    txt_sms.setTextColor(getResources().getColor(R.color.white));
                    txt_sms.setEnabled(true);
                } else {
                    layout_smsline1.setBackgroundColor(getResources().getColor(R.color.line_gray));
                    layout_smsline2.setBackgroundColor(getResources().getColor(R.color.white));
                    txt_sms.setTextColor(getResources().getColor(R.color.line_gray));
                    txt_sms.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edit_phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    edit_phone.setText(null);
                }
                return false;
            }
        });
    }

    public void setBtn_Sms() {
        txt_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_phone.length() == 11 && str_phone.substring(0, 3).equals("010")) {
                    HttpClient user = new HttpClient();
                    String result = user.HttpClient("Web_Ojig", "Phone_Confirm.jsp", str_phone);
                    String[][] parseData = jsonParserList_Phone_Confirm(result);
                    if (parseData[0][0].toString().equals("not existent")) {
                        flag_phone = false;
                        Toast.makeText(Find.this, "번호가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Random random = new Random();
                        rnd = Math.abs(random.nextInt(8999) + 1000);
                        String msg = "오직 인증번호는 [" + String.valueOf(rnd) + "] 입니다.감사합니다.";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                        Date d = new Date();
                        String date = dateFormat.format(d);
                        user = new HttpClient();
                        Log.i("인증", rnd + "");
                        user.HttpClient("InetSMSExample", "example.jsp", msg, str_phone, "15662649", date);

                        setLayout_Certi();
                        //핸드폰 번호 변경 이벤트 제거
                        edit_phone.setEnabled(false);
                        //핸드폰 완료 이벤트
                        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(img_phonecheck, 1);
                        Glide.with(Find.this).load(R.drawable.join_check).into(gifImage);
                        //핸드폰 포커스 제거 이벤트
                        line_phone.setBackgroundColor(getResources().getColor(R.color.line_gray));
                        //인증번호 포커스 이벤트
                        flag_phone = true;
                    }
                } else {
                    flag_phone = false;
                    Toast.makeText(Find.this, "정확한 휴대전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setLayout_Certi() {
        layout_certi.setVisibility(View.VISIBLE);
        layout_smsline1.setVisibility(View.GONE);
        Certi_Timer();
        line_certi.setBackgroundColor(getResources().getColor(R.color.point));
        edit_certi.requestFocus();
        txt_certi_succed.setVisibility(View.VISIBLE);
        SpannableString content = new SpannableString(getResources().getString(R.string.login_join_certi_recerti));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        txt_certi_succed.setText(content);
        txt_certi_succed.setTextColor(getResources().getColor(R.color.black));
        setEdit_CertiEvent();
    }

    public void setResms_Event(){
        txt_certi_succed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_phone.setEnabled(true);
                layout_certi.setVisibility(View.GONE);
                layout_smsline1.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        });
    }

    public void setEdit_CertiEvent() {
        edit_certi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit_certi.getText().toString().equals(rnd + "")) {
                    //포커스 해제 이벤트
                    line_certi.setBackgroundColor(getResources().getColor(R.color.line_gray));

                    //인증번호 완료 시 이벤트
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(img_cetricheck, 1);
                    Glide.with(Find.this).load(R.drawable.join_check).into(gifImage);
                    img_cetricheck.setVisibility(View.VISIBLE);
                    txt_cetritime.setVisibility(View.GONE);
                    txt_certi_succed.setVisibility(View.VISIBLE);
                    txt_certi_succed.setText(getResources().getString(R.string.login_join_certi_succed));
                    txt_certi_succed.setTextColor(getResources().getColor(R.color.point));

                    //비밀번호 입력 창 활성화 이벤트
                    Layout_Pass.setVisibility(View.VISIBLE);
                    Layout_PassCheck.setVisibility(View.VISIBLE);
                    Line_Pass.setVisibility(View.VISIBLE);
                    Line_PassCheck.setVisibility(View.VISIBLE);

                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setEdit_PassEvent(){
        Edit_Pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Edit_Pass.length() == 4){
                    str_pass = Edit_Pass.getText().toString();
                    Img_PassCheck.setVisibility(View.VISIBLE);
                    //인증번호 완료 시 이벤트
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(Img_PassCheck, 1);
                    Glide.with(Find.this).load(R.drawable.join_check).into(gifImage);
                    All_Flag();
                }
                else{
                    Img_PassCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setEdit_PassCheckEvent(){
        Edit_PassCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(Edit_PassCheck.getText().toString().equals(Edit_Pass.getText().toString())){
                    flag_pass = true;
                    //비밀번호 체크시 이벤트
                    Img_PassCheckCheck.setVisibility(View.VISIBLE);
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(Img_PassCheckCheck, 1);
                    Glide.with(Find.this).load(R.drawable.join_check).into(gifImage);
                    All_Flag();
                    Layout_Join.setVisibility(View.VISIBLE);
                }
                else{
                    Img_PassCheckCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void setJoin_Event(){
        Layout_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async async = new Async();
                async.execute(str_phone, str_pass);
            }
        });
    }
    public void All_Flag(){
        if(flag_phone == true && flag_pass == true){
            Layout_Join.setEnabled(true);
            Layout_Join.setBackgroundColor(getResources().getColor(R.color.point_gold));
        }else{
            Layout_Join.setEnabled(false);
            Layout_Join.setBackgroundColor(getResources().getColor(R.color.line_gray));
        }
    }
    public String[][] jsonParserList_Phone_Confirm(String pRecvServerPage) {
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
    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Find.this);

        String result= "";

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
                Log.i("test123",str_phone);
                Log.i("test123",str_pass);

                //베스트 다운로드 데이터 셋팅
                HttpClient http = new HttpClient();
                result = http.HttpClient("Web_Ojig", "User_ChangePass.jsp", params);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("succed")){
                Toast.makeText(Find.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        finish();
                        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    }
                };
                handler.sendEmptyMessageDelayed(0, 1000);
                finish();
            }
            else{
                Toast.makeText(Find.this, "잠시 후 다시 이용바랍니다.", Toast.LENGTH_SHORT).show();
            }
            asyncDialog.dismiss();
        }

    }
    public void Certi_Timer() {
        if(myTask != null){
            myTask.cancel();
            myTask = null;
        }
        myTask = new TimerTask() {
            int i = 180;
            int certi_time = 259;
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 해당 작업을 처리함
                        if (i <= 0) {
                            //인증 만료 시 처리
                            timer.cancel();
                        } else {
                            String str_time = Integer.toString(certi_time);
                            if (i == 121) {
                                certi_time = certi_time - 40;
                            } else if (i == 61) {
                                certi_time = certi_time - 40;
                            }
                            if (i <= 60) {
                                if (i <= 10) {
                                    txt_cetritime.setText("0 : 0" + str_time.substring(0, 1));
                                } else {
                                    txt_cetritime.setText("0 : " + str_time.substring(0, 2));
                                }
                            } else {
                                txt_cetritime.setText(str_time.substring(0, 1) + " : " + str_time.substring(1, 3));
                            }
                        }
                    }
                });
                i--;
                certi_time--;
            }
        };
        timer = new Timer();
        timer.schedule(myTask, 1000, 1000); // 5초후 첫실행, 1초마다 계속실행
    }
}

