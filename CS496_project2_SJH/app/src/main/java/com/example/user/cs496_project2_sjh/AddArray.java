package com.example.user.cs496_project2_sjh;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2018-01-02.
 */

public class AddArray{

    public ArrayList<String> imageIDs;
    private String memberID;
    private SecondTab_ImageGridAdapter imageGridAdapter;

    public AddArray(String id){
        this.memberID = id;
    }


    public ArrayList<String> setArray(String id) {

        ArrayList<String> mylist = new ArrayList<String>();
        String output = null;

        try {

            output = new connecting_jh(null, "/images/memberID/"+memberID, "", "", "GET").execute("").get();
            if(output != null){
                JSONArray jr = new JSONArray(output);
                for(int i = 0; i <jr.length() ; i++){
//                    int temp ;
//                    temp  = jr.length()-i-1;
                    JSONObject object = jr.getJSONObject(i);
                    output = object.getString("img");
                    mylist.add(output);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mylist;
    }


    public void imageGridAdapter(View myfragmentView, FragmentActivity activity, GridView gridViewImages) {
        imageIDs = setArray(memberID);
        int i = 0;
        if(imageIDs.size() ==0){
            Toast.makeText(activity,"Please add your photos!!" + imageIDs.size(), Toast.LENGTH_LONG).show();
            i = 1;
        }else{
            gridViewImages = (GridView) myfragmentView.findViewById(R.id.gridViewImages);
            imageGridAdapter = new SecondTab_ImageGridAdapter(activity,setArray(memberID), activity);
            gridViewImages.setAdapter(imageGridAdapter);
            Toast.makeText(activity,  imageIDs.size() +"photos are Uploaded", Toast.LENGTH_LONG).show();
        }
    }

}
