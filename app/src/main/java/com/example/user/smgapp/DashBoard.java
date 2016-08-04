package com.example.user.smgapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 28-05-2016.
 */
public class DashBoard extends NavigationDrawer {

    TextView app_title, user_name_from_email;
    String user_name_value, email_id_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_dashboard, null, false);
        drawer.addView(contentView, 0);
        user_name_from_email = (TextView) findViewById(R.id.user_name_from_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title = (TextView) findViewById(R.id.app_title);
        app_title.setText("DashBoard");
        Intent intent = getIntent();
        user_name_value = intent.getStringExtra("email_name");
        email_id_value = intent.getStringExtra("email_id");
        user_name_from_email.setText("Hello," + user_name_value);
    }
}
