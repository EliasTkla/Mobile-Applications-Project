package com.example.fitlife;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.widget.EditText;


import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView imageView;
    private Button cameraButton;
    private Button uploadButton;

    private Bitmap imageBitmap;


    private SharedPreferences sharedPreferences;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        // Retrieve user details from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", 0);
        int weight = sharedPreferences.getInt("weight", 0);
        int height = sharedPreferences.getInt("height", 0);

        // Set user details in EditText fields
        EditText nameEditText = view.findViewById(R.id.nameEditText);
        nameEditText.setText(name);

        EditText ageEditText = view.findViewById(R.id.ageEditText);
        ageEditText.setText(String.valueOf(age));

        EditText weightEditText = view.findViewById(R.id.weightEditText);
        weightEditText.setText(String.valueOf(weight));

        EditText heightEditText = view.findViewById(R.id.heightEditText);
        heightEditText.setText(String.valueOf(height));

        // Set up click listeners for camera and upload buttons
        cameraButton = view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle camera button click
            }
        });

        uploadButton = view.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upload button click
            }
        });

        // Set up click listener for save button
        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle save button click
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imageView.setImageBitmap(imageBitmap);
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
