package com.example.evitar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userLogin();
            }
        });



    }

    private void userLogin(){
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        Log.d("cc", user);
        Log.d("cc", pass);

        Call<User> call = RetrofitClient
                .getInstance()
                .getApi()
                .login("Dumbrica", "password");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = new User(response.body().getId(), response.body().getUsername(), response.body().getToken());
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
