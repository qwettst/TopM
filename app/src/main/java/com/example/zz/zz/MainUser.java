package com.example.zz.zz;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresApi;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.zz.zz.Auth_User.SignIn;
import com.example.zz.zz.LK_User.Create_LK;
import com.example.zz.zz.LK_User.LK;
import com.example.zz.zz.Show_Review.UserReview;
import com.example.zz.zz.Show_Review.allReview;
import com.example.zz.zz.Show_Review.myReview;
import com.example.zz.zz.chat.user_chat_list;
import com.example.zz.zz.database.DatabaseUserProfileHelper;
import com.example.zz.zz.model.UserProfile_DB;
import com.example.zz.zz.model.getAllReview.User;
import com.example.zz.zz.model.getSpecUser.GetSpecUser;
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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
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

    private ProgressBar progressBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;
    private FrameLayout frameLayout;

    private String sIdU, sFirstname, sLastname, sAuth;

    private int a,iSign=-1;

    private DatabaseUserProfileHelper db;

    private Button bSearch;
    private EditText etDatetime;
    private int access=0;




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


        nvDrawer.getMenu().clear();
        nvDrawer.inflateMenu(R.menu.drawermenu_without_lk);


        if(!sAuth.equals("VK"))
           checkAuth(fragmentClass,sAuth);
        else
            getMeInfo(fragmentClass);

        etDatetime=findViewById(R.id.s_daterev);
        final Calendar myCalendar = Calendar.getInstance();



        etDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainUser.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd-MM-yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        etDatetime.setText(sdf.format(myCalendar.getTime()));
                    }
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        etDatetime.setText("");
                    }
                });

                datePickerDialog.show();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Class fragmentClass = null;
        fragmentClass=allReview.class;
        actToFragment(fragmentClass,iSign);
        mDrawerLayout.closeDrawers();
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
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            case R.id.signout:
                signOutAct();
                Intent intent=new Intent(this,SignIn.class);
                startActivity(intent);
                break;
            case R.id.signin:
                signInAct();
                break;
            case R.id.create_lk:
                fragmentClass= Create_LK.class;
                break;
            case R.id.UserReview:
                fragmentClass= UserReview.class;
                break;
            default:
                fragmentClass=allReview.class;
                break;


        }
        if(fragmentClass!=null)
           actToFragment(fragmentClass,iSign);
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
    }

    private void checkAuth(Class fragmentClass, String sAuth){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        switch (sAuth) {
            case "guest":
                nvDrawer.getMenu().clear();
                nvDrawer.inflateMenu(R.menu.drawermenu_logout);
                sIdU="0";
                sFirstname="0";
                sLastname="0";
                iSign=-1;
                actToFragment(fragmentClass,iSign);
                break;
            case "VK":
                getMeInfo(fragmentClass);
                break;
            case "G+": {
                sIdU = user.getUid();
                String[] sArray = user.getDisplayName().split(" ");
                sFirstname=sArray[0];
                sLastname=sArray[1];
                iSign=1;
                userProfileAuth(fragmentClass);
                break;
            }
            case "FB": {
                sIdU = user.getUid();
                String[] sArray = user.getDisplayName().split(" ");
                sFirstname=sArray[0];
                sLastname=sArray[1];
                iSign=1;
                userProfileAuth(fragmentClass);
                break;
            }
        }
    }

    private void getMeInfo(final Class fragmentClass) {
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
                    iSign=1;
                    userProfileAuth(fragmentClass);
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
                actToFragment(fragmentClass,-1);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                // Неудачная попытка. В аргументах имеется номер попытки и общее их количество.
                actToFragment(fragmentClass,-1);
            }
        });
    }

    private void userProfileAuth(final Class fragmentClass)
    {

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
                        UserProfile_DB uDB;
                        uDB=db.getUserById(response.body().getIdUser());
                        if(uDB==null) {
                            uDB=new UserProfile_DB();
                            uDB.setIdU(response.body().getIdUser());
                            uDB.setLastname(response.body().getSurname());
                            uDB.setFirstname(response.body().getName());
                            uDB.setEmail(response.body().getEmail());
                            db.insertUser(uDB);
                        }
                        a = response.body().getIdUser();
                        actToFragment(fragmentClass,1);
                        nvDrawer.getMenu().clear();
                        nvDrawer.inflateMenu(R.menu.drawermenu_without_lk);
                        if(response.body().getAccess().getIdAccess()==3)
                        {
                            nvDrawer.getMenu().clear();
                            nvDrawer.inflateMenu(R.menu.drawermenu_mod);
                            access=1;
                        }
                        checkProfile(a);
                    } else {
                        Log.d("TAG","response code " + response.code());
                        actToFragment(fragmentClass,-1);
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Tag","failure " + t);
                    actToFragment(fragmentClass,-1);
                    Toasty.error(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_SHORT, true).show();
                }
            });


    }

    private void checkProfile(final int iUid){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
        }

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://94.251.14.36:8080/TopMaster/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();

        SpecUserGetter specUserGetter = retrofit.create(SpecUserGetter.class);

        final Call<GetSpecUser> specUserCall = specUserGetter.serviceUser(iUid);

        specUserCall.enqueue(new Callback<GetSpecUser>() {
            @Override
            public void onResponse(Call<GetSpecUser> call, Response<GetSpecUser> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG","response " + response.body());
                    UserProfile_DB uDB;
                    uDB=db.getUserById(response.body().getIdUser());
                    uDB.setUprofile(1);
                    db.updateUser(uDB,response.body().getIdUser());
                    nvDrawer.getMenu().clear();
                    nvDrawer.inflateMenu(R.menu.drawermenu);
                } else {
                    Log.d("TAG","response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSpecUser> call, Throwable t) {
                if(access!=1){
                    nvDrawer.getMenu().clear();
                    nvDrawer.inflateMenu(R.menu.drawermenu_without_lk);
                }
                Log.d("Tag","failure " + t);
            }
        });
    }



    private void actToFragment(Class fragmentClass,int id) {
        Bundle bundle=new Bundle();
        switch (id) {
            case -1:
                bundle.putString("mSign",sAuth);
                bundle.putInt("uID", -1);
                break;
            default:
                try {
                    UserProfile_DB uDb=db.getUserById(sIdU);
                    bundle.putString("mSign",sAuth);
                    bundle.putInt("uID", uDb.getIdU());
                    bundle.putInt("uIDSpec",uDb.getIdU());
                }catch (NullPointerException e) {

                }

                 break;

        }

        if(fragmentClass!=null) {
            try {
                Fragment myFragment = null;
                myFragment = (Fragment) fragmentClass.newInstance();
                myFragment.setArguments(bundle);
                setFragment(myFragment);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateUI(int fl){
        switch (fl){
            case 0:
                progressBar.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                break;
            default:
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }

        return super.dispatchTouchEvent(event);
    }

}
