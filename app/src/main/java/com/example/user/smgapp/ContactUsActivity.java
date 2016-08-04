package com.example.user.smgapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SMG A Block on 5/23/2016.
 */

public class ContactUsActivity extends NavigationDrawer implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String TAG = ContactUsActivity.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    StringBuilder strReturnedAddress;
    TextView myAddress, app_title;
    Button submitbtn;
    ProgressDialog pdia;
    EditText nameedttxt,emailedttxt,telephoneedttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contactus);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_contactus, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title= (TextView) findViewById(R.id.app_title);
        app_title.setText("Contact Us");


        myAddress = (TextView)findViewById(R.id.mapaddress);
        submitbtn = (Button)findViewById(R.id.button);
        nameedttxt = (EditText)findViewById(R.id.nameedttxt);
        emailedttxt = (EditText)findViewById(R.id.emailedttxtcntctus);
        telephoneedttxt = (EditText)findViewById(R.id.telephoneedttxt);



        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String namestrcontctus =  nameedttxt.getText().toString();
             String emailstrcontctus =  emailedttxt.getText().toString();
             String telestrcontctus =  telephoneedttxt.getText().toString();

                boolean invalid = false;



                if (!isName(namestrcontctus)) {
                    invalid = true;

                    nameedttxt.setError("Invalid Name");

                }

                else  if (!isValidEmail(emailstrcontctus)) {
                    invalid = true;

                    emailedttxt.setError("Invalid Email");

                }


                else if (!isTelephone(telestrcontctus)) {
                    invalid = true;

                    telephoneedttxt.setError("Please enter 10 digit");

                }


                 else if (invalid == false) {

                    if(SingletonActivity.checkConnectivity(ContactUsActivity.this)){

                        ContactUsAPI(namestrcontctus, emailstrcontctus, telestrcontctus);
                    }
                    else{
                        Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent);
                    }



                 }


            }
        });


    //    setUpMapIfNeeded();

     /*   mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds*/
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isName(String name) {

        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
    }

    private boolean isTelephone(String tele) {

        if (tele != null && tele.length() == 10) {
            return true;
        }
        return false;


    }

    private void ContactUsAPI(String name,String email,String telephone) {


        pdia = new ProgressDialog(ContactUsActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();

      //  https://www.sendmygift.com/api/contactus.php?name=vinod&email=vinudelhi006@gmail.com&telephone=7090141466

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL+"api/contactus.php?name="+name+"&email="+email+"&telephone="+telephone,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("RESPONSE IN CARTACTIVITY for EDIT multiple CART QUANTITY IS" + response);
                        //Log.d("Request", response.toString());

                        try {

                            //   JSONObject jsonObject = new JSONObject(response);
                            String isSuccessStr = response.getString("isSuccess");
                            String resultStr = response.getString("result");
                            System.out.println("IS SUCCESS STRING IN EDIT MULTIPLE CART  IS" + isSuccessStr);
                            //System.out.println("MESSAGE STRING IN DELETE CART VOLLEY ACTIVITY IS" + messageStr);

                            if(isSuccessStr.equalsIgnoreCase("Success!")){

                                pdia.dismiss();
                                Toast.makeText(getApplicationContext(), resultStr, Toast.LENGTH_LONG).show();
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



    @Override
    protected void onResume() {
        super.onResume();
     //   setUpMapIfNeeded();
     //   mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

       /* if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }*/
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
            List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);

            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                 strReturnedAddress = new StringBuilder("Address: \n\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                  myAddress.setText(strReturnedAddress.toString());

             //   Toast.makeText(this, strReturnedAddress.toString(),
                    //    Toast.LENGTH_LONG).show();
            }
            else{
                   myAddress.setText("No Address returned!");
                //  Toast.makeText(this, "No Address returned!",
                //      Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
             myAddress.setText("Canont get Address!");
            //  Toast.makeText(this, "Cannot get Address!",
            //     Toast.LENGTH_LONG).show();
        }

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Send My Gift");
       Marker marker= mMap.addMarker(options);
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        CameraUpdate zoom= CameraUpdateFactory.zoomTo(11);

        mMap.animateCamera(zoom);


    }


    @Override
    public void onConnected(Bundle bundle) {
       Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
       if (location == null) {
           LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

      }
       else {
           handleNewLocation(location);
       }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
