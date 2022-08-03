package net.kartikchawla.todolist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {

    private EditText passwordTextView, emailTextView, nameTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        emailTextView = findViewById(R.id.emailEditText);
        passwordTextView = findViewById(R.id.passwordEditText);
        nameTextView = findViewById(R.id.nameEditText);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void signupApi() {
        String url = String.format("https://script.google.com/macros/s/AKfycbzOkf5ldgNYJD71bQMIBZxtDaJbWQDhLGb_isI3_g_8-Sg8zbvUoxD8SpCrkZ-kMoRzaQ/exec?signup=signup&name=%s&email=%s&password=%s", nameTextView.getText().toString(), emailTextView.getText().toString(), passwordTextView.getText().toString());
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                try {
                    JSONObject body = new JSONObject(str);
                    JSONObject data = new JSONObject(body.getString("data"));
                    progressBar.setVisibility(View.GONE);
                    if (body.get("status").equals("Error")) {
                        Toast.makeText(SignUpActivity.this, data.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    textView.setText("Error in calling api");
                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSignupUser(android.view.View view) {
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String name = nameTextView.getText().toString();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            Toast.makeText(SignUpActivity.this, "Please enter user name and password and name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupApi();
        } else {
            Toast.makeText(SignUpActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
        }
    }
}