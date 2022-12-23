package com.example.teammanagementv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private Button loginButton;
    private Button signUpButton;

    private EditText username;
    private EditText password;

    private final APIClient apiClient = new APIClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // go directly to that view if username and pass exist
        String usernameSp = sharedPref.getString("username", null);
        String passwordSp = sharedPref.getString("password", null);
        if (usernameSp != null && passwordSp != null) {
            Intent intent = new Intent(getApplicationContext(), TeamMemberActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        username = findViewById(R.id.usernameIdLogin);
        password = findViewById(R.id.passwordIdLogin);
        username.setText("mkingg");
        password.setText("000000");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  usernameInput = username.getText().toString();
                String  passwordInput = password.getText().toString();
                Toast.makeText(getApplicationContext(), "user inputted " + usernameInput + " " + passwordInput,
                        Toast.LENGTH_LONG).show();

                // perform username, password validation
                if (usernameInput.length() > 5 && passwordInput.length() > 5) {
                    Toast.makeText(getApplicationContext(), "valid user and pass, will send a post request to server",
                            Toast.LENGTH_LONG).show();
                    AtomicReference<Boolean> api_response = new AtomicReference<>(false);
                    try {
                        apiClient.loginUserAsync(usernameInput, passwordInput, new Callback() {
                            @Override
                            public void onFailure(final Call call, IOException e) {
                                // Error

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // For the example, you can show an error dialog or a toast
                                        // on the main UI thread
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                // Do something with the response
                                int code = response.code();
                                api_response.set(code == 200);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // For the example, you can show an error dialog or a toast
                                        // on the main UI thread
                                        if (response.code() != 200) {
                                            return;
                                        }
                                        Toast.makeText(getApplicationContext(), "user logged in successfully",
                                                Toast.LENGTH_LONG).show();

                                        // save username and pass to shared prefs, go to next activity(start intent)
                                        editor.putString("username", usernameInput);
                                        editor.putString("password", passwordInput);
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), TeamMemberActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}