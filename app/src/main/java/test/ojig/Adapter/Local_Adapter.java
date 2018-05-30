package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import test.ojig.Model.Local_Model;
import test.ojig.R;
import test.ojig.User.Setting;

import static test.ojig.Local.Local_Focus.graph_width;

public class Local_Adapter extends RecyclerView.Adapter<Local_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Local_Model> arrData;
    int item_layout;
    private  ViewHolder holder1;
    private Double division = 0.0;
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
        final Local_Model items = arrData.get(position);
        holder1 = holder;
        holder.Txt_Game.setText(items.getGame());
        holder.Txt_Count.setText(items.getCount());
        if(position == 0){
            holder.Seek_Graph.setProgress(100);
        }
        else{
            division = Double.parseDouble(items.getCount()) / Double.parseDouble(items.getMax());
            Log.i("test112", division+"");
            holder.Seek_Graph.setProgress((int)(division*100));
        }
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Game, Txt_Count;
        SeekBar Seek_Graph;

        public ViewHolder(View itemView) {
            super(itemView);
            Txt_Game = (TextView) itemView.findViewById(R.id.txt_game);
            Txt_Count = (TextView) itemView.findViewById(R.id.txt_count);
            Seek_Graph = (SeekBar) itemView.findViewById(R.id.seek_graph);
            Seek_Graph.setThumb(null);
            Seek_Graph.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    return true;
                }
            });

        }
    }
}
