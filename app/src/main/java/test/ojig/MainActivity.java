package test.ojig;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import test.ojig.Buy.Buy;
import test.ojig.promotion.Promotion;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout_buy, layout_sell,layout_promotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setBtn_Buy();
        setBtn_Promotion();
    }
    public void init(){
        layout_buy = (RelativeLayout)findViewById(R.id.layout_buy);
        layout_sell = (RelativeLayout)findViewById(R.id.layout_sell);
        layout_promotion = (RelativeLayout)findViewById(R.id.layout_promotion);
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
    public void setBtn_Promotion(){
        layout_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Promotion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
            }
        });
    }
}
