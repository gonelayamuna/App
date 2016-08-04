package com.example.user.smgapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Splash extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "	01Y8zEIOXFJ0JSmovGxi3pijw";
    private static final String TWITTER_SECRET = "	y0kjsvpBMkwcLb3GXXBxdAuUTWFaFRTcbTXdFuIEbPGcoSO5yR";
    private static final String PINCODE_URL = SingletonActivity.API_URL+"api/pincode.php";
    private static int SPLASH_TIME_OUT = 10000;
    ProgressBar pBar;
    DBController controller = new DBController(this);
    ArrayList<HashMap<String, String>> getPinCodeandCityList;
    ArrayList<String> citieslist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));*/
        setContentView(R.layout.activity_splash);
        pBar = (ProgressBar) findViewById(R.id.progress_bar_custom);
        pBar.setVisibility(View.VISIBLE);
        

       /*Intent in=new Intent(Splash.this,PincodeService.class);
        startService(in);

        if(controller.getAllPincodeCity().size()>0){
            getPinCodeandCityList = controller.getAllPincodeCity();

            for(int i = 0; i < getPinCodeandCityList.size(); i++){

                citieslist.add(getPinCodeandCityList.get(i).get("city"));
                //  System.out.println("CITIES ARE----" + citieslist.get(i));

                Set<String> hs = new HashSet<>();
                hs.addAll(citieslist);
                citieslist.clear();
                citieslist.addAll(hs);

                SingletonActivity.citiesarray = citieslist;
            }
        }*/



        if(controller.getPinCodeCount()>0)
        {
            if(controller.getAllPincodeCity().size()>0){
                getPinCodeandCityList = controller.getAllPincodeCity();

                for(int i = 0; i < getPinCodeandCityList.size(); i++){

                    citieslist.add(getPinCodeandCityList.get(i).get("city"));
                    //  System.out.println("CITIES ARE----" + citieslist.get(i));

                    Set<String> hs = new HashSet<>();
                    hs.addAll(citieslist);
                    citieslist.clear();
                    citieslist.addAll(hs);

                    SingletonActivity.citiesarray = citieslist;
                }
            }
        }

        else
        {
            Intent in = new Intent(Splash.this, PincodeService.class);
            startService(in);
        }



        //  ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        Runnable run = new Runnable() {

            @Override
            public void run() {
                //progressBar.
            }
        };
        Handler han = new Handler();
        han.postAtTime(run, 100);

        /*progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.darkGrey),
                android.graphics.PorterDuff.Mode.SRC_IN);*/

        // progressBar.startProgress();
        //progressBar.stopProgress();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                checkNetworkConnection(getApplicationContext());

                // close this activity
              finish();
            }
        }, SPLASH_TIME_OUT);

    }

  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/


    public boolean checkNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnectedOrConnecting() ||mobile.isConnectedOrConnecting()) {

            Log.e("if case..", " internet..");
           Intent i = new Intent(Splash.this, HomePage.class);
            startActivity(i);
        } else {
           Intent intent=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent);
        }
        return false;
    }

    private void PinCodeApi(){
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(PINCODE_URL);
            Log.d("url", "thrd" + PINCODE_URL);
            // HttpGet get = new HttpGet("www.google.com");
            HttpResponse response = client.execute(get);
            Log.d("status", "st_" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity);

            JSONObject mainObject = new JSONObject(jsonStr);
            JSONArray jsonArray = mainObject.getJSONArray("result");

            for(int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String pincodestr = jsonObject.getString("pincode");
                String parishable_havestr = jsonObject.getString("parishable_have");
                String service_havestr = jsonObject.getString("service_have");
                String citystr = jsonObject.getString("city");


                HashMap queryValues = new HashMap<String, String>();

                queryValues.put("Pincode", pincodestr);
                queryValues.put("Parishable_have", parishable_havestr);
                queryValues.put("Service_have", service_havestr);
                queryValues.put("City", citystr);

                controller.insertPincode(queryValues);



            }
            Log.d("finished","check");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
