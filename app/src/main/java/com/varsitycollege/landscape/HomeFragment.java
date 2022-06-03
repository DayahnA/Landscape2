package com.varsitycollege.landscape;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import java.util.HashMap;

public class HomeFragment extends Fragment {
    private FloatingActionButton add;
    private Button save;
    private ImageView back;
    private EditText cat_name, cat_goal;
    private RecyclerView recyclerView;
    private TextView name, goal;
    private CardView cv;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private FirebaseUser user;
    private ArrayList<CategoryInfo> list;
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home, container, false);

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        add = rootView.findViewById(R.id.add_listing);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Categories...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        user = FirebaseAuth.getInstance().getCurrentUser();;
        String userId = user.getUid();
        FirebaseDatabase db = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference root  = db.getReference().child(userId).child("Categories");
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        list = new ArrayList<>();
        adapter = new MyAdapter(getActivity() , list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CategoryInfo cat = dataSnapshot.getValue(CategoryInfo.class);
                    list.add(cat);
                }

                adapter.notifyDataSetChanged();

                // click on item
                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        Fragment fragment = new ShowListings();

                        //getting the position of current cardView when clicked
                        CategoryInfo cat = list.get(position);

                        //pulling the name and goal of current cardView
                        String catName = cat.getCategoryName();
                        String catGoal = cat.getCategoryGoal();

                        //pushing values to next activity
                        Bundle bundle = new Bundle();
                        bundle.putString("cat_name", catName);
                        bundle.putString("cat_goal", catGoal);
                        fragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  fragment).commit();
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Network error, check your connection!", Toast.LENGTH_SHORT).show();
            }
        });

        //adding buttons on homepage according to category name
                /*Button btn = new Button(getActivity());
                btn.setText(categoryName);
                layout.addView(btn);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), CategoryView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cat_name", categoryName);
                        bundle.putString("cat_goal", categoryGoal);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpCategory(userId);
            }
        });

        return rootView;
    }

    public void popUpCategory(String userId) {

        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_category, null);
        cat_name = categoryPopUp.findViewById(R.id.category_name);
        cat_goal = categoryPopUp.findViewById(R.id.category_goal);

        save = categoryPopUp.findViewById(R.id.save);
        back = categoryPopUp.findViewById(R.id.back);

        FirebaseDatabase db = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference root  = db.getReference().child(userId).child("Categories");

        dialogBuilder.setView(categoryPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cat_name.getText().toString();
                String goal = cat_goal.getText().toString();

                if (name.isEmpty()) {
                    cat_name.setError("Please enter category name");
                } else if (goal.isEmpty()) {
                    cat_goal.setError("Please enter category goal");
                } else if (goal.equals(0)){
                    cat_goal.setError("Goal cannot be zero!");
                } else {
                    Toast.makeText(getActivity(), "Category created", Toast.LENGTH_SHORT).show();

                    HashMap<String , String> userMap = new HashMap<>();
                    userMap.put("categoryName", name);
                    userMap.put("categoryGoal", goal);

                    String mCategoryId = root.push().getKey();
                    root.child(mCategoryId).setValue(userMap);

                    dialog.hide();
                }
            }
        });
        //https://www.youtube.com/watch?v=4GYKOzgQDWI - ref
    }
}


