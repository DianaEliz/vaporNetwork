package com.example.diana.ultimoproyecto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.PostViewHolder>{

    List<Post> posts;

    public Adapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler, viewGroup, false);

        PostViewHolder holder = new PostViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {

        Post post = posts.get(position);
        postViewHolder.savDescription.setText(post.getDescription());
        postViewHolder.savLocation.setText(post.getLocation());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView savDescription, savLocation;
        ImageView savFoto;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            //savFoto = itemView.findViewById(R.id.sav_foto);
            savDescription = itemView.findViewById(R.id.txt_sav_description);
            savLocation = itemView.findViewById(R.id.txt_sav_location);


        }
    }

}
