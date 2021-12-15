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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chocoverse-20dd8-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_login);


        final EditText adminName = findViewById(R.id.editTextName);
        final EditText pass = findViewById(R.id.editTextPassword);
        final Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = adminName.getText().toString();
                final String password = pass.getText().toString();
                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLogin.this, "please enter an email address and a password", Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(name).exists()){
                                if(snapshot.child(name).child("password").getValue().equals(password)){
                                    Toast.makeText(AdminLogin.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                }else {
                                    Toast.makeText(AdminLogin.this, "Wrong password ! Try again", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(AdminLogin.this, "This admin name does not exist !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
       /* login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String adr = address.getText().toString();
                final String password = pass.getText().toString();
                if (adr.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLogin.this, "please enter an email address and a password", Toast.LENGTH_SHORT).show();
                }else*/

            }
}