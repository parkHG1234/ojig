package test.ojig.Uitility;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 박효근 on 2018-02-12.
 */

public class LoginCheck extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    public String LoginPk(){
        preferences = getSharedPreferences("Ojig", MODE_PRIVATE);
        String Pk = preferences.getString("User_Pk", ".");
        return Pk;
    }
}
