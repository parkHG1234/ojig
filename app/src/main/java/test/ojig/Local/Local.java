package test.ojig.Local;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import test.ojig.R;

public class Local extends AppCompatActivity implements View.OnClickListener{
    ImageView Img_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.layout_1:
                intent = new Intent(Local.this, Local_Focus.class);
                break;
            case R.id.layout_2:
                intent = new Intent(Local.this, Local_Focus.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
