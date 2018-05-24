package test.ojig.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import test.ojig.Model.Buy_Model;
import test.ojig.Model.Join_MyGame_Model;
import test.ojig.R;
import test.ojig.User.Join;

/**
 * Created by 박효근 on 2018-05-20.
 */

public class Join_MyGame_Adapter extends RecyclerView.Adapter<Join_MyGame_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Join_MyGame_Model> arrData;
    int item_layout;

    public Join_MyGame_Adapter(Context c, ArrayList<Join_MyGame_Model> arr, int item_layout) {
        this.context = c;
        this.arrData = arr;
        this.item_layout = item_layout;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_join_mygame, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Join_MyGame_Model items = arrData.get(position);
        Join.mygame_name[position] = holder.Edit_name.getText().toString();
        Join.mygame_gamecount[position] = holder.Edit_count.getText().toString();
    }

    @Override

    public int getItemCount() {

        return this.arrData.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText Edit_name, Edit_count;
        public ViewHolder(View itemView) {
            super(itemView);
            Edit_name = (EditText) itemView.findViewById(R.id.edit_name);
            Edit_count = (EditText) itemView.findViewById(R.id.edit_count);
        }
    }
}
