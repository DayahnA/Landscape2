package com.varsitycollege.landscape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewListingAdapter extends RecyclerView.Adapter<ViewListingAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<ListingDetailsClass> mListings;

    private ViewListingAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ViewListingAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ViewListingAdapter(Context context, ArrayList<ListingDetailsClass> listings){
        mContext = context;
        mListings = listings;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.listing_items, parent, false);
        return new ImageViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ListingDetailsClass listingCurrent = mListings.get(position);
        holder.txtTitle.setText(listingCurrent.getTitle());
        Picasso.with(mContext).load(listingCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

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
