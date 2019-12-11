package com.example.evitar.LoginFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evitar.MenuActivity;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private ProgressBar pbar;

    SharedPreferences mUser;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginbutton);
        pbar=findViewById(R.id.progressBar);

        mUser = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mUser.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                userLogin();
            }
        });



    }

    private void userLogin(){
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        Log.d("cc", user);
        Log.d("cc", pass);
        SignIn signIn=new SignIn(user,pass);

        Call<User> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(signIn);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    User user = new User(response.body().getId(), response.body().getUsername(), response.body().getToken());
                    mEditor.putInt("user_id", user.getId());
                    mEditor.putString("token", user.getToken());
                    mEditor.commit();
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    pbar.setVisibility(View.GONE);
                }else{
                    pbar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pbar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}
