package com.example.user.smgapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.example.user.smgapp.R.id.checkbox_eggless;
import static com.example.user.smgapp.R.id.in_date;


public class ProductDetails extends NavigationDrawer {
    TextView app_title, p_det_priceTxt_1, p_det_priceTxt_2, p_det_priceTxt_3, p_det_price_1, p_det_price_2, p_det_price_3;
    Typeface face;
    TextView delvry_validatn_txt, pincode_validatn_txt;
    String passed_id, url_ProductList, availability_value, review_url, wishList_url, remove_wishList_url, submit_review_url;
    String rev_title, nick_name;
    String cityfromdb;
    String price;
    ProgressDialog pdia;
    String sizestr, weightstr, flavourstr, colstr;
    //ProgressDialog pDialog;
    String p_id, p_sku, p_name, p_img, p_price, p_description, special_price, product_price_single_txt;
    TextView tv_pName, tv_pPrice, tv_pDescriptn, tv_pPrice_strikeout, salesPkg_des, tv_product_price_single_txt;//, product_name_add_details, sku_txt_value
    ImageView im_pImage, more1, more2, more3, arrow_decscription;
    TableLayout table_additonl, table_review, table_det_pg_price;
    LinearLayout divdr_review_pink;
    TextView statc_howUrate, statc_rating, stock_txt, mandratory_txt, rating_alert_txt, user_total_reviews_txt;
    RatingBar rating_bar;
    TextInputLayout userReview;
    TextView submit_mandratory_txt, submit_rating_alert, total_reviews_txt;
    ImageView close_review_dialog, p_det_wishlist;
    EditText userReview_edittxt, tv_pincode, review_submit_editText;
    Button submit_review, gift_it;
    EditText user_review_name, review_title_edit;
    TextView username_alert, title_alert;
    String user_name_string, review_title_string;
    RelativeLayout relative_ratebar, show_hide, wt_outer, flvr_outer, clr_outer, size_outer;
    RatingBar ratebar, ratingBar_userReviews, submit_user_review_ratebar;
    int availability;
    LinearLayout reviews_layout;
    ProgressBar pBar;
    Gallery gallery;
    Button dialogloginbtn, dialogsignupbtn;
    Button datebtn, timebtn;
    //HashMap<String, String> images_hashmap;
    ArrayList<String> images_array, wt_title_array, wt_price_array, flvr_title_array, flvr_price_array;
    Boolean eggless;
    ArrayList<String> config_array, size_array, color_array;
    HashMap<String, String> config_details_map;
    ArrayList<HashMap<String, String>> config_details_array;
    HashMap<String, String> images_hashmap;
    LinearLayout share_layout, addTocart, bluraddtocart;
    String user_review, p_validity, p_brand, p_seller_brand, p_flavour, is_perishable, submitted_user_review_string, is_wislist;
    String sales_packages_value, bangalore_price, delhi_price, mumbai_price, chennai_price;
    HomePage home;
    Spinner wt_spinner, flvr_spinner, clr_spinner, size_spinner;
    LinkedHashMap<String, String> additnl_infr_map;
    AutoCompleteTextView delivryCity;
    ScrollView scroll;
    // TextView city_alert;
    ArrayList<HashMap<String, String>> getPinCodeandCityList;
    DBController controller = new DBController(this);
    int maxlen = 6;
    int flag = 1;
    ArrayList<HashMap<String, String>> attrib_array;
    ArrayList<String> citieslist = new ArrayList<String>();

    HashMap<String, String> attribute_values;
    String atrib_label, attrib_val;
    String rating_percent;
    float rating_cal, rating_value, final_rating;
    int review_length;
    Dialog mDialog;
    ImageView cancel_dialog_login;
    Button loginhint, submit_review_btn;
    Dialog review_dialog;
    ListView l1, l2, l3;
    TextView txt7, txt10, txt12, txt20, textView4;
    String review_title, review_date, review, ovrl_rating, individual_rating, review_user_name;
    ArrayList<String> user_reviews_array = new ArrayList<>();
    int status;
    int autocompletelength = 2;
    String autocompletetext, strapplycodeedittxt;
    TextView txt_pincode_alert, product_delivery_time;
    ArrayAdapter arrayAdapter;
    ArrayList<String> val, val2, val3;
    CheckBox eggless_checkbox;
    Button image_upload;
    private static final int RESULT_SELECT_IMAGE = 1;

    private int mYear, mMonth, mDay, mHour, mMinute;
    String datestr, timestr;
    TextView file_chosen_stat;
    String imageName;
    Menu menu;

    EditText date_edit_txt, time_edit_txt;
    private boolean inBed = false;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String pincodestr;

    public String SERVER = "http://192.168.1.2/co/storemanger/saveImage.php",
            timestamp;

