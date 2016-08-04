package com.example.user.smgapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TermsandCondition extends NavigationDrawer {
    TextView terms;
    TextView app_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_termsand_condition);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_termsand_condition, null, false);
        drawer.addView(contentView, 0);
        terms=(TextView)findViewById(R.id.textTerms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app_title = (TextView) findViewById(R.id.app_title);

        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");

        app_title.setText(R.string.action_terms);
        app_title.setTypeface(face, Typeface.BOLD);
        terms.setText(Html.fromHtml(getResources().getString(R.string.TermsAndCondition)));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        if(item.getItemId()== R.id.action_terms)
        {
            Intent in=new Intent(this,TermsandCondition.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}
