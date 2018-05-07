package com.example.zz.zz.Auth_User;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zz.zz.MainUser;
import com.example.zz.zz.R;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.CallbackManager;
import com.vk.sdk.VKSdk;

public class SocialAuth extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    private ProgressBar progress;

    private ImageButton signGoogle;
    private Button guestSignIn;
    private ImageButton signFB;
    private ImageButton signVK;

    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    public void setProgress(ProgressBar pb, ImageButton bG, ImageButton bFB, Button bGuest, ImageButton bVK){
        progress=pb;
        signGoogle=bG;
        guestSignIn=bGuest;
        signFB=bFB;
        signVK=bVK;
    }


    // Google Auth
    public void googleAuth(){


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.serverIDTokenG))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }
    // Google Auth
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,"G+");
                        } else {
                            Toast.makeText(getApplicationContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
                            // If sign in fails, display a message to the user.
                            updateUI(null,null);
                        }

                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
                // Google Sign In failed, update UI appropriately
                updateUI(null,null);

            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    //FaceBook
    public void FacebookAuth()
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Views


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.signFaceBook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Отмена авторизации",Toast.LENGTH_LONG).show();
                // [START_EXCLUDE]
                updateUI(null,null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
                // [START_EXCLUDE]
                updateUI(null,null);
                // [END_EXCLUDE]
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,"FB");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
                            updateUI(null,null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }



    public void updateUI(FirebaseUser user, String sAuth) {
        if (user != null) {
            Intent intent=new Intent(SocialAuth.this ,MainUser.class);
            intent.putExtra("mSign",sAuth);
            startActivity(intent);
        } else {
            progress.setVisibility(View.GONE);
            signGoogle.setVisibility(View.VISIBLE);
            signFB.setVisibility(View.VISIBLE);
            guestSignIn.setVisibility(View.VISIBLE);
            signVK.setVisibility(View.VISIBLE);

        }
    }



    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);;
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        VKSdk.logout();
        updateUI(null,null);
    }



}
