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
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
            holder.btn_type.setText("매매");
            holder.tv_price.setText(convertHangul(items.getPrice()));
        }else if(items.getType().equals("rent")){
            holder.btn_type.setText("임대");
            holder.tv_price.setText(items.getDeposit()+"  /  "+items.getRental());
        }
        holder.tv_space.setText(items.getSpace() + "평");
        holder.tv_layer.setText("/  " + items.getLayer() + "층");
        holder.tv_address.setText(items.getAddress());
//
        if (items.getStatus().equals("possible")) {
            holder.img_deal.setImageResource(R.drawable.deal_possible);
        } else if (items.getStatus().equals("ing")) {
            holder.img_deal.setImageResource(R.drawable.deal_ing);
        } else if (items.getStatus().equals("finish")) {
            holder.img_deal.setImageResource(R.drawable.deal_finish);
        } else{
            holder.img_deal.setVisibility(View.INVISIBLE);
        }

        if(items.getVideo().equals("true")){
            Glide.with(items.getActivity()).load("http://13.209.35.228:8080/Video_Store/thumbnail/"+items.getStore_Pk()+".jpg")
                    .into(holder.img_store);
        }
        else{
            holder.img_store.setBackgroundColor(items.getActivity().getResources().getColor(R.color.black));
        }


        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(items.getStatus().equals("wait")){
                    Toast.makeText(context,"확인이 필요한 글입니다.", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(context, Store_Focus.class);
                    intent.putExtra("Store_Pk", items.getStore_Pk());
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
        private TextView tv_title, tv_space, tv_layer, tv_address, tv_price;
        private ImageView img_deal, img_store;
        private Button btn_type, img_call;

        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout) itemView.findViewById(R.id.layout);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            btn_type = (Button) itemView.findViewById(R.id.btn_type);
            tv_space = (TextView) itemView.findViewById(R.id.tv_space);
            tv_layer = (TextView) itemView.findViewById(R.id.tv_layer);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            img_deal = (ImageView) itemView.findViewById(R.id.img_deal);
            img_store = (ImageView) itemView.findViewById(R.id.img_store);
        }

    }
    public String convertHangul(String money){
        String[] han1 = {"","1","2","3","4","5","6","7","8","9"};
        String[] han2 = {"","십","백","천"}; String[] han3 = {"","만","억","조","경"};
        StringBuffer result = new StringBuffer(); int len = money.length();
        for(int i=len-1; i>=0; i--){
            result.append(han1[Integer.parseInt(money.substring(len-i-1, len-i))]);
            if(Integer.parseInt(money.substring(len-i-1, len-i)) > 0) result.append(han2[i%4]);
            if(i%4 == 0) result.append(han3[i/4]); } return result.toString();
    }
}
