package com.example.android.stringer.database;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static FirebaseUtil firebaseUtil;
    public static ArrayList<Client> mClients;

    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static Activity caller;
    private static final int RC_SIGN_IN = 123;

    public static FirebaseStorage mStorage;
    public static StorageReference mStorageRef;

    private FirebaseUtil(){};

    public static void openFbReference(String ref/*, final Activity callerActivity*/){
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseAuth = FirebaseAuth.getInstance();
            /*caller = callerActivity;
            mAuthListener = new FirebaseAuth.AuthStateListener(){

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null){
                        FirebaseUtil.signIn();
                    }Toast.makeText(callerActivity.getBaseContext(),"Welcome back!", Toast.LENGTH_LONG).show();
                }
            };
            connectStorage();*/
        }
        mClients = new ArrayList<Client>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

 /*   private static void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        );
    }
    public static void attachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void removeListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }*/
    public static void connectStorage(){
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference().child("Rackets");
    }
}

