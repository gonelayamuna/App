package com.example.user.smgapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SMG A Block on 7/2/2016.
 */
public class MyOrderActivity extends NavigationDrawer {

    ListView myOrderslistView;
    ProgressDialog pdia;
    String custid;
    CustomAdap customadap;
    ArrayList<String> orderidvalarraylist = new ArrayList<String>();
    ArrayList<String> orderdatevalarraylist = new ArrayList<String>();
    ArrayList<String> totalvaluearraylist = new ArrayList<String>();
    ArrayList<String> statusvalrraylist = new ArrayList<String>();
    TextView app_title,noorderfoundtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.myorders);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.myorders, null, false);
        drawer.addView(contentView, 0);
        app_title = (TextView) findViewById(R.id.app_title);

        noorderfoundtextview = (TextView) findViewById(R.id.noorderfoundtxtvw);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_title.setText("My Orders");


        orderidvalarraylist.clear();
        orderdatevalarraylist.clear();

        totalvaluearraylist.clear();
        statusvalrraylist.clear();

        myOrderslistView = (ListView)findViewById(R.id.my_orders_list);


        custid = SingletonActivity.custidstr;
       // custid = "116";

        System.out.println("CUSTOMER ID IN MY ORDERS ACTIVITY-------" + custid);

        if (SingletonActivity.custidstr.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderActivity.this);
            builder.setTitle("Alert!");
            builder.setMessage("Ur not logged in")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           Intent intent = new Intent(getApplicationContext(), Login.class);
                           startActivity(intent);
                       }


                   });
            // Create the AlertDialog object and return it
            builder.show();
        } else {

            {

                if(SingletonActivity.checkConnectivity(MyOrderActivity.this)){

                    MyOrdersAPI(custid);
                }
                else{
                    Intent intent=new Intent(getApplicationContext(),NoInternet.class);
                    startActivity(intent);
                }



            }





        }



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void MyOrdersAPI(String customerid) {

        pdia = new ProgressDialog(MyOrderActivity.this);
        pdia.setMessage("Please Wait...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();



        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL+"api/my_orders.php?customer_id="+customerid, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        pdia.dismiss();

                        System.out.println("RESPONSE OF MY ORDERS API IS----" + response);
                        try {


                            String issuccessstr = response.getString("isSuccess");
                            System.out.println("IS SUCCESS STRING IN MY ORDERS API IS-----" +issuccessstr);

                            if(issuccessstr.equalsIgnoreCase("no records!")){
                                noorderfoundtextview.setVisibility(View.VISIBLE);

                            }

                            JSONArray resultjsonArray = response.getJSONArray("result");
                            System.out.println("RESULT JSON ARRAY IN MY ORDERS API IS----" +resultjsonArray);

                            for(int i = 0; i < resultjsonArray.length(); i++)
                            {
                                JSONObject resultjsonobj = resultjsonArray.getJSONObject(i);
                                System.out.println("RESULT JSON OBJECT IN MY ORDERS API IS----" + resultjsonobj);

                                String orderidstr = resultjsonobj.getString("order_id");
                                String orderdatestr = resultjsonobj.getString("order_date");
                                String totalstr = resultjsonobj.getString("total");
                                String statusstr = resultjsonobj.getString("status");

                                System.out.println("ORDER ID VALUE IN MY ORDERS API IS----" + orderidstr);
                                System.out.println("ORDER DATE VALUE IN MY ORDERS API IS----" + orderdatestr);
                                System.out.println("TOTAL VALUE IN MY ORDERS API IS----" + totalstr);
                                System.out.println("STATUS VALUE IN MY ORDERS API IS----" + statusstr);

                                String formatdate = orderdatestr.substring(0, 10);


                                //input date format
                                SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

                               //output date format
                                SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = dFormat.parse(formatdate);
                                String ddate = dFormatFinal.format(date);




                                String plaintotalstrtext = Html.fromHtml(totalstr).toString();
                              String totalwithoutdecimal = plaintotalstrtext.replaceAll("\\.0*$","");


                                orderidvalarraylist.add(resultjsonArray.getJSONObject(i).getString("order_id"));
                               // orderdatevalarraylist.add(resultjsonArray.getJSONObject(i).getString("order_date").substring(0, 10));
                                orderdatevalarraylist.add(ddate);
                             //   totalvaluearraylist.add(Html.fromHtml(resultjsonArray.getJSONObject(i).getString("total")).toString());
                                totalvaluearraylist.add(totalwithoutdecimal);
                                statusvalrraylist.add(resultjsonArray.getJSONObject(i).getString("status"));
                            }


                            customadap = new CustomAdap(MyOrderActivity.this,
                                    orderidvalarraylist, orderdatevalarraylist, totalvaluearraylist, statusvalrraylist);
                            myOrderslistView.setAdapter(customadap);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {

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
         /*   params.put("phone", phone);
            params.put("name", username);
            params.put("pwd",password);
            params.put("email", email);*/
                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);
    }





    public class CustomAdap extends BaseAdapter {
        private ArrayList<String> data;
        private Context c;
        private LayoutInflater inflater = null;
        private ArrayList<String> ids;
        private ArrayList<String> tids;

        public CustomAdap(Context mainActivity, ArrayList<String> details,
                          ArrayList<String> ids, ArrayList<String> tids, ArrayList<String> picupctylist) {
            // TODO Auto-generated constructor stub
            this.data = details;
            this.c = mainActivity;
            inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.ids = ids;
            this.tids = tids;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
         //   return 5;

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub

            final Holder holder = new Holder();
            final View rowView;
            // final Spinner spinner1;

            rowView = inflater.inflate(R.layout.myorders_row, null);

            holder.orderidtxtvw = (TextView) rowView.findViewById(R.id.orderidtxtval);
            holder.orderdatetxtvw = (TextView) rowView.findViewById(R.id.orderdatetxtval);
            holder.totaltxtvw = (TextView) rowView.findViewById(R.id.ordertotaltxtval);
            holder.statustxtvw = (TextView) rowView.findViewById(R.id.orderstatustxtval);

            holder.orderidtxtvw.setText(orderidvalarraylist.get(position));
            holder.orderdatetxtvw.setText(orderdatevalarraylist.get(position));
            holder.totaltxtvw.setText(totalvaluearraylist.get(position));
            holder.statustxtvw.setText(statusvalrraylist.get(position));




            return rowView;
        }

    }

    public class Holder {
        TextView orderidtxtvw ;
        TextView orderdatetxtvw;
        TextView totaltxtvw;
        TextView statustxtvw ;



    }
}
