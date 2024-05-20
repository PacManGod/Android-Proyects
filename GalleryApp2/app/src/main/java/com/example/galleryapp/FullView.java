package com.example.galleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.galleryapp.adapter.GalleryPhotoAdapter;

public class FullView extends AppCompatActivity {
    ImageView imageView;
    GalleryPhotoAdapter galleryPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = (ImageView)findViewById(R.id.iv_Photo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Full View");

        Intent intent = getIntent();
        int position = intent.getExtras().getInt("id");
        galleryPhotoAdapter = new GalleryPhotoAdapter(this);
        imageView.setImageResource(galleryPhotoAdapter.images[position]);
    }
}