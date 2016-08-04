package com.example.user.smgapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SMG A Block on 6/29/2016.
 */
public class CheckOutOrderIdActivity extends Activity {

    Button continueshoppingbutton;
    TextView orderidvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoutorder);

        orderidvalue = (TextView)findViewById(R.id.orderidtxtval);

       /* String orderidfrombundle = "1234";
        orderidvalue.setText(orderidfrombundle);*/

        Bundle b= getIntent().getExtras();
        String orderidfrombundle = b.getString("OrderId");



        if(b!=null){
            orderidvalue.setText(orderidfrombundle);
        }


        continueshoppingbutton = (Button) findViewById(R.id.continueshoppingbtn);

        continueshoppingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckOutOrderIdActivity.this,HomePage.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();

        Intent i = new Intent(CheckOutOrderIdActivity.this,HomePage.class);
        startActivity(i);

    }
}
