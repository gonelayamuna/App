package com.example.user.smgapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Map;

/**
 * Created by user on 6/25/2016.
 */
public class WishList extends NavigationDrawer {
    ListView wishlist_lv;
    TextView app_title, no_products;
    ArrayList<HashMap<String, String>> list;
    String wishList_url, remove_wishList_url;
    WishList_adapter adapter;
    Typeface face;
    Dialog review_dialog;
    ImageView close_review_dialog;
    Button submit_review_btn;

    ProgressBar pBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.product_details_page);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.wishlist, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wishlist_lv = (ListView) findViewById(R.id.wish_list);
        app_title = (TextView) findViewById(R.id.app_title);
        app_title.setTypeface(face);
        app_title.setText("Wishlist");
        no_products = (TextView) findViewById(R.id.no_products_wislist);
        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        no_products.setTypeface(face);
      //  pBar= (ProgressBar) findViewById(R.id.progress_bar_custom_wishlist);


        if (SingletonActivity.custidstr.isEmpty()) {
            wishlist_lv.setVisibility(View.INVISIBLE);
            no_products.setVisibility(View.VISIBLE);
            no_products.setText("No products in wish list");
            review_dialog = new Dialog(WishList.this, R.style.AppTheme);
            review_dialog.setCancelable(false);
            review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            review_dialog.setContentView(R.layout.custom);
            submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
            close_review_dialog = (ImageView) review_dialog.findViewById(R.id.review_submit_close);
            close_review_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    review_dialog.dismiss();
                }
            });
            submit_review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    review_dialog.dismiss();
                }
            });
            review_dialog.show();
        } else {
            wishList_url = SingletonActivity.API_URL + "api/wishlist.php?customer_id=" + SingletonActivity.custidstr;
            Log.e("wishlist..", "wishlist.." + wishList_url);

            if(SingletonActivity.checkConnectivity(WishList.this)){

                getWishList();


            }
            else{
                Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
                startActivity(intent1);
            }

           /* if (list.isEmpty()){
                wishlist_lv.setVisibility(View.INVISIBLE);
                no_products.setText("No products");
            }*/

        }


        AdapterView.OnItemClickListener wish_item_click = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Log.e("ur detail id..", "details id.." + list.get(position).get("product_id"));
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("product_id", list.get(position).get("product_id"));
                // intent.putExtra("special_price",list.get(position).get("product_id"));
                startActivity(intent);

            }
        };
        wishlist_lv.setOnItemClickListener(wish_item_click);

    }

    private void getWishList() {
      //  pBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                wishList_url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                //pBar .setVisibility(View.INVISIBLE);

                try {
                    String response_code = response.getString("responseCode");

                    if (response_code.equals("1")) {
                        JSONArray wishlist_array = response.getJSONArray("wishlistResponse");

                        list = new ArrayList<HashMap<String, String>>();

                        if (wishlist_array != null) {

                            for (int i = 0; i < wishlist_array.length(); i++) {
                                JSONObject jobj = wishlist_array.getJSONObject(i);
                                HashMap<String, String> Product_map = new HashMap<>();
                                Product_map.put("product_id", jobj.getString("productId"));
                                Product_map.put("product_name", jobj.getString("productName"));
                                Product_map.put("product_image", jobj.getString("thumbnailUrl"));
                                Product_map.put("product_price", jobj.getString("regularPrice"));
                                Product_map.put("special_price", jobj.getString("specialPrice"));
                                list.add(Product_map);
                             /*   gadapter = new wishList_adapter(WishList.this, list);
                                grid.setAdapter(gadapter);*/
                                //  wishlist_lv.setAdapter(new WishList_adapter(WishList.this, list));

                                adapter = new WishList_adapter(WishList.this, list);
                                wishlist_lv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();


                            }

                        }


                    } else {
                        wishlist_lv.setVisibility(View.INVISIBLE);
                        no_products.setVisibility(View.VISIBLE);
                        no_products.setText("No products in wish list");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Request", "Error: " + error.getMessage());
               //pBar.setVisibility(View.INVISIBLE);

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



    public class WishList_adapter extends BaseAdapter {


        List<HashMap<String, String>> Category_listData;
        HashMap<String, String> map;
        Context context;
        Typeface face;

        private LayoutInflater inflater = null;

        public WishList_adapter(Activity context, List<HashMap<String, String>> aList) {
            // TODO Auto-generated constructor stub
            Category_listData = aList;
        /*for(int i=1;i<aList.size();i++)
        {
            Category_listData.add(aList.get(i));
        }*/
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Category_listData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder {
            TextView name, price, o_price;
            ImageView img, dlt_wislist;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = new Holder();
            View rowView;
            this.face = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
            rowView = inflater.inflate(R.layout.wish_list_item, null);
            holder.name = (TextView) rowView.findViewById(R.id.wishlist_title);
            holder.img = (ImageView) rowView.findViewById(R.id.wishlist_img);
            holder.dlt_wislist = (ImageView) rowView.findViewById(R.id.wish_list_dlt);

            holder.price = (TextView) rowView.findViewById(R.id.wishlist_special_price);
            holder.o_price = (TextView) rowView.findViewById(R.id.wishlist_r_price);
            map = Category_listData.get(position);
            holder.name.setTypeface(face);
            holder.name.setText(map.get("product_name"));

            holder.dlt_wislist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    remove_wishList_url = SingletonActivity.API_URL + "api/remove_item_wishlist.php?customerid=" + SingletonActivity.custidstr + "&productid=" + Category_listData.get(position).get("product_id");

                    Log.e("remove url..", "remove url.." + remove_wishList_url);
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                            remove_wishList_url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {


                            try {
                                String name = response.getString("responseCode");
                                if (name.equals("1")) {

                                   // adapter.notifyDataSetChanged();
                                    getWishList();
                                    adapter.notifyDataSetChanged();

                                } else {
                                    Log.e("Delete failed..", "Delete Failed..");

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











                  /*  removeFromwishList(SingletonActivity.API_URL + "api/remove_item_wishlist.php?customerid=" + SingletonActivity.custidstr + "&productid=" + Category_listData.get(position).get("product_id"));
                    Toast.makeText(WishList.this, "Product Deleted from wish list", Toast.LENGTH_SHORT).show();
                 Log.e("removed item ","removed.."+Category_listData.get(position).get("product_id")+"\n"+Category_listData.get(position).get("product_name"));
                    Log.e("", "---array size before remove--" + list.size());
                    list.remove(Category_listData.get(position).get("product_id"));

                    Log.e("", "---array size after remove--" + list.size());
                    adapter = new WishList_adapter(WishList.this, list);
                    wishlist_lv.setAdapter(adapter);*/


            });



            if (map.get("special_price").equals("0.00") || map.get("special_price").equals("") || map.get("special_price").equals(map.get("product_price"))) {
                holder.price.setVisibility(View.INVISIBLE);
                // holder.o_price.setVisibility(View.INVISIBLE);
                holder.o_price.setText("Rs." + map.get("product_price"));
                holder.o_price.setTextAppearance(context, R.style.product_price_txt);

            } else {
                holder.price.setText("Rs." + map.get("special_price"));
                holder.o_price.setText("Rs." + map.get("product_price"));
                holder.o_price.setPaintFlags(holder.o_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            Picasso.with(context).load(map.get("product_image")).into(holder.img);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("id value for details..", "id value for details.." + Category_listData.get(position).get("product_id"));
                }
            });


            return rowView;
        }

    }
}
