package com.example.user.smgapp;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static String url = "http://52.77.18.137/api/allcategory.php";
    private ProgressDialog pDialog;
   public TextView nav_title;

    public static final String TAG_RESULTS = "category";
    public static final String TAG_CAT_ID = "main_cat_id";
    public static final String TAG_MAIN_CAT_NAME = "main_cat_name";
    public static final String TAG_MAIN_CAT_URL = "main_cat_url";
    public static final String TAG_SUB_CAT_DETAILS = "sub_cat_deatils";
    public static final String TAG_SUB_SUB_CAT_DETAILS = "sub_sub_cat_details";
    public static final String TAG_SUB_CAT_ID = "sub_cat_id";
    public static final String TAG_SUB_CAT_NAME = "sub_cat_name";
    String thumb_nam,thumb_image,thumb_id;


    public static final String TAG_SUB_SUB_DETAILS = "sub_sub_cat_details";
    public static final String TAG_SUB_SUB_CAT_ID = "sub_sub_cat_id";
    //  public static final String TAG_PARENT_SUB_CAT_NAME = "parent_sub_sub_cat";
    public static final String TAG_SUB_SUB_CAT_NAME = "sub_sub_cat_name";
    public static final String TAG_SUB_SUB_CAT_URL = "sub_sub_cat_url";
    String image_url;
    String cat_id = "",
            main_cat_name = "",
            sub_cat_url = "",
            sub_cat_details = "",
            Sub_cat_id = "",
            Sub_cat_name = "",
            sub_sub_cat_id = "",
            sub_sub_cat_name = "",
            Sub_sub_cat_url = "",
            Parent_sub_name = "";

    ArrayList<HashMap<String, String>> PostsList;
    ArrayList<HashMap<String, String>> sub_cat;
    ArrayList<HashMap<String, String>> parent_cat;
    ArrayList<HashMap<String, String>> sub_sub_cat_array;
    ArrayList<ArrayList<HashMap<String, String>>> array_list_sub_ids;
    ArrayList<String> thumb_cat_id;
    ArrayList<HashMap<String, String>> thumb_details;
    ArrayList<ArrayList<HashMap<String, String>>> main_product_details;
    ArrayList<HashMap<String, String>> details_array;
    ArrayList<String> main_cat_name_array;
    HorizontalListView listview, listView2, listView3, listView4;
    TextView listview_txt, listview2_txt, listview3_txt, listview4_txt;

    ArrayList<String> parent_name_array;
    ArrayList<String> child_name_array;
    ArrayList<String> banner_images_array;
    ArrayList<String> thumb_images_array;
    ArrayList<String> sub_sub_cat_id_array;
    // ArrayList<String> sub_cat_id_array;
    JSONArray RESULT = null;
    JSONArray SUB_SUB = null;
    DisplayMetrics metrics;
    int width;
   public Typeface face;
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    Gallery gallery;
    View v1,v2,v3,v4;


    protected View view;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    DrawerLayout drawer;
    List<ExpandableMenuModel> listDataHeader;
    HashMap<ExpandableMenuModel, List<String>> listDataChild;
    RecyclerView mRecyclerView, recycle_trend;
    RecyclerView.LayoutManager mLayoutManager, trendManager;
    RecyclerView.Adapter mAdapter, trendAdapter;
    GridView grid;
    GridViewAdapter gadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ImageView fabIconNew = new ImageView(this);

        listview = (HorizontalListView) findViewById(R.id.listview);
        listView2 = (HorizontalListView) findViewById(R.id.listview2);
        listView3 = (HorizontalListView) findViewById(R.id.listview3);
        listView4 = (HorizontalListView) findViewById(R.id.listview4);
        listview_txt = (TextView) findViewById(R.id.listview_txt);
        listview2_txt = (TextView) findViewById(R.id.listview2_txt);
        listview3_txt = (TextView) findViewById(R.id.listview3_txt);
        listview4_txt = (TextView) findViewById(R.id.listview4_txt);
        v1=findViewById(R.id.view1);
        v2=findViewById(R.id.view2);
        v3=findViewById(R.id.view3);
        v4=findViewById(R.id.view4);
        nav_title= (TextView) findViewById(R.id.navigation_title);
        face=Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        grid= (GridView) findViewById(R.id.grid);



      /*  mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

       mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
*/
       // mAdapter = new GridAdapter();
      //  mRecyclerView.setAdapter(mAdapter);

       // trendAdapter = new TrendingAdapter();
     
        gallery = (Gallery) findViewById(R.id.gallery);

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int pos, long l) {

                for (int i = 0; i < mDotsCount; i++) {
                    HomePage.mDotsText[i]
                            .setTextColor(Color.BLACK);

                }

                HomePage.mDotsText[pos]
                        .setTextColor(Color.parseColor("#9976b0"));

            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });


        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        final ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        rlIcon1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "CHAT...", Toast.LENGTH_LONG).show();
               /* Intent i = new Intent(getApplicationContext(), FB.class);
                startActivity(i);*/


                return false;
            }
        });

        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));


        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(fab)
                .build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fab, pvhR);
                animation.start();

            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fab, pvhR);
                animation.start();

            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        expandableList = (ExpandableListView) findViewById(R.id.expandable_list);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        new getCategories().execute();


    }


    @Override
    public void onBackPressed() {
        //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) HomePage.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(HomePage.this.getComponentName()));
            handleIntent(getIntent());
        }
        return super.onCreateOptionsMenu(menu);
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

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_login) {
            return true;
        }

        if (id == R.id.action_signup) {
            return true;
        }
        if(item.getItemId()==R.id.action_terms)
        {
            Intent in=new Intent(this,TermsandCondition.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }




    public class HorizAdapter extends BaseAdapter {
        ArrayList<HashMap<String, String>> list_sub;
        public HorizAdapter(ArrayList<HashMap<String, String>> list_sub) {
            this.list_sub = list_sub;
        }

        @Override
        public int getCount() {
            return list_sub.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_product_horizontal_list, null);
            TextView title = (TextView) retval.findViewById(R.id.title);

            title.setTypeface(face);
            ImageView image = (ImageView) retval.findViewById(R.id.image);
            Picasso.with(getApplicationContext())
                    .load(list_sub.get(position).get("product_image"))
                    .into(image);

            title.setText(list_sub.get(position).get("product_name"));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"u clicked on id:"+list_sub.get(position).get("product_id"),Toast.LENGTH_SHORT).show();
                }
            });

            return retval;
        }

    }

    ;



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
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.submenu);
            ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
            lblListHeader.setTypeface(face, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getIconName());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);

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
            // Showing progress dialog
            pDialog = new ProgressDialog(HomePage.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
        // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
        // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("jsonStr", "" + jsonStr);
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
                                    Log.d("main_cat_name",""+main_cat_name);
                                    parent_cat.add(parent_hash);
                                    ExpandableMenuModel model = new ExpandableMenuModel();
                                    model.setIconName(main_cat_name);
                                    listDataHeader.add(model);
                                    Log.e("length..","listdata headet length..."+listDataHeader.size());
                                    //sub_cat_deatils

                                    if(elem.isNull(TAG_SUB_CAT_DETAILS))
                                    {
                                        Log.d("main_cat_name","inside"+main_cat_name);
                                    }else {


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



                                               /* JSONArray sub_sub_cat = elem.getJSONArray(TAG_SUB_SUB_CAT_DETAILS);
                                                if (sub_sub_cat != null) {
                                                    sub_sub_cat_array = new ArrayList<HashMap<String, String>>();
                                                    HashMap<String, String> sub_sub_hash = new HashMap<>();

                                                    for (int k = 0; k < sub_sub_cat.length(); k++) {
                                                        JSONObject sub_sub = sub_sub_cat.getJSONObject(k);
                                                        if (sub_sub != null) {

                                                            sub_sub_cat_id = sub_sub.getString("sub_sub_cat_id");
                                                            sub_sub_cat_name = sub_sub.getString("sub_sub_cat_name");
                                                            sub_sub_hash.put("sub_sub_id", sub_sub_cat_id);
                                                            sub_sub_hash.put("sub_sub_name", sub_sub_cat_name);
                                                            sub_sub_cat_array.add(sub_sub_hash);

                                                            Log.e("sub_sub_cat_array..", "sub_sub_cat_array.." + sub_sub_cat_array);

                                                        }
                                                    }
                                                }*/

                                                }
                                            }
                                            Log.e("array_list_sub_ids", "array_list_sub_ids" + array_list_sub_ids);
                                            listDataChild.put(model, child_name_array);
                                            Log.e("child header..", "child Header.." + listDataChild);

                                        }

                                    }
                                }
                                array_list_sub_ids.add(sub_cat);
                            }

                        }

                        JSONArray thumb_array=jsonObj.getJSONArray("thumbnail");
                        if (thumb_array!=null){
                            thumb_images_array=new ArrayList<>();
                            thumb_details=new ArrayList<>();
                            thumb_cat_id=new ArrayList<>();
                            for (int i=0;i<thumb_array.length();i++){
                                JSONObject thumb_obj=thumb_array.getJSONObject(i);
                                HashMap<String,String> thumb_hash=new HashMap<>();

                                if (thumb_obj!=null){
                                     thumb_nam=thumb_obj.getString("cat_name");
                                    thumb_id=thumb_obj.getString("cat_id");
                                     thumb_image=thumb_obj.getString("cat_image");
                                    thumb_images_array.add(thumb_image);
                                    thumb_cat_id.add(thumb_id);
                                    thumb_hash.put("thumb_id", thumb_id);
                                    thumb_hash.put("thumb_nam",thumb_nam);
                                    thumb_hash.put("thumb_image",thumb_image);
                                    thumb_details.add(thumb_hash);
                                    Log.e("thumb details...", "thumb details.." + thumb_cat_id);

                                }

                            }

                        }



                        JSONArray banner_array = jsonObj.getJSONArray("banner");
                        if (banner_array != null) {
                            banner_images_array = new ArrayList<>();
                            for (int i = 0; i < banner_array.length(); i++) {
                                JSONObject banner_obj = banner_array.getJSONObject(i);
                                if (banner_obj != null) {
                                    image_url = banner_obj.getString("image");
                                    banner_images_array.add(image_url);
                                    String title_banner = banner_obj.getString("title");

                                }
                            }
                        }
                        JSONArray home_products_sub_cat_array = jsonObj.getJSONArray("home_product_sub_cat");
                        if (home_products_sub_cat_array != null) {
                            main_product_details = new ArrayList<>();
                            main_cat_name_array = new ArrayList<>();
                            for (int i = 0; i < home_products_sub_cat_array.length(); i++) {
                                JSONObject home_products_obj = home_products_sub_cat_array.getJSONObject(i);
                                if (home_products_obj != null) {

                                    String mian_cat_name = home_products_obj.getString("main_cat_name");
                                    main_cat_name_array.add(mian_cat_name);
                                    Log.e("main cat array...", "main cat array..." + main_cat_name_array);
                                    Log.e("mian_cat_name", "mian_cat_name" + mian_cat_name);

                                    JSONArray jsonArray = home_products_obj.getJSONArray("main_cat_product_details");
                                    if (jsonArray != null) {
                                        details_array = new ArrayList<>();
                                        for (int j = 0; j < jsonArray.length(); j++) {


                                            HashMap<String, String> product_details_hash = new HashMap<>();
                                            Log.e("", "jsonArray length....." + jsonArray.length());
                                            JSONObject jobj = jsonArray.getJSONObject(j);
                                            if (jobj != null) {
                                                String product_id=jobj.getString("product_id");
                                                String product_name = jobj.getString("product_name");
                                                String product_image = jobj.getString("product_image");
                                                product_details_hash.put("product_id",product_id);
                                                product_details_hash.put("product_name", product_name);
                                                product_details_hash.put("product_image", product_image);
                                                details_array.add(product_details_hash);


                                                Log.e("product details....", "product details..." + product_name + "\t" + product_image);
                                            }

                                        }


                                    }


                                }
                                main_product_details.add(details_array);
                                Log.e("main product details...", "main product details..." + main_product_details);
                                //  main_product_details.get(0)
                            }
                        }
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
            pDialog.dismiss();
            mMenuAdapter = new ExpandableListAdapter(getApplication(), listDataHeader, listDataChild, expandableList);
            expandableList.setAdapter(mMenuAdapter);
           gallery();
            expandableOnclick();
            gadapter=new GridViewAdapter(getApplicationContext(),thumb_images_array,thumb_cat_id);
            grid.setAdapter(gadapter);



            if (main_product_details.size() ==4)
            {
                listview.setAdapter(new HorizAdapter(main_product_details.get(0)));
                listview_txt.setText(main_cat_name_array.get(0));
                listView2.setAdapter(new HorizAdapter(main_product_details.get(1)));
                listview2_txt.setText(main_cat_name_array.get(1));
                listView3.setAdapter(new HorizAdapter(main_product_details.get(2)));
                listview3_txt.setText(main_cat_name_array.get(2));
                listView4.setAdapter(new HorizAdapter(main_product_details.get(3)));
                listview4_txt.setText(main_cat_name_array.get(3));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.VISIBLE);
                listView3.setVisibility(View.VISIBLE);
                listView4.setVisibility(View.VISIBLE);
            }
            else if (main_product_details.size()==1){
                listview.setAdapter(new HorizAdapter(main_product_details.get(0)));
                listview_txt.setText(main_cat_name_array.get(0));
                v1.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);

            }
            else if(main_product_details.size()==2) {

                listview.setAdapter(new HorizAdapter(main_product_details.get(0)));
                listview_txt.setText(main_cat_name_array.get(0));
                listView2.setAdapter(new HorizAdapter(main_product_details.get(1)));
                listview2_txt.setText(main_cat_name_array.get(1));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.VISIBLE);
            }
            else if(main_product_details.size()==3) {
                listview.setAdapter(new HorizAdapter(main_product_details.get(0)));
                listview_txt.setText(main_cat_name_array.get(0));
                listView2.setAdapter(new HorizAdapter(main_product_details.get(1)));
                listview2_txt.setText(main_cat_name_array.get(1));
                listView3.setAdapter(new HorizAdapter(main_product_details.get(2)));
                listview3_txt.setText(main_cat_name_array.get(2));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                listview.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.VISIBLE);
                listView3.setVisibility(View.VISIBLE);
            }

        }

    }

    private void expandableOnclick() {


        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getApplication(), "u clicked on:" + childPosition + "_" + groupPosition, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), ProductList.class);
                intent.putExtra("id", array_list_sub_ids.get(groupPosition).get(childPosition).get(TAG_SUB_CAT_ID));
                startActivity(intent);
                return true;
            }
        });
    }



    private void gallery() {
        gallery.setAdapter(new ImageAdapter(getApplicationContext()));


        mDotsLayout = (LinearLayout) findViewById(R.id.image_count);
        //here we count the number of images we have to know how many dots we need
        mDotsCount = gallery.getAdapter().getCount();

        //here we create the dots
        //as you can see the dots are nothing but "."  of large size
        mDotsText = new TextView[mDotsCount];

        //here we set the dots
        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i] = new TextView(getApplication());
            mDotsText[i].setText(Html.fromHtml("&#8226;"));
            //mDotsText[i].setText(".");
            mDotsText[i].setTextSize(30);
            mDotsText[i].setTypeface(null, Typeface.BOLD);
            mDotsText[i].setTextColor(Color.BLACK);
            mDotsLayout.addView(mDotsText[i]);
        }
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        //constructor
        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return banner_images_array.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);
            Log.e("banner images array...", "images array.." + banner_images_array);

            Picasso.with(getApplicationContext())
                    .load(banner_images_array.get(i))
                    .into(imageView);
            // imageView.setImageResource(mImageIds[i]);
            imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            return imageView;

        }

    }


  public class GridViewAdapter  extends BaseAdapter{
        private Context mContext;
      ArrayList<String> images, id;


        public GridViewAdapter(Context c,ArrayList<String> thumb_nail_images,ArrayList<String> thumb_id ) {
            mContext = c;
            this.images = thumb_nail_images;
            this.id=thumb_id;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_item, null);
               // TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.img_thumbnail);
               // textView.setText(web[position]);
                Picasso.with(getApplicationContext())
                        .load(images.get(position)).into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"selected id:"+id.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), ProductList.class);
                        intent.putExtra("id", id.get(position));
                        startActivity(intent);
                    }
                });


            } else {
                grid = (View) convertView;
            }


            return grid;
        }
    }
    }
