package net.kartikchawla.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   // private TextView userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView password1,email1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginButton);
        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = email1.getText().toString();
                String password = password1.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!userName.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
                        Toast.makeText(MainActivity.this, "Email Verified !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
        public void onsignupClick(View view){
            Intent registrationIntent = new Intent(MainActivity.this, SignUpActivity.class);
            MainActivity.this.startActivity(registrationIntent);

        }
}
