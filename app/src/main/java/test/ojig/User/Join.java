package test.ojig.User;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import test.ojig.R;
import test.ojig.Uitility.HttpClient;

/**
 * Created by 박효근 on 2018-04-29.
 */

public class Join extends AppCompatActivity {
    EditText Edit_Name;TextView Line_Name;ImageView Img_NameCheck;

    EditText edit_phone;
    TextView line_phone;
    ImageView img_phonecheck;

    TextView txt_sms;
    LinearLayout layout_smsline1, layout_smsline2, layout_certi;
    EditText edit_certi;
    ImageView img_cetricheck;
    TextView txt_cetritime;
    TextView line_certi, txt_certi_succed;

    String str_name = "", str_phone;
    Boolean flag_name= false, flag_phone= false;
    int rnd = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        setEdit_NameEvent();
        setEdit_PhoneEvent();
        //setBtn_Sms();
    }
    public void init(){
        Edit_Name = (EditText)findViewById(R.id.edit_name);
        Line_Name = (TextView)findViewById(R.id.line_name);
        Img_NameCheck = (ImageView)findViewById(R.id.img_namecheck);
        Img_NameCheck.setVisibility(View.INVISIBLE);

        edit_phone = (EditText) findViewById(R.id.edit_phone);
        line_phone = (TextView) findViewById(R.id.line_phone);
        img_phonecheck = (ImageView) findViewById(R.id.img_phonecheck);

        layout_smsline1 = (LinearLayout) findViewById(R.id.layout_smsline1);
        layout_smsline2 = (LinearLayout) findViewById(R.id.layout_smsline2);
        txt_sms = (TextView) findViewById(R.id.txt_sms);
    }
    public void setEdit_NameEvent() {
        Edit_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Line_Name.setBackgroundColor(getResources().getColor(R.color.point));
                str_name = Edit_Name.getText().toString();
                //번호가 다 입력됐을 경우 버튼 이벤트
                if(str_name.equals("")){
                    flag_name = false;
                    Img_NameCheck.setVisibility(View.INVISIBLE);
                }
                else{
                    flag_name = true;
                    Img_NameCheck.setVisibility(View.VISIBLE);
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(Img_NameCheck, 1);
                    Glide.with(Join.this).load(R.drawable.join_check).into(gifImage);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                } else {
                    layout_smsline1.setBackgroundColor(getResources().getColor(R.color.line_gray));
                    layout_smsline2.setBackgroundColor(getResources().getColor(R.color.white));
                    txt_sms.setTextColor(getResources().getColor(R.color.line_gray));
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

//    public void setBtn_Sms() {
//        txt_sms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (str_phone.length() == 11 && str_phone.substring(0, 3).equals("010")) {
//                    HttpClient user = new HttpClient();
//                    String result = user.HttpClient("Web_Blah", "Phone_Confirm.jsp", str_phone);
//                    String[][] parseData = jsonParserList_Phone_Confirm(result);
//                    if (parseData[0][0].toString().equals("not existent")) {
//                        Random random = new Random();
//                        rnd = Math.abs(random.nextInt(899999) + 100000);
//                        String msg = "애드플레이 인증번호는 [" + String.valueOf(rnd) + "] 입니다.감사합니다.";
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
//                        Date d = new Date();
//                        String date = dateFormat.format(d);
//                        user = new HttpClient();
//                        Log.i("인증", rnd + "");
//                        user.HttpClient("InetSMSExample", "example.jsp", msg, str_phone, "15662649", date);
//
//                        //핸드폰 번호 변경 이벤트 제거
//                        edit_phone.setEnabled(false);
//                        //핸드폰 완료 이벤트
//                        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(img_phonecheck, 1);
//                        Glide.with(Join.this).load(R.drawable.join_check).into(gifImage);
//                        //핸드폰 포커스 제거 이벤트
//                        line_phone.setBackgroundColor(getResources().getColor(R.color.line_gray));
//                        //인증번호 포커스 이벤트
//                        setLayout_Certi();
//                        flag_phone = true;
//                    } else {
//                        flag_phone = false;
//                        Toast.makeText(Join.this, "이미 가입된 번호가 있습니다.", Toast.LENGTH_SHORT).show();
//                        //Join_TextView_Phone_Warning.setText("이미 가입된 번호가 있습니다");
//                        //Join_TextView_Phone_Warning.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    phone_flag = false;
//                    Toast.makeText(Join.this, "정확한 휴대전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    public class Async extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Join.this);

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

                return "succed";
            } catch (Exception e) {
                Toast.makeText(Join.this, getString(R.string.http_error), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            asyncDialog.dismiss();
        }

    }
}

