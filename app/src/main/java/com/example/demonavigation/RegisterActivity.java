package com.example.demonavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity{

    private EditText inputEmail, inputPassword, inputCPassword;
    private FirebaseAuth auth;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.et_EmailLogin);
        inputPassword = (EditText) findViewById(R.id.et_PassLogin);
        inputCPassword = (EditText) findViewById(R.id.et_Cpass);
        btnSignUp = (Button) findViewById(R.id.btn_Login);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                final String Cpassword = inputCPassword.getText().toString();

                if(email.length()> 0 && password.length()>5){
                    if(password.equals(Cpassword)){
                        auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Email Này Đã Sử Dụng", Toast.LENGTH_LONG).show();
                                }else {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }if (!password.equals(Cpassword)){
                        Toast.makeText(RegisterActivity.this, "Không Khớp Mật Khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Nhập Đầy Đủ Thồng Tin, Mật Khẩu Phải Lớn Hơn 6 Ký Tự", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
