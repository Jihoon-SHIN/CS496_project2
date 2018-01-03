package com.example.user.cs496_project2_sjh;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_contact extends Fragment {
    private String memid, key,facebookValue;
    public Fragment_contact(String memid, String key, String facebookValue)
    {
        // required
        this.memid = memid;
        this.key = key;
        this.facebookValue = facebookValue;
    }
    private ListViewAdapter adapter;
    private ListView listView;
    private List<listviewitem> list;
    private Intent intent;
    private static final int  MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int permissionCheck = ContextCompat.checkSelfPermission((Activity) getContext(),
                Manifest.permission.WRITE_CALENDAR);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission((Activity)getContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)getContext(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myfragmentView = inflater.inflate(R.layout.fragment_contact,container,false);

        Button button = (Button)myfragmentView.findViewById(R.id.diabutton2);
        listView = (ListView)myfragmentView.findViewById(R.id.listview1);
        list = new ArrayList<>();

        //intent = getActivity().getIntent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //if (intent.getStringExtra("key").equals("login_own")) {
                if (key.equals("login_own")) {
                    String temp = null;
                    JSONArray jsonArray = new JSONArray();
                    add_mycontact_db add_mycontact_db = new add_mycontact_db(getContext());
                    ArrayList<Map<String, String>> dataList = add_mycontact_db.function();
                    for (int i = 0; i < dataList.size(); i++) {
                        BitmapDrawable ico = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.minah);
                        Bitmap icon = ico.getBitmap();
//                        list.add(mlistview(icon, dataList.get(i).get("name"), dataList.get(i).get("phone")));
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.accumulate("memberID", memid);
                            jsonObject.accumulate("img", null);
                            jsonObject.accumulate("name", dataList.get(i).get("name"));
                            jsonObject.accumulate("phone", dataList.get(i).get("phone"));
//                            jsonObject.accumulate("check", 1);
                            jsonArray.put(jsonObject);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            /*        adapter = new ListViewAdapter(list, getContext());
                    listView.setAdapter(adapter);*/
                    new connecting_js(jsonArray, "/contacts", "", "", "POST").execute("http:13.124.40.52:9200/api/members/member");
                }else{
                    String json = facebookValue;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray1 = null;

                    try {
                        jsonArray1 = jsonObject.getJSONObject("taggable_friends").getJSONArray("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray2 = new JSONArray();
                    for(int i=0; i<jsonArray1.length();i++) {
                        JSONObject object= null;
                        try {
                            object = (JSONObject) jsonArray1.get(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        URL profile_picture = null;
                        String url = null;
                        String name = null;
                        String url2 = null;
                        try {
                            profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
                            url = "https://graph.facebook.com/" + object.getString("id") + "/picture?width=50&height=50";
                            url2 = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            name = object.getString("name");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Bitmap myBitmap =getBitmap(url2);
                        Bitmap myBitmap2 = null;
                        try {
                            myBitmap2 = new imageTask().execute(url2).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
//                        list.add(mlistview(myBitmap2, name, "FaceBook은 연락처를 주기 싫어용"));
                        try {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.accumulate("memberID", memid);
                            jsonObject1.accumulate("name",name);
                            jsonObject1.accumulate("img", url2);
                            jsonObject1.accumulate("phone", "Facebook은 연락처를 안줘잉");
                            jsonArray2.put(jsonObject1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    new connecting_js(jsonArray2, "/contacts", "", "", "POST").execute("http:13.124.40.52:9200/api/members/member");
                    /*
                    adapter = new ListViewAdapter(list, getContext());
                    listView.setAdapter(adapter);*/
                }
            }
        });
        Button button_out = (Button)myfragmentView.findViewById(R.id.diabutton1);
        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_contact a = new add_contact();
                if (key.equals("login_own")){
                    String temp = null;
                    JSONArray jsonArray4 = new JSONArray();
                    try {
                        temp = new connecting_js(null, "/contacts", "", "", "GET").execute("http:13.124.40.52:9200/api/members/member").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonArray4 = new JSONArray(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i=0; i<jsonArray4.length();i++) {
                        JSONObject object = null;
                        String memberID = null;
                        String name = null;
                        String phone = null;
                        try {
                            object = (JSONObject) jsonArray4.get(i);
                            memberID = object.getString("memberID");
                            name = object.getString("name");
                            phone = object.getString("phone");
                            BitmapDrawable ico = (BitmapDrawable) getContext().getResources().getDrawable(R.drawable.minah);
                            Bitmap icon = ico.getBitmap();
                            list.add(mlistview(icon, name, phone));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new ListViewAdapter(list, getContext());
                    listView.setAdapter(adapter);
                }else{
                    String temp = null;
                    JSONArray jsonArray4 = new JSONArray();
                    try {
                        temp = new connecting_js(null, "/contacts", "", "", "GET").execute("http:13.124.40.52:9200/api/members/member").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonArray4 = new JSONArray(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i=0; i<jsonArray4.length();i++) {
                        JSONObject object = null;
                        String memberID = null;
                        String name = null;
                        String phone = null;
                        String img = null;
                        try {
                            object = (JSONObject) jsonArray4.get(i);
                            name = object.getString("name");
                            phone = object.getString("phone");
                            img = object.getString("img");

                            Bitmap myBitmap2 = null;
                            try {
                                myBitmap2 = new imageTask().execute(img).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            list.add(mlistview(myBitmap2, name, phone));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new ListViewAdapter(list, getContext());
                    listView.setAdapter(adapter);
                }
            }
        });

        return myfragmentView;
    }
    public listviewitem mlistview(Bitmap icon, String title, String desc){
        listviewitem item = new listviewitem();
        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        return item;
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    private Bitmap getBitmap(String url)  {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        Bitmap retBitmap = null;
        try {
            imgUrl = new URL(url);
            is = imgUrl.openStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
            return retBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            return retBitmap;
        }

    }
    public class imageTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL imgUrl= null;
            HttpURLConnection connection = null;
            InputStream is = null;
            Bitmap retBitmap = null;
            String url = strings[0];
            try {
                imgUrl = new URL(url);
                is = imgUrl.openStream(); // get inputstream
                retBitmap = BitmapFactory.decodeStream(is);
                return retBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return retBitmap;
            }
        }
    }

}