package fiuba.ordertracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fiuba.ordertracker.helpers.ImageLoadTask;
import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.pojo.Product;

/**
 *
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    public void setData(List<Product> data) {
        this.data = data;
    }

    List<Product> data = Collections.emptyList();
    private String category = "";
    private Fragment parentFragment;

    public List<Product> getOriginalData() {
        return originalData;
    }

    public void setOriginalData(List<Product> originalData) {
        this.originalData = originalData;
    }


    List<Product> originalData = Collections.emptyList();


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductListAdapter(Context context, List<Product> data, Fragment parentFragment) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.parentFragment = parentFragment;
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
        holder.setProduct(current);
        holder.nameAndBrand.setText(current.getNombre() + ", " + current.getMarca());
        holder.category.setText(this.category);
        holder.price.setText(current.getPrecio());
        holder.stock.setText(current.stockState());
        new ImageLoadTask(current.getUrlImageMini(), holder.thumbnail).execute();

        final Fragment _parentFragment = this.parentFragment;

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ***********");
                Product currentClicked = data.get(position);

                /*Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("url_image_normal", data.get(position).getUrlImageNormal()); // TODO show picture
                intent.putExtra("name", data.get(position).getNombre());
                intent.putExtra("brand", data.get(position).getMarca());
                intent.putExtra("description", data.get(position).getDescripcion());
                intent.putExtra("price", data.get(position).getPrecio());
                intent.putExtra("category", category);
                intent.putExtra("availability", data.get(position).stockState()); // TODO it has to be availability, not stock
                view.getContext().startActivity(intent);*/


                ProductsFragment instanceFragment = (ProductsFragment) _parentFragment;

                Bundle args= new Bundle();
                args.putString("url_image_normal", currentClicked.getUrlImageNormal());
                args.putString("name", currentClicked.getNombre());
                args.putString("brand", currentClicked.getMarca());
                args.putString("description", currentClicked.getDescripcion());
                args.putString("price", currentClicked.getPrecio());
                args.putString("category", category);
                args.putString("availability", currentClicked.stockState());
                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setArguments(args);
                instanceFragment.replaceFragment(productDetailFragment, true); // it was false, I changed it

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
        private Product product;

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

        public void setProduct(Product p) {
            this.product = p;
            final Product _product = this.product;

            Button button = (Button) itemView.findViewById(R.id.product_list_add_to_cart);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = ((Activity) itemView.getContext()).getFragmentManager().beginTransaction();
                    AddProductToCartFragment newFragment = AddProductToCartFragment.newInstance(_product);
                    newFragment.show(ft, "dialog");
                }
            });
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
