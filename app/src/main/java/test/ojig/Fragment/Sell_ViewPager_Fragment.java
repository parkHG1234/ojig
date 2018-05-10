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

/**
 * Created by 박효근 on 2018-05-05.
 */

public class Sell_ViewPager_Fragment extends Fragment {
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_promotion, container, false);

        Bundle extra = getArguments();
        String a = extra.getString("Image");

        img = (ImageView) rootView.findViewById(R.id.img);
        setImage(rootView, a);
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {
        try {
            String En_img = URLEncoder.encode(imgUrl, "utf-8");
            Glide.with(rootview.getContext()).load("http://13.124.32.32:8080/Blah_img/Home_Banner/" + En_img + ".jpg")
                    .into(img);
            Log.i("test", En_img);
        } catch (UnsupportedEncodingException e) {

        }
    }
}
