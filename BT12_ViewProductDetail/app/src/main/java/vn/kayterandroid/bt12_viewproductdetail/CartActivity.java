package vn.kayterandroid.bt12_viewproductdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.kayterandroid.bt12_viewproductdetail.dao.CartItemDatabase;
import vn.kayterandroid.bt12_viewproductdetail.model.CartItem;
import vn.kayterandroid.bt12_viewproductdetail.model.Food;
import vn.kayterandroid.bt12_viewproductdetail.recyclerview.CartAdapter;
import vn.kayterandroid.bt12_viewproductdetail.recyclerview.MyAdapter;
import vn.kayterandroid.bt12_viewproductdetail.utils.APIService;
import vn.kayterandroid.bt12_viewproductdetail.utils.RetrofitClient;

public class CartActivity extends AppCompatActivity {
    List<CartItem> cartItems = new ArrayList<>();
    CartAdapter cartAdapter;
    RecyclerView recyclerViewCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems);
        cartItems = CartItemDatabase.getInstance(this).cartItemDAO().getAll();
        cartAdapter = new CartAdapter(this);

        cartAdapter.setData(cartItems);
        recyclerViewCartItems.setHasFixedSize(false);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewCartItems.setAdapter(cartAdapter);
    }
}