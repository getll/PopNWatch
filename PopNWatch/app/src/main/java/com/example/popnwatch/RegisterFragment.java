package com.example.popnwatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    EditText fname, lname, email, password, bday;
    Button register;
    UserDB userDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_register, container, false );

        fname = view.findViewById( R.id.firstNameEditText );
        lname = view.findViewById( R.id.lastNameEditText );
        email = view.findViewById( R.id.cardNumberEditText);
        bday = view.findViewById( R.id.birthdayEditText );
        password = view.findViewById( R.id.passwordEditText );
        register = view.findViewById( R.id.registerAccButton );

        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB = new UserDB( view.getContext() );

                if (validateInput()) {
                    if (!userDB.checkEmailExists(email.getText().toString())) {
                        if (userDB.addUser(
                                fname.getText().toString().trim(),
                                lname.getText().toString().trim(),
                                bday.getText().toString().trim(),
                                email.getText().toString().trim(),
                                password.getText().toString().trim())) {

                            LoginFragment loginFragment = new LoginFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentContainerView, loginFragment);
                            transaction.commit();
                            Toast.makeText(view.getContext(), "User registered Successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(view.getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(view.getContext(), "Email already used", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public boolean validateInput () {
        if (
            fname.getText().toString().isEmpty() ||
            lname.getText().toString().isEmpty() ||
            bday.getText().toString().isEmpty() ||
            email.getText().toString().isEmpty() ||
            password.getText().toString().isEmpty()
        ) {
            Toast.makeText(this.getContext(), "Do not leave fields empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            Toast.makeText(this.getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}