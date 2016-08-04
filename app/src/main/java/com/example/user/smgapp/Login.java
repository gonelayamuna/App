package com.example.user.smgapp;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
/*import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;*/
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by user on 15-04-2016.
 */
public class Login extends NavigationDrawer implements  View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    EditText login_name, login_pwd;
    Button login_btn,backbtn;
  /*  LoginButton fb_login_btn;
    CallbackManager callbackManager;*/
    private static final int SIGN_IN_REQUEST_CODE = 10;
    private static final int ERROR_DIALOG_REQUEST_CODE = 11;
    ProgressDialog progress_dialog;
    static  String str_login_name, str_login_pwd;

    // For communicating with Google APIs
    private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private boolean mIntentInProgress;
    // contains all possible error codes for when a client fails to connect to
    // Google Play services
    private ConnectionResult mConnectionResult;

    public static final String MyPREFERENCES = "MyPrefs";
    static SharedPreferences prefs;

  // private static final String LOGIN_URLS = SingletonActivity.API_URL+"api/login.php?email="+str_login_name+"&&hash="+str_login_pwd ;
  private static final String LOGIN_URLS = SingletonActivity.API_URL+"api/test_login.php?email="+str_login_name+"&&password="+str_login_pwd ;

    TextView signuptxtvw,app_title;

    String dialogcustnamestr;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    String custfirstname,custfirstnme;
    String custlastname,custlastnme;
    Context c;

    UtilsDialog util = new UtilsDialog();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.login);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.login, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title = (TextView) findViewById(R.id.app_title);
        app_title.setText("Login");

        signuptxtvw = (TextView)findViewById(R.id.signuptxt);

        signuptxtvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(this);

        login_name = (EditText) findViewById(R.id.login_name);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        cartcounttxt = (TextView)findViewById(R.id.cartnumber);


        login_name.setText("adya@gmail.com");
        login_pwd.setText("adyarani"); //customer id = 1454
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Signing in....");



    }



    private void  login(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,SingletonActivity.API_URL+"api/test_login.php?email="+str_login_name+"&&password="+str_login_pwd,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("RESPONSE IN NEW LOGIN API IS----------"+ response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println("JSONOBJECT RESPONSE IN NEW LOGIN API IS---" +jsonObject);

                            String msgstr = jsonObject.getString("msg");
                            System.out.println("MESSAGE STRING IN NEW LOGIN API IS-----" +msgstr);

                            if(msgstr.equalsIgnoreCase("Invalid login or password.")){
                                Toast.makeText(getApplicationContext(),"Invalid login or password.",Toast.LENGTH_SHORT).show();
                            }

                            if(msgstr.equalsIgnoreCase("Email and password are required")){
                                Toast.makeText(getApplicationContext(),"Email and password are required",Toast.LENGTH_SHORT).show();
                            }

                         //   String countstr = jsonObject.getString("count");
                          //  System.out.println("COUNT IN LOGIN API IS-----" +countstr);

                            JSONObject jobj = jsonObject.getJSONObject("result");
                            System.out.println("RESULT JSON OBJECT IN NEW LOGIN API IS----" +jobj);


                            String custid = jobj.getString("customerId");
                             custfirstname = jobj.getString("customerFirstName");
                             custlastname = jobj.getString("customerLastName");
                            String custemail = jobj.getString("customerEmail");

                            System.out.println("CUSTOMER ID IN NEW LOGIN API IS----" +custid);
                            System.out.println("CUSTOMER FIRST NAME IN NEW LOGIN API IS----" + custfirstname);
                            System.out.println("CUSTOMER LAST NAME IN NEW LOGIN API IS----" + custlastname);
                            System.out.println("CUSTOMER EMAIL IN NEW LOGIN API IS----" + custemail);



                            SingletonActivity.custidstr=custid;
                            SingletonActivity.custemailstr=custemail;
                            SingletonActivity.custfirstname=custfirstname;
                            SingletonActivity.custlastname=custlastname;
                      //      SingletonActivity.cartcount=countstr;

                            if(custid!=null){


                                SharedPreferences.Editor editor = getSharedPreferences(
                                        MyPREFERENCES, MODE_PRIVATE).edit();
                                editor.putBoolean("loginlogoutkey", true);
                                editor.putString("customerid", SingletonActivity.custidstr);
                                editor.putString("firstname",  custfirstname);
                                editor.putString("lastname",  custlastname);
                                editor.putString("cartcount", SingletonActivity.cartcount);
                                editor.putString("senderemail", custemail);

                                //  editor.putString("")

                                editor.commit();

                                Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();
                                finish();



                              //  startActivity(getIntent());

                                cartcounttxt.setText(SingletonActivity.cartcount);




                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(HomeScreenActivity166.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            protected Map<String,String> getParams(){


                Map<String,String> params = new HashMap<String, String>();
                params.put("email",str_login_name);
                params.put("hash", str_login_pwd);



                return params;
            }

        };

        GlobalClass.getInstance().addToRequestQueue(stringRequest);
     /*   RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }


    private GoogleApiClient buildGoogleAPIClient() {
        return new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                str_login_name =  login_name.getText().toString();
                str_login_pwd =  login_pwd.getText().toString();

              boolean invalid = false;


                if (!isValidEmail(str_login_name)) {
                    invalid = true;
                    login_name.setError("Enter email");
                    login_pwd.setError(null);


                }

                else if (!isValidPassword(str_login_pwd)) {
                    invalid = true;

                    login_name.setError(null);
                    login_pwd.setError("Enter password");


                }



             else if(invalid == false)

                {

                    if(SingletonActivity.checkConnectivity(Login.this)){

                        login();
                    }
                    else{
                        Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent);
                    }



                }


              //  Toast.makeText(getApplicationContext(), "Clicked on Log button", Toast.LENGTH_SHORT).show();
                break;

            case R.id.sign_in_button:
                processSignIn();
                break;

        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password


    private boolean isValidPassword(String pass) {

        if (pass != null && pass.length() > 0) {
            return true;
        }
        return false;
    }


    private void processSignIn() {

        if (!mGoogleApiClient.isConnecting()) {
            processSignInError();
            mSignInClicked = true;
        }
    }

    private void processSignInError() {
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this,
                        SIGN_IN_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //twitter_loginButton.onActivityResult(requestCode, resultCode, data);


    }


  /* void getUserData() {
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {

                    @Override
                    public void failure(TwitterException e) {

                    }

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        String twitterImage = user.profileImageUrl;

                        try {
                            Log.d("imageurl", user.profileImageUrl);
                            Log.d("name", user.name);
                            //Log.d("email",user.email);
                            Log.d("des", user.description);
                            Log.d("followers ", String.valueOf(user.followersCount));
                            Log.d("createdAt", user.createdAt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                });


    }*/

    @Override
    public void onConnected(Bundle connectionHint) {

        mSignInClicked = false;
        Toast.makeText(getApplicationContext(), "Signed In Successfully",
                Toast.LENGTH_LONG).show();

        processUserInfoAndUpdateUI();

        processUIUpdate(true);

    }

    private void processUIUpdate(boolean isUserSignedIn) {
        if (isUserSignedIn) {
            //signOutButton.setEnabled(true);
           // userInfoButton.setEnabled(true);
           // sharePostButton.setEnabled(true);
           // shareMediaButton.setEnabled(true);
           // revokeAccessButton.setEnabled(true);
        } else {
          //  signOutButton.setEnabled(false);
          //  userInfoButton.setEnabled(false);
          //  sharePostButton.setEnabled(false);
          //  shareMediaButton.setEnabled(false);
           // revokeAccessButton.setEnabled(false);
        }
    }

    private void processUserInfoAndUpdateUI() {

        Person signedInUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (signedInUser != null) {

            if (signedInUser.hasDisplayName()) {
                String userName = signedInUser.getDisplayName();
              //  this.userName.setText("Name: " + userName);
            }

            if (signedInUser.hasTagline()) {
                String tagLine = signedInUser.getTagline();
              //  this.userTagLine.setText("TagLine: " + tagLine);
               // this.userTagLine.setVisibility(View.VISIBLE);
            }

            if (signedInUser.hasAboutMe()) {
                String aboutMe = signedInUser.getAboutMe();
              //  this.userAboutMe.setText("About Me: " + aboutMe);
              //  this.userAboutMe.setVisibility(View.VISIBLE);
            }

            if (signedInUser.hasBirthday()) {
                String birthday = signedInUser.getBirthday();
              //  this.userBirthday.setText("DOB " + birthday);
               // this.userBirthday.setVisibility(View.VISIBLE);
            }

            if (signedInUser.hasCurrentLocation()) {
                String userLocation = signedInUser.getCurrentLocation();
              //  this.userLocation.setText("Location: " + userLocation);
              //  this.userLocation.setVisibility(View.VISIBLE);
            }

            String userEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
           // this.userEmail.setText("Email: " + userEmail);

            if (signedInUser.hasImage()) {
                String userProfilePicUrl = signedInUser.getImage().getUrl();
                // default size is 50x50 in pixels.changes it to desired size
                int profilePicRequestSize = 250;

                userProfilePicUrl = userProfilePicUrl.substring(0,
                        userProfilePicUrl.length() - 2) + profilePicRequestSize;
               /* new UpdateProfilePicTask(userProfilePic)
                        .execute(userProfilePicUrl);*/
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
           GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, ERROR_DIALOG_REQUEST_CODE).show();
            return;
        }
        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                processSignInError();
            }
        }
    }
}
