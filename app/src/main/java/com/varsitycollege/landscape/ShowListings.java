package com.varsitycollege.landscape;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowListings extends Fragment {
    private RecyclerView recyclerView;
    private ViewListingAdapter adapter;
    private DatabaseReference root;
    private ArrayList<ListingDetailsClass> listing;
    private FirebaseUser user;
    private TextView heading;
    private FloatingActionButton add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_show_listings, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        heading = rootView.findViewById(R.id.cat_txt);
        add = rootView.findViewById(R.id.new_listing);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle bundle = new Bundle();
        bundle = getArguments();
        String catName = bundle.getString("cat_name");

        heading.setText(catName + " Category");

        listing = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        root  = db.getReference().child(userId).child("Listings");
        CategoryInfo category = new CategoryInfo();


        // https://www.youtube.com/watch?v=lPfQN-Sfnjw - ref
        root.orderByChild("category").equalTo(catName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    ListingDetailsClass listingDetailsClass = postSnapshot.getValue(ListingDetailsClass.class);
                    listing.add(listingDetailsClass);
                }

                adapter = new ViewListingAdapter(getActivity(), listing);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new ViewListingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Fragment fragment = new ViewDetails();

                        //getting the position of current cardView when clicked
                        ListingDetailsClass list = listing.get(position);

                        //pulling the data from the ViewListingClass
                        String title = list.getTitle();
                        String category = list.getCategory();
                        String image = list.getImageUrl();
                        String caption = list.getCaption();
                        String description = list.getDescription();

                        //pushing values to next activity
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        bundle.putString("cat", category);
                        bundle.putString("image", image);
                        bundle.putString("caption", caption);
                        bundle.putString("description", description);
                        fragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  fragment).commit();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddListingFragment()).commit();
            }
        });

        return rootView;
    }
}