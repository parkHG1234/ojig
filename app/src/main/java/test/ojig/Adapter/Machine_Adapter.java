package test.ojig.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.ojig.Machine.Machine_Focus;
import test.ojig.Model.Machine_Model;
import test.ojig.R;

public class Machine_Adapter extends RecyclerView.Adapter<Machine_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Machine_Model> arrData;
    int item_layout;

    public Machine_Adapter(Context c, ArrayList<Machine_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_machine, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Machine_Model items = arrData.get(position);
        holder.tv_title.setText(items.getTitle());
        holder.tv_machine.setText(items.getMachine());
        holder.tv_price.setText(items.getPrice() + "원");
        holder.tv_count.setText(items.getCount() + "개");
        holder.tv_address.setText(items.getAddress());



        Drawable mDefaultBackground = context.getResources().getDrawable(R.drawable.area);

        Glide.with(items.getActivity()).load("http://13.209.35.228:8080/Img_Machine/" + items.getMachine_Pk() + "_1.jpg")
                .error(mDefaultBackground)
                .into(holder.img_machine);

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Machine_Focus.class);
                intent.putExtra("Machine_Pk", items.getMachine_Pk());
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
        private TextView tv_title, tv_machine, tv_price, tv_count, tv_address;
        private ImageView img_machine;
        public ViewHolder(View itemView) {
            super(itemView);
            Layout = (LinearLayout) itemView.findViewById(R.id.layout);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_machine = (TextView) itemView.findViewById(R.id.tv_machine);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            img_machine = (ImageView) itemView.findViewById(R.id.img_machine);
        }

    }
}
