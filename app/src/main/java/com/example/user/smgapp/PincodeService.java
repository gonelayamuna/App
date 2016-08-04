package com.example.user.smgapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 6/2/2016.
 */

public class PincodeService extends IntentService {
    private static final String PINCODE_URL = SingletonActivity.API_URL+"api/pincode.php";
    DBController controller;
    ArrayList<HashMap<String, String>> getPinCodeandCityList;
    ArrayList<String> citiesreplist = new ArrayList<String>();
    ArrayList<String> citieslist = new ArrayList<String>();
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public PincodeService() {
        super("PincodeService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }*/
        Log.d("inside", "pincode");
        controller=new DBController(this);
       // PinCodeVolleyApi();


        if(SingletonActivity.checkConnectivity(PincodeService.this)){

            PinCodeApi();
        }
        else{
            Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent1);
        }

    }
   /* private void PinCodeVolleyApi(){

		*//*pdia = new ProgressDialog(SignupActivityVolley.this);
		pdia.setMessage("Please Wait...");
		pdia.setCanceledOnTouchOutside(false);
		pdia.show();*//*

        Log.d("inside","volley");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,PINCODE_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pdia.dismiss();
                        Log.d("inside","respose");
                        System.out.println("RESPONSE IN PINCODE VOLLEY ACTIVITY IS"+ response);

                        try {
                            JSONObject mainObject = new JSONObject(response);

                            //System.out.println("JSON OBJECT RESPONSE IS---->" + mainObject);

                            JSONArray jsonArray = mainObject.getJSONArray("result");

                           // System.out.println("JSON ARRAY RESPONSE IS---->" + jsonArray);

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






                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("Exception"+e);
                        }



                        //  Toast.makeText(SignupActivityVolley.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //  pdia.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }

        };

        *//*RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);*//*
    }
*/
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



               citiesreplist.add(jsonArray.getJSONObject(i).getString("city"));



               Set<String> hs = new HashSet<>();
               hs.addAll(citiesreplist);
               citiesreplist.clear();
               citiesreplist.addAll(hs);

               SingletonActivity.citiesarray = citiesreplist;

               HashMap queryValues = new HashMap<String, String>();

               queryValues.put("Pincode", pincodestr);
               queryValues.put("Parishable_have", parishable_havestr);
               queryValues.put("Service_have", service_havestr);
               queryValues.put("City", citystr);

               controller.insertPincode(queryValues);


           }
         //  DBController.database.setTransactionSuccessful();
          // DBController.database.endTransaction();




           Log.d("finished","check");



       } catch (Exception e) {
           e.printStackTrace();
       }

       }
}
