package test.ojig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import test.ojig.User.Login;

/**
 * Created by 박효근 on 2018-05-29.
 */

public class Choice extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RelativeLayout Choice_All, Choice_19;
    String User_Pk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        User_Pk = preferences.getString("User_Pk", ".");

        init();
        setChoice_All();
        setChoice_19();
    }
    public void init() {
        Choice_All = (RelativeLayout) findViewById(R.id.choice_all);
        Choice_19 = (RelativeLayout) findViewById(R.id.choice_19);
    }
    public void setChoice_All(){
        Choice_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Category", "all");
                editor.commit();

                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Choice.this, Login.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(Choice.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
    }
    public void setChoice_19(){
        Choice_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Category", "adult");
                editor.commit();

                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Choice.this, Login.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(Choice.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
    }
}

