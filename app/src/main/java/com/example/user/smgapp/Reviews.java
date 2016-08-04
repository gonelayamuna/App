package com.example.user.smgapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 13-06-2016.
 */
public class Reviews extends NavigationDrawer {
    TextView app_title;
    ArrayList<String> reviews_array;
    // TextView username, user_review, user_review_date, review_title;
    ListView review_list;
    ArrayList<HashMap<String, String>> final_reviews_array;
    ArrayList<Float> percent_array;
    ReviewAdapter review_adapter;
    HashMap<String, String> reviews_map;
    String pass_id, review_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.reviews, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title = (TextView) findViewById(R.id.app_title);
        app_title.setText("User Reviews");

        review_list = (ListView) findViewById(R.id.reviews_list);
        Intent intent = getIntent();
        pass_id = intent.getStringExtra("passed_id");
        Log.e("id value..", "p id val.." + pass_id);
        review_url = SingletonActivity.API_URL + "api/review.php?product_id=" + pass_id;

        if(SingletonActivity.checkConnectivity(Reviews.this)){

            getReview();
        }
        else{
            Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent1);
        }


    }

    private void getReview() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                review_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Request", response.toString());
                        try {
                            response.getString("isSuccess");
                            Log.e("response sucess..", "response sucess..");
                            JSONObject jobject = response.getJSONObject("result");
                            JSONArray rating_percent = jobject.getJSONArray("rating_persent");
                            if (rating_percent != null) {
                                percent_array = new ArrayList<>();
                                for (int i = 0; i < rating_percent.length(); i++) {
                                    Log.e("%..", "%.." + rating_percent.getString(i));
                                    percent_array.add(Float.parseFloat(rating_percent.getString(i)));
                                }
                            }

                            JSONArray review_array = jobject.getJSONArray("review");
                            if (review_array != null) {
                                final_reviews_array = new ArrayList<>();
                                for (int i = 0; i < review_array.length(); i++) {
                                    reviews_map = new HashMap<>();
                                    JSONObject review_obj = review_array.getJSONObject(i);
                                    String review_title = review_obj.getString("review_title");
                                    String date = review_obj.getString("review_date");
                                    String customer_name = review_obj.getString("customer_name");
                                    String review = review_obj.getString("review");


                                    Log.e("review details..", "review details.." + review_title + "\n" + date + "\n" + customer_name + "\n" + review);
                                    reviews_map.put("title", review_title);
                                    reviews_map.put("date", date);
                                    reviews_map.put("c_nam", customer_name);
                                    reviews_map.put("review", review);
                                    final_reviews_array.add(reviews_map);
                                    review_list.setAdapter(new ReviewAdapter(getApplicationContext(), final_reviews_array, percent_array));

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

                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);


    }


    public class ReviewAdapter extends BaseAdapter {
        List<HashMap<String, String>> review_array_adapter;
        HashMap<String, String> hmap;
        ArrayList<Float> percent_array;

        Context con;

        public ReviewAdapter(Context context, List<HashMap<String, String>> review_array_list, ArrayList<Float> p_array) {
            this.review_array_adapter = review_array_list;
            this.con = context;
            this.percent_array = p_array;
        }


        @Override
        public int getCount() {
            return review_array_adapter.size();
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
            View list;
            LayoutInflater inflater = (LayoutInflater) con
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                list = new View(con);
                list = inflater.inflate(R.layout.review_row, null);
                TextView username = (TextView) list.findViewById(R.id.review_user_name);
                TextView review_title = (TextView) list.findViewById(R.id.review_title);
                TextView user_review = (TextView) list.findViewById(R.id.review_des);
                TextView user_review_date = (TextView) list.findViewById(R.id.review_date);
                RatingBar user_ratebar = (RatingBar) list.findViewById(R.id.ind_rating);

               /* LayerDrawable stars = (LayerDrawable) user_ratebar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);*/
                Float rating = (percent_array.get(position)) / 20;
                Log.e("rating..","rating from %.."+rating);
                user_ratebar.setRating(rating);
                LayerDrawable stars = (LayerDrawable) user_ratebar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

                review_title.setText(review_array_adapter.get(position).get("title"));
                user_review.setText(review_array_adapter.get(position).get("review"));
                username.setText("By" + "\t" + review_array_adapter.get(position).get("c_nam"));
                user_review_date.setText(review_array_adapter.get(position).get("date"));


            } else {
                list = (View) convertView;
            }
            return list;
        }
    }
}
