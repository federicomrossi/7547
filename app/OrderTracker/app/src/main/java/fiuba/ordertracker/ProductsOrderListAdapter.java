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

import fiuba.ordertracker.pojo.Product;

/**
 * Created by scampa on 10/4/2016.
 */
public class ProductsOrderListAdapter extends RecyclerView.Adapter<ProductsOrderListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Product> data = Collections.emptyList();

    public ProductsOrderListAdapter(Context context, List<Product> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_list_row, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ProductsOrderListAdapter ***********");

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
            clickListener.onItemClick(view, getAdapterPosition());
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
