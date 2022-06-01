package com.varsitycollege.landscape;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddListing extends AppCompatActivity {
//    String[] categories = {"Water", "Animals", "Plants/Trees", "Land", "Life", "Sky", "Flowers", "Space"};
//    Spinner category;

    ImageView selectedImage;
    FloatingActionButton fab;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_add_listing);
//
//        selectedImage = findViewById(R.id.image);
//        fab = findViewById(R.id.fab_addimage);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddListing.this, "Camera fab is clicked", Toast.LENGTH_SHORT).show();
//                if (ActivityCompat.checkSelfPermission(AddListing.this,
//                        Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    final String[] permissions = {Manifest.permission.CAMERA};
//
//                    ActivityCompat.requestPermissions(AddListing.this,
//                            permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
//                } else {
//
//                    takePhoto();
//                }
//            }
//        });

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
//                        PackageManager.PERMISSION_GRANTED) {
//
//            takePhoto();
//        }
//    }
//
//    private void takePhoto() {
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
//    }
//
//    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            fab.setImageBitmap(bitmap);
//        }
    }
