package com.example.user.smgapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 03-06-2016.
 */
public class ProductDetailsImage extends NavigationDrawer {
   ArrayList<String> images;
    ImageView imageView;
    String product_name;
    TextView app_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.product_detail_images);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.product_detail_images, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app_title = (TextView) findViewById(R.id.app_title);
        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        getSupportActionBar().setTitle("");
        app_title.setTypeface(face);
        imageView = (ImageView) findViewById(R.id.image1);
        Intent intent=getIntent();

        images = intent.getStringArrayListExtra("images_array");
        product_name=intent.getStringExtra("product_name");
        app_title.setText(product_name);
        Log.e("images from intent..", "images from intent.." + images);


        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        Picasso.with(getApplicationContext()).load(images.get(0))
                .fit().into(imageView);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               /* Toast.makeText(getBaseContext(), "pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();*/
                // display the images selected

                Picasso.with(getApplicationContext()).load(images.get(position))
                        .fit().into(imageView);
            }
        });


    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 1);
            a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return images.size();
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            Picasso.with(getApplicationContext()).load(images.get(position))
                    .fit().into(imageView);
            imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }
}
