package test.ojig.Uitility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import test.ojig.R;

public class FullScreenImage extends AppCompatActivity {

    private String imgUrl="";
    private ImageView img_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);


        imgUrl = getIntent().getStringExtra("imgUrl");

        String En_img = null;
        try {
            En_img = URLEncoder.encode(imgUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        init(En_img);
    }

    private void init(String En_img){
        img_full = findViewById(R.id.img_fullscreen);

        Glide.with(getApplicationContext()).load("http://13.209.35.228:8080/Img_Sell/" + En_img + ".jpg")
                .error(R.drawable.img_add)
                .into(img_full);
    }
}
