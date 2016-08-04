package com.example.user.smgapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutActivity extends NavigationDrawer implements TextWatcher {

      String dialogcustnamestr;
    RadioGroup radioGroup;
    RadioButton cbdradiobutton,paytmradiobutton,citrusradiobutton,mobikwikradiobutton;
    ImageView  cancel_dialog_login, cancel_dialog_forgotpwd;
    LinearLayout pwdandconfpwdsenderlinear;
    CheckBox checkBox;
    Button revieworderbtn,editcartbtn,sendgiftbtn,dialogloginbtn,dialogsubmitbtn;
   // Button loginbtn;
    TextView paymenttxt;
   // TextView forgotpwddialog, backtologinsecdialog,alreadyregistxt;
    Spinner occasion;
    EditText dialogemail,dialogpwd,forgotpwddialogemail;
    EditText fnsenderedittxt,lnsenderedittxt,emailsenderedittxt,pwdedittxt,confirmpwdedittxt;
    EditText fnshippingedittxt,lnshippingedittxt,addressshippingedittxt,cityshippingedittxt,stateshippingedittxt,countryshippingedittxt,pincodeshippingedittxt,contactnosshippingedittxt;
    ScrollView scrollView;
    TextInputLayout emailsendertxtinput;
    TextView app_title,subtotaltxt, grandtotaltxt;


    String[] giftoccasion = {"Select Occasion", "Birthday", "Wedding Anniversary", "Festivals","Baby Naming Ceremony","House Warming Wishes","Wedding Wishes","Get Well Soon","I am Sorry","Please Forgive Me"};
    TableLayout stk;
    private Dialog mDialog,dialog;
    RelativeLayout checkoutreviewlist;
   // static String strdialogmailedittxt, strdialogpwdedittxt, strforgotpwddialogemail;
    static String strfnsenderedittxt,strlnsenderedittxt,  stremailsenderedittxt,strpwdedittxt,strconfirmpwdedittxt;
    static String strfnshipgedittxt,strlnshipedittxt,straddressshipedittxt,strcontactnosshipedittxt,occasionSpinnerSelectedStr;
    static String strcityshippingedittxt,strstateshippingedittxt,strpincodeshippingedittxt, strcountryshippingedittxt,strtoshippingedittxt,strcommentshippingedittxt;

String paymenttypestr,strnamecbdedttxt,straddresscbdedttxt,strcitycbdedttxt,strstatecbdedttxt,strpincbdedttxt,strcontactnumcbdedttxt,strcountrycbdedttxt;



    String stringcustname;
    String firstnamestr,lastnamestr;

    ImageView canceldialogcbdiv;
    Button savecbdiv;
    EditText namecbdedttxt,addresscbdedttxt,citycbdedttxt,statecbdedttxt,pincbdedttxt,contactnumcbdedttxt,countrycbdedttxt,toshippingedittxt,commentshippingedittxt;
  ProgressDialog  pdia;

    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_checkout);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_checkout, null, false);
        drawer.addView(contentView, 0);

        prefs = CheckoutActivity.this.getSharedPreferences(MyPREFERENCES, 0);
        //1)key=Name and 2)key=Emailid 3)key=Phonenos
        loginboolean = prefs.getBoolean("loginlogoutkey", false);
        firstnamestr = prefs.getString("firstname", "");
        lastnamestr = prefs.getString("lastname", "");

        app_title = (TextView) findViewById(R.id.app_title);

        emailsendertxtinput = (TextInputLayout)findViewById(R.id.textinputthree);
        //sender
        fnsenderedittxt = (EditText) findViewById(R.id.firstnameedittext);
        lnsenderedittxt = (EditText) findViewById(R.id.lastnameedittext);

        emailsenderedittxt = (EditText) findViewById(R.id.emailedittext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title.setText("Checkout");

        revieworderbtn = (Button)findViewById(R.id.revieworderbutton);

        editcartbtn = (Button)findViewById(R.id.editcartbutton);
        occasion = (Spinner) findViewById(R.id.spinner);
        scrollView = (ScrollView) findViewById(R.id.scrollView1);



        //shipping
        fnshippingedittxt = (EditText) findViewById(R.id.shippingfirstnameedittext);
        lnshippingedittxt = (EditText) findViewById(R.id.shippinglastnameedittext);
        addressshippingedittxt = (EditText) findViewById(R.id.shippingemailedittext);
        contactnosshippingedittxt = (EditText) findViewById(R.id.shippingmobilenosedittext);
        cityshippingedittxt = (EditText) findViewById(R.id.shippingcityedittext);
        stateshippingedittxt = (EditText) findViewById(R.id.shippingstateedittext);
        pincodeshippingedittxt = (EditText) findViewById(R.id.shippingpincodeedittext);
        countryshippingedittxt = (EditText) findViewById(R.id.shippingcountryedittext);

        toshippingedittxt = (EditText) findViewById(R.id.toedittext);
        commentshippingedittxt = (EditText) findViewById(R.id.commentedittext);


        subtotaltxt = (TextView)findViewById(R.id.subtotalvaluetext);
        grandtotaltxt = (TextView)findViewById(R.id.grandtotalvaluetext);

        fnsenderedittxt.setText(SingletonActivity.custfirstname);
        lnsenderedittxt.setText(SingletonActivity.custlastname);

      //  fnsenderedittxt.setText(firstnamestr);
       // lnsenderedittxt.setText(lastnamestr);


        lnsenderedittxt.addTextChangedListener(this);
        emailsenderedittxt.addTextChangedListener(this);
      //  pwdedittxt.addTextChangedListener(this);
       // confirmpwdedittxt.addTextChangedListener(this);

        fnshippingedittxt.addTextChangedListener(this);
        lnshippingedittxt.addTextChangedListener(this);
        addressshippingedittxt.addTextChangedListener(this);
        contactnosshippingedittxt.addTextChangedListener(this);
        cityshippingedittxt.addTextChangedListener(this);
        stateshippingedittxt.addTextChangedListener(this);
        pincodeshippingedittxt.addTextChangedListener(this);
        countryshippingedittxt.addTextChangedListener(this);



        System.out.println("CUST ID from singleton IN CheckoutActivity IS----" + SingletonActivity.custidstr);
      //  System.out.println("CUST NAME from singleton IN CheckoutActivity IS----" + SingletonActivity.custnamestr);
        System.out.println("CUST FIRST NAME from singleton IN CheckoutActivity IS----" + SingletonActivity.custfirstname);
        System.out.println("CUST LAST NAME from singleton IN CheckoutActivity IS----" + SingletonActivity.custlastname);
        System.out.println("CUST EMAIL from singleton IN CheckoutActivity IS----" + SingletonActivity.custemailstr);

        System.out.println("CART PRODUCT NAME from singleton IN CheckoutActivity IS----" + SingletonActivity.nameofcartimg);
        System.out.println("SINGLE CART QUANTITY from singleton IN CheckoutActivity IS----" + SingletonActivity.singlecartqty);
        System.out.println("SINGLE CART PRICE from singleton IN CheckoutActivity IS----" + SingletonActivity.singlecartprice);
        System.out.println("TOTAL AMOUNT from singleton IN CheckoutActivity IS----" + SingletonActivity.totalamount);
        System.out.println("CART PRODUCT IMAGES from singleton IN CheckoutActivity IS----" + SingletonActivity.listofimgs);



        /*stringcustname = SingletonActivity.custnamestr;
       SingletonActivity.custnamestr = stringcustname;
      System.out.println("Cust name is in checkoutactivity" +stringcustname);*/



        subtotaltxt.setText(SingletonActivity.totalamount);
        grandtotaltxt.setText(SingletonActivity.totalamount);

     /*   if(stringcustname!=null) {
            String[] parts = stringcustname.split(" ");
            String custfirstname = parts[0]; // 004
            String custlastname = parts[1]; // 034556
            System.out.println("PART 1-----" + custfirstname);
            System.out.println("PART 2-----" + custlastname);


            fnsenderedittxt.setText(custfirstname);
                lnsenderedittxt.setText(custlastname);

        }*/

        fnsenderedittxt.addTextChangedListener(this);

        stk = (TableLayout) findViewById(R.id.table_main);
        init();

        sendgiftbtn = (Button)findViewById(R.id.sendgiftbutton);
        radioGroup = (RadioGroup)findViewById(R.id.radiobuttongroup);
      //  razorpayradiobutton = (RadioButton)findViewById(R.id.razorpayradiobtn);
        cbdradiobutton = (RadioButton)findViewById(R.id.cbdradiobtn);
        paytmradiobutton = (RadioButton)findViewById(R.id.paytmradiobtn);
        citrusradiobutton = (RadioButton)findViewById(R.id.citruspayradiobtn);
        mobikwikradiobutton = (RadioButton)findViewById(R.id.mobikwikradiobtn);
        paymenttxt = (TextView)findViewById(R.id.paymenttext);
        paymenttxt.setVisibility(View.INVISIBLE);

      //  pwdandconfpwdsenderlinear = (LinearLayout)findViewById(R.id.senderlineartwo);
       // pwdandconfpwdsenderlinear.setVisibility(View.INVISIBLE);


       /* checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (checkBox.isChecked()) {
                    pwdandconfpwdsenderlinear.setVisibility(View.VISIBLE);
                } else {
                    pwdandconfpwdsenderlinear.setVisibility(View.INVISIBLE);

                }

            }


        });*/

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup group, int checkedId) {
             paymenttxt.setVisibility(View.INVISIBLE);
             radioGroup.setSelected(true);

             switch(checkedId){
                 case R.id.razorpayradiobtn:
                     // do operations specific to this selection
              //       paymenttypestr = "razorpay";
              //  Toast.makeText(getApplicationContext(), "Razor Pay Clicked", Toast.LENGTH_LONG).show();
                     break;

                 case R.id.cbdradiobtn:
                     // do operations specific to this selection

                     paymenttypestr = "checkmo";
                             //   Toast.makeText(getApplicationContext(), "Cash Before Delivery Clicked", Toast.LENGTH_LONG).show();
                     mDialog = new Dialog(CheckoutActivity.this, R.style.AppTheme);
                     mDialog.setCancelable(false);
                     mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                     mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                     mDialog.setContentView(R.layout.cashbeforedelivery);

                     canceldialogcbdiv = (ImageView) mDialog.findViewById(R.id.cancel_cbddialog);
                     savecbdiv = (Button)mDialog.findViewById(R.id.savebutton);

                     namecbdedttxt = (EditText)mDialog.findViewById(R.id.nameedittext);
                     addresscbdedttxt = (EditText)mDialog.findViewById(R.id.AddressEditText);
                     citycbdedttxt = (EditText)mDialog.findViewById(R.id.cityedittext);
                     statecbdedttxt = (EditText)mDialog.findViewById(R.id.stateedittext);
                     pincbdedttxt = (EditText)mDialog.findViewById(R.id.pinedittext);
                     contactnumcbdedttxt = (EditText)mDialog.findViewById(R.id.contactnumberedittext);
                     countrycbdedttxt = (EditText)mDialog.findViewById(R.id.countryedittext);

                     if(namecbdedttxt.getText().toString().length()==0||addresscbdedttxt.toString().length()==0) {

                         strfnsenderedittxt = fnsenderedittxt.getText().toString();
                         System.out.println("Sender name in cbd dialog is" + strfnsenderedittxt);
                         namecbdedttxt.setText(strfnsenderedittxt);
                         strlnshipedittxt = lnshippingedittxt.getText().toString();
                         straddressshipedittxt = addressshippingedittxt.getText().toString();
                         addresscbdedttxt.setText(straddressshipedittxt);
                         strcontactnosshipedittxt = contactnosshippingedittxt.getText().toString();
                         contactnumcbdedttxt.setText(strcontactnosshipedittxt);
                         strcityshippingedittxt = cityshippingedittxt.getText().toString();
                         citycbdedttxt.setText(strcityshippingedittxt);
                         strstateshippingedittxt = stateshippingedittxt.getText().toString();
                         statecbdedttxt.setText(strstateshippingedittxt);
                         strpincodeshippingedittxt = pincodeshippingedittxt.getText().toString();
                         pincbdedttxt.setText(strpincodeshippingedittxt);
                         strcountryshippingedittxt = countryshippingedittxt.getText().toString();
                         countrycbdedttxt.setText(strcountryshippingedittxt);

                        namecbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         addresscbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         contactnumcbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         countrycbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         citycbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         statecbdedttxt.addTextChangedListener(CheckoutActivity.this);
                         pincbdedttxt.addTextChangedListener(CheckoutActivity.this);

                     }

                     savecbdiv.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                           //  mDialog.dismiss();

                             if ((namecbdedttxt.getText().toString().length()==0)) {
                                 namecbdedttxt.setError("Mandatory field");
                             }
                             if ((addresscbdedttxt.getText().toString().length()==0)) {
                                 addresscbdedttxt.setError("Mandatory field");
                             }
                             if ((contactnumcbdedttxt.getText().toString().length()==0)) {
                                 contactnumcbdedttxt.setError("Mandatory field");
                             }
                             if ((countrycbdedttxt.getText().toString().length()==0)) {
                                 countrycbdedttxt.setError("Mandatory field");
                             }
                             if ((citycbdedttxt.getText().toString().length()==0)) {
                                 citycbdedttxt.setError("Mandatory field");
                             }

                             if ((statecbdedttxt.getText().toString().length()==0)) {
                                 statecbdedttxt.setError("Mandatory field");
                             }

                             if ((pincbdedttxt.getText().toString().length()==0)) {
                                 pincbdedttxt.setError("Mandatory field");
                             }

                             else {
                                 //login();


                                 if ((namecbdedttxt.getText().toString().length()>0)) {
                                     namecbdedttxt.setError(null);
                                 }

                                 if ((addresscbdedttxt.getText().toString().length()>0)) {
                                     addresscbdedttxt.setError(null);
                                 }
                                 if ((contactnumcbdedttxt.getText().toString().length()>0)) {
                                     contactnumcbdedttxt.setError(null);
                                 }
                                 if ((countrycbdedttxt.getText().toString().length()>0)) {
                                     countrycbdedttxt.setError(null);
                                 }


                                 if ((citycbdedttxt.getText().toString().length()>0)) {
                                     citycbdedttxt.setError(null);
                                 }

                                 if ((statecbdedttxt.getText().toString().length()>0)) {
                                     statecbdedttxt.setError(null);
                                 }

                                 if ((pincbdedttxt.getText().toString().length()>0)) {
                                     pincbdedttxt.setError(null);
                                 }


                                     strnamecbdedttxt = namecbdedttxt.getText().toString();
                                     straddresscbdedttxt = addresscbdedttxt.getText().toString();
                                     strcitycbdedttxt = citycbdedttxt.getText().toString();
                                     strstatecbdedttxt = statecbdedttxt.getText().toString();
                                     strpincbdedttxt = pincbdedttxt.getText().toString();
                                     strcontactnumcbdedttxt = contactnumcbdedttxt.getText().toString();
                                     strcountrycbdedttxt = countrycbdedttxt.getText().toString();

                                 System.out.println("BILLING FIRST NAME IN CASH BEFORE DELIVERY CHECKOUT API IS---"+ strnamecbdedttxt);
                                 System.out.println("BILLING LAST NAME IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strnamecbdedttxt);
                                 System.out.println("BILLING LOCALITY IN CASH BEFORE DELIVERY CHECKOUT API IS---" + straddresscbdedttxt);
                                 System.out.println("BILLING CITY IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strcitycbdedttxt);
                                 System.out.println("BILLING REGION IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strstatecbdedttxt);
                                 System.out.println("BILLING COUNTRY ID IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strcountrycbdedttxt);
                                 System.out.println("BILLING POST CODE IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strpincbdedttxt);
                                 System.out.println("BILLING CONTACT NUMBER IN CASH BEFORE DELIVERY CHECKOUT API IS---" + strcontactnumcbdedttxt);



                                 //Save the data in backend
                                 mDialog.dismiss();

                             }

                         }
                     });


                     canceldialogcbdiv.setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View arg0) {
                             // TODO Auto-generated method stub
                             mDialog.dismiss();
                         }
                     });

                     mDialog.show();
                     break;

                 case R.id.paytmradiobtn:
                     // do operations specific to this selection
                     paymenttypestr = "paytm_cc";
                  //   Toast.makeText(getApplicationContext(), "Credit/Paytm Clicked", Toast.LENGTH_LONG).show();
                     break;

                 case R.id.citruspayradiobtn:
                     // do operations specific to this selection
                     Toast.makeText(getApplicationContext(), "Citrus Pay Clicked", Toast.LENGTH_LONG).show();
                     break;

                 case R.id.mobikwikradiobtn:
                     // do operations specific to this selection
                     Toast.makeText(getApplicationContext(), "Mobikwik wallet Clicked", Toast.LENGTH_LONG).show();
                     break;

             }

         }
     });



        sendgiftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strfnsenderedittxt =  fnsenderedittxt.getText().toString();
                strlnsenderedittxt =  lnsenderedittxt.getText().toString();
                stremailsenderedittxt =  emailsenderedittxt.getText().toString();

                SingletonActivity.sendercontactnos= stremailsenderedittxt;

               // strpwdedittxt =  pwdedittxt.getText().toString();
               // strconfirmpwdedittxt =  confirmpwdedittxt.getText().toString();

                strfnshipgedittxt =  fnshippingedittxt.getText().toString();
                strlnshipedittxt = lnshippingedittxt.getText().toString();
                straddressshipedittxt =  addressshippingedittxt.getText().toString();
                strcontactnosshipedittxt =  contactnosshippingedittxt.getText().toString();

                strcityshippingedittxt =  cityshippingedittxt.getText().toString();
                strstateshippingedittxt =  stateshippingedittxt.getText().toString();
                strpincodeshippingedittxt  = pincodeshippingedittxt.getText().toString();
                strcountryshippingedittxt  = countryshippingedittxt.getText().toString();

                strtoshippingedittxt  = toshippingedittxt.getText().toString();
                strcommentshippingedittxt  = commentshippingedittxt.getText().toString();



                boolean invalid = false;

             if (!(strfnsenderedittxt.length()>0)) {
                    fnsenderedittxt.setError("Mandatory field");

                }

                if ((strlnsenderedittxt.length()==0)) {
                    lnsenderedittxt.setError("Mandatory field");
                }

                if ((stremailsenderedittxt.length()==0)) {
                    emailsenderedittxt.setError("Mandatory field");
                }
