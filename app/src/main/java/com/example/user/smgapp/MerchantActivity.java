package com.example.user.smgapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API's.
 **/

public class MerchantActivity extends Activity {

    Button proceedpaytmbutton;
    String orderId;
    String orderidfrombundle;
    TextView orderidpaytmtvaluextvw;
    TextView amtpaytmtvaluextvw;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.merchantapp);
        setContentView(R.layout.paytm);

      System.out.println("SINGLETONACTIVITY TOTALAMOUNT IN MERCHANT ACTIVITY--------" + SingletonActivity.totalamount);
        System.out.println("SINGLETONACTIVITY CUSTOMER ID IN MERCHANT ACTIVITY--------" +  SingletonActivity.custidstr);


         Bundle b= getIntent().getExtras();
         orderidfrombundle = b.getString("OrderId");

     //   orderidfrombundle = "123";
        proceedpaytmbutton = (Button)findViewById(R.id.proceedpaytmbtn);

        orderidpaytmtvaluextvw = (TextView)findViewById(R.id.orderidpaytmvaltxtvw);
        amtpaytmtvaluextvw = (TextView)findViewById(R.id.amtpaytmvaltxtvw);

        orderidpaytmtvaluextvw.setText(orderidfrombundle);
        amtpaytmtvaluextvw.setText(SingletonActivity.totalamount);

        proceedpaytmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartTransaction();
            }
        });

        initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}


    @Override
    public void onBackPressed() {
        //   super.onBackPressed();

        Intent i = new Intent(MerchantActivity.this,HomePage.class);
        startActivity(i);

    }
	//This is to refresh the order id: Only for the Sample App's purpose.
	@Override
	protected void onStart(){
		super.onStart();
		initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	

	private void initOrderId() {
		Random r = new Random(System.currentTimeMillis());
        orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
				+ r.nextInt(10000);
	/*	EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
		orderIdEditText.setText(orderId);*/
	}

	/*public void onStartTransaction(View view) {
		PaytmPGService Service = PaytmPGService.getStagingService();
		Map<String, String> paramMap = new HashMap<String, String>();

*//*		// these are mandatory parameters
//Paytm default credentials------------------------------------------->>>
		paramMap.put("REQUEST_TYPE", "DEFAULT");
		paramMap.put("ORDER_ID", ((EditText) findViewById(R.id.order_id)).getText().toString());
     //  paramMap.put("MID", "DIY12386817555501617");
      //  paramMap.put("MID", "sendmy46684249284303");
		paramMap.put("MID", "klbGlV59135347348753");

		paramMap.put("CUST_ID", "CUST2864");
		paramMap.put("CHANNEL_ID", "WAP");
		paramMap.put("INDUSTRY_TYPE_ID", "Retail");
	//	paramMap.put("WEBSITE", "DIYtestingwap");
     //   paramMap.put("WEBSITE", "sendmygift");
		paramMap.put("WEBSITE", "paytm");
		paramMap.put("TXN_AMOUNT", "50");
		paramMap.put("THEME", "merchant");*//*

//SMG credentials------------------------------------------->>>
       paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID", ((EditText) findViewById(R.id.order_id)).getText().toString());
        //  paramMap.put("MID", "DIY12386817555501617");
      paramMap.put("MID", "sendmy46684249284303");
     //   paramMap.put("MID", "wxOmOlpPUsNcdUhl");

        paramMap.put("CUST_ID", "CUST2864");
        paramMap.put("CHANNEL_ID", "WAP");
     //   paramMap.put("CHANNEL_ID", "WEB");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail114");
        //	paramMap.put("WEBSITE", "DIYtestingwap");
        //   paramMap.put("WEBSITE", "sendmygift");
        paramMap.put("WEBSITE", "Sendweb");
        paramMap.put("TXN_AMOUNT", "50");
        paramMap.put("THEME", "merchant");

		PaytmOrder Order = new PaytmOrder(paramMap);

		PaytmMerchant Merchant = new PaytmMerchant(
			"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");

		*//*"https://www.sendmygift.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*//*
		*//*"http://52.77.145.35/api/paytmCheckSumGenerator.jsp",
				"http://52.77.145.35/api/paytmCheckSumVerify.jsp");*//*

		Service.initialize(Order, Merchant, null);

		Service.startPaymentTransaction(this, true, true,
				new PaytmPaymentTransactionCallback() {
					@Override
					public void someUIErrorOccurred(String inErrorMessage) {
						// Some UI Error Occurred in Payment Gateway Activity.
						// // This may be due to initialization of views in
						// Payment Gateway Activity or may be due to //
						// initialization of webview. // Error Message details
						// the error occurred.
					}

					@Override
					public void onTransactionSuccess(Bundle inResponse) {
						// After successful transaction this method gets called.
						// // Response bundle contains the merchant response
						// parameters.
						Log.d("LOG", "Payment Transaction is successful " + inResponse);
						Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onTransactionFailure(String inErrorMessage,
							Bundle inResponse) {
                        // This method gets called if transaction failed. //
						// Here in this case transaction is completed, but with
						// a failure. // Error Message describes the reason for
						// failure. // Response bundle contains the merchant
						// response parameters.
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
						Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
					}

					@Override
					public void networkNotAvailable() { // If network is not
														// available, then this
														// method gets called.
					}

					@Override
					public void clientAuthenticationFailed(String inErrorMessage) {
						// This method gets called if client authentication
						// failed. // Failure may be due to following reasons //
						// 1. Server error or downtime. // 2. Server unable to
						// generate checksum or checksum response is not in
						// proper format. // 3. Server failed to authenticate
						// that client. That is value of payt_STATUS is 2. //
						// Error Message describes the reason for failure.
					}

					@Override
					public void onErrorLoadingWebPage(int iniErrorCode,
							String inErrorMessage, String inFailingUrl) {

                    }

					// had to be added: NOTE
					@Override
					public void onBackPressedCancelTransaction() {
						// TODO Auto-generated method stub
					}

				});
	}*/

    public void onStartTransaction() {
        PaytmPGService Service = PaytmPGService.getStagingService();
        Map<String, String> paramMap = new HashMap<String, String>();

		// these are mandatory parameters
//Paytm default credentials------------------------------------------->>>
		paramMap.put("REQUEST_TYPE", "DEFAULT");
	//	paramMap.put("ORDER_ID", ((EditText) findViewById(R.id.order_id)).getText().toString());
        System.out.println("ORDER ID IN ONSTARTTRANSACTION  IS----"+ orderId);
	//	paramMap.put("ORDER_ID",orderId);
        paramMap.put("ORDER_ID",orderidfrombundle);
     //  paramMap.put("MID", "DIY12386817555501617");
      //  paramMap.put("MID", "sendmy46684249284303");
		paramMap.put("MID", "klbGlV59135347348753");

	//	paramMap.put("CUST_ID", "CUST2864");
        paramMap.put("CUST_ID",  SingletonActivity.custidstr);

		paramMap.put("CHANNEL_ID", "WAP");
		paramMap.put("INDUSTRY_TYPE_ID", "Retail");
	//	paramMap.put("WEBSITE", "DIYtestingwap");
     //   paramMap.put("WEBSITE", "sendmygift");
		paramMap.put("WEBSITE", "paytm");
		//paramMap.put("TXN_AMOUNT", "1.0");
        paramMap.put("TXN_AMOUNT", SingletonActivity.totalamount);
		paramMap.put("THEME", "merchant");

//SMG credentials------------------------------------------->>>
      /*  paramMap.put("REQUEST_TYPE", "DEFAULT");
        paramMap.put("ORDER_ID",orderId);
        System.out.println("ORDER ID IN ONSTARTTRANSACTION SMG IS----"+ orderId);
        //  paramMap.put("MID", "DIY12386817555501617");
        paramMap.put("MID", "sendmy46684249284303");
        //   paramMap.put("MID", "wxOmOlpPUsNcdUhl");

        paramMap.put("CUST_ID", "CUST2864");
        paramMap.put("CHANNEL_ID", "WAP");
        //   paramMap.put("CHANNEL_ID", "WEB");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail114");
        //	paramMap.put("WEBSITE", "DIYtestingwap");
        //   paramMap.put("WEBSITE", "sendmygift");
        paramMap.put("WEBSITE", "Sendweb");
        paramMap.put("TXN_AMOUNT", "1.0");
        paramMap.put("THEME", "merchant");*/

        PaytmOrder Order = new PaytmOrder(paramMap);

        PaytmMerchant Merchant = new PaytmMerchant(
                "https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
                "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");

		/*"https://www.sendmygift.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/
		/*"http://52.77.145.35/api/paytmCheckSumGenerator.jsp",
				"http://52.77.145.35/api/paytmCheckSumVerify.jsp");*/

        Service.initialize(Order, Merchant, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    @Override
                    public void onTransactionSuccess(Bundle inResponse) {
                        // After successful transaction this method gets called.
                        // // Response bundle contains the merchant response
                        // parameters.
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionFailure(String inErrorMessage,
                                                     Bundle inResponse) {
                        // This method gets called if transaction failed. //
                        // Here in this case transaction is completed, but with
                        // a failure. // Error Message describes the reason for
                        // failure. // Response bundle contains the merchant
                        // response parameters.
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
                    }

                });
    }

}
