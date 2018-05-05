package test.ojig.Buy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.ojig.R;

public class Buy_Write extends AppCompatActivity implements View.OnClickListener {

    private String area;
    private Button btn_area;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_write);

        btn_area = (Button)findViewById(R.id.btn_area);
        btn_area.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_area:
                setDialog_Video(v);
                break;
            case R.id.seoul:
                dialogset("서울");
                break;
            case R.id.gyeonggi:
                dialogset("경기");
                break;
            case R.id.incheon:
                dialogset("인천");
                break;
            case R.id.gangwon:
                dialogset("강원");
                break;
            case R.id.chungbuk:
                dialogset("충북");
                break;
            case R.id.chungnam:
                dialogset("충남");
                break;
            case R.id.jeonbuk:
                dialogset("전북");
                break;
            case R.id.jeonnam:
                dialogset("전남");
                break;
            case R.id.gyeongbuk:
                dialogset("경북");
                break;
            case R.id.gyeongnam:
                dialogset("경남");
                break;
            case R.id.jeju:
                dialogset("제주");
                break;
        }
    }

    public void setDialog_Video(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_area, (ViewGroup) view.findViewById(R.id.Root));

        final AlertDialog.Builder aDialog = new AlertDialog.Builder(Buy_Write.this);
        aDialog.setCancelable(false);
        aDialog.setView(layout);
        // Dialog 사이즈 조절 하기
        dialog = aDialog.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void dialogset(String area){
        this.area = area;
        btn_area.setText(area);
        dialog.dismiss();
    }
}
