package com.example.zz.zz.Auth_User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zz.zz.MainUser;
import com.example.zz.zz.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class  SignIn extends SocialAuth implements  View.OnClickListener {

    private String[] scope=new String[] {};

    private ProgressBar progress;

    private ImageButton signGoogle;
    private Button guestSignIn;
    private ImageButton signFB;
    private ImageButton signVK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        ImageView ivBackground;


        ivBackground=(ImageView) findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);


        findViewById(R.id.signGoogle).setOnClickListener(this);
        findViewById(R.id.guestSignIn).setOnClickListener(this);
        findViewById(R.id.signFB).setOnClickListener(this);
        findViewById(R.id.signVK).setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.progress);

        signGoogle=(ImageButton) findViewById(R.id.signGoogle);
        guestSignIn=(Button)  findViewById(R.id.guestSignIn);
        signFB=(ImageButton)  findViewById(R.id.signFB);
        signVK=(ImageButton)findViewById(R.id.signVK);




        setProgress(progress,signGoogle,signFB,guestSignIn,signVK);

        signOut();


    }


    @Override
    public void onStart() {
        super.onStart();

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signGoogle:
                googleAuth();
                FacebookAuth();
                updateProgress();
                signIn();
                break;
            case R.id.guestSignIn:
                signOut();
                Intent intent=new Intent(SignIn.this ,MainUser.class);
                intent.putExtra("mSign","guest");
                startActivity(intent);
                break;
            case R.id.signFB: {
                googleAuth();
                FacebookAuth();
                Button signFaceBook = findViewById(R.id.signFaceBook);
                updateProgress();
                signFaceBook.performClick();
                break;
            }
            case R.id.signVK:
                VKSdk.login(this,scope);
                break;
        }
    }

    public void updateProgress(){
        progress.setVisibility(View.VISIBLE);
        signGoogle.setVisibility(View.GONE);
        signFB.setVisibility(View.GONE);
        guestSignIn.setVisibility(View.GONE);
        signVK.setVisibility(View.GONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                Intent intent=new Intent(SignIn.this ,MainUser.class);
                intent.putExtra("mSign","VK");
                startActivity(intent);
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(),"Ошибка авторизации",Toast.LENGTH_LONG).show();
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);

    }





}