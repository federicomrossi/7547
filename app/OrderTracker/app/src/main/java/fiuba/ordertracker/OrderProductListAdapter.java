package fiuba.ordertracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fiuba.ordertracker.helpers.ImageLoadTask;
import fiuba.ordertracker.pojo.Product;

/**
 *
 */
public class OrderProductListAdapter extends RecyclerView.Adapter<OrderProductListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Product> data = Collections.emptyList();
    private String category = "";
    private Fragment parentFragment;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public OrderProductListAdapter(Context context, List<Product> data, Fragment parentFragment) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.parentFragment = parentFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_product_list_row, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Product current = this.data.get(position);

        // Item context menu
        holder.setProduct(current);
        holder.nameAndBrand.setText(current.getNombre() + ", " + current.getMarca());
        holder.category.setText(this.category);
        holder.price.setText(current.getPrecio());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        new ImageLoadTask(current.getUrlImageMini(), holder.thumbnail).execute();
        //holder.thumbnail.setImageResource(current.getSomething()); // TODO implement method in Product class

        // Set listener to manage clicks on items from the RecyclerView
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("*********** Click on item ***********");

                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("url_image_normal", data.get(position).getUrlImageNormal()); // TODO show picture
                intent.putExtra("name", data.get(position).getNombre());
                intent.putExtra("brand", data.get(position).getMarca());
                intent.putExtra("description", data.get(position).getDescripcion());
                intent.putExtra("price", data.get(position).getPrecio());
                intent.putExtra("category", category);
                intent.putExtra("availability", data.get(position).stockState()); // TODO it has to be availability, not stock
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, android.widget.PopupMenu.OnMenuItemClickListener {
        TextView nameAndBrand, description, category, price, stock;
        ImageView thumbnail;
        ImageView mOverflowIcon;

        private OnItemClickListener clickListener;
        private Product product;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);
            /*itemView.setOnClickListener(this);
            this.mOverflowIcon = (ImageView) itemView.findViewById(R.id.orderProductContextMenu);
            mOverflowIcon.setOnClickListener(this);*/

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
        }

        public void setOnItemClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {

            /*if (view == mOverflowIcon) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(R.menu.menu_order_list_poduct_item);
                popup.setOnMenuItemClickListener(this);
                popup.show();
                return;
            }*/

            clickListener.onItemClick(view, getAdapterPosition()); // or getLayoutPosition() ??
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opciones del producto");
            menu.add(0, v.getId(), 0, "Modificar");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "Eliminar");
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }

    /**
     * Interface for handling clicks
     */
    private interface OnItemClickListener {
        /**
         * Called when the view is clicked
         *
         * @param view     that is clicked
         * @param position of the clicked item
         */
        public void onItemClick(View view, int position);

    }

}
