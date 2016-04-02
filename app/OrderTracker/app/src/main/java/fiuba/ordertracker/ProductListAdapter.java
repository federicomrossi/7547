package fiuba.ordertracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.pojo.Product;

/**
 *
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Product> data = Collections.emptyList();

    public ProductListAdapter(Context context, List<Product> data) {
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

        Product current = this.data.get(position);
        holder.name.setText(current.getNombre());
        holder.brand.setText(current.getMarca());
        holder.description.setText(current.getDescripcion());
        holder.category.setText(current.getCategoria());
        holder.price.setText(current.getPrecio());
        holder.stock.setText(current.getStock());

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ***********");

                Intent intent = new Intent(view.getContext(), ClientDetailActivity.class);
                /*intent.putExtra("name", data.get(position).getApenom());
                intent.putExtra("address", data.get(position).getDireccion());
                intent.putExtra("distance", data.get(position).getTelefono());*/

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, brand, description, category, price, stock;
        private OnItemClickListener clickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            /*name = (TextView) itemView.findViewById(R.id.product_list_row_name);
            brand = (TextView) itemView.findViewById(R.id.product_list_row_brand);
            description = (TextView) itemView.findViewById(R.id.product_list_row_description);
            category = (TextView) itemView.findViewById(R.id.product_list_row_category);
            price = (TextView) itemView.findViewById(R.id.product_list_row_price);
            stock = (TextView) itemView.findViewById(R.id.product_list_row_stock);*/

            // Set listener to the item view
            itemView.setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition()); // or getLayoutPosition() ??
        }
    }

    /**
     * Interface for handling clicks
     */
    private interface OnItemClickListener {
        /**
         * Called when the view is clicked
         * @param view that is clicked
         * @param position of the clicked item
         */
        public void onItemClick(View view, int position);

    }

}
