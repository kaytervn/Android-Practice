package vn.kayterandroid.foodappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.kayterandroid.foodappdemo.model.User;
import vn.kayterandroid.foodappdemo.utils.APIService;
import vn.kayterandroid.foodappdemo.utils.RetrofitClient;
import vn.kayterandroid.foodappdemo.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    TextView textRegister;
    Button buttonLogin;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService = RetrofitClient.getAPIService();
                User loginUser = new User(inputEmail.getText().toString(), inputPassword.getText().toString());
                Call<ResponseBody> call = apiService.login(loginUser);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String json = response.body().string();
                                JsonObject userObject = new Gson().fromJson(json, JsonObject.class)
                                        .getAsJsonObject("user");
                                SessionManager.getInstance(getApplicationContext()).saveLoginDetails(
                                        userObject.get("email").getAsString(),
                                        userObject.get("password").getAsString()
                                );
                                Log.d("Login", SessionManager.getInstance(getApplicationContext()).getEmail());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("Failed to call API", t.getMessage());
                    }
                });
            }
        });
    }

    void mapping() {
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textRegister = findViewById(R.id.textRegister);
    }
}