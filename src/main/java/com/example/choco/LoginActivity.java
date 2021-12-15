package com.example.choco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.acitvity_login);

        final EditText adresse = findViewById(R.id.editTextName);
        final EditText pass = findViewById(R.id.editTextPassword);
        final Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String adr = adresse.getText().toString();
                final String password = pass.getText().toString();
                if (adr.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please enter an email address and a password", Toast.LENGTH_SHORT).show();
                } else {

                    fAuth.signInWithEmailAndPassword(adr, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        openAdminLoginActivity();
        openForgotPassActivity();
        openSignupActivity();

    }

    public void openSignupActivity() {
        TextView signup = (TextView) findViewById(R.id.textViewSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(LoginActivity.this, SignupActivity.class);

                startActivity(nextScreen);
            }
        });
    }

    public void openHomeActivity() {
        Button signin = (Button) findViewById(R.id.login);

        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(nextScreen);
            }
        });
    }
    public void openAdminLoginActivity(){
        TextView admin = findViewById(R.id.textAdmin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(LoginActivity.this,AdminLogin.class);
                startActivity(nextScreen);
            }
        });
    }

    public void openForgotPassActivity() {
        TextView forgetpass = (TextView) findViewById(R.id.textViewForgetPass);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(LoginActivity.this, ForgotPassActivity.class);

                startActivity(nextScreen);
            }
        });
    }
}
 /* databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(adr)){
                                final String getPassword = snapshot.child("password").getValue(String.class);
                                if(getPassword.equals(password)){
                                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

