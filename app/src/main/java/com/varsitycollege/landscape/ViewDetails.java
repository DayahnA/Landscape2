package com.varsitycollege.landscape;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewDetails extends Fragment {

    private TextView txtTitle;
    private TextView txtCaption;
    private TextView txtDescription;
    private TextView txtCategory;
    private ImageView img;
    private Button back;
    private FirebaseUser user;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_view_details, container, false);

        txtTitle = rootView.findViewById(R.id.txtTitle1);
        txtCaption = rootView.findViewById(R.id.txtCaption1);
        txtDescription = rootView.findViewById(R.id.txtDescription1);
        txtCategory = rootView.findViewById(R.id.spnCategory1);
        img = rootView.findViewById(R.id.image1);
        back = rootView.findViewById(R.id.btn_back1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ;
        String userId = user.getUid();
        FirebaseDatabase db = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child(userId).child("Listings");

        //pulling values from previous activity
        Bundle bundle = new Bundle();
        bundle = getArguments();
        String title = bundle.getString("title");
        String caption = bundle.getString("caption");
        String description = bundle.getString("description");
        String category = bundle.getString("cat");
        String image = bundle.getString("image");

        txtTitle.setText(title);
        txtCaption.setText(caption);
        txtDescription.setText(description);
        txtCategory.setText(category);

        root.orderByChild("imageUrl").equalTo(image).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ListingDetailsClass listingDetailsClass = postSnapshot.getValue(ListingDetailsClass.class);
                    Picasso.with(mContext).load(listingDetailsClass.getImageUrl()).fit().centerCrop().into(img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ShowListings();

                //pulling the name and goal of current cardView
                String catName = category;

                //pushing values to next activity
                Bundle bundle = new Bundle();
                bundle.putString("cat_name", catName);
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  fragment).commit();
            }
        });

        return rootView;
    }
}