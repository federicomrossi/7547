package fiuba.ordertracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.pojo.Client;

/**
 *
 */
public class ProductCategoryListAdapter extends RecyclerView.Adapter<ProductCategoryListAdapter.MyViewHolder> {

    private LayoutInflater inflater;


    List<Categorie> data = Collections.emptyList();

    List<Categorie> originalData = Collections.emptyList();

    public void setOriginalData(List<Categorie> originalData) {
        this.originalData = originalData;
    }

    public List<Categorie> getOriginalData() {
        return originalData;
    }

    private Fragment parentFragment;
    public void setData(List<Categorie> data) {
        this.data = data;
    }

    public ProductCategoryListAdapter(Context context, List<Categorie> data, Fragment fragment) {
        inflater = LayoutInflater.from(context);
        this.data = data;

        this.parentFragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_category_list_row, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Categorie current = this.data.get(position);
        holder.category.setText(current.getNombre());

        final Fragment _parentFragment = this.parentFragment;

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ***********");
                Categorie currentClicked = data.get(position);

                /*Intent intent = new Intent(view.getContext(), ProductListActivity.class);
                intent.putExtra("category", currentClicked.getId());
                intent.putExtra("categoryName", currentClicked.getNombre());
                intent.putExtra("name", data.get(position).getApenom());
                intent.putExtra("address", data.get(position).getDireccion());
                intent.putExtra("distance", data.get(position).getTelefono());

                view.getContext().startActivity(intent);*/


                ProductsFragment instanceFragment = (ProductsFragment) _parentFragment;

                Bundle args= new Bundle();
                args.putString("category", currentClicked.getId());
                args.putString("categoryName", currentClicked.getNombre());
                ProductListFragment productListFragment = new ProductListFragment();
                productListFragment.setArguments(args);
                instanceFragment.replaceFragment(productListFragment, true); // it was false, I changed it
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView category;
        private OnItemClickListener clickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = (TextView) itemView.findViewById(R.id.product_category_list_row_category);

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
