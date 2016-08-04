package com.example.user.smgapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.audiofx.NoiseSuppressor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ProductList extends NavigationDrawer {
    GridView gv;
    //ListView Lv;
    String url_filterProductList = SingletonActivity.API_URL + "api/filter_product_list.php?category_id=";
    // http://52.77.18.137/api/flowers.php?category_id=341
    String url_ProductList, url_filter, url_filtrProduct;
    String[] sort_list = {"Name", "Price"};
    //ArrayList<String> filter_one, filter_one_value, filter_two, filter_two_value;
    String[] keys_filter = new String[2];
    LinearLayout grid_btn, list_btn;

    String passed_id;
    SimpleAdapter adapter;
    Prd_grid_adapter gadapter;
    Prd_list_adapter ladapter;
    ListView listView;
    Spinner spinner;
    ArrayList<HashMap<String, String>> Sub_category_only_list;
    ProgressDialog pDialog;
    String CategoryPage_name;
    String TAG_PRODUCTS = "products";
    String TAG_PRODUCT_NAME = "product_name";
    String TAG_PRODUCT_ID = "product_id";
    String TAG_PRODUCT_IMAGE = "product_image";
    String TAG_PRODUCT_PRICE = "product_price";
    String TAG_PRODUCT_SPECIAL_PRICE = "special_price";
    String TAG_IS_WISHLIST="is_wishlist";
  String  TAG_WISH_MSG="msg";

    String is_wishlist,msg_wishlist;
    ArrayList<HashMap<String, String>> list;
    TextView noProduct;
    RelativeLayout parentR, sort, filter, linear_head;
    AlertDialog dialog_sort;
    TextView app_title;
    Typeface face;
    ProgressBar pBar;
    // TreeSet<String> filter_set;
    ArrayList<String> filter_list;
    ArrayList<ArrayList<String>> filter_options, filter_options_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app_title = (TextView) findViewById(R.id.app_title);
        face = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setTitle(((GlobalClass) getApplication()).Selected_Category);
        pBar = (ProgressBar) findViewById(R.id.progress_bar_custom);

        nav_head_layout.setVisibility(View.VISIBLE);
        linear_head = (RelativeLayout) findViewById(R.id.linear_head);
        //linear_head.setVisibility(View.INVISIBLE);

        final Intent in = getIntent();
        passed_id = in.getStringExtra("id");
        SingletonActivity.passed_id=passed_id;
        //String passed_id="342";
        url_ProductList = SingletonActivity.API_URL + "api/products.php?category_id=" + passed_id+"&customer_id="+SingletonActivity.custidstr;
        Log.d("p_url", "p_url..." + url_ProductList);
        pBar.setVisibility(View.VISIBLE);
        parentR = (RelativeLayout) findViewById(R.id.parentRel);

        if(SingletonActivity.checkConnectivity(ProductList.this)){

            new Download().execute(url_ProductList);
        }
        else{
            Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent1);
        }


        // productList();
        noProduct = (TextView) findViewById(R.id.noProduct);
        noProduct.setVisibility(View.GONE);
        grid_btn = (LinearLayout) findViewById(R.id.linear_grid);
        list_btn = (LinearLayout) findViewById(R.id.linear_list);
        grid_btn.setBackgroundResource(R.drawable.grid_item_bg);
        list_btn.setBackgroundResource(R.drawable.grid_item_bg_deactive);


        sort = (RelativeLayout) findViewById(R.id.relativeLayout);
        filter = (RelativeLayout) findViewById(R.id.relativeLayout2);


        ArrayAdapter<String> adapterSp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sort_list);
        AlertDialog.Builder builder_sp = new AlertDialog.Builder(this);
        builder_sp.setTitle("Sort By");
        builder_sp.setAdapter(adapterSp, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // spinner_locatn.setText(hotels[item]); list
                // pDialog.show();
                pBar.setVisibility(View.VISIBLE);
                if (item == 0) {
                    // Collections.sort(list, new SortComparator("product_price"));

                    Collections.sort(list, new Comparator<Map<String, String>>() {
                        @Override
                        public int compare(final Map<String, String> map1, final Map<String, String> map2) {
                            // Get fields from maps, compare
                            return map1.get("product_name").compareTo(map2.get("product_name"));
                        }

                    });

                } else {
                    //Collections.sort(list, new SortComparator("product_name"));
                    Collections.sort(list, new Comparator<Map<String, String>>() {
                        @Override
                        public int compare(final Map<String, String> map1, final Map<String, String> map2) {
                            // Get fields from maps, compare
                            Float o1 = Float.parseFloat(map1.get("product_price").replaceAll(",", "")), o2 = Float.parseFloat(map2.get("product_price").replaceAll(",", ""));
                            return o1.compareTo(o2);
                        }

                    });
                }
                int viewState = listView.getVisibility();
                if (viewState == View.VISIBLE) {
                    setListviewList();
                } else {
                    setGridList();
                }
                // pDialog.cancel();
                pBar.setVisibility(View.INVISIBLE);
            }
        });

        dialog_sort = builder_sp.create();

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 1 && list.get(0).containsKey("Empty")) {

                } else {
                    dialog_sort.show();
                }

            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (list.size()==1&&list.get(0).containsKey("Empty")) {

                }else
                {*/
                Log.d("start", "onclick");

                if(SingletonActivity.checkConnectivity(ProductList.this)){

                    new DownloadFilter().execute();
                }
                else{
                    Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
                    startActivity(intent1);
                }


                // }

            }
        });

        AdapterView.OnItemClickListener itemClickListener_list = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {

                /*LinearLayout linearLayoutParent = (LinearLayout) container;
                RelativeLayout linearLayoutChild = (RelativeLayout) linearLayoutParent.getChildAt(1);

                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
                tvCountry.setGravity(Gravity.CENTER_VERTICAL);*/

                Log.e("ur detail id..", "details id.." + list.get(position).get("product_id"));
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("product_id", list.get(position).get("product_id"));
                // intent.putExtra("special_price",list.get(position).get("product_id"));
                startActivity(intent);
            }
        };
        AdapterView.OnItemClickListener itemClickListener_grid = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                Log.e("ur detail id..", "details id.." + list.get(position).get("product_id"));
                Intent intent = new Intent(getApplicationContext(), ProductDetails.class);
                intent.putExtra("product_id", list.get(position).get("product_id"));
                // intent.putExtra("special_price",list.get(position).get("product_id"));
                startActivity(intent);

            }
        };

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(itemClickListener_list);
        listView.setVisibility(View.GONE);

        gv = (GridView) findViewById(R.id.gridView1);
        gv.setOnItemClickListener(itemClickListener_grid);
        gv.setVisibility(View.VISIBLE);


        grid_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (list.size() == 1 && list.get(0).containsKey("Empty")) {

                } else {
                    grid_btn.setBackgroundResource(R.drawable.grid_item_bg);
                    list_btn.setBackgroundResource(R.drawable.grid_item_bg_deactive);
                    setGridList();
                }


            }

        });
        list_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (list.size() == 1 && list.get(0).containsKey("Empty")) {

                } else {
                    grid_btn.setBackgroundResource(R.drawable.grid_item_bg_deactive);
                    list_btn.setBackgroundResource(R.drawable.grid_item_bg);
                    setListviewList();
                }

            }

        });

    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(getIntent());
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    void setGridList() {
        Log.d("h1", "h1");
        if (list.isEmpty()) {
            Log.d("h2", "h2");
            noProduct.setVisibility(View.VISIBLE);
            noProduct.setText("There are no products matching the selection");
            gv.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else {
            Log.d("h3", "h3");
            HashMap<String, String> check_empty = list.get(0);
            if (check_empty.containsKey("Empty")) {
                noProduct.setVisibility(View.VISIBLE);
                noProduct.setText(check_empty.get("Empty"));
                gv.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

            } else {
                Log.d("h4", "h4");
                // Log.d("list_size", "size_" + list.get(Selected_sub_category_index));
                listView.setVisibility(View.GONE);
                noProduct.setVisibility(View.GONE);
                gv.setVisibility(View.VISIBLE);
               // gv.setAdapter(new Prd_grid_adapter(this, list));

                gadapter = new Prd_grid_adapter(ProductList.this, list);
               /* gadapter.notifyDataSetChanged();
                gv.invalidateViews();*/
                gv.setAdapter(gadapter);
             //  gadapter.notifyDataSetChanged();
               // gadapter.notify();
             /*   finish();
                startActivity(getIntent());*/
            }
        }


    }

    void setListviewList() {
        if (list.isEmpty()) {
            noProduct.setVisibility(View.VISIBLE);
            noProduct.setText("There are no products matching the selection");
            gv.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else {
            HashMap<String, String> check_empty = list.get(0);
            if (check_empty.containsKey("Empty")) {
                noProduct.setVisibility(View.VISIBLE);
                noProduct.setText(check_empty.get("Empty"));
                gv.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            } else {
                gv.setVisibility(View.GONE);
                noProduct.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
               // listView.setAdapter(new Prd_list_adapter(this, list));

                ladapter = new Prd_list_adapter(this, list);
                listView.setAdapter(ladapter);
             /* finish();
                startActivity(getIntent());*/
            }
        }
    }

    class Download extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog.show();
            pBar.setVisibility(View.VISIBLE);
            linear_head.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... f_url) {

            try {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(f_url[0]);
                Log.d("url", "thrd" + f_url[0]);
                // HttpGet get = new HttpGet("www.google.com");
                HttpResponse response = client.execute(get);
                Log.d("status", "st_" + response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(entity);

                if (jsonStr != null) {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    if (jsonObject.isNull("category_name")) {

                        CategoryPage_name = jsonObject.getString("");

                    } else {
                        CategoryPage_name = jsonObject.getString("category_name");
                    }

                    //category_name
                    list = new ArrayList<HashMap<String, String>>();
                    if (jsonObject.isNull(TAG_PRODUCTS)) {
                        HashMap<String, String> Empty_map = new HashMap<>();
                        Empty_map.put("Empty", "There are no products matching the selection");
                        list.add(Empty_map);
                        Log.d("inside", "null");

                    } else {


                        JSONArray ProductsJsonArray = jsonObject.getJSONArray(TAG_PRODUCTS);

                        for (int i = 0; i < ProductsJsonArray.length(); i++) {

                            JSONObject ProductJsosn = ProductsJsonArray.getJSONObject(i);
                            HashMap<String, String> Product_map = new HashMap<>();
                            Product_map.put("product_id", ProductJsosn.getString(TAG_PRODUCT_ID));
                            Product_map.put("product_name", ProductJsosn.getString(TAG_PRODUCT_NAME));
                            Product_map.put("product_image", ProductJsosn.getString(TAG_PRODUCT_IMAGE));
                            Product_map.put("product_price", ProductJsosn.getString(TAG_PRODUCT_PRICE));
                            Product_map.put("special_price", ProductJsosn.getString(TAG_PRODUCT_SPECIAL_PRICE));
                            Product_map.put("is_wishlist",ProductJsosn.getString(TAG_IS_WISHLIST));
                           // Product_map.put("msg",ProductJsosn.getString(TAG_WISH_MSG));
                           // msg_wishlist=ProductJsosn.getString(TAG_WISH_MSG);
                            is_wishlist=ProductJsosn.getString(TAG_IS_WISHLIST);
                            list.add(Product_map);

                        }

                    }

                } else {
                    Log.d("response_null", "null");
                }

                Log.d("list", "list_" + list.size());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            // super.onPostExecute(aVoid);
            // pDialog.cancel();
            pBar.setVisibility(View.INVISIBLE);
            linear_head.setVisibility(View.VISIBLE);
            Log.d("inside", "post");
            // Selected_sub_category_index=0;
            ((GlobalClass) getApplication()).Selected_Category = CategoryPage_name;

            app_title.setText(((GlobalClass) getApplication()).Selected_Category);
            app_title.setTypeface(face);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


           /* ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(ProductList.this,R.layout.spinner_item,Sublist_dropdown);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);*/
            if (listView.getVisibility() == View.VISIBLE) {
                setListviewList();
            } else {
                setGridList();
            }


        }
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

        if (item.getItemId() == R.id.action_terms) {
            Intent in = new Intent(this, TermsandCondition.class);
            startActivity(in);
        }
        if (item.getItemId() == R.id.action_login) {

            Intent intent = new Intent(ProductList.this, Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DownloadFilter extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog.show();
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                DefaultHttpClient client = new DefaultHttpClient();
                url_filter = SingletonActivity.API_URL + "api/filter.php?category_id=" + passed_id;
                HttpGet get = new HttpGet(url_filter);
                Log.d("url_filter", "" + url_filter);
                // HttpGet get = new HttpGet("www.google.com");
                HttpResponse response = client.execute(get);
                Log.d("status", "st_" + response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(entity);

                if (jsonStr != null) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    filter_list = new ArrayList<String>();
                    if (jsonObject.getString("isSuccess").equalsIgnoreCase("Success!")) {
                    /*filter_two = new ArrayList<String>();
                    filter_one = new ArrayList<String>();
                    filter_one_value = new ArrayList<String>();
                    filter_two_value = new ArrayList<String>();*/

                        // filter_set=new TreeSet<String>();
                        filter_options = new ArrayList<ArrayList<String>>();
                        filter_options_value = new ArrayList<ArrayList<String>>();

                        JSONArray attributeJsonArray = jsonObject.getJSONArray("attribute");

                        for (int i = 0; i < attributeJsonArray.length(); i++) {

                            JSONObject ProductJsosn = attributeJsonArray.getJSONObject(i);

                            if (!filter_list.contains(ProductJsosn.getString("key"))) {
                                Log.d("key", ProductJsosn.getString("key"));
                                filter_list.add(ProductJsosn.getString("key"));
                                filter_options.add(new ArrayList<String>());
                                filter_options_value.add(new ArrayList<String>());
                            }

                            int index = filter_list.indexOf(ProductJsosn.getString("key"));

                            filter_options.get(index).add(ProductJsosn.getString("label"));
                            filter_options_value.get(index).add(ProductJsosn.getString("value"));

                        }

                        Log.d("filter_options", "_" + filter_options.size());
                        Log.d("filter_options_value", "_" + filter_options_value.size());
                        Log.d("filter_list", "_" + filter_list.size());


                    }/*else
                    {

                    }*/

                } else {
                    Log.d("response_null", "null");
                }

                Log.d("list", "list_" + list.size());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //pDialog.cancel();
            pBar.setVisibility(View.INVISIBLE);
            if (filter_list.size() > 0) {
                new CustomFilterDialog(ProductList.this).show();
            }
        }
    }

    public class CustomFilterDialog extends Dialog {
        ListView listView01, listView02;
        int filtr_index = 0;

        public CustomFilterDialog(Context context) {
            super(context);
            setTitle("Filter By");
            setContentView(R.layout.filter_dialog_new);

            listView01 = (ListView) findViewById(R.id.filtr_main_list);
            listView02 = (ListView) findViewById(R.id.filtr_details_list);


            listView01.setAdapter(new ArrayAdapter<String>(ProductList.this, android.R.layout.simple_spinner_dropdown_item, filter_list));
            listView01.setSelection(0);
            listView01.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {

                    listView01.setSelection(position);
                    childView.setSelected(true);


                    //childView.setActivated(true);
                    filtr_index = position;
                    listView02.setAdapter(new ArrayAdapter<String>(ProductList.this, android.R.layout.simple_spinner_dropdown_item, filter_options.get(position)));

                }
            });

            //listView02.setAdapter(new ArrayAdapter<String>(ProductList.this, android.R.layout.simple_spinner_dropdown_item, filter_options.get(position)))

            listView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {

                    //Log.d("onclick", "filter_item" + filter_options_value.get(position));

                    if(SingletonActivity.checkConnectivity(ProductList.this)){

                        new Download().execute(url_filterProductList + passed_id + "&filter_key=" + filter_list.get(filtr_index) + "&filter_value=" + filter_options_value.get(filtr_index).get(position));
                    }
                    else{
                        Intent intent1=new Intent(getApplicationContext(),NoInternet.class);
                        startActivity(intent1);
                    }


                    dismiss();
                    //  http://52.77.18.137/api/filter_product_list.php?category_id=342&filter_key=city&filter_value=22
                }
            });

           /* // get our tabHost from the xml
            TabHost tabHost = (TabHost) findViewById(R.id.TabHost01);
            tabHost.setup();

            // create tab 1
            TabHost.TabSpec spec1 = tabHost.newTabSpec("tab1");
            spec1.setIndicator(keys_filter[0]);
            spec1.setContent(R.id.city_list);
            tabHost.addTab(spec1);
            //create tab2
            TabHost.TabSpec spec2 = tabHost.newTabSpec("tab2");
            spec2.setIndicator(keys_filter[1]);
            spec2.setContent(R.id.price_list);
            tabHost.addTab(spec2);*/

        }

    }


}
