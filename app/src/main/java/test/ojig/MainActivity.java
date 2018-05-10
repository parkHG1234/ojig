package test.ojig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import test.ojig.Buy.Buy;
import test.ojig.Sell.Sell;
import test.ojig.promotion.Promotion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String User_Bundle = "User_Bundle";
    RelativeLayout layout_buy, layout_sell, layout_promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_buy:
                intent = new Intent(MainActivity.this, Buy.class);
                intent.putExtra("User_Pk","1");
                break;
            case R.id.layout_sell:
                intent = new Intent(MainActivity.this, Sell.class);
                intent.putExtra("User_Pk","1");
                break;
            case R.id.layout_promotion:
                intent = new Intent(MainActivity.this, Promotion.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        }
    }
}
