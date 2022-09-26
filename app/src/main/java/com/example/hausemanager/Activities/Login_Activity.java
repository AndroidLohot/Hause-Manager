package com.example.hausemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hausemanager.CostumeClass.MyApp;
import com.example.hausemanager.CostumeClass.Person;
import com.example.hausemanager.R;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class Login_Activity extends AppCompatActivity {

    private EditText etPersonName, etPersonPhoneNumber;
    private String personName, personPhoneNumber, personGander;
    private RadioGroup rgGander;
    private RadioButton rbMale, rbFemale;
    private Button btnSubmit;
    private CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = MyApp.myApp_Auth.getCurrentUser();

        if (user != null) {

            startActivity(new Intent(Login_Activity.this, Home_Activity.class));
            finish();

        }

        initialView();

        rgGander.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton g = findViewById(i);

                personGander = g.getText().toString().trim();

                Log.d("MyTag", "(Radio Button Group) Gander Select " + personGander);

                Toast.makeText(Login_Activity.this, "" + g.getText().toString().trim(), Toast.LENGTH_SHORT).show();


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                personName = etPersonName.getText().toString().trim();
                personPhoneNumber = ccp.getFullNumberWithPlus().toString().trim();

                Log.d("MyTag", "Phone Number " + personPhoneNumber);


                if (personName.isEmpty()) {

                    etPersonName.setError("Enter the name");
                    return;

                } else if (personPhoneNumber.isEmpty()) {

                    etPersonPhoneNumber.setError("Enter the phone number");
                    return;


                } else  if ( ! (rbMale.isChecked() || rbFemale.isChecked()) ) {

                    Toast.makeText(Login_Activity.this, "Select gander", Toast.LENGTH_SHORT).show();

                    return;


                } else  if ( personPhoneNumber.length() != 13 ) {

                    etPersonPhoneNumber.setError("Incorrect phone number");

                    return;


                } else {


                    Person person = new Person(null, personName, personPhoneNumber, personGander);

                    Intent intent = new Intent(Login_Activity.this, ManageOtp_Activity.class);

                    intent.putExtra(MyApp.myApp_PassingKeyPerson, person);

                    startActivity(intent);

                    finish();


                }
            }
        });


    }

    // initialization of views
    private void initialView() {

        etPersonName = findViewById(R.id.etPersonName_LoginActivity);
        etPersonPhoneNumber = findViewById(R.id.etPersonPhoneNumbers_LoginActivity);
        rgGander = findViewById(R.id.rgGander_LoginActivity);
        rbMale = findViewById(R.id.rbMale_LoginActivity);
        rbFemale = findViewById(R.id.rbFemale_LoginActivity);
        btnSubmit = findViewById(R.id.btnSubmit_LoginActivity);
        ccp = findViewById(R.id.ccp_LoginActivity);

        ccp.registerCarrierNumberEditText(etPersonPhoneNumber);


    }

}