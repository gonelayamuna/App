package com.example.user.smgapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 20-04-2016.
 */
public class NoInternet extends AppCompatActivity {
    Button tryagain;
TextView no_inter;
    Typeface face;
    TextView app_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.no_internet);
       /* LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.no_internet, null, false);
        drawer.addView(contentView, 0);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_no_internet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title= (TextView) findViewById(R.id.app_title);
        app_title.setTypeface(face, Typeface.BOLD);

        tryagain= (Button) findViewById(R.id.tryagain_btn);
        no_inter=(TextView)findViewById(R.id.no_internet);
        face = Typeface.createFromAsset(getApplication().getAssets(),"fonts/OpenSans-Regular.ttf");


        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetworkConnection(getApplicationContext());
            }
        });

    }

    public boolean checkNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnectedOrConnecting() ||mobile.isConnectedOrConnecting()) {

            Log.e("if case..", " internet..");
            Intent i = new Intent(getApplicationContext(), HomePage.class);
            startActivity(i);
        } else {
           /* Intent intent=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent);*/
           // no_inter.setText("Please check your internet connection.");
        }
        return false;
    }

}
