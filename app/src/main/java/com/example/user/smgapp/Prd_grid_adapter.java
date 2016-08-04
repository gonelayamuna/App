package com.example.user.smgapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Prd_grid_adapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    List<HashMap<String, String>> Category_listData;
    HashMap<String, String> map;
    Context context;
    Typeface face;
    String wishList_url, remove_wishList_url;
    Cursor cursor;
    NavigationDrawer nav = new NavigationDrawer();
    Dialog review_dialog;
    ImageView close_review_dialog;
    Button submit_review_btn;

    public Prd_grid_adapter(Activity context, List<HashMap<String, String>> aList) {


        // TODO Auto-generated constructor stub
        Category_listData = aList;
        /*/for(int i=1;i<aList.size();i++)
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

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        final View rowView;
        this.face = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
        rowView = inflater.inflate(R.layout.grid_item_view, null);
        holder.name = (TextView) rowView.findViewById(R.id.p_name);
        holder.img = (ImageView) rowView.findViewById(R.id.p_img);
        holder.wish_list = (ImageView) rowView.findViewById(R.id.wish_list);
        holder.price = (TextView) rowView.findViewById(R.id.p_price);
        holder.original_price = (TextView) rowView.findViewById(R.id.orginal_p);
        map = Category_listData.get(position);
        holder.name.setTypeface(face);
        holder.name.setText(map.get("product_name"));
        Log.e("wish list staus..", "wishlist status.." + map.get("is_wishlist"));
        //  map=new HashMap<>();

        if (map.get("is_wishlist").equals("0") || map.get("is_wishlist").equals(null)||SingletonActivity.custidstr.isEmpty()) {
            holder.wish_list.setImageResource(R.drawable.empty_wishlist);
        } else {
            holder.wish_list.setImageResource(R.drawable.wishlist);
        }



        holder.wish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingletonActivity.custidstr.isEmpty()) {
                    Toast.makeText(context, "Ur not logged in,Please Login", Toast.LENGTH_SHORT).show();


                    review_dialog = new Dialog(context, R.style.AppTheme);
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
                            review_dialog.dismiss();
                        }
                    });
                    review_dialog.show();

                 /*   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Alert!");
                    builder.setMessage("Ur not logged in")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(context, Login.class);
                                    context.startActivity(intent);
                                }


                            });
                    // Create the AlertDialog object and return it
                    builder.show();*/

                } else {
                    wishList_url = SingletonActivity.API_URL + "api/add_wishlist.php?customer_id=" + SingletonActivity.custidstr + "&product_id=" + Category_listData.get(position).get("product_id");
                    remove_wishList_url = SingletonActivity.API_URL + "api/remove_item_wishlist.php?customerid=" + SingletonActivity.custidstr + "&productid=" + Category_listData.get(position).get("product_id");
                    Log.e("wishlist api..", "wish list api.." + wishList_url);
                    Log.e("remove wishlist api..", "remove wish list api.." + remove_wishList_url);
                    v.setActivated(!v.isActivated());
                    String wish_status = Category_listData.get(position).get("is_wishlist");
                    Log.e("wish status..", "wish status.." + wish_status);
                    int stat = Integer.parseInt(wish_status);
                    Log.e("status...", "status for clicked..." + stat);
                    if (stat == 0) {

                        nav.addTowishList(wishList_url);
                        Toast.makeText(context, "addedd..", Toast.LENGTH_SHORT).show();
                        //holder.wish_list.setImageResource(R.drawable.wishlist);
                        Resources resources = context.getResources();
                        holder.wish_list.setImageDrawable(ContextCompat.getDrawable(context,
                                R.drawable.wishlist));
                        Category_listData.get(position).put("is_wishlist","1");


                    } else {

                        nav.removeFromwishList(remove_wishList_url);
                        // holder.wish_list.setImageResource(R.drawable.empty_wishlist);
                        Toast.makeText(context, "removed....", Toast.LENGTH_SHORT).show();
                        Resources resources = context.getResources();
                        holder.wish_list.setImageDrawable(resources.getDrawable(R.drawable.empty_wishlist));
                        Category_listData.get(position).put("is_wishlist","0");

                    }
                   /* v.setActivated(!v.isActivated());
                    if (v.isActivated()){
                        Toast.makeText(context,"addedd..",Toast.LENGTH_SHORT).show();
                        holder.wish_list.setImageResource(R.drawable.wishlist);
                        nav.addTowishList(wishList_url);
                    }
                    else {
                        Toast.makeText(context,"removed....",Toast.LENGTH_SHORT).show();
                        nav.removeFromwishList(remove_wishList_url);
                        holder.wish_list.setImageResource(R.drawable.empty_wishlist);
                    }*/


                }


            }
        });


        if (map.get("special_price").equals("0.00")) {
            holder.price.setVisibility(View.INVISIBLE);
            // holder.o_price.setVisibility(View.INVISIBLE);
            holder.original_price.setText("Rs." + map.get("product_price"));
            holder.original_price.setTextAppearance(context, R.style.product_price_txt);

        } else {
            holder.price.setText("Rs." + map.get("special_price"));
            holder.original_price.setText("Rs." + map.get("product_price"));
            holder.original_price.setPaintFlags(holder.original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }


       /* holder.price.setText("Rs." + map.get("special_price"));
        holder.original_price.setText("Rs."+map.get("product_price"));
        holder.original_price.setPaintFlags(holder.original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);*/
        Picasso.with(context).load(map.get("product_image")).placeholder(R.drawable.loading)
                .fit().into(holder.img);

        return rowView;
    }

    public class Holder {
        TextView name, price, original_price;
        ImageView img, wish_list;
    }

}