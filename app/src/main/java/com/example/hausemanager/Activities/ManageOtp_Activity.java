package com.example.hausemanager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hausemanager.CostumeClass.MyApp;
import com.example.hausemanager.CostumeClass.Person;
import com.example.hausemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;

import java.util.concurrent.TimeUnit;

public class ManageOtp_Activity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnSubmit;
    private String otpID, otp;
    private String phoneNumber;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        MyApp.myApp_Tag = "ManageOtp";

        person = (Person) getIntent().getSerializableExtra(MyApp.myApp_PassingKeyPerson);

        phoneNumber = person.getPersonPhoneNumber();

        etOtp = findViewById(R.id.etOtp_MOActivity);
        btnSubmit = findViewById(R.id.btnSubmit_MOActivity);

        initialOtp();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                otp = etOtp.getText().toString().trim();

                if (otp.isEmpty()) {

                    etOtp.setError("Enter the otp");
                    return;

                } else if (otp.length() != 6) {

                    etOtp.setError("Enter the correct length of phone number");
                    return;


                } else {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpID, otp);

                    signInWithPhoneAuthCredential(credential);


                }
            }
        });


    }

    private void initialOtp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(MyApp.myApp_Auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpID = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ManageOtp_Activity.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


        MyApp.myApp_Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(MyApp.myApp_Tag, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            changeActivity(user);

                        } else {

                            // Sign in failed, display a message and update the UI
                            Log.w(MyApp.myApp_Tag, "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }

                        }
                    }
                });


    }

    private void changeActivity(FirebaseUser user) {

        String userID = user.getUid().toString().trim();

        person.setPersonID(userID);

        Task<Void> userInfo = MyApp.myApp_CollStore.collection("User_Info").document(phoneNumber).set(person);

        userInfo.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Intent intent = new Intent(ManageOtp_Activity.this, Home_Activity.class);

                    intent.putExtra(MyApp.myApp_PassingKeyPerson, person);

                    startActivity(intent);

                    finish();

                } else {

                    Toast.makeText(ManageOtp_Activity.this, "User information not save", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}