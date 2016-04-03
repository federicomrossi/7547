package fiuba.ordertracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fiuba.ordertracker.helpers.ImageLoadTask;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.pojo.Product;

/**
 *
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Product> data = Collections.emptyList();
    private String category = "";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductListAdapter(Context context, List<Product> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_list_row, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Product current = this.data.get(position);
        holder.nameAndBrand.setText(current.getNombre() + ", " + current.getMarca());
        //holder.description.setText(current.getDescripcion());
        holder.category.setText(this.category);
        holder.price.setText(current.getPrecio());
        holder.stock.setText(current.stockState());
        new ImageLoadTask(current.getUrlImageMini(), holder.thumbnail).execute();
        //holder.thumbnail.setImageResource(current.getSomething()); // TODO implement method in Product class

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ***********");

                // TODO store info into the intent and start activity ProductDetailActivity
                //Intent intent = new Intent(view.getContext(), ClientDetailActivity.class); // TODO! Change activity!
                /*intent.putExtra("name", data.get(position).getApenom());
                intent.putExtra("address", data.get(position).getDireccion());
                intent.putExtra("distance", data.get(position).getTelefono());*/

                //view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameAndBrand, description, category, price, stock;
        ImageView thumbnail;

        private OnItemClickListener clickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameAndBrand = (TextView) itemView.findViewById(R.id.product_list_row_name_brand);
            category = (TextView) itemView.findViewById(R.id.product_list_row_category);
            //description = (TextView) itemView.findViewById(R.id.product_list_row_description);
            price = (TextView) itemView.findViewById(R.id.product_list_row_price_value);
            stock = (TextView) itemView.findViewById(R.id.product_list_row_stock);
            thumbnail = (ImageView) itemView.findViewById(R.id.product_list_row_thumbnail);

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
