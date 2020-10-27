package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.biluutech.ztshopping.Activities.LoginActivity.PREFS_NAME;

public class SignupActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText phoneText, codeText;
    private Button createAccountBtn;
    private String checker = "", phoneNumber = "";
    private RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4, codeAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;

//    PhoneNumberModel phoneNumberModel;

    private DatabaseReference signupRef;


    private EditText nameSignupText, emailSignupText, passwordSignupText;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        signupRef = FirebaseDatabase.getInstance().getReference().child("Users");
        loadingBar = new ProgressDialog(this);

        phoneText = findViewById(R.id.phoneText);
        codeText = findViewById(R.id.codeText);
        createAccountBtn = findViewById(R.id.createAccountBtn);
        relativeLayout1 = findViewById(R.id.phoneAuth);
        relativeLayout2 = findViewById(R.id.nameAuth);
        relativeLayout3 = findViewById(R.id.emailAuth);
        relativeLayout4 = findViewById(R.id.passwordAuth);
        codeAuth = findViewById(R.id.codeAuth);
        nameSignupText = findViewById(R.id.name_signup_Text);
        emailSignupText = findViewById(R.id.email_signup_Text);
        passwordSignupText = findViewById(R.id.password_signup_Text);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneText);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(nameSignupText.getText().toString())) {

                    nameSignupText.setError("Please enter your name");

                } else if (TextUtils.isEmpty(phoneText.getText().toString())) {

                    phoneText.setError("Please enter your number");

                } else if (TextUtils.isEmpty(passwordSignupText.getText().toString())) {

                    passwordSignupText.setError("Please enter your password");

                }
                else{

                    phoneNumber = ccp.getFullNumberWithPlus();

                    signupRef.child(phoneNumber).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()){
                                Toast.makeText(SignupActivity.this, "User already registered with this number !", Toast.LENGTH_SHORT).show();
                            }

                            else{


                                if (createAccountBtn.getText().equals("Submit") || checker.equals("Code Sent")) {


                                    String verificationCode = codeText.getText().toString();

                                    if (verificationCode.equals("")) {
                                        Toast.makeText(SignupActivity.this, "Please enter verification code.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingBar.setTitle("Phone Number Verification");
                                        loadingBar.setMessage("Please wait, while we are verifying your code");
                                        loadingBar.setCanceledOnTouchOutside(false);
                                        loadingBar.show();

                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                                        signInWithPhoneAuthCredential(credential);

                                    }


                                } else {
                                    phoneNumber = ccp.getFullNumberWithPlus();
                                    if (!phoneNumber.equals("")) {

                                        loadingBar.setTitle("Code Verification");
                                        loadingBar.setMessage("Please wait, while we are verifying your phone number");
                                        loadingBar.setCanceledOnTouchOutside(false);
                                        loadingBar.show();

                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                phoneNumber,        // Phone number to verify
                                                60,                 // Timeout duration
                                                TimeUnit.SECONDS,   // Unit of timeout
                                                SignupActivity.this,               // Activity (for callback binding)
                                                mCallbacks);        // OnVerificationStateChangedCallbacks

                                    } else {
                                        Toast.makeText(SignupActivity.this, "Please enter valid number", Toast.LENGTH_SHORT).show();
                                    }
                                }


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

                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayout3.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                createAccountBtn.setText("Continue");
                codeAuth.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId = s;
                mResendToken = forceResendingToken;

                relativeLayout1.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                checker = "Code Sent";
                createAccountBtn.setText("Submit");
                codeAuth.setVisibility(View.VISIBLE);

                loadingBar.dismiss();
                Toast.makeText(SignupActivity.this, "Code has been sent, Please check", Toast.LENGTH_SHORT).show();

            }

        };
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            phoneNumber = ccp.getFullNumberWithPlus();

                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("phoneNumberKey", phoneNumber);
                            editor.commit();

                            String value = settings.getString("phoneNumberKey", "");


                            HashMap<String, Object> userMap = new HashMap<>();

                            userMap.put("uId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            userMap.put("name", nameSignupText.getText().toString());
                            userMap.put("email", emailSignupText.getText().toString());
                            userMap.put("password", passwordSignupText.getText().toString());
                            userMap.put("phone", phoneNumber);
                            userRef.child(phoneNumber).updateChildren(userMap);

                            loadingBar.dismiss();
                            Toast.makeText(SignupActivity.this, "Congratulations, You are logged in successfully", Toast.LENGTH_SHORT).show();

                            sendUserToMainActivity();


                        } else {

                            loadingBar.dismiss();
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void sendUserToMainActivity() {

        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
            finish();

        }
    }
}