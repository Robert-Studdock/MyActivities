package com.bignerdranch.android.myactivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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

import com.bignerdranch.android.myactivities.Location.LocationActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {
    private static final String ARG_ACTIVITY_ID = "activity_id";
    private static final int REQUEST_PHOTO= 2;
    public static final String EXTRA_LATITUDE = "com.example.bignerdranch.LATITUDE";
    public static final String EXTRA_LONGITUDE = "com.example.bignerdranch.LONGITUDE";

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
    private ImageView mImageView;
    private GoogleApiClient mClient;
    private String mCoordinants;
    private double mLatitude;
    private double mLongitude;
    private Button mMapButton;


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
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();

        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
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
                Toast.makeText(getContext(), "Activity Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        mSave = (Button) v.findViewById(R.id.save_button);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int x = Integer.parseInt(mActivity.getDurationMinutes().toString());
                if (x >= 60 || x < 0) {
                    Toast.makeText(getContext(), "Minutes should be between 0 and 59", Toast.LENGTH_SHORT).show();
                } else if (x < 60 && x >= 0) {

                    Toast.makeText(getContext(), "Activity Saved", Toast.LENGTH_SHORT).show();
                }*/
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
                startActivityForResult(captureImage, REQUEST_PHOTO);

            }
        });



        mPhotoView = (ImageView) v.findViewById(R.id.activity_photo);
        updatePhotoView();

        mLocation = (EditText) v.findViewById(R.id.activity_location);
        mLocation.setKeyListener(null);
        mLocation.setText(mActivity.getLocation());

        mMapButton = (Button) v.findViewById(R.id.map_button);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putDouble("latitude", mLatitude);
                //bundle.putDouble("longitude", mLongitude);
                Double tempLatitude = Double.parseDouble(mActivity.getLocation().substring(0, 10));
                Double tempLongitude = Double.parseDouble(mActivity.getLocation().substring(13, 23));
                bundle.putDouble("latitude", tempLatitude);
                bundle.putDouble("longitude", tempLongitude);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == android.content.pm.PackageManager.PERMISSION_DENIED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "got a fix: " + location);
                mCoordinants = location.toString();
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
                mActivity.setLocation(Double.toString(mLatitude) + ", " + Double.toString(mLongitude));
            }
        });
    }

    @Override
    public void onConnectionSuspended(int cause) {

    }
}
