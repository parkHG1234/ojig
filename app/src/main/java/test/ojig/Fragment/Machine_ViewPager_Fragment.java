package test.ojig.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import test.ojig.R;
import test.ojig.Uitility.FullScreenImage;

public class Machine_ViewPager_Fragment extends Fragment {
    ImageView img;
    String Image = ""; String machine_pk = ""; String PageCount = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_machine, container, false);

        Bundle extra = getArguments();
        Image = extra.getString("Image");
        machine_pk = extra.getString("Pk");
        PageCount = extra.getString("Count");

        img = (ImageView) rootView.findViewById(R.id.img);
        setImage(rootView, Image);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), FullScreenImage.class);
                intent.putExtra("Type","Machine");
                intent.putExtra("Pk",machine_pk);
                intent.putExtra("Image",Image);
                intent.putExtra("PageCount",PageCount);
                startActivity(intent);
            }
        });
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {

        try {
            String En_img = URLEncoder.encode(imgUrl,"utf-8");
            Glide.with(rootview.getContext()).load("http://13.209.35.228:8080/Img_Machine/" + En_img + ".jpg")
                    .error(R.drawable.basic_mainlist)
                    .into(img);
        } catch (UnsupportedEncodingException e) {

        }

    }
}
