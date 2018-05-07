package test.ojig.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import test.ojig.Model.Promotion_Model;
import test.ojig.R;
import test.ojig.promotion.Promotion_Focus;

/**
 * Created by 박효근 on 2018-05-05.
 */

public class Promotion_Adapter extends RecyclerView.Adapter<Promotion_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Promotion_Model> arrData;
    int item_layout;
    public Promotion_Adapter(Context c, ArrayList<Promotion_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_promotion, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Promotion_Model items = arrData.get(position);
        holder.Txt_GameName.setText(items.getGameName());
        holder.Txt_Company.setText(items.getCompanyName());
        holder.Txt_Title.setText(items.getTitle());
        holder.Txt_Date.setText(items.getDate()+"");
        try {
            String En_img = URLEncoder.encode(items.getPk(), "utf-8");
            Glide.with(items.getActivity()).load("http://13.209.35.228:8080/Promotion/" + En_img + ".jpg")
                    .into(holder.Img_Img);
            Log.i("test", En_img);
        } catch (UnsupportedEncodingException e) {

        }
        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Promotion_Focus.class);
                intent.putExtra("Promotion_Pk", items.getPk());
                items.getActivity().startActivity(intent);
                items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout Layout;
        TextView Txt_GameName, Txt_Company, Txt_Title, Txt_Date;
        ImageView Img_Img;
        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout) itemView.findViewById(R.id.layout);
            Txt_GameName = (TextView)itemView.findViewById(R.id.txt_gamename);
            Txt_Company = (TextView)itemView.findViewById(R.id.txt_company);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Img_Img = (ImageView)itemView.findViewById(R.id.img);
        }

    }
}
