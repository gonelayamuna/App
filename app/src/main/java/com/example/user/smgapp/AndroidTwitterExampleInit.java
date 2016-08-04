package com.example.user.smgapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class AndroidTwitterExampleInit extends Activity {
    private static final String TWITTER_KEY = "01Y8zEIOXFJ0JSmovGxi3pijw";
    private static final String TWITTER_SECRET = "y0kjsvpBMkwcLb3GXXBxdAuUTWFaFRTcbTXdFuIEbPGcoSO5yR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));*/
        startLoginActivity();
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, Login.class));
    }

}
