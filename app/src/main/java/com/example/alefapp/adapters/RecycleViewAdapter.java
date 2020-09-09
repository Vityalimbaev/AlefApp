package com.example.alefapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.alefapp.R;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ImageViewHolder> {

    private ArrayList<String> urlsOfImages;
    private OnUserClickListener onUserClickListener;

    public RecycleViewAdapter(){
        this(null);
    }

    public RecycleViewAdapter(ArrayList<String> urlsOfImages){
        super();
        if(urlsOfImages == null){
            urlsOfImages = new ArrayList<String>();
        }
        this.urlsOfImages = urlsOfImages;
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener){
        this.onUserClickListener = onUserClickListener;
    }

    public void setUrlsArray(ArrayList<String> urlsOfImages){
        this.urlsOfImages = urlsOfImages;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view, onUserClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        final Context context = holder.imageView.getContext();
        Glide.with(context)
                .load(urlsOfImages.get(position))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.enableListener = false;
                        holder.imageView.setImageResource(R.drawable.not_found_icon);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        Log.d("URLSALEF", "onBindViewHolder: " + urlsOfImages.size());
        return urlsOfImages.size();
    }

    public ArrayList<String> getElements() {
        return urlsOfImages;
    }


    protected static class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        boolean enableListener = true;

        public ImageViewHolder(@NonNull View itemView, final OnUserClickListener onUserClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Log.d("ALEF222", "onClick: "+ enableListener);
                    if(position != RecyclerView.NO_POSITION && enableListener){
                        onUserClickListener.onUserClick(position);
                    }
                }
            });

        }
    }

    public interface OnUserClickListener {
        public void onUserClick(int position);
    }
}
