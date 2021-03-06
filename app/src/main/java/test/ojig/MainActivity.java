package test.ojig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import test.ojig.Buy.Buy;
import test.ojig.Delivery.Delivery_Address;
import test.ojig.Local.Local;
import test.ojig.Machine.Machine;
import test.ojig.Others.Others;
import test.ojig.Others.Others_txt;
import test.ojig.Sell.Sell;
import test.ojig.Store.Store;
import test.ojig.Uitility.HttpClient;
import test.ojig.User.Setting;
import test.ojig.promotion.Promotion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences preferences = null; //캐쉬 데이터 생성

    public static Activity activity_main;
    public static final String User_Bundle = "User_Bundle";
    RelativeLayout layout_buy, layout_sell, layout_promotion;

    String User_Pk = "";

    ImageView Img_Add;
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");

        checkPermission();
        if (shouldAskPermissions()) {
            askPermissions();
        }

        init();
        setImage_Add();
        setToken();
    }

    public void init(){
        activity_main = this;
        Img_Add = (ImageView)findViewById(R.id.img_add);
    }
    public void setImage_Add(){
        Glide.with(this).load("http://13.209.35.228:8080/Img_Add/main_adult.jpg")
                .into(Img_Add);
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_buy:
                intent = new Intent(MainActivity.this, Buy.class);
                intent.putExtra("User_Pk",User_Pk);
                break;
            case R.id.layout_sell:
                intent = new Intent(MainActivity.this, Sell.class);
                intent.putExtra("User_Pk",User_Pk);
                break;
            case R.id.layout_local:
                Toast.makeText(MainActivity.this,"준비 중입니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_promotion:
                intent = new Intent(MainActivity.this, Promotion.class);
                break;
            case R.id.layout_store:
                intent = new Intent(MainActivity.this, Store.class);
                break;
            case R.id.layout_machine:
                intent = new Intent(MainActivity.this, Machine.class);
                break;
            case R.id.layout_others:
                intent = new Intent(MainActivity.this, Others_txt.class);
                break;
            case R.id.layout_delivery:
                intent = new Intent(MainActivity.this, Local.class);
                break;
            case R.id.img_setting:
                intent = new Intent(MainActivity.this, Setting.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        }
    }

    public int checkPermission() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1111);
            }
        }
        return 1;
    }
    public void setToken(){
        String Token = FirebaseInstanceId.getInstance().getToken();
        HttpClient http_addtoken = new HttpClient();
        http_addtoken.HttpClient("Web_Ojig", "User_Fcmadd.jsp", User_Pk, Token);
    }
}
