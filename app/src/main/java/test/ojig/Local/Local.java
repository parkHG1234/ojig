package test.ojig.Local;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.ojig.R;

public class Local extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
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
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        }
    }
}
