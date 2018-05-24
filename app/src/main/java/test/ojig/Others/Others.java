package test.ojig.Others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import test.ojig.R;

/**
 * Created by 박효근 on 2018-05-23.
 */

public class Others extends AppCompatActivity {
    ImageView Img1, Img2, Img3, Img4, Img5, Img6;
    Button btn1, btn2, btn3, btn4, btn5, btn6;
    ImageView Img_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        init();
        setImage();
        setImg_Back();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img1 = (ImageView)findViewById(R.id.img1);
        Img2 = (ImageView)findViewById(R.id.img2);
        Img3 = (ImageView)findViewById(R.id.img3);
        Img4 = (ImageView)findViewById(R.id.img4);
        Img5 = (ImageView)findViewById(R.id.img5);
        Img6 = (ImageView)findViewById(R.id.img6);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
    }
    public void setImg_Back(){
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
    public void setImage(){
        Glide.with(this).load("http://13.209.35.228:8080/Img_Others/others1.jpg")
                .into(Img1);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}


