package com.varsitycollege.landscape;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddListingFragment extends Fragment {
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int PICK_IMAGE_REQUEST = 1;


    private EditText txtTitle;
    private Spinner spnCategory;
    private EditText txtCaption;
    private EditText txtDescription;
    private Button save, camera, gallery;
    private FirebaseUser user;
    private String categoryNames;
    private Uri mImageUri;
    private ProgressDialog progressDialog;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_add_listing, container, false);

        final View rootView = inflater.inflate(R.layout.fragment_add_listing, container, false);

        //getting database reference
        user = FirebaseAuth.getInstance().getCurrentUser();;
        String userId = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root2 = db.getReference().child(userId).child("Categories");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        camera = rootView.findViewById(R.id.camera_image);
        gallery = rootView.findViewById(R.id.gallery_image);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        txtTitle = (EditText) rootView.findViewById(R.id.txtTitle);
        txtCaption = (EditText) rootView.findViewById(R.id.txtCaption);
        txtDescription = (EditText) rootView.findViewById(R.id.txtDescription);
        save = rootView.findViewById(R.id.btn_save);
        spnCategory = rootView.findViewById(R.id.spnCategory);

        //Adding category names to spinner https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        Spinner spinner = rootView.findViewById(R.id.spnCategory);
        ArrayList<String> list = new ArrayList<>();

        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryInfo cat = dataSnapshot.getValue(CategoryInfo.class);
                    list.add(cat.getCategoryName());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        categoryNames = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadListing();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
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

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getActivity()).load(mImageUri).fit().centerCrop().into(imageView);
        }
    }

    //getting the image file extension - example: .jpg
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadListing() {
        //getting database reference
        user = FirebaseAuth.getInstance().getCurrentUser();;
        String userId = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child(userId).child("Listings");

        String title = txtTitle.getText().toString();
        String category = categoryNames;
        String caption = txtCaption.getText().toString();
        String description = txtDescription.getText().toString();

        //creating a unique id for image with file extension
        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

        if (mImageUri != null) {
            if (title.isEmpty()) {
                txtTitle.setError("Please enter title");
            } else if (category.isEmpty()){
                Toast.makeText(getActivity(), "Please choose category", Toast.LENGTH_SHORT).show();
            } else if (caption.isEmpty()) {
                txtCaption.setError("Please enter caption");
            } else if (description.isEmpty()) {
                txtDescription.setError("Please enter description");
            } else{
                //setting progress dialog when uploading data to firebase
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Upload in progress...");
                progressDialog.setTitle("Uploading");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        progressDialog.dismiss();
                                        String photoStringLink = uri.toString();
                                        Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                                        ListingDetailsClass listing = new ListingDetailsClass(title, category, photoStringLink, caption, description);

                                        String uploadId = root.push().getKey();
                                        root.child(uploadId).setValue(listing);

                                        txtTitle.setText("");
                                        txtCaption.setText("");
                                        txtDescription.setText("");
                                        imageView.setImageResource(0);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else {
            Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

}

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getFragmentManager().findFragmentByTag("Your Fragment Tag");
        //onActivityResult(requestCode, resultCode, data);
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
    }*/


