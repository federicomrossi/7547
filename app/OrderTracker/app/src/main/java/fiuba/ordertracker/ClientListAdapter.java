package fiuba.ordertracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<ClientModel> data = Collections.emptyList();

    public ClientListAdapter(Context context, List<ClientModel> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.client_list_row, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ClientModel current = this.data.get(position);
        holder.name.setText(current.name);
        //holder.address.setText(current.address);
        holder.distance.setText(current.distance);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        TextView distance;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.client_list_row_name);
            address = (TextView) itemView.findViewById(R.id.client_list_row_address);
            distance = (TextView) itemView.findViewById(R.id.client_list_row_distance);
        }
    }
}
