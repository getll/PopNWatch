package com.example.popnwatch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLoginFragment extends Fragment {

    EditText email, password;
    Button login, register, signIn, admin;
    AdminDB adminDB;
    UserDB userDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminLoginFragment newInstance(String param1, String param2) {
        AdminLoginFragment fragment = new AdminLoginFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_admin_login, container, false );

        email = view.findViewById(R.id.adminEmailEditText);
        password = view.findViewById(R.id.adminPasswordEditText);
        login = view.findViewById(R.id.adminLoginButton);

        adminDB = new AdminDB(view.getContext());
        userDB = new UserDB(view.getContext());

        //creates default admin if there are no admin accounts
        Cursor cursor = adminDB.retrieveAdmin();

        if (cursor.getCount() == 0) {
            adminDB.addAdmin("admin@hotmail.com", "admin");
            Toast.makeText(view.getContext(), "Creating default admin account.", Toast.LENGTH_SHORT).show();
        }

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyCredentials(view)){
                    Toast.makeText(view.getContext(), "Log in successful",Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(view.getContext(), AdminActivity.class);
                    getActivity().overridePendingTransition( R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    startActivity(i);
                }
                else {
                    Toast.makeText(view.getContext(), "Admin log in failed.",Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        return view;
    }

    public boolean verifyCredentials(View view){
        Cursor cursor = adminDB.retrieveAdmin();

        while(cursor.moveToNext()) {
            String adminEmail = cursor.getString(1);
            String adminPassword = cursor.getString(2);
            if (adminEmail.equals( email.getText().toString().trim())
                    && adminPassword.equals(password.getText().toString().trim()))
            {
                return true;
            }
        }
        return false;
    }
}