package test.ojig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.Timer;
import java.util.TimerTask;

import test.ojig.User.Login;

/**
 * Created by 박효근 on 2018-05-20.
 */

public class Start extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String User_Pk = "";

    TimerTask myTask;
    Timer timer;

    ImageView Img_flash2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");
        init();
        start();
    }
    public void init(){
        Img_flash2 = (ImageView)findViewById(R.id.img_flash2);
        //인증번호 완료 시 이벤트
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(Img_flash2);
        Glide.with(Start.this).load(R.drawable.start2).into(gifImage);
    }
    public void start(){
        myTask = new TimerTask() {
            int i = 3;
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 해당 작업을 처리함
                        if (i <= 0) {
                            if(User_Pk.equals(".")){
                                Intent intent = new Intent(Start.this, Login.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            }
                            else{
                                Intent intent = new Intent(Start.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            }
                            timer.cancel();
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

