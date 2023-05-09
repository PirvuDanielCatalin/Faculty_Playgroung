package com.example.trickyapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trickyapp.R;
import com.example.trickyapp.activities.LetsDoSomething;
import com.example.trickyapp.helpers.Constants;

public class LoginForm extends Fragment {

    private EditText userET;
    private EditText passwordET;
    private Button submitFormBTN;
    private TextView textView;

    private int trick;

    public LoginForm() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userET = view.findViewById(R.id.user_et);
        passwordET = view.findViewById(R.id.password_et);
        submitFormBTN = view.findViewById(R.id.submit_login_btn);
        textView = view.findViewById(R.id.trick_msg);
        trick = 0;

        submitFormBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userET.getText().toString();
                String password = passwordET.getText().toString();

                /*
                 *
                 * Hatz Johnule x2. Chiar credeai că am pus autentificare? ^_^
                 *
                 * Acum sper să nu fi ajuns aici direct :)) și sper să nu te fi supărat pe mine
                 *
                 */

                if (user.isEmpty() && password.isEmpty()) {
                    if (trick == 0) {
                        textView.setVisibility(View.VISIBLE);
                        trick = 1;
                    } else {
                        Intent intent = new Intent(getActivity(), LetsDoSomething.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getActivity(), Constants.LOGIN_FAIL, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
