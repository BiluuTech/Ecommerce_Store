package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biluutech.ztshopping.Activities.LoginActivity;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminLoginEmail, adminLoginPassword;
    private Button adminLoginBtn;

    private TextView userLoginTV;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminLoginEmail = findViewById(R.id.admin_email_login_Text);
        adminLoginPassword = findViewById(R.id.admin_password_login_Text);
        adminLoginBtn = findViewById(R.id.adminLoginBtn);

        userLoginTV = findViewById(R.id.user_login_TV);

        loadingBar = new ProgressDialog(this);

        userLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminLoginActivity.this, LoginActivity.class));

            }
        });

        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                RootRef = FirebaseDatabase.getInstance().getReference().child("Admin");
                RootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String adminEmail = adminLoginEmail.getText().toString();
                        String adminPassword = adminLoginPassword.getText().toString();

                        String fEmail = snapshot.child("email").getValue().toString();
                        String fpassword = snapshot.child("password").getValue().toString();

                        if (fEmail.equals(adminEmail) && fpassword.equals(adminPassword)){
                            loadingBar.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminLoginActivity.this,AdminHomeActivity.class));
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}