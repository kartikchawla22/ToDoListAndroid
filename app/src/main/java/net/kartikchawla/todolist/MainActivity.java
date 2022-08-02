package net.kartikchawla.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // private TextView userNameEdt, passwordEdt;
    private TextView passwordTextView, emailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
    }

    public void loginUser(android.view.View view) {
            String email = emailTextView.getText().toString();
            String password = passwordTextView.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
                        if(body.get("status").equals("Error")) {
                            Toast.makeText(MainActivity.this, data.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent toDoListIntent = new Intent(getApplicationContext(), ToDoListActivity.class);
                            toDoListIntent.putExtra("name", data.get("name").toString());
                            toDoListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(toDoListIntent);
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

    public void onsignupClick(View view) {
        Intent registrationIntent = new Intent(MainActivity.this, SignUpActivity.class);
        MainActivity.this.startActivity(registrationIntent);

    }
}
