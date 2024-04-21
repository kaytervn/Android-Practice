package vn.kayterandroid.bt12_viewproductdetail.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.kayterandroid.bt12_viewproductdetail.R;
import vn.kayterandroid.bt12_viewproductdetail.model.CartItem;
import vn.kayterandroid.bt12_viewproductdetail.model.Food;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<CartItem> cartItems;

    public void setData(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carrt_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textTitle.setText(cartItems.get(position).getTitle());
        holder.textPrice.setText(String.valueOf(cartItems.get(position).getPrice()));
        Glide.with(context)
                .load(cartItems.get(position).getImage())
                .into(holder.imagePicture);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagePicture;
        TextView textTitle;
        TextView textPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePicture = itemView.findViewById(R.id.imagePicture);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }
}
