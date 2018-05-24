package test.ojig.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import test.ojig.Model.Store_Model;
import test.ojig.R;
import test.ojig.Store.Store_Focus;

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
        final Store_Model items = arrData.get(position);
        holder.tv_title.setText(items.getTitle());
        if(items.getType().equals("deal")){
            holder.tv_type.setText("매매");
        }else if(items.getType().equals("rent")){
            holder.tv_type.setText("임대");
        }
        holder.tv_space.setText(items.getSpace() + "평");
        holder.tv_layer.setText(items.getLayer() + "층");
        holder.tv_address.setText(items.getAddress());
//
        if (items.getStatus().equals("possible")) {
            holder.img_deal.setImageResource(R.drawable.deal_possible);
        } else if (items.getStatus().equals("ing")) {
            holder.img_deal.setImageResource(R.drawable.deal_ing);
        } else if (items.getStatus().equals("finish")) {
            holder.img_deal.setImageResource(R.drawable.deal_finish);
        }

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Store_Focus.class);
                intent.putExtra("Store_Pk", items.getStore_Pk());
                context.startActivity(intent);
            }
        });
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout Layout;
        private TextView tv_title, tv_type, tv_space, tv_layer, tv_address;
        private ImageView img_deal, img_store;
        private Button img_call;

        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout) itemView.findViewById(R.id.layout);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_space = (TextView) itemView.findViewById(R.id.tv_space);
            tv_layer = (TextView) itemView.findViewById(R.id.tv_layer);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            img_deal = (ImageView) itemView.findViewById(R.id.img_deal);

        }

    }
}
