package com.example.choco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    EditText email;
    Button send;
    String adr;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEnterEmail);
        send = findViewById(R.id.ButtonSendEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        adr = email.getText().toString();
        if(adr.isEmpty()){
            Toast.makeText(ForgotPassActivity.this, "Enter your email address first", Toast.LENGTH_SHORT).show();
        }else{
            forgotPassword();
        }
    }

    private void forgotPassword() {
        fAuth.sendPasswordResetEmail(adr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, "check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassActivity.this,LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(ForgotPassActivity.this, "Error !! "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}