package com.example.choco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chocoverse-20dd8-default-rtdb.firebaseio.com/");
    static int  i =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        final EditText username = findViewById(R.id.editTextFullName);
        final EditText email = findViewById(R.id.editTextEmailRegis);
        final EditText address = findViewById(R.id.editTextAddress);
        final EditText phone = findViewById(R.id.editTextPhone);
        final EditText pass = findViewById(R.id.editTextPasswordRegis);
        final Button register = findViewById(R.id.ButtonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getText().toString();
                final String gmail = email.getText().toString();
                final String adr = address.getText().toString();
                final String number = phone.getText().toString();
                final String password = pass.getText().toString();

                if(name.isEmpty()||gmail.isEmpty()||adr.isEmpty()||number.isEmpty()||password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    fAuth.createUserWithEmailAndPassword(gmail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            databaseReference. child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(adr)){
                                        Toast.makeText(SignupActivity.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                                    }else{
                                        databaseReference.child("users").child("user"+i).child("username").setValue(name);
                                        databaseReference.child("users").child("user"+i).child("email").setValue(gmail);
                                        databaseReference.child("users").child("user"+i).child("address").setValue(adr);
                                        databaseReference.child("users").child("user"+i).child("phone").setValue(number);
                                        databaseReference.child("users").child("user"+i).child("password").setValue(password);

                                        Toast.makeText(SignupActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    i++;
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });


                }
            }
        });

    }
}