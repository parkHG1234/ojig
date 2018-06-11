package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import test.ojig.Model.Others_Menu_Model;
import test.ojig.R;

/**
 * Created by 박효근 on 2018-06-11.
 */

public class Others_Menu_Adapter extends RecyclerView.Adapter<Others_Menu_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Others_Menu_Model> arrData;
    int item_layout;
    public Others_Menu_Adapter(Context c, ArrayList<Others_Menu_Model> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Others_Menu_Model items = arrData.get(position);
        holder.Txt_Name.setText(items.getName());
        holder.Txt_Price.setText(setPoint_rest(items.getPrice())+"원");

//        holder.Layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(context, Promotion_Focus.class);
////                intent.putExtra("Promotion_Pk", items.getPk());
////                items.getActivity().startActivity(intent);
////                items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Name, Txt_Price;
        public ViewHolder(View itemView) {
            super(itemView);
            Txt_Name = (TextView)itemView.findViewById(R.id.name);
            Txt_Price = (TextView)itemView.findViewById(R.id.price);
        }

    }
    //금액 콤마 표현
    public String setPoint_rest(String point){
        DecimalFormat df = new DecimalFormat("#,##0");

        return df.format(Integer.parseInt(point))+"";
    }
}
