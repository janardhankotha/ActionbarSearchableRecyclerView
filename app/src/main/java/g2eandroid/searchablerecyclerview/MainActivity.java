package g2eandroid.searchablerecyclerview;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    //------------------searchable view in actionbar---------------------
    private MenuItem searchMenuItem;
    MenuItemCompat menuitem = null;
    SearchView searchView;
    ArrayAdapter<String> adapter;

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    StudentRepo studentRepo ;
    private static DBHelper dbHelper;
    JSONParser jsonParser = new JSONParser();
    private ArrayList<Info_MyCart> mListFeederInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView1);

        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {
            new searchfunction().execute();
        } else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }


        EditText editsearch = (EditText) findViewById(R.id.editsearch);

        editsearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // Log.d(TAG, "onQueryTextSubmit ");
                Log.e("testing","s = "+s);
                cursor=studentRepo.getStudentListByKeyword(s.toString());
                if (cursor==null){
                    Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                }else{
                   // Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                }
                customAdapter.swapCursor(cursor);

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                cursor=studentRepo.getStudentListByKeyword(s.toString());
                if (cursor!=null){
                    customAdapter.swapCursor(cursor);
                }


            }

            public void afterTextChanged(Editable s) {

            }
        });
    }



   /* @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.menu, menu);
        // getMenuInflater().inflate(R.menu.menu, menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        // searchMenuItem = menu.findItem(R.id.mi_search);



        menuitem.setOnActionExpandListener(menu.findItem(R.id.mi_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                Log.e("testing", "search is expanding");


               // listview.setVisibility(View.VISIBLE);

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                Log.e("testing","testing only");

               // listview.setVisibility(View.GONE);
                //DO SOMETHING WHEN THE SEARCHVIEW IS CLOSING

                return true;
            }
        });


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.mi_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    // Log.d(TAG, "onQueryTextSubmit ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //  Log.d(TAG, "onQueryTextChange ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor!=null){
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }



        return true;

    }
*/


    class searchfunction extends AsyncTask<String, String,  Map<String,String>> {
        String responce;
        String spintype;
        String status;
        String arrayresponce;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        public  Map<String,String> doInBackground(String... args) {
            // Create an array
            //   search_array = new ArrayList<String>();
            mListFeederInfo = new ArrayList<Info_MyCart>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            //Log.e("testing", "user_id value=" + id);
            Map<String,String> productRateMap = new LinkedHashMap<String, String>();


            JSONObject json = jsonParser.makeHttpRequest("http://www.amedo.in/api/filter_medicine", "GET", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);


            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");
                    arrayresponce = json.getString("product");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return productRateMap;
        }

        @Override
        protected void onPostExecute( Map<String,String> responce) {
            super.onPostExecute(responce);

            Log.e("testing", "response is = " + responce.size());

            if (status.equals("success")) {


                //---------for deleting table database--------------------
                dbHelper = new DBHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(Student.TABLE, null,null);



                studentRepo = new StudentRepo(MainActivity.this);
                cursor=studentRepo.getStudentList();
                customAdapter = new CustomAdapter(MainActivity.this,  cursor, 0);
                listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(customAdapter);



                try {

                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();




                            Student student= new Student();

                            student.student_ID=post.optInt("product_id");
                            student.email=post.optString("product_id");
                            student.name=post.optString("product_name");
                            studentRepo.insert(student);


                        /*    item.set_cartid( post.optString("product_id"));
                            item.set_title( post.optString("product_name"));
                            mListFeederInfo.add(item);*/
                            //  search_array.add(post.optString("product_name"));
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*


                adapter = new ArrayAdapter<String>(Activity_Home.this, android.R.layout.simple_list_item_1, search_array);
                listview = (ListView) findViewById(R.id.listView1);
                listview.setAdapter(adapter);
*/

                String s = "";
                cursor=studentRepo.getStudentListByKeyword(s.toString());
                customAdapter.swapCursor(cursor);

            } else {

                //  Log.e("testing1", "json response == " + response);


            }





        }

    }


}
