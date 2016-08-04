package com.example.user.smgapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static String url = SingletonActivity.API_URL + "api/allcategory.php";
    public static final String TAG_RESULTS = "category";
    public static final String TAG_CAT_ID = "main_cat_id";
    public static final String TAG_MAIN_CAT_NAME = "main_cat_name";
    public static final String TAG_SUB_CAT_DETAILS = "sub_cat_deatils";
    public static final String TAG_SUB_CAT_ID = "sub_cat_id";
    public static final String TAG_SUB_CAT_NAME = "sub_cat_name";
    String thumb_nam, thumb_image, thumb_id;
    String image_url;
    String cat_id = "",
            main_cat_name = "",
            Sub_cat_id = "",
            Sub_cat_name = "";


    ArrayList<HashMap<String, String>> sub_cat;
    ArrayList<HashMap<String, String>> parent_cat;
    ArrayList<ArrayList<HashMap<String, String>>> array_list_sub_ids;
    ArrayList<String> thumb_cat_id;
    ArrayList<HashMap<String, String>> thumb_details;
    ArrayList<ArrayList<HashMap<String, String>>> main_product_details;
    ArrayList<HashMap<String, String>> details_array;
    ArrayList<String> main_cat_name_array;
    ArrayList<String> parent_name_array;
    ArrayList<String> child_name_array;
    ArrayList<String> banner_images_array;
    ArrayList<String> thumb_images_array;
    public Typeface face;
    ActionBarDrawerToggle toggle;
    protected View view;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    DrawerLayout drawer;
    List<ExpandableMenuModel> listDataHeader;
    HashMap<ExpandableMenuModel, List<String>> listDataChild;
    TextView nav_home_txt;
    ImageView group_indicator, gift_icon;
    RelativeLayout nav_head_layout;

    static SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    Menu mm;

    Boolean loginboolean;
    String cartcount, customeridstr;
    TextView cartcounttxt;
    ImageView searchicon;
    EditText searchedittext;
    RelativeLayout listgridrellayout;
    ImageView cartimg;
    String sendercontactemail,firstnamestr,lastnamestr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        prefs = NavigationDrawer.this.getSharedPreferences(MyPREFERENCES, 0);
        //1)key=Name and 2)key=Emailid 3)key=Phonenos
        loginboolean = prefs.getBoolean("loginlogoutkey", false);
        cartcount = prefs.getString("cartcount", "");
        customeridstr = prefs.getString("customerid", "");
        sendercontactemail = prefs.getString("senderemail", "");
        firstnamestr = prefs.getString("firstname", "");
        lastnamestr = prefs.getString("lastname", "");



        SingletonActivity.custidstr = customeridstr;

        SingletonActivity.custemailstr = sendercontactemail;

        SingletonActivity.custfirstname = firstnamestr;

        SingletonActivity.custlastname = lastnamestr;


        System.out.println("LOGIN BOOLEAN VALUE IN NAVIGATION DRAWER CLASS --------------" + loginboolean);

        System.out.println("CART COUNT FROM SHARED PREFERENCES IN NAVIGATION DRAWER CLASS --------------" + cartcount);

        cartcounttxt = (TextView) findViewById(R.id.cartnumber);
        cartimg = (ImageView)findViewById(R.id.shopping_bag);

        if (cartcount != "") {
            cartcounttxt.setText(cartcount);
            System.out.println("INSIDE IF LOOP");
        } else {
            //   if(cartcount=="") {
            cartcounttxt.setText("0");

            System.out.println("INSIDE ELSE LOOP");

        }

        cartimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavigationDrawer.this,CartActivity.class);
                startActivity(i);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        expandableList = (ExpandableListView) findViewById(R.id.expandable_list);
        face = Typeface.createFromAsset(getApplication().getAssets(), "fonts/OpenSans-Regular.ttf");
        group_indicator = (ImageView) findViewById(R.id.iconimage);
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        gift_icon = (ImageView) findViewById(R.id.nav_head_home);

        searchicon = (ImageView) findViewById(R.id.searchicon);


        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NavigationDrawer.this, SearchActivity.class);
                startActivity(i);

            }
        });

        expandableList.addHeaderView(listHeaderView);
        nav_home_txt = (TextView) findViewById(R.id.nav_home_txt);
        nav_home_txt.setTypeface(face);
        nav_head_layout = (RelativeLayout) findViewById(R.id.nav_head_layout);
        nav_head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableList.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });



        if(SingletonActivity.checkConnectivity(NavigationDrawer.this)){

            new getCategories().execute();
        }
        else{
            Intent intent=new Intent(getApplicationContext(),NoInternet.class);
            startActivity(intent);
        }





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context mContext;
        private List<ExpandableMenuModel> mListDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<ExpandableMenuModel, List<String>> mListDataChild;
        ExpandableListView expandList;

        public ExpandableListAdapter(Context context, List<ExpandableMenuModel> listDataHeader, HashMap<ExpandableMenuModel, List<String>> listChildData, ExpandableListView mView) {
            this.mContext = context;
            this.mListDataHeader = listDataHeader;
            this.mListDataChild = listChildData;
            this.expandList = mView;

        }

        @Override
        public int getGroupCount() {
            int i = mListDataHeader.size();
            Log.d("GROUPCOUNT", String.valueOf(i));
            return this.mListDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            int childCount = 0;
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();
            Log.e("child size...", "child size...." + childCount);

            return childCount;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.mListDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .get(childPosition).toString());
            return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .get(childPosition);

        }

        

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {

            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ExpandableMenuModel headerTitle = (ExpandableMenuModel) getGroup(groupPosition);


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.listheader, null);
            }

            if (mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size() == 0) {
                ((ImageView) convertView.findViewById(R.id.iconimage)).setVisibility(View.INVISIBLE);

            } else {

                ((ImageView) convertView.findViewById(R.id.iconimage)).setVisibility(View.VISIBLE);
                ((ImageView) convertView.findViewById(R.id.iconimage))
                        .setImageResource(isExpanded ? R.drawable.up : R.drawable.down);
            }
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.submenu);
            lblListHeader.setTypeface(face, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getIconName());
            //  }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);


           /* Log.e("child count...","child count..."+mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .get(childPosition));
           */


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_submenu, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.submenu);
            txtListChild.setTypeface(face);

            txtListChild.setText(childText);


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            return true;
        }


    }

    private class getCategories extends AsyncTask<Void, Void, Void>

    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            listDataChild = new HashMap<ExpandableMenuModel, List<String>>();


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj != null) {
                        JSONArray list = jsonObj.getJSONArray(TAG_RESULTS);//category
                        if (list != null) {
                            parent_name_array = new ArrayList<>();
                            listDataHeader = new ArrayList<ExpandableMenuModel>();
                            parent_cat = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> parent_hash = new HashMap<String, String>();
                            array_list_sub_ids = new ArrayList<>();
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject elem = list.getJSONObject(i);
                                if (elem != null) {
                                    cat_id = elem.getString(TAG_CAT_ID);
                                    main_cat_name = elem.getString(TAG_MAIN_CAT_NAME);//main_cat_name
                                    parent_name_array.add(main_cat_name);
                                    parent_hash.put("id", cat_id);
                                    parent_hash.put("name", main_cat_name);
                                    parent_cat.add(parent_hash);
                                    ExpandableMenuModel model = new ExpandableMenuModel();
                                    model.setIconName(main_cat_name);
                                    listDataHeader.add(model);
                                    //sub_cat_deatils
                                    child_name_array = new ArrayList<>();
                                    if (elem.isNull(TAG_SUB_CAT_DETAILS)) {
                                        listDataChild.put(model, child_name_array);

                                    } else {
                                        JSONArray prods = elem.getJSONArray(TAG_SUB_CAT_DETAILS);

                                        if (prods != null) {
                                            child_name_array = new ArrayList<>();
                                            sub_cat = new ArrayList<HashMap<String, String>>();
                                            for (int j = 0; j < prods.length(); j++)

                                            {

                                                JSONObject innerElem = prods.getJSONObject(j);
                                                if (innerElem != null) {
                                                    Sub_cat_id = innerElem.getString(TAG_SUB_CAT_ID);
                                                    Sub_cat_name = innerElem.getString(TAG_SUB_CAT_NAME);
                                                    HashMap<String, String> sub_cat_hash = new HashMap<>();
                                                    sub_cat_hash.put("sub_cat_id", Sub_cat_id);
                                                    sub_cat_hash.put("sub_cat_name", Sub_cat_name);
                                                    sub_cat.add(sub_cat_hash);
                                                    child_name_array.add(Sub_cat_name);


                                                }
                                            }
                                            // Log.e("array_list_sub_ids", "array_list_sub_ids" + array_list_sub_ids);
                                            listDataChild.put(model, child_name_array);

                                        }
                                    }
                                }
                                array_list_sub_ids.add(sub_cat);
                            }

                        }
                        /*JSONArray home_products_sub_cat_array = jsonObj.getJSONArray("home_product_sub_cat");
                        if (home_products_sub_cat_array != null) {
                            main_product_details = new ArrayList<>();
                            main_cat_name_array = new ArrayList<>();
                            for (int i = 0; i < home_products_sub_cat_array.length(); i++) {
                                JSONObject home_products_obj = home_products_sub_cat_array.getJSONObject(i);
                                if (home_products_obj != null) {

                                    String mian_cat_name = home_products_obj.getString("main_cat_name");
                                    main_cat_name_array.add(mian_cat_name);
                                    //Log.e("main cat array...", "main cat array..." + main_cat_name_array);
                                    //Log.e("mian_cat_name", "mian_cat_name" + mian_cat_name);

                                    JSONArray jsonArray = home_products_obj.getJSONArray("main_cat_product_details");
                                    if (jsonArray != null) {
                                        details_array = new ArrayList<>();
                                        for (int j = 0; j < jsonArray.length(); j++) {


                                            HashMap<String, String> product_details_hash = new HashMap<>();
                                           // Log.e("", "jsonArray length....." + jsonArray.length());
                                            JSONObject jobj = jsonArray.getJSONObject(j);
                                            if (jobj != null) {
                                                String product_id = jobj.getString("product_id");
                                                String product_name = jobj.getString("product_name");
                                                String product_image = jobj.getString("product_image");
                                                product_details_hash.put("product_id", product_id);
                                                product_details_hash.put("product_name", product_name);
                                                product_details_hash.put("product_image", product_image);
                                                details_array.add(product_details_hash);


                                               // Log.e("product details....", "product details..." + product_name + "\t" + product_image);
                                            }

                                        }


                                    }


                                }
                                main_product_details.add(details_array);
                               // Log.e("main product details...", "main product details..." + main_product_details);
                                //  main_product_details.get(0)
                            }
                        }*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mMenuAdapter = new ExpandableListAdapter(getApplication(), listDataHeader, listDataChild, expandableList);
            expandableList.setAdapter(mMenuAdapter);
            expandableOnclick();


        }

        private void expandableOnclick() {

            expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    Intent intent = new Intent(getApplication(), ProductList.class);
                    intent.putExtra("id", array_list_sub_ids.get(groupPosition).get(childPosition).get(TAG_SUB_CAT_ID));
                    startActivity(intent);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;

                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);


        SearchManager searchManager = (SearchManager) getApplication().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(NavigationDrawer.this.getComponentName()));
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

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        Typeface face = Typeface.createFromAsset(getApplication().getAssets(), "fonts/OpenSans-Regular.ttf");    //  THIS


        // if(sessionManager.isLoggedIn()==false) {
        if (id == R.id.action_login) {

            Intent in = new Intent(this, Login.class);
            startActivity(in);


        }


        if (id == R.id.action_logout) {

            prefs = NavigationDrawer.this.getSharedPreferences(MyPREFERENCES, 0);
            prefs.edit().clear().commit();

            finish();

            Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(NavigationDrawer.this, HomePage.class);
            startActivity(i);


        }

        if (item.getItemId() == R.id.action_terms) {
            Intent in = new Intent(this, TermsandCondition.class);
            startActivity(in);
        }

        if (item.getItemId() == R.id.action_privacy) {
            Intent in = new Intent(this, PrivacyPolicyActivity.class);
            startActivity(in);
        }

        if (item.getItemId() == R.id.action_contactApp) {
            Intent in = new Intent(this, ContactUsActivity.class);
            startActivity(in);
        }
        if (item.getItemId() == R.id.action_myWishlist) {
            Intent intent = new Intent(getApplicationContext(), WishList.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_myOrder) {
            Intent in = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(in);
        }

        if (item.getItemId() == R.id.action_rateApp) {
            Intent in = new Intent(getApplicationContext(), ComingSoonActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);


        MenuItem logintext = menu.findItem(R.id.action_login);
        MenuItem logouttext = menu.findItem(R.id.action_logout);


        logintext.setVisible(!loginboolean);
        logouttext.setVisible(loginboolean);
        return true;
    }

    public void removeFromwishList(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    String name = response.getString("responseCode");
                    if (name.equals("1")) {
                        Log.e("deleted..", "deleted..");
                     /*   finish();
                        startActivity(getIntent());*/

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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

                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void addTowishList(String url) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                  String  url_ProductList = SingletonActivity.API_URL + "api/products.php?category_id=" + SingletonActivity.passed_id+"&customer_id="+SingletonActivity.custidstr;
                    String name = response.getString("responseCode");
                    int responsecode = Integer.parseInt(name);
                    if (responsecode == 0) {
                        Log.e("already in wishlist", "alreday in wishlist");
                        // p_det_wishlist.setImageResource(R.drawable.empty_wishlist);
                       /* finish();
                        startActivity(getIntent());*/



                    } else {
                        Log.e("added wishlist", "added to wishlist..");
                        // p_det_wishlist.setImageResource(R.drawable.wishlist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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

                return params;
            }
        };

        GlobalClass.getInstance().addToRequestQueue(jsonObjReq);

    }



}
