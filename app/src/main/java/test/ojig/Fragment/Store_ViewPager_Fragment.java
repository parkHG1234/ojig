package test.ojig.Fragment;

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

public class Store_ViewPager_Fragment extends Fragment {
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_store, container, false);

        Bundle extra = getArguments();
        String a = extra.getString("Image");

        img = (ImageView) rootView.findViewById(R.id.img);
        setImage(rootView, a);
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {

        try {
            String En_img = URLEncoder.encode(imgUrl,"utf-8");
            Glide.with(rootview.getContext()).load("http://13.209.35.228:8080/Img_Store/" + En_img + ".jpg")
                    .into(img);
        } catch (UnsupportedEncodingException e) {

        }

    }
}
