package com.example.fitlife;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {
    View view;
    FragmentManager fragmentManager;
    private ImageView imageView11;
    private Button cameraButton2;
    private Button uploadButton2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Bitmap imageBitmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        TextView editProfileTextView = view.findViewById(R.id.viewEdit);
        Button settingsButton=view.findViewById(R.id.viewSettings);
        imageView11=view.findViewById(R.id.imageView22);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.page_container, editProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.page_container, settingsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        cameraButton2 = view.findViewById(R.id.cameraButton11);
        cameraButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle camera button click
                dispatchTakePictureIntent();
            }
        });

        uploadButton2 = view.findViewById(R.id.uploadButton11);
        uploadButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upload buttonopen click
                openGallery();
            }
        });


        return view;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                imageView11.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imageView11.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_PICK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



