package com.example.zz.zz.Auth_User;


import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by Pavel on 05.04.2018.
 */

public class VKAuth extends android.app.Application {

    @Override
    public void onCreate(){
        super.onCreate();
        VKSdk.initialize(this);
        vkAccessTokenTracker.startTracking();
    }

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent=new Intent(VKAuth.this,SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };
}