/*
                if (!isValidEmail( stremailsenderedittxt)) {
                    invalid = true;
                    emailsenderedittxt.setError("Mandatory field");
                }*/
                /*if ((strpwdedittxt.length()==0)) {
                    pwdedittxt.setError("Mandatory field");
                }

                if (!(strpwdedittxt.equalsIgnoreCase(strconfirmpwdedittxt))){
                    confirmpwdedittxt.setError("Password not match");
                }*/

               /* if ((   strconfirmpwdedittxt.length()==0)) {
                    confirmpwdedittxt.setError("Mandatory field");
                }*/

                if ((strfnshipgedittxt.length()==0)) {
                    fnshippingedittxt.setError("Mandatory field");
                }

                if ((strlnshipedittxt.length()==0)) {
                    lnshippingedittxt.setError("Mandatory field");
                }

                if ((straddressshipedittxt.length()==0)) {
                    addressshippingedittxt.setError("Mandatory field");
                }

                if ((strcontactnosshipedittxt.length()==0)) {
                    contactnosshippingedittxt.setError("Mandatory field");
                }

                if ((strcityshippingedittxt.length()==0)) {
                    cityshippingedittxt.setError("Mandatory field");
                }

                if ((strstateshippingedittxt.length()==0)) {
                    stateshippingedittxt.setError("Mandatory field");
                }

                if ((strpincodeshippingedittxt.length()==0)) {
                    pincodeshippingedittxt.setError("Mandatory field");
                }
                if ((strcountryshippingedittxt.length()==0)) {
                    countryshippingedittxt.setError("Mandatory field");
                }



                if(radioGroup.isSelected()==false){

                    paymenttxt.setVisibility(View.VISIBLE);
                }
                else {
                    //  invalid = true;
                    paymenttxt.setVisibility(View.INVISIBLE);

                    if (paytmradiobutton.isChecked()&&strfnsenderedittxt.length()>0&&strlnsenderedittxt.length()!=0&&stremailsenderedittxt.length()!=0&&strfnshipgedittxt.length()!=0&&strlnshipedittxt.length()!=0&&straddressshipedittxt.length()!=0&&strcontactnosshipedittxt.length()!=0&&strcityshippingedittxt.length()!=0&&strstateshippingedittxt.length()!=0&&strpincodeshippingedittxt.length()!=0&&strcountryshippingedittxt.length()!=0){

                        strnamecbdedttxt = strfnshipgedittxt;
                        straddresscbdedttxt = straddressshipedittxt;
                        strcitycbdedttxt = strcityshippingedittxt;
                        strstatecbdedttxt = strstateshippingedittxt;
                        strpincbdedttxt = strpincodeshippingedittxt;
                        strcontactnumcbdedttxt = strcontactnosshipedittxt;
                        strcountrycbdedttxt = strcountryshippingedittxt;

                        if(flag==1) {
                            sendgiftbtn.setEnabled(false);
                            Log.d("Button clicked count", "called");
                            int pinkcolor = getResources().getColor(R.color.lightpink);
                            sendgiftbtn.setBackgroundColor(pinkcolor);

                        }


                        if(SingletonActivity.checkConnectivity(CheckoutActivity.this)){

                            checkoutOtherAPI();
                        }
                        else{
                            Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                            startActivity(intent);
                        }


                        flag=0;

                    }

                    if (cbdradiobutton.isChecked()&&strfnsenderedittxt.length()>0&&strlnsenderedittxt.length()!=0&&stremailsenderedittxt.length()!=0&&strfnshipgedittxt.length()!=0&&strlnshipedittxt.length()!=0&&straddressshipedittxt.length()!=0&&strcontactnosshipedittxt.length()!=0&&strcityshippingedittxt.length()!=0&&strstateshippingedittxt.length()!=0&&strpincodeshippingedittxt.length()!=0&&strcountryshippingedittxt.length()!=0){

                        if(flag==1) {
                            sendgiftbtn.setEnabled(false);
                            Log.d("Button clicked count", "called");
                            int pinkcolor = getResources().getColor(R.color.lightpink);
                            sendgiftbtn.setBackgroundColor(pinkcolor);

                        }



                        if(SingletonActivity.checkConnectivity(CheckoutActivity.this)){

                            checkoutAPI();
                        }
                        else{
                            Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                            startActivity(intent);
                        }


                        flag=0;





                    }

                }

            }

        });


        checkoutreviewlist  = (RelativeLayout)findViewById(R.id.reviewlistrelative);
       // stk.setVisibility(View.GONE);
        checkoutreviewlist.setVisibility(View.GONE);

        revieworderbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             Log.d("hi","hi1");
             Log.d("hi", "hi1");
                 if (checkoutreviewlist.getVisibility()==View.VISIBLE) {
                    checkoutreviewlist.setVisibility(View.GONE);
                     Log.d("hi", "hi2");
                }
             else {
                    checkoutreviewlist.setVisibility(View.VISIBLE);
                     Log.d("hi", "hi3");
                }

         }



     });


        editcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });



        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (CheckoutActivity.this, android.R.layout.select_dialog_item, giftoccasion);
        occasion.setAdapter(adapter);

        occasion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                occasionSpinnerSelectedStr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void checkoutAPI() {


        pdia = new ProgressDialog(CheckoutActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();

        String inAddressBookVal = "1";
        String shipcountryid = "India";
        String shipSaveinAddressBook = "1";
        String billingCountryId = "IN";

        System.out.println("CUSTOMER ID IN CHECKOUT API IS---" + SingletonActivity.custidstr);
        System.out.println("IN ADDRESS BOOK VALUE IN CHECKOUT API IS---" + inAddressBookVal);
        System.out.println("SHIP FIRST NAME IN CHECKOUT API IS---"+ strfnshipgedittxt);
        System.out.println("SHIP LAST NAME IN CHECKOUT API IS---"+ strlnshipedittxt);
        System.out.println("SHIP ADDRESS IN CHECKOUT API IS---"+ straddressshipedittxt);
        System.out.println("SHIP COUNTRY ID IN CHECKOUT API IS---"+ shipcountryid);
        System.out.println("SHIP CITY IN CHECKOUT API IS---"+ strcityshippingedittxt);
        System.out.println("SHIP REGION IN CHECKOUT API IS---"+ strstateshippingedittxt);
        System.out.println("SHIP POST CODE IN CHECKOUT API IS---"+ strpincodeshippingedittxt);
        System.out.println("SHIP TELEPHONE IN CHECKOUT API IS---"+ strcontactnosshipedittxt);
        System.out.println("GIFT RECEIVER IN CHECKOUT API IS---"+ strtoshippingedittxt);
        System.out.println("GIFT MESSAGE IN CHECKOUT API IS---"+ strcommentshippingedittxt);
        System.out.println("OCCASION TYPE IN CHECKOUT API IS---"+ occasionSpinnerSelectedStr);
        System.out.println("PAYMENT TYPE IN CHECKOUT API IS---"+ paymenttypestr);
        System.out.println("BILLING FIRST NAME IN CHECKOUT API IS---"+ strnamecbdedttxt);
        System.out.println("BILLING LAST NAME IN CHECKOUT API IS---"+ strnamecbdedttxt);
        System.out.println("BILLING STREET IN CHECKOUT API IS---"+ straddresscbdedttxt);
        System.out.println("BILLING LOCALITY IN CHECKOUT API IS---"+ straddresscbdedttxt);
        System.out.println("BILLING CITY IN CHECKOUT API IS---" + strcitycbdedttxt);
        System.out.println("BILLING REGION IN CHECKOUT API IS---" + strstatecbdedttxt);
        System.out.println("BILLING COUNTRY ID IN CHECKOUT API IS---" + billingCountryId);
        System.out.println("BILLING POST CODE IN CHECKOUT API IS---" + strpincbdedttxt);
        System.out.println("BILLING TELEPHONE NO. IN CHECKOUT API IS---" + strcontactnumcbdedttxt);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,

                SingletonActivity.API_URL+"api/checkout_new.php?customer_id="+SingletonActivity.custidstr+"&billingfirstname="+strnamecbdedttxt+"&billinglastname="+strnamecbdedttxt
                        +"&billingaddress="+straddresscbdedttxt+"&billingcity="+strcitycbdedttxt+"&billingregion="+strstatecbdedttxt+"&billingpostcode="+strpincbdedttxt+"&billingtelephone="+strcontactnumcbdedttxt+"&billing_country_id="
                        +billingCountryId+"&save_in_address_book="+shipSaveinAddressBook+"&shipfirstname="+strfnshipgedittxt+"&shiplastname="+strlnshipedittxt+"&shipaddress="
                        +straddressshipedittxt+"&shipcity="+strcityshippingedittxt+"&shipstate="+strstateshippingedittxt+"&shippostcode="+strpincodeshippingedittxt+"&shiptelephone="
                        +strcontactnosshipedittxt+"&shipcountry_id="+shipcountryid+"&shipsave_in_address_book="+shipSaveinAddressBook+"&gift_reciver="+strtoshippingedittxt+"&gift_message="+strcommentshippingedittxt+"&occassion_type="+occasionSpinnerSelectedStr+"&payment_method="
                        +paymenttypestr
                ,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        pdia.dismiss();
                        System.out.println("RESPONSE IN CHECKOUT API IS-------" + response);


                        try {


                            String orderid = response.getString("order_id");
                            String payment_method = response.getString("payment_method");

                            System.out.println("ORDER ID IN CHECKOUT API  IS" + orderid);
                            System.out.println("PAYMENT METHOD IN CHECKOUT API  IS" + payment_method);

                            if(payment_method.equalsIgnoreCase("checkmo")){

                                Intent i = new Intent(CheckoutActivity.this,CheckOutOrderIdActivity.class);
                                i.putExtra("OrderId",orderid);
                                startActivity(i);
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

              /*  params.put("name", username);
                params.put("pwd",password);
                params.put("email", email);*/
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void checkoutOtherAPI() {


        pdia = new ProgressDialog(CheckoutActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();

        String inAddressBookVal = "1";
        String shipcountryid = "India";
        String shipSaveinAddressBook = "1";
        String billingCountryId = "IN";

        System.out.println("CUSTOMER ID IN CHECKOUT OTHER API IS---" + SingletonActivity.custidstr);
        System.out.println("IN ADDRESS BOOK VALUE IN CHECKOUT OTHER API IS---" + inAddressBookVal);
        System.out.println("SHIP FIRST NAME IN CHECKOUT OTHER API IS---"+ strfnshipgedittxt);
        System.out.println("SHIP LAST NAME IN CHECKOUT OTHER API IS---"+ strlnshipedittxt);
        System.out.println("SHIP ADDRESS IN CHECKOUT OTHER API IS---"+ straddressshipedittxt);
        System.out.println("SHIP COUNTRY ID IN CHECKOUT OTHER API IS---"+ shipcountryid);
        System.out.println("SHIP CITY IN CHECKOUT OTHER API IS---"+ strcityshippingedittxt);
        System.out.println("SHIP REGION IN CHECKOUT OTHER API IS---"+ strstateshippingedittxt);
        System.out.println("SHIP POST CODE IN CHECKOUT OTHER API IS---"+ strpincodeshippingedittxt);
        System.out.println("SHIP TELEPHONE IN CHECKOUT OTHER API IS---"+ strcontactnosshipedittxt);
        System.out.println("GIFT RECEIVER IN CHECKOUT OTHER API IS---"+ strtoshippingedittxt);
        System.out.println("GIFT MESSAGE IN CHECKOUT OTHER API IS---"+ strcommentshippingedittxt);
        System.out.println("OCCASION TYPE IN CHECKOUT OTHER API IS---"+ occasionSpinnerSelectedStr);
        System.out.println("PAYMENT TYPE IN CHECKOUT OTHER API IS---"+ paymenttypestr);
        System.out.println("BILLING FIRST NAME IN CHECKOUT OTHER API IS---"+ strnamecbdedttxt);
        System.out.println("BILLING LAST NAME IN CHECKOUT OTHER API IS---"+ strnamecbdedttxt);
        System.out.println("BILLING STREET IN CHECKOUT OTHER API IS---"+ straddresscbdedttxt);
        System.out.println("BILLING LOCALITY IN CHECKOUT OTHER API IS---"+ straddresscbdedttxt);
        System.out.println("BILLING CITY IN CHECKOUT OTHER API IS---" + strcitycbdedttxt);
        System.out.println("BILLING REGION IN CHECKOUT OTHER API IS---" + strstatecbdedttxt);
        System.out.println("BILLING COUNTRY ID IN CHECKOUT OTHER API IS---" + billingCountryId);
        System.out.println("BILLING POST CODE IN CHECKOUT OTHER API IS---" + strpincbdedttxt);
        System.out.println("BILLING TELEPHONE NO. IN CHECKOUT OTHER API IS---" + strcontactnumcbdedttxt);

        System.out.println("URL FOR PAYTM_CC IN CHECKOUT API IS------->>>" + SingletonActivity.API_URL+"api/checkout_new.php?customer_id="+SingletonActivity.custidstr+"&save_in_address_book="+inAddressBookVal+"&shipfirstname="+strfnshipgedittxt
                +"&shiplastname="+strlnshipedittxt+"&shipaddress="+straddressshipedittxt+"&shipcity="+strcityshippingedittxt+"&shipstate="+strstateshippingedittxt+"&shippostcode="+strpincodeshippingedittxt+"&shiptelephone="
                +strcontactnosshipedittxt+"&shipcountry_id="+shipcountryid+"&shipsave_in_address_book="+shipSaveinAddressBook+"&gift_reciver="+strtoshippingedittxt+"&gift_message="
                +strcommentshippingedittxt+"&occassion_type="+occasionSpinnerSelectedStr+"&payment_method="+paymenttypestr);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
             SingletonActivity.API_URL+"api/checkout_new.php?customer_id="+SingletonActivity.custidstr+"&save_in_address_book="+inAddressBookVal+"&shipfirstname="+strfnshipgedittxt
                        +"&shiplastname="+strlnshipedittxt+"&shipaddress="+straddressshipedittxt+"&shipcity="+strcityshippingedittxt+"&shipstate="+strstateshippingedittxt+"&shippostcode="+strpincodeshippingedittxt+"&shiptelephone="
                        +strcontactnosshipedittxt+"&shipcountry_id="+shipcountryid+"&shipsave_in_address_book="+shipSaveinAddressBook+"&gift_reciver="+strtoshippingedittxt+"&gift_message="
                        +strcommentshippingedittxt+"&occassion_type="+occasionSpinnerSelectedStr+"&payment_method="+paymenttypestr


                ,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        pdia.dismiss();
                        System.out.println("RESPONSE IN CHECKOUT API IS-------" + response);



                        try {


                            String orderid = response.getString("order_id");
                            String payment_method = response.getString("payment_method");

                            System.out.println("ORDER ID IN CHECKOUT API  IS" + orderid);
                            System.out.println("PAYMENT METHOD IN CHECKOUT API  IS" + payment_method);


                            if(payment_method.equalsIgnoreCase("paytm_cc")){

                                Intent i = new Intent(CheckoutActivity.this,MerchantActivity.class);
                                i.putExtra("OrderId",orderid);
                                startActivity(i);

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

              /*  params.put("name", username);
                params.put("pwd",password);
                params.put("email", email);*/
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }*/

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void init() {

     //  final TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        for (int i = 0; i < SingletonActivity.nameofcartimg.size(); i++) {
            // TableRow tbrow = new TableRow(this);

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            final RelativeLayout row = (RelativeLayout) inflater.inflate(R.layout.checkout_row, null);

            TextView productname = (TextView) row.findViewById(R.id.imagenametext);
            TextView singleprice = (TextView) row.findViewById(R.id. subtotalvallist);
            EditText edittxtqty = (EditText) row.findViewById(R.id.qtyedittext);
             ImageView checkoutimg = (ImageView) row.findViewById(R.id.revieworderimg);

            String cartProductName;
            if (SingletonActivity.nameofcartimg.get(i).length() >= 22) {
                cartProductName = SingletonActivity.nameofcartimg.get(i).substring(0, 22) + "...";

               // SingletonActivity.nameofcartimg = namecartproductdetails;
            } else {

                cartProductName = SingletonActivity.nameofcartimg.get(i);
             //   SingletonActivity.nameofcartimg = namecartproductdetails;

            }


           // productname.setText(SingletonActivity.nameofcartimg.get(i));
            productname.setText(cartProductName);
            singleprice.setText(SingletonActivity.singlecartprice.get(i));
            edittxtqty.setText(SingletonActivity.singlecartqty.get(i));
            //  cartimg.setImageResource(Integer.parseInt(android_image_urls.toString()));

            Picasso.with(CheckoutActivity.this).load(SingletonActivity.listofimgs.get(i)).placeholder(R.drawable.loading)
                        .fit().into(checkoutimg);

            //     textView.setText(namecartproductdetails.get(2));

            stk.addView(row, i);

          /*  productname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_LONG).show();
                }
            });*/

        }

    }



    //For edittext setError Condition using TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (fnsenderedittxt.getText().toString().length()>0) {
            fnsenderedittxt.setError(null);
        }

        if (lnsenderedittxt.getText().toString().length()>0) {
            lnsenderedittxt.setError(null);
        }

        if (emailsenderedittxt.getText().toString().length()>0) {
            emailsenderedittxt.setError(null);
        }

       /* if ( pwdedittxt.getText().toString().length()>0) {
            pwdedittxt.setError(null);
        }

         if ( confirmpwdedittxt.getText().toString().length()>0) {
            confirmpwdedittxt.setError(null);
        }*/

        if (fnshippingedittxt.getText().toString().length()>0) {
            fnshippingedittxt.setError(null);
        }

        if (lnshippingedittxt.getText().toString().length()>0) {
            lnshippingedittxt.setError(null);
        }

        if ( addressshippingedittxt.getText().toString().length()>0) {
            addressshippingedittxt.setError(null);
        }

        if (contactnosshippingedittxt.getText().toString().length()>0) {
            contactnosshippingedittxt.setError(null);
        }

        if ( cityshippingedittxt.getText().toString().length()>0) {
            cityshippingedittxt.setError(null);
        }

        if ( stateshippingedittxt.getText().toString().length()>0) {
            stateshippingedittxt.setError(null);
        }

        if (pincodeshippingedittxt.getText().toString().length()>0) {
            pincodeshippingedittxt.setError(null);
        }
        if (countryshippingedittxt.getText().toString().length()>0) {
            countryshippingedittxt.setError(null);
        }


    /*   if ((namecbdedttxt.getText().toString().length()>0)) {
            namecbdedttxt.setError(null);
        }

     if ((addresscbdedttxt.getText().toString().length()>0)) {
            addresscbdedttxt.setError(null);
        }
        if ((contactnumcbdedttxt.getText().toString().length()>0)) {
            contactnumcbdedttxt.setError(null);
        }
        if ((countrycbdedttxt.getText().toString().length()>0)) {
             countrycbdedttxt.setError(null);
        }


        if ((citycbdedttxt.getText().toString().length()>0)) {
            citycbdedttxt.setError(null);
        }

        if ((statecbdedttxt.getText().toString().length()>0)) {
            statecbdedttxt.setError(null);
        }

        if ((pincbdedttxt.getText().toString().length()>0)) {
            pincbdedttxt.setError(null);
        }*/


        /*if ( dialogemail.getText().toString().length()>0) {
            dialogemail.setError(null);
        }*/

       /* if ( dialogpwd.getText().toString().length()>0) {
            dialogpwd.setError(null);
        }*/

      /*  if (forgotpwddialogemail.getText().toString().length()>0) {
            forgotpwddialogemail.setError(null);
        }*/




    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    //For Tabs
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) CheckoutActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(CheckoutActivity.this.getComponentName()));
            handleIntent(getIntent());
        }
        return true;
    }



    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("query....", "query...." + query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Typeface face = Typeface.createFromAsset(getApplication().getAssets(),"fonts/OpenSans-Regular.ttf");    //  THIS

        if (id == R.id.action_login) {

            Intent intent = new Intent(CheckoutActivity.this, Login.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_terms) {
            Intent in = new Intent(this, TermsandCondition.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);

    }

}





/* loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog = new Dialog(CheckoutActivity.this, R.style.AppTheme);
                mDialog.setCancelable(false);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.setContentView(R.layout.login_dialog_box);

                cancel_dialog_login = (ImageView) mDialog.findViewById(R.id.cancel_logindialog);
                dialogemail = (EditText) mDialog.findViewById(R.id.dialogemailedittxt);
                dialogpwd = (EditText) mDialog.findViewById(R.id.dialogpwdedittext);
                forgotpwddialog = (TextView) mDialog.findViewById(R.id.forgotpwd);

                dialogemail.addTextChangedListener(CheckoutActivity.this);
                dialogpwd.addTextChangedListener(CheckoutActivity.this);

                dialogloginbtn = (Button) mDialog.findViewById(R.id.login_btn);

                dialogloginbtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        strdialogmailedittxt = dialogemail.getText().toString();
                        strdialogpwdedittxt = dialogpwd.getText().toString();

                        if ((strdialogmailedittxt.length() == 0)) {
                            dialogemail.setError("Mandatory field");
                        }
                        if ((strdialogpwdedittxt.length() == 0)) {
                            dialogpwd.setError("Mandatory field");
                        } else {
                            login();
                        }
*//*
                        else if((strdialogmailedittxt.length()>0) && (strdialogpwdedittxt.length()>0)){


                            mDialog.dismiss();
                        }*//*

                        // Toast.makeText(getApplicationContext(),"hi hello how r u",Toast.LENGTH_LONG).show();
                    }
                });

                cancel_dialog_login.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });

                forgotpwddialog.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog = new Dialog(CheckoutActivity.this, R.style.AppTheme);
                        dialog.setCancelable(false);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.forgot_pwd_dialog);

                        cancel_dialog_forgotpwd = (ImageView) dialog.findViewById(R.id.cancel_forgotpwddialog);
                        forgotpwddialogemail = (EditText) dialog.findViewById(R.id.secdiaemailedittxt);
                        dialogsubmitbtn = (Button) dialog.findViewById(R.id.submit_btn);
                        backtologinsecdialog = (TextView) dialog.findViewById(R.id.backtologin);

                        forgotpwddialogemail.addTextChangedListener(CheckoutActivity.this);


                        backtologinsecdialog.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();

                            }
                        });

                        dialogsubmitbtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                boolean invalid = false;
                                strforgotpwddialogemail = forgotpwddialogemail.getText().toString();


                                if (!isValidEmail(strforgotpwddialogemail)) {
                                    invalid = true;
                                    forgotpwddialogemail.setError("Mandatory field");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Check your mail to reset your password and login here.", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }

                              *//*  else if (strforgotpwddialogemail.length() > 0){
                                    Toast.makeText(getApplicationContext(), "Check your mail to reset your password.", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                }*//*

                            }
                        });


                        cancel_dialog_forgotpwd.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                mDialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                mDialog.show();

            }
        });*/