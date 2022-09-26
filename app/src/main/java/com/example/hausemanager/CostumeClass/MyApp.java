package com.example.hausemanager.CostumeClass;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyApp extends Application {

    public static String myApp_Tag;
    public static String myApp_PassingKeyPerson = "Person";

    public static FirebaseDatabase myApp_DBRef;
    public static FirebaseAuth myApp_Auth;
    public static FirebaseFirestore myApp_CollStore;
    public static FirebaseStorage myApp_Storage;

    @Override
    public void onCreate() {
        super.onCreate();

        myApp_Auth = FirebaseAuth.getInstance();
        myApp_CollStore = FirebaseFirestore.getInstance();
        myApp_DBRef = FirebaseDatabase.getInstance();
        myApp_Storage = FirebaseStorage.getInstance();


    }






}
