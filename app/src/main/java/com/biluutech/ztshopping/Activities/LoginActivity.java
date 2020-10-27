package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biluutech.ztshopping.Admin.AdminLoginActivity;
import com.biluutech.ztshopping.HomeActivity;
import com.biluutech.ztshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private TextView adminLoginTV;
    private TextView dontHaveAccountTV;

    private EditText phoneLoginText, passwordLoginText, codeTextLogin;
    private Button loginBtn;

    private String databasePhone, databasePass;

    public static final String PREFS_NAME = "MyApp_Settings";

    private DatabaseReference loginRef;

    private CountryCodePicker ccpLogin;

    private String checker = "", phoneNumber = "";

    private RelativeLayout relativeLayoutPhoneLogin,relativeLayoutPassLogin, codeAuthLogin;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginRef = FirebaseDatabase.getInstance().getReference().child("Users");

        loadingBar = new ProgressDialog(this);

        adminLoginTV = findViewById(R.id.admin_login_TV);
        dontHaveAccountTV = findViewById(R.id.dont_have_account_TV);
        phoneLoginText = findViewById(R.id.phoneLoginText);
        passwordLoginText = findViewById(R.id.password_login_Text);
        codeTextLogin = findViewById(R.id.codeTextLogin);

        relativeLayoutPhoneLogin = findViewById(R.id.phoneAuthLogin);
        relativeLayoutPassLogin = findViewById(R.id.passwordAuthLogin);
        codeAuthLogin = findViewById(R.id.codeAuthLogin);
        loginBtn = findViewById(R.id.loginBtn);
        ccpLogin = findViewById(R.id.ccpLogin);
        ccpLogin.registerCarrierNumberEditText(phoneLoginText);

        phoneNumber = ccpLogin.getFullNumberWithPlus();


        adminLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, AdminLoginActivity.class));
            }
        });

        dontHaveAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(phoneLoginText.getText().toString())){

                    phoneLoginText.setError("Please enter your number");

                }

                else if (TextUtils.isEmpty(passwordLoginText.getText().toString())) {
                    passwordLoginText.setError("Please enter your password");
                }

                else {
                    phoneNumber = ccpLogin.getFullNumberWithPlus();

                    loginRef.child(phoneNumber).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                databasePhone = snapshot.child("phone").getValue().toString();
                                databasePass = snapshot.child("password").getValue().toString();


                                if (databasePass.equals(passwordLoginText.getText().toString())) {

                                    if (loginBtn.getText().equals("Submit") || checker.equals("Code Sent"))
                                    {


                                        String verificationCode = codeTextLogin.getText().toString();

                                        if (verificationCode.equals("")) {
                                            Toast.makeText(LoginActivity.this, "Please enter verification code.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            loadingBar.setTitle("Phone Number Verification");
                                            loadingBar.setMessage("Please wait, while we are verifying your code");
                                            loadingBar.setCanceledOnTouchOutside(false);
                                            loadingBar.show();

                                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                                            signInWithPhoneAuthCredential(credential);

                                        }


                                    }

                                    else {
                                        phoneNumber = ccpLogin.getFullNumberWithPlus();
                                        if (!phoneNumber.equals("")) {

                                            loadingBar.setTitle("Code Verification");
                                            loadingBar.setMessage("Please wait, while we are verifying your phone number");
                                            loadingBar.setCanceledOnTouchOutside(false);
                                            loadingBar.show();

                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                    phoneNumber,        // Phone number to verify
                                                    60,                 // Timeout duration
                                                    TimeUnit.SECONDS,   // Unit of timeout
                                                    LoginActivity.this,               // Activity (for callback binding)
                                                    mCallbacks);        // OnVerificationStateChangedCallbacks

                                        } else {
                                            Toast.makeText(LoginActivity.this, "Please enter valid number", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                } else {
                                    Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(LoginActivity.this, "No user exist with this number", Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });


    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            signInWithPhoneAuthCredential(phoneAuthCredential);

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            relativeLayoutPhoneLogin.setVisibility(View.VISIBLE);
            relativeLayoutPassLogin.setVisibility(View.VISIBLE);
            loginBtn.setText("Continue");
            codeAuthLogin.setVisibility(View.GONE);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationId = s;
            mResendToken = forceResendingToken;

            relativeLayoutPassLogin.setVisibility(View.GONE);
            relativeLayoutPhoneLogin.setVisibility(View.GONE);

            checker = "Code Sent";
            loginBtn.setText("Submit");
            codeAuthLogin.setVisibility(View.VISIBLE);

            loadingBar.dismiss();
            Toast.makeText(LoginActivity.this, "Code has been sent, Please check", Toast.LENGTH_SHORT).show();

        }

    };
}


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            phoneNumber = ccpLogin.getFullNumberWithPlus();

                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("phoneNumberKey", phoneNumber);
                            editor.commit();

                            String value = settings.getString("phoneNumberKey", "");
//                            Toast.makeText(LoginActivity.this, value, Toast.LENGTH_LONG).show();



                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Congratulations, You are logged in successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));


                        } else {

                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();

        }
    }

}