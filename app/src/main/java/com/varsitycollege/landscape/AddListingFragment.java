package com.varsitycollege.landscape;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class AddListingFragment extends Fragment {
    ImageView imageView;
    FloatingActionButton fab;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;

    private EditText txtTitle;
    private Spinner spnCategory;
    private EditText txtCaption;
    private EditText txtDescription;
    private Button btn_save;
    public Uri selectedImageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_add_listing, container, false);

        final View rootView = inflater.inflate(R.layout.fragment_add_listing, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_addimage);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        txtTitle = (EditText) rootView.findViewById(R.id.txtTitle);
        txtCaption= (EditText) rootView.findViewById(R.id.txtCaption);
        txtDescription = (EditText) rootView.findViewById(R.id.txtDescription);
        btn_save = (Button) rootView.findViewById(R.id.save);

        // Adding category names to spinner
        // Reference: Tutorialspoint. 2022. [Online]. Available on: https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        Spinner spinner = rootView.findViewById(R.id.spnCategory);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Water");
        arrayList.add("Animals");
        arrayList.add("Plants/Trees");
        arrayList.add("Land");
        arrayList.add("Life");
        arrayList.add("Sky");
        arrayList.add("Flowers");
        arrayList.add("Space");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryNames = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + categoryNames,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        //Gallery permission and selecting an image in gallery
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(container.getContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    final String[] permissions = {Manifest.permission.CAMERA};

                    ActivityCompat.requestPermissions(getActivity(),
                            permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get all values
                String title = txtTitle.getText().toString();
                String category = spnCategory.getSelectedItem().toString();
//                String imageUrl = .toString();
                String caption = txtCaption.getText().toString();
                String description = txtTitle.getText().toString();
                ListingDetailsClass listingDetails = new ListingDetailsClass(title, category,caption,description);
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getFragmentManager().findFragmentByTag("Your Fragment Tag");
        onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imageView.setImageBitmap(bitmap);

            }
        }
    }

}
