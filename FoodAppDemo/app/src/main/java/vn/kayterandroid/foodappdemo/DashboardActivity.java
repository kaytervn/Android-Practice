package vn.kayterandroid.foodappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import vn.kayterandroid.foodappdemo.model.Food;
import vn.kayterandroid.foodappdemo.model.User;
import vn.kayterandroid.foodappdemo.utils.APIService;
import vn.kayterandroid.foodappdemo.utils.RetrofitClient;
import vn.kayterandroid.foodappdemo.utils.SessionManager;

public class DashboardActivity extends AppCompatActivity {
    APIService apiService;
    ImageView imagePicture;
    TextView textName;
    List<Food> listFoods = new ArrayList<>();
    MyAdapter foodsAdapter;
    RecyclerView recyclerViewFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mapping();
        getFoods();
        String id = SessionManager.getInstance(getApplicationContext()).getId();
        if (id == "") {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            apiService = RetrofitClient.getAPIService();
            Call<ResponseBody> call = apiService.getUser(id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String json = response.body().string();
                            JsonObject userObject = new Gson().fromJson(json, JsonObject.class).getAsJsonObject("user");
                            textName.setText(userObject.get("name").getAsString());
                            if (userObject.get("image").getAsString().length() > 0) {
                                Glide.with(getApplicationContext()).load(userObject.get("image").getAsString()).into(imagePicture);
                            }
                            imagePicture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Failed to call API", t.getMessage());
                }
            });
        }
    }

    void getFoods() {
        apiService = RetrofitClient.getAPIService();
        Call<ResponseBody> call = apiService.getFoods();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONArray foodsArray = jsonObject.getJSONArray("foods");
                        for (int i = 0; i < foodsArray.length(); i++) {
                            JSONObject foodObject = foodsArray.getJSONObject(i);
                            listFoods.add(new Food(
                                    foodObject.getString("image"),
                                    foodObject.getString("title"),
                                    foodObject.getString("price"))
                            );
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    foodsAdapter = new MyAdapter(DashboardActivity.this, listFoods);
                    recyclerViewFoods.setHasFixedSize(true);
                    recyclerViewFoods.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                    recyclerViewFoods.setAdapter(foodsAdapter);
                    foodsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed to call API", t.getMessage());
            }
        });
    }

    void mapping() {
        imagePicture = findViewById(R.id.imagePicture);
        textName = findViewById(R.id.textName);
        recyclerViewFoods = findViewById(R.id.recyclerViewFoods);
    }
}