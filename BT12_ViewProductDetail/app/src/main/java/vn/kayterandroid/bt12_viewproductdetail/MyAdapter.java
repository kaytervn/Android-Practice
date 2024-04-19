package vn.kayterandroid.bt12_viewproductdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.kayterandroid.bt12_viewproductdetail.model.Food;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Food> listFoods;

    public MyAdapter(Context context, List<Food> listFoods) {
        this.context = context;
        this.listFoods = listFoods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.food_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textTitle.setText(listFoods.get(position).getTitle());
        holder.textPrice.setText(listFoods.get(position).getPrice() + " $");
        Glide.with(context)
                .load(listFoods.get(position).getImage())
                .into(holder.imagePicture);
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }
}
