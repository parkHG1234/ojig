package test.ojig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import test.ojig.Buy.Buy;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout layout_buy, layout_sell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setBtn_Buy();
    }
    public void init(){
        layout_buy = (RelativeLayout)findViewById(R.id.layout_buy);
        layout_sell = (RelativeLayout)findViewById(R.id.layout_sell);
    }
    public void setBtn_Buy(){
        layout_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Buy.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.layout_video:
//                startActivity(new Intent(MainActivity.this, VideoActivity.class));
        }
    }
}
