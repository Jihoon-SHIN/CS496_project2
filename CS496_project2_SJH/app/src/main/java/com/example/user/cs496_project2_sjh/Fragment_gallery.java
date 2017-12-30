package com.example.user.cs496_project2_sjh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 2017-12-30.
 */

public class Fragment_gallery extends Fragment {
    public Fragment_gallery()
    {
        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//          LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_gallery, container, false);

//        GridView gridViewImages = (GridView) myfragmentView.findViewById(R.id.gridViewImages);
//        SecondTab_ImageGridAdapter imageGridAdapter = new SecondTab_ImageGridAdapter(getActivity(),imageIDs, getActivity());
//        gridViewImages.setAdapter(imageGridAdapter);

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

        final Handler handler = new Handler();


        final View myfragmentView = inflater.inflate(R.layout.fragment_gallery,container,false);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;
                    final ImageView iv = (ImageView)myfragmentView.findViewById(R.id.imageView2);
                    URL url = new URL("http://myhswm.org/images/sized/images/animals/August-256x256.jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            iv.setImageBitmap(bm);
                        }
                    });
                    iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){

                }

            }
        });

        t.start();


        return myfragmentView;
    }
}