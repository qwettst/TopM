package com.example.zz.zz.LK_User;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zz.zz.BuildConfig;
import com.example.zz.zz.MainUser;
import com.example.zz.zz.R;
import com.example.zz.zz.SaveSpecUser;
import com.example.zz.zz.Show_Review.myReview;
import com.example.zz.zz.database.DatabaseUserProfileHelper;

import com.example.zz.zz.model.UserProfile_DB;
import com.example.zz.zz.model.saveSpecUser.SpecUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Create_LK.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Create_LK#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Create_LK extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Create_LK() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Create_LK.
     */
    // TODO: Rename and change types and number of parameters
    public static Create_LK newInstance(String param1, String param2) {
        Create_LK fragment = new Create_LK();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu_lkcreate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_lkcreate: {

                if (etName.getText().length() != 0) {
                    SpecUser specUser=new SpecUser();

                    specUser.setName(etName.getText().toString());
                    specUser.setSurname(etFirstname.getText().toString());
                    specUser.setOtchestvo(etLastname.getText().toString());
                    specUser.setCity(etCity.getText().toString());
                    specUser.setAddress(etAdress.getText().toString());
                    specUser.setInfo(etInfo.getText().toString());
                    specUser.setEmail(userDB.getEmail());
                    specUser.setSpecName(etSpec.getText().toString());

                    if(chOnCallYes.isChecked())
                        specUser.setOnCall(1);
                    else
                        specUser.setOnCall(0);


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

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = null;
                    try {
                        jsonString = mapper.writeValueAsString(specUser);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("json " + jsonString);

                    SaveSpecUser saveSpecUser = retrofit.create(SaveSpecUser.class);

                    final Call<Void> specU = saveSpecUser.saveSpecUser(jsonString);

                    specU.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("TAG","response " + response.body());

                            } else {
                                Log.d("TAG","response code " + response.code());
                            }
                            if (response.code() == 200)
                                Toasty.success(getContext(), "ЛК успешно создан", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Tag","failure " + t);
                            Toasty.error(getContext(), "Сервер не отвечает", Toast.LENGTH_SHORT, true).show();
                        }
                    });

                    Bundle bSUser=new Bundle();
                    bSUser.putString("mSign",bundle.getString("mSign"));
                    bSUser.putInt("uID", bundle.getInt("uID"));
                    Intent intent=new Intent(getActivity() ,MainUser.class);
                    intent.putExtra("mSign",bundle.getString("mSign"));
                    startActivity(intent);

                }
                else
                    Toasty.info(getContext(), "Поле - Имя - не должно быть пустым", Toast.LENGTH_LONG).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private EditText etName, etFirstname, etLastname, etSpec, etCity, etAdress, etInfo;
    private CheckBox chOnCallYes,chOnCallNo;
    private int iUID;
    UserProfile_DB userDB;
    private Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create__lk, container,
                false);
        getActivity().setTitle("Создать ЛК");

        bundle = this.getArguments();
        iUID= bundle.getInt("uID");
        DatabaseUserProfileHelper db;
        db=new DatabaseUserProfileHelper(getContext());
        userDB=db.getUserById(iUID);

        ImageView ivBackground;

        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);

        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
        etName=(EditText)rootView.findViewById(R.id.send_name);
        etFirstname=(EditText)rootView.findViewById(R.id.send_first_name);
        etLastname=(EditText)rootView.findViewById(R.id.send_otchestvo);
        etCity=(EditText)rootView.findViewById(R.id.send_gorod);
        etAdress=(EditText)rootView.findViewById(R.id.send_street);
        etInfo=(EditText)rootView.findViewById(R.id.send_infoRev);
        etSpec=(EditText)rootView.findViewById(R.id.send_spec);

        chOnCallYes=(CheckBox)rootView.findViewById(R.id.onCallYes);
        chOnCallNo=(CheckBox)rootView.findViewById(R.id.onCallNo);

        chOnCallYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chOnCallYes.isChecked())
                    chOnCallNo.setChecked(false);
            }
        });

        chOnCallNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chOnCallNo.isChecked())
                    chOnCallYes.setChecked(false);
            }
        });


        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
