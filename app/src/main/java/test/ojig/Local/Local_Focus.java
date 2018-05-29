package test.ojig.Local;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import test.ojig.Adapter.Local_Adapter;
import test.ojig.Model.Local_Model;
import test.ojig.R;

public class Local_Focus extends AppCompatActivity {


    private RecyclerView List_Local;
    private Local_Adapter local_adapter;
    private List<Local_Model> local_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_focus);

        List_Local = (RecyclerView)findViewById(R.id.list_local);



        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Local_Focus.this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager1.scrollToPosition(0);

        //어댑터 셋팅
         local_adapter = new Local_Adapter(Local_Focus.this, (ArrayList<Local_Model>) local_list, 2);
        List_Local.setLayoutManager(layoutManager1);
        List_Local.setAdapter(local_adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
