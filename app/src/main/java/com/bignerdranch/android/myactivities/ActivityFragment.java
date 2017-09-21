package com.bignerdranch.android.myactivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityFragment extends Fragment {
    private static final String ARG_ACTIVITY_ID = "activity_id";
    private static final int REQUEST_PHOTO= 2;

    private Activity mActivity;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mLocation;
    private EditText mComment;
    private EditText mDurationHours;
    private EditText mDurationMinutes;
    private Spinner mType;
    private Button mDelete;
    private Button mSave;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;


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
        mPhotoFile = ActivityLab.get(getActivity()).getPhotoFile(mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();

        ActivityLab.get(getActivity()).updateActivity(mActivity);
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
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

        mDurationHours = (EditText) v.findViewById(R.id.activity_duration_hours);
        mDurationHours.setText(mActivity.getDurationHours());
        mDurationHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivity.setDurationHours(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDurationMinutes = (EditText) v.findViewById(R.id.activity_duration_minutes);
        mDurationMinutes.setText(mActivity.getDurationMinutes());
        mDurationMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mActivity.setDurationMinutes(s.toString());
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

        mDelete = (Button) v.findViewById(R.id.delete_button);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLab.get(getActivity()).deleteActivity(mActivity.getId());
                Intent intent = new Intent(getActivity(), ActivitiesListActivity.class);
                startActivity(intent);
            }
        });

        mSave = (Button) v.findViewById(R.id.save_button);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitiesListActivity.class);
                startActivity(intent);
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        mPhotoButton = (ImageButton) v.findViewById(R.id.activity_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Listener", "calls on Click");
                startActivityForResult(captureImage, REQUEST_PHOTO);
                Log.d("Listener", "after on Click");

            }
        });



        mPhotoView = (ImageView) v.findViewById(R.id.activity_photo);
        updatePhotoView();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }
}
