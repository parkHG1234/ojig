package test.ojig.Sell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.jar.Manifest;

import test.ojig.Model.User_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;
import test.ojig.Uitility.HttpFileUpload;

public class Sell_Write extends AppCompatActivity implements View.OnClickListener {

    private String area = "";
    private Button btn_area;
    private AlertDialog dialog;
    private String Profile = "";
    private String User_Pk;
    private ArrayList<User_Model> user_models;
    private String[][] parseredData;
    private EditText edt_title, edt_name, edt_amount, edt_memo, edt_company_name, edt_phone, edt_company_focus;
    private ImageView img_write1, img_write2, img_write3, img_write4, img_write5, img_write6;
    private String img_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_write);

        init();
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    private void init() {

        img_write1 = (ImageView)findViewById(R.id.img_write1);
        img_write2 = (ImageView)findViewById(R.id.img_write2);
        img_write3 = (ImageView)findViewById(R.id.img_write3);
        img_write4 = (ImageView)findViewById(R.id.img_write4);
        img_write5 = (ImageView)findViewById(R.id.img_write5);
        img_write6 = (ImageView)findViewById(R.id.img_write6);

        User_Pk = getIntent().getStringExtra("User_Pk");
//        btn_area = (Button) findViewById(R.id.btn_area);
//        btn_area.setOnClickListener(this);
//        edt_title = (EditText) findViewById(R.id.edt_title);
//        edt_name = (EditText) findViewById(R.id.edt_name);
//        edt_amount = (EditText) findViewById(R.id.edt_amount);
//        edt_memo = (EditText) findViewById(R.id.edt_memo);
//        edt_company_name = (EditText) findViewById(R.id.edt_company_name);
//        edt_phone = (EditText) findViewById(R.id.edt_phone);
//        edt_company_focus = (EditText) findViewById(R.id.edt_company_focus);
//
//        HttpClient http = new HttpClient();
//        String result = http.HttpClient("Web_Ojig2", "user_select.jsp", User_Pk);
//        parseredData = jsonParserList(result);
//
//
//        user_models = new ArrayList<User_Model>();
//        for (int i = 0; i < parseredData.length; i++) {
//            user_models.add(new User_Model(Sell_Write.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6]));
//        }
//
//        edt_company_name.setText(user_models.get(0).getCompany_Name());
//        edt_phone.setText(user_models.get(0).getPhone());
//        edt_company_focus.setText(user_models.get(0).getCompany_Focus());
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
            case R.id.img_write1:
                Album(1);
                break;
            case R.id.img_write2:
                Album(2);
                break;
            case R.id.img_write3:
                Album(3);
                break;
            case R.id.img_write4:
                Album(4);
                break;
            case R.id.img_write5:
                Album(5);
                break;
            case R.id.img_write6:
                Album(6);
                break;
        }
    }


    public int checkPermission() {

//        if(ContextCompat.checkSelfPermission(Sell_Write.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            if((ActivityCompat.shouldShowRequestPermissionRationale(Sell_Write.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)))
//        }
        return 1;
    }

    public void Album(int i){

        Uri uri = Uri.parse("content://media/external/images/media");
        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //인텐트에 요청을 덛붙인다.
        intent.setAction(Intent.ACTION_PICK);
        //모든 이미지
        intent.setType("image/*");
        //결과값을 받아오는 액티비티를 실행한다.
        startActivityForResult(intent, i);
        //결과 URL DB 저장
        try {
            String En_Profile = URLEncoder.encode(User_Pk, "utf-8");
            Profile = "http://13.124.32.32:8080/Blah_img/Profile/" + En_Profile + ".jpg";
            HttpClient user = new HttpClient();
            user.HttpClient("Web_Blah", "Change_Profile.jsp", User_Pk, Profile);
        }catch (UnsupportedEncodingException e){

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            //인텐트에 데이터가 담겨 왔다면
            if (!intent.getData().equals(null)) {
                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                //이미지의 크기 조절하기.
                selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
                //image_bt.setImageBitmap(selPhoto);//썸네일
                //화면에 출력해본다.
                //Profile_ImageVIew_Profile.setImageBitmap(selPhoto);
                Log.e("선택 된 이미지 ", "selPhoto : " + selPhoto);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //선택한 이미지의 uri를 읽어온다.
                Uri selPhotoUri = intent.getData();
                Log.e("전송", "시~~작 ~~~~~!");
                //업로드할 서버의 url 주소
                String urlString = "http://13.209.35.228:8080/Web_Ojig2/ImageUpload.jsp";
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###파일의 절대 경로###", absolutePath);
                //파일 업로드 시작!
                new HttpFileUpload(urlString, "", absolutePath);


//                Glide.with(MainActivity.this).load(Profile).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
//                        .skipMemoryCache(true)
//                        .into(Navi_Profile);
//                Glide.with(this).load(Profile).diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true).bitmapTransform(new BlurTransformation(getBaseContext(),25), new ColorFilterTransformation(getBaseContext(), Color.argb(100, 0, 0, 0)))
//                        .into(Navi_Profile_Blur);
            }
        } catch (FileNotFoundException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e("tt",e.toString());
        }

    }


    private void btn_write() {
        if (edt_title.getText().length() != 0) {
            if (edt_name.getText().length() != 0) {
                if (!area.equals("")) {
                    if (edt_amount.getText().length() != 0) {
                        if (edt_memo.getText().length() != 0) {
                            if (edt_company_name.getText().length() != 0) {
                                if (edt_phone.getText().length() != 0) {
                                    if (edt_company_focus.getText().length() != 0) {
                                        HttpClient http = new HttpClient();
                                        String result = http.HttpClient("Web_Ojig2", "buy_write.jsp", User_Pk, edt_title.getText().toString(), edt_name.getText().toString(), area, edt_amount.getText().toString(),
                                                edt_memo.getText().toString(), edt_company_name.getText().toString(), edt_phone.getText().toString(), edt_company_focus.getText().toString());
                                        Log.i("result", result);
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getString("msg1").equals("succed")) {
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "인터넷연결을 확인해주세요", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "주소를 입력해주세요", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "핸드폰번호를 입력해주세요", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "회사명을 입력해주세요", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "메모를 입력해주세요", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "구입희망 수량을 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "지역을 선택해주세요", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "구입희망 게임기을 입력해주세요", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "글제목을 입력해주세요", Toast.LENGTH_LONG).show();
        }

    }

    public void setDialog_Video(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_area, (ViewGroup) view.findViewById(R.id.Root));

        final AlertDialog.Builder aDialog = new AlertDialog.Builder(Sell_Write.this);
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
