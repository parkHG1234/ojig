package test.ojig.Others;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import test.ojig.R;

/**
 * Created by 박효근 on 2018-06-10.
 */

public class Others_txt extends AppCompatActivity {
    ImageView Img_Back;
    RelativeLayout Layout_Menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_txt);

        init();
        setImg_Back();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Layout_Menu = (RelativeLayout)findViewById(R.id.layout_menu);
        Layout_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Others_txt.this, Others_Menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}


