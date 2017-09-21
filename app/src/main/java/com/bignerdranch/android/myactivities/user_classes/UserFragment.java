package com.bignerdranch.android.myactivities.user_classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bignerdranch.android.myactivities.ActivityLab;
import com.bignerdranch.android.myactivities.R;
import com.bignerdranch.android.myactivities.database.UserCursorWrapper;
import com.bignerdranch.android.myactivities.user_classes.User;

import java.util.UUID;

/**
 * Created by Robert on 14/09/2017.
 */

public class UserFragment extends Fragment {
    private User mUser;
    private EditText mFName;
    private EditText mLName;
    private TextView mUserId;
    private Spinner mGender;
    private EditText mEmail;
    private EditText mComment;

    @Override
    public void onPause() {
        super.onPause();
        UserLab.get(getActivity()).updateUser(mUser, mUser.getId());
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mUser = UserLab.get(getActivity()).getUser();

        mFName = (EditText) view.findViewById(R.id.first_name);
        mFName.setText(mUser.getFName());
        mFName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setFName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLName = (EditText) view.findViewById(R.id.last_name);
        mLName.setText(mUser.getLName());
        mLName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setLName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUserId = (TextView) view.findViewById(R.id.user_id);
        mUserId.setText("User ID: " + mUser.getId().toString());

        mGender = (Spinner) view.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(adapter);
        mGender.setSelection(mUser.getGender());
        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUser.setGender(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mEmail = (EditText) view.findViewById(R.id.email_edittext);
        mEmail.setText(mUser.getEmail());
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mComment = (EditText) view.findViewById(R.id.user_comments);
        mComment.setText(mUser.getComment());
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

}
