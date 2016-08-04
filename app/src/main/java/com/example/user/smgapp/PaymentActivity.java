package com.example.user.smgapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.razorpay.Checkout;

import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

public class PaymentActivity extends Activity
{

  TextView senderamttxt,orderidrzrpaytxtvw;
    String sendercontactnum,sendercontactemail;
    static SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    String finalamttopay;
    String finalamtafterconvert;

    public PaymentActivity(){}

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.main);




      senderamttxt = (TextView)findViewById(R.id.sender);

      orderidrzrpaytxtvw = (TextView)findViewById(R.id.orderid);

      Bundle b= getIntent().getExtras();
      String orderidfrombundle = b.getString("OrderId");

      if(b!=null){
          orderidrzrpaytxtvw.setText("ORDER ID "+orderidfrombundle);
      }

      senderamttxt.setText("INR "+SingletonActivity.totalamount);

      finalamttopay = SingletonActivity.totalamount;

      float finalamtflt = Float.parseFloat(finalamttopay);
      float finalamtinpaiseindecimal = finalamtflt * 100;

      int finalamtinpaise = (int) finalamtinpaiseindecimal;


       finalamtafterconvert = String.valueOf(finalamtinpaise);

      sendercontactnum = SingletonActivity.sendercontactnos;
      sendercontactemail =  SingletonActivity.custemailstr;

      System.out.println("SENDER EMAIL IN PAYMENT ACTIVITY IS---" + sendercontactemail);
      System.out.println("SENDER NUMBER IN PAYMENT ACTIVITY IS---" + sendercontactnum);
      System.out.println("FINAL AMOUNT IN PAYMENT ACTIVITY IS---" + finalamtafterconvert);

    // payment button created by you in xml layout
    View button = (View) findViewById(R.id.pay_btn);

    button.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            startPayment();
        }

        ;
    });
  }

  public void startPayment(){
    /**
     * Replace with your public key
     */
   final String public_key = "rzp_live_ILgsfZCZoFIKMb";

//smg key
   // final String public_key = "rzp_live_H7ONDH8vXcqb9D";


    /**
     * You need to pass current activity in order to let razorpay create CheckoutActivity
     */
    final Activity activity = this;

    final Checkout co = new Checkout();
    co.setPublicKey(public_key);

    try{
      JSONObject options = new JSONObject("{" +
        "description: 'Payment Charges'," +
        "image: 'https://www.sendmygift.com/skin/frontend/rwd/default/images/logo1.png'," +
        "currency: 'INR'}"
      );

     options.put("amount", finalamtafterconvert);
      //  options.put("amount", "1264500");
      options.put("name", "Send My Gift");
    //  options.put("prefill", new JSONObject("{email: 'saurabh.sendmygift@gmail.com', contact: '8050952462'}"));

        JSONObject options1 = new JSONObject();
        options1.put("email",sendercontactemail);
        options1.put("contact",sendercontactnum);

        options.put("prefill",options1);



      co.open(activity, options);

    } catch(Exception e){
      Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }
  }

  /**
  * The name of the function has to be
  *   onPaymentSuccess
  * Wrap your code in try catch, as shown, to ensure that this method runs correctly
  */
  public void onPaymentSuccess(String razorpayPaymentID){
    try {
      Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
    }
    catch (Exception e){
      Log.e("com.merchant", e.getMessage(), e);
    }
  }

  /**
  * The name of the function has to be
  *   onPaymentError
  * Wrap your code in try catch, as shown, to ensure that this method runs correctly
  */
  public void onPaymentError(int code, String response){
    try {
      Toast.makeText(this, "Payment failed: " + Integer.toString(code) + " " + response, Toast.LENGTH_SHORT).show();
    }
    catch (Exception e){
      Log.e("com.merchant", e.getMessage(), e);
    }
  }
}
