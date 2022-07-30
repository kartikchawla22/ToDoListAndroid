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

import java.text.ParseException;

public class SignUpActivity extends AppCompatActivity {

    private TextView passwordEdt,emailEdt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        emailEdt = findViewById(R.id.emailid);
        passwordEdt = findViewById(R.id.newpassword);
        registerBtn = findViewById(R.id.signupbutton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userName = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();


                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!userName.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
                        Toast.makeText(SignUpActivity.this, "Email Verified !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}