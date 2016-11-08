package vaibhav.iiitd.com.todolist;

/**
 * Created by Vaibhav on 31-10-2016.
 */

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    ArrayList<String[]> data_list = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView rvi_title;
        public TextView rvi_date;

        public ViewHolder(View v) {
            super(v);
            rvi_title = (TextView) v.findViewById(R.id.rvi_title);
            rvi_date = (TextView) v.findViewById(R.id.rvi_date);
        }
    }

    public ToDoAdapter(ArrayList<String[]> myDataset) {
        this.data_list = myDataset;
    }

    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String []item = data_list.get(position);
        holder.rvi_title.setText(item[0]);
        holder.rvi_date.setText(item[1]);
        holder.rvi_title.setId(position);
        holder.rvi_date.setId(position + 10000);
    }

    @Override
    public int getItemCount() {
        if(data_list == null)
            return 0;
        return data_list.size();
    }

}
