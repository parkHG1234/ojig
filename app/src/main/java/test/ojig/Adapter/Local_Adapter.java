package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import test.ojig.Model.Local_Model;
import test.ojig.R;

public class Local_Adapter extends RecyclerView.Adapter<Local_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Local_Model> arrData;
    int item_layout;

    public Local_Adapter(Context c, ArrayList<Local_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Local_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_local, parent, false);

        return new Local_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override

    public int getItemCount() {

//        return this.arrData.size();
        return 8;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout Layout;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
