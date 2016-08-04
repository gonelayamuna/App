package com.example.user.smgapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CartActivity extends NavigationDrawer implements TextWatcher {

    TableLayout tableclick, table_list;
    Button proceedtocheckout, clearallitemsbtn, updateallcartbtn, blurupdateallcartbtn, blurproceedtocheckoutbtn, applycodebtn;
    RelativeLayout cartrelative, viewbelow, tablerelative;
    TextView clickheretxt, app_title, totalcartsubtotaltxt, totalcartgrandtotaltxt;
    EditText tv_pincode, applycodeedittxt;
    TextView delvry_validatn_txt, pincode_validatn_txt, cartcounttxt;
    ProgressBar pBar;
    TextView discounttxt,discountvaluetxt,discounttotaltxt,discounttotalvaluetxt;
    View discountview;
    EditText datetxt, timetxt;
    Button  datebtn,timebtn;
    private int mYear, mMonth, mDay,mHour, mMinute;
    Dialog mDialog;
    TextView  dialogdeleteitemtxt;
    Button dialogokbtn,dialogcancelbtn;
    TextView pincodeval,dateval,timeval;




    AutoCompleteTextView delivryCity;
    ArrayList<HashMap<String, String>> getPinCodeandCityList;
    DBController controller = new DBController(this);
    int maxlen = 6;
    int autocompletelength = 2;
    ArrayList<String> citieslist = new ArrayList<String>();
    String autocompletetext,strapplycodeedittxt;
    public static TableLayout stk;
    public static RelativeLayout row;
    public int count = 1;


    public static final String MyPREFERENCES = "MyPrefs";
    static SharedPreferences prefs;


    ArrayList<String> idcartproductdetails = new ArrayList<String>();
    ArrayList<String> namecartproductdetails = new ArrayList<String>();
    ArrayList<String> imgcartproductdetails = new ArrayList<String>();
    ArrayList<String> pricecartproductdetails = new ArrayList<String>();
    ArrayList<String> buyatcartproductdetails = new ArrayList<String>();
    ArrayList<String> bookdesccartproductdetails = new ArrayList<String>();
    ArrayList<String> imgarraycartproductdetails = new ArrayList<String>();
    ArrayList<String> quantitydetails = new ArrayList<String>();
    ArrayList<String> valuesquantity = new ArrayList<String>();
    ArrayList<String> allsinglecartprices = new ArrayList<String>();
    ArrayList<Float> totalvalueofallcart = new ArrayList<Float>();

    ArrayList<String>  sizedetails = new ArrayList<String>();
    ArrayList<String>  colordetails = new ArrayList<String>();
    ArrayList<String>  flavourdetails = new ArrayList<String>();
    ArrayList<String>  weightdetails = new ArrayList<String>();

    ArrayList<String> qtyvaluesbefrupdatecart = new ArrayList<String>();


    static String totalbuystr;

    static int totalcount;
    // static int  quantityintval;
    static float quantityintval;
    float subtotalvalfloatnoneditqty,totaldiscountamountfloat;

    String pincodestr;
    String cityfromdb;
    String subtotalvalfloattostr,totaldiscountamtfloattostr;
    String datestr, timestr;



    String updatecart_url, productidstr,sizestr,colorstr,flavourstr,weightstr;
    public static String quantitystr;
    float subtotalvalfloat, pricefloatval, pricevalmultipli, singlecarttotalfloat;
    String subtotalvalstr, pricevalstr;
    ProgressDialog pdia;
    float add;

    float editedsum;
    TextView textView;

    float finaltotal, cartsubtotalvalstrtofloat, editedfinaltotal;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.cart_activity_main);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.cart_activity_main, null, false);
        drawer.addView(contentView, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        idcartproductdetails.clear();
        namecartproductdetails.clear();
        imgcartproductdetails.clear();
        pricecartproductdetails.clear();
        buyatcartproductdetails.clear();
        bookdesccartproductdetails.clear();
        imgarraycartproductdetails.clear();
        citieslist.clear();
        quantitydetails.clear();
        sizedetails.clear();
        colordetails.clear();
        flavourdetails.clear();
        weightdetails.clear();


        app_title = (TextView) findViewById(R.id.app_title);
        app_title.setText("Cart");

        cartcounttxt = (TextView) findViewById(R.id.cartnumber);
        delvry_validatn_txt = (TextView) findViewById(R.id.delvry_validatn_txt);
        pincode_validatn_txt = (TextView) findViewById(R.id.pincode_validtn_txt);
        pBar = (ProgressBar) findViewById(R.id.progress_bar_custom_cart);
        applycodebtn = (Button) findViewById(R.id.applybutton);
        applycodeedittxt = (EditText)findViewById(R.id.applycodeedittext);


        totalcartsubtotaltxt = (TextView) findViewById(R.id.subtotalvaluetext);
        totalcartgrandtotaltxt = (TextView) findViewById(R.id.grandtotalvaluetext);

        updateallcartbtn = (Button) findViewById(R.id.updatecartbtn);
        blurupdateallcartbtn = (Button) findViewById(R.id.blurupdatebtn);
        blurproceedtocheckoutbtn = (Button) findViewById(R.id.blurproceedtocheckoutbtn);

        pincodeval = (TextView) findViewById(R.id.pincodevaluetxt);
        dateval = (TextView) findViewById(R.id.datecartvaluetxt);
        timeval = (TextView) findViewById(R.id.timecartvaluetxt);
       /* datetxt = (EditText) findViewById(R.id.in_date);
        datebtn = (Button) findViewById(R.id.btn_date);
        timebtn = (Button) findViewById(R.id.btn_time);
        timetxt = (EditText) findViewById(R.id.in_time);*/


        discounttxt = (TextView) findViewById(R.id.discounttext);
        discountvaluetxt = (TextView) findViewById(R.id.discountvaluetext);
        discounttotaltxt = (TextView) findViewById(R.id.discounttotaltext);
        discounttotalvaluetxt = (TextView) findViewById(R.id.discounttotalvaluetext);
        discountview = (View) findViewById(R.id.discountviewline);

        discounttxt.setVisibility(View.INVISIBLE);
        discountvaluetxt.setVisibility(View.INVISIBLE);
        discounttotaltxt.setVisibility(View.INVISIBLE);
        discounttotalvaluetxt.setVisibility(View.INVISIBLE);

       /* clearallbtn=(Button)findViewById(R.id.clearallitembutton);

        clearallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAllVolleyApi();

            }
        });*/


        System.out.println("PRODUCT ID from singleton IN CartActivity IS----" + SingletonActivity.productid);
        System.out.println("CUST ID from singleton IN CartActivity IS----" + SingletonActivity.custidstr);
        System.out.println("CUST NAME IN CartActivity IS----" + SingletonActivity.custnamestr);
        System.out.println("CUST EMAIL IN CartActivity IS----" + SingletonActivity.custemailstr);

        System.out.println("DATE IN CartActivity IS----" + SingletonActivity.date);
        System.out.println("TIME IN CartActivity IS----" + SingletonActivity.time);
        System.out.println("PINCODE IN CartActivity IS----" + SingletonActivity.pincode);

        pincodeval.setText(SingletonActivity.pincode);
        dateval.setText(SingletonActivity.date);
        timeval.setText(SingletonActivity.time);

        //updatecart_url="http://localhost:8080/smg_dev/api/update_cart.php?product_id=591&product_qty=10&customer_id=89";

        updatecart_url = SingletonActivity.API_URL + "api/update_cart.php?product_id=" + SingletonActivity.productid + "&product_qty=" + 10 + "&customer_id=" + SingletonActivity.custidstr;


      /*  citieslist = SingletonActivity.citiesarray;
        delivryCity = (AutoCompleteTextView) findViewById(R.id.delivryCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, citieslist);
        delivryCity.setAdapter(adapter);

        delivryCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                autocompletetext = (String) parent.getItemAtPosition(position);
                //      delivryCity.setEnabled(false);
                //TODO Do something with the selected text
            }
        });


        tv_pincode = (EditText) findViewById(R.id.tv_det_pincode);
        tv_pincode.setEnabled(false);

        delivryCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (delivryCity.getText().toString().length() >= autocompletelength) {

                    tv_pincode.setEnabled(true);

                }
                return false;
            }
        });

        tv_pincode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (tv_pincode.getText().toString().length() >= maxlen) {
                    pincodestr = tv_pincode.getText().toString();
                    cityfromdb = controller.getSingleCityEntry(pincodestr);

                    if (autocompletetext.equals(cityfromdb)) {
                        Toast.makeText(CartActivity.this, "this city is serviceable", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CartActivity.this, "this city is not serviceable", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

*/
        applycodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applycodeedittxt.getText().toString().length()==0){
                    applycodeedittxt.setError("Enter coupon code");
                }
                else if(applycodeedittxt.getText().toString().length()>0){
                    applycodeedittxt.setError(null);

                    strapplycodeedittxt = applycodeedittxt.getText().toString();
                    System.out.println("edittext value is" + strapplycodeedittxt);


                    if(SingletonActivity.checkConnectivity(CartActivity.this)){

                        applycodesapi(strapplycodeedittxt);

                    }
                    else{
                        Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent);
                    }


                }
            }
        });




      /*  datebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (v ==   datebtn) {

                    // Process to get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    // Launch Date Picker Dialog
                    DatePickerDialog dialog = new DatePickerDialog(CartActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Display Selected date in textbox
									datestr = dayOfMonth + "-"
											+ (monthOfYear + 1) + "-" + year;
                                    *//*datestr = year + "-"
                                            + (monthOfYear + 1) + "-" + dayOfMonth;*//*
                                    System.out.println("Datestr is--" +datestr);

                                    datetxt.setText(datestr);


                                }

                            }, mDay, mMonth, mYear);

                    dialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    dialog.show();
                }

            }
        });


        timebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (v == timebtn) {

                    // Process to get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog tpd = new TimePickerDialog(CartActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    // Display Selected time in textbox
                                    timestr = hourOfDay + ":" + minute;
                                    System.out.println("TIME IS--" + timestr);
                                    timetxt.setText(timestr);
                                }
                            }, mHour, mMinute, false);
                    tpd.show();
                }

            }
        });

*/



       /* prefs = CartActivity.this.getSharedPreferences(MyPREFERENCES, 0);
        //1)key=Name and 2)key=Emailid 3)key=Phonenos
        custidstr = prefs.getString("CustId", "");
        custnamestr = prefs.getString("CustName", "");
        custemailstr = prefs.getString("CustEmail", "");

        System.out.println("CUST ID IN CartActivity IS----" + custidstr);
        System.out.println("CUST NAME IN CartActivity IS----" + custnamestr);
        System.out.println("CUST EMAIL IN CartActivity IS----" + custemailstr);*/


        tablerelative = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        clickheretxt = (TextView) findViewById(R.id.heretext);
        table_list = (TableLayout) findViewById(R.id.table_main);

        cartrelative = (RelativeLayout) findViewById(R.id.cartrelativehead);
        cartrelative.setVisibility(View.INVISIBLE);
        tableclick = (TableLayout) findViewById(R.id.tablehere);
        tableclick.setVisibility(View.INVISIBLE);
        //   updatecartbtn = (Button)findViewById(R.id.updatecartbutton);
        clearallitemsbtn = (Button) findViewById(R.id.clearallitembutton);
        viewbelow = (RelativeLayout) findViewById(R.id.belowview);
        viewbelow.setVisibility(View.INVISIBLE);


        clearallitemsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartrelative.removeAllViews();

                if(SingletonActivity.checkConnectivity(CartActivity.this)){

                    DeleteAllVolleyApi();

                }
                else{
                    Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                    startActivity(intent);
                }


            }
        });

        clickheretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this, HomePage.class);
                startActivity(intent);

            }
        });

        proceedtocheckout = (Button) findViewById(R.id.proceedtocheckoutbutton);
        proceedtocheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valuesquantity.clear();

                for (int i = 0; i < stk.getChildCount(); i++) {

                    EditText et = (EditText) stk.getChildAt(i).findViewById(R.id.editText);
                    if (et != null) {
                        valuesquantity.add(String.valueOf(et.getText()));
                    }
                }
                System.out.println("EDITTEXT VALUES on clicking PROCEED TO CHECKOUT BUTTON" + valuesquantity);
                SingletonActivity.singlecartqty = valuesquantity;

                allsinglecartprices.clear();

                for (int i = 0; i < stk.getChildCount(); i++) {

                    TextView subtotalsinglecart = (TextView) stk.getChildAt(i).findViewById(R.id.subtotalval);
                    //EditText et = (EditText) stk.getChildAt(i).findViewById(R.id.editText);
                    if (subtotalsinglecart != null) {
                        allsinglecartprices.add(String.valueOf(subtotalsinglecart.getText()));
                    }
                }
                System.out.println("single cart price VALUES on PROCEED TO CHECKOUT " + allsinglecartprices);
                SingletonActivity.singlecartprice = allsinglecartprices;

                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);

               /* if (delivryCity.getText().toString().isEmpty() || delivryCity.getText().toString().equals("")) {
                    // Toast.makeText(ProductDetails.this,"Choose delivery city..!",Toast.LENGTH_SHORT).show();
                    delivryCity.requestFocus();
                    delvry_validatn_txt.setVisibility(View.VISIBLE);
                    delvry_validatn_txt.setText("Choose delivery city..!");


                } else if (tv_pincode.getText().toString().equals("") && (!delivryCity.getText().toString().equals(""))) {
                    tv_pincode.setEnabled(true);
                    pincode_validatn_txt.setVisibility(View.VISIBLE);
                    delvry_validatn_txt.setText("");

                    pincode_validatn_txt.setText("Enter proper pincode based on city");
                } else {

.
                    if (autocompletetext.equals(cityfromdb)) {
                        //Toast.makeText(CartActivity.this, "this city is serviceable", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(CartActivity.this, CheckoutActivity.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(CartActivity.this, "wrong pincode", Toast.LENGTH_SHORT).show();
                    }

                }*/
            }
        });


        if(SingletonActivity.checkConnectivity(CartActivity.this)){

            TotalCartVolleyApi();

        }
        else{
            Intent intent=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent);
        }





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

  /* @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent intent = new Intent(CartActivity.this,ProductDetails.class);
       startActivity(intent);
       *//* finish();
        startActivity(getIntent());*//*

    }
*/
    private void applycodesapi(String applycodes) {

        pdia = new ProgressDialog(CartActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL + "api/discount_code.php?customer_id=" + SingletonActivity.custidstr + "&discount_code=" +applycodes, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("RESPONSE for APPLY CODES in Cart Activity" + response);
                        //Log.d("Request", response.toString());
                        pdia.dismiss();
                        try {

                            String isSuccessStr = response.getString("isSuccess");
                            System.out.println("IS SUCCESS STRING IN APPLY CODES API IS" + isSuccessStr);
                            String successMsgStr = response.getString("successMsg");
                            System.out.println("SUCCESS MESSSAGE IS IN APPLY CODES API IS" + successMsgStr);

                            if(isSuccessStr.equalsIgnoreCase("false")&&(successMsgStr.equalsIgnoreCase("Coupen Code Not Valid!"))){
                                discounttxt.setVisibility(View.INVISIBLE);
                                discountvaluetxt.setVisibility(View.INVISIBLE);
                                discounttotaltxt.setVisibility(View.INVISIBLE);
                                discountview.setVisibility(View.INVISIBLE);
                                discounttotalvaluetxt.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Coupon code not valid!",Toast.LENGTH_SHORT).show();
                            }

                            String discountamtStr = response.getString("Discount Amount ");
                            System.out.println("DISCOUNT AMOUNT IS IN APPLY CODES API IS" + discountamtStr);
                            discountvaluetxt.setText(discountamtStr);

                            if (isSuccessStr.equalsIgnoreCase("true")&&(successMsgStr.equalsIgnoreCase("Coupen applied successfully!"))) {


                                float discountamtfloat = Float.parseFloat(discountamtStr);
                                System.out.println("DISCOUNT AMOUNT IN FLOAT" + discountamtfloat);

                                System.out.println("TOTAL AMOUNT IS IN APPLY CODES API IS" + SingletonActivity.totalamount);

                                float totalamtfloat = Float.parseFloat(SingletonActivity.totalamount);
                                System.out.println("total amount float value from singleton" + totalamtfloat);

                                totaldiscountamountfloat = totalamtfloat + discountamtfloat;
                                System.out.println("Total discount amount float is " + totaldiscountamountfloat);

                                totaldiscountamtfloattostr = Float.toString(totaldiscountamountfloat);
                                System.out.println("DISCOUNT TOTAL VALUE IN STRING" + totaldiscountamtfloattostr);
                                discounttotalvaluetxt.setText(totaldiscountamtfloattostr);

                                SingletonActivity.totalamount = totaldiscountamtfloattostr;

                               /* subtotalvalfloattostr = Float.toString(subtotalvalfloat);
                                System.out.println("SUBTOTAL VALUE float to string in onclick" + subtotalvalfloattostr);
                                subtotalval.setText(subtotalvalfloattostr);*/

                                // if(!(discountamtStr.equalsIgnoreCase("0"))){
                                discounttxt.setVisibility(View.VISIBLE);
                                discountvaluetxt.setVisibility(View.VISIBLE);
                                discounttotaltxt.setVisibility(View.VISIBLE);
                                discountview.setVisibility(View.VISIBLE);
                                discounttotalvaluetxt.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Successfully coupon is applied!", Toast.LENGTH_SHORT).show();
                                //  }
                            }



                            if (isSuccessStr.equalsIgnoreCase("false")&&(successMsgStr.equalsIgnoreCase("Coupen Already Applied"))) {



                                float discountamtfloat = Float.parseFloat(discountamtStr);
                                System.out.println("DISCOUNT AMOUNT IN FLOAT" + discountamtfloat);

                                System.out.println("TOTAL AMOUNT IS IN APPLY CODES API IS" + SingletonActivity.totalamount);

                                float totalamtfloat = Float.parseFloat(SingletonActivity.totalamount);
                                System.out.println("total amount float value from singleton" + totalamtfloat);

                                totaldiscountamountfloat = totalamtfloat + discountamtfloat;
                                System.out.println("Total discount amount float is " + totaldiscountamountfloat);

                                totaldiscountamtfloattostr = Float.toString(totaldiscountamountfloat);
                                System.out.println("DISCOUNT TOTAL VALUE IN STRING" + totaldiscountamtfloattostr);
                                discounttotalvaluetxt.setText(totaldiscountamtfloattostr);

                                SingletonActivity.totalamount = totaldiscountamtfloattostr;

                               /* subtotalvalfloattostr = Float.toString(subtotalvalfloat);
                                System.out.println("SUBTOTAL VALUE float to string in onclick" + subtotalvalfloattostr);
                                subtotalval.setText(subtotalvalfloattostr);*/

                                // if(!(discountamtStr.equalsIgnoreCase("0"))){
                                discounttxt.setVisibility(View.VISIBLE);
                                discountvaluetxt.setVisibility(View.VISIBLE);
                                discounttotaltxt.setVisibility(View.VISIBLE);
                                discountview.setVisibility(View.VISIBLE);
                                discounttotalvaluetxt.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Coupon was already applied!", Toast.LENGTH_SHORT).show();
                                //  }
                            }



                           /* if (isSuccessStr.equalsIgnoreCase("true")) {

                                String discountamtStr = response.getString("Discount Amount");
                                System.out.println("DISCOUNT AMOUNT IS IN APPLY CODES API IS" + discountamtStr);
                                discountvaluetxt.setText(discountamtStr);


                                float discountamtfloat = Float.parseFloat(discountamtStr);
                                System.out.println("DISCOUNT AMOUNT IN FLOAT" + discountamtfloat);

                                System.out.println("DISCOUNT AMOUNT IS IN APPLY CODES API IS" + SingletonActivity.totalamount);

                                float totalamtfloat = Float.parseFloat(SingletonActivity.totalamount);
                                System.out.println("total amount float value from singleton" + totalamtfloat);

                                totaldiscountamountfloat = totalamtfloat + discountamtfloat;
                                System.out.println("Total discount amount float is " + totaldiscountamountfloat);

                                totaldiscountamtfloattostr = Float.toString(totaldiscountamountfloat);
                                System.out.println("DISCOUNT TOTAL VALUE IN STRING" +  totaldiscountamtfloattostr);
                                discounttotalvaluetxt.setText( totaldiscountamtfloattostr);

                                *//*subtotalvalfloattostr = Float.toString(subtotalvalfloat);
                                System.out.println("SUBTOTAL VALUE float to string in onclick" + subtotalvalfloattostr);
                                subtotalval.setText(subtotalvalfloattostr);*//*



                                if(!(discountamtStr.equalsIgnoreCase("0"))){
                                    discounttxt.setVisibility(View.VISIBLE);
                                    discountvaluetxt.setVisibility(View.VISIBLE);
                                    discounttotaltxt.setVisibility(View.VISIBLE);
                                   discountview.setVisibility(View.VISIBLE);
                                    discounttotalvaluetxt.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(),"Successfully coupon is applied!",Toast.LENGTH_SHORT).show();
                                }



                                if(discountamtStr.equalsIgnoreCase("0")){
                                    Toast.makeText(getApplicationContext(),"Coupon is already applied!",Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (isSuccessStr.equalsIgnoreCase("false")&&(successMsgStr.equalsIgnoreCase("Coupen Code Not Valid!"))){
                                Toast.makeText(getApplicationContext(),"Coupon code is wrong!",Toast.LENGTH_SHORT).show();
                            }
*/
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception" + e);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdia.dismiss();
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
                //  params.put("phone", phone);

                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void editmultiplecartquantity(String quantity, String prodid) {


        pdia = new ProgressDialog(CartActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();

        // System.out.println("url for edit multiple cart quantity IS" + SingletonActivity.API_URL + "api/update_cart.php?product_id=" + prodid + "&product_qty=" + quantity + "&customer_id=" + SingletonActivity.custidstr);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL + "api/update_total_cart.php?customer_id=" + SingletonActivity.custidstr + "&product_id=" + prodid + "&qty=" + quantity, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("RESPONSE IN CARTACTIVITY for EDIT multiple CART QUANTITY IS" + response);
                        //Log.d("Request", response.toString());

                        try {

                            //   JSONObject jsonObject = new JSONObject(response);
                            String isSuccessStr = response.getString("isSuccess");
                            // String messageStr = jsonObject.getString("message");
                            System.out.println("IS SUCCESS STRING IN EDIT MULTIPLE CART  IS" + isSuccessStr);
                            //System.out.println("MESSAGE STRING IN DELETE CART VOLLEY ACTIVITY IS" + messageStr);

                            if (isSuccessStr.equalsIgnoreCase("true")) {

                                pdia.dismiss();
                                Toast.makeText(getApplicationContext(), "Successfully your cart items is updated", Toast.LENGTH_SHORT).show();
                                proceedtocheckout.setVisibility(View.VISIBLE);
                                blurupdateallcartbtn.setVisibility(View.VISIBLE);
                                updateallcartbtn.setVisibility(View.GONE);
                                blurproceedtocheckoutbtn.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception" + e);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdia.dismiss();
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
                 /* params.put("phone", phone);
                params.put("name", username);
                params.put("pwd",password);
                params.put("email", email);*/
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }


   /* private void editsinglecartquantity(String quantity,String prodid) {


      pdia = new ProgressDialog(CartActivity.this);
      pdia.setMessage("Please Wait...");
      pdia.setCanceledOnTouchOutside(false);
      pdia.show();

        System.out.println("url for edit single csrt updating quantity IS" + SingletonActivity.API_URL + "api/update_cart.php?product_id=" + prodid + "&product_qty=" + quantity + "&customer_id=" + SingletonActivity.custidstr);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL+"api/update_cart.php?product_id="+prodid+"&product_qty="+quantity+"&customer_id="+SingletonActivity.custidstr, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("RESPONSE IN CARTACTIVITY for EDIT SINGLE CART QUANTITY IS" + response);
                        //Log.d("Request", response.toString());

                        try {

                            //   JSONObject jsonObject = new JSONObject(response);
                            String isSuccessStr = response.getString("isSuccess");
                            // String messageStr = jsonObject.getString("message");
                            System.out.println("IS SUCCESS STRING IN EDIT SINGLE CART  IS" + isSuccessStr);
                            //System.out.println("MESSAGE STRING IN DELETE CART VOLLEY ACTIVITY IS" + messageStr);

                            if(isSuccessStr.equalsIgnoreCase("true")){

                                 pdia.dismiss();
                                Toast.makeText(getApplicationContext(),"Successfully your cart is updated",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception" + e);
                        }
                    }
                },
                new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pdia.dismiss();
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
         *//*   params.put("phone", phone);
            params.put("name", username);
            params.put("pwd",password);
            params.put("email", email);*//*
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }*/

    private void deletesinglecartitem(String prodid) {

        pdia = new ProgressDialog(CartActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();

        System.out.println("url for delete single csrt IS" + SingletonActivity.API_URL + "api/delete_single_item_cart.php?product_id=" + prodid + "&customer_id=" + SingletonActivity.custidstr);
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL + "api/delete_single_item_cart.php?product_id=" + prodid + "&customer_id=" + SingletonActivity.custidstr, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("RESPONSE for DELETE SINGLE CART IS in Cart Activity" + response);
                        //Log.d("Request", response.toString());
                        pdia.dismiss();
                        try {

                            String isSuccessStr = response.getString("isSuccess");
                            System.out.println("IS SUCCESS STRING IN DELETE SINGLE CART  IS" + isSuccessStr);

                            if (isSuccessStr.equalsIgnoreCase("true")) {


                                stk.removeView(row);
                                finish();
                                startActivity(getIntent());

                                Toast.makeText(getApplicationContext(), "Successfully your one cart item is deleted", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception" + e);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdia.dismiss();
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
                //  params.put("phone", phone);

                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void DeleteAllVolleyApi() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SingletonActivity.API_URL + "api/delete_cart_items.php?customerId=" + SingletonActivity.custidstr,

                //    StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://52.77.145.35/api/totalcart.php?customer_id=112",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pdia.dismiss();

                        System.out.println("RESPONSE IN DELETE CART VOLLEY ACTIVITY IS" + response);

                        try {


                            JSONObject jsonObject = new JSONObject(response);

                            String isSuccessStr = jsonObject.getString("isSuccess");

                            String messageStr = jsonObject.getString("message");

                            System.out.println("IS SUCCESS STRING IN DELETE CART VOLLEY ACTIVITY IS" + isSuccessStr);
                            System.out.println("MESSAGE STRING IN DELETE CART VOLLEY ACTIVITY IS" + messageStr);

                            if (isSuccessStr.equalsIgnoreCase("true")) {
                                cartrelative.removeAllViews();
                                viewbelow.setVisibility(View.VISIBLE);
                                tableclick.setVisibility(View.VISIBLE);
                                cartcounttxt.setText("0");
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception" + e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        //  pdia.dismiss();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("customer_id", SingletonActivity.custidstr);

                return params;
            }

        };

        GlobalClass.getInstance().addToRequestQueue(stringRequest);
        //RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        // requestQueue.add(stringRequest);
    }


    private void TotalCartVolleyApi() {

      /*pdia = new ProgressDialog(SignupActivityVolley.this);
      pdia.setMessage("Please Wait...");
      pdia.setCanceledOnTouchOutside(false);
      pdia.show();*/
        pBar.setVisibility(View.VISIBLE);

        //  SingletonActivity.custidstr="112";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SingletonActivity.API_URL + "api/totalcart.php?customer_id=" + SingletonActivity.custidstr,

                //    StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://52.77.145.35/api/totalcart.php?customer_id=112",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pdia.dismiss();
                        pBar.setVisibility(View.INVISIBLE);

                        System.out.println("RESPONSE IN TOTAL CART VOLLEY ACTIVITY IS" + response);

                        try {
                            JSONObject mainObject = new JSONObject(response);

                            System.out.println("JSON OBJECT RESPONSE IS---->" + mainObject);

                            String issuccessstr = mainObject.getString("isSuccess");
                            totalbuystr = mainObject.getString("total_buy");

                            System.out.println("TOTAL BUY IS " + totalbuystr);

                            SingletonActivity.cartcount = totalbuystr;

                            if (totalbuystr.equalsIgnoreCase("0")) {
                                cartrelative.removeAllViews();
                                viewbelow.setVisibility(View.VISIBLE);
                                tableclick.setVisibility(View.VISIBLE);
                                cartcounttxt.setText("0");

                            }

                            SharedPreferences.Editor editor = getSharedPreferences(
                                    MyPREFERENCES, MODE_PRIVATE).edit();
                            editor.putString("cartcount", SingletonActivity.cartcount);

                            editor.commit();

                            cartcounttxt.setText(SingletonActivity.cartcount);

                            JSONArray resultjsonArray = mainObject.getJSONArray("result");

                            System.out.println("JSON ARRAY IN TOTAL CART VOLLEY ACTIVITY IS---" + resultjsonArray);

                            for (int i = 0; i < resultjsonArray.length(); i++) {
                                JSONObject resultjsonobject = resultjsonArray.getJSONObject(i);

                                System.out.println("JSON OBJECT IN TOTAL CART VOLLEY ACTIVITY IS---" + resultjsonobject);

                                String idstr = resultjsonobject.getString("id");
                                String skustr = resultjsonobject.getString("sku");
                                String namestr = resultjsonobject.getString("name");
                                String imgurlstr = resultjsonobject.getString("img_url");
                               // String pricestr = resultjsonobject.getString("price");
                                String buyatstr = resultjsonobject.getString("buy_at");
                                String bookdescstr = resultjsonobject.getString("book_description");
                                String imgarraystr = resultjsonobject.getString("image_array");
                                String qtystr = resultjsonobject.getString("quantity");
                              //  String sizestr = resultjsonobject.getString("size");
                             //   String colorstr = resultjsonobject.getString("color");
                             //   String flavourstr = resultjsonobject.getString("flavour");
                                String weightstr = resultjsonobject.getString("weight");

                              //  String availabilitystr = resultjsonobject.getString("availability");


                                System.out.println("ID STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + idstr);
                                System.out.println("SKU STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + skustr);
                                System.out.println("NAME STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + namestr);
                                System.out.println("IMG URL STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + imgurlstr);
                             //   System.out.println("PRICE STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + pricestr);
                                System.out.println("BUY AT STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + buyatstr);
                                System.out.println("BOOK DESCRIPTION STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + bookdescstr);
                                System.out.println("IMG ARRAY STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + imgarraystr);
                                System.out.println("QUANTITY STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + qtystr);
                              /*  System.out.println("SIZE STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + sizestr);
                                System.out.println("COLOR STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + colorstr);
                                System.out.println("FLAVOUR STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + flavourstr);*/
                                System.out.println("WEIGHT STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + weightstr);

                                idcartproductdetails.add(resultjsonobject.getString("id"));
                                namecartproductdetails.add(resultjsonobject.getString("name"));
                                imgcartproductdetails.add(resultjsonobject.getString("img_url"));
                               // pricecartproductdetails.add(resultjsonobject.getString("price"));
                                buyatcartproductdetails.add(resultjsonobject.getString("buy_at"));
                                bookdesccartproductdetails.add(resultjsonobject.getString("book_description"));
                                imgarraycartproductdetails.add(resultjsonobject.getString("image_array"));
                                quantitydetails.add(resultjsonobject.getString("quantity"));
                                sizedetails.add(resultjsonobject.getString("size"));
                                colordetails.add(resultjsonobject.getString("color"));
                                flavourdetails.add(resultjsonobject.getString("flavour"));
                                weightdetails.add(resultjsonobject.getString("weight"));



                            }

                            SingletonActivity.listofimgs=  imgcartproductdetails;

                            init();

                            System.out.println("IS SUCCESS STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + issuccessstr);
                            System.out.println("TOTAL BUY STRING IN TOTAL CART VOLLEY ACTIVITY IS---" + totalbuystr);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception-------" + e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        pBar.setVisibility(View.INVISIBLE);
                        //  pdia.dismiss();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("customer_id", SingletonActivity.custidstr);


                return params;
            }

        };

        GlobalClass.getInstance().addToRequestQueue(stringRequest);

     /*   RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        requestQueue.add(stringRequest);*/
    }


    public void init() {

        for (int j = 0; j < namecartproductdetails.size(); j++) {

            totalcount = Integer.parseInt(totalbuystr);

            System.out.println("TOTAL COUNT DETAILS---" + totalcount);

            System.out.println("NAME CART PRODUCT DETAILS---" + namecartproductdetails.size());


            stk = (TableLayout) findViewById(R.id.table_main);
            // TableRow tbrow = new TableRow(this);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            final RelativeLayout row = (RelativeLayout) inflater.inflate(R.layout.cart_list_row, null);

            final TextView priceval = (TextView) row.findViewById(R.id.priceval);
            final TextView subtotalval = (TextView) row.findViewById(R.id.subtotalval);
            final TextView prodid = (TextView) row.findViewById(R.id.pinnumbertext);
            // ImageView cartimg = (ImageView) row.findViewById(R.id.imageView);
            ImageView closeimg = (ImageView) row.findViewById(R.id.deleterow);
            //Button updatecartbtninrow = (Button) row.findViewById(R.id.updatecartbutton);
            final ImageView plusimg = (ImageView) row.findViewById(R.id.plusimgview);
            final ImageView minusimg = (ImageView) row.findViewById(R.id.minusimgview);
            TextView textView = (TextView) row.findViewById(R.id.imagenametext);
            ImageView itemimg = (ImageView) row.findViewById(R.id.itemimg);
            final EditText quantityedittext = (EditText) row.findViewById(R.id.editText);

            TextView sizetxt = (TextView) row.findViewById(R.id.size);
            TextView colortxt = (TextView) row.findViewById(R.id.color);
            TextView flavourtxt = (TextView) row.findViewById(R.id.flavour);
            TextView weighttxt = (TextView) row.findViewById(R.id.weight);

            Picasso.with(CartActivity.this).load(SingletonActivity.listofimgs.get(j)).placeholder(R.drawable.loading)
                    .fit().into(itemimg);


          /* quantityedittext.addTextChangedListener(new TextWatcher(){
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    if (quantityedittext.getText().toString().matches("^0") )
                    {
                        // Not allowed
                        Toast.makeText(CartActivity.this, "not allowed", Toast.LENGTH_SHORT).show();
                        quantityedittext.setText("");
                    }
                }
                @Override
                public void afterTextChanged(Editable arg0) { }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            });*/

           sizestr = sizedetails.get(j);
            sizetxt.setText(sizedetails.get(j));

            colorstr = colordetails.get(j);
            colortxt.setText(colordetails.get(j));

            flavourstr = flavourdetails.get(j);
            flavourtxt.setText(flavourdetails.get(j));

            weightstr = weightdetails.get(j);
            weighttxt.setText(weightdetails.get(j));

            productidstr = idcartproductdetails.get(j);
            prodid.setText(idcartproductdetails.get(j));

            pricevalstr = buyatcartproductdetails.get(j);
            priceval.setText(buyatcartproductdetails.get(j));

           /* if (buyatcartproductdetails.get(j).equalsIgnoreCase("0.00")) {
                pricevalstr = pricecartproductdetails.get(j);
                priceval.setText(pricecartproductdetails.get(j));
            }*/

            System.out.println("array quantity--" + quantitydetails.get(j));
            quantityedittext.setText(quantitydetails.get(j));
            quantityintval = Float.parseFloat(quantityedittext.getText().toString());
            System.out.println("EDITTEXT QUANTITY IS" + quantityintval);


           /* quantityintval = Integer.parseInt(quantityedittext.getText().toString());
            System.out.println("EDITTEXT QUANTITY IS" + quantityintval);*/

            if (quantityintval == 1) {
                minusimg.setEnabled(false);

            }

            plusimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateallcartbtn.setVisibility(View.VISIBLE);
                    proceedtocheckout.setVisibility(View.GONE);
                    blurupdateallcartbtn.setVisibility(View.GONE);
                    blurproceedtocheckoutbtn.setVisibility(View.VISIBLE);

                    count = Integer.parseInt(quantityedittext.getText().toString()) + 1;
                    System.out.println("increment count value of plus img " + count);
                    quantityedittext.setText(String.valueOf(count));
                    //   quantityedittext.setText(count);
                    minusimg.setEnabled(true);
                    /*pluscountvalues.clear();
                    pluscountvalues.add(count);
                    System.out.println("ARRAYLIST PLUS COUNT VALUES IS---" + pluscountvalues);

                    lastplusValue = pluscountvalues.get(pluscountvalues.size()-1);
                    System.out.println("ARRAYLIST plus last value is--" + lastplusValue);*/

                }
            });


            minusimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateallcartbtn.setVisibility(View.VISIBLE);
                    proceedtocheckout.setVisibility(View.GONE);
                    blurupdateallcartbtn.setVisibility(View.GONE);
                    blurproceedtocheckoutbtn.setVisibility(View.VISIBLE);

                    count = Integer.parseInt(quantityedittext.getText().toString()) - 1;
                    System.out.println("decrement count value of plus img" + count);

                    if (count > 1) {
                        minusimg.setEnabled(true);
                    } else {
                        minusimg.setEnabled(false);
                    }
                    quantityedittext.setText(String.valueOf(count));
                   /* minuscountvalues.clear();
                    minuscountvalues.add(count);
                    System.out.println("ARRAYLIST MINUS COUNT VALUES IS---" + minuscountvalues);
                    lastminusValue =  minuscountvalues.get( minuscountvalues.size()-1);
                    System.out.println("ARRAYLIST plus last value is--" + lastminusValue);*/
                }
            });


            String pricewithoutcommastr = priceval.getText().toString().replace(",", "");

            pricefloatval = Float.parseFloat(pricewithoutcommastr);
            System.out.println("PRICE FLOAT VALUE IS" + pricefloatval);

            pricevalmultipli = quantityintval * pricefloatval;
            System.out.println("PRICE AND QUANTITY MULTIPLICATION VALUE IS--" + pricevalmultipli);

            subtotalvalfloattostr = Float.toString(pricevalmultipli);
            System.out.println("SUBTOTAL VALUE float to string before edit quantity is--" + subtotalvalfloattostr);
            subtotalval.setText(subtotalvalfloattostr);

            subtotalvalfloatnoneditqty = Float.parseFloat(subtotalval.getText().toString());
            totalvalueofallcart.add(subtotalvalfloatnoneditqty);
            System.out.println("TOTAL VALUE OF ALL CART" + totalvalueofallcart);


            // SingletonActivity.singlecartprice=  totalvalueofallcart;


            float sum = 0;
            for (int i = 0; i < totalvalueofallcart.size(); i++) {
                sum += totalvalueofallcart.get(i);
                System.out.println("SUM BEFORE EDITION" + sum);

            }
            add = sum;
            System.out.println("Grand total is" + sum);
            String sumstr = Float.toString(sum);
            totalcartsubtotaltxt.setText(sumstr);
            totalcartgrandtotaltxt.setText(sumstr);

            SingletonActivity.totalamount = sumstr;


            quantityedittext.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // quantityintval = Integer.parseInt(quantityedittext.getText().toString());
                    // System.out.println("EDITTEXT QUANTITY IS on editing" + quantityintval);
                }

                @Override
                public void afterTextChanged(Editable arg0) {

                    quantityintval = Float.parseFloat(quantityedittext.getText().toString());
                    System.out.println("EDITTEXT QUANTITY IS after editing" + quantityintval);
                  /*  quantityintval = Integer.parseInt(quantityedittext.getText().toString());
                    System.out.println("EDITTEXT QUANTITY IS after editing" + quantityintval);*/

                    //  countvalues.add(quantityintval);
                    //System.out.println("ARRAYLIST EDITED QUANTITY VALUES IS--" + countvalues);

                    String pricewithoutcommastr = priceval.getText().toString().replace(",", "");

                    float pricevalfloat = Float.parseFloat(pricewithoutcommastr);
                    System.out.println("PRICE IN FLOAT AFTER EDITING " + pricevalfloat);

                    subtotalvalfloat = pricevalfloat * quantityintval;
                    System.out.println("SUBTOTAL VALUE " + subtotalvalfloat);

                    subtotalvalfloattostr = Float.toString(subtotalvalfloat);
                    System.out.println("SUBTOTAL VALUE float to string in onclick" + subtotalvalfloattostr);
                    subtotalval.setText(subtotalvalfloattostr);

                    cartsubtotalvalstrtofloat = Float.parseFloat(subtotalval.getText().toString());
                    System.out.println("TOTAL VALUE OF SINGLE CART " + cartsubtotalvalstrtofloat);


                    finaltotal = (add + cartsubtotalvalstrtofloat) - singlecarttotalfloat;
                    System.out.println("final total of edited cart " + finaltotal);

                    String sumstrin = Float.toString(finaltotal);
                    System.out.println("final total of edited cart " + sumstrin);

                    add = finaltotal;

                    totalcartsubtotaltxt.setText(sumstrin);
                    totalcartgrandtotaltxt.setText(sumstrin);

                    SingletonActivity.totalamount = sumstrin;


                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    quantityintval = Float.parseFloat(quantityedittext.getText().toString());
                    System.out.println("EDITTEXT QUANTITY IS BEFORE editing" + quantityintval);

                  /*  quantityintval = Integer.parseInt(quantityedittext.getText().toString());
                    System.out.println("EDITTEXT QUANTITY IS BEFORE editing" + quantityintval);
*/
                    String pricewithoutcommastr = priceval.getText().toString().replace(",", "");

                    float pricevalfloat = Float.parseFloat(pricewithoutcommastr);
                    System.out.println("PRICE IN FLOAT BEFORE EDITING " + pricevalfloat);

                    singlecarttotalfloat = pricevalfloat * quantityintval;
                    System.out.println("SUBTOTAL VALUE BEFORE EDITING " + singlecarttotalfloat);

                    subtotalvalfloattostr = Float.toString(singlecarttotalfloat);
                    System.out.println("SUBTOTAL VALUE float BEFORE EDITING to string in onclick" + subtotalvalfloattostr);
                    subtotalval.setText(subtotalvalfloattostr);

                    cartsubtotalvalstrtofloat = Float.parseFloat(subtotalval.getText().toString());
                    System.out.println("TOTAL VALUE cart before editing is" + cartsubtotalvalstrtofloat);


                }
            });





           /* updatecartbtninrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    quantitystr = quantityedittext.getText().toString();

                    System.out.println("QUANTITY IN EDITTEXT ARE--------" + quantitystr);

                    editqtyval = Integer.parseInt(quantityedittext.getText().toString());
                    System.out.println("EDITTEXT UPDATED QUANTITY IS " + editqtyval);

                    if (editqtyval == 0) {
                        // minusimg.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Quantity should not be equal to 0", Toast.LENGTH_SHORT).show();
                    } else {

                        System.out.println("PRODUCT ID IN EDIT QTY ONCLICK ARE--------" + prodid.getText().toString());

                        editsinglecartquantity(quantityedittext.getText().toString(), prodid.getText().toString());

                        String pricewithoutcommastr = priceval.getText().toString().replace(",", "");

                        float pricevalfloat = Float.parseFloat( pricewithoutcommastr);
                        System.out.println("PRICE IN FLOAT " + pricevalfloat);

                        subtotalvalfloat = pricevalfloat * editqtyval;
                        System.out.println("SUBTOTAL VALUE " + subtotalvalfloat);

                        subtotalvalfloattostr = Float.toString(subtotalvalfloat);
                        System.out.println("SUBTOTAL VALUE float to string in onclick" + subtotalvalfloattostr);
                        subtotalval.setText(subtotalvalfloattostr);

                        subtotalvalfloatnoneditqty = Float.parseFloat(subtotalval.getText().toString());

                        if (editqtyval > 0) {
                            finaltotal = subtotalvalfloat - (subtotalvalfloatnoneditqty / editqtyval);
                        } else {
                            finaltotal = subtotalvalfloat - pricevalfloat;
                        }


                        totalvalueofallcart.add(finaltotal);
                        System.out.println("TOTAL VALUE OF ALL CART in onclick" + totalvalueofallcart);

                        float sum = 0;
                        for (int i = 0; i < totalvalueofallcart.size(); i++) {
                            sum += totalvalueofallcart.get(i);

                        }

                        System.out.println("Grand total is in onclick" + sum);
                        String sumstr = Float.toString(sum);
                        totalcartsubtotaltxt.setText(sumstr);
                        totalcartgrandtotaltxt.setText(sumstr);
                    }
                }
            });*/

            System.out.println("NAME CART PRODUCT ARE--------" + namecartproductdetails.get(j));
            String cartProductName;
            if (namecartproductdetails.get(j).length() >= 25) {
                cartProductName = namecartproductdetails.get(j).substring(0, 25) + "...";

                SingletonActivity.nameofcartimg = namecartproductdetails;

            } else {

                cartProductName = namecartproductdetails.get(j);
                SingletonActivity.nameofcartimg = namecartproductdetails;

            }
            textView.setText(cartProductName);
            // textView.setText(namecartproductdetails.get(j));

           // System.out.println("PRICE VALUE IN INIT--------" + pricecartproductdetails.get(j));
            System.out.println("BUY AT CART VALUE IN INIT--------" + buyatcartproductdetails.get(j));


            //     Picasso.with(CartActivity.this).load(imgcartproductdetails.get(j)).placeholder(R.drawable.loading)
            //             .fit().into(cartimg);

            //     textView.setText(namecartproductdetails.get(2));


            quantityedittext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getId() == quantityedittext.getId()) {
                        quantityedittext.setCursorVisible(true);
                    }
                }
            });

            quantityedittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard();
                    }
                }

                private void hideKeyboard() {
                    if (quantityedittext != null) {
                        InputMethodManager imanager = (InputMethodManager) CartActivity.this
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imanager.hideSoftInputFromWindow(quantityedittext.getWindowToken(), 0);

                    }

                }
            });


            stk.addView(row, j);


            cartrelative.setVisibility(View.VISIBLE);

            closeimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDialog = new Dialog(CartActivity.this, R.style.AppTheme);
                    mDialog.setCancelable(false);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.setContentView(R.layout.deleteitem_dialog);

                    dialogdeleteitemtxt = (TextView) mDialog.findViewById(R.id.suretodelete);
                    dialogokbtn = (Button) mDialog.findViewById(R.id.okaybtn);
                    dialogcancelbtn = (Button) mDialog.findViewById(R.id.cancelbtn);

                    dialogokbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(SingletonActivity.checkConnectivity(CartActivity.this)){
                                mDialog.dismiss();
                                deletesinglecartitem(prodid.getText().toString());

                            }
                            else{
                                Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                                startActivity(intent);
                            }


                        }
                    });

                    dialogcancelbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           mDialog.dismiss();
                        }
                }

                );

                    mDialog.show();

                    // stk.removeView(row);
                   // deletesinglecartitem(prodid.getText().toString());
                }
            });


            updateallcartbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    valuesquantity.clear();


                    for (int i = 0; i < stk.getChildCount(); i++) {

                        EditText et = (EditText) stk.getChildAt(i).findViewById(R.id.editText);
                        if (et != null) {
                            valuesquantity.add(String.valueOf(et.getText()));
                        }
                    }
                    System.out.println("EDITTEXT VALUES in onclick" + valuesquantity);

                    String noofqtyvalues = TextUtils.join(",", valuesquantity);
                    System.out.println("No of qty csv arraylist on click of update cart button---" + noofqtyvalues);

                    String prodidcsv = TextUtils.join(",", idcartproductdetails);

                    System.out.println("product ids csv arraylist on click of update cart button---" + prodidcsv);

                    allsinglecartprices.clear();
                    for (int i = 0; i < stk.getChildCount(); i++) {

                        TextView subtotalsinglecart = (TextView) stk.getChildAt(i).findViewById(R.id.subtotalval);
                        //EditText et = (EditText) stk.getChildAt(i).findViewById(R.id.editText);
                        if (subtotalsinglecart != null) {
                            allsinglecartprices.add(String.valueOf(subtotalsinglecart.getText()));
                        }
                    }
                    System.out.println("single cart price VALUES on update cart" + allsinglecartprices);
                    SingletonActivity.singlecartprice = allsinglecartprices;

                    if(SingletonActivity.checkConnectivity(CartActivity.this)){

                        editmultiplecartquantity(noofqtyvalues, prodidcsv);
                    }
                    else{
                        Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent);
                    }


                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) CartActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(CartActivity.this.getComponentName()));
            handleIntent(getIntent());
        }
        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("query....", "query...." + query);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Typeface face = Typeface.createFromAsset(getApplication().getAssets(), "fonts/OpenSans-Regular.ttf");    //  THIS


        if (id == R.id.action_login) {

            Intent intent = new Intent(CartActivity.this, Login.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_terms) {
            Intent in = new Intent(this, TermsandCondition.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if (fnsenderedittxt.getText().toString().length()>0) {
                    fnsenderedittxt.setError(null);
                }*/
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Cart Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
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
                "Cart Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.smgapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}







