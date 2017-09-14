package com.bignerdranch.android.myactivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityFragment extends Fragment {
    private static final String ARG_ACTIVITY_ID = "activity_id";

    private Activity mActivity;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mLocation;
    private EditText mComment;
    private EditText mDuration;
    private Spinner mType;

    public static ActivityFragment newInstance(UUID activityId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVITY_ID, activityId);

        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID activityId = (UUID) getArguments().getSerializable(ARG_ACTIVITY_ID);
        mActivity = ActivityLab.get(getActivity()).getActivity(activityId);
    }

    @Override
    public void onPause() {
        super.onPause();
        ActivityLab.get(getActivity()).updateActivity(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstancedState) {
        View v = inflater.inflate(R.layout.fragment_activities, container, false);

        mTitleField = (EditText) v.findViewById(R.id.activity_title);
        mTitleField.setText(mActivity.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivity.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.activity_date);
        mDateButton.setText(mActivity.getDate().toString());
        mDateButton.setEnabled(false);

        mLocation = (EditText) v.findViewById(R.id.activity_location);
        mLocation.setText(mActivity.getLocation());
        mLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivity.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mComment = (EditText) v.findViewById(R.id.activity_comment);
        mComment.setText(mActivity.getComment());
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivity.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDuration = (EditText) v.findViewById(R.id.activity_duration);
        mDuration.setText(mActivity.getDuration());
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivity.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mType = (Spinner) v.findViewById(R.id.activity_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.activites_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);
        mType.setSelection(mActivity.getType());
        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*String text = mType.getSelectedItem().toString();
                mActivity.setType(text);*/
                mActivity.setType(position);
                //Toast.makeText(parent.getContext(), mActivity.getType(), Toast.LENGTH_LONG)
                //.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }
}
