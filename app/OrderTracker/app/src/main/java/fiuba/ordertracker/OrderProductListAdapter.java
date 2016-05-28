package fiuba.ordertracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import fiuba.ordertracker.helpers.ImageLoadTask;
import fiuba.ordertracker.pojo.Categorie;
import fiuba.ordertracker.pojo.OrderProduct;
import fiuba.ordertracker.pojo.Product;
import fiuba.ordertracker.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class OrderProductListAdapter extends RecyclerView.Adapter<OrderProductListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<OrderProduct> data = Collections.emptyList();
    List<Categorie> dataCategories = Collections.emptyList();
    private String category = "";
    private final OrderProductListAdapter _self = this;
    private Fragment parentFragment;
    private Context context ;
    private Boolean allowActions = false;

    public void setData(List<OrderProduct> data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /*public OrderProductListAdapter(Context context, List<Product> data, Fragment parentFragment) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.parentFragment = parentFragment;
    }*/

    public OrderProductListAdapter(Context context, List<OrderProduct> data, List<Categorie> listCategories, Fragment parentFragment, Boolean allowActions) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.dataCategories = listCategories;
        this.parentFragment = parentFragment;
        this.allowActions = allowActions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_product_list_row, null);
        MyViewHolder holder = new MyViewHolder(view, this.allowActions);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final OrderProduct current = this.data.get(position);

        // Item context menu
        holder.setProduct(current);
        holder.nameAndBrand.setText(current.getNombre() + ", " + current.getMarca());
        holder.category.setText(getProductCategory(current.getCategoria()).getNombre());
        holder.productAmount.setText(current.getCantidad());

        // Price formatter
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es_AR"));
        currencyFormatter.setMaximumFractionDigits(2);

        // Set price with discount
        String appliedDiscount = current.getAppliedDiscount();

        // Format subtotal including discount
        Double subtotalWithDiscount = Double.parseDouble(current.getSubtotalWithDiscount());
        holder.price.setText(currencyFormatter.format(subtotalWithDiscount));

        // Set price without discount
        if (appliedDiscount.equals("0")){
            holder.noDiscountAmount.setVisibility(View.GONE);
        } else {
            Double subtotalWithoutDiscount = Double.parseDouble(current.getSubtotalWithoutDiscount());
            holder.noDiscountAmount.setText("(" + currencyFormatter.format(subtotalWithoutDiscount) + ")");
        }
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

                /*Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("url_image_normal", data.get(position).getUrlImageNormal()); // TODO show picture
                intent.putExtra("name", data.get(position).getNombre());
                intent.putExtra("brand", data.get(position).getMarca());
                intent.putExtra("description", data.get(position).getDescripcion());
                intent.putExtra("price", data.get(position).getPrecio());
                intent.putExtra("category", category);
                intent.putExtra("availability", data.get(position).stockState()); // TODO it has to be availability, not stock
                view.getContext().startActivity(intent);*/
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
        TextView productAmount;
        TextView noDiscountAmount;
        ImageView mOverflowIcon;

        private OnItemClickListener clickListener;
        private OrderProduct product;
        private Boolean allowActions;

        public MyViewHolder(View itemView, Boolean allowActions) {
            super(itemView);

            this.allowActions = allowActions;

            // Disallow context menu
            if(allowActions)
                itemView.setOnCreateContextMenuListener(this);

            /*itemView.setOnClickListener(this);
            this.mOverflowIcon = (ImageView) itemView.findViewById(R.id.orderProductContextMenu);
            mOverflowIcon.setOnClickListener(this);*/

            nameAndBrand = (TextView) itemView.findViewById(R.id.product_list_row_name_brand);
            category = (TextView) itemView.findViewById(R.id.product_list_row_category);
            price = (TextView) itemView.findViewById(R.id.product_list_row_price_value);
            stock = (TextView) itemView.findViewById(R.id.product_list_row_stock);
            thumbnail = (ImageView) itemView.findViewById(R.id.product_list_row_thumbnail);
            productAmount = (TextView) itemView.findViewById(R.id.textProductAmount);

            noDiscountAmount = (TextView) itemView.findViewById(R.id.textNoDiscountAmount);
            noDiscountAmount.setPaintFlags(noDiscountAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Set listener to the item view
            itemView.setOnClickListener(this);
        }

        public void setProduct(OrderProduct p) {
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
            final View view = v;
            OrderProduct order = data.get(getAdapterPosition());
            int productId = Integer.valueOf(order.getId());
            int orderId = Integer.valueOf(order.getIdOrden());
            menu.setHeaderTitle("Opciones del producto");
            menu.add(0, v.getId(), 0, "Modificar");//groupId, itemId, order, title
            menu.add(productId, v.getId(), orderId, "Eliminar").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String idProduct = String.valueOf(item.getGroupId());
                    String idOrder = String.valueOf(item.getOrder());
                    try {
                        Call<List<OrderProduct>> call = OrderService.getInstance().order.removeProductFromOrder(idProduct, idOrder);
                        call.enqueue(new Callback<List<OrderProduct>>() {
                            @Override
                            public void onResponse(Call<List<OrderProduct>> call, Response<List<OrderProduct>> response) {
                                ((TabActivity) view.getContext()).productAdded();
                                /*List<OrderProduct> list = response.body();
                                setData(list);
                                RecyclerView rv = (RecyclerView) view.findViewById(R.id.productsList);
                                rv.setAdapter(_self);*/
                            }

                            @Override
                            public void onFailure(Call<List<OrderProduct>> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        String as = "";
                    }
                    return false;
                }
            });

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


    private Categorie getProductCategory(String id) {
        for(Categorie o : this.dataCategories) {
            if(o != null && o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }
}
