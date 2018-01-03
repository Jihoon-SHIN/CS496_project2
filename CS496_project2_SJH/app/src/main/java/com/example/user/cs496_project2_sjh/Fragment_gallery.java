package com.example.user.cs496_project2_sjh;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_gallery extends Fragment {
    public Fragment_gallery() {
    }

    Button buttonGetPhoto;
    Button buttonGetPhoto2;

    ImageView updatedPhoto;
    View myfragmentView;
    GridView gridViewImages;


    String _memberid = "memberID2";
    AddArray addArray = new AddArray(_memberid);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myfragmentView = inflater.inflate(R.layout.fragment_gallery,container,false);

        addArray.imageGridAdapter(myfragmentView,getActivity(), gridViewImages);




        //buttonGetPhoto = (Button)myfragmentView.findViewById((R.id.getPhoto));
        //buttonGetPhoto2 = (Button)myfragmentView.findViewById((R.id.getPhoto_2));
        updatedPhoto = (ImageView)myfragmentView.findViewById(R.id.imageView3);

        GridView gv = (GridView) myfragmentView.findViewById(R.id.gridViewImages);

        Ion.getDefault(getContext()).configure().setLogging("ion-sample", Log.DEBUG);


        // Floating Action Button을 리스트 뷰에 적용
        FloatingActionButton fab = (FloatingActionButton) myfragmentView.findViewById(R.id.fab);
        fab.attachToListView(gv);

        // 이벤트 적용
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
                fintent.setType("image/jpeg");

                try {

                    startActivityForResult(fintent, 100);

                } catch (ActivityNotFoundException e) {

                }
            }
        });

//        buttonGetPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//        });
//
//        buttonGetPhoto2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String output = null;
//
//                addArray.imageGridAdapter(myfragmentView,getActivity(), gridViewImages);
//
//            }
//        });

        return myfragmentView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {


                    String test = encodeImage(RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData()));


                    String _img = "testImage";


                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        jsonObject1.accumulate("memberID",_memberid);
                        jsonObject1.accumulate("img",test);
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject1);
                        new connecting_jh(jsonArray,"/images","","","POST").execute("");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    byte[] imageBytes = Base64.decode(test, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    updatedPhoto.setImageBitmap(decodedImage);

                    //Toast.makeText(getContext(),data.getData().getPath(), Toast.LENGTH_LONG).show();

                    addArray.imageGridAdapter(myfragmentView,getActivity(), gridViewImages);




                }
        }
    }
    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG,20,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;

    }

}