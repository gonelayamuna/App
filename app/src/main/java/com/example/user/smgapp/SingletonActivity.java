package com.example.user.smgapp;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class SingletonActivity extends Activity {

    public static SingletonActivity _sInstane = null;

    public static String custidstr, custnamestr, custemailstr, cartcount, productid,passed_id,totalpriceofcart,sendercontactnumber;
    public static String custfirstname,custlastname;
    //public static String  API_URL = "http://106.51.250.180/smgdemo/";
   // public static String API_URL = "http://192.168.56.1/smg_dev/";
    public static String API_URL = "http://www.sendmygift.com/";
    public static ArrayList<String> listofimgs;
    public static ArrayList<String> citiesarray;
    public static ArrayList<String> individual_rating_array, rating_name_array, rating_title_array, rating_date_array, user_review_array;
    public static ArrayList<String> nameofcartimg,singlecartqty;
    public static ArrayList<String> singlecartprice;
    public static String totalamount,sendercontactnos;
    public static String date,time,pincode,image_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        _sInstane = this;

    }

    public static SingletonActivity getInstance() {

        if (_sInstane == null) {
        }
        return _sInstane;
    }

    public static boolean checkConnectivity(Context context){
        boolean isConnected = false;
        try{
            ConnectivityManager connService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network = connService.getActiveNetworkInfo();
            if(network != null) {
                NetworkInfo.State state = network.getState();


                if(network.isConnected()){
                    isConnected = true;

                }

            }else{
                isConnected = false;

            }
        }catch (Exception e) {
            e.printStackTrace();

        }
        return isConnected;
    }



}



