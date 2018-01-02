package com.example.user.cs496_project2_sjh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2018-01-02.
 */

public class AddArray{


    public ArrayList<String> setArray() {

        ArrayList<String> mylist = new ArrayList<String>();
        String output = null;

        try {

            output = new connecting_jh(null, "/image", "", "", "GET").execute("").get();
            JSONArray jr = new JSONArray(output);

            JSONObject object = jr.getJSONObject(0);
            output = object.getString("img");
            mylist.add(output);

            object = jr.getJSONObject(1);
            output = object.getString("img");
            mylist.add(output);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mylist;
    }
}
