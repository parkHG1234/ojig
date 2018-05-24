package test.ojig.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import test.ojig.R;

/**
 * Created by 박효근 on 2018-05-05.
 */

public class Promotion_ViewPage extends Fragment {
    ImageView img;
    String Pk = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_promotion, container, false);

        Bundle extra = getArguments();
        Pk = extra.getString("Promotion_Pk");

        img = (ImageView) rootView.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Promotion_Focus.class);
                intent.putExtra("Promotion_Pk", Pk);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        setImage(rootView, Pk);
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {
        try {
            String En_img = URLEncoder.encode(imgUrl, "utf-8");
            Glide.with(rootview.getContext()).load("http://13.209.35.228:8080/Promotion/banner/" + En_img + ".jpg")
                    .into(img);
            Log.i("test", En_img);
        } catch (UnsupportedEncodingException e) {

        }
    }
}
