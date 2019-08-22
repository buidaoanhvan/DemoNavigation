package com.example.demonavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private TextView resiter;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.et_EmailLogin);
        inputPassword = (EditText) findViewById(R.id.et_PassLogin);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        resiter = (TextView) findViewById(R.id.tv_ResiterNow);

        resiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentResiter();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (password.length() > 0 && email.length() > 0) {
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Sai Email Hoặc Mật Khẩu", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "Nhâp Email Và Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
             }
        });
    }

    private void IntentResiter() {
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
    }

    protected void onResume() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        super.onResume();
    }
}
