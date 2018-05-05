package test.ojig.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import test.ojig.R;

public class Viewpager_PagerFragment extends Fragment {

    private int mPageNumber;

    public static Viewpager_PagerFragment create(int pageNumber){
        Viewpager_PagerFragment fragment = new Viewpager_PagerFragment();
        Bundle args = new Bundle();
        args.putInt("page",pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.viewpager, container, false);
        ImageView imageView =  rootView.findViewById(R.id.image);


        Glide.with(this).load("http://18.218.70.203:8080/Alba_report_img/a"+mPageNumber+".jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .thumbnail(0.1f)
                .fitCenter()
                .into(imageView);


        return rootView;
    }
}
