package com.example.user.smgapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends NavigationDrawer {

    Button registrationbtn;
    static String firstnamestr, lastnamestr, emailaddressstr, mobilestr, passwordstr, confirmpwdstr, RESULT;
    EditText firstnameedttxt, lastnameedttxt, emailedttxt, mobileedttxt, pwdedttxt, confrmpwdedttxt;
    RegistrationModel regmodel;
    TextView app_title;
    Context context;
    Dialog registration_dialog;
    UtilsDialog util = new UtilsDialog();
    ImageView close_reg_dialog;
    Button submit_regdialog_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_registration);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_registration, null, false);
        drawer.addView(contentView, 0);
        app_title = (TextView) findViewById(R.id.app_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title.setText("Register");

        firstnameedttxt = (EditText) findViewById(R.id.firstnameedittxt);
        lastnameedttxt = (EditText) findViewById(R.id.lastnameedttxt);
        emailedttxt = (EditText) findViewById(R.id.emailedttxt);
        mobileedttxt = (EditText) findViewById(R.id.mobileedttxt);
        pwdedttxt = (EditText) findViewById(R.id.passwordedttxt);
        confrmpwdedttxt = (EditText) findViewById(R.id.confirmpwdedttxt);
        registrationbtn = (Button) findViewById(R.id.registerbtn);

        registrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstnamestr = firstnameedttxt.getText().toString();
                lastnamestr = lastnameedttxt.getText().toString();
                emailaddressstr = emailedttxt.getText().toString();
                mobilestr = mobileedttxt.getText().toString();
                passwordstr = pwdedttxt.getText().toString();
                confirmpwdstr = confrmpwdedttxt.getText().toString();

                boolean invalid = false;


                if (!isFirstName(firstnamestr)) {

                    firstnameedttxt.setError("Enter first name");
                    lastnameedttxt.setError(null);
                    emailedttxt.setError(null);
                    mobileedttxt.setError(null);
                    pwdedttxt.setError(null);
                    confrmpwdedttxt.setError(null);

                } else if (!isLastName(lastnamestr)) {
                    invalid = true;
                    firstnameedttxt.setError(null);
                    lastnameedttxt.setError("Enter last name");
                    emailedttxt.setError(null);
                    mobileedttxt.setError(null);
                    pwdedttxt.setError(null);
                    confrmpwdedttxt.setError(null);

                } else if (!isValidEmail(emailaddressstr)) {
                    invalid = true;
                    firstnameedttxt.setError(null);
                    lastnameedttxt.setError(null);
                    emailedttxt.setError("Invalid Email");
                    mobileedttxt.setError(null);
                    pwdedttxt.setError(null);
                    confrmpwdedttxt.setError(null);
                } else if (!isValidMobile(mobilestr)) {
                    invalid = true;
                    firstnameedttxt.setError(null);
                    lastnameedttxt.setError(null);
                    emailedttxt.setError(null);
                    mobileedttxt.setError("Invalid Mobile");

                } else if (!isValidPassword(passwordstr)) {
                    invalid = true;
                    firstnameedttxt.setError(null);
                    lastnameedttxt.setError(null);
                    emailedttxt.setError(null);
                    mobileedttxt.setError(null);
                    pwdedttxt.setError("Password length should be more than 3 digits");
                } else if (!(confirmpwdstr.equalsIgnoreCase(passwordstr))) {
                    invalid = true;
                    firstnameedttxt.setError(null);
                    lastnameedttxt.setError(null);
                    emailedttxt.setError(null);
                    mobileedttxt.setError(null);
                    pwdedttxt.setError(null);
                    confrmpwdedttxt.setError("Password not matching");
                } else if (invalid == false) {

                    if(SingletonActivity.checkConnectivity(RegistrationActivity.this)){

                        new RegistrationAPI()
                                .execute(SingletonActivity.API_URL + "api/register.php?email=" + emailaddressstr + "&&fname=" + firstnamestr + "&&lname=" + lastnamestr + "&&hash=" + passwordstr + "&&mobile=" + mobilestr);


                    }
                    else{
                        Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent1);
                    }


                }


            }
        });


    }


    private class RegistrationAPI extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... urls) {

            regmodel = new RegistrationModel();


            return POST(urls[0], regmodel);
        }

        public String POST(String url, RegistrationModel registrationModel) {
            InputStream inputStream = null;

            System.out.println("URL IS----------" + url);

            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                Log.d("URL", url);

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();


                System.out.println("EMAIL IS----------" + emailaddressstr);
                System.out.println("FNAME IS----------" + firstnamestr);
                System.out.println("LNAME IS----------" + lastnamestr);
                System.out.println("HASH IS----------" + passwordstr);
                System.out.println("MOBILE IS----------" + mobilestr);


                jsonObject.accumulate("email", emailaddressstr);
                jsonObject.accumulate("fname", firstnamestr);
                jsonObject.accumulate("lname", lastnamestr);
                jsonObject.accumulate("hash", passwordstr);
                jsonObject.accumulate("mobile", mobilestr);


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin
                // Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the
                // content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if (inputStream != null)
                    RESULT = convertInputStreamToString(inputStream);

                else
                    RESULT = "Did not work!";
                Log.d("Registration API result",
                        "Registration API result" + RESULT);

            } catch (Exception e) {
                // Log.e("InputStream", e.getLocalizedMessage());
            }

            Log.e("Result....", "Result.." + RESULT);


            return RESULT;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            System.out.println("REGISTRATION API RESPONSE IS---->" + result);


            try {
                JSONObject mainObject = new JSONObject(result);

                String isSuccessString = mainObject.getString("isSuccess");

                JSONObject resultjson = mainObject.getJSONObject("result");

                String messagestr = resultjson.getString("message");

                if (messagestr.equalsIgnoreCase("Already Exist.")) {


                  util.dialog(RegistrationActivity.this, "Email already exists");


                } else {

                    String idstr = resultjson.getString("id");
                    System.out.println("ID IN REGISTRATION ACTIVITY IS--->" + idstr);

                    SingletonActivity.custidstr=idstr;

                    SharedPreferences.Editor editor = getSharedPreferences(
                            MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putBoolean("loginlogoutkey", true);
                    editor.putString("customerid", SingletonActivity.custidstr);


                    editor.commit();

                    registration_dialog = new Dialog(RegistrationActivity.this, R.style.AppTheme);
                    registration_dialog.setCancelable(false);
                    registration_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    registration_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    registration_dialog.setContentView(R.layout.custom_regdialog);
                    submit_regdialog_btn= (Button) registration_dialog.findViewById(R.id.ok_button);

                    submit_regdialog_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registration_dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                        }
                    });
                    registration_dialog.show();

                  /*  AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle("Alert!");
                    builder.setMessage("Registered Successfully")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                }


                            });
                    // Create the AlertDialog object and return it
                    builder.show();*/

                   // util.dialog(RegistrationActivity.this, "Registered Successfully");

                  /*  Intent i = new Intent(RegistrationActivity.this,HomePage.class);
                    startActivity(i);*/



                 /*   Intent i = new Intent(RegistrationActivity.this,Login.class);
                    startActivity(i);*/

                   /* finish();


                    Intent i = new Intent(RegistrationActivity.this,HomePage.class);
                    startActivity(i);*/


                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Exception" + e);
            }


        }


    }

    private String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private boolean isValidMobile(String mobile) {
        if (mobile != null && mobile.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean isFirstName(String name) {

        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
    }

    private boolean isLastName(String name) {

        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
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
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }


}
