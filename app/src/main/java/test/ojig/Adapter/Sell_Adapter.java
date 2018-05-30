package test.ojig.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.ojig.Model.Sell_Model;
import test.ojig.R;
import test.ojig.Sell.Sell_Focus;
import test.ojig.Uitility.convertHangul;

public class Sell_Adapter extends RecyclerView.Adapter<Sell_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Sell_Model> arrData;
    int item_layout;

    public Sell_Adapter(Context c, ArrayList<Sell_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sell, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Sell_Model items = arrData.get(position);
        holder.tv_name.setText(items.getName());
        holder.tv_title.setText(items.getTitle());
        convertHangul convertHangul = new convertHangul();
        holder.tv_price.setText(convertHangul.convertHangul(items.getPrice())+"원");
        holder.tv_address.setText(items.getAddress());
        holder.tv_gamecount.setText(items.getAmount() + "대 희망");

        if(items.getStatus().equals("possible")){
            Glide.with(context).load(R.drawable.deal_possible)
                    .into(holder.img_deal);
        }
        else if(items.getStatus().equals("ing")){
            Glide.with(context).load(R.drawable.deal_ing)
                    .into(holder.img_deal);
        }
        else if(items.getStatus().equals("finish")){
            Glide.with(context).load(R.drawable.deal_finish)
                    .into(holder.img_deal);
        }

        Drawable mDefaultBackground = context.getResources().getDrawable(R.drawable.basic_mainlist);


        Glide.with(items.getActivity()).load("http://13.209.35.228:8080/Img_Sell/" + items.getSell_Pk() + "_0.jpg")
                .error(mDefaultBackground)
                .into(holder.img_sell);


        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (items.getStatus().equals("wait")) {
                    Toast.makeText(context, "확인이 필요한 글입니다.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(context, Sell_Focus.class);
                    intent.putExtra("Sell_Pk", items.getSell_Pk());
                    context.startActivity(intent);
                    items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
    }

    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout Layout;
        private TextView tv_title, tv_name, tv_price, tv_address, tv_gamecount;
        private ImageView img_deal, img_sell;
        private Button img_call;

        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout) itemView.findViewById(R.id.layout);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_gamecount = (TextView) itemView.findViewById(R.id.tv_gamecount);
            img_deal = (ImageView) itemView.findViewById(R.id.img_deal);
            img_sell = (ImageView) itemView.findViewById(R.id.img_sell);
            img_call = (Button) itemView.findViewById(R.id.img_call);

        }

    }
}
