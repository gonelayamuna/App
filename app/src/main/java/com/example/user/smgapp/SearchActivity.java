package com.example.user.smgapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    EditText searchedittxt;
    String searchstr;
    Button searchbtn;
    ListView listView;
    GridView gridView;
    ArrayList<HashMap<String, String>> searchlist  = new ArrayList<HashMap<String, String>>();
    ProgressDialog pdia;
    String customerid;
    TextView noitemfoundtxtvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        customerid = SingletonActivity.custidstr;
        System.out.println("CUST ID from singleton IN SearchActivity IS----" + customerid);

      // searchlist.clear();

      //  listView = (ListView)findViewById(R.id.listview1);
        gridView = (GridView)findViewById(R.id.gridView11);

        searchedittxt = (EditText)findViewById(R.id.searchEdittextNew);

        noitemfoundtxtvw = (TextView)findViewById(R.id.noitemfoundtxtvw);


        searchedittxt.setText("   ");
       // searchedittxt.setText("   ");
        searchedittxt.setSelection(2);
      //  searchedittxt.requestFocus(searchedittxt.getText().length() / 5);
       // searchedittxt.setSelection(searchedittxt.getText().length() / 4);

        searchedittxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchedittxt.getRight() - searchedittxt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here


                        searchlist.clear();
                        searchstr = searchedittxt.getText().toString();

                    //    String searchreplacestr = searchstr.replaceAll("","%20");

                        if(SingletonActivity.checkConnectivity(SearchActivity.this)){

                            searchAPI(searchstr, customerid);

                        }
                        else{
                            Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
                            startActivity(intent1);
                        }



                        return true;
                    }
                }

                if(event.getRawX() <= (searchedittxt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())){
                    finish();
                }

                return false;
            }
        });


      /*  searchedittxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchedittxt.getLeft() - searchedittxt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        // your action here
                       finish();
                    }
                }
                return false;
            }
        });
*/
        /*AdapterView.OnItemClickListener itemClickListener_list = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {

                *//*LinearLayout linearLayoutParent = (LinearLayout) container;
                RelativeLayout linearLayoutChild = (RelativeLayout) linearLayoutParent.getChildAt(1);

                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
                tvCountry.setGravity(Gravity.CENTER_VERTICAL);*//*

                Log.e("ur detail id..", "details id.." + searchlist.get(position).get("product_id"));
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("product_id", searchlist.get(position).get("product_id"));
                // intent.putExtra("special_price",list.get(position).get("product_id"));
                startActivity(intent);


            }
        };*/
        AdapterView.OnItemClickListener itemClickListener_grid = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {


                Log.e("ur detail id..", "details id.." + searchlist.get(position).get("product_id"));
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("product_id", searchlist.get(position).get("product_id"));
                // intent.putExtra("special_price",list.get(position).get("product_id"));
                startActivity(intent);

            }
        };

       // listView.setOnItemClickListener(itemClickListener_list);
        gridView.setOnItemClickListener(itemClickListener_grid);


    }


    private void searchAPI(String search,String custid) {

    pdia = new ProgressDialog(SearchActivity.this);
	pdia.setMessage("Please Wait...");
	pdia.setCanceledOnTouchOutside(false);
	pdia.show();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                SingletonActivity.API_URL+"api/search.php?q="+search+"&customer_id="+custid, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        pdia.dismiss();


                        try {


                            String responsecode = response.getString("responseCode");
                            String messagestr = response.getString("msg");
                      /*      String keywordstr = response.getString("keyword");
                            String currencystr = response.getString("currency");
*/

                            System.out.println("RESPONSE OF SEARCH API IS----" +response);
                            System.out.println("RESPONSE CODE STRING IS----" +responsecode);
                            System.out.println("MESSAGE STRING IS----" +messagestr);

                            if(messagestr.equalsIgnoreCase("No Record Found")){

                                gridView.setVisibility(View.GONE);
                                noitemfoundtxtvw.setVisibility(View.VISIBLE);

                            }
                            else {
                                gridView.setVisibility(View.VISIBLE);
                                noitemfoundtxtvw.setVisibility(View.INVISIBLE);
                            }


                            JSONArray jsonArray = response.getJSONArray("searchProdResult");
                            System.out.println("SEARCH PRODUCT RESULT ARRAY JSON ARRAY IS----" +jsonArray);

                        //    JSONObject mainObject = jsonArray.getJSONObject(0);
                       //     System.out.println("SEARCH PRODUCT MAIN OBJECT IS----" +mainObject);


                            for(int i = 0 ; i < jsonArray.length(); i++){

                                HashMap<String, String> Product_map = new HashMap<>();
                                Product_map.put("product_id", jsonArray.getJSONObject(i).getString("productId"));
                                Product_map.put("product_name", jsonArray.getJSONObject(i).getString("productName"));
                                Product_map.put("product_image", jsonArray.getJSONObject(i).getString("thumbnailUrl"));
                                Product_map.put("product_price", jsonArray.getJSONObject(i).getString("regularPrice"));
                                Product_map.put("special_price", jsonArray.getJSONObject(i).getString("specialPrice"));
                                Product_map.put("is_wishlist", jsonArray.getJSONObject(i).getString("is_wishlist"));



                                searchlist.add(Product_map);







                            }

                            System.out.println("SEARCH LIST IN SEARCHACTIVITY IS----"+ searchlist);
                            //   listView.setAdapter(new Prd_list_adapter(SearchActivity.this, searchlist));
                            gridView.setAdapter(new Prd_grid_adapter(SearchActivity.this, searchlist));

                        } catch (JSONException e) {
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


}
