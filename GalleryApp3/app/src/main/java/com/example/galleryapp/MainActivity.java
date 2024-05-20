package com.example.galleryapp;

import android.Manifest;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.RequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private static final int REQUEST_PERMISSIONS = 1234;

    private static final String[] PERMISSIONS = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;

    private boolean arePermissionsDenied() {
        for (int i = 0; i < PERMISSIONS_COUNT; i++) {
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (arePermissionsDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                onResume();
            }
        }
    }

    private boolean isGalleryInitialized;


    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if (!isGalleryInitialized) {
            final ListView listView = findViewById(R.id.listView);
            final GalleryAdapter galleryAdapter = new GalleryAdapter();
            final File imageDir = new File(String.valueOf(Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
            final File[] files = imageDir.listFiles();
            final int filesCount = files.length;
            final List<String> filesList = new ArrayList<>();
            for (int i = 0; i < filesCount; i++) {
                final String path = files[i].getAbsolutePath();
                if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
                    filesList.add(path);
                }
            }
            galleryAdapter.setData(filesList);
            listView.setAdapter(galleryAdapter);
            isGalleryInitialized = true;
        }
    }

    final class GalleryAdapter extends BaseAdapter {

        private List<String> data = new ArrayList<>();

        void setData(List<String> data) {
            if (this.data.size() > 0) {
                this.data.clear();
            }
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ImageView imageView;
            if (convertView == null) {
                imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }

            String imagePath = data.get(position);
            Log.d("ImagePath", "Ruta de la imagen: " + imagePath);
            Glide.with(MainActivity.this)
                    .load(imagePath)
                    .centerCrop()
                    .error(com.google.android.material.R.drawable.material_cursor_drawable)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("Glide", "Error al cargar la imagen: " + e.getMessage(), e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);

            return imageView;
        }
    }
}