    String timeparam, selectshiplabelstr;
    String citycodestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.product_details_page);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.product_details_page, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app_title = (TextView) findViewById(R.id.app_title);
        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        getSupportActionBar().setTitle("");
        app_title.setTypeface(face);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Intent in = getIntent();
        passed_id = in.getStringExtra("product_id");
        SingletonActivity.productid = passed_id;
        gallery = (Gallery) findViewById(R.id.gallery1);
        p_det_wishlist = (ImageView) findViewById(R.id.p_det_wishlist);
        tv_pDescriptn = (TextView) findViewById(R.id.tv_det_descrptn);
        username_alert = (TextView) findViewById(R.id.username_alert);
        title_alert = (TextView) findViewById(R.id.title_alert);
        user_review_name = (EditText) findViewById(R.id.user_name_edt);
        review_title_edit = (EditText) findViewById(R.id.title_edt);
        product_delivery_time = (TextView) findViewById(R.id.delivry_duratn);
        eggless_checkbox = (CheckBox) findViewById(checkbox_eggless);

        tv_pDescriptn.setTypeface(face);
        txt7 = (TextView) findViewById(R.id.textView7);
        txt10 = (TextView) findViewById(R.id.textView10);
        txt12 = (TextView) findViewById(R.id.textView12);
        txt20 = (TextView) findViewById(R.id.textView20);
        textView4 = (TextView) findViewById(R.id.textView4);
        file_chosen_stat = (TextView) findViewById(R.id.file_chosen_stat);
        txt7.setTypeface(face);
        txt10.setTypeface(face);
        txt12.setTypeface(face);
        txt20.setTypeface(face);
        textView4.setTypeface(face);
        scroll = (ScrollView) findViewById(R.id.scroll);

        prefs = ProductDetails.this.getSharedPreferences(MyPREFERENCES, 0);
        //updateMenuTitles();
        //1)key=Name and 2)key=Emailid 3)key=Phonenos
        //loginboolean = prefs.getBoolean("loginlogoutkey", false);
        firstnamestr = prefs.getString("firstname", "");
        user_review_name.setText(firstnamestr);

        txt_pincode_alert = (TextView) findViewById(R.id.pincode_validtn_txt);
        txt_pincode_alert.setVisibility(View.INVISIBLE);
        // city_alert = (TextView) findViewById(R.id.city_alert);
        // city_alert.setVisibility(View.INVISIBLE);
        datebtn = (Button) findViewById(R.id.btn_date);
        date_edit_txt = (EditText) findViewById(R.id.in_date);
        time_edit_txt = (EditText) findViewById(R.id.in_time);

        timebtn = (Button) findViewById(R.id.btn_time);
        image_upload = (Button) findViewById(R.id.image_upload_btn);

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        //String passed_id="342";
        url_ProductList = SingletonActivity.API_URL + "api/product_details.php?product_id=" + passed_id + "&customer_id=" + SingletonActivity.custidstr;
        review_url = SingletonActivity.API_URL + "api/review.php?product_id=" + passed_id;


        Log.d("p_url", "product_list_url..." + url_ProductList);
        pBar = (ProgressBar) findViewById(R.id.progress_bar_custom);
        user_total_reviews_txt = (TextView) findViewById(R.id.total_reviews_txt);
        /*pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);*/
        pBar.setVisibility(View.VISIBLE);

        citieslist.clear();

        if (SingletonActivity.checkConnectivity(ProductDetails.this)) {

            new Download().execute(url_ProductList);
        } else {
            Intent intent1 = new Intent(getApplicationContext(), NoInternet.class);
            startActivity(intent1);
        }

        // getReview();

        System.out.println("CUST ID from singleton IN PRODUCT DETAILS ACTIVITY IS----" + SingletonActivity.custidstr);
        reviews_layout = (LinearLayout) findViewById(R.id.linearLayout);

        //reviewsFromUser();

        tv_pName = (TextView) findViewById(R.id.p_det_prName);
        tv_pName.setTypeface(face);
        tv_pPrice = (TextView) findViewById(R.id.p_det_price);
        total_reviews_txt = (TextView) findViewById(R.id.total_reviews_txt);
        total_reviews_txt.setTypeface(face);
        //tv_pDescriptn=(TextView)findViewById(R.id.p_det_prName);
        im_pImage = (ImageView) findViewById(R.id.p_det_prImg);
        tv_pName.setTypeface(face);
        tv_pPrice_strikeout = (TextView) findViewById(R.id.p_det_price_strikeout);
        tv_product_price_single_txt = (TextView) findViewById(R.id.product_price);
        tv_product_price_single_txt.setTypeface(face);

        stock_txt = (TextView) findViewById(R.id.stock_txt);
        stock_txt.setTypeface(face);
        tv_pDescriptn = (TextView) findViewById(R.id.tv_det_descrptn);
        /*product_name_add_details = (TextView) findViewById(R.id.product_name_add_details);
        sku_txt_value = (TextView) findViewById(R.id.sku_txt_value);*/
        ratebar = (RatingBar) findViewById(R.id.ratingBar);
        userReview_edittxt = (EditText) findViewById(R.id.userReview);
        mandratory_txt = (TextView) findViewById(R.id.mandratory_msg);
        rating_alert_txt = (TextView) findViewById(R.id.rating_alert);
        share_layout = (LinearLayout) findViewById(R.id.share_layout_tab);
        ratingBar_userReviews = (RatingBar) findViewById(R.id.total_review_rating);
       /* LayerDrawable stars = (LayerDrawable) ratingBar_userReviews.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);*/

        delvry_validatn_txt = (TextView) findViewById(R.id.delvry_validatn_txt);
        pincode_validatn_txt = (TextView) findViewById(R.id.pincode_validate_txt);


        more1 = (ImageView) findViewById(R.id.more_img_1);
        more2 = (ImageView) findViewById(R.id.more_img_2);
        more3 = (ImageView) findViewById(R.id.img_more_3);
        arrow_decscription = (ImageView) findViewById(R.id.imageView5);

        salesPkg_des = (TextView) findViewById(R.id.more_details_1);
        salesPkg_des.setTypeface(face);
        table_additonl = (TableLayout) findViewById(R.id.det_table_additionl);
        table_review = (TableLayout) findViewById(R.id.tableRating);
        divdr_review_pink = (LinearLayout) findViewById(R.id.rating_pinkDivider);

        statc_howUrate = (TextView) findViewById(R.id.howTorateTXT);
        statc_rating = (TextView) findViewById(R.id.ratingTXT);

        relative_ratebar = (RelativeLayout) findViewById(R.id.relativeLayoutRatebar);
        rating_bar = (RatingBar) findViewById(R.id.ratingBar);
        // Drawable stars1 = (Drawable) rating_bar.getProgressDrawable();
        // stars1.setColorFilter(Color.parseColor("#CF0A8B"), PorterDuff.Mode.SRC_ATOP);
        userReview = (TextInputLayout) findViewById(R.id.input_layout_review);
        submit_review = (Button) findViewById(R.id.submit_review);
        show_hide = (RelativeLayout) findViewById(R.id.show_hide);
        gift_it = (Button) findViewById(R.id.btn_gift_it);
        gift_it.setTypeface(face);

        wt_spinner = (Spinner) findViewById(R.id.wt_spinner);
        flvr_spinner = (Spinner) findViewById(R.id.flvr_spinner);
        clr_spinner = (Spinner) findViewById(R.id.color_spinner);
        size_spinner = (Spinner) findViewById(R.id.size_spinner);

        wt_outer = (RelativeLayout) findViewById(R.id.wt_outer);
        // flvr_outer = (RelativeLayout) findViewById(R.id.flvr_outer);
        size_outer = (RelativeLayout) findViewById(R.id.size_outer);
        //  clr_outer = (RelativeLayout) findViewById(R.id.color_outer);
        addTocart = (LinearLayout) findViewById(R.id.addTocart);
        bluraddtocart = (LinearLayout) findViewById(R.id.bluraddTocart);

        citieslist = SingletonActivity.citiesarray;


        delivryCity = (AutoCompleteTextView) findViewById(R.id.delivryCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SingletonActivity.citiesarray);
        delivryCity.setAdapter(adapter);

        delivryCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                autocompletetext = (String) parent.getItemAtPosition(position);

             /*   if(autocompletetext.equalsIgnoreCase("bangalore||Bengaluru")){
                    citycodestr = "22";

                }
                if(autocompletetext.equalsIgnoreCase("mumbai")){
                    citycodestr = "36";

                }
                if(autocompletetext.equalsIgnoreCase("delhi")){
                    citycodestr = "20";

                }*/

                //TODO Do something with the selected text
            }
        });

        p_det_priceTxt_1 = (TextView) findViewById(R.id.p_det_priceTxt_1);
        p_det_priceTxt_2 = (TextView) findViewById(R.id.p_det_priceTxt_2);
        p_det_priceTxt_3 = (TextView) findViewById(R.id.p_det_priceTxt_3);
        p_det_price_1 = (TextView) findViewById(R.id.p_det_price_1);
        p_det_price_2 = (TextView) findViewById(R.id.p_det_price_2);
        p_det_price_3 = (TextView) findViewById(R.id.p_det_price_3);
        table_det_pg_price = (TableLayout) findViewById(R.id.table_det_pg);
        tv_pincode = (EditText) findViewById(R.id.tv_det_pincode);

        //  tv_pincode.setEnabled(false);
        //  timebtn.setEnabled(false);
        //datebtn.setEnabled(false);

        if (delivryCity.getText().toString().isEmpty() || delivryCity.getText().toString().equals("")) {
            // Toast.makeText(ProductDetails.this,"Choose delivery city..!",Toast.LENGTH_SHORT).show();
            delivryCity.requestFocus();
            delvry_validatn_txt.setVisibility(View.VISIBLE);
            delvry_validatn_txt.setText("Choose delivery city..!");
            pincode_validatn_txt.setVisibility(View.VISIBLE);
            pincode_validatn_txt.setText("Enter pincode based on city..!");
            timebtn.setEnabled(false);
            datebtn.setEnabled(false);


        }


        delivryCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //  if (delivryCity.getText().toString().length() >0) {

                if (delivryCity.getText().toString().length() >= autocompletelength) {
                    tv_pincode.setEnabled(true);


                } else {
                    //tv_pincode.setEnabled(false);
                    delvry_validatn_txt.setText("Choose delivery city..!");
                    delvry_validatn_txt.setTextColor(Color.parseColor("#B57EDC"));
                    addTocart.setVisibility(View.GONE);
                    bluraddtocart.setVisibility(View.VISIBLE);
                    timebtn.setEnabled(false);
                    time_edit_txt.setText("");
                    datebtn.setEnabled(false);
                    date_edit_txt.setText("");
                    timebtn.setBackgroundColor(Color.parseColor("#EDADE8"));
                    datebtn.setBackgroundColor(Color.parseColor("#EDADE8"));

                }
                return false;
            }
        });

        tv_pincode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                // tv_pincode.requestFocus();
                // tv_pincode.setFocusable(true);
                pincode_validatn_txt.setText("Enter pincode based on city..!");
                pincode_validatn_txt.setTextColor(Color.parseColor("#B57EDC"));
                addTocart.setVisibility(View.VISIBLE);
                bluraddtocart.setVisibility(View.GONE);
                timebtn.setEnabled(false);
                time_edit_txt.setText("");

                if (tv_pincode.getText().toString().length() >= maxlen && (delivryCity.getText().toString().length() == 0)) {
                    // Toast.makeText(ProductDetails.this, "Select the city first", Toast.LENGTH_SHORT).show();
                    pincode_validatn_txt.setText("Select the city first!");
                    pincode_validatn_txt.setTextColor(Color.parseColor("#DC143C"));
                } else if (tv_pincode.getText().toString().length() >= maxlen) {
                    pincodestr = tv_pincode.getText().toString();
                    cityfromdb = controller.getSingleCityEntry(pincodestr);

                    SingletonActivity.pincode = pincodestr;


                    if (autocompletetext.equals(cityfromdb) && (delivryCity.getText().toString().length() >= autocompletelength) && (tv_pincode.getText().toString().length() > 5)) {

                        //         if (autocompletetext.equals(cityfromdb))  {

                        // Toast.makeText(ProductDetails.this, "this city is serviceable", Toast.LENGTH_SHORT).show();
                        pincode_validatn_txt.setText("Serviceable!");
                        pincode_validatn_txt.setTextColor(Color.parseColor("#8F00FF"));
                        delvry_validatn_txt.setText("Serviceable!");
                        delvry_validatn_txt.setTextColor(Color.parseColor("#8F00FF"));

                        if (is_perishable.equals("1")) {
                            datebtn.setEnabled(true);
                            datebtn.setBackgroundColor(Color.parseColor("#CF0A8B"));

                            if (date_edit_txt.getText().toString().length() > 0 && time_edit_txt.getText().toString().length() > 0) {
                                addTocart.setVisibility(View.VISIBLE);
                                bluraddtocart.setVisibility(View.GONE);
                            } else {
                                addTocart.setVisibility(View.GONE);
                                bluraddtocart.setVisibility(View.VISIBLE);
                            }


                        } else {
                            timebtn.setEnabled(false);
                            datebtn.setEnabled(false);
                            addTocart.setVisibility(View.VISIBLE);
                            bluraddtocart.setVisibility(View.GONE);

                        }


                    } else {
                        // Toast.makeText(ProductDetails.this, "wrong pincode..", Toast.LENGTH_SHORT).show();
                        addTocart.setVisibility(View.GONE);
                        bluraddtocart.setVisibility(View.VISIBLE);
                        timebtn.setEnabled(false);
                        time_edit_txt.setText("");
                        datebtn.setEnabled(false);
                        date_edit_txt.setText("");
                        pincode_validatn_txt.setText("wrong pincode!");
                        pincode_validatn_txt.setTextColor(Color.parseColor("#DC143C"));
                        timebtn.setBackgroundColor(Color.parseColor("#EDADE8"));
                        datebtn.setBackgroundColor(Color.parseColor("#EDADE8"));

                    }
                } else {

                    addTocart.setVisibility(View.GONE);
                    bluraddtocart.setVisibility(View.VISIBLE);
                    timebtn.setEnabled(false);
                    time_edit_txt.setText("");
                    datebtn.setEnabled(false);
                    date_edit_txt.setText("");
                    timebtn.setBackgroundColor(Color.parseColor("#EDADE8"));
                    datebtn.setBackgroundColor(Color.parseColor("#EDADE8"));
                }
                //  return true;


                return false;
            }
        });
        salesPkg_des.setVisibility(View.GONE);
        table_additonl.setVisibility(View.GONE);
        table_review.setVisibility(View.GONE);
        tv_pDescriptn.setVisibility(View.GONE);
        show_hide.setVisibility(View.GONE);
        gift_it.setVisibility(View.VISIBLE);

        more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (salesPkg_des.getVisibility() == View.VISIBLE) {
                    salesPkg_des.setVisibility(View.GONE);
                    more1.setImageResource(R.drawable.down);
                } else {
                    salesPkg_des.setVisibility(View.VISIBLE);
                    more1.setImageResource(R.drawable.up);
                }


            }
        });
        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (table_additonl.getVisibility() == View.VISIBLE) {
                    table_additonl.setVisibility(View.GONE);
                    more2.setImageResource(R.drawable.down);
                } else {
                    table_additonl.setVisibility(View.VISIBLE);
                    more2.setImageResource(R.drawable.up);
                }


            }
        });
        more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (table_review.getVisibility() == View.VISIBLE) {
                    table_review.setVisibility(View.GONE);
                    divdr_review_pink.setVisibility(View.GONE);
                    statc_howUrate.setVisibility(View.GONE);
                    relative_ratebar.setVisibility(View.GONE);
                    user_review_name.setVisibility(View.GONE);
                    username_alert.setVisibility(View.GONE);
                    review_title_edit.setVisibility(View.GONE);
                    title_alert.setVisibility(View.GONE);
                    //rating_bar.setVisibility(View.GONE);
                    userReview.setVisibility(View.GONE);
                    submit_review.setVisibility(View.GONE);
                    mandratory_txt.setVisibility(View.GONE);
                    rating_alert_txt.setVisibility(View.GONE);
                    more3.setImageResource(R.drawable.down);
                } else {
                    table_review.setVisibility(View.VISIBLE);
                    divdr_review_pink.setVisibility(View.VISIBLE);
                    statc_howUrate.setVisibility(View.VISIBLE);
                    relative_ratebar.setVisibility(View.VISIBLE);
                    //rating_bar.setVisibility(View.VISIBLE);
                    userReview.setVisibility(View.VISIBLE);
                    submit_review.setVisibility(View.VISIBLE);
                    more3.setImageResource(R.drawable.up);
                    user_review_name.setVisibility(View.VISIBLE);
                    review_title_edit.setVisibility(View.VISIBLE);
                    user_review_name.setText(firstnamestr);

                }

            }
        });
        arrow_decscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_pDescriptn.getVisibility() == View.VISIBLE) {
                    tv_pDescriptn.setVisibility(View.GONE);
                    arrow_decscription.setImageResource(R.drawable.down);
                } else {
                    tv_pDescriptn.setVisibility(View.VISIBLE);
                    arrow_decscription.setImageResource(R.drawable.up);
                }
            }
        });
        gift_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_hide.setVisibility(View.VISIBLE);
                gift_it.setVisibility(View.GONE);

            }
        });


        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);*/

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, "check this product");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Via"));
            }
        });

        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Log.e("rating value...", "rating start are..." + String.valueOf(rating));
                ratebar.setRating(rating);
                // Drawable drawable = ratingBar.getProgressDrawable();
                // drawable.setColorFilter(Color.parseColor("#CF0A8B"), PorterDuff.Mode.SRC_ATOP);


            }
        });

        submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_review = userReview_edittxt.getText().toString();
                nick_name = user_review_name.getText().toString();
                rev_title = review_title_edit.getText().toString();

                Log.e("custmr id..", "cutmr id.." + SingletonActivity.custidstr);
                if (SingletonActivity.custidstr.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ProductDetails.this, R.style.myDialog));
                    builder.setTitle("Alert!");
                    builder.setMessage("Ur not logged in")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }


                            });
                    // Create the AlertDialog object and return it
                    builder.show();
                } else if (ratebar.getRating() == 0 && user_review.isEmpty() && user_review.equals("") && nick_name.isEmpty() && nick_name.equals("") && rev_title.isEmpty() && rev_title.equals("")) {
                    rating_alert_txt.setVisibility(View.VISIBLE);
                    mandratory_txt.setVisibility(View.VISIBLE);
                    username_alert.setVisibility(View.VISIBLE);
                    title_alert.setVisibility(View.VISIBLE);
                    // Log.e("condition 1..", "condition 1.." + user_review)
                } else if (nick_name.isEmpty() || nick_name.equals("")) {
                    username_alert.setVisibility(View.VISIBLE);

                    rating_alert_txt.setVisibility(View.INVISIBLE);
                    mandratory_txt.setVisibility(View.INVISIBLE);
                    title_alert.setVisibility(View.INVISIBLE);
                } else if (rev_title.isEmpty() || rev_title.equals("")) {
                    title_alert.setVisibility(View.VISIBLE);
                    username_alert.setVisibility(View.INVISIBLE);

                    rating_alert_txt.setVisibility(View.INVISIBLE);
                    mandratory_txt.setVisibility(View.INVISIBLE);
                } else if (ratebar.getRating() == 0) {
                    rating_alert_txt.setVisibility(View.VISIBLE);
                    mandratory_txt.setVisibility(View.INVISIBLE);
                    title_alert.setVisibility(View.INVISIBLE);
                    username_alert.setVisibility(View.INVISIBLE);
                    // Log.e("condition 2..", "condition 2.." + user_review);

                } else if (user_review.isEmpty() || user_review.equals("")) {
                    // Log.e("above conditions sucess", "above conditions sucess.." + "\t" + "user reviews..." + user_review);

                    rating_alert_txt.setVisibility(View.INVISIBLE);
                    mandratory_txt.setVisibility(View.VISIBLE);
                    title_alert.setVisibility(View.INVISIBLE);
                    username_alert.setVisibility(View.INVISIBLE);
                } else {
                    rating_alert_txt.setVisibility(View.INVISIBLE);
                    mandratory_txt.setVisibility(View.INVISIBLE);
                    title_alert.setVisibility(View.INVISIBLE);
                    username_alert.setVisibility(View.INVISIBLE);


                    submit_review_url = SingletonActivity.API_URL + "api/submit_review.php?customer_id=" + SingletonActivity.custidstr + "&product_id=" + passed_id + "&nickname=" + nick_name + "&title=" + rev_title + "&review=" + user_review + "&ratingvalue=" + ratebar.getRating();
                    Log.e("submit url..", "submit url.." + submit_review_url);


                    if (SingletonActivity.checkConnectivity(ProductDetails.this)) {

                        submitReview(submit_review_url);
                    } else {
                        Intent intent1 = new Intent(getApplicationContext(), NoInternet.class);
                        startActivity(intent1);
                    }


                   /* Toast.makeText(getApplicationContext(), "Review submitted", Toast.LENGTH_SHORT).show();
                    review_submit_editText.setText("");
                    submit_user_review_ratebar.setRating(0F);
                    review_dialog.dismiss();
*/


                    Toast.makeText(getApplicationContext(), "Review Sumitted", Toast.LENGTH_SHORT).show();
                    userReview_edittxt.setText("");
                    ratebar.setRating(0F);
                    review_title_edit.setText("");
                    user_review_name.setText("");


                    table_review.setVisibility(View.GONE);
                    divdr_review_pink.setVisibility(View.GONE);
                    statc_howUrate.setVisibility(View.GONE);
                    relative_ratebar.setVisibility(View.GONE);
                    user_review_name.setVisibility(View.GONE);
                    username_alert.setVisibility(View.GONE);
                    review_title_edit.setVisibility(View.GONE);
                    title_alert.setVisibility(View.GONE);
                    //rating_bar.setVisibility(View.GONE);
                    userReview.setVisibility(View.GONE);
                    submit_review.setVisibility(View.GONE);
                    mandratory_txt.setVisibility(View.GONE);
                    rating_alert_txt.setVisibility(View.GONE);
                    more3.setImageResource(R.drawable.down);
                }


            }
        });


        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginboolean == false) {


                    mDialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                    mDialog.setCancelable(false);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.setContentView(R.layout.productdetails_dialog);

                    cancel_dialog_login = (ImageView) mDialog.findViewById(R.id.cancel_logindialog);
                    dialogloginbtn = (Button) mDialog.findViewById(R.id.loginbtn);
                    dialogsignupbtn = (Button) mDialog.findViewById(R.id.signupbtn);

                    dialogloginbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            Intent intent = new Intent(ProductDetails.this, Login.class);
                            startActivity(intent);


                        }
                    });

                    dialogsignupbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            Intent intent = new Intent(ProductDetails.this, RegistrationActivity.class);
                            startActivity(intent);

                        }
                    });


                    cancel_dialog_login.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            mDialog.dismiss();
                        }
                    });

                    mDialog.show();


                } else {

                    if (flag == 1) {
                        addTocart.setEnabled(false);
                        Log.d("Button clicked count", "called");
                        int pinkcolor = getResources().getColor(R.color.lightpink);
                        addTocart.setBackgroundColor(pinkcolor);

                    }

                    if (SingletonActivity.checkConnectivity(ProductDetails.this)) {

                        AddToCartVolleyApi();
                    } else {
                        Intent intent1 = new Intent(getApplicationContext(), NoInternet.class);
                        startActivity(intent1);
                    }


                    flag = 0;

                }


            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void selectImage() {
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }

    /*
    * This function is called when we pick some image from the album
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            //set the selected image to image variable
            Uri image = data.getData();


            String s = getRealPathFromURI(image);

            File f = new File(s);

            imageName = f.getName();
            SingletonActivity.image_name = imageName;
            Log.e("image name...", "image name.." + SingletonActivity.image_name);
            Log.e("path..", "path.." + s + "\t" + imageName);
            file_chosen_stat.setText(SingletonActivity.image_name);


            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp = tsLong.toString();

           // Toast.makeText(getApplicationContext(), timestamp, Toast.LENGTH_SHORT).show();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void AddToCartVolleyApi() {

        pdia = new ProgressDialog(ProductDetails.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();


        // SingletonActivity.custidstr = "112";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SingletonActivity.API_URL + "api/add_to_cart.php?product_id=" + passed_id + "&&quantity=" + 1 + "&&customer_id=" + SingletonActivity.custidstr + "select_ship_label=" + selectshiplabelstr + "&&time=" + timeparam + "&&pin=" + pincodestr + "&&city=" + autocompletetext + "&&date=" + datestr + "&&size=" + sizestr + "&&color=" + colstr + "&&flavour=" + flavourstr + "&&weight=" + weightstr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(ProductDetails.this,response , Toast.LENGTH_LONG).show();
                        pdia.dismiss();

                        System.out.println("URL OF ADD TO CART IS----" + SingletonActivity.API_URL + "api/add_to_cart.php?product_id=" + passed_id + "&&quantity=" + 1 + "&&customer_id=" + SingletonActivity.custidstr);

                        System.out.println("RESPONSE IN ADD TO CART VOLLEY ACTIVITY IS" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String isSuccessStr = jsonObject.getString("isSuccess");

                            String quotestr = jsonObject.getString("quote");

                            System.out.println("ISSUCCESS STRING IN ADD TO CART VOLLEY ACTIVITY IS" + isSuccessStr);
                            System.out.println("QUOTE STRING IN ADD TO CART VOLLEY ACTIVITY IS" + quotestr);

                            if (isSuccessStr.equalsIgnoreCase("true")) {
                                //   pBar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(ProductDetails.this, CartActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                        pdia.dismiss();
                        //  pBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(ProductDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                pdia.dismiss();
            }
        });



       /* stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(stringRequest);*/


        RequestQueue requestQueue = Volley.newRequestQueue(ProductDetails.this);
        requestQueue.add(stringRequest);
    }

    /*private void getReview() {
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
                            JSONArray array = jobject.getJSONArray("review");

                            JSONArray review_array = jobject.getJSONArray("rating_persent");
                            if (review_array != null) {
                                for (int i = 0; i < review_array.length(); i++) {
                                    String review_array_user = review_array.getString(i);
                                    Log.e(" rating..", "review_array_user rating..." + review_array_user);

                                    float individual_rating = (Float.parseFloat(review_array_user) / 100) * 5;
                                    Log.e("individual rating...", "individual rating..." + individual_rating);
                                }
                            }
                            if (array != null) {
                                for (int i = 0; i < array.length(); i++) {
                                    review_length = array.length();
                                    // JSONObject jobj=array.getJSONObject(i);
                                    user_total_reviews_txt.setText(String.valueOf(array.length()) + "\t" + "User Reviews");
                                    Log.e("user reviews text..", "user reviews text.." + user_total_reviews_txt.getText().toString());
                                    Log.e("array length..", "array length.." + array.length());
                                    JSONObject id = (JSONObject) array.get(i);
                                    String custom_review = id.getString("customer_name");
                                    String review = id.getString("review");

                                    Log.e("user review..", "user review.." + review);
                                }
                            }

                            rating_percent = jobject.getString("rating");
                            LayerDrawable stars = (LayerDrawable) ratingBar_userReviews.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                            rating_cal = Float.parseFloat(rating_percent);
                            rating_value = ((rating_cal / 100) * 5);

                            final_rating = (float) Math.round(rating_value * 100) / 100;
                            Log.e("rating percent..", "rating percent..." + rating_percent);
                            Log.e("rating_value..", "rating_value..." + final_rating);
                            // ratingBar_userReviews.setRating(Float.parseFloat(rating_percent));
                            ratingBar_userReviews.setRating(final_rating);


                            if (review_length == 0) {
                                user_total_reviews_txt.setText("Be the first to write review");
                            } else {
                                user_total_reviews_txt.setText(review_length + "\t" + "User Reviews");

                            }
                            reviews_layout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.e("layout clicked..", "layout clicked....");
                                    if (user_total_reviews_txt.getText().toString() == "Be the first to write review") {

                                        review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                                        review_dialog.setCancelable(false);
                                        review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        review_dialog.setContentView(R.layout.custom);

                                        submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
                                        submit_user_review_ratebar = (RatingBar) review_dialog.findViewById(R.id.review_submit_ratebar);
                                        review_submit_editText = (EditText) review_dialog.findViewById(R.id.review_sub_edittxt);
                                        submit_mandratory_txt = (TextView) review_dialog.findViewById(R.id.sumit_mandratory_msg);
                                        submit_rating_alert = (TextView) review_dialog.findViewById(R.id.submit_rating_alert);
                                        close_review_dialog = (ImageView) review_dialog.findViewById(R.id.review_submit_close);


                                        submit_user_review_ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                                // Log.e("rating value...", "rating start are..." + String.valueOf(rating));
                                                Drawable drawable = ratingBar.getProgressDrawable();
                                                drawable.setColorFilter(Color.parseColor("#CF0A8B"), PorterDuff.Mode.SRC_ATOP);


                                            }
                                        });


                                        close_review_dialog.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {
                                                // TODO Auto-generated method stub
                                                review_dialog.dismiss();
                                            }
                                        });


                                        submit_review_btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                submitted_user_review_string = review_submit_editText.getText().toString();
                                                if (submit_user_review_ratebar.getRating() == 0 && submitted_user_review_string.isEmpty() && submitted_user_review_string.equals("")) {
                                                    submit_rating_alert.setVisibility(View.VISIBLE);
                                                    submit_mandratory_txt.setVisibility(View.VISIBLE);
                                                    // Log.e("condition 1..", "condition 1.." + user_review);

                                                } else if (submit_user_review_ratebar.getRating() == 0) {
                                                    submit_rating_alert.setVisibility(View.VISIBLE);
                                                    submit_mandratory_txt.setVisibility(View.INVISIBLE);
                                                    // Log.e("condition 2..", "condition 2.." + user_review);

                                                } else if (submitted_user_review_string.isEmpty() || submitted_user_review_string.equals("")) {
                                                    // Log.e("above conditions sucess", "above conditions sucess.." + "\t" + "user reviews..." + user_review);

                                                    submit_rating_alert.setVisibility(View.INVISIBLE);
                                                    submit_mandratory_txt.setVisibility(View.VISIBLE);
                                                } else {
                                                    submit_rating_alert.setVisibility(View.INVISIBLE);
                                                    submit_mandratory_txt.setVisibility(View.INVISIBLE);


                                                     submit_review_url = SingletonActivity.API_URL + "api/submit_review.php?customer_id=" + SingletonActivity.custidstr + "&product_id=" + passed_id + "&nickname=" + "&title=" + "&review=" + submitted_user_review_string + "ratingvalue=" + submit_user_review_ratebar.getRating();
                                                    Log.e("submit url..", "submit url.." + submit_review_url);
                                                    submitReview(submit_review_url);
                                                    Toast.makeText(getApplicationContext(), "Review submitted", Toast.LENGTH_SHORT).show();
                                                    review_submit_editText.setText("");
                                                    submit_user_review_ratebar.setRating(0F);
                                                    review_dialog.dismiss();
                                                  *//*  Toast.makeText(getApplicationContext(), "Review Sumitted", Toast.LENGTH_SHORT).show();
                                                    review_submit_editText.setText("");
                                                    submit_user_review_ratebar.setRating(0F);
                                                    review_dialog.dismiss();*//*
                                                }


                                            }
                                        });


                                        review_dialog.show();


                                    } else {
                                        Log.e("Already reviews ..", "already reviews are there..");
                                        Intent intent = new Intent(getApplicationContext(), Reviews.class);
                                        intent.putExtra("review_user_name", review_user_name);
                                        intent.putExtra("review_date", review_date);
                                        intent.putExtra("review_title", review_title);

                                    }
                                }
                            });


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
             *//*   params.put("phone", phone);
                params.put("name", username);
                params.put("pwd",password);
                params.put("email", email);*//*
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProductDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.smgapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProductDetails Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.smgapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    class Download extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog.show();
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... f_url) {

            try {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(f_url[0]);
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(entity);

                if (jsonStr != null) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
                    p_id = jsonObjectResult.getString("id");

                    p_name = jsonObjectResult.getString("name");
                    p_img = jsonObjectResult.getString("img");
                    p_price = jsonObjectResult.getString("price");
                    special_price = jsonObjectResult.getString("special_price");

                    p_description = jsonObjectResult.getString("description");
                    availability_value = jsonObjectResult.getString("availability");
                    System.out.println("Availability value is" + availability_value);


                    //p_flavour  = jsonobj_additnl.getString("flavour");
                    if (jsonObjectResult.isNull("image_array")) {
                        images_array = new ArrayList<String>();
                        images_array.add(p_img);

                    } else {
                        JSONArray jarray = jsonObjectResult.getJSONArray("image_array");
                        images_array = new ArrayList<String>();
                        for (int i = 0; i < jarray.length(); i++) {
                            // images_hashmap = new HashMap<>();
                            JSONObject jsonobj_image = jarray.getJSONObject(i);
                            if (jsonobj_image != null) {
                                String image_urls = jsonobj_image.getString("images");
                                //images_hashmap.put("p_image_urls", image_urls);
                                images_array.add(image_urls);
                                // Log.e("images array...", "product details images..." + images_array);
                            }

                        }
                    }

                    sales_packages_value = jsonObjectResult.getString("sales_packages");
                    bangalore_price = "0";
                    delhi_price = "0";
                    mumbai_price = "0";
                    chennai_price = "0";

                    if (!jsonObjectResult.isNull("bangalore_price")) {
                        bangalore_price = jsonObjectResult.getString("bangalore_price");
                        Log.e("blr price..","blr price.."+bangalore_price);
                    }
                    if (!jsonObjectResult.isNull("delhi_price")) {
                        delhi_price = jsonObjectResult.getString("delhi_price");
                    }
                    if (!jsonObjectResult.isNull("mumbai_price")) {
                        mumbai_price = jsonObjectResult.getString("mumbai_price");
                    }
                    if (!jsonObjectResult.isNull("chennai_price")) {
                        chennai_price = jsonObjectResult.getString("chennai_price");
                    }


                    wt_title_array = new ArrayList<String>();
                    wt_price_array = new ArrayList<String>();

                    if (jsonObjectResult.isNull("weight")) {

                    } else {

                        JSONArray weight_jarray = jsonObjectResult.getJSONArray("weight");
                        // wt_title_array.add("Weight");
                        if (weight_jarray != null) {
                            for (int i = 0; i < weight_jarray.length(); i++) {
                                JSONObject jsonobj_weight = weight_jarray.getJSONObject(i);
                                if (jsonobj_weight != null) {

                                    String wt_title = jsonobj_weight.getString("option_title");
                                    String wt_price = jsonobj_weight.getString("option_value_price");
                                    wt_title_array.add(wt_title);
                                    wt_price_array.add(wt_price);

                                }
                            }
                        }

                    }
                    flvr_title_array = new ArrayList<String>();
                    flvr_price_array = new ArrayList<String>();
                    if (jsonObjectResult.isNull("flavour_option")) {

                    } else {

                        JSONArray flavour_jarray = jsonObjectResult.getJSONArray("flavour_option");
                        // flvr_title_array.add("Flavour");

                        if (flavour_jarray != null) {
                            for (int i = 0; i < flavour_jarray.length(); i++) {
                                JSONObject jsonobj_flavour = flavour_jarray.getJSONObject(i);
                                if (jsonobj_flavour != null) {
                                    String flvr_title = jsonobj_flavour.getString("option_title");
                                    String flvr_price = jsonobj_flavour.getString("option_value_price");
                                    flvr_title_array.add(flvr_title);
                                    flvr_price_array.add(flvr_price);

                                }
                            }
                        }
                    }

                    // eggless_array = new ArrayList<String>();
// flvr_price_array = new ArrayList<String>();
                    if (jsonObjectResult.isNull("eggless_cake")) {
                        Log.e("not eggless..", "not eggless");
                        eggless = false;

                    } else {

                        JSONArray eggless_jsonarray = jsonObjectResult.getJSONArray("eggless_cake");
// flvr_title_array.add("Flavour");

                        if (eggless_jsonarray != null) {
                            Log.e("eggless cake..", "egg less cake..");
                            eggless = true;
                        }
                    }


                    JSONArray jarrayAdditnl = jsonObjectResult.getJSONArray("additional_information");
                    additnl_infr_map = new LinkedHashMap<>();

                    if (jarrayAdditnl != null) {
                        attrib_array = new ArrayList<HashMap<String, String>>();


                        for (int i = 0; i < jarrayAdditnl.length(); i++) {


                            JSONObject jsonobj_add = jarrayAdditnl.getJSONObject(i);

                            attribute_values = new HashMap<String, String>();
                            if (jsonobj_add != null) {

                                atrib_label = jsonobj_add.getString("attributelabel");
                                attrib_val = jsonobj_add.getString("attributevalue");
                                if (!(attrib_val.equalsIgnoreCase("null")) && !(attrib_val.equalsIgnoreCase("false")) && !(attrib_val.equalsIgnoreCase("No"))) {
                                    //  ||!attrib_val.equalsIgnoreCase("false")||!attrib_val.equalsIgnoreCase("No")
                                    attribute_values.put("value", attrib_val);
                                    attribute_values.put("label", atrib_label);
                                    Log.e("attributes..", "attributes.." + attribute_values);
                                    attrib_array.add(attribute_values);

                                    Log.e("attributes..", "attributes array..." + attrib_array);

                                }
                                Log.e("attributes..", "attributes array1..." + attrib_array);

                            }

                        }


                    }
                    is_perishable = jsonObjectResult.getString("is_perishable");
                    Log.e("is perishanble...", "is perishable.." + is_perishable);
                    is_wislist = jsonObjectResult.getString("is_wishlist");
                    Log.e("is_wislist...", "is_wislist.." + is_wislist);


                    JSONObject review_obj = jsonObjectResult.getJSONObject("rivews");
                    if (review_obj != null) {


                        JSONArray indivl_rating = review_obj.getJSONArray("rating_persent");
                        if (indivl_rating != null) {
                            SingletonActivity.individual_rating_array = new ArrayList<>();

                            for (int i = 0; i < indivl_rating.length(); i++) {
                                individual_rating = indivl_rating.getString(i);
                                SingletonActivity.individual_rating_array.add(individual_rating);
                                String review_array_user = indivl_rating.getString(i);
                                float individual_rating = (Float.parseFloat(review_array_user) / 100) * 5;
                                Log.e("individual rating..", "individual rating array.." + SingletonActivity.individual_rating_array);
                            }
                        }


                        String review = review_obj.getString("review");
                        Log.e("review string..", "review string.." + review);
                        if (review.equals("null")) {

                            Log.e("no reviews..", "no reviews..");
                        } else {
                            JSONArray jarray_review = review_obj.getJSONArray("review");


                            Log.e("jarray review..", "jarray review.." + jarray_review);
                            if (jarray_review != null) {
                                SingletonActivity.rating_date_array = new ArrayList<>();
                                SingletonActivity.rating_name_array = new ArrayList<>();
                                SingletonActivity.rating_title_array = new ArrayList<>();
                                SingletonActivity.user_review_array = new ArrayList<>();


                                for (int i = 0; i < jarray_review.length(); i++) {

                                    JSONObject jsonobj_review = jarray_review.getJSONObject(i);
                                    review_length = jarray_review.length();

                                    if (jsonobj_review != null) {
                                        user_reviews_array = new ArrayList<>();
                                        review_title = jsonobj_review.getString("review_title");
                                        SingletonActivity.rating_title_array.add(review_title);
                                        review_date = jsonobj_review.getString("review_date");
                                        SingletonActivity.rating_date_array.add(review_date);
                                        review = jsonobj_review.getString("review");
                                        SingletonActivity.user_review_array.add(review);
                                        review_user_name = jsonobj_review.getString("customer_name");

                                        user_reviews_array.add(review_user_name);
                                        user_reviews_array.add(review_date);
                                        user_reviews_array.add(review_title);
                                        user_reviews_array.add(review);


                                        SingletonActivity.rating_name_array.add(review_user_name);
                                        Log.e("ratings...", "ratings.." + review_title + "\t" + review_date + "\n" + review);
                                    }

                                }
                                Log.e("user review array..", "user_reviews_array.." + user_reviews_array);
                                ovrl_rating = review_obj.getString("rating");
                                Log.e("over all rating..", "over all rating.." + ovrl_rating);

                            }


                            rating_percent = review_obj.getString("rating");
                            rating_cal = Float.parseFloat(rating_percent);
                            Log.e("rating cal..", "rating cal.." + rating_cal);

                        }
                    }


                    size_array = new ArrayList<>();
                    color_array = new ArrayList<>();
                    config_array = new ArrayList<>();
                    config_details_array = new ArrayList<>();

                    if (jsonObjectResult.isNull("configurable_associate_product_details")) {
                        Log.e("no config array..", "no config array..");

                    } else {
                        JSONArray config_array = jsonObjectResult.getJSONArray("configurable_associate_product_details");
                        // size_array.add("Size");
                        // color_array.add("Color");
                        if (config_array != null) {
                            for (int i = 0; i < config_array.length(); i++) {
                                config_details_map = new HashMap<>();
                                JSONObject j_config_obj = config_array.getJSONObject(i);
                                String associate_name = j_config_obj.getString("associate_name");
                                String associate_sku = j_config_obj.getString("associate_sku");
                                String associate_price = j_config_obj.getString("associate_price");
                                String associate_special_price = j_config_obj.getString("associate_special_price");
                                String associate_id = j_config_obj.getString("associate_id");
                                String size = j_config_obj.getString("size");
                                String color = j_config_obj.getString("color");
                                config_details_map.put("associate_name", associate_name);
                                config_details_map.put("associate_sku", associate_sku);
                                config_details_map.put("associate_price", associate_price);
                                config_details_map.put("associate_special_price", associate_special_price);
                                config_details_map.put("associate_id", associate_id);
                                config_details_array.add(config_details_map);
                                color_array.add(color);
                                size_array.add(size);

                                Set<String> hs = new HashSet<>();
                                hs.addAll(size_array);
                                size_array.clear();
                                size_array.addAll(hs);


                                Set<String> hs1 = new HashSet<>();
                                hs1.addAll(color_array);
                                color_array.clear();
                                color_array.addAll(hs1);
                                Log.e("config array..", "config array.." + config_details_array);
                                Log.e("configurable details..", "config details.." + associate_id + "\n" + associate_name + "\n" + associate_sku + "\n" + associate_price + "\n" + associate_special_price);


                            }
                        }
                    }


                } else {
                    Log.d("response_null", "null");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            // super.onPostExecute(aVoid);
            // pDialog.cancel();
            pBar.setVisibility(View.INVISIBLE);
            Log.d("inside", "post");
            Picasso.with(ProductDetails.this).load(p_img).into(im_pImage);
            if (eggless == true) {
                eggless_checkbox.setVisibility(View.VISIBLE);
            } else {
                eggless_checkbox.setVisibility(View.INVISIBLE);
            }
            if (is_wislist.equals("null") || is_wislist.contains("null") || is_wislist.isEmpty()) {
                status = 0;
            } else {
                status = Integer.parseInt(is_wislist);
            }
            if (is_perishable.equals("1")) {
                datebtn.setVisibility(View.VISIBLE);
                timebtn.setVisibility(View.VISIBLE);
                time_edit_txt.setVisibility(View.VISIBLE);
                date_edit_txt.setVisibility(View.VISIBLE);
            } else {
                datebtn.setVisibility(View.INVISIBLE);
                timebtn.setVisibility(View.INVISIBLE);
                time_edit_txt.setVisibility(View.INVISIBLE);
                date_edit_txt.setVisibility(View.INVISIBLE);
                product_delivery_time.setVisibility(View.VISIBLE);
                product_delivery_time.setText("Delivery with in 4-7 Business days");
                product_delivery_time.setTextColor(Color.parseColor("#008000"));
                eggless_checkbox.setVisibility(View.INVISIBLE);

            }


            eggless_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
//checked

                        price = tv_pPrice.getText().toString();
                        Log.e("price..", "price.." + price);

                        String[] output = price.split("\\.");
                        Log.e("o/p", "o/p.." + output[0] + "\n" + output[1]);
                        DecimalFormat precision = new DecimalFormat("0.00");
                        double eggless_price = Double.parseDouble(output[1]) + 50.00;
                        Log.e("eggless price..", "eggless price.." + eggless_price);
                        tv_pPrice.setText("Rs." + precision.format(eggless_price));
                    } else {
//not checked
                        Log.e("unchecked..", "unchecked..");

                        String price = tv_pPrice.getText().toString();
                        Log.e("price..", "price.." + price);

                        String[] output = price.split("\\.");
                        Log.e("o/p", "o/p.." + output[0] + "\n" + output[1]);
                        DecimalFormat precision = new DecimalFormat("0.00");
                        double eggless_price = Double.parseDouble(output[1]) - 50.00;
                        Log.e("eggless price..", "eggless price.." + eggless_price);
                        tv_pPrice.setText("Rs." + precision.format(eggless_price));
                    }
                }

            });


            timebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Date date = new Date();
                    date.setHours(date.getHours());
                    System.out.println(date);
                    SimpleDateFormat simpDate;
                    Calendar calendar = Calendar.getInstance();

                    Log.e("time..", "time" + calendar.get(Calendar.HOUR_OF_DAY));
                    simpDate = new SimpleDateFormat("d-M-yyyy");
                    System.out.println("Date ..." + simpDate.format(date));
                    //   String[] parts = simpDate.format(date).split(" ");
                    Log.e("parts..", "parts.." + simpDate.format(date));
                    String strDate = "Current Date : " + simpDate.format(calendar.getTime());
                    int value = (calendar.get(Calendar.HOUR_OF_DAY));

                    Log.e("time...", "time.." + strDate);
                    val = new ArrayList<String>();
                    val2 = new ArrayList<String>();
                    val3 = new ArrayList<String>();
                    if (datestr.equals(simpDate.format(calendar.getTime()))) {
                        Log.e("same date..", "same date..");


                        if (delivryCity.getText().toString().equals("BANGALORE") || delivryCity.getText().toString().equals("DELHI") || delivryCity.getText().toString().equals("MUMBAI")) {

                            review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                            review_dialog.setCancelable(false);
                            review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            review_dialog.setContentView(R.layout.delivery_dialog);
                            //submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
                            close_review_dialog = (ImageView) review_dialog.findViewById(R.id.close_dialog);
                            l1 = (ListView) review_dialog.findViewById(R.id.standar_deliver);
                            l2 = (ListView) review_dialog.findViewById(R.id.midnight_delivery);
                            l3 = (ListView) review_dialog.findViewById(R.id.normal_delivery);

                            close_review_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    review_dialog.dismiss();
                                }
                            });
                            if (value < 20) {
                                for (int i = value + 2; i <= 19; i++) {

                                    Log.e("i value..", "i value.." + i);

                                    val.add((String.valueOf(i) + ":00" + "-" + (i + 3) + ":00 hrs"));
                                    Log.e("value1...", "value1.." + val);
                                }
                            } else {
                                val.add("Not applicable");
                            }


                            if (value < 22) {
                                for (int i = value + 4; i <= 21; i++) {
                                    Log.e("i value2...", "i2 value..." + i);

                                    val2.add((String.valueOf(i) + ":00" + "-" + (i + 1) + ":00 hrs"));
                                    Log.e("value2...", "value2.." + val2);
                                }
                            } else {
                                val2.add("not applicable");
                            }


                            if (value <= 23) {
                                val3.add("23:00-00:30 hrs");
                            } else {
                                val3.add("not applicable");
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val2);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val3);
                            l1.setAdapter(adapter);
                            l2.setAdapter(adapter3);
                            l3.setAdapter(adapter2);


                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //  Toast.makeText(getApplicationContext(), "u selected" + val.get(position), Toast.LENGTH_SHORT).show();

                                    timeparam = val.get(position);

                                    selectshiplabelstr = "Standard Delivery";
                                    time_edit_txt.setText(val.get(position) + " (Free delivery)");
                                    SingletonActivity.time = val.get(position) + " (Free delivery)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val3.get(position);

                                    selectshiplabelstr = "Mid Night Delivery";
                                    // Toast.makeText(getApplicationContext(), "u selected" + val3.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val3.get(position) + " (149 Extra)");
                                    SingletonActivity.time = val3.get(position) + " (149 Extra)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val2.get(position);

                                    selectshiplabelstr = "Fixed Time Delivery";
                                    //  Toast.makeText(getApplicationContext(), "u selected" + val2.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val2.get(position) + " (100 Extra)");
                                    SingletonActivity.time = val2.get(position) + " (100 Extra)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });
                            review_dialog.show();

                        } else {
                            Log.e("not available after 6", "not available");

                            val.add("09:00-21:00 hrs");
                            val2.add("Not applicable");
                            val3.add("23:20-00:30 hrs");

                            review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                            review_dialog.setCancelable(false);
                            review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            review_dialog.setContentView(R.layout.delivery_dialog);
                            //submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
                            close_review_dialog = (ImageView) review_dialog.findViewById(R.id.close_dialog);
                            l1 = (ListView) review_dialog.findViewById(R.id.standar_deliver);
                            l2 = (ListView) review_dialog.findViewById(R.id.midnight_delivery);
                            l3 = (ListView) review_dialog.findViewById(R.id.normal_delivery);

                            close_review_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    review_dialog.dismiss();
                                }
                            });

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val2);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val3);
                            l1.setAdapter(adapter);
                            l2.setAdapter(adapter3);
                            l3.setAdapter(adapter2);


                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // Toast.makeText(getApplicationContext(), "u selected" + val.get(position), Toast.LENGTH_SHORT).show();

                                    timeparam = val.get(position);
                                    selectshiplabelstr = "Standard Delivery";
                                    time_edit_txt.setText(val.get(position) + " (Free delivery)");
                                    SingletonActivity.time = val.get(position) + " (Free delivery)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val3.get(position);

                                    selectshiplabelstr = "Mid Night Delivery";
                                    //  Toast.makeText(getApplicationContext(), "u selected" + val3.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val3.get(position) + " (149 Extra)");
                                    SingletonActivity.time = val3.get(position) + " (149 Extra)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                           /* l3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val2.get(position);
                                    selectshiplabelstr = "Fixed Time Delivery";
                                    Toast.makeText(getApplicationContext(), "u selected" + val2.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val2.get(position) + " (100 Extra)");
                                    review_dialog.dismiss();
                                }
                            });*/
                            review_dialog.show();


                        }

                    } else {
                        Log.e("other date..", "other date..");


                        if (delivryCity.getText().toString().equals("BANGALORE") || delivryCity.getText().toString().equals("DELHI") || delivryCity.getText().toString().equals("MUMBAI")) {

                            review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                            review_dialog.setCancelable(false);
                            review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            review_dialog.setContentView(R.layout.delivery_dialog);
                            //submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
                            close_review_dialog = (ImageView) review_dialog.findViewById(R.id.close_dialog);
                            l1 = (ListView) review_dialog.findViewById(R.id.standar_deliver);
                            l2 = (ListView) review_dialog.findViewById(R.id.midnight_delivery);
                            l3 = (ListView) review_dialog.findViewById(R.id.normal_delivery);

                            close_review_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    review_dialog.dismiss();
                                }
                            });

                            val = new ArrayList<String>();
                            val2 = new ArrayList<String>();
                            val3 = new ArrayList<String>();

                            val.add("09:00-12:00hrs");
                            val.add("10:00-13:00hrs");
                            val.add("11:00-14:00hrs");
                            val.add("12:00-15:00hrs");
                            val.add("13:00-16:00hrs");
                            val.add("14:00-17:00hrs");
                            val.add("15:00-18:00hrs");
                            val.add("16:00-19:00hrs");
                            val.add("17:00-20:00hrs");
                            val.add("18:00-21:00hrs");
                            val.add("19:00-22:00hrs");


                            val2.add("11:00-12:00hrs");
                            val2.add("12:00-13:00hrs");
                            val2.add("13:00-14:00hrs");
                            val2.add("14:00-15:00hrs");
                            val2.add("15:00-16:00hrs");
                            val2.add("16:00-17:00hrs");
                            val2.add("17:00-18:00hrs");
                            val2.add("18:00-19:00hrs");
                            val2.add("19:00-20:00hrs");
                            val2.add("20:00-21:00hrs");
                            val2.add("21:00-22:00hrs");


                            val3.add("23:00-00:30hrs");


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val2);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val3);
                            l1.setAdapter(adapter);
                            l2.setAdapter(adapter3);
                            l3.setAdapter(adapter2);


                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(getApplicationContext(), "u selected" + val.get(position), Toast.LENGTH_SHORT).show();

                                    timeparam = val.get(position);
                                    selectshiplabelstr = "Standard Delivery";
                                    time_edit_txt.setText(val.get(position) + " (Free delivery)");
                                    SingletonActivity.time = val.get(position) + " (Free delivery)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val3.get(position);
                                    selectshiplabelstr = "Mid Night Delivery";
                                    Toast.makeText(getApplicationContext(), "u selected" + val3.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val3.get(position) + " (149 Extra)");
                                    SingletonActivity.time = val3.get(position) + " (149 Extra)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    timeparam = val2.get(position);
                                    selectshiplabelstr = "Fixed Time Delivery";
                                    Toast.makeText(getApplicationContext(), "u selected" + val2.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val2.get(position) + " (100 Extra)");
                                    SingletonActivity.time = val2.get(position) + " (100 Extra)";
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });
                            review_dialog.show();

                        } else {

                            // timebtn.setEnabled(true);
                            review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
                            review_dialog.setCancelable(false);
                            review_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            review_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            review_dialog.setContentView(R.layout.delivery_dialog);
                            close_review_dialog = (ImageView) review_dialog.findViewById(R.id.close_dialog);
                            //submit_review_btn = (Button) review_dialog.findViewById(R.id.submit_review_btn);
                            //close_review_dialog = (ImageView) review_dialog.findViewById(R.id.review_submit_close);
                            l1 = (ListView) review_dialog.findViewById(R.id.standar_deliver);
                            l2 = (ListView) review_dialog.findViewById(R.id.midnight_delivery);
                            l3 = (ListView) review_dialog.findViewById(R.id.normal_delivery);

                            close_review_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    review_dialog.dismiss();
                                }
                            });

                            val = new ArrayList<String>();
                            val2 = new ArrayList<String>();
                            val3 = new ArrayList<String>();

                            val.add("09:00-21:00hrs");


                            val2.add("Not applicable");


                            val3.add("23:00-00:30hrs");


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val2);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ProductDetails.this,
                                    R.layout.delivertype_list, R.id.delivery_type_txt, val3);
                            l1.setAdapter(adapter);
                            l2.setAdapter(adapter3);
                            l3.setAdapter(adapter2);

                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(getApplicationContext(), "u selected" + val.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val.get(position) + " (Free delivery)");
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);
                                    review_dialog.dismiss();
                                }
                            });

                            l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(getApplicationContext(), "u selected" + val3.get(position), Toast.LENGTH_SHORT).show();
                                    time_edit_txt.setText(val3.get(position) + " (149 Extra)");
                                    addTocart.setVisibility(View.VISIBLE);
                                    bluraddtocart.setVisibility(View.GONE);

                                    review_dialog.dismiss();
                                }
                            });


                        /*l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getApplicationContext(),"u selected"+val.get(position), Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setTitle("Alert");
                                builder.setMessage("Fixed Time Delivery not applicable")
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       finish();
                                    }
                                });
                            }
                        });*/
                            review_dialog.show();
                        }
                    }

                }
            });


            datebtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    if (v == datebtn) {

                        // Process to get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        // Launch Date Picker Dialog
                        DatePickerDialog dialog = new DatePickerDialog(ProductDetails.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // Display Selected date in textbox
                                        datestr = dayOfMonth + "-"
                                                + (monthOfYear + 1) + "-" + year;
                                    /*datestr = year + "-"
                                            + (monthOfYear + 1) + "-" + dayOfMonth;*/
                                        System.out.println("Datestr is--" + datestr);

                                        date_edit_txt.setText(datestr);
                                        timebtn.setEnabled(true);
                                        timebtn.setBackgroundColor(Color.parseColor("#CF0A8B"));

                                        SingletonActivity.date = datestr;


                                    }

                                }, mDay, mMonth, mYear);

                        dialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        dialog.show();
                    }

                }
            });


            p_det_wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SingletonActivity.custidstr.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ur not logged in,Please Login", Toast.LENGTH_SHORT).show();
                        review_dialog = new Dialog(ProductDetails.this, R.style.AppTheme);
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

                    } else {
                        wishList_url = SingletonActivity.API_URL + "api/add_wishlist.php?customer_id=" + SingletonActivity.custidstr + "&product_id=" + passed_id;
                        remove_wishList_url = SingletonActivity.API_URL + "api/remove_item_wishlist.php?customerid=" + SingletonActivity.custidstr + "&productid=" + passed_id;
                        Log.e("wishlist api..", "wish list api.." + wishList_url);
                        Log.e("remove wishlist api..", "remove wish list api.." + remove_wishList_url);
                        //  v.setActivated(!v.isActivated());


                        if (status == 0) {
                            Toast.makeText(getApplicationContext(), "added..", Toast.LENGTH_SHORT).show();
                            addTowishList(wishList_url);
                            p_det_wishlist.setImageResource(R.drawable.wishlist);
                            status = 1;
                        } else {
                            Toast.makeText(getApplicationContext(), "removed....", Toast.LENGTH_SHORT).show();
                            removeFromwishList(remove_wishList_url);
                            p_det_wishlist.setImageResource(R.drawable.empty_wishlist);
                            status = 0;
                        }
                    }

                }


            });


            Gallery gallery = (Gallery) findViewById(R.id.gallery1);
            gallery.setAdapter(new ImageAdapter(getApplicationContext()));

            Picasso.with(getApplicationContext()).load(images_array.get(0))
                    .fit().into(im_pImage);
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Picasso.with(getApplicationContext()).load(images_array.get(position))
                            .fit().into(im_pImage);
                }
            });


            app_title.setText(p_name);
            tv_pName.setText(p_name);
            Log.e("special price..", "detail sp price..." + special_price);
            if (special_price.equals("0.00")) {
                tv_product_price_single_txt.setVisibility(View.VISIBLE);
                tv_product_price_single_txt.setText("Rs." + p_price);
                // tv_pPrice_strikeout.setText("Rs." + p_price);
                tv_product_price_single_txt.setTextAppearance(getApplicationContext(), R.style.product_price_txt);
                tv_pPrice_strikeout.setVisibility(View.INVISIBLE);

                tv_pPrice.setVisibility(View.INVISIBLE);


               /* LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
                llp.setMargins(50, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
                tv_pPrice_strikeout.setLayoutParams(llp);*/

            } else {
                tv_pPrice_strikeout.setPaintFlags(tv_pPrice_strikeout.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tv_pPrice_strikeout.setText("Rs." + p_price);
                tv_pPrice.setText("Rs." + special_price);
            }

            tv_pDescriptn.setText(p_description);

            availability = Integer.parseInt(availability_value);
            user_total_reviews_txt.setText(review_length + "\t" + "User Reviews");

            if (review_length == 0) {
                user_total_reviews_txt.setText("Be the first to write review");

            } else {
                user_total_reviews_txt.setText(review_length + "\t" + "User Reviews");

                LayerDrawable stars = (LayerDrawable) ratingBar_userReviews.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                // stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                rating_value = ((rating_cal / 100) * 5);
                Log.e("rating value..", "rating value.." + rating_value);
                final_rating = (float) Math.round(rating_value * 100) / 100;
                ratingBar_userReviews.setRating(final_rating);
                Log.e("final rating..", "final rating.." + final_rating);

            }





          /*  reviews_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_hide.setVisibility(View.VISIBLE);
                    gift_it.setVisibility(View.GONE);
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    table_review.setVisibility(View.VISIBLE);
                    divdr_review_pink.setVisibility(View.VISIBLE);
                    statc_howUrate.setVisibility(View.VISIBLE);
                    relative_ratebar.setVisibility(View.VISIBLE);
                    //rating_bar.setVisibility(View.VISIBLE);
                    userReview.setVisibility(View.VISIBLE);
                    submit_review.setVisibility(View.VISIBLE);

                }
            });
*/
            reviews_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (review_length == 0) {

                        show_hide.setVisibility(View.VISIBLE);
                        gift_it.setVisibility(View.GONE);
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        table_review.setVisibility(View.VISIBLE);
                        divdr_review_pink.setVisibility(View.VISIBLE);
                        statc_howUrate.setVisibility(View.VISIBLE);
                        relative_ratebar.setVisibility(View.VISIBLE);
                        //rating_bar.setVisibility(View.VISIBLE);
                        userReview.setVisibility(View.VISIBLE);
                        submit_review.setVisibility(View.VISIBLE);
                        user_review_name.setVisibility(View.VISIBLE);
                        review_title_edit.setVisibility(View.VISIBLE);
                        user_review_name.setText(firstnamestr);


                    } else {
                        Log.e("Already reviews ..", "already reviews are there..");
                        Intent intent = new Intent(getApplicationContext(), Reviews.class);
                        intent.putExtra("passed_id", passed_id);
                        intent.putExtra("image_url", p_img);
                        intent.putExtra("review_length", Integer.toString(review_length));
                        intent.putExtra("review_p_title", p_name);
                        startActivity(intent);


                    }
                }
            });
            if (status == 0) {
                p_det_wishlist.setImageResource(R.drawable.empty_wishlist);
            } else {
                p_det_wishlist.setImageResource(R.drawable.wishlist);
            }

            im_pImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProductDetailsImage.class);
                    intent.putStringArrayListExtra("images_array", images_array);
                    intent.putExtra("product_name", p_name);
                    startActivity(intent);
                }
            });
            if (availability >= 1) {
                stock_txt.setText("In Stock");
                stock_txt.setTextColor(Color.parseColor("#008000"));
            } else {
                stock_txt.setText("Out of Stock");
                stock_txt.setTextColor(Color.parseColor("#FF0000"));
            }
            if (flvr_title_array.size() == 0) {
                flvr_spinner.setVisibility(View.INVISIBLE);
                // flvr_outer.setVisibility(View.GONE);
            }
            if (wt_title_array.size() == 0) {
                wt_spinner.setVisibility(View.INVISIBLE);
                //  wt_outer.setVisibility(View.GONE);
            }
            if (flvr_title_array.size() == 0 && wt_title_array.size() == 0) {
                wt_outer.setVisibility(View.GONE);
            }
            if (color_array.size() == 0 || color_array.contains("null") || color_array.contains("false")) {
                clr_spinner.setVisibility(View.INVISIBLE);
            }
            if (size_array.size() == 0) {
                size_spinner.setVisibility(View.INVISIBLE);
            }
            if (color_array.size() == 0 && size_array.size() == 0) {
                size_outer.setVisibility(View.GONE);
            }

            if (wt_title_array != null) {
                ArrayAdapter<String> wt_Adapter = new ArrayAdapter<String>(ProductDetails.this, android.R.layout.simple_spinner_item, wt_title_array);
                wt_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                wt_spinner.setAdapter(wt_Adapter);

                wt_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("selected weight..", "selected weight.." + wt_title_array.get(position));
                        Log.e("weight price..", "weight price.." + wt_price_array.get(position));
                        weightstr = wt_title_array.get(position);
                        tv_pPrice.setText("Rs." + wt_price_array.get(position).replaceAll(".0000", ".00"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            if (flvr_title_array != null) {
                ArrayAdapter<String> flvr_Adapter = new ArrayAdapter<String>(ProductDetails.this, android.R.layout.simple_spinner_item, flvr_title_array);
                flvr_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                flvr_spinner.setAdapter(flvr_Adapter);

                flvr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("selected flavour..", "selected flavour.." + flvr_title_array.get(position).contains("option_title"));
                        Log.e("flavour price..", "flavour price.." + flvr_price_array.get(position));
                        flavourstr = flvr_title_array.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            }


            if (size_array != null) {
                ArrayAdapter<String> size_Adapter = new ArrayAdapter<String>(ProductDetails.this, android.R.layout.simple_spinner_item, size_array);
                size_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                size_spinner.setAdapter(size_Adapter);

                size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("selected size..", "selected size.." + size_array.get(position).contains("size"));

                        sizestr = size_array.get(position);
                        // Log.e("wt price..", "wt price.." + flvr_price_array.get(position));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            }


            if (color_array != null) {
                ArrayAdapter<String> color_Adapter = new ArrayAdapter<String>(ProductDetails.this, android.R.layout.simple_spinner_item, color_array);
                color_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clr_spinner.setAdapter(color_Adapter);

                clr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("selected color..", "selected color.." + color_array.get(position).contains("color"));
                        colstr = color_array.get(position);
                        // Log.e("wt price..", "wt price.." + flvr_price_array.get(position));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            }


            salesPkg_des.setText(sales_packages_value);
            Log.e("banaglore price.", "bangalore price.." + bangalore_price);



            if (bangalore_price.equals("0") || bangalore_price.equals("null")) {
                p_det_priceTxt_1.setVisibility(View.GONE);
                p_det_price_1.setVisibility(View.GONE);
                Log.e("if b price..","if b price.."+bangalore_price);

            } else {
                p_det_priceTxt_1.setVisibility(View.VISIBLE);
                p_det_price_1.setVisibility(View.VISIBLE);
                p_det_price_1.setText( bangalore_price.replaceAll(".0000", ".00"));
                Log.e("else b price..","else b price.."+bangalore_price);
            }
            if (mumbai_price.equals("null") || mumbai_price.equals("0")) {
                p_det_priceTxt_3.setVisibility(View.GONE);
                p_det_price_3.setVisibility(View.GONE);

            } else {
                p_det_priceTxt_3.setVisibility(View.VISIBLE);
                p_det_price_3.setVisibility(View.VISIBLE);
                p_det_price_3.setText(mumbai_price.replaceAll(".0000", ".00"));
            }
            if (delhi_price.equals("0") || delhi_price.equals("null")) {
                p_det_priceTxt_2.setVisibility(View.GONE);
                p_det_price_2.setVisibility(View.GONE);


            }

            else {
                p_det_priceTxt_2.setVisibility(View.VISIBLE);
                p_det_price_2.setVisibility(View.VISIBLE);
                p_det_price_2.setText(delhi_price.replaceAll(".0000", ".00"));
            }
            if (chennai_price.equals("0") || chennai_price.equals("null")) {
                p_det_priceTxt_3.setVisibility(View.GONE);
                p_det_price_3.setVisibility(View.GONE);


            } else {
                p_det_priceTxt_3.setVisibility(View.VISIBLE);
                p_det_price_3.setVisibility(View.VISIBLE);
                p_det_price_3.setText(chennai_price.replaceAll(".0000", ".00"));
            }
            if (bangalore_price.equals("null") && mumbai_price.equals("null") && delhi_price.equals("null")&& chennai_price.equals("null") || bangalore_price.equals("0") && mumbai_price.equals("0") && delhi_price.equals("0")&& chennai_price.equals("0")) {
                table_det_pg_price.setVisibility(View.GONE);
            }

            table_additonl.removeAllViews();
            for (int i = 0; i < attrib_array.size(); i++) {
                Log.e("attrib array size..", "attrib array size.." + attrib_array.size());
                TableRow tr = new TableRow(ProductDetails.this);
                TableRow.LayoutParams TRparam = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT);
                TRparam.setMargins(2, 2, 2, 2);

                tr.setLayoutParams(TRparam);

                TextView tv1 = new TextView(ProductDetails.this);
                tv1.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv1.setText(attrib_array.get(i).get("label"));

                tv1.setTypeface(null, Typeface.BOLD);
                tv1.setPadding(7, 5, 3, 5);
                tv1.setBackgroundColor(Color.WHITE);
                tv1.setTypeface(face);

                tr.addView(tv1);

                TextView tv2 = new TextView(ProductDetails.this);
                tv2.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv2.setText(attrib_array.get(i).get("value"));
                Log.e("values ...", "values ...." + "\t" + attrib_array.get(i).get("label") + "\t" + attrib_array.get(i).get("value"));
                // tv2.setTypeface(null, Typeface.BOLD);
                tv2.setBackgroundColor(Color.WHITE);
                tv2.setPadding(3, 5, 3, 5);
                tv2.setTypeface(face);
                tr.addView(tv2);
                table_additonl.addView(tr, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

            }


        }
    }

    private void submitReview(String url) {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Request", response.toString());
                        try {
                            String res = response.getString("true");
                            Log.e("response sucess..", "response sucess..");
                            String submit_message = response.getString("successMsg");
                            Log.e("submit msg..", "submit msg.." + submit_message);
                            if (res.equals("true")) {
                                Log.e("review sucess..", "review sucess..");
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


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;

        public ImageAdapter(Context c) {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 1);
            a.recycle();
        }

        // returns the number of images
        public int getCount() {
            return images_array.size();
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
            Picasso.with(getApplicationContext()).load(images_array.get(position))
                    .fit().into(imageView);
            imageView.setLayoutParams(new Gallery.LayoutParams(140, 140));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);
        return true;
    }


}
