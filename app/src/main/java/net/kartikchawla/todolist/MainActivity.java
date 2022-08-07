package net.kartikchawla.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // private TextView userNameEdt, passwordEdt;
    private TextView passwordTextView, emailTextView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void loginUser(android.view.View view) {
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                progressBar.setVisibility(View.VISIBLE);
                loginApi();
            } else {
                Toast.makeText(MainActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginApi() {
        String url = String.format("https://script.google.com/macros/s/AKfycbzOkf5ldgNYJD71bQMIBZxtDaJbWQDhLGb_isI3_g_8-Sg8zbvUoxD8SpCrkZ-kMoRzaQ/exec?email=%s&password=%s", emailTextView.getText().toString(), passwordTextView.getText().toString());
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                try {
                    JSONObject body = new JSONObject(str);
                    JSONObject data = new JSONObject(body.getString("data"));
                    progressBar.setVisibility(View.GONE);
                    if (body.get("status").equals("Error")) {
                        Toast.makeText(MainActivity.this, data.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        saveUserDetails(data.get("name").toString(), data.get("email").toString());
                        Intent toDoListIntent = new Intent(getApplicationContext(), ToDoListActivity.class);
                        toDoListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(toDoListIntent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    textView.setText("Error in calling api");
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserDetails(String userName, String userEmail) {
        SharedPreferences sharedPrefs = getSharedPreferences("ToDoListUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("userName", userName);
        editor.putString("userEmail", userEmail);
        editor.apply();
    }

    public void onsignupClick(View view) {
        Intent registrationIntent = new Intent(MainActivity.this, SignUpActivity.class);
        MainActivity.this.startActivity(registrationIntent);

    }
}
