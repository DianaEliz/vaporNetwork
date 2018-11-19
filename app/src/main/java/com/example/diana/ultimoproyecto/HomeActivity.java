package com.example.diana.ultimoproyecto;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity{

    Button profile, newPost, listPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        init();
    }

    private void init() {
        profile = findViewById(R.id.nav_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, VaporCamActivity.class);
                startActivity(intent);
            }
        });
        newPost = findViewById(R.id.nav_new_post);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewPostActivity.class);
                startActivity(intent);
            }
        });
        listPost = findViewById(R.id.nav_list_post);
        listPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PostListActivity.class);
                startActivity(intent);
            }
        });
    }

}