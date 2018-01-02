package com.example.user.cs496_project2_sjh;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_gallery extends Fragment {
    public Fragment_gallery()
    {
        // required
    }

    String path;
    Button buttonGetPhoto;
    Button buttonGetPhoto2;

    ImageView mainPhoto;
    ImageView updatedPhoto;
    EditText editText;
    EditText editText2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AddArray addArray = new AddArray();
        final ArrayList<String> imageIDs = new ArrayList<String>(addArray.setArray());
        View myfragmentView = inflater.inflate(R.layout.fragment_gallery,container,false);
        final Handler handler = new Handler();
        GridView gridViewImages = (GridView) myfragmentView.findViewById(R.id.gridViewImages);



        SecondTab_ImageGridAdapter imageGridAdapter = new SecondTab_ImageGridAdapter(getActivity(),imageIDs, getActivity());
        gridViewImages.setAdapter(imageGridAdapter);



        //        SecondTab_ImageGridAdapter imageGridAdapter = new SecondTab_ImageGridAdapter(getActivity(),imageIDs, getActivity());
//
//        ImageView label;
//        Bitmap bmp = null;
//
//        label = (ImageView)layout.findViewById(R.id.imageView2);
//        URL url = null;
//        try {
//            url = new URL("http://myhswm.org/images/sized/images/animals/August-256x256.jpg");
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
////              label.setImageBitmap(bmp);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


//
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {    // 오래 거릴 작업을 구현한다
//                // TODO Auto-generated method stub
//                try{
//                    // 걍 외우는게 좋다 -_-;
//                    final ImageView iv = (ImageView)myfragmentView.findViewById(R.id.imageView2);
//                    URL url = new URL("http://myhswm.org/images/sized/images/animals/August-256x256.jpg");
//                    InputStream is = url.openStream();
//                    final Bitmap bm = BitmapFactory.decodeStream(is);
//                    handler.post(new Runnable() {
//
//                        @Override
//                        public void run() {  // 화면에 그려줄 작업
//                            iv.setImageBitmap(bm);
//                        }
//                    });
//                    iv.setImageBitmap(bm); //비트맵 객체로 보여주기
//                } catch(Exception e){
//
//                }
//
//            }
//        });

//        t.start();


        buttonGetPhoto = (Button)myfragmentView.findViewById((R.id.getPhoto));
        buttonGetPhoto2 = (Button)myfragmentView.findViewById((R.id.getPhoto_2));

        updatedPhoto = (ImageView)myfragmentView.findViewById(R.id.imageView3);

        Ion.getDefault(getContext()).configure().setLogging("ion-sample", Log.DEBUG);



        buttonGetPhoto.setOnClickListener(new View.OnClickListener() {
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

        buttonGetPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = null;
                try {
                    output = new connecting_jh(null, "/image", "", "", "GET").execute("").get();
                    JSONArray jr = new JSONArray(output);
                    JSONObject object = jr.getJSONObject(0);

                    //editText2.setText(object.getString("img"));

                    byte[] imageBytes = Base64.decode(object.getString("img"), Base64.DEFAULT);
//                    byte[] imageBytes = Base64.decode(imageIDs.get(0), Base64.DEFAULT);

                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                    updatedPhoto.setImageBitmap(decodedImage);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return myfragmentView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    path = data.getData().getPath();
                    mainPhoto.setImageURI(data.getData());
                    String test = encodeImage(RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData()));
                    editText.setText(test);

                    String _memberid = "memberID";
                    String _img = "testImage";


                    //JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        jsonObject1.accumulate("memberID",_memberid);
                        jsonObject1.accumulate("img",test);
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject1);
                        new connecting_jh(jsonArray,"/images","","","POST").execute("");

                        //String var =  new connecting_jh(jsonArray,"/image","","","GET").execute("").get();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    byte[] imageBytes = Base64.decode(test, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    updatedPhoto.setImageBitmap(decodedImage);

                    //"testImage" --> "test"로 수정하기

                    //Toast.makeText(getContext(),data.getData().getPath(), Toast.LENGTH_LONG).show();

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
        //Base64.de
        return encImage;

    }

}




