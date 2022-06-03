package com.varsitycollege.landscape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private CardView cardView;
    ArrayList<CategoryInfo> catList;
    Context context;
    private DatabaseReference root;
    private FirebaseUser user;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(Context context , ArrayList<CategoryInfo> catList){

        this.catList = catList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cat_items , parent ,false);
        cardView = v.findViewById(R.id.cardView);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryInfo cat = catList.get(position);
        holder.cat_name.setText(cat.getCategoryName());

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        root  = db.getReference().child(userId).child("Listings");

        //getting the number of items in a certain category
        root.orderByChild("category").equalTo(cat.getCategoryName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cat.setCatCount((int) snapshot.getChildrenCount());

                //setting the percentages and progressBar values
                holder.bar.setProgress((int) cat.getPercentage());
                holder.bar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                holder.percent.setText((int) cat.getPercentage() + "%");
                holder.completion.setText(cat.getCatCount() + "/" + cat.getCategoryGoal());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cat_name, percent, completion;
        ProgressBar bar;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            cat_name = itemView.findViewById(R.id.name_txt);
            percent = itemView.findViewById(R.id.percentage);
            completion = itemView.findViewById(R.id.completion);
            bar = itemView.findViewById(R.id.progressBar2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        if (position != RecyclerView.NO_POSITION);{

                            listener.onItemClick(position);

                        }
                    }

                }
            });
        }
    }

}

//https://www.youtube.com/watch?v=V4E5ROnbrGk - ref
