package com.example.user.smgapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomePage extends NavigationDrawer {
    private static String url = SingletonActivity.API_URL+"api/thumbnails.php";
    public TextView nav_title;
    ProgressBar pBar;
    String thumb_nam, thumb_image, thumb_id;
    ArrayList<String> thumb_cat_id;
    ArrayList<HashMap<String, String>> thumb_details;
    ArrayList<String> thumb_images_array;
    public Typeface face;
    TextView app_title;
    protected View view;
    GridView grid;
   // getCategories.GridViewAdapter gadapter;
    DBController db;
    GridViewAdapter gadapter;
    Dialog review_dialog;
    Button submit_review_btn;
    ImageView close_review_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.content_splash, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title = (TextView) findViewById(R.id.app_title);
        pBar = (ProgressBar) findViewById(R.id.progress_bar_custom);

       // nav_title = (TextView) findViewById(R.id.navigation_title);
        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        grid = (GridView) findViewById(R.id.grid);
        app_title.setText(R.string.app_name);
        app_title.setTypeface(face, Typeface.BOLD);

        if(SingletonActivity.checkConnectivity(HomePage.this)){

            getThumbnails();
        }
        else{
            Intent intent=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent);
        }


      /*  review_dialog = new Dialog(HomePage.this, R.style.AppTheme);
        review_dialog.setCancelable(false);
        review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        review_dialog.setContentView(R.layout.custom);
        submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
        close_review_dialog = (ImageView) review_dialog.findViewById(R.id.review_submit_close);

        submit_review_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                review_dialog.dismiss();
            }
        });

        close_review_dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                review_dialog.dismiss();
            }
        });
        review_dialog.show();
*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    private void getThumbnails() {

        pBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pBar.setVisibility(View.INVISIBLE);

                        Log.d("Request", response.toString());
                        try {
                            response.getString("isSuccess");
                            Log.e("response sucess..", "response sucess..");
                            JSONArray jarray=response.getJSONArray("thumbnail");
                            thumb_images_array = new ArrayList<>();
                            thumb_details = new ArrayList<>();
                            thumb_cat_id = new ArrayList<>();

                            if (jarray!=null){


                            for (int i = 0; i < jarray.length(); i++) {
                                // JSONObject jobj=array.getJSONObject(i);
                                Log.e("array length..", "array length.." + jarray.length());
                                JSONObject jobj = jarray.getJSONObject(i);
                                String cat_name = jobj.getString("cat_name");
                                String cat_image = jobj.getString("cat_image");
                                String cat_id = jobj.getString("cat_id");
                                Log.e("thumbnails volley..", "volley thumbnails.." + cat_name + "\t" + cat_id + "\t" + cat_image);
                                thumb_images_array.add(cat_image);
                                thumb_cat_id.add(cat_id);
                                Log.e("thumb images..", "thumb images.." + thumb_cat_id + "\n" + thumb_images_array);

                                gadapter = new GridViewAdapter(HomePage.this, thumb_images_array, thumb_cat_id);
                                grid.setAdapter(gadapter);


                            }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Request", "Error: " + error.getMessage());
                pBar.setVisibility(View.INVISIBLE);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
             /*   params.put("phone", phone);
                params.put("name", username);
                params.put("pwd",password);
                params.put("email", email);*/
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }


    public class GridViewAdapter extends BaseAdapter {
            private Context mContext;
            ArrayList<String> images, id;


            public GridViewAdapter(Context c, ArrayList<String> thumb_nail_images, ArrayList<String> thumb_id) {
                mContext = c;
                this.images = thumb_nail_images;
                this.id = thumb_id;

            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return images.size();
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View grid;
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (convertView == null) {

                    grid = new View(mContext);
                    grid = inflater.inflate(R.layout.grid_item, null);
                    // TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                    ImageView imageView = (ImageView) grid.findViewById(R.id.img_thumbnail);
                    // textView.setText(web[position]);
                    Picasso.with(getApplicationContext())
                            .load(images.get(position)).into(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplication(), ProductList.class);
                            intent.putExtra("id", id.get(position));
                            startActivity(intent);
                        }
                    });


                } else {
                    grid = (View) convertView;
                }


                return grid;
            }
        }

    }

