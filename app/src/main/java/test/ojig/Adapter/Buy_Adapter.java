package test.ojig.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.ojig.Buy.Buy_Focus;
import test.ojig.Model.Buy_Model;
import test.ojig.R;

public class Buy_Adapter extends RecyclerView.Adapter<Buy_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Buy_Model> arrData;
    int item_layout;

    public Buy_Adapter(Context c, ArrayList<Buy_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buy, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Buy_Model items = arrData.get(position);
        holder.tv_GameName.setText(items.getName());
        holder.tv_title.setText(items.getTitle());
        holder.tv_Address.setText(items.getAddress());
        holder.tv_GameCount.setText(items.getAmount() + "대 희망");
        if(items.getStatus().equals("possible")){
            Glide.with(context).load(R.drawable.deal_possible)
                    .into(holder.Img_deal);
        }
        else if(items.getStatus().equals("ing")){
            Glide.with(context).load(R.drawable.deal_ing)
                    .into(holder.Img_deal);
        }
        else if(items.getStatus().equals("finish")){
            Glide.with(context).load(R.drawable.deal_finish)
                    .into(holder.Img_deal);
        }

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.getStatus().equals("wait")){
                    Toast.makeText(context,"확인이 필요한 글입니다.", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(context, Buy_Focus.class);
                    intent.putExtra("Buy_Pk", items.getBuy_Pk());
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
        LinearLayout Layout;
        TextView tv_GameName, tv_title, tv_Address, tv_GameCount;
        ImageView Img_deal;

        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout)itemView.findViewById(R.id.layout);
            tv_GameName = (TextView) itemView.findViewById(R.id.tv_gamename);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_Address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_GameCount = (TextView) itemView.findViewById(R.id.tv_gamecount);
            Img_deal = (ImageView) itemView.findViewById(R.id.img_deal);
        }

    }
}
