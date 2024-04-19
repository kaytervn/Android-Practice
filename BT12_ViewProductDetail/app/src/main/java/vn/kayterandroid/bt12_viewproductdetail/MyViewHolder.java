package vn.kayterandroid.bt12_viewproductdetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imagePicture;
    TextView textTitle, textPrice;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imagePicture = itemView.findViewById(R.id.imagePicture);
        textTitle = itemView.findViewById(R.id.textTitle);
        textPrice = itemView.findViewById(R.id.textPrice);
    }
}
