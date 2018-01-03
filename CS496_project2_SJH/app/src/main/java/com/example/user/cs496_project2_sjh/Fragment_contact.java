package com.example.user.cs496_project2_sjh;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_contact extends Fragment {
    public Fragment_contact()
    {
        // required

    }
    private ListViewAdapter adapter;
    private ListView listView;
    private List<listviewitem> list;

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_mycontact_db add_mycontact_db = new add_mycontact_db(getContext());
                ArrayList<Map<String, String>> dataList = add_mycontact_db.function();
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for(int i=0; i<dataList.size(); i++){
                    list.add(mlistview(R.drawable.minah, dataList.get(i).get("name"),dataList.get(i).get("phone")));
                    try {
                        jsonObject.accumulate("name", dataList.get(i).get("name"));
                        jsonObject.accumulate("phone", dataList.get(i).get("phone"));
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new ListViewAdapter(list, getContext());
                Log.d(null, "onClick: fucking");
                listView.setAdapter(adapter);
//                new connecting_js(jsonArray, "/members", "", "", "POST").execute("");
            }
        });
        return myfragmentView;
    }
    public listviewitem mlistview(int icon, String title, String desc){
        listviewitem item = new listviewitem();
        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        return item;
    }
}