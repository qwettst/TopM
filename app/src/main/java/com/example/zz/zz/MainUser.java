package com.example.zz.zz;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.zz.zz.Auth_User.SignIn;
import com.example.zz.zz.LK_User.Create_LK;
import com.example.zz.zz.LK_User.LK;
import com.example.zz.zz.Show_Review.allReview;
import com.example.zz.zz.Show_Review.myReview;
import com.example.zz.zz.chat.user_chat_list;
import com.example.zz.zz.database.DatabaseUserProfileHelper;
import com.example.zz.zz.model.UserProfile_DB;
import com.example.zz.zz.model.getAllReview.User;
import com.facebook.login.LoginManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainUser extends AppCompatActivity {

   public MainUser(){

    }


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;

    private String sIdU, sFirstname, sLastname, sAuth;

    private DatabaseUserProfileHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);


        findViewById(R.id.search_form).setVisibility(View.GONE);
        Intent intent = getIntent();
        sAuth=intent.getStringExtra("mSign");




        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout , R.string.openDrwm,R.string.closeDrwm);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        nvDrawer=(NavigationView)findViewById(R.id.nv);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon((getResources().getDrawable(R.drawable.ic_menu)));

        setupDrawerContent(nvDrawer);

        db=new DatabaseUserProfileHelper(this);

        Class fragmentClass=allReview.class;

        if(!sAuth.equals("VK"))
            actToFragment(fragmentClass,checkAuth(sAuth));
        else
            getMeInfo();

    }

    @Override
    public void onBackPressed()
    {
        Fragment myFragment=null;
        Class fragmentClass = null;
        fragmentClass=allReview.class;
        try{
            myFragment=(Fragment) fragmentClass.newInstance();
            if(fragmentClass!=null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
                setTitle("Отзывы");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_main, menu);
        return true;
    }

    public void setFragment(Fragment myFragment){
        if(myFragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
       int id=item.getItemId();
        switch (id){
            case R.id.action_search:
                if(findViewById(R.id.search_form).getVisibility()!=View.VISIBLE)
                    findViewById(R.id.search_form).setVisibility(View.VISIBLE);
                else findViewById(R.id.search_form).setVisibility(View.GONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItemDrawer(MenuItem item)
    {
        Class fragmentClass = null;
        int id = item.getItemId();
        switch (id) {
            case R.id.myReview:
                fragmentClass=allReview.class;
                break;
            case R.id.chat:
                fragmentClass=user_chat_list.class;
                break;
            case R.id.meReview:
                fragmentClass=myReview.class;
                break;
            case R.id.lk:
                fragmentClass=LK.class;
                break;
            case R.id.signout: {
                signOutAct();
            }
                break;
            case R.id.signin:
                signInAct();
                break;
            case R.id.create_lk:
                fragmentClass= Create_LK.class;
                break;
            default:
                fragmentClass=allReview.class;
                break;


        }
        actToFragment(fragmentClass,checkAuth(sAuth));
        mDrawerLayout.closeDrawers();
    }




    private void signInAct() {
        Intent intent=new Intent(MainUser.this,SignIn.class);
        startActivity(intent);
    }



    private void setupDrawerContent(NavigationView navigateionView){
        navigateionView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }



    private void signOutAct() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        VKSdk.logout();
        Intent intent=new Intent(this,SignIn.class);
        startActivity(intent);
    }

    private int checkAuth(String sAuth){
        int ret=0;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        switch (sAuth) {
            case "guest":
                nvDrawer.getMenu().clear();
                nvDrawer.inflateMenu(R.menu.drawermenu_logout);
                sIdU="0";
                sFirstname="0";
                sLastname="0";
                ret=1;
                break;
            case "VK":
                ret=1;
                getMeInfo();
                break;
            case "G+": {
                ret=1;
                nvDrawer.getMenu().clear();
                nvDrawer.inflateMenu(R.menu.drawermenu);
                sIdU = user.getUid();
                String[] sArray = user.getDisplayName().split(" ");
                sFirstname=sArray[0];
                sLastname=sArray[1];
                userProfileAuth();
                break;
            }
            case "FB": {
                ret=1;
                nvDrawer.getMenu().clear();
                nvDrawer.inflateMenu(R.menu.drawermenu);
                sIdU = user.getUid();
                String[] sArray = user.getDisplayName().split(" ");
                sFirstname=sArray[0];
                sLastname=sArray[1];
                userProfileAuth();
                break;
            }
        }
        return ret;
    }

    private void getMeInfo() {
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
            private static final String TAG = "myLogs";
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONObject r = response.json.getJSONArray("response").getJSONObject(0);
                    // Здесь обрабатываем полученный response.
                    Log.d(TAG,"response: " + r);
                    sIdU=r.getString("id");
                    sFirstname=r.getString("first_name");
                    sLastname=r.getString("last_name");
                    userProfileAuth();
                    Class fragmentClass=allReview.class;
                    actToFragment(fragmentClass,1);
                } catch( JSONException e ) {
                    Log.e(TAG,e.getMessage(), e);
                }
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);

            }

            @Override
            public void onError(VKError error) {
                new AlertDialog.Builder(MainUser.this).setMessage(error.errorMessage).show();
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                // Неудачная попытка. В аргументах имеется номер попытки и общее их количество.

            }
        });
    }

    private void userProfileAuth()
    {
        UserProfile_DB user=new UserProfile_DB();
        if(db.getUserById(sIdU)==null){



            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

            if(BuildConfig.DEBUG){
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
            }

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit rftiUser = new Retrofit.Builder()
                    .baseUrl("http://94.251.14.36:8080/TopMaster/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build();

            User uLogin=new User();

            uLogin.setName(sFirstname);
            uLogin.setSurname(sLastname);
            uLogin.setEmail(sIdU);

            LoginUser loginUser = rftiUser.create(LoginUser.class);


            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(uLogin);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println("json " + jsonString);



           final Call<User> call = loginUser.createUser(jsonString);


            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG","response " + response.body());
                        UserProfile_DB uDB=new UserProfile_DB();
                        uDB.setIdU(response.body().getIdUser());
                        uDB.setLastname(response.body().getSurname());
                        uDB.setFirstname(response.body().getName());
                        uDB.setEmail(response.body().getEmail());
                        db.insertUser(uDB);
                    } else {
                        Log.d("TAG","response code " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Tag","failure " + t);
                }
            });
        }
        List<UserProfile_DB> us=new ArrayList<UserProfile_DB>();
        us.addAll(db.getAllUserrs());
        us.size();

    }


    private void actToFragment(Class fragmentClass,int id) {
        Bundle bundle=new Bundle();
        switch (id) {
            case -1:
                bundle.putString("mSign",sAuth);
                bundle.putInt("uID", -1);
                break;
            default:
                UserProfile_DB uDb=db.getUserById(sIdU);
                bundle.putString("mSign",sAuth);
                bundle.putInt("uID", uDb.getIdU());
                 break;

        }

        if(fragmentClass!=null) {
            try {
                Fragment myFragment = null;
                myFragment = (Fragment) fragmentClass.newInstance();
                myFragment.setArguments(bundle);
                setFragment(myFragment);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


}
