package vn.kayterandroid.foodappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.kayterandroid.foodappdemo.utils.APIService;
import vn.kayterandroid.foodappdemo.utils.RetrofitClient;
import vn.kayterandroid.foodappdemo.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    APIService apiService;
    ImageView imagePicture;
    TextView textName, textEmail, textPassword;
    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mapping();
        String id = SessionManager.getInstance(getApplicationContext()).getId();
        apiService = RetrofitClient.getAPIService();
        Call<ResponseBody> call = apiService.getUser(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        JsonObject userObject = new Gson().fromJson(json, JsonObject.class)
                                .getAsJsonObject("user");
                        textName.setText(userObject.get("name").getAsString());
                        textEmail.setText(userObject.get("email").getAsString());
                        textPassword.setText(userObject.get("password").getAsString());
                        if (userObject.get("image").getAsString().length() > 0) {
                            Glide.with(getApplicationContext())
                                    .load(userObject.get("image").getAsString())
                                    .into(imagePicture);
                        }
                        imagePicture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
//                                startActivity(intent);
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

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.getInstance(getApplicationContext()).clearLoginUser();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void mapping() {
        imagePicture = findViewById(R.id.imagePicture);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        buttonLogout = findViewById(R.id.buttonLogout);
    }
}