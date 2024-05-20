package com.example.galleryapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.galleryapp.R;

public class GalleryPhotoAdapter extends BaseAdapter {
    private Context context;
    public  int[] images = {
            R.drawable.Captura165218,
            R.drawable.Captura080624,
            R.drawable.Captura084541,
            R.drawable.Captura085232,
            R.drawable.Captura085711,
            R.drawable.Captura090311,
            R.drawable.Captura102510,
            R.drawable.Captura215946
    };

    public GalleryPhotoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(340, 350));
        return imageView;
    }
}
