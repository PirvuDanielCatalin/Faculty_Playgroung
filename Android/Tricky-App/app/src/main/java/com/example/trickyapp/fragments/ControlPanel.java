package com.example.trickyapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trickyapp.R;
import com.example.trickyapp.activities.LetsDoSomething;
import com.example.trickyapp.helpers.Constants;
import com.example.trickyapp.interfaces.OnUserRepositoryActionListener;
import com.example.trickyapp.models.User;
import com.example.trickyapp.repositories.UserRepository;

public class ControlPanel extends Fragment {

    private EditText firstNameET;
    private EditText lastNameET;
    private Button removeUserBTN;
    private Button addUserBTN;
    private UserRepository userRepository;

    public ControlPanel() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRepository = new UserRepository(getContext());
        firstNameET = view.findViewById(R.id.first_name_et);
        lastNameET = view.findViewById(R.id.last_name_et);
        removeUserBTN = view.findViewById(R.id.remove_user_btn);
        addUserBTN = view.findViewById(R.id.add_user_btn);

        addUserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = firstNameET.getText().toString();
                String last_name = lastNameET.getText().toString();
                User user;

                if (!first_name.isEmpty() && !last_name.isEmpty()) {
                    user = userRepository.getByFirstAndLastName(first_name, last_name, new OnUserRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {

                        }

                        @Override
                        public void actionFailed() {

                        }
                    });

                    if (user == null) {
                        User aux = new User(first_name, last_name);
                        userRepository.insert(aux, new OnUserRepositoryActionListener() {
                            @Override
                            public void actionSuccess() {

                            }

                            @Override
                            public void actionFailed() {

                            }
                        });

                        RvList rvList = ((LetsDoSomething) getActivity()).rvList;
                        rvList.array.add(0, aux);
                        rvList.customAdapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), Constants.USER_ADDED, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), Constants.USER_ALREADY_EXIST, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Constants.USER_EMPTY, Toast.LENGTH_LONG).show();
                }
            }
        });

        removeUserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = firstNameET.getText().toString();
                String last_name = lastNameET.getText().toString();
                User user;

                if (!first_name.isEmpty() && !last_name.isEmpty()) {
                    user = userRepository.getByFirstAndLastName(first_name, last_name, new OnUserRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {

                        }

                        @Override
                        public void actionFailed() {

                        }
                    });

                    if (user != null) {
                        userRepository.delete(user, new OnUserRepositoryActionListener() {
                            @Override
                            public void actionSuccess() {

                            }

                            @Override
                            public void actionFailed() {

                            }
                        });

                        RvList rvList = ((LetsDoSomething) getActivity()).rvList;

                        int pos = rvList.array.indexOf(user);
                        for (int i = 0; i < rvList.array.size(); i++)
                            if (rvList.array.get(i).first_name.equals(first_name) && rvList.array.get(i).last_name.equals(last_name))
                            {
                                pos = i;
                                break;
                            }

                        rvList.array.remove(rvList.array.get(pos));
                        rvList.customAdapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), Constants.USER_REMOVED, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), Constants.USER_DOESNT_EXIST, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Constants.USER_EMPTY, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
