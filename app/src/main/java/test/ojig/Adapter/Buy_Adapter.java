package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import test.ojig.Model.Buy_Model;
import test.ojig.R;

/**
 * Created by 박효근 on 2018-04-22.
 */

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
        holder.Txt_GameName.setText(items.getCategory());
        holder.Txt_title.setText(items.getTitle());
        holder.Txt_Memo.setText(items.getMemo());
        holder.Txt_Address.setText(items.getAddress());
        holder.Txt_GameCount.setText(items.getAmount() + "대 희망");

        if (items.getStatus().equals("possible")) {
            holder.Img_deal.setImageResource(R.drawable.deal_possible);
        } else if (items.getStatus().equals("ing")) {
            holder.Img_deal.setImageResource(R.drawable.deal_ing);
        } else if (items.getStatus().equals("finish")) {
            holder.Img_deal.setImageResource(R.drawable.deal_finish);
        }

//        holder.Img_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, CPI_Focus.class);
//                intent.putExtra("Goods_Pk", items.getPk());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_GameName, Txt_title, Txt_Address, Txt_GameCount, Txt_Memo;
        ImageView Img_deal;

        public ViewHolder(View itemView) {
            super(itemView);
            Txt_GameName = (TextView) itemView.findViewById(R.id.txt_gamename);
            Txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            Txt_Memo = (TextView) itemView.findViewById(R.id.txt_memo);
            Txt_Address = (TextView) itemView.findViewById(R.id.txt_address);
            Txt_GameCount = (TextView) itemView.findViewById(R.id.txt_gamecount);
            Img_deal = (ImageView) itemView.findViewById(R.id.img_deal);
        }

    }
}
