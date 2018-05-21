package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import test.ojig.Model.Store_Model;
import test.ojig.R;

public class Store_Adapter extends RecyclerView.Adapter<Store_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Store_Model> arrData;
    int item_layout;

    public Store_Adapter(Context c, ArrayList<Store_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_store, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        final Store_Model items = arrData.get(position);
//        holder.tv_name.setText(items.getName());
//        holder.tv_title.setText(items.getTitle());
//        holder.tv_address.setText(items.getAddress());
//        holder.tv_gamecount.setText(items.getAmount() + "대 희망");
////
//        if (items.getStatus().equals("possible")) {
//            holder.img_deal.setImageResource(R.drawable.deal_possible);
//        } else if (items.getStatus().equals("ing")) {
//            holder.img_deal.setImageResource(R.drawable.deal_ing);
//        } else if (items.getStatus().equals("finish")) {
//            holder.img_deal.setImageResource(R.drawable.deal_finish);
//        }
//
//        holder.Layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Store_Focus.class);
//                intent.putExtra("Sell_Pk", items.getSell_Pk());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override

    public int getItemCount() {

//        return this.arrData.size();
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }

    }
}
